package wb7.picturemess.java.gui;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import wb7.picturemess.java.core.CreateAlbum;

public class CreateAlbumDialog{

	private static JDialog dialog;
	private JFrame frame;
	private HashMap<String, String[]> folderTitleMap;
	private CreateAlbum cAlbum;

	public CreateAlbumDialog(HashMap<String, String[]> folderTitleMap, JFrame frame, CreateAlbum cAlbum) {
		
		this.frame = frame;
		this.folderTitleMap = folderTitleMap;
		this.cAlbum = cAlbum;
		
	}
	
	public void createAlbumDialog() {
		
		//Creates a JDialog for the JFrame frame
		dialog = new JDialog(frame);
		dialog.setSize(500, 400);
		dialog.setLocationRelativeTo(null);
		dialog.setLayout(null);
		dialog.setVisible(true);
		
		//Creates the headline
		JLabel headline = new JLabel("Create a new album");
		headline.setBounds(150, 10, 300, 50);
		headline.setFont(new Font(null, Font.BOLD, 20));
		dialog.add(headline);
		
		//Creates the folderLabel
		JLabel folder = new JLabel("Folder:");
		folder.setFont(new Font(null, 0, 15));
		folder.setBounds(40, 60, 100, 30);
		dialog.add(folder);
		
		//Creates the folderTextField
		final JTextField folderTextField = new JTextField();
		folderTextField.setBounds(40, 90, 400, 30);
		dialog.add(folderTextField);
		
		//Creates the titleLabel
		JLabel title = new JLabel("Title:");
		title.setFont(new Font(null, 0, 15));
		title.setBounds(40, 130, 100, 30);
		dialog.add(title);
		
		//Creates the titleTextField
		final JTextField titleTextField = new JTextField();
		titleTextField.setBounds(40, 160, 400, 30);
		dialog.add(titleTextField);
		
		//Creates the descriptionLabel
		JLabel description = new JLabel("Description:");
		description.setFont(new Font(null, 0, 15));
		description.setBounds(40, 210, 100, 30);
		dialog.add(description);
		
		//Creates the descriptionTextField
		final JTextField descriptionTextField = new JTextField();
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
		System.out.println("Creates dialog");
		
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
		if(folderTitleMap.containsKey(folder)){
			
			//Opens an error-dialog
			JOptionPane.showConfirmDialog(dialog, "This album dose already exist.", "Album exists", JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE);
			
			//Outputs an error-message
			System.err.println("Album already exists");
			
			return;
			
		}
		
		//Outputs an error if the folder is ""
		if(folder.equals("")){
			
			//Opens an error-dialog
			JOptionPane.showConfirmDialog(dialog, "Please, enter a word/name for the folder.", "Folder must not be nothing", JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE);
			
			//Outputs an error-message
			System.err.println("Album already exists");
			
			return;
			
		}
		
		//Creates the album and checks if all is ok
		if(cAlbum.createAlbum(folder, title, description)){
			
			//Opens a success-dialog
			JOptionPane.showConfirmDialog(dialog, "Album \"" + folder + "\" created.", "Album created", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE);
			
			//Outputs a message of success
			System.out.println("Creates album " + folder);
		
			//Closes the dialog
			dialog.dispose();
			
		}else{
			
			//Opens an error-dialog
			JOptionPane.showConfirmDialog(dialog, "Failed to create album " + folder + ".", "Could not create the album", JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE);
			
			//Outputs an error-message
			System.out.println("Creates album " + folder);
			
		}
		
		
	}

	
	
}
