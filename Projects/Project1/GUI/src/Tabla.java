/*
 * Juan Carlos Tapia 14133
 * Juan Luis Garcia 14189
 * Olga Cobaquil  13020
 * 
 * Proyecto 1. Base de datos.
 * 
 * Clase: Tabla.java
 * 
 * Clase que se encarga de guardar la informacion de una tabla en una base de datos.
 * */

import java.util.ArrayList;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

public class Tabla {
	
	// Atributos
	String nombre;  // Nombre de la tabla
	ArrayList<Columna> columnas;   // Columnas de la tablas
	ArrayList<PrimaryKey> pk;    // Llaves primarias de la tabla
	ArrayList<ForeignKey> fk;   // Llaves foraneas de la tabla
	ArrayList<Check> checks;   // Checks de la tabla
	
	ArrayList<ArrayList<String>> registros ;  // Registros de la tabla
	
	// Constructor
	public Tabla(String nombre) {
		super();
		this.nombre = nombre;
		columnas = new ArrayList<Columna>();
		pk = new ArrayList<PrimaryKey>();
		fk = new ArrayList<ForeignKey>();
		checks = new ArrayList<Check>();
		registros = new  ArrayList<ArrayList<String>>();
	}
	
	
	// Metodo que recibe la informacion de una columna, y la anade si es valida.
	public boolean addColumna(String nombre, String tipo, int tamano){
		
		Interfaz.verbose+=" Revisando que la columna "+ nombre +" no exista  \n";
		
		// Se revisa que la columna no exista ya.
		if(ColumnaExiste(nombre)==-1){
			
			columnas.add(new Columna(nombre, tipo.toUpperCase(), tamano));
			
			// Se pone NULL en cada registro para que la nueva columna no tenga datos vacios.
			for(int i=0;i<registros.size();i++){
				
				registros.get(i).add("NULL");
				
			}
			Interfaz.verbose+=" Columna "+nombre+" creada \n";
			return true;
		}else{
			Interfaz.verbose+=" No se pudo crear la columna "+ nombre +" \n";
			return false;
		}
		
	}
	
	
	// Metodo que recibe la informacion de una llave primaria, y la anade si es valida.
	public boolean addPK(Node key){
		
		
		String nombre = key.getHijos().get(0).getData();
		Interfaz.verbose+=" Revisando que las llaves de la Primary Key "+nombre+" sean validas \n";
		PrimaryKey newPK = new PrimaryKey(nombre);
	//	System.out.println(x);
		String llaves = "";
		
		// Se revisan que las columnas si existan dentro de la tabla.
		for(int i=0;i<key.getHijos().get(4).getHijos().size();i=i+2){
			String actual = key.getHijos().get(4).getHijos().get(i).getData();
			if(ColumnaExiste(actual)==-1){
				Interfaz.verbose+= "	Las llaves de la Primary Key "+nombre+" no existen";
				return false;
			}else{
				llaves+=actual + " , ";
				
			}
				
		}
		newPK.setLlave(llaves);
		Interfaz.verbose+=" Llave primaria "+nombre+" creada \n";
		pk.add(newPK);
		return true;
	}
	
	// Metodo que recibe la informacion de una llave foranea, y la anade a la tabla.
	public int addFK(Node key){
		
		String nombreFK = key.getHijos().get(0).getHijos().get(0).getData(); 
		String Col = key.getHijos().get(0).getHijos().get(4).getData(); 
		String TabRef = key.getHijos().get(0).getHijos().get(7).getData();
		String ColRef = key.getHijos().get(0).getHijos().get(9).getData();
		
		ForeignKey newFK = new ForeignKey(nombreFK, Col, TabRef, ColRef);
		
		Interfaz.verbose+=" Llave foranea "+nombreFK+" creada \n";
		fk.add(newFK);
		return 0;
	}
	
	
	// Metodo que recibe la informacion de una CHECK, y lo anade a la tabla si es valido.
	
	public boolean addChk(String nombre,Node key){
		
		
		Check newChk = new Check(nombre);
		
		newChk.setExpresion(Expresiones.hacerExprecion(key.getHijos().get(0)));
		
		if(Expresiones.revisarTiposChk(this, newChk,key.getHijos().get(0)).equals("Ok")){
			
			
			Interfaz.verbose+="	Check "+nombre+" creado \n";
			
			checks.add(newChk);
			
			
		}else
		{
			return false;
		}
		
		
		
		return true;
		
		
		
	}
	
	
	
	
	
