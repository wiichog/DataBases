package GUI;

import org.eclipse.swt.widgets.Display;
import BackEnd.*;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Label;
import org.eclipse.wb.swt.SWTResourceManager;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;


public class ClientEntry {

	protected Shell shell;
	private Text id_Text;
	private Text Pais__Text;
	private Text email__Text;
	private Text telefono__Text;
	private Text NIT__Text;
	private Text numeroTarjeta_text;
	private Text twitter_text;
	private Text direccion__Text;
	public String filepath = "";
	public int getSelectionIndexIdTienda = 0;
	public int getSelectionIndexIdBodega = 0;
	public int getSelectionIndexIdPais = 0;
	public int getSelectionIndexIdImpuesto = 0;
	public implementCommands sql = new implementCommands();
	private Text limDeC_text;
	private CommandsSQL command = new implementCommands();
	private String currDirectory = System.getProperty("user.dir");
	private String saveTo = currDirectory + "/imgFolder/";
	private File masterfolder;
	private File f;
	private String imgDir = null;
	/**
	 * Launch the application.
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			System.out.println("on ClientEntry");
			CommandsSQL newCommand = new implementCommands();
			ClientEntry window = new ClientEntry(newCommand);
			window.open();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public ClientEntry(CommandsSQL commands){
		command = commands;
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
		shell.setSize(750, 650);
		shell.setText("Editor de Cliente");
		checkMasterFolder();
		id_Text = new Text(shell, SWT.BORDER);
		id_Text.setEditable(false);
		id_Text.setBounds(145, 58, 106, 29);
		
		Label fotoCliente = new Label(shell, SWT.BORDER);
		fotoCliente.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseUp(MouseEvent e) {
				JFileChooser imagenEscoger = new JFileChooser();
				imagenEscoger.showOpenDialog(null);
				f=imagenEscoger.getSelectedFile();
				if(!(f == null)){
					filepath = f.getAbsolutePath();
					fotoCliente.setImage(SWTResourceManager.getImage(filepath));
				}

			}
		});
		fotoCliente.setBounds(407, 23, 256, 256);
		
		Label lblNewLabel_1 = new Label(shell, SWT.NONE);
		lblNewLabel_1.setBounds(73, 60, 19, 17);
		lblNewLabel_1.setText("ID:");
		
		Label lblNewLabel_2 = new Label(shell, SWT.NONE);
		lblNewLabel_2.setBounds(33, 116, 71, 17);
		lblNewLabel_2.setText("Nombre:");
		
		Label lblNewLabel_3 = new Label(shell, SWT.NONE);
		lblNewLabel_3.setFont(SWTResourceManager.getFont("Ubuntu", 14, SWT.BOLD));
		lblNewLabel_3.setBounds(39, 23, 227, 29);
		lblNewLabel_3.setText("Cliente Seleccionado");
		
		Pais__Text = new Text(shell, SWT.BORDER);
		Pais__Text.setBounds(145, 109, 203, 29);
		
		Label email = new Label(shell, SWT.NONE);
		email.setBounds(50, 181, 53, 17);
		email.setText("email:");
		
		Label telefono = new Label(shell, SWT.NONE);
		telefono.setBounds(33, 231, 71, 17);
		telefono.setText("Telefono:");
		
		Label NIT = new Label(shell, SWT.NONE);
		NIT.setBounds(73, 284, 71, 17);
		NIT.setText("NIT:");
		
		Label lblNewLabel_9 = new Label(shell, SWT.NONE);
		lblNewLabel_9.setBounds(356, 35, 71, 17);
		lblNewLabel_9.setText("Foto:");
		
		Label direccion = new Label(shell, SWT.NONE);
		direccion.setBounds(33, 525, 71, 17);
		direccion.setText("Dirección:");
		
		email__Text = new Text(shell, SWT.BORDER);
		email__Text.setBounds(145, 171, 171, 29);
		
		telefono__Text = new Text(shell, SWT.BORDER);
		telefono__Text.setBounds(145, 231, 171, 29);
		
		NIT__Text = new Text(shell, SWT.BORDER);
		NIT__Text.setBounds(145, 284, 203, 29);
		
		numeroTarjeta_text = new Text(shell, SWT.BORDER);
		numeroTarjeta_text.setBounds(145, 346, 203, 29);
		
		twitter_text = new Text(shell, SWT.BORDER);
		twitter_text.setBounds(145, 403, 203, 29);
		
		direccion__Text = new Text(shell, SWT.BORDER | SWT.WRAP);
		direccion__Text.setBounds(145, 519, 256, 60);

		Combo tiendaID_combo = new Combo(shell, SWT.NONE);
		sql.fillCombobox(tiendaID_combo, "tienda");
		tiendaID_combo.setBounds(524, 333, 195, 29);
		getSelectionIndexIdTienda = tiendaID_combo.getSelectionIndex();
		
		limDeC_text = new Text(shell, SWT.BORDER);
		limDeC_text.setBounds(524, 380, 195, 29);
		
		Combo paisID_combo = new Combo(shell, SWT.NONE);
		sql.fillCombobox(paisID_combo, "pais");
		paisID_combo.setBounds(524, 423, 195, 29);
		getSelectionIndexIdPais = paisID_combo.getSelectionIndex();
		
		Combo impuestoID_combo = new Combo(shell, SWT.NONE);
		sql.fillCombobox(impuestoID_combo, "impuestos");
		impuestoID_combo.setBounds(524, 470, 195, 29);
		getSelectionIndexIdImpuesto = impuestoID_combo.getSelectionIndex();
		
		Combo bodegaID_combo = new Combo(shell, SWT.NONE);
		sql.fillCombobox(bodegaID_combo, "bodega");
		bodegaID_combo.setBounds(145, 458, 195, 29);
		getSelectionIndexIdBodega = bodegaID_combo.getSelectionIndex();
			
		fotoCliente.setImage(SWTResourceManager.getImage(ClientEntry.class, "/resources/defaultProfilePic.png"));
		Button btnNewButton = new Button(shell, SWT.NONE);
		btnNewButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if(Pais__Text.getText().equals("")){
					JOptionPane.showMessageDialog(null, "Ingresar un nombre", "ERROR", JOptionPane.INFORMATION_MESSAGE);
				}
				else if(email__Text.getText().equals("")){
					JOptionPane.showMessageDialog(null, "Ingresar email", "ERROR", JOptionPane.INFORMATION_MESSAGE);
				}
				else if(telefono__Text.getText().equals("")){
					JOptionPane.showMessageDialog(null, "Ingresar telefono", "ERROR", JOptionPane.INFORMATION_MESSAGE);
				}
				else if(NIT__Text.getText().equals("")){
					JOptionPane.showMessageDialog(null, "Ingresar NIT", "ERROR", JOptionPane.INFORMATION_MESSAGE);
				}
				else if(direccion__Text.getText().equals("")){
					JOptionPane.showMessageDialog(null, "Ingresar direccion", "ERROR", JOptionPane.INFORMATION_MESSAGE);
				}
				else if(numeroTarjeta_text.getText().equals("")){
					JOptionPane.showMessageDialog(null, "Ingresar numero de tarjeta", "ERROR", JOptionPane.INFORMATION_MESSAGE);
				}
				else if(numeroTarjeta_text.getText().length()<15){
					JOptionPane.showMessageDialog(null, "Numero de tarjeta tiene que ser mayor a 15 carecteres", "ERROR", JOptionPane.INFORMATION_MESSAGE);
				}
				else if(twitter_text.getText().equals("")){
					JOptionPane.showMessageDialog(null, "Ingresar usuario de twitter", "ERROR", JOptionPane.INFORMATION_MESSAGE);
				}
				else if(tiendaID_combo.getText().equals("")){
					JOptionPane.showMessageDialog(null, "Elegir tienda", "ERROR", JOptionPane.INFORMATION_MESSAGE);
				}
				else if(paisID_combo.getText().equals("")){
					JOptionPane.showMessageDialog(null, "Elegir pais", "ERROR", JOptionPane.INFORMATION_MESSAGE);
				}
				else if(impuestoID_combo.getText().equals("")){
					JOptionPane.showMessageDialog(null, "Elegir impuesto", "ERROR", JOptionPane.INFORMATION_MESSAGE);
				}
				else if(bodegaID_combo.getText().equals("")){
					JOptionPane.showMessageDialog(null, "Elegir bodega", "ERROR", JOptionPane.INFORMATION_MESSAGE);
				}
				else if(limDeC_text.getText().equals("")){
					JOptionPane.showMessageDialog(null, "Ingresar limite de credito", "ERROR", JOptionPane.INFORMATION_MESSAGE);
				}
				else{
				if(MessageDialog.openConfirm(shell, "Confirmar", "Seguro que quiere guardar la informacion del cliente "+ Pais__Text.getText() +"?"))
				{
					if(sql.InsertClient(Pais__Text.getText(), email__Text.getText(), telefono__Text.getText(),NIT__Text.getText()
							,direccion__Text.getText(), filepath , numeroTarjeta_text.getText(), twitter_text.getText()
							,tiendaID_combo.getSelectionIndex(), Float.parseFloat(limDeC_text.getText())
							,paisID_combo.getSelectionIndex()
							,impuestoID_combo.getSelectionIndex()
							,bodegaID_combo.getSelectionIndex())==0){
						JOptionPane.showMessageDialog(null, "Cliente creado exitosamente", "ERROR", JOptionPane.INFORMATION_MESSAGE);
						Pais__Text.setText("");
						email__Text.setText("");
						telefono__Text.setText("");
						NIT__Text.setText("");
						direccion__Text.setText("");
						numeroTarjeta_text.setText("");
						twitter_text.setText("");
						limDeC_text.setText("");
						tiendaID_combo.setText("");
						paisID_combo.setText("");
						impuestoID_combo.setText("");
						bodegaID_combo.setText("");
						sql.fillCombobox(tiendaID_combo, "tienda");
						sql.fillCombobox(paisID_combo, "pais");
						sql.fillCombobox(impuestoID_combo, "impuestos");
						sql.fillCombobox(bodegaID_combo, "bodega");
						fotoCliente.setImage(SWTResourceManager.getImage(ClientEntry.class, "/resources/defaultProfilePic.png"));
						if(f != null)
							guardarFoto(f);
						else
							JOptionPane.showMessageDialog(null, "Problema Guardado La foto", "ERROR", JOptionPane.INFORMATION_MESSAGE);
					}
					else{
						JOptionPane.showMessageDialog(null, "Error al crear el cliente ver consola para errores", "ERROR", JOptionPane.INFORMATION_MESSAGE);
					}
				}
				}
			}
		});
		btnNewButton.setBounds(537, 525, 156, 29);
		btnNewButton.setText("Subir");
		
		Button btnRegresar = new Button(shell, SWT.NONE);
		btnRegresar.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				windowClose();
				ViewTable regresar = new ViewTable(command, "cliente"); //TODO cliente manualmente ingresado
				regresar.open();
			}
		});
		btnRegresar.setText("Regresar");
		btnRegresar.setBounds(537, 572, 156, 29);
		
		Label lblNewLabel_4 = new Label(shell, SWT.WRAP);
		lblNewLabel_4.setBounds(20, 346, 83, 46);
		lblNewLabel_4.setText("Numero de Tarjeta:");
		
		Label lblNewLabel_5 = new Label(shell, SWT.NONE);
		lblNewLabel_5.setBounds(33, 403, 71, 17);
		lblNewLabel_5.setText("Twitter:");
		
		Label lblNewLabel_6 = new Label(shell, SWT.NONE);
		lblNewLabel_6.setBounds(407, 301, 286, 17);
		lblNewLabel_6.setText("Fecha de Creacion:");
		
		Label lblNewLabel_7 = new Label(shell, SWT.NONE);
		lblNewLabel_7.setBounds(437, 338, 71, 17);
		lblNewLabel_7.setText("TiendaID");
		
		Label lblNewLabel_8 = new Label(shell, SWT.NONE);
		lblNewLabel_8.setBounds(391, 380, 117, 17);
		lblNewLabel_8.setText("Limite de Credito");
		
		Label lblNewLabel_10 = new Label(shell, SWT.NONE);
		lblNewLabel_10.setBounds(33, 470, 71, 17);
		lblNewLabel_10.setText("Bodega id");
		
		Label lblNewLabel_11 = new Label(shell, SWT.NONE);
		lblNewLabel_11.setBounds(437, 430, 71, 17);
		lblNewLabel_11.setText("Pais ID");
		
		Label lblNewLabel_12 = new Label(shell, SWT.NONE);
		lblNewLabel_12.setBounds(402, 475, 106, 17);
		lblNewLabel_12.setText("Impuesto ID");
	}
	private void windowClose(){
		this.shell.setVisible(false);
	}
	private void checkMasterFolder(){
		masterfolder = new File(currDirectory + "/imgFolder");
		boolean success = (new File(currDirectory + "/imgFolder")).mkdir();
		if (success) {
		      System.out.println("Directory: " + currDirectory + " created");
		    }
		else{
			System.out.println("FolderToUser: imgFolder in:: \n" + currDirectory + "/imgFolder");
		}

	}
	private void guardarFoto(File f){
		try {
			// retrieve image
			BufferedImage bi = ImageIO.read(f);
			File outputfile = new File(saveTo + "/fotoParaGuardar");
			ImageIO.write(bi, "png", outputfile);
			System.out.println("Imagen Guardada");
			} catch (Exception e1) {
			e1.printStackTrace();
			System.out.println("Error en save image");
			}
	}
	
}
