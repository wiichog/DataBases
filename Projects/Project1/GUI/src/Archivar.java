/*
 * Juan Carlos Tapia 14133
 * Juan Luis Garcia 14189
 * Olga Cobaquil  13020
 * 
 * Proyecto 1. Base de datos.
 * 
 * Clase: Archivar.java
 * 
 * Clase que lee la informacion de los archivos de data y metadata, y la guarda dentro del programa para que se pueda trabajar de forma mas sencilla.
 * 
 * */

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;



public class Archivar {

	
	// Constructor
	public Archivar(){
		
	}
	
	// Metodo que inicia la lectura, devolviendo un arreglo de bases de datos.
	public static ArrayList<Database>  Leer( ArrayList<Database> basesDeDatos) throws IOException{
		
		// Cadena guarda una linea de informacion del archivo.
		String cadena;
		
		// Se lee el archivo de la metadata de Bases de datos para ver que Bases de datos existen.
	      FileReader f = new FileReader("Servidor\\metaDB.txt");
	      BufferedReader b = new BufferedReader(f);
	      
	      // Se lee cada cadena, hasta llegar al final del archivo.
	      while((cadena = b.readLine())!=null) {
	    	  if(!cadena.equals(""))
		          if(cadena.charAt(0)=='-'){
		        	  int lim = cadena.indexOf(" {");
		        	  //System.out.println(cadena.substring(2, lim));
		        	  Interfaz.verbose+="Leyendo base de datos "+cadena.substring(2, lim)  + "\n";
		        	  
		        	  // Se crea un objeto de la clase Database, llamado data
		        	  Database data = new Database(cadena.substring(2, lim));
		        	  
		        	  // Se llama al metodo que lee las tablas de la BD y se las ingresa.
		        	  data = LeerTabla(data);
		        	  
		        	  // Se agrega data al Arreglo de Bases de datos.
		        	  basesDeDatos.add(data);
		        	  Interfaz.verbose+="base de datos "+cadena.substring(2, lim)  + " leida \n";
		          }
	      }
	      b.close();
	      f.close();
		return basesDeDatos;
	      
	}
	
	// Metodo que lee las tablas de un directorio, que es una base de datos, y las ingresa en la base de datos que recibe de atributo.
	
	public static Database  LeerTabla(Database data) throws IOException {
		String cadena;
		
		// Fase controla en que etapa de lectura de la metadata de una tabla se esta. Asi se sabe si toca guardar columnas o constraints.
		int fase = 0;
		
		
	     FileReader f2 = new FileReader("Servidor\\"+data.nombre+"\\MetaData.txt");
	     BufferedReader b2 = new BufferedReader(f2);
	    
	     // tabla es una variable temporal que representara una tabla creada con la informacion leida.
	     Tabla tabla = null;
	     
	     while((cadena = b2.readLine())!=null) {
	    	 
		     // Se cambia de fase con forme se topa con diferenes encabezados en la metadata de una tabla.
	    	 if(!cadena.equals("")){
	    		 if(cadena.equals("TABLA")){
	    			 fase = 1;
	    		 }
	    		 if(cadena.equals("COLUMNAS")){
	    			 fase = 2;
	    		 }
	    		 if(cadena.equals("PRIMARIAS")){
	    			 fase = 3;
	    		 }
	    		 if(cadena.equals("FORANEAS")){
	    			 fase = 4;
	    		 }
	    		 if(cadena.equals("CHECKS")){
	    			 fase = 5;
	    		 }
	    		 if(cadena.equals("___")){
	    			 data.addTabla(tabla);
	    		 }
	    		 
	    		 if(cadena.charAt(0)=='-'){
	    			 cadena = cadena.substring(2);
	    			 String[] datos;
	    			 switch (fase){
	    			 
	    			 // Se crea la tabla
	    			 	case 1:
	    			 		Interfaz.verbose+="	Leyendo tabla "+cadena  + "\n";
	    			 		tabla = new Tabla(cadena);
	    			 		
	    			 		// Se llama al metodo que lee los registros de la tabla.
	    			 		tabla.setRegistros(leerRegistros(data.nombre, cadena));
	    			 		Interfaz.verbose+="Tabla "+cadena  + " leida \n";
	    			 		break;
	    			 	
	    			 // Se leen las columnas.
	    			 	case 2:
	    			 		datos = cadena.split(" : ");
	    			 		tabla.columnas.add(new Columna(datos[0],datos[1], Integer.parseInt(datos[2])));
	    			 		break;
	    			 		
	    			 // Se leen las llaves primarias.
	    			 	case 3:
	    			 		datos = cadena.split(" : ");
	    			 		
	    			 		tabla.pk.add(new PrimaryKey(datos[0],datos[1]));
	    			 		break;
	    			 	
	    			 // Se leen las llaves foraneas
	    			 	case 4:
	    			 		datos = cadena.split(" : ");
	    			 		tabla.fk.add(new ForeignKey(datos[0],datos[1],datos[2],datos[3]));
	    			 		break;
	    			 		
	    			 // Se leen los checks.
	    			 	case 5:
	    			 		datos = cadena.split(" : ");
	    			 		tabla.checks.add(new Check(datos[0],datos[1],datos[2]));
	    			 		break;
	    			 }
	    		 }
	    	 }
	     }
	     
	     b2.close();
	      f2.close();
	      
	     // Al final se devuelve la base de datos con sus tablas ya ingresadas.
		return data;
		
	}
	