	// Metodo que devuelve la posicion de una columna dentro de la tabla, y -1 si no existe.
	public int ColumnaExiste(String col){
		for(int i=0;i<columnas.size();i++){
			if(columnas.get(i).nombre.equals(col)){
				Interfaz.verbose+="	Si existe la columna "+ col+"\n";
				return i;
			}
		}
		Interfaz.verbose+="	No existe la columna "+ col+"\n";
		
		return -1;
	}
	
	
	
	// Metodo que devuelve la posicion de una llave primaria dentro de la tabla, y -1 si no existe.
	public int PKExiste(String llave){
		for(int i=0;i<pk.size();i++){
			if(pk.get(i).nombre.equals(llave)){
				Interfaz.verbose+="	Si existe la PK "+ llave+"\n";
				return i;
			}
		}
		Interfaz.verbose+="	No existe la PK "+ llave+"\n";
		return -1;
	}
	
	// Metodo que devuelve la posicion de una llave foranea dentro de la tabla, y -1 si no existe.
	
	public int FKExiste(String llave){
		
		for(int i=0;i<fk.size();i++){
			if(fk.get(i).nombre.equals(llave)){
				Interfaz.verbose+="	Si existe la FK "+ llave+"\n";
				return i;
			}
		}
		Interfaz.verbose+="	No existe la FK "+ llave+"\n";
		return -1;
	}

	
	// Metodo que devuelve la posicion de un check dentro de la tabla, y -1 si no existe.
	
	public int chkExiste(String llave){
		for(int i=0;i<checks.size();i++){
			if(checks.get(i).nombre.equals(llave)){
				Interfaz.verbose+="	Si existe el Check "+ llave+"\n";
				return i;
			}
		}
		Interfaz.verbose+="	No existe el Check "+ llave+"\n";
		return -1;
	}
	
	
	// Metodo que recibe el nombre de una columna, y la borra de la tabla, incluyendo las restricciones que la involucren.	
	public void BorrarColumna(String columna){
		Interfaz.verbose+="	Eliminando la columna "+nombre+"\n";
		
		for(int i=0;i<columnas.size();i++){
			if(columnas.get(i).nombre.equals(columna)){
				columnas.remove(i);
				i--;
			}
		}
		Interfaz.verbose+="	Borrando Llaves y Check ";
		for(int i=0;i<fk.size();i++){
			if(fk.get(i).llaveActual.equals(columna)){
				fk.remove(i);
				i--;
			}
		}
		
		for(int i=0;i<pk.size();i++){
			if(pk.get(i).llave.equals(columna)){
				pk.remove(i);
				i--;
			}
		}
		
		
		for(int i=0;i<checks.size();i++){
			if(checks.get(i).variables.contains(columna)){
				checks.remove(i);
				i--;
			}
		}
		
		Interfaz.verbose+="	Borrando registros \n";
		for(int i=0;i<registros.size();i++){
			registros.get(i).remove(ColumnaExiste(columna));
		}
		
		Interfaz.verbose+="	Se elimino la columna "+nombre+"\n";
		
	}
	
	
	// Metodo que devuelve una lista con los nombres de las columnas
	public ArrayList<String> NombresColumnas(){
		ArrayList<String> cols = new ArrayList<String> ();
		
		for(int i=0;i<columnas.size();i++){
			cols.add(columnas.get(i).nombre);
		}
		return cols;
	}
	
	// Metodo que devuelve una lista con los nombres de las columnas, precedidos por el nombre de la tabla.
	
	public ArrayList<String> NombresColumnas2(){
		ArrayList<String> cols = new ArrayList<String> ();
		
		for(int i=0;i<columnas.size();i++){
			cols.add(nombre+" . "+columnas.get(i).nombre);
		}
		return cols;
	}
	
	
	// Metodo que recibe la posicion de un registro y lo borra.
	
	public void BorrarRegistro(int i){
		registros.remove(i);
		Interfaz.verbose+="	Registro borrado \n";
	}
	
