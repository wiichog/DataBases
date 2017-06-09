/**
 * 
 */
package Controllers;

import java.awt.Rectangle;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import org.apache.poi.sl.usermodel.PictureData;
import org.apache.poi.util.IOUtils;
import org.apache.poi.xslf.usermodel.SlideLayout;
import org.apache.poi.xslf.usermodel.XMLSlideShow;
import org.apache.poi.xslf.usermodel.XSLFPictureData;
import org.apache.poi.xslf.usermodel.XSLFPictureShape;
import org.apache.poi.xslf.usermodel.XSLFShape;
import org.apache.poi.xslf.usermodel.XSLFSlide;
import org.apache.poi.xslf.usermodel.XSLFSlideLayout;
import org.apache.poi.xslf.usermodel.XSLFSlideMaster;
import org.apache.poi.xslf.usermodel.XSLFTextShape;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;

/**
 * @author Perry
 *
 */
public class Reporteria {
	
	// Private variables...
	private String pathToResources = "./resources/";
	
	private String fileName;    
	
	public Reporteria(String fileName) throws IOException {
		this.fileName = fileName + ".pptx";
		
		//creating a new empty slide show
		XMLSlideShow ppt = new XMLSlideShow();	     
		  
		//creating an FileOutputStream object
		File file = new File(pathToResources + this.fileName);
		FileOutputStream out = new FileOutputStream(file);
		
		//adding slides to the slodeshow
		
		XSLFSlideMaster slideMaster = ppt.getSlideMasters().get(0);
		
		XSLFSlideLayout contentlayout = slideMaster.getLayout(SlideLayout.TITLE);
		
		XSLFSlide slide = ppt.createSlide(contentlayout);
		
		//selection of title place holder
	    XSLFTextShape title = slide.getPlaceholder(0);
	    
	    //setting the title in it
	    title.setText("Reporte sobre la base de datos");
	    
	    //selection of title place holder
	    XSLFTextShape text = slide.getPlaceholder(1);
	    
	    //setting the title in it
	    text.setText("Por:\nJuan Luis García\nFreddie Batlle\nSantiago Paíz\nAlejandro Cortés");
		  
		//saving the changes to a file
		ppt.write(out);
		System.out.println("Presentation created successfully");
		out.close();
	}
	
	public Reporteria() throws IOException {
		this.fileName = "reporte.pptx";
		
		//creating a new empty slide show
		XMLSlideShow ppt = new XMLSlideShow();	     
		  
		//creating an FileOutputStream object
		File file = new File(pathToResources + this.fileName);
		FileOutputStream out = new FileOutputStream(file);
		
		//adding slides to the slodeshow
		
		XSLFSlideMaster slideMaster = ppt.getSlideMasters().get(0);
		
		XSLFSlideLayout contentlayout = slideMaster.getLayout(SlideLayout.TITLE);
		
		XSLFSlide slide = ppt.createSlide(contentlayout);
		
		//selection of title place holder
	    XSLFTextShape title = slide.getPlaceholder(0);
	    
	    //setting the title in it
	    title.setText("Reporte sobre la base de datos");
	    
	    //selection of title place holder
	    XSLFTextShape text = slide.getPlaceholder(1);
	    
	    //setting the title in it
	    text.setText("Por:\nJuan Luis García\nFreddie Batlle\nSantiago Paíz\nAlejandro Cortés");
		
		//saving the changes to a file
		ppt.write(out);
		System.out.println("Presentation created successfully");
out.close();
	}
	
	public void display(String Titulo, String[] values, String text) throws IOException {
		//opening an existing slide show
		File file = new File(pathToResources + this.fileName);
		FileInputStream inputstream=new FileInputStream(file);
		XMLSlideShow ppt = new XMLSlideShow(inputstream);
		
		//adding slides to the slodeshow
		
		XSLFSlideMaster slideMaster = ppt.getSlideMasters().get(0);
		
		XSLFSlideLayout contentlayout = slideMaster.getLayout(SlideLayout.TITLE_AND_CONTENT);
		
		XSLFSlide slide = ppt.createSlide(contentlayout);
		
		//selection of title place holder
	    XSLFTextShape title = slide.getPlaceholder(0);
	    
	    //setting the title in it
	    title.setText(Titulo);
	    
	    //selection of title place holder
	    XSLFTextShape desc = slide.getPlaceholder(1);
	    
	    //setting the description here.
	    String placeholder = "";
	    for (String value : values) {
	    	placeholder += '\n' + value;
	    }
	    desc.setText(text + '\n' + placeholder);
	    
	    //saving the changes 
  		FileOutputStream out = new FileOutputStream(file);
  		ppt.write(out);
  		  
  		System.out.println("Presentation edited successfully");
  		out.close();
	}
	
	public void bars (int[] valores, String[] names, String nombre) throws IOException {
		//opening an existing slide show
		File file = new File(pathToResources + this.fileName);
		FileInputStream inputstream=new FileInputStream(file);
		XMLSlideShow ppt = new XMLSlideShow(inputstream);
		
		//adding slides to the slodeshow
		
		XSLFSlideMaster slideMaster = ppt.getSlideMasters().get(0);
		
		XSLFSlideLayout contentlayout = slideMaster.getLayout(SlideLayout.TITLE_ONLY);
		
		XSLFSlide slide = ppt.createSlide(contentlayout);
		
		//selection of title place holder
	    XSLFTextShape title = slide.getPlaceholder(0);
	    
	    //setting the title in it
	    title.setText("Reporte de: " + nombre);
	    
	    //reading an image
	    String img = makeBarChar(valores, names, nombre);
	    File image = new File(img);
	    
	    //converting it into a byte array
	    byte[] picture = IOUtils.toByteArray(new FileInputStream(image));
	      
	    //adding the image to the presentation
	    XSLFPictureData idx = ppt.addPicture(picture, PictureData.PictureType.JPEG);
	    
	    //selection of body placeholder
	    XSLFPictureShape graph1 = slide.createPicture(idx);
	    graph1.setAnchor(new Rectangle(150, 150, 350, 350));
	    
	    //saving the changes 
		FileOutputStream out = new FileOutputStream(file);
		ppt.write(out);
		  
		System.out.println("Presentation edited successfully");
		out.close();
		
	}
	
