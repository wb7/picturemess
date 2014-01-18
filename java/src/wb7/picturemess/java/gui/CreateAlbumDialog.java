/*
 *  Copyright 2013, 2014 vilaureu
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

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

public class CreateAlbumDialog{

	private static JDialog dialog;
	private VariablesCollection variablesCollection;
	
	public CreateAlbumDialog(VariablesCollection variablesCollection) {
		
		this.variablesCollection = variablesCollection;
		
	}

	public void createAlbumDialog() {
		
		//Creates a JDialog for the JFrame frame
		dialog = new JDialog(variablesCollection.frame, variablesCollection.languageMap.get("cad->headline"));
		dialog.setSize(500, 400);
		dialog.setLocationRelativeTo(null);
		dialog.setLayout(null);
		dialog.setVisible(true);
		
		//Creates the headline
		JLabel headline = new JLabel(variablesCollection.languageMap.get("cad->headline"));
		headline.setBounds(150, 10, 300, 50);
		headline.setFont(new Font(null, Font.BOLD, 20));
		dialog.add(headline);
		
		//Creates the titleLabel
		JLabel title = new JLabel(variablesCollection.languageMap.get("cad->title"));
		title.setFont(new Font(null, 0, 15));
		title.setBounds(40, 130, 100, 30);
		dialog.add(title);
		
		//Creates the titleTextField with a hint
		final HintJTextField titleTextField = new HintJTextField(variablesCollection.languageMap.get("cad->title field"));
		titleTextField.setBounds(40, 160, 400, 30);
		dialog.add(titleTextField);
		
		//Creates the folderLabel
		JLabel folder = new JLabel(variablesCollection.languageMap.get("cad->folder"));
		folder.setFont(new Font(null, 0, 15));
		folder.setBounds(40, 60, 100, 30);
		dialog.add(folder);
		
		//Creates the folderTextField
		@SuppressWarnings("serial")
		final SensetiveJTextField folderTextField = new SensetiveJTextField() {
			
			//What to do if the value changed
			@Override
			void onChanged() {
				//Update the hint of the titleTextField
				titleTextField.setHint(variablesCollection.languageMap.get("cad->title field") + this.getText());
			}
			
		};
		folderTextField.setBounds(40, 90, 400, 30);
		dialog.add(folderTextField);
		
		//Creates the descriptionLabel
		JLabel description = new JLabel(variablesCollection.languageMap.get("cad->descr"));
		description.setFont(new Font(null, 0, 15));
		description.setBounds(40, 210, 100, 30);
		dialog.add(description);
		
		//Creates the descriptionTextField with a hint
		final HintJTextField descriptionTextField = new HintJTextField(variablesCollection.languageMap.get("cad->descr field"));
		descriptionTextField.setBounds(40, 240, 400, 30);
		dialog.add(descriptionTextField);
		
		//Creates the createButton
		JButton button = new JButton("create");
		button.setBounds(140, 280, 200, 40);
		//Adds the ActionListener
		button.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent actionEvent) {
				
				createAlbum(folderTextField, titleTextField, descriptionTextField);
				
			}
		});
		dialog.add(button);
		
		//Outputs a message of success
		System.out.println("creates create dialog");
		System.out.println();
		
	}

	protected void createAlbum(JTextField folderTextField, JTextField titleTextField, JTextField descriptionTextField) {
		
		//Gets the text from the Textfields
		String folder = folderTextField.getText();
		String title = titleTextField.getText();
		String description = descriptionTextField.getText();
		
		//If no more arguments are given the title and the description will be a automatically generated String.
		if(title.equals(""))
			title = "New Album - " + folder;
		if(description.equals(""))
			description = "An awesome album with great pictures.";
		
		//Checks if the album already exists
		if(variablesCollection.folderTitleMap.containsKey(folder)){
			
			//Opens an error-dialog
			JOptionPane.showConfirmDialog(dialog, "This album dose already exist.", "Album exists", JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE);
			
			//Outputs an error-message
			System.err.println("album already exists");
			
			return;
			
		}
		
		//Outputs an error if the folder is ""
		if(folder.equals("")){
			
			//Opens an error-dialog
			JOptionPane.showConfirmDialog(dialog, "Please, enter a word/name for the folder.", "Folder must not be nothing", JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE);
			
			//Outputs an error-message
			System.err.println("album must not be \"\"");
			
			return;
			
		}
		
		//Creates the album and checks if all is ok
		if(variablesCollection.cAlbum.createAlbum(folder, title, description)){
			
			//Updates the MainPanel and the folderTitleMap
			variablesCollection.update.updateAlbumsXml();
			
			//Opens a success-dialog
			JOptionPane.showConfirmDialog(dialog, "Album \"" + folder + "\" created.", "Album created", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE);
			
			//Outputs a message of success
			System.out.println("creates album " + folder);
			System.out.println();
			
			//Closes the dialog
			dialog.dispose();
			
		}else{
			
			//Opens an error-dialog
			JOptionPane.showConfirmDialog(dialog, "Failed to create album " + folder + ".", "Could not create the album", JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE);
			
			//Outputs an error-message
			System.out.println("Something went wrong while creating an album! :(");
			System.out.println();
			
		}
		
		
	}

	
	
}
