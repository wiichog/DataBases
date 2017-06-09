/*
 * Juan Carlos Tapia 14133
 * Juan Luis Garcia 14189
 * Olga Cobaquil  13020
 * 
 * Proyecto 1. Base de datos.
 * 
 * Clase: Arbol.java
 * 
 * Clase que lee la informacion del arbol sintactico de ANTLR y la traduce a un arbol propio.
 * 
 * */

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JTextArea;
import javax.swing.tree.DefaultMutableTreeNode;

import org.antlr.runtime.CommonToken;
import org.antlr.runtime.Token;
import org.antlr.runtime.tree.Tree;
import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;


import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;



public class Arbol {
	// Arbol propio
	Node raiz;
	
	
	// Constructor
	public Arbol(ParseTree tree) {
		super();
		
		raiz = CrearArbol(tree);
	}
	
	
	// Metodo que lee el arbol sintactico de ANTLR, y devuelve un arbol personal. 
	public Node CrearArbol(ParseTree tree ){
		
		// Se leyo una hoja del arbol
    	if(tree.getChildCount()==0){
    		
    		// Se obtiene el valor del token
    		String ob =  tree.getPayload().toString();
    		int i = ob.indexOf("'");
    		int j = ob.substring(i+1, ob.length()).indexOf("'")+i+1;
    		String linea = ob.split(",")[ob.split(",").length-1];
    		
    		
    		// Se crea un nodo personal y se devuelve
    		Node newChild = new Node(ob.substring(i+1, j));
    		
    		return newChild;
    	}
    	
    	// Se lee un nodo con hijos, o sea, una produccion.
    	else
    	{
    		// Se obtiene el nombre de la produccion
    		Node newChild = new Node(tree.getPayload().getClass().toString().replaceAll("Context", "").replaceAll("Gramatica", "").replaceAll("Parser", "").replaceAll("$", "").replaceAll("class", "").substring(2));
    		
    		
    		// Se llama al metodo de forma recursiva para cada hijo
    		for(int i=0;i<tree.getChildCount();i++ ){
    			
    			
    			Node n = CrearArbol(tree.getChild(i));
    			
    			newChild.addHijos(n);
    		}
    		
    		// Si es un Char, se combinan los caracteres para hacer una sola cadena.
    		if(newChild.getData().equals("Char_literal")){
    			String car = "";
    			for(int i=1;i<newChild.getHijos().size()-1;i++ ){
    				car+= newChild.getHijos().get(i).getData();
    			}
    			
    			newChild.getHijos().clear();
 
    			newChild.addHijos(new Node(""));
    			newChild.addHijos(new Node(car));
    			newChild.addHijos(new Node(""));
    		}
    		return newChild;
    		
    	}
		
	}
	
	
	
	
	
	/*

	// Metodo para desplegar el arbol en un Jtree
	public void MostrarArbol(Node tree, int nivel, DefaultMutableTreeNode node){
		nivel++;
		if(tree.getHijos().size()==0){
			
		
    		return;
    	}
    	else
    	{
    	
    		for(int i=0;i<tree.getHijos().size();i++ ){
    			//node.add(new DefaultMutableTreeNode(tree.getHijos().get(i).getData()+","+tree.getHijos().get(i).getAmbito()));
    			
    			//node.add(new DefaultMutableTreeNode(tree.getHijos().get(i).getData()+" - "+tree.getHijos().get(i).getTipo()+" - "+tree.getHijos().get(i).getLinea() ));
    			
    			//node.add(new DefaultMutableTreeNode(tree.getHijos().get(i).getData()));
    			MostrarArbol(tree.getHijos().get(i),nivel,(DefaultMutableTreeNode) node.getChildAt(i));
    		}
    		
    		
    	}
	}
	*/
	
	
	
	
	
	
	
	
	
}
