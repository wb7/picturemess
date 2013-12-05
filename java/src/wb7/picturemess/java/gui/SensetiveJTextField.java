package wb7.picturemess.java.gui;

import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

@SuppressWarnings("serial")
public abstract class SensetiveJTextField extends JTextField implements DocumentListener{

	//JTextField with no text
	public SensetiveJTextField() {

		//Creates the JTextField
		super();
		
		//Adds the listener
		super.getDocument().addDocumentListener(this);
		
	}
	
	//JTextField with text
	public SensetiveJTextField(String text) {
		
		//Creates the JTextField
		super(text);
		
		//Adds the listener
		super.getDocument().addDocumentListener(this);
		
	}
	
	//What to do if something changes
	abstract void onChanged();
	
	//If an argument changes (?)
	@Override
	public void changedUpdate(DocumentEvent e) {
		onChanged();
	}
	
	//If something is written in the JTextFiled
	@Override
	public void insertUpdate(DocumentEvent e) {
		onChanged();
	}

	//If something is deleted from the JTextFiled
	@Override
	public void removeUpdate(DocumentEvent e) {
		onChanged();
	}
	
}
