/*
 * Juan Carlos Tapia 14133
 * Juan Luis Garcia 14189
 * Olga Cobaquil  13020
 * 
 * Proyecto 1. Base de datos.
 * 
 * Clase: Expresiones.java
 * 
 * Clase que se encarga los analisis de las expresiones booleanas.
 * */


import java.util.ArrayList;

public class Expresiones {
	
	
	// Recibe un nodo de la produccion exp, analiza su contenido, y devuelve una expresion booleana. 
	public static String hacerExprecion(Node exp){
		if (exp.getData().equals("Cond") && exp.getHijos().size()==1){
			return hacerExprecion(exp.getHijos().get(0));
		}
		
		if (exp.getData().equals("Cond") && exp.getHijos().size()!=1){
			String s = "";
			for(int i=0;i<exp.getHijos().size();i++){
				if(i%2 ==0){
					s += hacerExprecion(exp.getHijos().get(i))+" ";
				}else{
					s += exp.getHijos().get(i).getHijos().get(0).getData()+ " ";
				}
					
			}
			
			
			return s;
		}
		
		
		if (exp.getData().equals("Rel") && exp.getHijos().size()==3){
			String s = "";
			
			s += hacerExprecion(exp.getHijos().get(0)) + " ";
			s += exp.getHijos().get(1).getHijos().get(0).getData()+ " ";
			s += hacerExprecion(exp.getHijos().get(2))+ " ";
			
			
			return s;
		}
		
		if (exp.getData().equals("Rel") && exp.getHijos().size()==1){
			return hacerExprecion(exp.getHijos().get(0));
		}
		
		if (exp.getData().equals("NotOp")){
			if(exp.getHijos().get(0).getData().equals("NOT")){
				return "NOT ("+hacerExprecion(exp.getHijos().get(2))+")";
			}else{
				return "("+hacerExprecion(exp.getHijos().get(1))+")";	
			}
		}
		
		if (exp.getData().equals("Operacion") ){
			return hacerExprecion(exp.getHijos().get(0));
		}
		
		if (exp.getData().equals("Literal") ){
			return hacerExprecion(exp.getHijos().get(0));
		}
		
		
		if (exp.getData().equals("Int_literal") ){
			return exp.getHijos().get(0).getData();
		}
		
		if (exp.getData().equals("Float_literal") ){
			return exp.getHijos().get(0).getData()+"."+exp.getHijos().get(2).getData();
		}
		if (exp.getData().equals("Char_literal") ){
			return "\'"+exp.getHijos().get(1).getData()+"\'";
		}
		
		if (exp.getData().equals("Date_literal") ){
			return "\'"+exp.getHijos().get(1).getData()+"-"+exp.getHijos().get(3).getData()+"-"+exp.getHijos().get(5).getData()+"\'";
		}
		
		if (exp.getData().equals("NULL") ){
			return "null";
		}
		
		if (exp.getData().equals("Location") ){
			if(exp.getHijos().size()==1)
				return exp.getHijos().get(0).getData();
			else
				return exp.getHijos().get(0).getData()+"___"+exp.getHijos().get(2).getData();
		}
		
		return " ";
	
	}

	
	// Revisa que cuando se declare un CHECK, los tipos coincidan en la expresion. Tambien se van guardando las variables encontradas dentro del CHECK
	public static String revisarTiposChk(Tabla tabla, Check chk, Node exp){
		if (exp.getData().equals("Cond") && exp.getHijos().size()==1){
			return revisarTiposChk(tabla, chk, exp.getHijos().get(0));
		}
		
		if (exp.getData().equals("Cond") && exp.getHijos().size()!=1){
			boolean b = true; 
			for(int i=0;i<exp.getHijos().size();i++){
				if(i%2 ==0){
					b = b && revisarTiposChk(tabla, chk,exp.getHijos().get(i)).equals("Ok");
				}
					
			}
			
			
			if(b){
				return "Ok";
			}
			return "Error";
		}
		
		
		if (exp.getData().equals("Rel") && exp.getHijos().size()==3){
			boolean b = true; 
			String left = revisarTiposChk(tabla, chk,exp.getHijos().get(0));
			String right = revisarTiposChk(tabla, chk,exp.getHijos().get(2));
			b = left.equals(right);
			if(left.equals("FLOAT")   &&  right.equals("INT")){
				b = true;
			}
			
			if(right.equals("NULL")){
				b=true;
			}
			
			if(b){
				return "Ok";
			}
			return "Error";
		}
		
		if (exp.getData().equals("Rel") && exp.getHijos().size()==1){
			return revisarTiposChk(tabla, chk, exp.getHijos().get(0));
		}
		
		if (exp.getData().equals("NotOp")){
			if(exp.getHijos().get(0).getData().equals("NOT")){
				return revisarTiposChk(tabla, chk,exp.getHijos().get(2));
			}else{
				return revisarTiposChk(tabla, chk,exp.getHijos().get(1));
			}
		}
		
		if (exp.getData().equals("Operacion") ){
			return revisarTiposChk(tabla, chk, exp.getHijos().get(0));
		}
		
		if (exp.getData().equals("Literal") ){
			return revisarTiposChk(tabla, chk, exp.getHijos().get(0));
		}
		
		if (exp.getData().equals("Location") ){
			String nombre = exp.getHijos().get(0).getData();
			int pos = tabla.ColumnaExiste(nombre);
			if (pos ==-1){
				return "Error";
			}
			
			if(!chk.variables.contains(nombre))
				chk.addVariable(nombre);
			//System.out.println(chk.variables.size());
			return tabla.columnas.get(pos).tipo.toUpperCase();
		}
		
		if (exp.getData().equals("Int_literal") ){
			return "INT";
		}
		
		if (exp.getData().equals("Float_literal") ){
			return "FLOAT";
		}
		if (exp.getData().equals("Char_literal") ){
			return "CHAR";
		}
		
		if (exp.getData().equals("Date_literal") ){
			return "DATE";
		}
		
		if (exp.getData().equals("NULL") ){
			return "NULL";
		}
	
		return "";
	}

	
	// Traduce la expresion booleana a algo que Java entienda. Cambia "AND" por "&&", "OR" por "||" y "NOT" por "!" 
	public static String traducir (String expresion){
		
		return expresion.replace("AND", "&&").replace("OR", "||").replace("NOT", "!").replaceAll("=", "==").replace("<==", "<=").replace(">==", ">=").replace("<>", "!=").replace(" ", "");
	}
	
	
	// Revisa que cuando se declare un WHERE, los tipos coincidan en la expresion.
	public static String revisarTiposWhere(Tabla tabla, ArrayList<String> cols, Node exp){
		if (exp.getData().equals("Cond") && exp.getHijos().size()==1){
			return revisarTiposWhere(tabla, cols, exp.getHijos().get(0));
		}
		
		if (exp.getData().equals("Cond") && exp.getHijos().size()!=1){
			boolean b = true; 
			for(int i=0;i<exp.getHijos().size();i++){
				if(i%2 ==0){
					b = b && revisarTiposWhere(tabla, cols,exp.getHijos().get(i)).equals("Ok");
				}
					
			}
			
			
			if(b){
				return "Ok";
			}
			return "Error";
		}
		
		
		if (exp.getData().equals("Rel") && exp.getHijos().size()==3){
			boolean b = true; 
			String left = revisarTiposWhere(tabla, cols,exp.getHijos().get(0));
			String right = revisarTiposWhere(tabla, cols,exp.getHijos().get(2));
			b = left.equals(right);
			if(left.equals("FLOAT")   &&  right.equals("INT")){
				b = true;
			}
			if(right.equals("NULL")){
				b=true;
			}
			
			if(b){
				return "Ok";
			}
			return "Error";
		}
		
		if (exp.getData().equals("Rel") && exp.getHijos().size()==1){
			return revisarTiposWhere(tabla, cols, exp.getHijos().get(0));
		}
		
		if (exp.getData().equals("NotOp")){
			if(exp.getHijos().get(0).getData().equals("NOT")){
				return revisarTiposWhere(tabla, cols,exp.getHijos().get(2));
			}else{
				return revisarTiposWhere(tabla, cols,exp.getHijos().get(1));
			}
		}
		
		if (exp.getData().equals("Operacion") ){
			return revisarTiposWhere(tabla, cols, exp.getHijos().get(0));
		}
		
		if (exp.getData().equals("Literal") ){
			return revisarTiposWhere(tabla, cols, exp.getHijos().get(0));
		}
		
		if (exp.getData().equals("Location") ){
			String nombre = exp.getHijos().get(0).getData();
			int pos = tabla.ColumnaExiste(nombre);
			if (pos ==-1){
				return "Error";
			}
			cols.add(nombre);
			
			return tabla.columnas.get(pos).tipo.toUpperCase();
		}
		
		if (exp.getData().equals("Int_literal") ){
			return "INT";
		}
		
		if (exp.getData().equals("Float_literal") ){
			return "FLOAT";
		}
		if (exp.getData().equals("Char_literal") ){
			return "CHAR";
		}
		
		if (exp.getData().equals("Date_literal") ){
			return "DATE";
		}
		if (exp.getData().equals("Date_literal") ){
			return "NULL";
		}
	
		return "";
	}
	

