package BackEnd;

import org.eclipse.swt.graphics.Image;

public class Cliente {
	//variables
	private int id;
	private String nombre;
	private String pais;
	private String email;
	private String telefono;
	//private String
	private String nit;
	private String direccion;
	private Image foto;
	
	//Constructors
	public Cliente(){
		
	}
	public Cliente(int idDado, String nombreDado, 
			String paisDado,String emailDado, 
			String telefonoDado, String no_tarjeta,
			String twitterUser, String nitDado, 
			String direccionDada,Image fotoDado,
			String fechaDecreacionDada, int id_Tienda,
			int limiteCredito, int idpais,
			int id_Impuestos, int id_bodega){
		id = idDado;
		nombre = nombreDado;
		pais = paisDado;
		email = emailDado;
		telefono = telefonoDado;

		nit = nitDado;
		direccion = direccionDada;
		foto = fotoDado;
	}
	
	/** 
	 * GETTERS 
	 * **/
	public int getID(){
		return id;
	}
	public String getNombre(){
		return nombre;
	}
	public String getPais(){
		return pais;
	}
	public String getEmail(){
		return email;
	}
	public String getTelefono(){
		return telefono;
	}
	public String getNit(){
		return nit;
	}
	public String getDireccion(){
		return direccion;
	}
	public Image getFoto(){
		return foto;
	}
	/** 
	 * SETTERS 
	 * **/
	public void setID(int newId){
		id = newId;
		// true;
	}
	public void setNombre(String newValue){
		nombre = newValue;
		// nombre;
	}
	public void setPais(String newValue){
		pais = newValue;
		// pais;
	}
	public void setEmail(String newValue){
		email = newValue;
		// email;
	}
	public void setTelefono(String newValue){
		telefono = newValue;
		// telefono;
	}
	public void setNit(String newValue){
		nit = newValue;
		// nit;
	}
	public void setDireccion(String newValue){
		direccion = newValue;
		// direccion;
	}
	public void setFoto(Image newValue){
		foto = newValue;
		// foto;
	}
}
