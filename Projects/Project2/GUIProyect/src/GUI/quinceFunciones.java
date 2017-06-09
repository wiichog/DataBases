package GUI;
import java.io.IOException;

import BackEnd.*;
import Controllers.*;
public class quinceFunciones {
	Reporteria reporte;
	CommandsSQL commands;

	public quinceFunciones(CommandsSQL command){
		try {
			System.out.println("Testing crear reporte");
			reporte = new Reporteria("ReporteInfo");
			System.out.println("reporte creado");
			commands = command;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	public boolean tiendaConMasTiendas(){
		String[] col1 = commands.SELECT("T1.NOMBRE", "CLIENTE AS T0 INNER JOIN TIENDA AS T1 ON T0.ID_PAIS = T1.ID GROUP BY T1.NOMBRE ORDER BY T1.NOMBRE", "LIMIT 1");
		String[] int1 = commands.SELECT("COUNT(*)", "CLIENTE AS T0 INNER JOIN TIENDA AS T1 ON T0.ID_PAIS = T1.ID GROUP BY T1.NOMBRE ORDER BY T1.NOMBRE", "LIMIT 1");
		int[] something = changeStringToINT(int1);
		try {
			reporte.bars(something, col1, "Tiendas con mas Cliente");
		} catch (IOException e) {
			System.out.println("error q1");;
			return false;
		}
		return true;
	}
	public boolean paisConMasClientes(){
		String[] col1 = commands.SELECT("T1.NOMBRE", "CLIENTE AS T0 INNER JOIN PAIS AS T1 ON T0.ID_PAIS = T1.ID GROUP BY T1.NOMBRE ORDER BY T1.NOMBRE LIMIT 1", "");
		String[] int1 = commands.SELECT("COUNT(*)", "CLIENTE AS T0 INNER JOIN PAIS AS T1 ON T0.ID_PAIS = T1.ID GROUP BY T1.NOMBRE ORDER BY T1.NOMBRE LIMIT 1", "");
		int[] something = changeStringToINT(int1);
		try {
			reporte.bars(something, col1, "paisConMasClientes");
		} catch (IOException e) {
			System.out.println("error q2");;
			return false;
		}
		return true;
	}
	public boolean clientesConMayorCredito(){
		String[] col1 = commands.SELECT("NOMBRE", "CLIENTE", " ORDER BY LIMITE_CREDITO LIMIT 10");
		try {
			reporte.display("Clientes con mayor credito", col1, "Aqui estan los 10 clientes con mayor credito");
		} catch (IOException e) {
			System.out.println("error q3");;
			return false;
		}
		return true;
	}
	public boolean clientesEnGuatemala(){
		String[] col1 = commands.SELECT("NOMBRE", "CLIENTE", " WHERE ID_PAIS = 82");
		try {
			reporte.display("clientes En Guatemala",col1, "Todos los nombres de los clientes de guatemala");
		} catch (IOException e) {
			System.out.println("error q4");;
			return false;
		}
		return true;
	}
	public boolean fechaDeCreacion(){
		String[] col1 = commands.SELECT("T0.FECHA_CREACION", "(SELECT COUNT(*),FECHA_CREACION FROM CLIENTE GROUP BY FECHA_CREACION LIMIT 1) AS T0");
		try {
			reporte.display("Fecha De Creacion Con Mas Clientes",col1,  "La fecha de creacion con mayor ingresos de clientes");
		} catch (IOException e) {
			System.out.println("error q5");;
			return false;
		}
		return true;
	}	

	private int[] changeStringToINT(String[] someThing){

		int[] paraRegresar = new int[someThing.length];
		for(int i = 0; i < someThing.length; i++){
			paraRegresar[i] = Integer.parseInt(someThing[i]);
		}
		return paraRegresar;
	}
}
