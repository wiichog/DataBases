
/*
 * Juan Carlos Tapia 14133
 * Juan Luis Garcia 14189
 * Olga Cobaquil  13020
 * 
 * Proyecto 1. Base de datos.
 * 
 * Clase: Database.java
 * 
 * Clase que guarda la informacion de una Base de datos.
 * */


import java.util.ArrayList;

public class Database {
	
	// Atributos
	String nombre;  // Nombre de la base de datos.
	 ArrayList<Tabla> tablas;   // Lista de tablas de la base de datos.
	int cantTablas;  // Cantidad de tablas de la base de datos.
	
	
	
	// Constructor
	public Database(String nombre) {
		super();
		this.nombre = nombre;
		cantTablas = 0;
		tablas = new ArrayList<Tabla>();
	}
	
	// Sets y Gets
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public int getCantTablas() {
		return cantTablas;
	}
	public void setCantTablas(int cantTablas) {
		this.cantTablas = cantTablas;
	}
	
	public void addTabla(Tabla tabla){
		tablas.add(tabla);
		cantTablas++;
	}
	
	
	// Metodo que devuelve cuantos registros hay en una base de datos.
	public int CantRegistros(){
		int contador=0;
		for(int i=0;i<tablas.size();i++){
			contador+=tablas.get(i).registros.size();
		}
		return contador;
	}
	
	
	// Metodo que devuelve la posicion de una tabla en la base de datos, y -1 si no existe.
	public int TablaExiste(String tab){
		for(int i=0;i<tablas.size();i++){
			if(tablas.get(i).nombre.equals(tab)){
				return i;
			}
		}
		return -1;
	}
	
	
	
	
}
