package wb7.picturemess.java.gui;

import java.awt.Color;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.JTextField;

@SuppressWarnings("serial")
public class HintJTextField extends JTextField implements FocusListener{
	
	private String hint;

	//JTextField with no text only a hint
	public HintJTextField(String hint) {
		
		super(hint);
		this.hint = hint;
		
		addFocusListener(this);
		
		setForeground(Color.gray);
		
	}

	//JTextField with text and a hint
	public HintJTextField(String hint, String text) {
		
		super(text);
		this.hint = hint;
		
		addFocusListener(this);
		
	}
	
	//If the cursor joins the JTextField
	@Override
	public void focusGained(FocusEvent e) {
		if (getForeground() == Color.gray){
			setText("");
		}
	}

	//If the cursor leaves the JTextField
	@Override
	public void focusLost(FocusEvent e) {
		if(getText().isEmpty()){
			super.setText(hint);
			setForeground(Color.gray);
		}
	}

	//If you the text is changed from a class the colour must be black
	public void setText(String text) {
		super.setText(text);
		setForeground(Color.black);
	}

	//Updates the hint
	public void setHint(String hint){
		this.hint = hint;
		if(getForeground() == Color.gray)
			super.setText(hint);
	}

	
	
	
}
