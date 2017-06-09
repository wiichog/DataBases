/*
 * Juan Carlos Tapia 14133
 * Juan Luis Garcia 14189
 * Olga Cobaquil  13020
 * 
 * Proyecto 1. Base de datos.
 * 
 * Clase: Recorrido.java
 * 
 * Clase que se encarga de recorrer el arbol sintactico y ejecutar sus instrucciones.
 * */


import java.awt.Component;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

import java.io.FileInputStream;
import java.io.FileNotFoundException;



public class Recorrido {
	
	// Atributos
	public ArrayList<Database> basesDeDatos;  // Lista de bases de datos.
	public String salidaTexto;   // Cadena con la informacion de ejecuciones y errores.
	public Database enUso;  // Variable que representa que base de datos se seta usando.
	boolean Seleccionada = false;   // Variable que indica que hay una base de datos seleccionada para usarse.
	// Contadores.
	int contDelete;
	int contSelect;
	int contUpdate;
	int ContadorInsert;
	
	
	boolean Ins = false;  // Variable que indica que se realizo algun insert.
	JTable modelo;  // variable que representa la tabla para mostrar los resultados del SELECT.
	
	
	
	// Constructor, que tambien lee los archivos.
	public Recorrido(JTable modelo) throws IOException{
		basesDeDatos = new ArrayList<Database>();
		basesDeDatos = Archivar.Leer(basesDeDatos);
		salidaTexto = "";	
		enUso=null;
		contDelete = 0;
		contUpdate = 0;
		this.modelo = modelo;
		
	}
	
