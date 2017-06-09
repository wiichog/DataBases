package GUI;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableItem;

import BackEnd.CommandsSQL;
import BackEnd.implementCommands;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.wb.swt.SWTResourceManager;
import org.eclipse.swt.widgets.DateTime;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;

public class ViewTableCompleta {

	protected Shell shell;
	private Table table;
	protected CommandsSQL command = new implementCommands();
	protected String tableName;
	private Text buscaNombre_Text;
	private String sqlParaMandar;
	private String[] columnaIdCliente;
	/**
	 * Launch the application.
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			CommandsSQL givenCommand = new implementCommands();
			ViewTableCompleta window = new ViewTableCompleta(givenCommand, "probando");
			window.open();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public ViewTableCompleta(CommandsSQL commands, String tableNameRecived){
		command = commands;
		tableName = tableNameRecived;
	}
	/**
	 * Open the window.
	 */
	public void open() {
		Display display = Display.getDefault();
		createContents();
		shell.open();
		shell.layout();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}

	/**
	 * Create contents of the window.
	 */
	protected void createContents() {
		shell = new Shell( SWT.SHELL_TRIM & (~SWT.RESIZE));
		shell.setSize(950, 525);
		shell.setText("Tabla Cliente");
		shell.setLayout(null);
		sqlParaMandar = "";
		table = new Table(shell, SWT.BORDER | SWT.FULL_SELECTION);
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDoubleClick(MouseEvent arg0) {
				windowClose();
				ClientEntryUpdate editClient = new ClientEntryUpdate(command, columnaIdCliente[table.getSelectionIndex()]); //columnaIdCliente[table.getSelectionIndex()]
				editClient.open();
			}
		});
		table.setBounds(25, 48, 880, 337);
		table.setFont(SWTResourceManager.getFont("Ubuntu", 10, SWT.NORMAL));
		table.setHeaderVisible(true);
		table.setLinesVisible(true);

		createColumnas(table);
		String[][] tablaCompleta = null;
		createTable(tablaCompleta);

		Label lblNewLabel = new Label(shell, SWT.NONE);
		lblNewLabel.setBounds(25, 10, 71, 17);
		lblNewLabel.setText(tableName);
		columnaIdCliente = command.SELECT("id", "cliente");
		Button btnNewButton = new Button(shell, SWT.NONE);
		btnNewButton.setBounds(25, 414, 125, 29);
		btnNewButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				windowClose();
				WindowGui login = new WindowGui();
				login.open();
			}
		});
		btnNewButton.setText("LogOut");

		Button btnNewButton_2 = new Button(shell, SWT.NONE);
		btnNewButton_2.setBounds(432, 404, 125, 39);
		btnNewButton_2.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				windowClose();
				ClientEntry newClient = new ClientEntry(command);
				newClient.open();
			}
		});
		btnNewButton_2.setText("Nuevo Cliente");

		Button Tablachiquita = new Button(shell, SWT.NONE);
		Tablachiquita.setBounds(166, 414, 221, 29);
		Tablachiquita.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				windowClose();
				ViewTable tablaPeque = new ViewTable(command, tableName);
				tablaPeque.open();
			}
		});
		Tablachiquita.setText("Tabla Pequeña");

		DateTime dateTime = new DateTime(shell, SWT.BORDER);
		dateTime.setBounds(749, 0, 167, 39);
		dateTime.setEnabled(false);
		//AQUI COMBOS
		Combo paiscombo = new Combo(shell, SWT.NONE);
		paiscombo.setBounds(143, 10, 134, 29);
		paiscombo.setText("Pais");
		combosLlenar(paiscombo);

		Combo tienda_combo = new Combo(shell, SWT.NONE);
		tienda_combo.setBounds(283, 10, 125, 29);
		tienda_combo.setText("Tienda");
		combosLlenar(tienda_combo);

		Combo bodega_combo = new Combo(shell, SWT.NONE);
		bodega_combo.setBounds(414, 10, 125, 29);
		bodega_combo.setText("Bodega");
		combosLlenar(bodega_combo);

		buscaNombre_Text = new Text(shell, SWT.BORDER);
		buscaNombre_Text.setBounds(666, 406, 209, 29);

		Label lblNewLabel_1 = new Label(shell, SWT.NONE);
		lblNewLabel_1.setBounds(586, 396, 93, 39);
		lblNewLabel_1.setText("Buscar Por \nNombre:");

		Combo impuesto_combo = new Combo(shell, SWT.NONE);
		impuesto_combo.setBounds(554, 10, 125, 29);
		impuesto_combo.setText("Impuestos");
		combosLlenar(impuesto_combo);
		Button btnCheckButton = new Button(shell, SWT.CHECK);
		btnCheckButton.setBounds(685, 10, 117, 22);
		btnCheckButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				if(dateTime.getEnabled()) {
					dateTime.setEnabled(false);
				}else{
					dateTime.setEnabled(true);
				}
			}
		});
		btnCheckButton.setText("Fecha");

		Button searchButton = new Button(shell, SWT.NONE);
		searchButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				//Al precionar buscar, este va a mandar un String con las instrucciones 
				//para filtrar solo lo que se busca
				table.removeAll();
				sqlParaMandar = "";
				String[] columnas = new String[4];
				table.setBounds(25, 48, 880, 337);
				table.setFont(SWTResourceManager.getFont("Ubuntu", 10, SWT.NORMAL));
				table.setHeaderVisible(true);
				table.setLinesVisible(true);
				if(!bodega_combo.getText().equalsIgnoreCase("Bodega") && !(bodega_combo.getText().equalsIgnoreCase("nada"))){
					columnas[0] = bodega_combo.getText();
				}if(!impuesto_combo.getText().equalsIgnoreCase("Impuestos") && !(impuesto_combo.getText().equalsIgnoreCase("nada"))){
					columnas[1] = impuesto_combo.getText();
				}if(!paiscombo.getText().equalsIgnoreCase("Pais") && !(paiscombo.getText().equalsIgnoreCase("nada"))){
					columnas[2] = paiscombo.getText();
				}if(!tienda_combo.getText().equalsIgnoreCase("Tienda") && !(tienda_combo.getText().equalsIgnoreCase("nada"))){
					columnas[3] = tienda_combo.getText();
				}
				for(int i = 0; i < columnas.length; i++){
					if(!(columnas[i] == null))
						if(sqlParaMandar == "")
							sqlParaMandar += "WHERE "+ getColumNameWithInt(i)+ "." + getVariableDeColumna(getColumNameWithInt(i))+ " = \'"+columnas[i] +"\'";
						else
							sqlParaMandar += " AND "+ getColumNameWithInt(i)+ "." + getVariableDeColumna(getColumNameWithInt(i))+ " = \'"+columnas[i] +"\'";
				}//Ya esta el WHERE de columnas
				
				if(dateTime.getEnabled()){
					if(sqlParaMandar.equalsIgnoreCase(""))
						sqlParaMandar += " WHERE cliente.fecha_creacion = \'"+(dateTime.getMonth()+1)+ "\\"
								+ "" + dateTime.getDay() + "\\"+dateTime.getYear()+"\'";
					else
						sqlParaMandar += " AND cliente.fecha_creacion = \'"+(dateTime.getMonth()+1)+ "\\"
								+ "" + dateTime.getDay() + "\\"+dateTime.getYear()+"\'";
				}
				if(buscaNombre_Text != null && !buscaNombre_Text.getText().equalsIgnoreCase("")){
					String newstr = buscaNombre_Text.getText().replaceAll("[^A-Za-z]+", "");
					if(sqlParaMandar.equalsIgnoreCase(""))
						sqlParaMandar += " WHERE cliente.nombre LIKE \'" + newstr + "%\'";
					else
						sqlParaMandar += " AND cliente.nombre LIKE \'" + newstr + "%\'";
				}
				String[][] tablaCompleta = null;
				createTable(tablaCompleta);
			}
		});
		searchButton.setBounds(685, 447, 170, 29);
		searchButton.setText("Buscar");
	}
	private void windowClose(){
		this.shell.setVisible(false);
	}

	private void createTable(String[][] tablaCompleta){
		tablaCompleta = command.SELECTENTIRETABLE(sqlParaMandar);
		for(int i =0; i<tablaCompleta.length;i++){
			TableItem[] arrayItems = new TableItem[tablaCompleta[0].length];			
			arrayItems [i] = new TableItem(table, SWT.NONE);
			for(int s =0; s<tablaCompleta[0].length;s++){
				arrayItems[i].setText(s, tablaCompleta[i][s]);
			}
		}
	}
	private void createColumnas(Table table){
		//COLUMNAS DE LA TABLA
		TableColumn idCliente = new TableColumn(table, SWT.NONE);
		idCliente.setWidth(40);
		idCliente.setText("idCliente");

		TableColumn Nombre = new TableColumn(table, SWT.NONE);
		Nombre.setWidth(80);
		Nombre.setText("Nombre");

		TableColumn Email = new TableColumn(table, SWT.NONE);
		Email.setWidth(80);
		Email.setText("Email");

		TableColumn Telefono = new TableColumn(table, SWT.NONE);
		Telefono.setWidth(80);
		Telefono.setText("Telefono");

		TableColumn NIT = new TableColumn(table, SWT.NONE);
		NIT.setWidth(80);
		NIT.setText("NIT");

		TableColumn Direccion = new TableColumn(table, SWT.NONE);
		Direccion.setWidth(150);
		Direccion.setText("Direccion");

		TableColumn foto = new TableColumn(table, SWT.NONE);
		foto.setWidth(80);
		foto.setText("foto");


		TableColumn noTarjeta = new TableColumn(table, SWT.NONE);
		noTarjeta.setWidth(80);
		noTarjeta.setText("Tarjeta");

		TableColumn twitterUser = new TableColumn(table, SWT.NONE);
		twitterUser.setWidth(80);
		twitterUser.setText("twitter");

		TableColumn fechaCreacion = new TableColumn(table, SWT.NONE);
		fechaCreacion.setWidth(100);
		fechaCreacion.setText("Fecha Creada");

		TableColumn idTienda = new TableColumn(table, SWT.NONE);
		idTienda.setWidth(80);
		idTienda.setText("idTienda");

		TableColumn limCredito = new TableColumn(table, SWT.NONE);
		limCredito.setWidth(80);
		limCredito.setText("Limite de Credito");

		TableColumn idPais = new TableColumn(table, SWT.NONE);
		idPais.setWidth(80);
		idPais.setText("idPais");

		TableColumn idImpuesto = new TableColumn(table, SWT.NONE);
		idImpuesto.setWidth(80);
		idImpuesto.setText("idImpuesto");

		TableColumn idBodega = new TableColumn(table, SWT.NONE);
		idBodega.setWidth(80);
		idBodega.setText("idBodega");
		//Se acabaron las columnas
	}
	private String getColumNameWithInt(int givenInt){
		String returningString = null;
		if(givenInt == 0){
			returningString = "bodega";
		}
		if(givenInt == 1){
			returningString = "impuestos";
		}
		if(givenInt == 2){
			returningString = "pais";
		}if(givenInt == 3){
			returningString = "tienda";
		}
		return returningString;
	}
	private void combosLlenar(Combo paraLlenar){
		String[] fillCombo;
		paraLlenar.add("nada");
		if(paraLlenar.getText().equalsIgnoreCase("bodega") || paraLlenar.getText().equalsIgnoreCase("tienda"))
			fillCombo = (command.SELECT("nombre", paraLlenar.getText()));
		else if (paraLlenar.getText().equalsIgnoreCase("impuestos"))
			fillCombo = (command.SELECT("nombre", paraLlenar.getText()));
		else	
			fillCombo = (command.SELECT("nombre", paraLlenar.getText()));
		for(int i = 0; i < fillCombo.length; i++){
			paraLlenar.add(fillCombo[i]);
		}
		
	}
	private String getVariableDeColumna(String givenString){
		String strToReturn = null;
		if(givenString.equalsIgnoreCase("bodega"))
			strToReturn = "nombre";
		if(givenString.equalsIgnoreCase("impuestos"))
			strToReturn = "nombre";
		if(givenString.equalsIgnoreCase("pais"))
			strToReturn = "nombre";
		if(givenString.equalsIgnoreCase("tienda"))
			strToReturn = "nombre";
		return strToReturn;
	}
}
