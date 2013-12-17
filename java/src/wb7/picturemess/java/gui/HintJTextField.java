/*
 *  Copyright 2013 vilaureu
 *   
 *     This file is part of picturemess.
 *
 *  picturemess is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  picturemess is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with picturemess.  If not, see <http://www.gnu.org/licenses/>.
 * 
 */

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
