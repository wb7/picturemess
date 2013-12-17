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

import java.awt.Desktop;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.LinkedHashMap;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;

import vilaureu.sjkevDB.SjkevDBReader;
import wb7.picturemess.java.core.CreateAlbum;
import wb7.picturemess.java.core.CreateFiles;
import wb7.picturemess.java.core.Export;
import wb7.picturemess.java.core.Init;
import wb7.picturemess.java.gui.CreateAlbumDialog;

public class Main_gui {
	
	private static HashMap<String, String[]> folderTitleMap;
	private static HashMap<String, HashMap<String, String>> fileDescrMap;
	private static int width;
	private static String path;
	private static JFrame frame;
	private static CreateAlbumDialog cAlbumDialog;
	private static CreateAlbum cAlbum;
	private static CreateFiles cFiles;
	private static Export export;
	private static LinkedHashMap<String, String> languageMap;

	public static void main(String[] args) {
		
		//Loads the config.xml the albums.xml and the xml files for the albums.
		init();
		
		//Loads the language file
		SjkevDBReader reader = new SjkevDBReader(new File("language.sjkevDB"));
		try {
			languageMap = reader.read();
		} catch (IOException e) {
			System.out.println("Something went wrong while loading the language file! :(");
			e.printStackTrace();
		}
		
		//Initialises some classes
		cAlbum = new CreateAlbum(path, folderTitleMap);
		cAlbumDialog = new CreateAlbumDialog(folderTitleMap, frame, cAlbum, languageMap);
		cFiles = new CreateFiles(path, fileDescrMap, folderTitleMap);
		export = new Export(path, fileDescrMap, folderTitleMap, width);
		
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
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		System.out.println("frame created.");
		System.out.println();
		
		JMenuBar menuBar = new JMenuBar();
		
		//Creates the JMenue File and set Alt+F as mnemonic
		JMenu file = new JMenu(languageMap.get("menue-file"));
		file.setMnemonic(KeyEvent.VK_F);
		menuBar.add(file);
		
		//Creates the JMenueItem New album and set CTRL+N as mnemonic
		JMenuItem createAlbumItem = new JMenuItem(languageMap.get("menue-new album"), KeyEvent.VK_N);
		createAlbumItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, ActionEvent.CTRL_MASK));
		//Adds the ActionListener
		createAlbumItem.addActionListener(new ActionListener() {
			@Override public void actionPerformed(ActionEvent actionEvent) {
				cAlbumDialog.createAlbumDialog();
			}
		});
		file.add(createAlbumItem);
		
		//Creates the JMenueItem Update xmls and set CTRL+U as mnemonic
		JMenuItem updateXmlsItem = new JMenuItem(languageMap.get("menue-update"), KeyEvent.VK_U);
		updateXmlsItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_U, ActionEvent.CTRL_MASK));
		//Adds the ActionListener
		updateXmlsItem.addActionListener(new ActionListener() {
			@Override public void actionPerformed(ActionEvent actionEvent) {
				if(cFiles.createFiles("--all")){
					//Shows a success dialog
					System.out.println("all files created");
					JOptionPane.showConfirmDialog(frame, languageMap.get("succ pane-upd"), languageMap.get("succ title-upd"), JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE);
				}else{
					//Shows an error dialog
					System.out.println("Something went wrong while creating files! :(");
					JOptionPane.showConfirmDialog(frame, languageMap.get("error pane-upd"), languageMap.get("error title-upd"), JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE);	
				}
			}
		});
		file.add(updateXmlsItem);
		
		//Creates the JMenueItem Export and set CTRL+E as mnemonic
		JMenuItem exportItem = new JMenuItem(languageMap.get("menue-export"), KeyEvent.VK_E);
		exportItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_E, ActionEvent.CTRL_MASK));
		//Adds the ActionListener
		exportItem.addActionListener(new ActionListener() {
			@Override public void actionPerformed(ActionEvent actionEvent) {
				if(export.export()){
					//Shows a success dialog
					System.out.println("all files exported");
					JOptionPane.showConfirmDialog(frame, languageMap.get("succ pane-exp"), languageMap.get("succ title-exp"), JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE);
				}else{
					//Shows an error dialog
					System.out.println("Something went wrong while exporting files! :(");
					JOptionPane.showConfirmDialog(frame, languageMap.get("error pane-exp"), languageMap.get("error title-exp"), JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE);	
				}
			}
		});
		file.add(exportItem);
		
		//Creates the JMenueItem Show and set ALT+S as mnemonic
		JMenuItem showItem = new JMenuItem(languageMap.get("menue-show"), KeyEvent.VK_S);
		showItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, ActionEvent.ALT_MASK));
		//Adds the ActionListener
		showItem.addActionListener(new ActionListener() {
			@Override public void actionPerformed(ActionEvent actionEvent) {
				try {
					Desktop.getDesktop().browse(new URI("file://" + path + "output/index.html"));
				} catch (IOException | URISyntaxException e) {

					e.printStackTrace();
					
					//Shows an error dialog
					System.out.println("Something went wrong while opening export! :(");
					JOptionPane.showConfirmDialog(frame, languageMap.get("error pane-show"), languageMap.get("error title-show"), JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE);	
				}
			}
		});
		file.add(showItem);
		
		//Adds the JMenueBar to the frame
		frame.setJMenuBar(menuBar);

		//Makes the frame visibly
		frame.setVisible(true);
		
	}

}
