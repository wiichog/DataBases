package BackEnd;
import java.sql.*;
import org.eclipse.swt.SWT;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.text.SimpleDateFormat;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Label;
import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
import java.util.ArrayList;

public class implementCommands implements CommandsSQL{
	Connection c = null;
	Statement statement = null;
	String sql = null;
	String output = null;

	
	public String[] SELECT(String select, String fromTable) //SOLO FUNCIONA PARA una cosa
	{
		String[] returnArray;
		ArrayList<String> tableCount = new ArrayList<String>();
		try {
			statement = c.createStatement();
			ResultSet rs = statement.executeQuery("SELECT " + select + " FROM "+ fromTable+" ORDER BY id");
			while (rs.next()) {
				tableCount.add(rs.getString(1));
			}
			returnArray = new String[tableCount.size()];
			for (int i = 0; i < tableCount.size(); i++){
				returnArray[i] = tableCount.get(i).toString();
				//System.out.println(tablesToReturn[i].toString()); //DISPLAYS THE CURRENT STUFF
			}
			statement.close();
			return returnArray;

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	public String[] SELECT(String select, String fromTable, String where) //SOLO FUNCIONA PARA una cosa
	{
		String[] returnArray;
		ArrayList<String> tableCount = new ArrayList<String>();
		try {
			statement = c.createStatement();
			ResultSet rs = statement.executeQuery("SELECT " + select + " FROM "+ fromTable +" "+ where + ";");
			while (rs.next()) {
				tableCount.add(rs.getString(1));
			}
			returnArray = new String[tableCount.size()];
			for (int i = 0; i < tableCount.size(); i++){
				returnArray[i] = tableCount.get(i).toString();
				//System.out.println(tablesToReturn[i].toString()); //DISPLAYS THE CURRENT STUFF
			}
			statement.close();
			return returnArray;

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}


	@Override
	public boolean Connect(String username, String password, String db) {
		try {
			//System.out.println(username + "   " + password);
			if (password == null)
				password = "";
			Class.forName("org.postgresql.Driver");
			c = DriverManager
					.getConnection("jdbc:postgresql://localhost:5432/"+db,
							username, password);
		} catch (Exception e) {
			System.err.println( e.getClass().getName()+": "+ e.getMessage() );
			return false;
		}
		System.out.println("connection successful");
		return true;
	}

	@Override
	public String[] GETTABLESOFDATABASE() {
		String[] tablesToReturn;
		ArrayList<String> tableCount = new ArrayList<String>();
		try {
			PreparedStatement s = c
					.prepareStatement("SELECT table_name FROM information_schema.tables"
							+ " WHERE table_schema='public';");
			ResultSet rs = s.executeQuery();
			while (rs.next()) {
				tableCount.add(rs.getString(1));
				//System.out.println(tableCount.toString());
			}
			tablesToReturn = new String[tableCount.size()];
			for (int i = 0; i < tableCount.size(); i++){
				tablesToReturn[i] = tableCount.get(i).toString();
				//System.out.println(tablesToReturn[i].toString()); //DISPLAYS THE CURRENT STUFF
			}
			s.close();
			//c.close();
			return tablesToReturn;

		} catch (SQLException e) {
			System.out.println("Error en GETTABLEOFDATABASE");
			e.printStackTrace();
		}
		System.out.println("Error demostrando las tablas de la base de datos");
		return null;
	}

	public int InsertClient(String Nombre, String Email, String Telefono,String NIT, String Direccion, String Picture, String No_Tarjeta,
			String Twitter_User,int Id_Tienda, float Limite_Credito, int Id_Pais, int Id_Impuestos, int Id_Bodega){
		Connection c = null;
		Statement stmt = null;

		try{
			Class.forName("org.postgresql.Driver");
			c = DriverManager.getConnection(crypt("JTAt953gSQKMitFxut0yP6rsAUoser/SBOxWADMHyUMH3JGXFidrBOt7/FXBaTJ6"),crypt("QGX23Vj4nciMEeuwXLN7lAbMTa4SiB9j"), crypt("liYtV26MRFswwSCCzqVNZA=="));
			c.setAutoCommit(false);
			stmt = c.createStatement();
			Date date = new Date();
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			StandardPBEStringEncryptor s = new StandardPBEStringEncryptor(); 
			s.setPassword("1234567890zxcvbnmasdfghjklqwertyuiopZXCVBNMASDFGHJKLQWERTYUIOP");
			String sql = "INSERT INTO CLIENTE (NOMBRE,EMAIL,TELEFONO,NIT,DIRECCION,PICTURE,NO_TARJETA,TWITTER_USER,FECHA_CREACION,ID_TIENDA,"
					+ "LIMITE_CREDITO, ID_PAIS, ID_IMPUESTOS, ID_BODEGA)"
					+ " VALUES ('"+ Nombre +"','"+ Email +"','"+ Telefono +"','"+ NIT +"','"+ Direccion +"','"+ Picture +"',"
					+ "'"+ s.encrypt(No_Tarjeta) +"','"+ Twitter_User +"','"+ sdf.format(date) +"','"+ Integer.toString(Id_Tienda) +"'"
					+ ",'"+ Float.toString(Limite_Credito) +"','"+ Integer.toString(Id_Pais) +"','"+ Integer.toString(Id_Impuestos) +"'"
					+ ",'"+ Integer.toString(Id_Bodega) +"');";
			stmt.executeUpdate(sql);
			stmt.close();
			c.commit();
			c.close();
			System.out.println("Cliente creado exitosamente");
			return 0;
		}
		catch(Exception e){	
			System.out.println("Error: " + e);
			return 1;
		}

	}

	public int DeleteCliente(String ID){
		Connection c = null;
		Statement stmt = null;
		try{
			Class.forName("org.postgresql.Driver");
			c = DriverManager.getConnection(crypt("JTAt953gSQKMitFxut0yP6rsAUoser/SBOxWADMHyUMH3JGXFidrBOt7/FXBaTJ6"),crypt("QGX23Vj4nciMEeuwXLN7lAbMTa4SiB9j"), crypt("liYtV26MRFswwSCCzqVNZA=="));
			c.setAutoCommit(false);
			stmt = c.createStatement();
			String sql = "DELETE FROM CLIENTE "
					+ " WHERE ID = "+ID+"";
			stmt.executeUpdate(sql);
			stmt.close();
			c.commit();
			c.close();
			return 0;
		}
		catch(Exception e){		
			System.out.println("Error: " + e);
			return 1;
		}
	}

	public void FillData(Text id, Text nombre,Text email,Text telefono, Text NIT,Text tarjeta,Text twitter,
			Text direccion,Text limite, Combo tienda, Combo pais, Combo impuesto, Combo bodega, String idTabla, Label FechaCreacion, String pathImg
			){
		StandardPBEStringEncryptor s = new StandardPBEStringEncryptor(); 
		s.setPassword("1234567890zxcvbnmasdfghjklqwertyuiopZXCVBNMASDFGHJKLQWERTYUIOP");
		Connection c = null;
		Statement stmt = null;
		try {  
			Class.forName("org.postgresql.Driver");
			c = DriverManager.getConnection(crypt("JTAt953gSQKMitFxut0yP6rsAUoser/SBOxWADMHyUMH3JGXFidrBOt7/FXBaTJ6"),crypt("QGX23Vj4nciMEeuwXLN7lAbMTa4SiB9j"), crypt("liYtV26MRFswwSCCzqVNZA=="));
			c.setAutoCommit(false);
			stmt = c.createStatement();
			ResultSet rs = stmt.executeQuery( "SELECT * FROM CLIENTE WHERE ID="+ idTabla +  ";" );
			while (rs.next()) {
				id.setText(Integer.toString(rs.getInt("id")));
				nombre.setText(rs.getString("nombre"));
				email.setText(rs.getString("email"));
				telefono.setText(rs.getString("telefono"));
				NIT.setText(rs.getString("nit"));
				tarjeta.setText(s.decrypt(rs.getString("no_tarjeta")));
				pathImg = rs.getString("picture");
				twitter.setText(rs.getString("twitter_user"));
				direccion.setText(rs.getString("direccion"));
				limite.setText(Float.toString(rs.getFloat("limite_credito")));
				tienda.select(rs.getInt("id_tienda"));
				pais.select(rs.getInt("id_pais"));
				impuesto.select(rs.getInt("id_impuestos"));
				bodega.select(rs.getInt("id_bodega"));
				FechaCreacion.setText("Fecha de Creacion: " + rs.getDate("fecha_creacion").toString());
				}
		} catch (Exception e) {
		} 

	}

	public int UpdateCliente(String ID, String Nombre, String Email, String Telefono, String NIT, String Direccion, String Picture,
			String No_Tarjeta, String Twitter_user, int idTienda, String LimiteCredito, int idPais, int idImpuesto, int idBodega){
		Connection c = null;
		Statement stmt = null;
		try{
			Class.forName("org.postgresql.Driver");
			c = DriverManager.getConnection(crypt("JTAt953gSQKMitFxut0yP6rsAUoser/SBOxWADMHyUMH3JGXFidrBOt7/FXBaTJ6"),crypt("QGX23Vj4nciMEeuwXLN7lAbMTa4SiB9j"), crypt("liYtV26MRFswwSCCzqVNZA=="));
			c.setAutoCommit(false);
			stmt = c.createStatement();
			StandardPBEStringEncryptor s = new StandardPBEStringEncryptor(); 
			s.setPassword("1234567890zxcvbnmasdfghjklqwertyuiopZXCVBNMASDFGHJKLQWERTYUIOP");
			String sql = "UPDATE CLIENTE "
					+ "SET NOMBRE = '"+ Nombre +"',"
					+ "EMAIL = '"+ Email +"',"
					+ "TELEFONO = '"+ Telefono +"',"
					+ "NIT = '"+ NIT +"',"
					+ "DIRECCION = '"+ Direccion +"',"
					+ "PICTURE = '"+ Picture +"',"
					+ "NO_TARJETA = '"+ s.encrypt(No_Tarjeta) +"',"
					+ "TWITTER_USER = '"+ Twitter_user +"',"
					+ "ID_TIENDA = '"+ Integer.toString(idTienda) +"',"
					+ "LIMITE_CREDITO = '"+ LimiteCredito +"',"
					+ "ID_PAIS = "+ Integer.toString(idPais) +","
					+ "ID_IMPUESTOS = "+ Integer.toString(idImpuesto) +","
					+ "ID_BODEGA = "+ Integer.toString(idBodega) +""
					+ " WHERE ID = "+ID+"";
			stmt.executeUpdate(sql);
			stmt.close();
			c.commit();
			c.close();
			return 0;
		}
		catch(Exception e){		
			System.out.println("Error: " + e);
			return 1;
		}

	}

	public void fillCombobox(Combo toFill, String Select){
		toFill.removeAll();     
		toFill.add("");
		Connection c = null;
		Statement stmt = null;
		try {  
			Class.forName("org.postgresql.Driver");
			c = DriverManager.getConnection(crypt("JTAt953gSQKMitFxut0yP6rsAUoser/SBOxWADMHyUMH3JGXFidrBOt7/FXBaTJ6"),crypt("QGX23Vj4nciMEeuwXLN7lAbMTa4SiB9j"), crypt("liYtV26MRFswwSCCzqVNZA=="));
			c.setAutoCommit(false);
			stmt = c.createStatement();
			ResultSet rs = stmt.executeQuery( "SELECT * FROM "+ Select +";" );
			System.out.println(rs.toString());
			while ( rs.next() ) {
				int id = rs.getInt("id");
				String  name = rs.getString("nombre");
				toFill.add(name, id);

			}
		} catch (Exception e) {
		} 
	}


	public String crypt(String cadena) { 
		StandardPBEStringEncryptor s = new StandardPBEStringEncryptor(); 
		s.setPassword("1234567890zxcvbnmasdfghjklqwertyuiopZXCVBNMASDFGHJKLQWERTYUIOP"); 
		String devuelve = ""; 
		try {
			return s.decrypt(cadena);
		} catch (Exception e) { 
		} 
		return devuelve; 
	}


	@Override
	public String[][] SELECTENTIRETABLE() {
		String[][] paraDevolver;
		try { 
			statement = c.createStatement();
			ResultSet rs = statement.executeQuery("SELECT * FROM cliente ORDER BY id");
			ResultSetMetaData rsmd = rs.getMetaData();
			int columnsNumber = rsmd.getColumnCount();
			
			
			ArrayList<String> tableCount = new ArrayList<String>();
			while (rs.next()) {
				for (int getRow = 1; getRow <= columnsNumber; getRow++) {
					tableCount.add(rs.getString(getRow));          
		        }

			}
			int rows = tableCount.size() /15;
			paraDevolver = new String [rows][];
			for(int i = 0; i < rows; i++){
				paraDevolver[i]  =  new String[columnsNumber];
				for (int s = 0; s < columnsNumber;s++){
					paraDevolver[i][s] = tableCount.get(s+(15*i)).toString();
				}
			}
			statement.close();
			return paraDevolver;
		} catch (Exception e) { 
			System.out.println("SELECTENTIRETABLE: ERROR");		
		} 

		return null;
	} 
	
	@Override
	public String[][] SELECTENTIRETABLE(String WHERE) {
		String[][] paraDevolver;
			
		try { 
			statement = c.createStatement();
			ResultSet rs = statement.executeQuery("SELECT cliente.id, cliente.nombre, cliente.email,"
					+ "cliente.telefono, cliente.nit, cliente.direccion, cliente.picture, cliente.no_tarjeta,"
					+ "cliente.twitter_user, cliente.fecha_creacion, tienda.nombre, cliente.limite_credito, "
					+ "pais.nombre, impuestos.nombre, bodega.nombre "
					+ "FROM cliente INNER JOIN bodega ON(cliente.id_bodega = bodega.id)"
					+ "INNER JOIN pais ON (pais.id = cliente.id_pais)"
					+ "INNER JOIN impuestos ON (impuestos.id = cliente.id_impuestos)"
					+ "INNER JOIN tienda ON (tienda.id = cliente.id_tienda)"
					+ " "+ WHERE+";");
			ResultSetMetaData rsmd = rs.getMetaData();
			int columnsNumber = rsmd.getColumnCount();
			
			
			ArrayList<String> tableCount = new ArrayList<String>();
			while (rs.next()) {
				for (int getRow = 1; getRow <= columnsNumber; getRow++) {
					tableCount.add(rs.getString(getRow));          
		        }

			}
			int rows = tableCount.size() /15;
			paraDevolver = new String [rows][];
			for(int i = 0; i < rows; i++){
				paraDevolver[i]  =  new String[columnsNumber];
				for (int s = 0; s < columnsNumber;s++){
					paraDevolver[i][s] = tableCount.get(s+(15*i)).toString();
				}
			}
			statement.close();
			return paraDevolver;
		} catch (Exception e) { 
			System.out.println("SELECTENTIRETABLE: ERROR");		
		} 

		return null;
	} 
}
