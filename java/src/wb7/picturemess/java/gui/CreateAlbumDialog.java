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
	private String path;
	private JFrame frame;
	private HashMap<String, String[]> folderTitleMap;
	private CreateAlbum cAlbum;

	public CreateAlbumDialog(HashMap<String, String[]> folderTitleMap, JFrame frame, CreateAlbum cAlbum) {
		
		this.frame = frame;
		this.folderTitleMap = folderTitleMap;
		this.cAlbum = cAlbum;
		
	}
	
	public void createAlbumDialog() {
		
		dialog = new JDialog(frame);
		dialog.setSize(500, 400);
		dialog.setLocationRelativeTo(null);
		dialog.setLayout(null);
		dialog.setVisible(true);
		
		JLabel headline = new JLabel("Create a new album");
		headline.setBounds(150, 10, 300, 50);
		headline.setFont(new Font(null, Font.BOLD, 20));
		dialog.add(headline);
		
		JLabel folder = new JLabel("Folder:");
		folder.setFont(new Font(null, 0, 15));
		folder.setBounds(40, 60, 100, 30);
		dialog.add(folder);
		
		final JTextField folderTextField = new JTextField();
		folderTextField.setBounds(40, 90, 400, 30);
		dialog.add(folderTextField);
		
		JLabel title = new JLabel("Title:");
		title.setFont(new Font(null, 0, 15));
		title.setBounds(40, 130, 100, 30);
		dialog.add(title);
		
		final JTextField titleTextField = new JTextField();
		titleTextField.setBounds(40, 160, 400, 30);
		dialog.add(titleTextField);
		
		JLabel description = new JLabel("Description:");
		description.setFont(new Font(null, 0, 15));
		description.setBounds(40, 210, 100, 30);
		dialog.add(description);
		
		final JTextField descriptionTextField = new JTextField();
		descriptionTextField.setBounds(40, 240, 400, 30);
		dialog.add(descriptionTextField);
		
		JButton button = new JButton("create");
		button.setBounds(140, 280, 200, 40);
		button.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent actionEvent) {
				
				createAlbum(folderTextField, titleTextField, descriptionTextField);
				
			}
		});
		dialog.add(button);
		
		System.out.println("Creates dialog");
		
	}

	protected void createAlbum(JTextField folderTextField, JTextField titleTextField, JTextField descriptionTextField) {
		
		String folder = folderTextField.getText();
		String title = titleTextField.getText();
		String description = descriptionTextField.getText();
		
		if(folderTitleMap.containsKey(folder)){
			
			JOptionPane.showConfirmDialog(dialog, "This album dose already exist.", "Album exists", JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE);

			System.err.println("Album already exists");
			
		}else{
			
			cAlbum.createAlbum(folder, title, description);
			
			System.out.println("Creates album " + folder);
			
		}
		
		
		
	}

	
	
}
