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
