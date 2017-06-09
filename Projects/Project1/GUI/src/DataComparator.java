/*
 * Juan Carlos Tapia 14133
 * Juan Luis Garcia 14189
 * Olga Cobaquil  13020
 * 
 * Proyecto 1. Base de datos.
 * 
 * Clase: DataComparator.java
 * 
 * Clase que implementa la clase Comparator para poder ordenar los datos del Select.
 * */


import java.util.ArrayList;
import java.util.Comparator;


public class DataComparator implements Comparator<ArrayList<String>>
{    
	
	 ArrayList<Integer> cols;  // Lista con las posiciones, dentro de la tabla de las columnas con las que se hara el ORDER.
	 ArrayList<Boolean> DESC;  // Lista de si los ordenamientos seran ASC o DESC.
    

	 
	 // Constructor
	public DataComparator(ArrayList<Integer> cols, ArrayList<Boolean> dESC) {
		
		this.cols = cols;
		DESC = dESC;
	}

// Metodo que compara dos registros.
	@Override
	public int compare(ArrayList<String> o1, ArrayList<String> o2) {
		
		int comp =0;
		
		// Se revisan cada columna por la que se hara el orden. 
		for(int i=0;i<cols.size();i++ ){
			
			
				// Estas dos variables agarran los datos de los registros que se compararan
				String a1 = o1.get(cols.get(i));
				String a2 = o2.get(cols.get(i));
				// Se revisa si es descendente o ascendente
				if(DESC.get(i)){
					// comp indica cual es mayor.
		        	 comp = a2.compareTo(a1);	
		        	 
		        	 // Se asegura que el NULL tenga un valor mayor.
		        	 if(a2.equals("null")){
		        		 if(a1.equals("null")){
			        		 comp = 0;
			        	 }else{
			        		 comp = 1;
			        	 }
		        	 }
				}

		        else{
		        	comp = a1.compareTo(a2);
		        	 if(a1.equals("null")){
		        		 if(a2.equals("null")){
			        		 comp = 0;
			        	 }else{
			        		 comp = 1;
			        	 }
		        	 }
		        }
				
				// Cuando comp es 0, ambos datos son iguales. Asi que se pasa a revisar el siguiente valor de ordenamiento, si es que existe.
		        if(comp!=0)
		        	return comp;
			}
	        
	        
	        
			
	//	}
		
         return comp;
	}

	
 }


