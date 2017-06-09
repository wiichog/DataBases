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
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Label;
import org.eclipse.wb.swt.SWTResourceManager;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;

public class ClientEntryUpdate {

	protected Shell shell;
	private Text id_Text;
	private Text email__Text;
	private Text telefono__Text;
	private Text NIT__Text;
	private Text twitter_text;
	private Text direccion__Text;
	private Text limDeC_text;
	private Text numeroTarjeta_text;
	private Combo tiendaID_combo;
	private Combo paisID_combo;
	private Combo impuestoID_combo;
	private Combo bodegaID_combo;
	private Label lblNewLabel_6;
	public String filepath = "";
	private CommandsSQL command = new implementCommands();
	public String idDelCliente;
	public implementCommands sql = new implementCommands();
	private Text txt_nombre;
	private String currDirectory = System.getProperty("user.dir");
	private String saveTo = currDirectory + "/imgFolder/";
	private File f;
	public String imgDir = null;
	//VARIABLES PARA EVITAR SQL INJECTION
	//END VARIABLES
	/**
	 * @wbp.parser.entryPoint
	 */
	public static void main(String[] args) {
		try {
			System.out.println("on ClientEntry");
			CommandsSQL newCommand = new implementCommands();
			ClientEntryUpdate window = new ClientEntryUpdate(newCommand, "6");
			window.open();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * @wbp.parser.entryPoint
	 */
	public ClientEntryUpdate(CommandsSQL commands, String id){
		command = commands;
		idDelCliente = id;
	}
	/**
	 * Open the window.
	 * @wbp.parser.entryPoint
	 */
	public void open() {
		Display display = Display.getDefault();
		createContents();
		sql.fillCombobox(paisID_combo, "pais");
		sql.fillCombobox(tiendaID_combo, "tienda");
		sql.fillCombobox(impuestoID_combo, "impuestos");
		sql.fillCombobox(bodegaID_combo, "bodega");
		sql.FillData(id_Text, txt_nombre, email__Text, telefono__Text, NIT__Text, numeroTarjeta_text, 
				twitter_text, direccion__Text, limDeC_text, tiendaID_combo, paisID_combo, impuestoID_combo, bodegaID_combo, idDelCliente,lblNewLabel_6,imgDir);
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
	 * @wbp.parser.entryPoint
	 */
	protected void createContents() {
		shell = new Shell( SWT.SHELL_TRIM & (~SWT.RESIZE));
		shell.setSize(750, 650);
		shell.setText("Editor de Cliente");

		id_Text = new Text(shell, SWT.BORDER);
		id_Text.setEditable(false);
		id_Text.setBounds(145, 58, 106, 29);

		email__Text = new Text(shell, SWT.BORDER);
		email__Text.setBounds(145, 171, 171, 29);

		telefono__Text = new Text(shell, SWT.BORDER);
		telefono__Text.setBounds(145, 231, 171, 29);

		NIT__Text = new Text(shell, SWT.BORDER);
		NIT__Text.setBounds(145, 284, 203, 29);

		txt_nombre = new Text(shell, SWT.BORDER);
		txt_nombre.setBounds(145, 104, 171, 29);

		numeroTarjeta_text = new Text(shell, SWT.BORDER);
		numeroTarjeta_text.setBounds(145, 346, 203, 29);

		twitter_text = new Text(shell, SWT.BORDER);
		twitter_text.setBounds(145, 403, 203, 29);

		direccion__Text = new Text(shell, SWT.BORDER | SWT.WRAP);
		direccion__Text.setBounds(145, 519, 256, 60);

		tiendaID_combo = new Combo(shell, SWT.NONE);
		tiendaID_combo.setBounds(524, 333, 195, 29);

		limDeC_text = new Text(shell, SWT.BORDER);
		limDeC_text.setBounds(524, 380, 195, 29);

		paisID_combo = new Combo(shell, SWT.NONE);
		paisID_combo.setBounds(524, 423, 195, 29);

		impuestoID_combo = new Combo(shell, SWT.NONE);
		impuestoID_combo.setBounds(524, 470, 195, 29);

		bodegaID_combo = new Combo(shell, SWT.NONE);
		bodegaID_combo.setBounds(145, 458, 195, 29);

		Label fotoCliente = new Label(shell, SWT.BORDER);
		fotoCliente.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseUp(MouseEvent e) {
				JFileChooser imagenEscoger = new JFileChooser();
				imagenEscoger.showOpenDialog(null);
				f=imagenEscoger.getSelectedFile();
				if(!(f == null)){
					imgDir = f.getAbsolutePath();
					fotoCliente.setImage(SWTResourceManager.getImage(imgDir));
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


		if(imgDir == null)
			fotoCliente.setImage(SWTResourceManager.getImage(ClientEntry.class, "/resources/defaultProfilePic.png"));
		else
			fotoCliente.setImage(SWTResourceManager.getImage(imgDir));
		Button btnNewButton = new Button(shell, SWT.NONE);
		btnNewButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if (sql.UpdateCliente(dejarNumeros(id_Text.getText()), dejarLetras(txt_nombre.getText()), email__Text.getText(), dejarNumeros(telefono__Text.getText()),
						NIT__Text.getText(), direccion__Text.getText(), imgDir, dejarNumeros(numeroTarjeta_text.getText()),
						twitter_text.getText(), tiendaID_combo.getSelectionIndex(), dejarNumeros(limDeC_text.getText()), paisID_combo.getSelectionIndex(),
						impuestoID_combo.getSelectionIndex(), bodegaID_combo.getSelectionIndex())==0){
					JOptionPane.showMessageDialog(null, "Cliente actualizado exitosamente", "EXITO", JOptionPane.INFORMATION_MESSAGE);
					if(f != null)
						guardarFoto(f);
					else
						JOptionPane.showMessageDialog(null, "Problema Guardado La foto", "ERROR", JOptionPane.INFORMATION_MESSAGE);
				}
				else{
					JOptionPane.showMessageDialog(null, "Error al actualizar el cliente ver consola para errores", "ERROR", JOptionPane.INFORMATION_MESSAGE);
				}
			}
		});
		btnNewButton.setBounds(537, 525, 156, 29);
		btnNewButton.setText("Actualizar");

		Button btnEliminar = new Button(shell, SWT.NONE);
		btnEliminar.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if(sql.DeleteCliente(id_Text.getText())==0){
					JOptionPane.showMessageDialog(null, "Cliente eliminado exitosamente", "ERROR", JOptionPane.INFORMATION_MESSAGE);
				}
				else{
					JOptionPane.showMessageDialog(null, "Error al actualizar el cliente ver consola para errores", "ERROR", JOptionPane.INFORMATION_MESSAGE);
				}
				windowClose();
				ViewTable regresar = new ViewTable(command, "cliente"); //TODO cliente manualmente ingresado
				regresar.open();
			}
		});
		btnEliminar.setText("Eliminar");
		btnEliminar.setBounds(537, 557, 156, 29);