	public void makeTwitterReports(List<String> users) throws IOException {
		
		//opening an existing slide show
		File file = new File(pathToResources + this.fileName);
		FileInputStream inputstream=new FileInputStream(file);
		XMLSlideShow ppt = new XMLSlideShow(inputstream);
		
		// We'll do this for each element on users.
		for (String iter : users) {
		
			//adding slides to the slodeshow
			
			XSLFSlideMaster slideMaster = ppt.getSlideMasters().get(0);
			
			XSLFSlideLayout contentlayout = slideMaster.getLayout(SlideLayout.TWO_TX_TWO_OBJ);
			
			XSLFSlide slide = ppt.createSlide(contentlayout);
			
			//selection of title place holder
		    XSLFTextShape title = slide.getPlaceholder(0);
		    
		    //setting the title in it
		    title.setText("User: " + iter);
		    
		    //selection of body placeholder
		    XSLFTextShape text1 = slide.getPlaceholder(1);
  
		    //clear the existing text in the slide
		    text1.clearText();
  
		    //adding new paragraph
		    text1.addNewTextParagraph().addNewTextRun().setText("Twitter report for user:\n" + iter);
		    
		    // Getting twitter data.
		    Twitter tw = new Twitter(iter);
			
			int[] values = tw.getAnalisis(iter);
			String[] names = {"Happiness", "Angriness", "Relaxness", "# Count", "Word Count", "Tweets"};
		    
		    //reading an image
		    String img = makePieChart(values, names);
		    File image = new File(img);
		    
		    //converting it into a byte array
		    byte[] picture = IOUtils.toByteArray(new FileInputStream(image));
		      
		    //adding the image to the presentation
		    XSLFPictureData idx = ppt.addPicture(picture, PictureData.PictureType.JPEG);
		    
		    //selection of body placeholder
		    XSLFPictureShape graph1 = slide.createPicture(idx);
		    graph1.setAnchor(new Rectangle(365, 140, 350, 350));
		    
		    //selection of body placeholder
		    XSLFTextShape text2 = slide.getPlaceholder(2);
  
		    //clear the existing text in the slide
		    text2.clearText();
  
		    //adding new paragraph
		    
		    if (values[6] <= 0) {
		    	values[6] = 1;
		    }
		    
		    text2.addNewTextParagraph().addNewTextRun().setText("The emotions for this user where:\n" + 
		    "Happiness: " + values[0] +
		    "\nAngriness: " + values[1] +
		    "\nRelaxness: " + values[2] +
		    "\nNeutral: " + values[3] +
		    "\n# Counter: " + values[4] +
		    "\nWords per tweet(avg): " + values[5]/values[6] +
		    "\n Report ended for this user.");
		    
		}
		  
		//saving the changes 
		FileOutputStream out = new FileOutputStream(file);
		ppt.write(out);
		  
		System.out.println("Presentation edited successfully");
out.close();
		
		
	}
	
	private static PieDataset createDataset( int[] values, String[] names ) {
		DefaultPieDataset dataset = new DefaultPieDataset( );
		for (int i = 0; i < names.length; i++) {
			dataset.setValue(names[i], values[i]);
		}
		return dataset;         
	}
	
	private static CategoryDataset createDataset(int[] values, String[] names, String name) {
		DefaultCategoryDataset dataset = new DefaultCategoryDataset( );
		for (int i = 0; i < names.length; i++) {
			dataset.addValue(values[i], names[i], name);
		}
		return dataset;
	}
	
	private String makeBarChar(int[] values, String[] names, String name) throws IOException {
		JFreeChart barChart = ChartFactory.createBarChart(
		         name+"_Bars",           
		         "Category",            
		         "Score",            
		         createDataset(values, names, name),          
		         PlotOrientation.VERTICAL,           
		         true, true, false);
		
		int width = 480;   /* Width of the image */
	    int height = 480;  /* Height of the image */ 
	    File pieChart = new File(pathToResources + name + "BarChartChart.jpeg"); 
	    ChartUtilities.saveChartAsJPEG( pieChart , barChart , width , height );
	    
	    return pathToResources + name + "BarChartChart.jpeg";
	}
	
	private String makePieChart(int[] values, String[] names) throws IOException {
		
		DefaultPieDataset dataset = (DefaultPieDataset) createDataset(values, names);
		
		JFreeChart chart = ChartFactory.createPieChart(      
		         "Reporte Emociones",   // chart title 
		         dataset,          // data    
		         true,             // include legend   
		         true, 
		         false);
		
		int width = 480;   /* Width of the image */
	    int height = 480;  /* Height of the image */ 
	    File pieChart = new File(pathToResources + "PieChart.jpeg" ); 
	    ChartUtilities.saveChartAsJPEG( pieChart , chart , width , height );
	    
	    return pathToResources + "PieChart.jpeg";
	}
}
