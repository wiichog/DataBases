package GUI;

import org.eclipse.swt.widgets.Composite;
import BackEnd.*;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;

public class DataBaseInformationDisplay extends Composite {
	public List givenList;
	private String[] tables;
	/**
	 * Create the composite.
	 * @param parent
	 * @param style
	 */
	public DataBaseInformationDisplay(Composite parent, int style, CommandsSQL command) {
		
		super(parent, style);
		System.out.println("on DataBaseInformationDisplay");
		givenList = new List(this, SWT.BORDER);
		givenList.setBounds(20, 33, 406, 241);
		tables = command.GETTABLESOFDATABASE();
		
		for (int i = 0; i < tables.length;i++){
			givenList.add(tables[i]);
		}
		
		Label lblNewLabel = new Label(this, SWT.NONE);
		lblNewLabel.setBounds(20, 10, 155, 17);
		lblNewLabel.setText("Select Table");
	} 
	protected void closeWindow(){
		this.setVisible(false);
	}
	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}
	public String selectedOnList(){
		return tables[givenList.getSelectionIndex()].toString();
	}
	
}