		Button btnRegresar = new Button(shell, SWT.NONE);
		btnRegresar.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				windowClose();
				ViewTable regresar = new ViewTable(command, "cliente");
				regresar.open();
			}
		});
		btnRegresar.setText("Regresar");
		btnRegresar.setBounds(537, 589, 156, 29);

		Label lblNewLabel_4 = new Label(shell, SWT.WRAP);
		lblNewLabel_4.setBounds(20, 346, 83, 46);
		lblNewLabel_4.setText("Numero de Tarjeta:");

		Label lblNewLabel_5 = new Label(shell, SWT.NONE);
		lblNewLabel_5.setBounds(33, 403, 71, 17);
		lblNewLabel_5.setText("Twitter:");

		lblNewLabel_6 = new Label(shell, SWT.NONE);
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
	/**
	 * @wbp.parser.entryPoint
	 */
	private void windowClose(){
		this.shell.setVisible(false);
	}

	/**
	 * @wbp.parser.entryPoint
	 */
	private void guardarFoto(File f){
		try {
			// retrieve image
			BufferedImage bi = ImageIO.read(f);
			File outputfile = new File(saveTo + "fotoParaGuardar");
			ImageIO.write(bi, "png", outputfile);
			System.out.println("Imagen Guardada");
		} catch (Exception e1) {
			e1.printStackTrace();
			System.out.println("Error en save image");
		}
	}
	private String dejarLetras(String quitarShit){
		String newstr = quitarShit.replaceAll("[^A-Za-z]+", "");
		return newstr;
	}
	private String dejarNumeros(String quitarShit){
		String newstr = quitarShit.replaceAll("[^\\d.]", "");
		return newstr;
	}
}
