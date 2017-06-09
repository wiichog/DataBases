
/*
 * Juan Carlos Tapia 14133
 * Juan Luis Garcia 14189
 * Olga Cobaquil  13020
 * 
 * Proyecto 1. Base de datos.
 * 
 * Clase: Columna.java
 * 
 * Clase que guarda la informacion de una columna de una tabla.
 * */

public class Columna {
	
	// Atributo
	String nombre;  // Nombre de la columna
	String tipo;  // Tipo de la columna
	int tamano;   // Tamano de la columna para cuando se es del tipo char.
	
	
	// Constructor sin tamano para el char
	public Columna(String nombre, String tipo) {
		
		this.nombre = nombre;
		this.tipo = tipo;

	}
	
	// Constructor con tamano para el char
	public Columna(String nombre, String tipo, int tamano) {
		
		this.nombre = nombre;
		this.tipo = tipo;
		this.tamano = tamano;
	}


	
	// Sets y gets.
	public String getNombre() {
		return nombre;
	}


	public void setNombre(String nombre) {
		this.nombre = nombre;
	}


	public String getTipo() {
		return tipo;
	}


	public void setTipo(String tipo) {
		this.tipo = tipo;
	}


	public int getTamaño() {
		return tamano;
	}


	public void setTamaño(int tamano) {
		this.tamano = tamano;
	}
	
	
}