	public static String revisarTiposWhere(ArrayList<Tabla> tabla, ArrayList<String> cols, Node exp){
		if (exp.getData().equals("Cond") && exp.getHijos().size()==1){
			return revisarTiposWhere(tabla, cols, exp.getHijos().get(0));
		}
		
		if (exp.getData().equals("Cond") && exp.getHijos().size()!=1){
			boolean b = true; 
			for(int i=0;i<exp.getHijos().size();i++){
				if(i%2 ==0){
					b = b && revisarTiposWhere(tabla, cols,exp.getHijos().get(i)).equals("Ok");
				}
					
			}
			
			
			if(b){
				return "Ok";
			}
			return "Error";
		}
		
		
		if (exp.getData().equals("Rel") && exp.getHijos().size()==3){
			boolean b = true; 
			String left = revisarTiposWhere(tabla, cols,exp.getHijos().get(0));
			String right = revisarTiposWhere(tabla, cols,exp.getHijos().get(2));
			b = left.equals(right);
			if(left.equals("FLOAT")   &&  right.equals("INT")){
				b = true;
			}
			

			if(right.equals("NULL")){
				b=true;
			}
			
			if(b){
				return "Ok";
			}
			return "Error";
		}
		
		if (exp.getData().equals("Rel") && exp.getHijos().size()==1){
			return revisarTiposWhere(tabla, cols, exp.getHijos().get(0));
		}
		
		if (exp.getData().equals("NotOp")){
			if(exp.getHijos().get(0).getData().equals("NOT")){
				return revisarTiposWhere(tabla, cols,exp.getHijos().get(2));
			}else{
				return revisarTiposWhere(tabla, cols,exp.getHijos().get(1));
			}
		}
		
		if (exp.getData().equals("Operacion") ){
			return revisarTiposWhere(tabla, cols, exp.getHijos().get(0));
		}
		
		if (exp.getData().equals("Literal") ){
			return revisarTiposWhere(tabla, cols, exp.getHijos().get(0));
		}
		
		if (exp.getData().equals("Location") ){
			String nombre = "";
			if(exp.getHijos().size()==1){
				
				 nombre = exp.getHijos().get(0).getData();	
				 int encontrado = 0;
				 int tablaEncontrado = 0;
				 int posEncontrado =0;
				 for(int i=0;i<tabla.size();i++ ){
						
						int pos = tabla.get(i).ColumnaExiste(nombre);
						if (pos !=-1){
							encontrado++;
							posEncontrado = pos;
							tablaEncontrado = i;
							
						}
					}
				if(encontrado==1){ 
					cols.add(nombre);
					return tabla.get(tablaEncontrado).columnas.get(posEncontrado).tipo.toUpperCase();
				}
			}else{
				 nombre = exp.getHijos().get(0).getData()+" . "+exp.getHijos().get(2).getData();
				 String[] info = nombre.split(" . ");
				
				 for(int i=0;i<tabla.size();i++ ){
						
					 	if(tabla.get(i).nombre.equals(info[0])){
							int pos = tabla.get(i).ColumnaExiste(info[1]);
							
							if (pos !=-1){
								
								cols.add(nombre);
								return tabla.get(i).columnas.get(pos).tipo.toUpperCase();
							}
					 	}
					}
			}
			
			
			return "Error";
			
			
		}
		
		if (exp.getData().equals("Int_literal") ){
			return "INT";
		}
		
		if (exp.getData().equals("Float_literal") ){
			return "FLOAT";
		}
		if (exp.getData().equals("Char_literal") ){
			return "CHAR";
		}
		
		if (exp.getData().equals("Date_literal") ){
			return "DATE";
		}
		
		if (exp.getData().equals("NULL") ){
			return "NULL";
		}
	
		return "";
	}

	
}
