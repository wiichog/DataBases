/*
 * Juan Carlos Tapia 14133
 * Juan Luis Garcia 14189
 * Olga Cobaquil  13020
 * 
 * Proyecto 1. Base de datos.
 * 
 * Clase: Interfaz.java
 * 
 * Clase que se encarga de controlar lo que es la interfaz grafica de la aplicacion.
 * */

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import javax.swing.JTable;
import javax.swing.JCheckBox;
import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;

public class Interfaz {

	// atributos de la GUI.
	private JFrame frame;
	Recorrido recorrer = null;
	private JTextArea Verbose; 
	private JTable table;
	private JCheckBox verb;
	
	// Texto del verbose.
	public static String verbose = "";

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Interfaz window = new Interfaz();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Interfaz() {
		verbose+="Programa iniciado \n";
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 * @return 
	 */
	public void initialize() {
		
		verbose+= "Interfaz grafica desplegada";
		frame = new JFrame();
		frame.setBounds(100, 100, 978, 830);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		
		JScrollPane scrollPane_2 = new JScrollPane();
		scrollPane_2.setBounds(12, 373, 883, 87);
		frame.getContentPane().add(scrollPane_2);
		JTextArea textArea_1 = new JTextArea();
		scrollPane_2.setViewportView(textArea_1);
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(12, 13, 883, 315);
		frame.getContentPane().add(tabbedPane);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		tabbedPane.addTab("IDE", null, scrollPane_1, null);
		
		JTextArea txtrClassProgramVoid = new JTextArea();
		scrollPane_1.setViewportView(txtrClassProgramVoid);
		
		
		
		
		
        
		
		JButton btnCompiilar = new JButton("Ejecutar");
		btnCompiilar.setBounds(342, 329, 195, 43);
		frame.getContentPane().add(btnCompiilar);
		
		JScrollPane scrollPane_3 = new JScrollPane();
		scrollPane_3.setBounds(12, 665, 883, 87);
		frame.getContentPane().add(scrollPane_3);
		
		Verbose = new JTextArea();
		scrollPane_3.setViewportView(Verbose);
		Verbose.hide();
		
		JScrollPane scrollPane_4 = new JScrollPane();
		scrollPane_4.setBounds(12, 462, 880, 162);
		frame.getContentPane().add(scrollPane_4);
		
		table = new JTable();
		scrollPane_4.setViewportView(table);
		
		
		// Se leen los archivos de los directorios.
		verbose+="Leyendo archivos de data \n";
		try {
			recorrer = new Recorrido(table);
			
			verb = new JCheckBox("verbose");
			verb.addChangeListener(new ChangeListener() {
				public void stateChanged(ChangeEvent arg0) {
					if(verb.isSelected()){
						Verbose.show();
					}else{
						Verbose.hide();
					}
				}
			});
			verb.setBounds(35, 631, 113, 25);
			frame.getContentPane().add(verb);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		verbose+="Archivos de data leidos \n";
		
		 btnCompiilar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				//System.out.println("Click");
				 long startTotal = System.currentTimeMillis();

				 // Se llaman a los metodos que se encargan de los comentarios y los espacios.
				 verbose+="Leyendo codigo ingresado \n";
				String codigo = txtrClassProgramVoid.getText();
				codigo = Espacios(codigo);
				codigo = Comentarios(codigo);
				
				verbose+="Creando arbol sintactico \n";
				
				// Se llaman a los metods que construyen el arbol sintactico de ANTLR.
				 ANTLRInputStream input = new ANTLRInputStream(codigo);
				 GramaticaLexer lexer = new GramaticaLexer(input);
			     CommonTokenStream tokens = new CommonTokenStream(lexer);
			     MyErrorListener errorr = new MyErrorListener();	
			     lexer.removeErrorListeners();			        
			     lexer.addErrorListener(errorr); 
			     GramaticaParser parser = new GramaticaParser(tokens);
			     parser.removeErrorListeners();
			     parser.addErrorListener(errorr);   
			     ParseTree tree = parser.programa(); // begin parsing at rule 'r'
			        
			     verbose+="Detectan do errores \n";
			     
			     
			     int errores = parser.getNumberOfSyntaxErrors();
			        
			        
			       // System.out.println(errores);
			        
			      if(errores!=0){
			        	verbose+="Errores de sintaxis detectados. Desplegando errores \n";
			      }
			        // Se muestran los errores de sintaxis.
			        String s = "";
			        for (int i =0;i<errorr.ErrorMessages.size();i++){
			        	//System.out.println(errorr.ErrorMessages.get(i));
			        	s = s + errorr.ErrorMessages.get(i)+'\n';
			        }
			        
			        
			        textArea_1.setText(s);
			        
			        
			       
			       // tree_1.setModel(null);
			        
			        
			        
			        // Si no hay errores de sintaxis, se realiza el resto de la ejecucion
			        if(errores ==0){
			        	
			        	
			        	long startTime = System.currentTimeMillis();
			        	
			        	// Se crea el arbol personalizado.
			        	Arbol arbol = new Arbol(tree);

				        long endTime = System.currentTimeMillis();

						long duration = (endTime - startTime); 
						System.out.println("Creacion del arbol:" +duration/1000 +" segundos");

				        
				        
				       
				        
				       
				        
				        
				        
				        
				        //DefaultMutableTreeNode node = new DefaultMutableTreeNode (arbol.raiz.getData()+" - "+arbol.raiz.getTipo());
				        //arbol.MostrarArbol(arbol.raiz, -1,node);
				        //tree_1.setModel(new DefaultTreeModel(node));
				        
				        
				        verbose+="No se detectaron errores. Arbol sintactico creado \n";
				        
				        startTime = System.currentTimeMillis();
				        
				        verbose+="Ejecutando querys \n";			
				        
				        
				        // Se llama al metodo que realiza los Querys
				        recorrer.Realizar(arbol.raiz);
				        
				        // Se despliegan los resultados.
						String salida =recorrer.getSalidaTexto();
						textArea_1.setText(salida);
						
				        endTime = System.currentTimeMillis();

						duration = (endTime - startTime); 
						System.out.println("Ejecucion:" +duration/1000 +" segundos");

						verbose+="Ejecucion terminada \n";
						
						startTime = System.currentTimeMillis();
						
						verbose+= "Actualizando archivos de datos \n";
				        
						// Se actualizan los archivos que contienen la data.
						try {
							Archivar.Escribir(recorrer.basesDeDatos);
							
							//System.out.println("\n Hecho \n");
							//Archivar.Leer(recorrer.basesDeDatos);
						} catch (IOException e) {
							// TODO Auto-generated catch block
							//e.printStackTrace();
						}
				        
				        verbose+="Archivos de datos actualizados \n";
				        // Se muestra el verbose si esta seleccionado.
				        if(verb.isSelected())
				        	Verbose.setText(verbose);
				        
						 endTime = System.currentTimeMillis();

						 duration = (endTime - startTime); 
						System.out.println("Escritura:" +duration/1000 +" segundos");

				        
						
						
						long endTotal = System.currentTimeMillis();

						long durationTotal = (endTotal - startTotal); 
						System.out.println("Total:" +durationTotal/1000 +" segundos");

				        
			        }
			        
			       
			      
			      
			       
			        
			        
			        
			        
			        
			        
	
			}
		});
		
		
	
	
	}
	
	public String Espacios(String cadena){
		
		long startTime = System.currentTimeMillis();
		
	

		
		
		String nuevaCadena = "";
		
		
		int i =0;
		float antPor = 0;
		boolean apos = false;

		while (i<cadena.length()){
			
			if(cadena.substring(i).contains("\'")){
				
				
				
				if(!apos){
					int pos = i+ cadena.substring(i).indexOf("\'");
					
					nuevaCadena+= cadena.substring(i, pos)+"\'";
					i = pos+1;
					apos = true;
				}else{
					
					int pos = i+cadena.substring(i).indexOf("\'");
					
					nuevaCadena+= cadena.substring(i, pos).replace(" ", "#")+"\'";
						
					i = pos+1;
					apos = false;
				}
			}
			else{
				nuevaCadena+= cadena.substring(i);
				i = cadena.length();
			}
			
			
			
		}
		
	
		long endTime = System.currentTimeMillis();

		long duration = (endTime - startTime); 
		System.out.println("Conversion:" +duration/1000 +" segundos");
		
		//System.out.println(nuevaCadena);
		return nuevaCadena;
		
	}
	
	
	public String Comentarios(String cadena){
		
		long startTime = System.currentTimeMillis();
		
	

		
		int i =0;
		String nuevaCadena = "";
		
		boolean comentario = false;

		if(!cadena.contains("--")){
			return cadena;
		}
		while (i<cadena.length()){
			if(cadena.substring(i).contains("--")){
				
				if(comentario){
					
					if(cadena.substring(i).contains("\n")){
						int pos = i+ cadena.substring(i).indexOf("\n");
						i = pos+1;
						comentario = false;
						nuevaCadena+="\n";
						
					}else{
						i = cadena.length();
					}
				}else{
					
					int pos = i+cadena.substring(i).indexOf("--");
					
					nuevaCadena+= cadena.substring(i, pos);
						
					i = pos;
					comentario = true;
				}
			}
			else{
				nuevaCadena+= cadena.substring(i);
				i = cadena.length();
			}
		}
		
		return nuevaCadena;
		
	}
}
