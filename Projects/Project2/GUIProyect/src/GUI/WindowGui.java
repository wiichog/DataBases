package GUI;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

import java.awt.GridLayout;

import org.eclipse.jface.dialogs.MessageDialog;
import BackEnd.CommandsSQL;
import BackEnd.implementCommands;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Label;
import org.eclipse.wb.swt.SWTResourceManager;

public class WindowGui {
	CommandsSQL commands = new implementCommands();
	protected Shell shell;
	private Text usrText;
	private Text passText;
	private Label lblNewLabel_2;
	private Label lblNewLabel_3;
	private Button confButton;
	private Label lblNewLabel_4;
	private final int xValue = 600;
	private final int yValue = 530;

	/**
	 * Launch the application.
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			System.out.println("on WindowGui");
			WindowGui window = new WindowGui();
			window.open();
		} catch (Exception e) {

			System.out.println("Error: WindowGui, not working properly");
		}
	}

	/**
	 * Open the window.
	 */
	public void open() {
		System.out.println("on ViewTableCompleta");
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
		shell.setSize(xValue, yValue);
		
		shell.setText("Data Base Manager Elite");

		usrText = new Text(shell, SWT.BORDER);
		usrText.setBounds(314, 336, 178, 29);

		passText = new Text(shell, SWT.BORDER | SWT.PASSWORD);
		passText.setBounds(314, 389, 178, 29);

		Label lblNewLabel = new Label(shell, SWT.NONE);
		lblNewLabel.setImage(SWTResourceManager.getImage(WindowGui.class, "/resources/icon_256x256.png"));
		lblNewLabel.setBounds(161, 10, 245, 249);

		Label lblNewLabel_1 = new Label(shell, SWT.NONE);
		lblNewLabel_1.setBounds(211, 348, 71, 17);
		lblNewLabel_1.setText("Username");

		lblNewLabel_2 = new Label(shell, SWT.NONE);
		lblNewLabel_2.setBounds(211, 401, 71, 17);
		lblNewLabel_2.setText("Password");

		lblNewLabel_3 = new Label(shell, SWT.NONE);
		lblNewLabel_3.setFont(SWTResourceManager.getFont("Ubuntu", 18, SWT.BOLD));
		lblNewLabel_3.setBounds(119, 269, 373, 36);
		lblNewLabel_3.setText("Database Manager Elite V0.8");

		confButton = new Button(shell, SWT.NONE);
		confButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if(!commands.Connect(usrText.getText(), passText.getText(),"CRM")){
					MessageDialog.openError(shell, "Error", "Usuario o contraseña incorrecta");
				} else if (commands.Connect(usrText.getText(), passText.getText(), "CRM")){
					closeWindow();
					try {
						ViewTable aftWindow = new ViewTable(commands, "cliente");
						aftWindow.open();
					} catch (Exception exception) {
						System.out.println("Error: aftWindow, not working properly");
					}
					
					
				}


			}
		});
		confButton.setBounds(359, 449, 97, 29);
		confButton.setText("Confirm");

		lblNewLabel_4 = new Label(shell, SWT.BORDER | SWT.CENTER);
		lblNewLabel_4.setFont(SWTResourceManager.getFont("Ubuntu", 11, SWT.ITALIC));
		lblNewLabel_4.setText("\nCreado por: \nGRUPO\n BASEDEDATOS");
		lblNewLabel_4.setBounds(48, 336, 131, 87);
	}
	protected void closeWindow(){
		this.shell.setVisible(false);
	}
}
