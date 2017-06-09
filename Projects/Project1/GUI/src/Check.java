/*
 * Juan Carlos Tapia 14133
 * Juan Luis Garcia 14189
 * Olga Cobaquil  13020
 * 
 * Proyecto 1. Base de datos.
 * 
 * Clase: Check.java
 * 
 * Clase que guarda la informacion de una restriccion del tipo Check.
 * */

import java.util.ArrayList;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

public class Check {
	// Atributos
	public String nombre;   // nombre del check
	public ArrayList<String> variables;  // lista con los nombres de las columnas que estan dentro del check
	public String expresion;   // Cadena con la expresion logica en sintaxis de SQL
	public String expresionTraducida;   // Cadena con la expresion logica en sintaxis de Java.
	
	
	
	// Constructor con solo nombre
	public Check (String nombre){
		variables = new ArrayList<String> ();
		this.nombre = nombre;
	}
	
	// Constructor con todos los datos.
	public Check (String nombre, String variables, String expresion){
		this.variables = new ArrayList<String> ();
		this.nombre = nombre;
		String[] vars = variables.split(",");  
		for(int i=0;i<vars.length;i++){
			this.variables.add(vars[i]);
		}
			
		setExpresion(expresion);
	}

	// Este metodo evalua que se cumpla el CHECK. Se le manda un arreglo de Strings con los valores de las columnas requeridas, en orden
	public boolean evaluar (ArrayList<String> valores){
		if(valores.isEmpty())
			return true;
		
		if(valores.contains("NULL"))
			return true;
		
		
		// engine permite que se ingresen expresiones para evaluarlas.
		ScriptEngine engine = new ScriptEngineManager().getEngineByExtension("js");
		
		
		// Se ingresan los valores recibidos como parametros.
		for(int i=0;i<valores.size();i++){
			
			engine.put(variables.get(i), valores.get(i));
			
		}
		
		// Se devuelve el resultado de la evaluacion.
		try {
			return (boolean) engine.eval(expresionTraducida);
		} catch (ScriptException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

	
	
	// Sets y Gets.
	public String getNombre() {
		return nombre;
	}


	public void setNombre(String nombre) {
		this.nombre = nombre;
	}


	public ArrayList<String> getVariables() {
		return variables;
	}


	public void setVariables(ArrayList<String> variables) {
		this.variables = variables;
	}

	
	
	public void addVariable (String var){
		variables.add(var);
	}
	

	public String getExpresion() {
		return expresion;
	}


	public void setExpresion(String expresion) {
		
		this.expresion = expresion;
		expresionTraducida = Expresiones.traducir(expresion);
				
	}


	public String getExpresionTraducida() {
		return expresionTraducida;
	}


	public void setExpresionTraducida(String expresionTraducida) {
		this.expresionTraducida = expresionTraducida;
	}
	
	
	
	
}
