package GUI;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableItem;

import BackEnd.CommandsSQL;
import BackEnd.implementCommands;
import Controllers.Reporteria;

import java.io.IOException;

import javax.swing.JOptionPane;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;

public class ViewTable {

	protected Shell shell;
	private Table table;
	private String[] columnaIdCliente;
	protected CommandsSQL command = new implementCommands();
	protected String tableName;
	public implementCommands sql = new implementCommands();
	/**
	 * Launch the application.
	 * @param args
	 */
	public static void main(String[] args) {
		try {

			CommandsSQL givenCommand = new implementCommands();
			ViewTable window = new ViewTable(givenCommand, "probando");
			window.open();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public ViewTable(CommandsSQL commands, String tableNameRecived){
		command = commands;
		tableName = tableNameRecived;
	}
	/**
	 * Open the window.
	 */
	public void open() {
		//System.out.println("on ViewTable");
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
		shell.setSize(602, 408);
		shell.setText("Tabla Cliente");

		table = new Table(shell, SWT.BORDER | SWT.FULL_SELECTION);
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDoubleClick(MouseEvent e) {
				windowClose();
				ClientEntryUpdate editClient = new ClientEntryUpdate(command, columnaIdCliente[table.getSelectionIndex()]); //columnaIdCliente[table.getSelectionIndex()]
				editClient.open();
			}
		});
		table.setBounds(25, 48, 540, 244);
		table.setHeaderVisible(true);
		table.setLinesVisible(true);

		TableColumn idCliente = new TableColumn(table, SWT.NONE);
		idCliente.setWidth(100);
		idCliente.setText("idCliente");

		TableColumn Nombre = new TableColumn(table, SWT.NONE);
		Nombre.setWidth(100);
		Nombre.setText("Nombre");
		columnaIdCliente = command.SELECT("id", "cliente");
		String[] columnaNombreCliente = command.SELECT("nombre", "cliente");
		TableItem[] arrayItems = new TableItem[columnaNombreCliente.length];
		if(columnaIdCliente.length != columnaNombreCliente.length){
			MessageDialog.openError(shell, "Error: Columnas", "No hay mismo numero de id que nombres");
			windowClose();
			WindowGui startAgain = new WindowGui();
			startAgain.open();
		}
		for(int i =0; i<columnaIdCliente.length;i++){
			arrayItems[i] = new TableItem(table, SWT.NONE);
			arrayItems[i].setText(0, columnaIdCliente[i].toString());
			arrayItems[i].setText(1, columnaNombreCliente[i].toString());
		}

		Label lblNewLabel = new Label(shell, SWT.NONE);
		lblNewLabel.setBounds(25, 10, 71, 17);
		lblNewLabel.setText(tableName);

		Button btnNewButton = new Button(shell, SWT.NONE);
		btnNewButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				windowClose();
				WindowGui login = new WindowGui();
				login.open();
			}
		});
		btnNewButton.setBounds(35, 328, 125, 29);
		btnNewButton.setText("LogOut");

		Button GenerateReport = new Button(shell, SWT.NONE);
		GenerateReport.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				boolean error = false;
				quinceFunciones test = new quinceFunciones(command);
				if(!test.tiendaConMasTiendas() || !test.paisConMasClientes() || !test.clientesConMayorCredito()|| !test.clientesEnGuatemala() || !test.fechaDeCreacion())
					error = true;
				if(error)
					MessageDialog.openError(shell, "Error", "Reporte no se genero");
				else
					MessageDialog.openInformation(shell, "success", "Si se logro generar el reporte!");
			}
		});
		GenerateReport.setBounds(427, 328, 138, 29);
		GenerateReport.setText("Generar Reporte");

		Button btnNewButton_2 = new Button(shell, SWT.NONE);
		btnNewButton_2.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				ClientEntry clientNuevo = new ClientEntry(command);
				clientNuevo.open();
			}
		});
		btnNewButton_2.setBounds(277, 328, 125, 29);
		btnNewButton_2.setText("Nuevo Cliente");

		Button modoTablagrande = new Button(shell, SWT.NONE);
		modoTablagrande.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				windowClose();
				ViewTableCompleta tableCompleta = new ViewTableCompleta(command, tableName);
				tableCompleta.open();
			}
		});
		modoTablagrande.setBounds(361, 10, 178, 29);
		modoTablagrande.setText("Tabla Completa");
	}
	private void windowClose(){
		this.shell.setVisible(false);
	}
}
