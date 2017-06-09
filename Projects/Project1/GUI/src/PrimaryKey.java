/*
 * Juan Carlos Tapia 14133
 * Juan Luis Garcia 14189
 * Olga Cobaquil  13020
 * 
 * Proyecto 1. Base de datos.
 * 
 * Clase: PrimaryKey.java
 * 
 * Clase que se encarga de guardar la informacion de una llave primaria
 * */

import java.util.ArrayList;

public class PrimaryKey {
	
	// Atributos
	String nombre;  // Nombre de la restriccion
	String llave;   // columna de la tabla (cuando solo hay una)
	ArrayList<String> llaves; // columnas de la tabla (cuando hay mas de una)
	
	
	// Constructor con solo nombre
	public PrimaryKey(String nombre) {
		
		this.nombre = nombre;
		this.llaves = new ArrayList<String>();
		
	}
	// constructor con nombre y llave.
	public PrimaryKey(String nombre, String llave) {
		
		this.nombre = nombre;
		
		this.llaves = new ArrayList<String>();
		
		setLlave(llave);
	}
	
	
	
	// Sets y Gets

	public String getLlave() {
		return llave;
	}

	public void setLlave(String llave) {
		if(llave.contains(" , ")){
			String[] keys = llave.split(" , ");
			this.llave = keys[0];

			for(int i=0;i<keys.length;i++){
				llaves.add(keys[i]);
			}
		}else{
			this.llave = llave;
			llaves.add(llave);

		}
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public ArrayList<String> getLlaves() {
		return llaves;
	}
	public void setLlaves(ArrayList<String> llaves) {
		this.llaves = llaves;
	}
	
	
	
	
	
	
	
	
}