	// Metodo que recorre el arbol, y llama a metodos que realizan las operaciones.
	public boolean Realizar(Node arbol){
	
		if(arbol.getHijos().size()==0){
			
			
    		return true;
    	}
    	else
    	{
    	// Raiz del arbol
    		if(arbol.getData().equals("Programa")){
    			salidaTexto = "";	
    			contDelete = 0;
    			contSelect = 0;
    			contUpdate = 0;
    			ContadorInsert = 0;
    			
    			boolean Ins = false;
    			
    			DefaultTableModel model = new DefaultTableModel(
    				){
    				};
    			
    			modelo.setModel(model);
    			for(int i=0;i<arbol.getHijos().size();i++ ){
    				if(!Realizar(arbol.getHijos().get(i)))
    					return false;
    			
    			}
    			if(Ins){
    				salidaTexto += "INSERT "+ Integer.toString(ContadorInsert) +" \n";
    			}
    		
    			return true;
    		}
    		
    		
    		// Se encontro un query para trabajar las bases de datos.
    		if(arbol.getData().equals("Database")){
    			if(Realizar(arbol.getHijos().get(0)))
    				return true;
    			
    			return false;
    		}
    		
    		// Se creara una base de datos.
    		if(arbol.getData().equals("CreateDatabase")){
    			if(CrearBaseDeDatos(arbol.getHijos().get(2).getData()))
    				return true;
    			
    			return false;

    		}
    			   		
    		// Se alterara una base de datos.
    		if(arbol.getData().equals("AlterDatabase")){
    			AlterarBD(arbol.getHijos().get(2).getData(),arbol.getHijos().get(5).getData());
    		}
    		
    		// Se borrara una base de datos.
    		if(arbol.getData().equals("DropDatabase")){
    			try {
					BorrarBD(arbol.getHijos().get(2).getData());
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
    		}
    		
    		
    		// Se mostraran las bases de datos.
    		if(arbol.getData().equals("ShowDatabase")){
    			MostrarBD();
    		}
    		
    		// Se usara una base de datos.
    		if(arbol.getData().equals("UseDatabase")){
    			UsarBD(arbol.getHijos().get(2).getData());
    		}
    		
    		
    		// Se creara una tabla.
    		if(arbol.getData().equals("CreateTable")){
    			CrearTabla(arbol);
    		}
    		// Se borrara una tabla
    		if(arbol.getData().equals("DropTable")){
    			try {
					BorrarTabla(arbol);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
    		}
    		
    		// Se mostraran las tablas.
    		if(arbol.getData().equals("ShowTables")){
    			MostrarTablas(arbol);
    		}
    		// Se mostraran las columnas de una tabla
    		if(arbol.getData().equals("ShowColumns")){
    			MostrarColumnas(arbol);
    		}
    		
    		// Se alterara una tabla
    		if(arbol.getData().equals("AlterTable")){
    			// Se renombrara la tabla
    			if(arbol.getHijos().get(3).getData().equals("RENAME")){
    				RenombrarTabla(arbol);
    			}
    			
    			if(arbol.getHijos().get(3).getData().equals("Action")){
    				for(int i=3;i<arbol.getHijos().size();i=i+2 ){
    					// Se anadira una columna
    					if(arbol.getHijos().get(i).getHijos().get(0).getData().equals("AddColumn")){
    						AgregarColumna(arbol.getHijos().get(2).getData(),arbol.getHijos().get(i).getHijos().get(0));
    					}
    					
    					// Se anadira un constraint.
    					if(arbol.getHijos().get(i).getHijos().get(0).getData().equals("AddConstraint")){
    						AgregarConstraint(arbol.getHijos().get(2).getData(),arbol.getHijos().get(i).getHijos().get(0));
    					}
    					
    					// Se eliminara un constraint.
    					if(arbol.getHijos().get(i).getHijos().get(0).getData().equals("DropConstraint")){
    						BorrarConstraint(arbol.getHijos().get(2).getData(),arbol.getHijos().get(i).getHijos().get(0));
    					}
    					
    					
    					// Se borrara una columna.
    					if(arbol.getHijos().get(i).getHijos().get(0).getData().equals("DropColumn")){
    						BorrarColumna(arbol.getHijos().get(2).getData(),arbol.getHijos().get(i).getHijos().get(0));
    					}
    				}
    				
    			}
    			
    		}
    		
    		// Se hara un DELETE.
    		if(arbol.getData().equals("DeleteFrom")){
    			try {
					Delete(arbol);
					salidaTexto += "Delete ("+ Integer.toString(contDelete) + ") \n";
				} catch (ScriptException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
    		}
    		
    		// Se hara un SELECT.    		
    		if(arbol.getData().equals("SelectFrom")){
    			try {
					Select(arbol);
					salidaTexto+= "Select ("+Integer.toString(contSelect)+")";
				} catch (ScriptException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
    		}
    		
    		
    		// Se hara un INSERT.
    		if(arbol.getData().equals("InsertInto")){
    			
				try {
					Insert(arbol);
					
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
    		}
    		
    		
    		// Se hara un UPDATE.
    		if(arbol.getData().equals("UpdateSet")){
    			
				try {
					Update(arbol);
					salidaTexto += "Update "+ Integer.toString(contUpdate) + "\n";
				} catch (ScriptException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
    		}
    		
    		
	    	//for(int i=0;i<arbol.getHijos().size();i++ ){
	    //		Realizar(arbol.getHijos().get(i));
	    	//}
    	}
		return true;
	}
	
	
	
	
	private boolean Insert(Node arbol) throws IOException {
		Interfaz.verbose+="Iniciando insert   \n";
		
		
		Interfaz.verbose+="Buscando Base de datos en uso \n";
		// Se revisa que haya una BD en uso
		if(!Seleccionada){
			salidaTexto +="Error. No hay una Base de Datos en uso \n";
			return false;
		}
		// Se revisa que se llame una tabla que existe.
		
		Interfaz.verbose+="Buscando tabla para insertar \n";
		String nomTabla = arbol.getHijos().get(2).getData();
		File archivo = new File("Servidor\\"+enUso.nombre+"\\"+nomTabla+".txt");
		if(!archivo.exists()){
			salidaTexto +="Error. No existe una tabla con ese nombre \n";
			return false;
		}
		
		int pos = enUso.TablaExiste(nomTabla);
		Tabla tablaInsert = enUso.tablas.get(pos);
		ArrayList<Columna> ColumnsOfTable = tablaInsert.columnas;
		ArrayList<String> nuevoRegistro = new ArrayList<String> ();
		ArrayList<String> NameOfColumnsT = new ArrayList<String>();
		ArrayList<String> NameOfColumns = new ArrayList<String>();
		
		
		Interfaz.verbose+="Revisando valores ingresados";
		//Insert without columns
		if(arbol.getHijos().get(3).getData().equals("VALUES")){
			ArrayList<String> TypeForColumns = new ArrayList<String>();
			ArrayList<String> TypeForData = new ArrayList<String>();
			ArrayList<String> CleanData = new ArrayList<String>();
			nuevoRegistro.clear();
			String Data = "";
			
			int NumeroDatos = 0;;
			for(int i=5;i<arbol.getHijos().size();i=i+2){
				NumeroDatos++;
			}
			
			if (tablaInsert.columnas.size()<NumeroDatos){
				salidaTexto += "Error. El numero de datos es mayor al numero de columnas \n";
				return false;
			}
			
			
			for(int i=5;i<arbol.getHijos().size();i=i+2){
				Data = "";
				String TypeOfData = arbol.getHijos().get(i).getHijos().get(0).getData();
				int Size = arbol.getHijos().get(i).getHijos().get(0).getHijos().size();
				for(int y=0;y<Size;y++){
					Data = Data + arbol.getHijos().get(i).getHijos().get(0).getHijos().get(y).getData();
				}
				if(TypeOfData.toLowerCase().contains("char")){
					Data = "'" + Data + "'";
				}
				if(TypeOfData.toLowerCase().contains("date")){
					Data = "'" + Data + "'";
				}
				
				String Type = arbol.getHijos().get(i).getHijos().get(0).getData();
				if(Type.equals("Char_literal")){
					Type = "CHAR"+"("+ (Data.length()-2) +")";
				}
				else if(Type.equals("Date_literal")){
					String[] parts = Data.split("-");
					if(parts.length>3){
						salidaTexto += "Fecha Invalidad, existe mas de 3 - \n";
						return false;
					}
					else if(Integer.parseInt(parts[0])<1){
						salidaTexto += "El año no puede ser menor a 1 \n";
						return false;
					}
					else if(Integer.parseInt(parts[1])<1 || Integer.parseInt(parts[1])>12){
						salidaTexto += "El mes tiene que ser mayor a 1 y menor a 12 \n";
						return false;
					}
					else if(Integer.parseInt(parts[2])<1 || Integer.parseInt(parts[2])>31){
						salidaTexto += "El dia tiene que ser mayor a 1 y menor a 31 \n";
						return false;
					}
				}
				CleanData.add(Data);
				TypeForData.add(Type);
			}
			for(int i=0;i<TypeForData.size();i++){
				if(ColumnsOfTable.get(i).tipo.equals("CHAR")){
					TypeForColumns.add(ColumnsOfTable.get(i).tipo + "(" + ColumnsOfTable.get(i).tamano + ")");
				}
				else{
					TypeForColumns.add(ColumnsOfTable.get(i).tipo);
				}
				NameOfColumnsT.add(ColumnsOfTable.get(i).nombre);
			}
			
			Interfaz.verbose+="	Revisando tipos de los datos \n";
			for(int i=0; i<TypeForData.size();i++){
				if(TypeForData.get(i).toLowerCase().contains(TypeForColumns.get(i).toLowerCase())){
					nuevoRegistro.add(CleanData.get(i));
					NameOfColumns.add(NameOfColumnsT.get(i));
				}
				else{
					if(TypeForData.get(i).toLowerCase().contains("int") && TypeForColumns.get(i).toLowerCase().contains("float")){
						String ToConvert = String.valueOf((float)Integer.parseInt(CleanData.get(i)));
						nuevoRegistro.add(ToConvert);
						NameOfColumns.add(NameOfColumnsT.get(i));
					}
					else if(TypeForColumns.get(i).toLowerCase().contains("char")){
						String ToConvert = CleanData.get(i);
						if(!ToConvert.contains("\'")){
							ToConvert = "\'"+ToConvert+"\'";
						}

						
						int char1 = ToConvert.length()-2;
						int char2 = Integer.parseInt(TypeForColumns.get(i).substring(TypeForColumns.get(i).indexOf("(")+1,TypeForColumns.get(i).indexOf(")")));
						if(char1>char2){
							salidaTexto += "El numero de caracteres es mayor de lo que permite la columna " +NameOfColumnsT.get(i)+ ". No debe pasar de "+ char2+" \n";
							return false;
						}
						else{
							nuevoRegistro.add(ToConvert);
							NameOfColumns.add(NameOfColumnsT.get(i));
							
						}
						
						
						
					}
						
						
					else if(TypeForData.get(i).toLowerCase().contains("char") && TypeForColumns.get(i).toLowerCase().contains("float")){
						String ToConvert = CleanData.get(i);
						ToConvert = ToConvert.substring(1, ToConvert.length()-1);
						try{
							ToConvert = String.valueOf((float)Integer.parseInt(ToConvert));
							nuevoRegistro.add(ToConvert);
							NameOfColumns.add(NameOfColumnsT.get(i));
						}
						catch(Exception e){
							try{
								ToConvert = String.valueOf(Float.parseFloat(ToConvert));
								nuevoRegistro.add(ToConvert);
								NameOfColumns.add(NameOfColumnsT.get(i));
							}
							catch(Exception q){
								salidaTexto += "El tipo del dato " + TypeForData.get(i) + " y el tipo de la columna " + TypeForColumns.get(i) + "  no se pueden comparar \n";
								return false;
							}
						}
						
					}
					else if(TypeForData.get(i).toLowerCase().contains("char") && TypeForColumns.get(i).toLowerCase().contains("int")){
						String ToConvert = CleanData.get(i);
						ToConvert = ToConvert.substring(1, ToConvert.length()-1);
						try{
							ToConvert = String.valueOf((int)Float.parseFloat(ToConvert));
							nuevoRegistro.add(ToConvert);
							NameOfColumns.add(NameOfColumnsT.get(i));
						}
						catch(Exception e){
							try{
								ToConvert = String.valueOf(Integer.parseInt(ToConvert));
								nuevoRegistro.add(ToConvert);
								NameOfColumns.add(NameOfColumnsT.get(i));
							}
							catch(Exception q){
								salidaTexto += "El tipo del dato " + TypeForData.get(i) + " y el tipo de la columna " + TypeForColumns.get(i) + "  no se pueden comparar \n";
								return false;
							}
						}
						
					}
					else if(TypeForData.get(i).toLowerCase().contains("float") && TypeForColumns.get(i).toLowerCase().contains("int")){
						String ToConvert = String.valueOf((int)Float.parseFloat(CleanData.get(i)));
						nuevoRegistro.add(ToConvert);
						NameOfColumns.add(NameOfColumnsT.get(i));
					}
					else{
						salidaTexto += "El tipo de datos " + TypeForData.get(i) + " y el tipo de dato " + TypeForColumns.get(i) + "  no se pueden comparar \n";
						return false;
					}
					
				}
				
			}
			
			
			
			
			
		}
		else{//insert with columns
			ArrayList<String> TypeForColumns = new ArrayList<String>();
			ArrayList<String> TypeForData = new ArrayList<String>();
			ArrayList<String> CleanData = new ArrayList<String>();
			nuevoRegistro.clear();
			NameOfColumns.clear();
			String Data = "";
			
			
			boolean FlagColumnsData = true;
			
			int NumeroDatos = 0;
			int NumeroColumnas = 0;
			
			for(int i=4;i<arbol.getHijos().size();i=i+2){
				if((arbol.getHijos().get(i).getData()).equals("VALUES")){
					FlagColumnsData=false;
					NumeroDatos--;
				}
				if(FlagColumnsData){
					NumeroColumnas++;
				}else{
					NumeroDatos++;
				}
			}

			if (NumeroColumnas!=NumeroDatos){
				salidaTexto += "Error. El numero de datos ingresados no coincide con el de columnas especificadas \n";
				return false;
			}
			
			
			FlagColumnsData = true;
			
			for(int i=4;i<arbol.getHijos().size();i=i+2){
				if((arbol.getHijos().get(i).getData()).equals("VALUES")){
					FlagColumnsData=false;
				}
				if(FlagColumnsData){//here we are gonna insert the type for the columns because VALUES is true
					String NameOfColumn = arbol.getHijos().get(i).getData();//we get the name of the column
					if(tablaInsert.ColumnaExiste(NameOfColumn)!=-1){//we check if exists
						for(int k=0;k<ColumnsOfTable.size();k++){
							if(NameOfColumn.equals(ColumnsOfTable.get(k).getNombre())){
								if(ColumnsOfTable.get(k).getTipo().equals("CHAR")){
									TypeForColumns.add(ColumnsOfTable.get(k).getTipo().toLowerCase() + "(" + ColumnsOfTable.get(k).tamano + ")");	
								}
								else{
									TypeForColumns.add(ColumnsOfTable.get(k).getTipo().toLowerCase());
								}
								NameOfColumnsT.add(NameOfColumn);
								
							}
						}
					}
					else{
						salidaTexto += "La columa "+ NameOfColumn + " no existe \n";
						return false;
					}
				}
				else{//here we are gona insert the type for the data
					
					//System.out.println(NameOfColumns.get(0)+"____"+NameOfColumns.get(1)+"___"+NameOfColumns.get(2));
					if(!(arbol.getHijos().get(i).getData().equals("VALUES"))){
						int size = arbol.getHijos().get(i).getHijos().get(0).getHijos().size();
						for(int y=0;y<size;y++){
							Data = Data + arbol.getHijos().get(i).getHijos().get(0).getHijos().get(y).getData();
						}
						if(arbol.getHijos().get(i).getHijos().get(0).getData().toLowerCase().contains("char")){
							Data = "'" + Data + "'";
						}
						CleanData.add(Data);
						String Type = arbol.getHijos().get(i).getHijos().get(0).getData();
						//System.out.println("TIPO " + Type);
						if(Type.equals("Char_literal")){
							Type = "CHAR"+"("+ (Data.length()-2) +")";
						}
						else if(Type.equals("Date_literal")){
							String[] parts = Data.split("-");
							if(parts.length>3){
								salidaTexto += "Fecha Invalidad, existe mas de 3 - \n";
								return false;
							}
							else if(Integer.parseInt(parts[0])<1){
								salidaTexto += "El año no puede ser menor a 1 \n";
								return false;
							}
							else if(Integer.parseInt(parts[1])<1 || Integer.parseInt(parts[1])>12){
								salidaTexto += "El mes tiene que ser mayor a 1 y menor a 12 \n";
								return false;
							}
							else if(Integer.parseInt(parts[2])<1 || Integer.parseInt(parts[2])>31){
								salidaTexto += "El dia tiene que ser mayor a 1 y menor a 31 \n";
								return false;
							}
						}
						TypeForData.add(Type);
						Data = "";
					}
				}
			}
			
			Interfaz.verbose+="	Revisando tipos de los datos \n";
			for(int i=0; i<TypeForData.size();i++){
				if(TypeForData.get(i).toLowerCase().contains(TypeForColumns.get(i).toLowerCase()) || TypeForData.get(i).toLowerCase().equals("null")){
					nuevoRegistro.add(CleanData.get(i));
					NameOfColumns.add(NameOfColumnsT.get(i));
				}
				
				
				else if(TypeForColumns.get(i).toLowerCase().contains("char")){
					String ToConvert = CleanData.get(i);
					if(!ToConvert.contains("\'")){
						ToConvert = "\'"+ToConvert+"\'";
					}
	
					int char1 = ToConvert.length()-2;
					int char2 = Integer.parseInt(TypeForColumns.get(i).substring(TypeForColumns.get(i).indexOf("(")+1,TypeForColumns.get(i).indexOf(")")));
					if(char1>char2){
						salidaTexto += "El numero de caracteres es mayor de lo que permite la columna " +NameOfColumnsT.get(i)+ ". No debe pasar de "+ char2+" \n";
						return false;
					}
					else{
						nuevoRegistro.add(ToConvert);
						
						NameOfColumns.add(NameOfColumnsT.get(i));
						
					}
					
					
					
				}
					
					
				else if(TypeForData.get(i).toLowerCase().contains("char") && TypeForColumns.get(i).toLowerCase().contains("float")){
					String ToConvert = CleanData.get(i);
					ToConvert = ToConvert.substring(1, ToConvert.length()-1);
					try{
						ToConvert = String.valueOf((float)Integer.parseInt(ToConvert));
						nuevoRegistro.add(ToConvert);
						NameOfColumns.add(NameOfColumnsT.get(i));
					}
					catch(Exception e){
						try{
							ToConvert = String.valueOf(Float.parseFloat(ToConvert));
							nuevoRegistro.add(ToConvert);
							NameOfColumns.add(NameOfColumnsT.get(i));
						}
						catch(Exception q){
							salidaTexto += "El tipo del dato " + TypeForData.get(i) + " y el tipo de la columna " + TypeForColumns.get(i) + "  no se pueden comparar \n";
							return false;
						}
					}
					
				}
				else if(TypeForData.get(i).toLowerCase().contains("char") && TypeForColumns.get(i).toLowerCase().contains("int")){
					String ToConvert = CleanData.get(i);
					ToConvert = ToConvert.substring(1, ToConvert.length()-1);
					try{
						ToConvert = String.valueOf((int)Float.parseFloat(ToConvert));
						nuevoRegistro.add(ToConvert);
						NameOfColumns.add(NameOfColumnsT.get(i));
					}
					catch(Exception e){
						try{
							ToConvert = String.valueOf(Integer.parseInt(ToConvert));
							nuevoRegistro.add(ToConvert);
							NameOfColumns.add(NameOfColumnsT.get(i));
						}
						catch(Exception q){
							salidaTexto += "El tipo del dato " + TypeForData.get(i) + " y el tipo de la columna " + TypeForColumns.get(i) + "  no se pueden comparar \n";
							return false;
						}
					}
					
				}
				
				else if(TypeForData.get(i).toLowerCase().contains("int") && TypeForColumns.get(i).toLowerCase().contains("float")){
					String ToConvert = String.valueOf((float)Integer.parseInt(CleanData.get(i)));
					nuevoRegistro.add(ToConvert);
					NameOfColumns.add(NameOfColumnsT.get(i));
				}
				else if(TypeForData.get(i).toLowerCase().contains("float") && TypeForColumns.get(i).toLowerCase().contains("int")){
					String ToConvert = String.valueOf((int)Float.parseFloat(CleanData.get(i)));
					nuevoRegistro.add(ToConvert);
					NameOfColumns.add(NameOfColumnsT.get(i));
					
				}
				else{
					salidaTexto += "El tipo del dato " + TypeForData.get(i) + " y el tipo de la columna " + TypeForColumns.get(i) + "  no se pueden comparar \n";
					return false;
				}
				
				//System.out.println(nuevoRegistro.get(i));
				//System.out.println(NameOfColumns.get(0));
				
			
			}
			

		}
		Interfaz.verbose+="	Revisando Primary Keys \n";
		//here we are gonna validate that the primary key is unique
		ArrayList<ArrayList<String>> Registros = (ArrayList<ArrayList<String>>) tablaInsert.registros.clone();
		ArrayList<PrimaryKey> PK = tablaInsert.getPk();
		ArrayList<ForeignKey> FK = tablaInsert.getFk();
		ArrayList<Check> CK = tablaInsert.getChecks();
		ArrayList<String> NombreColumnas = tablaInsert.NombresColumnas();
		
		for(int i=0; i<nuevoRegistro.size();i++){
			String ABuscarKey = nuevoRegistro.get(i);
			//System.out.println(nuevoRegistro.size());
			String NameOfColumn = NameOfColumns.get(i);
			//System.out.println(NameOfColumn);
			int PositionColumn = -1;
			ArrayList<String> Keys = new ArrayList<String>();
			for(int y=0;y<PK.size();y++){
				if(PK.get(y).getLlave().toLowerCase().equals(NameOfColumn)){//here we get foreign key
					PositionColumn = NombreColumnas.indexOf(NameOfColumn);
				}
			}
			if(PositionColumn!=-1){
				for(int y=0;y<Registros.size();y++){
				
					ArrayList<String> LineaEnArchivo= Registros.get(y);
					String Dato = LineaEnArchivo.get(PositionColumn);
					Dato = Dato.replace(" ", "");
					Keys.add(Dato);
				}
			}
			
			if(Keys.contains(ABuscarKey)){
				salidaTexto += "La llave primaria ya fue tomada \n";
				return false;
			}
			
			Interfaz.verbose+="	Revisando Foreign Keys \n";
			//here we are gonna validate that the foreign key does exist on the other table
			Keys.clear();
			if(!FK.isEmpty()){
				
				for(int y=0;y<FK.size();y++){
					PositionColumn = -1;
					if(FK.get(y).llaveActual.toLowerCase().equals(NameOfColumn)){
						PositionColumn = NombreColumnas.indexOf(NameOfColumn);
					}
					
				
					
					if(PositionColumn!=-1){
						String NameOfOtherTable = FK.get(y).getTablaReferencia();
						String NameOfOtherColum = FK.get(y).ColumnaReferencia;
						int Position = enUso.TablaExiste(NameOfOtherTable);
						Tabla TheOtherTable = enUso.tablas.get(Position);
						ArrayList<ArrayList<String>> RegistrosOtraTabla = TheOtherTable.registros;
						for(int x=0;x<RegistrosOtraTabla.size();x++){
						
							ArrayList<String> LineaEnArchivo= RegistrosOtraTabla.get(x);
							String Dato = LineaEnArchivo.get(TheOtherTable.ColumnaExiste(NameOfOtherColum));
							Dato = Dato.replace(" ", "");
							
							Keys.add(Dato);
						}
					
						if(!(Keys.contains(ABuscarKey))){
							salidaTexto += "La llave foranea no existe en la otra tabla \n";
							return false;
						}
					}
				}
				//here are gonna validate that the column of the foreign key does exist
				for(int y=0;y<FK.size();y++){
					String NameOfOtherTable = FK.get(y).getTablaReferencia();
					String NameOfOtherColum = FK.get(y).ColumnaReferencia;
					int Position = enUso.TablaExiste(NameOfOtherTable);
					Tabla TheOtherTable = enUso.tablas.get(Position);
					if(TheOtherTable.ColumnaExiste(NameOfOtherColum)==-1){
						salidaTexto += "La columna no existe \n";
						return false;
					}
				}
			}
			Interfaz.verbose+="	Revisando Checks \n";
			//CHECKS
			for(int y=0;y<CK.size();y++){
				ArrayList<String> Data = new ArrayList<String>();
				for(int j=0;j<CK.get(y).getVariables().size();j++){
					String Column = CK.get(y).getVariables().get(j);
					if(NameOfColumns.contains(Column)){
						int Position = NameOfColumns.indexOf(Column);
						
						
						Data.add(nuevoRegistro.get(Position));
					}
				}
				boolean Valor = false;
				if(Data.size()>0){
					
					Valor = CK.get(y).evaluar(Data);
				
					if(!Valor){
						salidaTexto += "Uno de los registro no ha pasado el check \n";
						return false;
					}}
			}
		}
		
		
		Interfaz.verbose+="Insertando fila en la tabla \n";
		if(ColumnsOfTable.size()==nuevoRegistro.size()){//here we are gonna have the insert with all the columns
			ArrayList<String> nuevoRegistroT = (ArrayList<String>) nuevoRegistro.clone();
			for(int i=0;i<nuevoRegistro.size();i++){
				
				int Position = tablaInsert.ColumnaExiste(NameOfColumns.get(i));
				nuevoRegistro.set(Position, nuevoRegistroT.get(i));
			}
			
			tablaInsert.registros.add(nuevoRegistro);
		}
		/*else if (NameOfColumns.size()!=nuevoRegistro.size()){
			salidaTexto += "El numero de datos es mayor al numero de columnas \n";
			return false;
		}*/
		else{
			ArrayList<String> nuevoRegistroNulls = new ArrayList<String>();
			for(int i=0;i<ColumnsOfTable.size();i++){
				nuevoRegistroNulls.add("NULL");
			}
			for(int i=0;i<NameOfColumns.size();i++){
				int Position = tablaInsert.ColumnaExiste(NameOfColumns.get(i));
				nuevoRegistroNulls.set(Position, nuevoRegistro.get(i));
			}
			tablaInsert.registros.add(nuevoRegistroNulls);
		}
		ContadorInsert = ContadorInsert +1;
		return true;
		
	}
	
	
	
	// Metodo que realiza un UPDATE.
	private boolean Update(Node arbol) throws ScriptException {
		
		Interfaz.verbose+="Iniciando Update \n";
		// Se revisa que haya una BD en uso
		Interfaz.verbose+="Buscando Base de datos en uso \n";
		if(!Seleccionada){
			salidaTexto += "Error. No hay una base de datos en uso \n";
			return false;
		}
		
		
		// Se revisa que se llame una tabla que existe.
		
		String nomTabla = arbol.getHijos().get(1).getData();
		Interfaz.verbose+="Llamando a la tabla "+ nomTabla+"\n";
		
		File archivo = new File("Servidor\\"+enUso.nombre+"\\"+nomTabla+".txt");
		
		if(!archivo.exists()){
			salidaTexto += "Error. No existe la tabla "+nomTabla +"\n";
			return false;
		}
		int pos = enUso.TablaExiste(nomTabla);
		
		/*TablaInsert es la tabla donde se agregan los datos*/
		Tabla tablaUpdate = enUso.tablas.get(pos);
		
	
		// Listas para columnas que se modificaran y los nuevos valores.
		ArrayList<Integer> columnas = new ArrayList<Integer> ();
		ArrayList<String> valores = new ArrayList<String> ();
		
		// Revisar Columas
		Interfaz.verbose+="	Revisando columnas a actualizar"; 
		Interfaz.verbose+="	Revisando que los tipos coincidan \n";
		for(int i=0;i<arbol.getHijos().get(3).getHijos().size();i=i+2 ){
			Node ColumnaActual = arbol.getHijos().get(3).getHijos().get(i);
			
			String nombreColumna = ColumnaActual.getHijos().get(0).getData();
			
			pos =tablaUpdate.ColumnaExiste(nombreColumna);
			if(pos==-1){
			
				salidaTexto+="Error. No existe la columna "+nombreColumna+" en la tabla " + nomTabla;
				return false;
			}
			
			String tipoColumna = tablaUpdate.columnas.get(pos).tipo.toUpperCase();
			String tipoValor =  ColumnaActual.getHijos().get(2).getHijos().get(0).getData();
			String valor = "";
			
			// Se revisan los tipos de las columnas.
			switch (tipoValor){
				case "Int_literal":
					tipoValor = "INT";
					valor = ColumnaActual.getHijos().get(2).getHijos().get(0).getHijos().get(0).getData();
					
					break;
				case "Float_literal":
					tipoValor = "FLOAT";
					valor = ColumnaActual.getHijos().get(2).getHijos().get(0).getHijos().get(0).getData() +" . "+ ColumnaActual.getHijos().get(2).getHijos().get(0).getHijos().get(2).getData();
					
					break;
				case "Char_literal":
					tipoValor = "CHAR";
					valor = "\'"+ColumnaActual.getHijos().get(2).getHijos().get(0).getHijos().get(1).getData()+"\'";
					
					break;
				case "Date_literal":
					tipoValor = "DATE";
					valor = "\'"+ColumnaActual.getHijos().get(2).getHijos().get(0).getHijos().get(1).getData()+"-"+ColumnaActual.getHijos().get(2).getHijos().get(0).getHijos().get(3).getData()+"-"+ColumnaActual.getHijos().get(2).getHijos().get(0).getHijos().get(5).getData()+"\'";
					break;
				case "NULL":
					tipoValor = "NULL";
					valor = "NULL";
					break;
				
			}
			
			
			
			if(!tipoValor.equals("NULL")){
				
				// Se hacen los casteos de otros elementos a CHAR.
				if(tipoColumna.equals("CHAR")){
					if(!valor.contains("\'"))
						valor = "\'"+valor+"\'";
					
				}else{
					
					// Se hacen los casteos entre FLOAT Y INT.
					if(tipoColumna.equals("INT")){
						if(!(tipoValor.equals("INT")||tipoValor.equals("FLOAT"))){
							salidaTexto+= "Error. Los tipos no coinciden en la columna "+nombreColumna+ " en el Update";
							return false;
						}
					}else if(tipoColumna.equals("FLOAT")){
						if(!(tipoValor.equals("INT")||tipoValor.equals("FLOAT"))){
							salidaTexto+= "Error. Los tipos no coinciden en la columna "+nombreColumna+ " en el Update";
							return false;
						}
					}else{
						if(!tipoColumna.equals(tipoValor)){
							salidaTexto+= "Error. Los tipos no coinciden en la columna "+nombreColumna+ " en el Update";
							return false;
						}
					}
				
				}
				
				// Se revisa el tamano de la cadena en un CHAR.
				if(tipoColumna.equals("CHAR")){
					if((valor.length()-2)> tablaUpdate.columnas.get(pos).tamano){
						salidaTexto+= "Error. La columna " +nombreColumna+" no puede recibir cadenas de mas de " + Integer.toString(tablaUpdate.columnas.get(pos).tamano) +" caracteres";
						return false;
					}
				}
			}	
			
			// No hay errores con los tipos
			
			columnas.add(pos);
			valores.add(valor);
			
		}
		
		
		Interfaz.verbose+="	Ingresando los nuevos datos \n";
		// UPDATE todo de la tabla
		if(arbol.getHijos().size() ==4){
			//Se van actualizando uno por uno los registros de la tabla
			for(int i=0;i<tablaUpdate.registros.size();i++ ){
				
				// Resultado indica si el Update fue un exito. Puede no serlo por problemas con las restricciones.
				String resultado = tablaUpdate.acualizarRegistro(i, columnas, valores, enUso.tablas);
				
				
				if(resultado.equals("Ok")){
					contUpdate++;
					
				}else{
					if(!salidaTexto.contains(resultado))
						salidaTexto+=resultado+"\n";
					 
				}
			}
		}
		// UPDATE con condicion.
		else{
			
			// Se obtiene la expresion booleanana del WHERE
			Node exp = arbol.getHijos().get(5);
			String expresion = Expresiones.traducir(Expresiones.hacerExprecion(exp.getHijos().get(0)));
			ArrayList<String> cols = new ArrayList<String> ();
			Interfaz.verbose+="	Revisando condicion del UPDATE \n";
			
			// Se revisa que la expresion sea valida.
			if(Expresiones.revisarTiposWhere(tablaUpdate, cols, exp.getHijos().get(0)).equals("Error")){
				salidaTexto+= "Error. Problemas en el Where. Los tipos no coinciden, ingreso columnas que no existen, o hay ambiguedad en las columnas \n";
				return false;
			}
			
			Interfaz.verbose+="	Evaluando si los datos cumplen la condicion \n";
			
			
			// Se revisan todos los registros.
			for(int i=0;i<tablaUpdate.registros.size();i++ ){
				// condicion
				ArrayList<String> registroLinea = tablaUpdate.registros.get(i);
				ScriptEngine engine = new ScriptEngineManager().getEngineByExtension("js");
				for(int j=0;j<cols.size();j++ ){
					String valor = registroLinea.get(tablaUpdate.ColumnaExiste(cols.get(j)));
					
					if(valor.charAt(0)=='\''){
						valor = valor.substring(1, valor.length()-1);
					}
					//engine.put(cols.get(j).toString(), .toString());
					engine.put(cols.get(j), valor );
					
					//System.out.println(engine.get(cols.get(j)));
				}
						
						
						
				// Solo se actualizan los que cumplan la condicion. 
	
				if((boolean) engine.eval(expresion)){
					// Se revisa que no haya problemas con las restricciones.
					String resultado = tablaUpdate.acualizarRegistro(i, columnas, valores, enUso.tablas);
					if(resultado.equals("Ok")){
						contUpdate++;
					}else{
						salidaTexto+=resultado+"\n";
					}
				}
				
						
			}	
		}
		
		
		
		
		
		
		Interfaz.verbose+="Update realizado \n";
		return true;
		
		
		
		
	}


	// Metodo que realiza un SELECT
	private boolean Select(Node arbol) throws ScriptException{
		Interfaz.verbose+="	Iniciando Select \n";
		
		Interfaz.verbose+="	Buscando Base de datos en uso \n";
		
		// Se revisa que haya una Base de datos en uso.
		
		if(!Seleccionada){
			salidaTexto += "Error. No hay una base de datos en uso \n";
			return false;
		}
		
		
		Interfaz.verbose+="	Llamando tablas seleccionadas \n";
		
		// FROM
		// Se revisa que las tablas llamadas si existan.
		Node tabs = arbol.getHijos().get(3);
		//Los nombres se agregan a tablasSelect.
		ArrayList<String> tablasSelect = new ArrayList<String> ();
		// Y la referencia a las Tablas se guarda en tablas.
		ArrayList<Tabla> tablas = new  ArrayList<Tabla>();
		for(int i=0;i<tabs.getHijos().size();i=i+2 ){
			if(enUso.TablaExiste(tabs.getHijos().get(i).getData())==-1){
				salidaTexto += "Error. La tabla "+tabs.getHijos().get(i).getData()+" no existe  \n";
				Interfaz.verbose+="	No existe la tabla "+tabs.getHijos().get(i).getData()+"\n";
				return false;
			}else{
				
				tablasSelect.add(tabs.getHijos().get(i).getData());
				tablas.add(enUso.tablas.get(enUso.TablaExiste( tabs.getHijos().get(i).getData() )));
			}
			
		}
		
		// registros guarda los datos llamados.
		ArrayList<ArrayList<String>> registros = new ArrayList<ArrayList<String>>();
		
		// Si solo hay una tabla seleccionada, se guardan los registros de esa tabla.
		if(tablasSelect.size()==1){
			registros.add(enUso.tablas.get(enUso.TablaExiste(tablasSelect.get(0))).NombresColumnas());
			registros.addAll(enUso.tablas.get(enUso.TablaExiste(tablasSelect.get(0))).registros);
			
		}
		// Si hay mas de una tabla. Se hace un producto Cruz.
		else{
			ArrayList<String> colsR = new ArrayList<String> (); 
			for(int i=0;i<tablasSelect.size();i++ ){
				colsR.addAll(enUso.tablas.get(enUso.TablaExiste(tablasSelect.get(i))).NombresColumnas2());
			}
			registros.add(colsR);
			registros.addAll(  productoCruz(tablasSelect) );
		}
		
		
		
		
		
		
		
		
		// WHERE
		// En registrosWhere se guardaran los registros que cumplan con la condicion del WHERE del SELECT.
		ArrayList<ArrayList<String>> registrosWhere = new ArrayList<ArrayList<String>>();
		
		
		// No hay Where
		if(arbol.getHijos().size()==4){
	
			registrosWhere = (ArrayList<ArrayList<String>>) registros.clone();
		}else{
			if(!arbol.getHijos().get(4).getData().toUpperCase().equals("WHERE")){
				registrosWhere = (ArrayList<ArrayList<String>>) registros.clone();
			}
			// Si hay Where
			else{
				registrosWhere.add(registros.get(0));
				
				// Se revisa la expresion booleana, y se obtienen las columnas que participan.
				Node exp = arbol.getHijos().get(5);
				String expresion = Expresiones.traducir(Expresiones.hacerExprecion(exp.getHijos().get(0)));
				
				ArrayList<String> colsWhere = new ArrayList<String> ();
				Interfaz.verbose+="	Revisando la expresion del WHERE del Select  \n";
				if(Expresiones.revisarTiposWhere(tablas, colsWhere, exp.getHijos().get(0)).equals("Error")){
					
					salidaTexto+= "Error. Problemas en el Where. Los tipos no coinciden, ingreso columnas que no existen, o hay ambiguedad en las columnas \n";
					return false;
				}
				
				Interfaz.verbose+="	Evaluando todos los registros en la condicion  \n";
				// Se evaluan todos los registros, y se seleccionan los que cumplan.
				for(int i=1;i<registros.size();i++ ){
					// condicion
					ArrayList<String> registroLinea = registros.get(i);
					ScriptEngine engine = new ScriptEngineManager().getEngineByExtension("js");
					for(int j=0;j<colsWhere.size();j++ ){
						int col = registros.get(0).indexOf(colsWhere.get(j));
						if(col==-1){
							for(int k=0;k<tablasSelect.size();k++ ){
								col = registros.get(0).indexOf(tablasSelect.get(k)+" . "+colsWhere.get(j));
								if(col!=-1){
									break;
								}
							}
						}
						
						String valor = registroLinea.get(col);
						
						if(valor.charAt(0)=='\''){
							valor = valor.substring(1, valor.length()-1);
						}
						if(valor.equals("NULL")){
							valor = null;
						}
						//engine.put(cols.get(j).toString(), .toString());
						engine.put(colsWhere.get(j).replaceAll(" . ", "___"), valor );
						
						
					}
					

					if((boolean) engine.eval(expresion)){
						registrosWhere.add(registroLinea);
						
						contSelect++;
					}
					
					
				}
				
				
			}
		}
		
		Interfaz.verbose+="	Registros evaluados  \n";
		
		// SELECT
		Interfaz.verbose+="	Revisando las columnas seleccionadas  \n";
		
		
		
		Node cols = arbol.getHijos().get(1);
		// Ahora se obtienen las columnas que se desean mostrar, y se guardaran en colsSelect.
		ArrayList<String> colsSelect = new ArrayList<String> ();
		// Se seleccionan todas.
		if(cols.getHijos().get(0).getData().equals("*")){
			if(tablasSelect.size()==1){
				colsSelect.addAll(enUso.tablas.get(enUso.TablaExiste(tablasSelect.get(0))).NombresColumnas() );
			}else{
				
				for(int i=0;i<tablasSelect.size();i++ ){
					colsSelect.addAll(enUso.tablas.get(enUso.TablaExiste(tablasSelect.get(i))).NombresColumnas2() );
				}
			}
		}
		// Se seleccionan columnas especificas.
		else{
			for(int i=0;i<cols.getHijos().size();i=i+2 ){
				Node Location = cols.getHijos().get(i);
				if(Location.getHijos().size()== 1 ){
					// Se revisa que las columnas existan, o que no haya ambiguedad.
					String nombre = Location.getHijos().get(0).getData(); 
					int correcto = ColumnaCorrectas(nombre, tablasSelect);
					if(correcto == 0 ){
						
						salidaTexto +="Error. La columna "+nombre +" no existe \n";
						Interfaz.verbose+="	La columna "+nombre+" no existe   \n";
						return false;
					}
					if(correcto > 1 ){
						salidaTexto +="Error. Ambiguedad La columna "+nombre +" existe en mas de una tabla seleccionada \n";
						Interfaz.verbose+="	La columna "+nombre+" es ambigua   \n";
						return false;
					}
					
					// Si todo esta en orden, se guarda la columna.
					colsSelect.add(nombre);
				}
				else if(Location.getHijos().size()== 3 ){
					String nombre = Location.getHijos().get(0).getData()+" . "+Location.getHijos().get(2).getData(); 
					int correcto = ColumnaCorrectas(nombre, tablasSelect);
					if(correcto == 0 ){
						salidaTexto +="Error. La columna "+nombre +" no existe \n";
						Interfaz.verbose+="	La columna "+nombre+" no existe   \n";
						return false;
					}
					if(correcto > 1 ){
						salidaTexto +="Error. Ambiguedad La columna "+nombre +" existe en mas de una tabla seleccionada \n";
						Interfaz.verbose+="	La columna "+nombre+" es antigua  \n";
						return false;
					}
					
					colsSelect.add(nombre);
				}
					
			}
		}
	
		
		;
		Interfaz.verbose+="	Se obtuvieron los registros de las columnas seleccionadas \n";
		
		// Registros mostrar guarda los registros que se mostraran al usuario, pero solo con las columnas seleccionadas.
		ArrayList<ArrayList<String>> registrosMostrar = new ArrayList<ArrayList<String>>();
		
		registrosMostrar.add(colsSelect);

		// Se recorren todos los registros que pasaron el WHERE y se toman solo los datos de las columnas seleccionadas.
		for(int i=1;i<registrosWhere.size();i++ ){
			ArrayList<String> linea = new ArrayList<String>();
			for(int j=0;j<colsSelect.size();j++ ){
				int col = registrosWhere.get(0).indexOf(colsSelect.get(j));
				if(col==-1){
					for(int k=0;k<tablasSelect.size();k++ ){
						col = registrosWhere.get(0).indexOf(tablasSelect.get(k)+" . "+colsSelect.get(j));
						if(col!=-1){
							break;
						}
					}
				}
				
				linea.add(registrosWhere.get(i).get(col));
				
			}
			registrosMostrar.add(linea);
		}
		
		
		
		
		// ORDER BY
		
		// registrosOrder guardara los registros en el orden en el que se desean mostrar.
		ArrayList<ArrayList<String>> registrosOrder = new ArrayList<ArrayList<String>>();
			
		// No hay Order
		if(arbol.getHijos().size()==4 || arbol.getHijos().size()==6){
			registrosOrder = registrosMostrar;
		}
		else{
			// Order sin WHERE
			
			Interfaz.verbose+="	Iniciando ordenamiento de datos por el ORDER \n";
			Node orden ;
			if(arbol.getHijos().size()==5){
				
				
				orden = arbol.getHijos().get(4);
				
				
				
				
				
			}
			// Order con WHERE
			else{
				orden = arbol.getHijos().get(6);
			}
			
			Interfaz.verbose+="	Revisando columnas del ORDER \n";
			registrosOrder = (ArrayList<ArrayList<String>>) registrosMostrar.clone();
			registrosOrder.remove(0);
			// En colsOrder se guardan las posiciones de las columnas para el ORDER, y en DESC, se guarda si son por orden DESC o ASC.
			ArrayList<Integer> colsOrder = new ArrayList<Integer>();
			ArrayList<Boolean> DESC = new ArrayList<Boolean>();
			for(int i=2; i<orden.getHijos().size();i=i+2 ){
				
				if(orden.getHijos().get(i).getHijos().size()==1){
					DESC.add(false);
				}else{
					if(orden.getHijos().get(i).getHijos().get(1).getData().toUpperCase().equals("ASC")){
						DESC.add(false);
					}else{
						DESC.add(true);
					}
				}
				Node location = orden.getHijos().get(i).getHijos().get(0);
				if(location.getHijos().size()==1){
					String columna =  location.getHijos().get(0).getData();
					
					if(!colsSelect.contains(columna)){
						boolean encontrado = false;
						for(int k=0;k<tablasSelect.size();k++ ){
							
							if(colsSelect.contains(tablasSelect.get(k)+" . "+columna)){
								colsOrder.add(registrosMostrar.get(0).indexOf(tablasSelect.get(k)+" . "+columna));
								encontrado = true;
								break;
							}
						}
						if(!encontrado){
							salidaTexto +="Error. La columna "+columna +" no existe. El ORDER no puede tener columnas que no existen \n";
							return false;
						}
					}else{
						colsOrder.add(registrosMostrar.get(0).indexOf(columna));
					}
				}
				else if(location.getHijos().size()==3){
					String columna =  location.getHijos().get(0).getData()+" . "+location.getHijos().get(2).getData();
					
					if(!colsSelect.contains(columna)){
						
						salidaTexto +="Error. La columna "+columna +" no esta seleccionada. El ORDER no puede tener columnas que no estan seleccionadas \n";
						
					
						return false;
					
					}else{
						colsOrder.add(registrosMostrar.get(0).indexOf(columna));
					}
				}
				
				// Se ordenan los datos de la lista.
				Collections.sort(registrosOrder, new DataComparator(colsOrder, DESC));
				
			}
			registrosOrder.add(0, colsSelect);		
		}
		
		
		Interfaz.verbose+="	Desplegando datos seleccionados";
		
		// Registros imprimir contiene la lista de registros para ensenarle al usuario, ya en el orden correcto.
		ArrayList<ArrayList<String>> registrosImprimir = new ArrayList<ArrayList<String>>();

		registrosImprimir.addAll(registrosOrder);
		
		
		contSelect = registrosImprimir.size()-1;

		// Se llama al metodo que muestra la tabla.
		Mostrar(registrosImprimir);
		
		Interfaz.verbose+="Select realizado \n";
		
		
		
		
		return true;
		
	}
	
	
	// Metodo que me indica si una columna aparece ninguna o mas veces en una lista de tablas
	private int ColumnaCorrectas(String col, ArrayList<String> tabs ){

		int contador = 0;
		
		if(col.contains(".")){
				
			String[] info = col.split(" . ");
			
			if(enUso.tablas.get(enUso.TablaExiste(info[0])).ColumnaExiste(info[1])!=-1){
				contador++;
			}
		}else{
			for(int i=0;i<tabs.size();i++ ){
				if(enUso.tablas.get(enUso.TablaExiste(tabs.get(i))).ColumnaExiste(col)!=-1){
					contador++;
				}
			}
			
		}
		
		return contador;
		
	}

	
	// MEtodo que realiza un producto cruz de todos los registros de varias tablas.
	private ArrayList<ArrayList<String>>  productoCruz (ArrayList<String>  tablas){
		Interfaz.verbose+=" 	Realizando producto cruz \n";
	
		ArrayList<ArrayList<String>>  registrosCruz = new ArrayList<ArrayList<String>> ();
		ArrayList<ArrayList<String>> registros1 = null;
		for(int i=0;i<tablas.size();i++ ){
			if(i==0){
				registros1 = (ArrayList<ArrayList<String>>) enUso.tablas.get(enUso.TablaExiste(tablas.get(0))).registros.clone();
			}else{
				ArrayList<ArrayList<String>>  registros2 = (ArrayList<ArrayList<String>>) enUso.tablas.get(enUso.TablaExiste(tablas.get(i))).registros.clone();
				for(int j=0;j<registros1.size();j++ ){
					for(int k=0;k<registros2.size();k++ ){
						ArrayList<String> linea = new ArrayList<String>();
						linea.addAll(registros1.get(j));
						linea.addAll(registros2.get(k));
						registrosCruz.add(linea);
					}
				}
			}
			
			
		}
		
		Interfaz.verbose+="		Producto cruz realizado \n";
		return registrosCruz;
		
	}

	
	
	// Metodo que ingresa los datos del SELECT en una tabla de la interfaz grafica.
	public void Mostrar(ArrayList<ArrayList<String>> reg){
		String[]  cols = new String[reg.get(0).size()];
		for(int i=0;i<reg.get(0).size();i++ ){
			cols[i]=reg.get(0).get(i);
		}
		DefaultTableModel model = new DefaultTableModel(
				new Object[][] {
				},
				cols
			){
			};
		

		for(int i=1;i<reg.size();i++ ){
			
				Object[]  fila = new Object[reg.get(0).size()];
				for(int j=0;j<reg.get(i).size();j++ ){
					fila[j] = reg.get(i).get(j);
				}
				model.addRow(fila);
			
			
		}
		
		modelo.setModel(model);
		
	}
	
	
	// Metodo que hace un DELETE
	private boolean Delete(Node arbol) throws ScriptException{
		
		Interfaz.verbose+="Iniciando Delete \n";
		
		
		Interfaz.verbose+="Buscando base de datos en uso \n";
		
		// Se revisa que haya una base de datos en uso.
		if(!Seleccionada){
			salidaTexto += "Error. No hay una base de datos en uso \n";
			return false;
		}
		
		// Se revisa que se borrara de una tabla que existe.
		String nomTabla = arbol.getHijos().get(2).getData();
		Interfaz.verbose+="Llamando tabla "+nomTabla+" \n";
		File archivo = new File("Servidor\\"+enUso.nombre+"\\"+nomTabla+".txt");
		
		if(!archivo.exists()){
			salidaTexto += "Error. No existe la tabla "+nomTabla +"\n";
			return false;
		}
		int pos = enUso.TablaExiste(nomTabla);
		Tabla tablaBorrar = enUso.tablas.get(pos);
		
		// Borrar todo de la tabla
		
		if(arbol.getHijos().size() ==3){
			Interfaz.verbose+="	Borrando todos los registros \n";
			// Se borran los registros uno por uno.
			for(int i=0;i<tablaBorrar.registros.size();i++ ){
				if(BuscarRefsDelete(i, tablaBorrar)){
					tablaBorrar.BorrarRegistro(i);
					i--;
					contDelete++;
				}
			}
		}
		// Borrar con condicion.
		else{
			Interfaz.verbose+="	Revisando condicion del WHERE \n";
			
			// Se revisa la expresion del WHERE y se obtienen las columnas que participan en este.
			Node exp = arbol.getHijos().get(4);
			String expresion = Expresiones.traducir(Expresiones.hacerExprecion(exp.getHijos().get(0)));
			ArrayList<String> cols = new ArrayList<String> ();
			if(Expresiones.revisarTiposWhere(tablaBorrar, cols, exp.getHijos().get(0)).equals("Error")){
				
				salidaTexto+= "Error. Problemas en el Where. Los tipos no coinciden, ingreso columnas que no existen, o hay ambiguedad en las columnas \n";
				return false;
			}
			Interfaz.verbose+="	Evaluando los registros en la condicion \n";
			
			// Se revisan todos los registros.
			for(int i=0;i<tablaBorrar.registros.size();i++ ){
				// condicion
				ArrayList<String> registroLinea = tablaBorrar.registros.get(i);
				ScriptEngine engine = new ScriptEngineManager().getEngineByExtension("js");
				
				// Se insertan los valores de las columnas que participan en el WHERE.
				for(int j=0;j<cols.size();j++ ){
					String valor = registroLinea.get(tablaBorrar.ColumnaExiste(cols.get(j)));
					if(valor.charAt(0)=='\''){
						valor = valor.substring(1, valor.length()-1);
					}
					//engine.put(cols.get(j).toString(), .toString());
					engine.put(cols.get(j), valor.replace(" ", "") );
					
					//System.out.println(engine.get(cols.get(j)));
				}
				
				
				

				// Solo se borran los que cumplen la condicion.
				if((boolean) engine.eval(expresion)){
					
					if(BuscarRefsDelete(i, tablaBorrar)){
						tablaBorrar.BorrarRegistro(i);
						i--;
						contDelete++;
					}
				}
				
				
			}	
		}
		Interfaz.verbose+="Delete realizado \n";
		//System.out.println(contDelete);
		return true;
		
	}
	
	
	// Metodo que revisa que no haya una referencia a un registro que se borrara.
	private boolean BuscarRefsDelete(int pos, Tabla tablaBorrar){
		Interfaz.verbose+="		Revisando que no se violen llaves foraneas con el DELETE  \n";
		
		// Se revisan todas las llaves foraneas de todas las tablas de la base de datos en uso.
		for(int i=0;i<enUso.tablas.size();i++){
			for(int j=0;j<enUso.tablas.get(i).fk.size();j++){
				// Se encontro una tabla que hace referencia a la tabla donde se borra.
				if(enUso.tablas.get(i).fk.get(j).TablaReferencia.equals(tablaBorrar.nombre)){
					// Se obtiene el numero de columna referenciada y el valor en esa columna
					int numColumna = tablaBorrar.NombresColumnas().indexOf(enUso.tablas.get(i).fk.get(j).ColumnaReferencia);
					String valorActual = tablaBorrar.registros.get(pos).get(numColumna);
					
					// Se obtiene numero de la columna en la tabla que hace la referencia;
					int NumColumnaEnTablaRef = enUso.tablas.get(i).ColumnaExiste(enUso.tablas.get(i).fk.get(j).llaveActual);
					for(int k=0;k<enUso.tablas.get(i).registros.size();k++){
						String valorEnTabRef = enUso.tablas.get(i).registros.get(k).get(NumColumnaEnTablaRef);
						//System.out.println(valorEnTabRef);
						if(valorActual.equals(valorEnTabRef)){
							salidaTexto +=  "Error. Violacion a llave foranea " + enUso.tablas.get(i).fk.get(j).nombre + " en la tabla " +enUso.tablas.get(i).nombre + " no se puede borrar el registro donde " + tablaBorrar.NombresColumnas().get(numColumna) + " = " +valorActual + " por que en la tabla "+enUso.tablas.get(i).fk.get(j).nombre + " hay una referencia a ese valor \n";
							Interfaz.verbose+="	Violacion a llave Foranea \n";
							return false;
						}
					}
				}
				
			}
		}
		return true;
	}

	
	// Metodo que muestra columnas de una tabla
	private boolean MostrarColumnas(Node arbol) {
		Interfaz.verbose+="Iniciando SHOW COLUMNS \n";
		Interfaz.verbose+="Llamando Base de datos en Uso \n";
		if(!Seleccionada){
			salidaTexto += "Error. No hay una Base de Datos en uso";
			return false;
		}
		
		String nomTabla = arbol.getHijos().get(3).getData();
		Interfaz.verbose+="	Llamando a la tabla "+nomTabla+"\n";
		File archivo = new File("Servidor\\"+enUso.nombre+"\\"+nomTabla+".txt");
		
		if(!archivo.exists()){
			salidaTexto += "Error. No existe la tabla "+nomTabla +"\n";
			return false;
		}
		
		int pos = enUso.TablaExiste(nomTabla);
		Tabla tablaMostrar = enUso.tablas.get(pos);
		
		Interfaz.verbose+="	Desplegando columnas \n";
		salidaTexto += "Columnas en la tabla: "+ tablaMostrar.nombre+" \n";
		for(int i=0;i<tablaMostrar.columnas.size();i++ ){
			String tamano = "";
			if(tablaMostrar.columnas.get(i).tipo.toUpperCase().equals("CHAR")){
				 tamano = " ("+ Integer.toString(tablaMostrar.columnas.get(i).tamano) + ")";
			}
			salidaTexto += "	- "+ tablaMostrar.columnas.get(i).nombre+"  "+ tablaMostrar.columnas.get(i).tipo +tamano +" \n";
		}
		
		salidaTexto += "Restricciones: \n";
		
		if(tablaMostrar.pk.size()!=0){
			salidaTexto += "* Llave Primaria \n";
			if(tablaMostrar.pk.get(0).llaves.size()==1){
				salidaTexto += "	- "+tablaMostrar.pk.get(0).nombre + ":= "+tablaMostrar.pk.get(0).llave +"\n";
			}else{
				String llaves = "";
				for(int i=0;i<tablaMostrar.pk.get(0).llaves.size();i++ ){
					llaves+=tablaMostrar.pk.get(0).llaves.get(i) +","; 
				}
				salidaTexto += "	- "+tablaMostrar.pk.get(0).nombre + ":= "+ llaves +"\n";
			}
		}
		
		
		if(tablaMostrar.fk.size()!=0){
			salidaTexto += "* Llaves Foraneas \n";
			for(int i=0;i<tablaMostrar.fk.size();i++ ){
				
				salidaTexto += "	- "+tablaMostrar.fk.get(i).nombre + ":= "+tablaMostrar.fk.get(i).llaveActual +" REFERENCES "+ tablaMostrar.fk.get(i).TablaReferencia +" ( "+ tablaMostrar.fk.get(i).ColumnaReferencia+") \n";
			}
		}
		
		if(tablaMostrar.checks.size()!=0){
			salidaTexto += "* Checks \n";
			for(int i=0;i<tablaMostrar.checks.size();i++ ){
				
				salidaTexto += "	- "+tablaMostrar.checks.get(i).nombre + ":= "+tablaMostrar.checks.get(i).expresion +" \n";
			}
		}
		
		Interfaz.verbose+="SHOW COLUMNS realizado \n";
		return true;
	}

	// Metodo que muestra las tablas de una base de datos.
	private boolean MostrarTablas(Node arbol) {
		Interfaz.verbose+="Iniciando SHOW TABLES \n";
		Interfaz.verbose+="Llamando a base de datos en uso \n";
		Interfaz.verbose+="	Desplegando tablas \n";
		if(!Seleccionada){
			salidaTexto += "Error. No hay una base de datos en uso \n";
			return false;
		}
		salidaTexto += "Tablas en: "+ enUso.nombre+" \n";
		for(int i=0;i<enUso.tablas.size();i++ ){
			salidaTexto += "- "+ enUso.tablas.get(i).nombre+ "\n"; 
		}
		Interfaz.verbose+="SHOW TABLES realizado \n";
		return true;
	}

	
	
	// Metodo que borra una tabla de una base de datos.
	private boolean BorrarTabla(Node arbol) throws IOException {
		Interfaz.verbose+="Iniciando DROP TABLE \n";
		Interfaz.verbose+="Llamando Base de datos en uso \n";
		
		// Se revisa que haya una base de datos en uso.
		if(!Seleccionada){
			salidaTexto += "Error. No hay una base de datos en uso \n";
			return false;
		}
		// Se revisa que la tabla exista.
		String nomTabla = arbol.getHijos().get(2).getData();
		Interfaz.verbose+="Llamando a la tabla "+ nomTabla +" \n";
		File archivo = new File("Servidor\\"+enUso.nombre+"\\"+nomTabla+".txt");
		
		if(!archivo.exists()){
			salidaTexto += "Error. No existe la tabla "+nomTabla +"\n";
			return false;
		}
		int pos = enUso.TablaExiste(nomTabla);
		
		Interfaz.verbose+="	Buscando referencias a la tabla "+ nomTabla +"\n";
		
		// Se revisa que no haya una referencia a esa tabla.
		if(BuscarReferenciaTabla(nomTabla)){
			
			// Y si no hay, se borra de la metadata y tambien el archivo con sus registros.
			FileWriter f = new FileWriter("Servidor\\"+enUso.nombre+"\\"+nomTabla+".txt");
		      
		    BufferedWriter bw = new BufferedWriter(f);
		    bw.close();  
			enUso.tablas.remove(pos);
			
			archivo.delete();
			salidaTexto += "Se elimino la tabla "+nomTabla+" \n";
			Interfaz.verbose+="	Tabla "+ nomTabla +" borrada \n";
			Interfaz.verbose+="DROP TABLE Realizado \n";
			return true;
		}
		else{
			Interfaz.verbose+="	No se elimino la tabla "+ nomTabla +"\n";
			//salidaTexto += "Error. No existe la tabla "+tabla;
			//System.out.println("No se pudo borrar la tabla "+nomTabla+ " porque hay una referencia a esta en otra tabla");
		}
		
		Interfaz.verbose+="DROP TABLE Realizado \n";
		return false;
	}

	// Metodo que revisa todas las llaves foraneas de las tablas de la base de datos en uso, para ver si hay una referencia a esa tabla.
	private boolean BuscarReferenciaTabla(String tabla) {
		for(int i=0;i<basesDeDatos.size();i++ ){
			for(int j=0;j<basesDeDatos.get(i).tablas.size();j++ ){
				Tabla actual = basesDeDatos.get(i).tablas.get(j);
				for(int k=0;k<actual.fk.size();k++ ){
					if(actual.fk.get(k).TablaReferencia.equals(tabla) ){
						
						salidaTexto += "Error. No se puede borrar la tabla "+tabla +" porque la tabla "+actual.fk.get(k).TablaReferencia + " depende de ella en su llave foranea "+actual.fk.get(k).nombre + "\n";
						//System.out.println("No se pudo borrar la tabla "+nomTabla+ " porque hay una referencia a esta en otra tabla");
						return false;
					}
				}
			}
		}
		
		
		return true;
	}

	
	// Metodo que borra una columna de una tabla
	private boolean BorrarColumna(String tabla, Node arbol) {
		Interfaz.verbose+="Iniciando DROP COLUMN \n";
		Interfaz.verbose+="Llamando base de datos en uso \n";
		
		// Se revisa que haya una base de datos en uso.
		if(!Seleccionada){
			salidaTexto += "Error. No hay una base de datos en uso \n";
			return false;
		}
		
		Interfaz.verbose+="Llamando a la tabla "+tabla +"\n";
		
		
		// Se revisa que la tabla exista.
		File archivo = new File("Servidor\\"+enUso.nombre+"\\"+tabla+".txt");
		
		if(!archivo.exists()){
			salidaTexto += "Error. No existe la tabla "+tabla +"\n";
			return false;
		}
		
		int tab = getTabla(tabla);
		
		Tabla tablaAlt = enUso.tablas.get(tab);	
		
		
		// Se revisa que la columna exista.
		String nomCol = arbol.getHijos().get(2).getData();
		Interfaz.verbose+="Buscando la columna "+nomCol+" \n";
		
		int pos = tablaAlt.ColumnaExiste(nomCol);
		if(pos !=-1){
			// Se revisa que no haya una referencia a esa columna.
			if(BuscarReferenciaColumna(tablaAlt.nombre, nomCol)){
				// Se borra la columna.
				tablaAlt.BorrarColumna(nomCol);
				salidaTexto += "Se elimino la columna "+nomCol+" de la tabla "+tablaAlt.nombre+" \n";
				Interfaz.verbose+="	Se elimino la culumna "+ nomCol +"\n";
				Interfaz.verbose+="DROP COLUMN Realizado \n";
				return true;
			}else{
				
				return false;
			}
				
		}
		salidaTexto += "Error. No existe la columna "+ nomCol +"\n";
		Interfaz.verbose+="DROP COLUMN Realizado \n";
		return false;
	}

	// Metodo que revisa todas las llaves foraneas de las tablas de la base de datos en uso, para ver si hay una referencia a esa columna
	private boolean BuscarReferenciaColumna(String tabla, String columna) {
		for(int i=0;i<basesDeDatos.size();i++ ){
			for(int j=0;j<basesDeDatos.get(i).tablas.size();j++ ){
				Tabla actual = basesDeDatos.get(i).tablas.get(j);
				for(int k=0;k<actual.fk.size();k++ ){
					if(actual.fk.get(k).TablaReferencia.equals(tabla) &&  actual.fk.get(k).ColumnaReferencia.equals(columna) ){
						salidaTexto += "Error. No se puede borrar la columna "+columna +" porque la tabla "+actual.fk.get(k).TablaReferencia + "depende de ella en su llave foranea "+actual.fk.get(k).nombre  +" \n";
						
						//System.out.println("Error. Hay una llave en otra tabla que hace referencia a esa columna, asi que no se puede eliminar");
						
						
						return false;
					}
				}
			}
		}
		
		
		return true;
	}

	// Metodo que borra un CONSTRAINT
	private boolean BorrarConstraint(String tabla, Node arbol) {
		
		Interfaz.verbose+="Iniciando DROP CONSTRAINT \n";
		
		Interfaz.verbose+="Llamando Base de datos en uso \n";
		
		// Se revisa que haya una base de datos en uso
		if(!Seleccionada){
			salidaTexto += "Error. No hay una base de datos en uso \n";
			return false;
		}
		
		// se revisa que la tabla exista.
		File archivo = new File("Servidor\\"+enUso.nombre+"\\"+tabla+".txt");
		Interfaz.verbose+="Llamando a la tabla "+tabla+" \n";
		
		if(!archivo.exists()){
			salidaTexto += "Error. No existe la tabla "+tabla +"\n";
			return false;
		}
		
		int tab = getTabla(tabla);
		
		Tabla tablaAlt = enUso.tablas.get(tab);	
		
		
		// se busca el nombre del constraint dentro de todas las restricciones, y al encontrarse se borra.
		String nomCons = arbol.getHijos().get(2).getData();
		
		int pos = tablaAlt.PKExiste(nomCons);
		if(pos !=-1){
			tablaAlt.pk.remove(pos);
			salidaTexto += "Se elimino el constraint "+nomCons+" de la tabla "+tablaAlt.nombre+" \n";
			Interfaz.verbose+="	Constraint "+nomCons+" eliminada \n";
			Interfaz.verbose+="	DROP CONSTRAINT realizado \n";
			return true;
		}
		
		pos = tablaAlt.FKExiste(nomCons);
		if(pos !=-1){
			tablaAlt.fk.remove(pos);
			salidaTexto += "Se elimino el constraint "+nomCons+" de la tabla "+tablaAlt.nombre+" \n";
			Interfaz.verbose+="	Constraint "+nomCons+" eliminada \n";
			Interfaz.verbose+="	DROP CONSTRAINT realizado \n";
			return true;
		}
		
		pos = tablaAlt.chkExiste(nomCons);
		if(pos !=-1){
			tablaAlt.checks.remove(pos);
			salidaTexto += "Se elimino el constraint "+nomCons+" de la tabla "+tablaAlt.nombre+" \n";
			Interfaz.verbose+="	Constraint "+nomCons+" eliminada \n";
			Interfaz.verbose+="	DROP CONSTRAINT realizado \n";
			return true;
		}
		
		
		salidaTexto += "Error. No existe el COSTRAINT "+ nomCons +"\n";
		
		Interfaz.verbose+="	DROP CONSTRAINT realizado \n";
		return false;
	}

	// Metodo que agrega un CONSTRAINT
	private boolean AgregarConstraint(String tabla, Node arbol) {
		
		// Se revisa que haya una base de datos en uso
		Interfaz.verbose+="Iniciando ADD CONSTRAINT \n";
		Interfaz.verbose+="Llamando Base de datos en Uso \n";
		
		if(!Seleccionada){
			salidaTexto += "Error. No hay una base de datos en uso \n";
			return false;
		}
		
		// Se revisa que la tabla exista.
		File archivo = new File("Servidor\\"+enUso.nombre+"\\"+tabla+".txt");
		Interfaz.verbose+="	Llamando a la tabla "+tabla+"\n";
		if(!archivo.exists()){
			salidaTexto += "Error. No existe la tabla "+tabla +"\n";
			return false;
		}
		
		int tab = getTabla(tabla);
		
		Tabla tablaAlt = enUso.tablas.get(tab);	
		Node cons = arbol.getHijos().get(2);
		Interfaz.verbose+="	Revisando datos del constraint \n";
		
		//Se revisa que tipo de constraint es, y se agrega si cumple. 
			if(cons.getHijos().get(0).getData().equals("PrimaryKey")){
				// Se intenta agregar una llave primaria.
				if(tablaAlt.pk.size() == 1 ){
					salidaTexto += "Error. La tabla "+tablaAlt.nombre + " ya tiene una llave primaria \n";
					return false;
					
				}
				if(!tablaAlt.addPK(cons.getHijos().get(0))){
					salidaTexto += "Error. No existe la columna "+cons.getHijos().get(0).getHijos().get(4).getData()+" en la tabla "+tablaAlt.nombre+"\n";
					
					return false;
				}
			}
			// Se intenta agregar una llave foranea.
			if(cons.getHijos().get(0).getData().equals("ForeignKey")){
				// Se obtienen los datos de la restriccion.
				String nombreFK = cons.getHijos().get(0).getHijos().get(0).getData();  // Nombre
				String nombreCol = cons.getHijos().get(0).getHijos().get(4).getData();  // Columna
				String nombreTabRef = cons.getHijos().get(0).getHijos().get(7).getData(); // Tabla referenciada
				String nombreColRef = cons.getHijos().get(0).getHijos().get(9).getData(); // Columna referenciada.
				
				// Se revisa que el nombre de la FK no exista ya.
				if( (tablaAlt.FKExiste(nombreFK) != -1) || (tablaAlt.PKExiste(nombreFK) != -1)  || (tablaAlt.chkExiste(nombreFK) != -1)  ){
					salidaTexto += "Error. La constraint "+nombreFK+" ya existe en la tabla "+tablaAlt.nombre +"\n";
					return false;
				}
				
				// Se revisa que exista la columna
				if(tablaAlt.ColumnaExiste(nombreCol)==-1){
					salidaTexto += "Error. La columna " + nombreCol +" no existe en la tabla "+tablaAlt.nombre;
					return false;
				}
				// Se revisa que exista la tabla referenciada
				if(enUso.TablaExiste(nombreTabRef)==-1){
					salidaTexto += "Error. No existe la tabla "+nombreTabRef +" para hacer referencia \n";
					
					return false;
				}
				// Se revisa que exista la columna referenciada
				if(enUso.tablas.get(enUso.TablaExiste(nombreTabRef)).ColumnaExiste(nombreColRef)==-1){
					salidaTexto += "Error. No existe la columna "+nombreColRef+" en la tabla "+nombreTabRef +"\n";
					return false;
				}
				// Se revisa que exista la columna referenciada sea llave primaria.
				Tabla TabRef = enUso.tablas.get(enUso.TablaExiste(nombreTabRef));
				if(TabRef.pk.isEmpty()){
					salidaTexto += "Error. La columna "+nombreColRef+" en la tabla "+nombreTabRef +" no es una llave primaria, asi que no se puede hacer referencia a ella como llave foranea \n";
					return false;
				}else{
					if(!TabRef.pk.get(0).llave.equals(nombreColRef)){
						salidaTexto += "Error. La columna "+nombreColRef+" en la tabla "+nombreTabRef +" no es una llave primaria, asi que no se puede hacer referencia a ella como llave foranea \n";
						return false;
					}
				}
				
				// Se revisa que los tipos de las columnas coincidad.
				if(!compararTipoColumnas(tablaAlt.nombre, nombreCol, nombreTabRef, nombreColRef  ) ){
					
					
					return false;
				}
				
				
				tablaAlt.addFK(cons);
				
				
			}
			
			// Se intenta agregar un check.
			if(cons.getHijos().get(0).getData().equals("Check")){
				String nombreChk = cons.getHijos().get(0).getHijos().get(0).getData();
				Node chk = cons.getHijos().get(0).getHijos().get(2);
				// Se revisa que el nombre no exista.
				if( (tablaAlt.FKExiste(nombreChk) != -1) || (tablaAlt.PKExiste(nombreChk) != -1)  || (tablaAlt.chkExiste(nombreChk) != -1)   ){
					salidaTexto += "Error. La constraint "+nombreChk+" ya existe en la tabla "+tablaAlt.nombre +"\n";
					return false;
				}
				
				// Se revisa que la condicion sea valida.
				if(!tablaAlt.addChk(nombreChk, chk)){
					salidaTexto += "Error. El CHECK " +nombreChk +" no esta bien definido. Ingreso columnas que no existen, o los tipos no combinan";
					return false;
				}
				
				
				
			}
			
			
			Interfaz.verbose+=" Constraint agregado \n";
			Interfaz.verbose+="ADD CONSTRAINT realizado \n";
			salidaTexto += "Se agrego un constraint a la tabla "+tablaAlt.nombre+" \n";
		
		return true;
		
	}

	// Metodo que agrega una columna.
	private boolean AgregarColumna(String tabla, Node arbol) {
		// Se revisa que haya una base de datos en uso.
		Interfaz.verbose+="Iniciando ADD COLUMN \n";
		Interfaz.verbose+="Llamando Base de datos en Uso \n";
		if(!Seleccionada){
			salidaTexto += "Error. No hay una base de datos en uso \n";
			return false;
		}
		// Se revisa que la columna exista.
		File archivo = new File("Servidor\\"+enUso.nombre+"\\"+tabla+".txt");
		Interfaz.verbose+="Llamando tabla "+tabla+" \n";
		
		if(!archivo.exists()){
			salidaTexto += "Error. No existe la tabla "+tabla +"\n";
			return false;
		}
		
		
		// Se obtienen el nombre, tipo y tamano (si es CHAR) de la nueva columna.
		String nomNuevaCol = arbol.getHijos().get(2).getHijos().get(0).getData();
		String tipoNuevaCol = arbol.getHijos().get(2).getHijos().get(1).getHijos().get(0).getData();
		int tamanoNuevaCol = 0;
		if(tipoNuevaCol.toUpperCase().equals("CHAR")){
			tamanoNuevaCol = Integer.parseInt(arbol.getHijos().get(2).getHijos().get(1).getHijos().get(2).getData());
			System.out.println(tamanoNuevaCol);
		}
		ArrayList<PrimaryKey> newPK = new ArrayList<PrimaryKey>(); 
		ArrayList<ForeignKey> newFK = new ArrayList<ForeignKey>();
		ArrayList<Check> newCHK = new ArrayList<Check>();
		
		
		
		int tab = getTabla(tabla);
		
		Tabla tablaAlt = enUso.tablas.get(tab);
		
		
		// Se repite el proceso para agregar un CONSNTRAINT.
		for(int i=4;i<arbol.getHijos().size();i++){
			Node actual = arbol.getHijos().get(i);
			if(actual.getData().equals("Constraint")){
				if(actual.getHijos().get(0).getData().equals("PrimaryKey")){
					String nomPK = actual.getHijos().get(0).getHijos().get(0).getData();
					String colPK = actual.getHijos().get(0).getHijos().get(4).getData();
					if(tablaAlt.pk.size() == 1 ){
						salidaTexto += "Error. La tabla "+tablaAlt.nombre + " ya tiene una llave primaria \n";
						return false;
					}
					if( !colPK.equals(nomNuevaCol)  ){
						salidaTexto += "Error. No puede agregar Constraints que no involucren la columna nueva "+ nomNuevaCol+"\n";
						return false;
					}
					newPK.add(new PrimaryKey(nomPK, colPK));
				}
				
				if(actual.getHijos().get(0).getData().equals("ForeignKey")){
					
					String nombreFK = actual.getHijos().get(0).getHijos().get(0).getData(); 
					String nombreCol = actual.getHijos().get(0).getHijos().get(4).getData(); 
					String nombreTabRef = actual.getHijos().get(0).getHijos().get(7).getData();
					String nombreColRef = actual.getHijos().get(0).getHijos().get(9).getData();
					
					if( (tablaAlt.FKExiste(nombreFK) != -1) || (tablaAlt.PKExiste(nombreFK) != -1) || (tablaAlt.chkExiste(nombreFK) != -1)   ){
						salidaTexto += "Error. La constraint "+nombreFK+" ya existe en la tabla "+tablaAlt.nombre +"\n";
						return false;
					}
					
					if(!nombreCol.equals(nomNuevaCol)){
						salidaTexto += "Error. No puede agregar Constraints que no involucren la columna nueva "+ nomNuevaCol+"\n";
						
						return false;
					}
					
					if(enUso.TablaExiste(nombreTabRef)==-1){
						salidaTexto += "Error. No existe la tabla "+nombreTabRef +" para hacer referencia \n";
						
						return false;
					}
					if(enUso.tablas.get(enUso.TablaExiste(nombreTabRef)).ColumnaExiste(nombreColRef)==-1){
						salidaTexto += "Error. No existe la columna "+nombreColRef+" en la tabla "+nombreTabRef +"\n";
						return false;
					}
					
					Tabla TabRef = enUso.tablas.get(enUso.TablaExiste(nombreTabRef));
					if(TabRef.pk.isEmpty()){
						salidaTexto += "Error. La columna "+nombreColRef+" en la tabla "+nombreTabRef +" no es una llave primaria, asi que no se puede hacer referencia a ella como llave foranea \n";
						return false;
					}else{
						if(!TabRef.pk.get(0).llave.equals(nombreColRef)){
							salidaTexto += "Error. La columna "+nombreColRef+" en la tabla "+nombreTabRef +" no es una llave primaria, asi que no se puede hacer referencia a ella como llave foranea \n";
							return false;
						}
					}
					
					if(!compararTipoColumnas(tablaAlt.nombre, nombreCol, nombreTabRef, nombreColRef  ) ){
						System.out.println("Error. No se pueden hacer referencias entre columnas de diferentes tipos");
						return false;
					}
					
					newFK.add(new ForeignKey (nombreFK, nombreCol, nombreTabRef, nombreColRef));
					
					
				}
				
				if(actual.getHijos().get(0).getData().equals("Check")){
					String nombreChk = actual.getHijos().get(0).getHijos().get(0).getData();
					Node chk = actual.getHijos().get(0).getHijos().get(2);
					if( (tablaAlt.FKExiste(nombreChk) != -1) || (tablaAlt.PKExiste(nombreChk) != -1)  || (tablaAlt.chkExiste(nombreChk) != -1)   ){
						salidaTexto += "Error. La constraint "+nombreChk+" ya existe en la tabla "+tablaAlt.nombre +"\n";
						return false;
					}
					Check nChk = new Check(nombreChk);
					String expresion = Expresiones.hacerExprecion(chk.getHijos().get(0));
					nChk.setExpresion(expresion);
					tablaAlt.columnas.add(new Columna(nomNuevaCol, tipoNuevaCol));
					String tiposCorrectos = Expresiones.revisarTiposChk(tablaAlt, nChk,chk.getHijos().get(0)); 
					tablaAlt.columnas.remove(tablaAlt.columnas.size()-1);
					if(!tiposCorrectos.equals("Ok")){
						salidaTexto += "Error. El CHECK " +nombreChk +" no esta bien definido. Ingreso columnas que no existen, o los tipos no combinan";
						return false;
					}
					
					
					

					
					if(nChk.variables.size()==1  && nChk.variables.get(0).equals(nomNuevaCol)){
						newCHK.add(nChk);
					}
					else{
						salidaTexto += "Error. No puede agregar Constraints que no involucren la columna nueva "+ nomNuevaCol+"\n";
						return false;
					}
					
					
					
					
				}
				
			}
		}
		
		
		// Se revisa que la columna no exista ya.
		
		if(!tablaAlt.addColumna(nomNuevaCol, tipoNuevaCol, tamanoNuevaCol)){
			salidaTexto += "Error. La columna "+nomNuevaCol+" ya existe en la tabla "+tablaAlt.nombre + "\n";
			return false;
		}
		
		for(int i=0;i<newPK.size();i++){
			tablaAlt.pk.add(newPK.get(0));
		}
		
		for(int i=0;i<newFK.size();i++){
			tablaAlt.fk.add(newFK.get(i));
		}
		
		for(int i=0;i<newCHK.size();i++){
			tablaAlt.checks.add(newCHK.get(i));
		}
		
		Interfaz.verbose+="Iniciando ADD COLUMN realizado \n";

		salidaTexto += "Se agrego una columna a la tabla "+tablaAlt.nombre+" \n";
		return true;
	}

	// Metodo para renombrar una tabla
	private boolean RenombrarTabla(Node arbol) {
		
		// Se revisa que haya una base de datos en uso
		Interfaz.verbose+="Iniciando ALTER TABLE ... RENAME \n";
		Interfaz.verbose+="Llamando Base de datos en Uso \n";
		if(!Seleccionada){
			salidaTexto += "Error. No hay una base de datos en uso \n";
			return false;
		}
		
		// Se obtienen el nombre actual y el nuevo.
		String nombreActual = arbol.getHijos().get(2).getData();
		String nuevoNombre = arbol.getHijos().get(5).getData();
		Interfaz.verbose+="Llamando a la tabla "+nombreActual+" \n";

		// Se revisa que el nombre actual si exista.
		File archivo = new File("Servidor\\"+enUso.nombre+"\\"+nombreActual+".txt");
		
		if(!archivo.exists()){
			salidaTexto += "Error. No existe la tabla "+nombreActual +"\n";
			return false;
		}
		
		// Se revisa que el nuevo nombre no exista ya.
		File archivo2 = new File("Servidor\\"+enUso.nombre+"\\"+nuevoNombre+".txt");
		Interfaz.verbose+="	Revisando nuevo nombre \n";

		if(archivo2.exists()){
			salidaTexto += "Error. Ya exista una tabla llamada "+nuevoNombre +"\n";
			return false;
		}
		
		
		
		// Se renombran las referencias a la tabla.
		RenombrarReferencias(nombreActual,nuevoNombre );
		
		int tab = getTabla(nombreActual);
		enUso.tablas.get(tab).setNombre(nuevoNombre);
		
		
		// Se renombra el archivo.
		archivo.renameTo(new File("Servidor\\"+enUso.nombre+"\\"+nuevoNombre+".txt"));
		
		Interfaz.verbose+="ALTER TABLE... RENAME realizado \n";

		
		salidaTexto += "Se renombro la tabla "+nombreActual+" a "+nuevoNombre+"\n";
		return true;
	}

	// Metodo para Crear una tabla
	public boolean CrearTabla(Node arbol){
		
		Interfaz.verbose+="Iniciando ADD CONSTRAINT \n";
		Interfaz.verbose+="Llamando Base de datos en Uso \n";
		
		// Se revisa que haya una base de datos en uso.
		if(!Seleccionada){
		
			salidaTexto += "Error. No hay una base de datos en uso \n";
			return false;
		}
		else{
			
			Interfaz.verbose+="Revisando datos de la tabla \n";
			
			// Se revisa que el nombre de la tabla no exista ya.
			String nombre = arbol.getHijos().get(2).getData();
		
			File archivo = new File("Servidor\\"+enUso.nombre+"\\"+nombre+".txt");
			if(archivo.exists()){
				salidaTexto += "Error. Ya existe la tabla "+nombre +"\n";
				return false;
			}else{
			
				// Se agregan las columnas de la nueva tabla con sus nombres y tipos.
				Tabla nuevaTabla = new Tabla(nombre);
				for(int i=4;i<arbol.getHijos().size();i++){
					Node actual = arbol.getHijos().get(i);
					if(actual.getData().equals("Columna")){
						String nomCol = actual.getHijos().get(0).getData();
						String tipoCol = actual.getHijos().get(1).getHijos().get(0).getData();
						
						if(tipoCol.toUpperCase().equals("CHAR")){
							int tamanoCol = Integer.parseInt(actual.getHijos().get(1).getHijos().get(2).getData());
							if(!nuevaTabla.addColumna(nomCol, tipoCol.toUpperCase(), tamanoCol)){
								salidaTexto += "Error. La columna "+nomCol+" ya existe en la tabla "+nuevaTabla.nombre +"\n";
								return false;
							}
						}
						else{
							// Si una columna ya existe, no se agrega.
							if(!nuevaTabla.addColumna(nomCol, tipoCol.toUpperCase(), 0)){
						
								salidaTexto += "Error. La columna "+nomCol+" ya existe en la tabla "+nuevaTabla.nombre +"\n";
								return false;
							}
						}
					
					}
					// Se repite el proceso del ADD CONSTRAINT.
					if(actual.getData().equals("Constraint")){
						
						
						
						
						if(actual.getHijos().get(0).getData().equals("PrimaryKey")){
							if(nuevaTabla.pk.size() == 1 ){
								salidaTexto += "Error. La tabla "+nuevaTabla.nombre + " ya tiene una llave primaria \n";
								return false;
							}
							if(!nuevaTabla.addPK(actual.getHijos().get(0))){
								salidaTexto += "Error. No existe la columna "+actual.getHijos().get(0).getHijos().get(4).getHijos().get(0).getData()+" en la tabla "+nuevaTabla.nombre+"\n";
								return false;
							}
						}
						if(actual.getHijos().get(0).getData().equals("ForeignKey")){
							
							String nombreFK = actual.getHijos().get(0).getHijos().get(0).getData(); 
							String nombreCol = actual.getHijos().get(0).getHijos().get(4).getData(); 
							String nombreTabRef = actual.getHijos().get(0).getHijos().get(7).getData();
							String nombreColRef = actual.getHijos().get(0).getHijos().get(9).getData();
							
							if( (nuevaTabla.FKExiste(nombreFK) != -1) || (nuevaTabla.PKExiste(nombreFK) != -1) || (nuevaTabla.chkExiste(nombreFK) != -1)  ){
								salidaTexto += "Error. La constraint "+nombreFK+" ya existe en la tabla "+nuevaTabla.nombre +"\n";
								
								return false;
							}
							
							if(nuevaTabla.ColumnaExiste(nombreCol)==-1){
								salidaTexto += "Error. No existe la columna "+nombreCol+" en la tabla "+nuevaTabla.nombre +"\n";
								
								return false;
							}
							
							if(enUso.TablaExiste(nombreTabRef)==-1){
								salidaTexto += "Error. No existe la tabla "+nombreTabRef +" para hacer referencia \n";
								return false;
							}
							if(enUso.tablas.get(enUso.TablaExiste(nombreTabRef)).ColumnaExiste(nombreColRef)==-1){
								salidaTexto += "Error. No existe la columna "+nombreColRef+" en la tabla "+nombreTabRef +"\n";
								
								return false;
							}
							
							Tabla TabRef = enUso.tablas.get(enUso.TablaExiste(nombreTabRef));
							if(TabRef.pk.isEmpty()){
								salidaTexto += "Error. La columna "+nombreColRef+" en la tabla "+nombreTabRef +" no es una llave primaria, asi que no se puede hacer referencia a ella como llave foranea \n";
								return false;
							}else{
								if(!TabRef.pk.get(0).llave.equals(nombreColRef)){
									salidaTexto += "Error. La columna "+nombreColRef+" en la tabla "+nombreTabRef +" no es una llave primaria, asi que no se puede hacer referencia a ella como llave foranea \n";
									return false;
								}
							}
							
							
							/*if(!compararTipoColumnas(nuevaTabla.nombre, nombreCol, nombreTabRef, nombreColRef  ) ){
								System.out.println("Error. No se pueden hacer referencias entre columnas de diferentes tipos");
								return false;
							}*/
							
							nuevaTabla.addFK(actual);
							
							
						}
						
						
						if(actual.getHijos().get(0).getData().equals("Check")){
							String nombreChk = actual.getHijos().get(0).getHijos().get(0).getData();
							Node chk = actual.getHijos().get(0).getHijos().get(2);
							if( (nuevaTabla.FKExiste(nombreChk) != -1) || (nuevaTabla.PKExiste(nombreChk) != -1)  || (nuevaTabla.chkExiste(nombreChk) != -1)   ){
								salidaTexto += "Error. La constraint "+nombreChk+" ya existe en la tabla "+nuevaTabla.nombre +"\n";
								return false;
							}
							

							if(!nuevaTabla.addChk(nombreChk, chk)){
								salidaTexto += "Error. El CHECK no esta bien definido. Ingreso columnas que no existen, o los tipos no combinan \n";
								return false;
							}
							
							
							
						}
						
						
					}
					
						
				}
				enUso.addTabla(nuevaTabla);
				Interfaz.verbose+=" Se creo la tabla "+nombre+" \n";

				salidaTexto += "Se creo la tabla "+nombre+" \n";
				
				Interfaz.verbose+="	Creando archivo de datos \n";

				// Se crea un nuevo archivo para la tabla
				try {
					FileWriter f = new FileWriter("Servidor\\"+enUso.nombre+"\\"+nombre+".txt");
					f.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
		}
		
		Interfaz.verbose+="CREATE TABLE realizado \n";
	
		return true;
		
	}
	
	
	
	
	// Metodo para crear una DB
	public boolean  CrearBaseDeDatos(String nombre){
		Interfaz.verbose+="Iniciando CREATE DATABASE \n";
		// Se revisa que el nombre no exista ya.
		File directorio = new File("Servidor\\"+nombre);
		if(directorio.isDirectory()){
			Interfaz.verbose+="	Ya existe la base de datos "+nombre+ "\n";
			salidaTexto += "Ya existe la base de datos "+nombre+" \n";
			

			return false;
		}else{
			directorio.mkdir();
			basesDeDatos.add(new Database(nombre));
			Interfaz.verbose+="Se creo la base de datos "+nombre+" \n";
			salidaTexto += "Se creo la base de datos "+nombre+" \n";
			return true;
		}
		
		
	}
	
	
	// Metodo para borrar una DB
	public boolean BorrarBD(String nombre) throws IOException{
			// Se revisa que la base de datos exista.
		Interfaz.verbose+="Iniciando DROP DATABASE \n";
			File directorio = new File("Servidor\\"+nombre);
			if(!directorio.isDirectory()){
				salidaTexto += "No existe la base de datos "+nombre+" asi que no se puede borrar \n";
				Interfaz.verbose+="	No existe la base de datos "+nombre+" \n";
				return false;
			}else{
				
				// Se pregunta al usuario si esta seguro de borrar.
				if(JOptionPane.showConfirmDialog(null, "¿Esta seguro de borar la base de datos \""+nombre+"\"  con "+ Integer.toString(basesDeDatos.get(getBD(nombre)).CantRegistros()) +" registros?", "choose one", JOptionPane.YES_NO_OPTION) ==JOptionPane.YES_OPTION){
					BorrarDirectorio(directorio);
					if(Seleccionada)
						if(enUso.equals(basesDeDatos.get(getBD(nombre)))){
							enUso = null;
							Seleccionada = false;
						}
					basesDeDatos.remove(getBD(nombre));
					salidaTexto += "Se borro la base de datos "+nombre+" \n";
					Interfaz.verbose+="	Se borro la base de datos "+nombre+" \n";
				}else{
					Interfaz.verbose+="	No se borro la base de datos "+nombre+" \n";
				}
				Interfaz.verbose+="DROP DATABASE REALIZADO";
				return true;
			}
			
			
		}
		
	// Metodo para alterar una DB
	public boolean  AlterarBD(String nombre, String nuevoNombre){
		Interfaz.verbose+="Iniciando ALTER DATABASE \n";
		// Se revisa que la base de datos exista.
		File directorio = new File("Servidor\\"+nombre);
		if(!directorio.isDirectory()){
			Interfaz.verbose+="No existe la base de datos "+nombre+"\n";
			
			salidaTexto += "	No existe la base de datos "+nombre+" asi que no se puede alterar \n";
			return false;
		}else{
			// Se cambia el nombre.
			File directorio2 = new File("Servidor\\"+nuevoNombre);
			directorio.renameTo(directorio2);
			basesDeDatos.get(getBD(nombre)).setNombre(nuevoNombre);
			Interfaz.verbose+="	Se renombro la base de datos "+nombre+" a "+ nuevoNombre+" \n";
			salidaTexto += "Se renombro la base de datos "+nombre+" a "+ nuevoNombre+" \n";
			Interfaz.verbose+= "	ALTER DATABASE realizado \n";
			
			return true;
		}
		
		
	}
	
	
	// Metodo para usar una Base de datos.
	public boolean  UsarBD(String nombre){
		// Se revisa que la base de datos exista.
		Interfaz.verbose+="Iniciando USE DATABASE \n";
		File directorio = new File("Servidor\\"+nombre);
		if(!directorio.isDirectory()){
			salidaTexto += "No existe la base de datos "+nombre+" asi que no se puede usar \n";
			return false;
		}else{
			// Se guarda esa base de datos en enUso.
			enUso = basesDeDatos.get(getBD(nombre));
			Seleccionada = true;
			salidaTexto += "Se esta usando la Base de Datos: "+nombre+" \n";
			Interfaz.verbose+="Se esta usando la Base de Datos: "+nombre+" \n";
			Interfaz.verbose+= "	USE DATABASE realizado \n";
			
			return true;
		}
		
	}

	// Metodo que muestra las bases de datos.
	public void MostrarBD(){
		Interfaz.verbose+="Iniciando SHOW DATABASES \n";
		salidaTexto += "Bases de Datos:  \n";
		for(int i=0;i<basesDeDatos.size();i++ ){
			salidaTexto += "- "+ basesDeDatos.get(i).getNombre()+ "\n"; 
		}
		Interfaz.verbose+="SHOW DATABASES realizado \n";
	}
	
	
	// Metodo que la posicion de una base de datos en la lista. -1 si no existre.
	public int getBD(String nombre){
		for(int i=0;i<basesDeDatos.size();i++ ){
			if(basesDeDatos.get(i).getNombre().equals(nombre))
				return i;
		}
		return -1;
	}

	// Metodo que la posicion de una tabla en la base de datos en uso. -1 si no existre.
	public int getTabla(String nombre){
		for(int i=0;i<enUso.tablas.size();i++ ){
			if(enUso.tablas.get(i).nombre.equals(nombre))
				return i;
		}
		return -1;
	}
	
	
	// Metodo que altera las referencias a una tabla cuando esta es renombrada.
	public void RenombrarReferencias(String nomAct, String nuevNom){
		for(int i=0;i<basesDeDatos.size();i++ ){
			for(int j=0;j<basesDeDatos.get(i).tablas.size();j++ ){
				Tabla actual = basesDeDatos.get(i).tablas.get(j);
				for(int k=0;k<actual.fk.size();k++ ){
					if(actual.fk.get(k).TablaReferencia.equals(nomAct)){
						actual.fk.get(k).setTablaReferencia(nuevNom);
					}
				}
			}
		}
	}
	
	// Metodo que borra un directorio de una base de datos cuando esta se borra.
	
	public void BorrarDirectorio(File file) throws IOException{
			File[] contents = file.listFiles();
			
		    if (contents.length!=0) {
		    	
		    	int i=0;
		        while(i!=contents.length){
		        	
		        	
		        	
		        	
		        	FileWriter f1 = new FileWriter("Servidor\\"+file.getName()+"\\"+contents[i].getName());
				    BufferedWriter bw = new BufferedWriter(f1);
				    bw.close(); 
				    
				    File f = new File("Servidor\\"+file.getName()+"\\"+contents[i].getName());
				    f.delete();
		        	/*FileInputStream fi;
				    
					try {
						fi = new FileInputStream("Servidor\\"+file.getName()+"\\"+contents[i].getName());
						fi.close();
					} catch (FileNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
		        	*/

		        	
		        	i++;
		        }
		    }
		    file.delete();

	}

	
	// Metodo que compara que dos columnas tengan el mismo tipo.
	public boolean compararTipoColumnas(String Tabla1, String Columna1, String Tabla2, String Columna2 ){
		
		Tabla tab1 = enUso.tablas.get(enUso.TablaExiste(Tabla1));
		String tipo1 = tab1.columnas.get(tab1.ColumnaExiste(Columna1)).tipo.toUpperCase();
		
		
		Tabla tab2 = enUso.tablas.get(enUso.TablaExiste(Tabla2));
		String tipo2 = tab2.columnas.get(tab2.ColumnaExiste(Columna2)).tipo.toUpperCase();
		
		if((tipo1.toUpperCase().equals("INT") && tipo2.toUpperCase().equals("FLOAT")) ||  (tipo2.toUpperCase().equals("INT") && tipo1.toUpperCase().equals("FLOAT")) )
			return true;
		
		
		if(!tipo1.equals(tipo2)){
			salidaTexto +="Error. La restriccion no puede ser hecha por tipos que no son iguales. " +tipo1 +" - " + tipo2;
			return false;
		}
		return true;
	}
	
	 // Sets y Gets.
	public String getSalidaTexto() {
		return salidaTexto;
	}


	public void setSalidaTexto(String salidaTexto) {
		this.salidaTexto = salidaTexto;
	}

	
	
	
	
	 	
}

