# -*- coding: utf-8 -*-

# Imports here.
import tweepy
import pprint
from pymongo import MongoClient

# This Python class will handdle all the twitter stuff and mongo.

class TwitterManager(object):
    # Attributes:
    # Mongo Setup
    client = MongoClient()
    db = client['TwitterDB']

    #  Set the keys and secrets for auth.
    key = "lnkoYWQwNYuMhMlmuJrq1ACnV"
    key_secret = "ZHARBM8iUNvZvy1HjI6KEsWqMOqlScq9ceREFXVwwn5my0sBJk"

    token = "399890384-FzHEFK7eVOVJkwhlQib2OXHg8eUeIxxw0pj88xGh"
    token_secret = "00VhW963RJer5xCS6RqK8Ww94BgrNDlvKZI7SUfK2BUXq"

    # Initial method.
    def __init__(self, user_name):
        # Setup a tweety conection.
        auth = tweepy.OAuthHandler(self.key, self.key_secret)
        auth.set_access_token(self.token, self.token_secret)
        self.client = tweepy.API(auth)
        self.user = self.client.get_user(screen_name=user_name)

        # Save the user's timeline to mongo collection.
        # Open the collection.
        collection = self.db[user_name]

        # Delete the old data if exists.
        collection.delete_many({})

        # Create a placeholder for posts.
        tweets = []

        # Get timeline.
        timeline = self.user.timeline()

        # Iterate and get all posible tweets.
        for item in timeline:
            text = item.text.encode('utf-8', errors='replace')
            tweets.append( {"author": item.user.screen_name,
                    "content": text})

        # Insert all posts.
        collection.insert_many(tweets)

    # Return all the tweets from the collection.
    def getTimeline(self, user_name):
        # Open the collection.
        collection = self.db[user_name]

        # Get all the tweets from collection.
        tweets = collection.find()

        # Make a placeholder for posts.
        posts = []

        # Print all the collection.
        for post in collection.find():
            #print type(post)
            posts.append(post)

        #print type(posts)
        return posts
    
    # Return an array of data with the following structure:
    # [0]: Happiness #.
    # [1]: Angriness #.
    # [2]: Relax #.
    # [3]: Neutral #.
    # [4]: Number of #.
    # [5]: Words #.
    # [6]: Tweets #.
    # This in an Array.
    def getEmotionAnalisis(self, user_name):
        # Initialize all the arrays with some words.
        happy = ["feliz", "emocion", "alegria", "fortuna", "bonito", "bendecido", "alegre", "bien", "orgullo", "enhorabuena", "orgulloso"]
        angry = ["enojo", "frustrado", "infeliz", "maldito", "estupido", "me enoja", "molesto", "molesta", "ofendido", "ultimo"]
        relax = ["chill", "tranquilo", "fresh", "relajado", "vacaciones", "descanso", "relajando", "despreocupado"]
        
        # Initialize the counters.
        happyCounter = 0
        angryCounter = 0
        relaxCounter = 0
        neutralCounter = 0
        hashtagCounter = 0
        
        wordCounter = 0
        tweetsCounter = 0
        
        # Open the collection.
        collection = self.db[user_name]

        # Get all the tweets from collection.
        tweets = collection.find()

        # Make a placeholder for posts.
        posts = []

        # Print all the collection.
        for post in collection.find():
            words = post['content'].split()
            for word in words:
                # Check if happy.
                if (word.lower() in happy):
                    happyCounter += 1
                # Check if angry.
                elif (word.lower() in angry):
                    angryCounter += 1
                # Check if relax.
                elif (word.lower() in relax):
                    relaxCounter += 1
                # If none, then neutral.
                else:
                    neutralCounter += 1
                    
                # Check for hashtag
                if ('#' in word):
                    hashtagCounter += 1
                    
                # Count a word
                wordCounter += 1
                
            tweetsCounter += 1
            
        analisis = [(happyCounter), 
                    (angryCounter),
                    (relaxCounter),
                    (neutralCounter),
                    (hashtagCounter),
                    (wordCounter),
                    (tweetsCounter)]

        return analisis

    # This is an easter egg.
    @staticmethod
    def getPlatypus():
        return "Perry"


test = TwitterManager("Lebusinessking_")
posts = test.getTimeline("Lebusinessking_")
#pprint.pprint(posts)
analisis = test.getEmotionAnalisis("Lebusinessking_")
print analisis