/*
 * Juan Carlos Tapia 14133
 * Juan Luis Garcia 14189
 * Olga Cobaquil  13020
 * 
 * Proyecto 1. Base de datos.
 * 
 * Clase: Node.java
 * 
 * Clase que se encarga de guardar un nodo hecho de forma personal, para un arbol
 * */

import java.util.ArrayList;

public class Node {
	
	// Atributos
	
		private String data  = "";   // Data del nodo
       
        private ArrayList<Node>  hijos = new ArrayList<Node>();  // Hijos del nodo
       

        
        
       
        
        
      // Constructor
        public Node(String data) {
			super();
			// Se reemplaza # por un espacio vacio.
			if(data.contains("#")){
				this.data = data.replace("#", " ");
			}else{
				this.data = data;
			}
			
		}
        
       

        // Sets y Gets
		
		public String getData() {
			return data;
		}



		public void setData(String data) {
			this.data = data;
		}



		public ArrayList<Node> getHijos() {
			return hijos;
		}



		public void setHijos(ArrayList<Node> hijos) {
			this.hijos = hijos;
		}
        
        public void addHijos(Node hijo){
        	
        	hijos.add(hijo);
        }




       
		public String SumaHijos(){
			String TodosHijos = "";
			for(int j=1;j<(hijos.size()-1);j++){
				TodosHijos+= hijos.get(j).getData();
			}
			return TodosHijos;
		}
		
}