	// Metodo que lee los registros del archivo que representa una tabla en una base de datos.
	public static ArrayList<ArrayList<String>> leerRegistros(String BD, String tabla) throws IOException{
		// registros es una variable temporal que acumula los datos encontrados en el archivo.
		ArrayList<ArrayList<String>> registros = new ArrayList<ArrayList<String>>();
		String cadena;
	
		//File archivo = new File("Servidor\\"+data.nombre+"\\MetaData.txt");
		//System.out.println(archivo.exists());
		
		
	     FileReader f2 = new FileReader("Servidor\\"+BD+"\\"+tabla+".txt");
	     BufferedReader b2 = new BufferedReader(f2);
	    
	     
	     
	     while((cadena = b2.readLine())!=null) {
		     if(!cadena.equals("")){
		    	 // Datos es un array con cada elemento de un registro.
		    	 String[] datos = cadena.split(" , ");
		    	 
		    	 // Linea es un arraylist temporal que guardara una linea de registro para guardarla dentro del programa.
		    	 ArrayList<String> linea = new ArrayList<String>();
		    	 for(int i=0;i<datos.length;i++ ){
		    		 linea.add(datos[i]);
		    		 
		    	 }
		    	 // Se anade la linea a los registros
		    	 registros.add(linea);
		     }
	     }
		
		
	    
	     // Se devuelve el conjunto de registros de la tabla.
		return registros;
		
	}
	
	
	
	// Metodo que actualiza los archivos que guardan la data.
	public static void Escribir( ArrayList<Database> basesDeDatos) throws IOException{
		String cadena;
	      FileWriter f = new FileWriter("Servidor\\metaDB.txt");
	      
	      BufferedWriter bw = new BufferedWriter(f);
	      
	       
	      bw.write("DATABASES\n\n");
	    
	      // Se trabaja una base de datos a la ves
	      for(int i=0;i<basesDeDatos.size();i++ ){
	    	  // Se crea su representacion en la metadata de bases de datos.
	    	  bw.write("- "+basesDeDatos.get(i).getNombre()+" {Tables = "+Integer.toString(basesDeDatos.get(i).getCantTablas()) +"}\n");
	    	  
	    	  
	    	  
	    	  
	    	  FileWriter f2 = new FileWriter("Servidor\\"+basesDeDatos.get(i).getNombre()+"\\MetaData.txt");
	    	  BufferedWriter bw2 = new BufferedWriter(f2);
	    	  
	    	  // Se guarda la metadata de sus tablas.
	    	  for(int j=0;j<basesDeDatos.get(i).tablas.size();j++ ){
	    		  Tabla tabla = basesDeDatos.get(i).tablas.get(j);
	    		  
	    		
	    		  FileWriter f3 = new FileWriter("Servidor\\"+basesDeDatos.get(i).getNombre()+"\\"+tabla.nombre+".txt");
		    	  BufferedWriter bw3 = new BufferedWriter(f3);
		    	  
		    	  // Se guarda la informacion de sus registros
		    	  for(int k=0;k<tabla.registros.size();k++ ){
		    		  for(int l=0;l<tabla.registros.get(k).size();l++ ){
		    			  if(l!=(tabla.registros.get(k).size()-1)){
		    				  bw3.write(tabla.registros.get(k).get(l)+" , ");
		    			  }
		    				  
		    			  else{
		    				  bw3.write(tabla.registros.get(k).get(l));
		    			  }
		    				  
		    		  }
		    		  bw3.write("\n");
		    	  }
	    		  bw3.close();
	    		  
	    		  // Se escribe todos los datos importantes de cada tabla, como sus columnas y restricciones.
	    		  bw2.write("TABLA\n\n");
	    		  bw2.write("- "+tabla.nombre+"\n");
	    		  
	    		  bw2.write("COLUMNAS\n\n");
	    		  for(int k=0;k<tabla.columnas.size();k++ ){
	    			  bw2.write("- "+tabla.columnas.get(k).nombre+" : " + tabla.columnas.get(k).tipo+" : " +tabla.columnas.get(k).tamano+"\n");
	    		  }
	    		  
	    		  bw2.write("PRIMARIAS\n\n");
	    		  for(int k=0;k<tabla.pk.size();k++ ){
	    			  if(tabla.pk.get(k).llaves.size()==1)
	    				  bw2.write("- "+tabla.pk.get(k).nombre+" : " + tabla.pk.get(k).llaves.get(0) +"\n");
	    			  else{
	    				  String llaves = "";
	    				  for(int l=0;l<tabla.pk.get(k).llaves.size();l++ ){
	    					  llaves += tabla.pk.get(k).llaves.get(l) +" , ";
	    				  }
	    				  bw2.write("- "+tabla.pk.get(k).nombre+" : " + llaves +"\n");
		    			  
	    			  }
	    		  }
	    		  
	    		  bw2.write("FORANEAS\n\n");
	    		  for(int k=0;k<tabla.fk.size();k++ ){
	    			  bw2.write("- "+tabla.fk.get(k).nombre+" : " + tabla.fk.get(k).llaveActual + " : " + tabla.fk.get(k).TablaReferencia + " : "+tabla.fk.get(k).ColumnaReferencia + "\n");
	    		  }
	    		  
	    		  
	    		  bw2.write("CHECKS\n\n");
	    		  for(int k=0;k<tabla.checks.size();k++ ){
	    			  String vars = "";
	    			  for(int l=0;l<tabla.checks.get(k).variables.size();l++ ){
	    				  vars += tabla.checks.get(k).variables.get(l)+ ",";
	    			  }
	    			  bw2.write("- "+tabla.checks.get(k).nombre+" : "+ vars +" : "+ tabla.checks.get(k).expresion+" \n");
	    		  }
	    		  
	    		  bw2.write("___\n\n");
	    	  }
	    	  bw2.close();
	      }
	      
	      
	      bw.close();
	}
	
	
	
	
}
