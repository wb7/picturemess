import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.HashMap;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;
import wb7.picturemess.java.core.CreateAlbum;
import wb7.picturemess.java.core.CreateFiles;
import wb7.picturemess.java.core.Init;
import wb7.picturemess.java.gui.CreateAlbumDialog;

public class Main_gui {

	private static HashMap<String, String[]> folderTitleMap;
	private static HashMap<String, HashMap<String, String>> fileDescrMap;
	@SuppressWarnings("unused")
	private static int width;
	private static String path;
	private static JFrame frame;
	private static CreateAlbumDialog cAlbumDialog;
	private static CreateAlbum cAlbum;
	private static CreateFiles cFiles;

	public static void main(String[] args) {
		
		//Loads the config.xml the albums.xml and the xml files for the albums.
		init();
		
		//Initialises some classes
		cAlbum = new CreateAlbum(path, folderTitleMap);
		cAlbumDialog = new CreateAlbumDialog(folderTitleMap, frame, cAlbum);
		cFiles = new CreateFiles(path, fileDescrMap, folderTitleMap);
		
		//Creates the frame and static includes
		createFrame();
		
	}
	
	private static void init() {
		
		Init init = new Init();
		
		//Loads the config.xml. Returns an error if something went wrong.
		if (init.loadConfig())
			System.out.println("config loaded.");
		else
			System.err.println("Something went wrong while loding config! :(");
		
		//A clear line for better structure.
		System.out.println();
		
		//Loads the albums.xml. Returns an error if something went wrong.
		if (init.initAlbumsxml())
			System.out.println("albums.xml loaded.");
		else
			System.err.println("Something went wrong while loding albums.xml! :(");
		
		//A clear line for better structure.
		System.out.println();
		
		//Loads the other xmls. Returns an error if something went wrong.
		if(init.initXmls(null))
			System.out.println("album xmls loaded.");
		else
			System.err.println("Something went wrong while loding album xmls! :(");
		
		//A clear line for better structure.
		System.out.println();
		
		path = init.path;
		width = init.width;
		fileDescrMap = init.fileDescrMap;
		folderTitleMap = init.folderTitleMap;
		
	}

	private static void createFrame() {
		
		//Creates the JFrame
		frame = new JFrame("picturemess");
		frame.setSize(700, 600);
		frame.setLocationRelativeTo(null);
		frame.setLayout(null);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		System.out.println("frame created.");
		System.out.println();
		
		JMenuBar menuBar = new JMenuBar();
		
		//Creates the JMenue File and set Alt+F as mnemonic
		JMenu file = new JMenu("File");
		file.setMnemonic(KeyEvent.VK_F);
		menuBar.add(file);
		
		//Creates the JMenueItem New album and set CTRL+N as mnemonic
		JMenuItem createAlbum = new JMenuItem("New album", KeyEvent.VK_N);
		createAlbum.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, ActionEvent.CTRL_MASK));
		//Adds the ActionListener
		createAlbum.addActionListener(new ActionListener() {
			@Override public void actionPerformed(ActionEvent actionEvent) {
				cAlbumDialog.createAlbumDialog();
			}
		});
		file.add(createAlbum);
		
		//Creates the JMenue Update xmls and set CTRL+N as mnemonic
		JMenuItem updateXmls = new JMenuItem("Update xmls", KeyEvent.VK_F5);
		updateXmls.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F5, 0));
		//Adds the ActionListener
		updateXmls.addActionListener(new ActionListener() {
			@Override public void actionPerformed(ActionEvent actionEvent) {
				if(cFiles.createFiles("--all")){
					//Shows a success dialog
					System.out.println("all files created");
					JOptionPane.showConfirmDialog(frame, "All xmls updated", "Updated", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE);
				}else{
					//Shows an error dialog
					System.out.println("Something went wrong while creating files! :(");
					JOptionPane.showConfirmDialog(frame, "Could not update xmls", "Update failed", JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE);	
				}
			}
		});
		file.add(updateXmls);
		
		//Adds the JMenueBar to the frame
		frame.setJMenuBar(menuBar);

	}

}