	// Sets y Gets
	public String getNombre() {

		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public ArrayList<ArrayList<String>> getRegistros() {
		return registros;
	}

	public void setRegistros(ArrayList<ArrayList<String>> registros) {
		this.registros = registros;
	}
	
	
	
	public ArrayList<Columna> getColumnas() {
		return columnas;
	}

	public void setColumnas(ArrayList<Columna> columnas) {
		this.columnas = columnas;
	}

	public ArrayList<PrimaryKey> getPk() {
		return pk;
	}

	public void setPk(ArrayList<PrimaryKey> pk) {
		this.pk = pk;
	}

	public ArrayList<ForeignKey> getFk() {
		return fk;
	}

	public void setFk(ArrayList<ForeignKey> fk) {
		this.fk = fk;
	}

	public ArrayList<Check> getChecks() {
		return checks;
	}

	public void setChecks(ArrayList<Check> checks) {
		this.checks = checks;
	}

	
	
	
	// Metodo que revisa que se cumplan los checks en un registro del Update.
	public int RevisarChecks(ArrayList<String> registro){
		
		// Se recorre cada check
		for(int i=0;i<checks.size();i++){
			Check chActual = checks.get(i);
			ArrayList<String> valores = new ArrayList<String>();
			
			// Se obtienen los datos del registro.
			for(int j=0;j<chActual.variables.size();j++){
	
				valores.add(registro.get(ColumnaExiste(chActual.variables.get(j))));
			}
			// Se evalua que cumpla. Si no lo hace, devuelve el numero de check que no se cumplio.
			if(!chActual.evaluar(valores)){
				return i;
			}
		}
		return -1;
	}

	
	// Metodo que recibe la nueva informacion del UPDATE y actualiza los registros si se cumplen las restricciones. 
	//Recibe la posicion del registro a actualizar. Los numeros de columnas a actualizar. Los nuevos valores. Y una lista de tablas de la base de datos.

	public String acualizarRegistro(int pos, ArrayList<Integer> cols, ArrayList<String> vals, ArrayList<Tabla> tablas) {
		
		
		// Se revisa que no exista ya la llave primaria.
		Interfaz.verbose+=" Revisando Primary keys \n";
		int verPK  = BuscarPkUPDATE(pos, cols, vals);
		if(verPK!=-1){
			return "Error. Llave duplicada. Ya existe la llave " + columnas.get(cols.get(verPK)).nombre + " = "+ vals.get(verPK);
		}
		
		Interfaz.verbose+=" Revisando Foreign keys \n";
		
		// Se revisa que la llave foranea exista en la tabla referenciada
		int verFK  = BuscarFkUPDATE(pos, cols, vals, tablas);
		if(verFK!=-1){
			int numCol = cols.indexOf(ColumnaExiste(fk.get(verFK).llaveActual));
			
			return "Error. Violacion a llave foranea. No existe la llave " + columnas.get(cols.get(numCol)).nombre + " = "+ vals.get(numCol) + " en la tabla: "+fk.get(verFK).TablaReferencia ;
		}
		
		// Se revisa que no haya ya una referencia al valor que se actualizara.
		String verFK2 = BuscarFkUPDATE2(pos, cols, vals, tablas);
		
		if(!verFK2.equals("Ok")){
			return verFK2;
		}
		
		
		// Se revisa que se cumplan los checks,
		Interfaz.verbose+=" Revisando Checks \n";
		int verCHECK  = BuscarCheckUPDATE(pos, cols, vals);
		if(verCHECK!=-1){

			
			return "Error. El nuevo registro en el UPDATE para la tabla: " +nombre+ " viola la restriccion: "+checks.get(verCHECK).nombre+":  "+ checks.get(verCHECK).expresion;
		}
		
		// Se actualiza cada columna el registro.
		for(int i=0;i<cols.size();i++){
			// Se hace la conversion de FLOAT a INT.
			if(columnas.get(cols.get(i)).tipo.toUpperCase().equals("INT") && vals.get(i).contains(" . ")){
				
				String trunc[] = vals.get(i).split(" . ");
				System.out.println(trunc[0]);
				
				registros.get(pos).set(cols.get(i), trunc[0]);
			}else{
				registros.get(pos).set(cols.get(i), vals.get(i).replace(" . ", "."));
				
			}
			
		}
		
		Interfaz.verbose+=" Registro actualizado \n";
		return "Ok";
	}
	
	// Metodo que revisa que las columnas a actualizar del UPDATE esten dentro de los checks.
	private int BuscarCheckUPDATE(int pos, ArrayList<Integer> cols, ArrayList<String> vals) {
		if(checks.isEmpty()){
			return -1;
		}
		boolean esta = false;
		for(int i=0;i<checks.size();i++){
			for(int j=0;j<cols.size();j++){

				if(checks.get(i).variables.contains(columnas.get(cols.get(j)).nombre)){
					esta = true;
				}
			}
			
		}
		
		if(!esta){
			return -1;
		}
		ArrayList<String> registroActual = (ArrayList<String>) registros.get(pos).clone();
		
		for(int i=0;i<cols.size();i++){
			if(columnas.get(cols.get(i)).tipo.toUpperCase().equals("INT") && vals.get(i).contains(" . ")){
				String trunc[] = vals.get(i).split(" . ");
				
				
				registroActual.set(cols.get(i), trunc[0]);
			}else{
				registroActual.set(cols.get(i), vals.get(i).replace(" . ", "."));
				
			}
			
		}
		
		
		
		return RevisarChecks(registroActual);
	}

	
	// Metodo que revisa que la llave primaria no exista ya cuando se haga el UPDATE.
	private int BuscarPkUPDATE(int pos, ArrayList<Integer> cols, ArrayList<String> vals){

		if(pk.isEmpty()){
			return -1;
		}
			
			
		if(!cols.contains(ColumnaExiste(pk.get(0).llave))){
			return -1;
		}
		
		int numCol = cols.indexOf(ColumnaExiste(pk.get(0).llave));
		for(int i=0;i<registros.size();i++){
			if(i!=pos){
				if(vals.get(numCol).equals(registros.get(i).get(cols.get(numCol)))){
					return numCol;
				}
			}
		}
		return -1;
	}
	
		
	
	
	// Metodo que revisa que la llave foranea exista en la tabla referenciada cuando se haga el UPDATE.
	private int BuscarFkUPDATE(int pos, ArrayList<Integer> cols, ArrayList<String> vals, ArrayList<Tabla> tablas){
		if(fk.isEmpty()){
			return -1;
		}
		int numCol = -1;
		for(int i=0;i<fk.size();i++){
			if(cols.contains(ColumnaExiste(fk.get(i).llaveActual))){
				numCol = cols.indexOf(ColumnaExiste(fk.get(i).llaveActual));
				
				Tabla tablaRef = null;
				for(int j=0;j<tablas.size();j++){
					if (tablas.get(j).nombre.equals(fk.get(i).TablaReferencia)){
						tablaRef = tablas.get(j);
						break;
					}
				}	
				boolean Encontrada = false;
				for(int j=0;j<tablaRef.registros.size();j++){
					if(vals.get(numCol).equals(tablaRef.registros.get(j).get(tablaRef.ColumnaExiste(fk.get(i).ColumnaReferencia)  ))){
						Encontrada = true;
					}
				}
				
				if(!Encontrada){
					return i;
				}
				
			}
		}
		
		return -1;
	}
	
	
	// Metodo que revisa que no haya una referencia a la llave que se modificara con el UPDATE.	
	
	private String BuscarFkUPDATE2(int pos, ArrayList<Integer> cols, ArrayList<String> vals, ArrayList<Tabla> tablas){
		
		
		ArrayList<String> colsNoms = new ArrayList<String>();
		for(int i=0;i<cols.size();i++){
			colsNoms.add(columnas.get(cols.get(i)).nombre);
		}
		
		for(int i=0;i<tablas.size();i++){
			for(int j=0;j<tablas.get(i).fk.size();j++){
				// Se encontro una tabla que hace referencia a esta
				if(tablas.get(i).fk.get(j).TablaReferencia.equals(nombre)){
					if(colsNoms.contains(tablas.get(i).fk.get(j).ColumnaReferencia)){
						int numColumna = colsNoms.indexOf(tablas.get(i).fk.get(j).ColumnaReferencia);
						String valorActual = registros.get(pos).get(cols.get(numColumna));
						
						
						// Se busca que haya una referencia a ese valor
						int NumColumnaEnTablaRef = tablas.get(i).ColumnaExiste(tablas.get(i).fk.get(j).llaveActual);
						for(int k=0;k<tablas.get(i).registros.size();k++){
							
							String valorEnTabRef = tablas.get(i).registros.get(k).get(NumColumnaEnTablaRef);
							//System.out.println(valorEnTabRef);
							if(valorActual.equals(valorEnTabRef)){
								return "Error. Violacion a llave foranea " + tablas.get(i).fk.get(j).nombre + " en la tabla " +tablas.get(i).nombre + " no se puede actualizar el valor " + colsNoms.get(numColumna) + " = " +valorActual + " por que en la tabla "+tablas.get(i).fk.get(j).nombre + " hay una referencia a ese valor \n";
						
							}
						}
						
					}
					
				}
			}
		}
			
		return "Ok";
	}
	
	
}
