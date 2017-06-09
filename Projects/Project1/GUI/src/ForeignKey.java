/*
 * Juan Carlos Tapia 14133
 * Juan Luis Garcia 14189
 * Olga Cobaquil  13020
 * 
 * Proyecto 1. Base de datos.
 * 
 * Clase: ForeignKey.java
 * 
 * Clase que se encarga de guardar la informacion de una llave foranea
 * */


public class ForeignKey {
	
	// Atributos
	String nombre;   // Nombre de la restriccion.
	String llaveActual;   // Nombre de la columna dentro de la tabla que tiene la restriccion.
	String TablaReferencia;  // Nombre de la tabla a la que se hace referencia.
	String ColumnaReferencia;  // Nombre de la columna a la que se hace referencia.
	
	
	// Constructor
	public ForeignKey(String nombre, String llaveActual, String tablaReferencia, String columnaReferencia) {
		this.nombre = nombre;
		this.llaveActual = llaveActual;
		TablaReferencia = tablaReferencia;
		ColumnaReferencia = columnaReferencia;
	}

	
	// Sets y GEts
	public String getTablaReferencia() {
		return TablaReferencia;
	}

	public void setTablaReferencia(String tablaReferencia) {
		TablaReferencia = tablaReferencia;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getLlaveActual() {
		return llaveActual;
	}

	public void setLlaveActual(String llaveActual) {
		this.llaveActual = llaveActual;
	}

	public String getColumnaReferencia() {
		return ColumnaReferencia;
	}

	public void setColumnaReferencia(String columnaReferencia) {
		ColumnaReferencia = columnaReferencia;
	}
	
	
	
}
