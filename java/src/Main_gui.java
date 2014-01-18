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

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedHashMap;

import javax.swing.JFrame;
import vilaureu.sjkevDB.SjkevDBReader;
import wb7.picturemess.java.core.CreateAlbum;
import wb7.picturemess.java.core.CreateFiles;
import wb7.picturemess.java.core.Export;
import wb7.picturemess.java.core.Init;
import wb7.picturemess.java.core.RemoveAlbum;
import wb7.picturemess.java.gui.CreateAlbumDialog;
import wb7.picturemess.java.gui.Menu;
import wb7.picturemess.java.gui.PaneController;
import wb7.picturemess.java.gui.Update;
import wb7.picturemess.java.gui.VariablesCollection;

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
	private static VariablesCollection variablesCollection;
	private static RemoveAlbum rAlbum;
	private static Init init;

	public static void main(String[] args) {
		
		//Loads the config.xml, the albums.xml and the xml files for the albums.
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
		cFiles = new CreateFiles(path, fileDescrMap, folderTitleMap);
		export = new Export(path, fileDescrMap, folderTitleMap, width);
		rAlbum = new RemoveAlbum(path, folderTitleMap);
		
		variablesCollection = new VariablesCollection(folderTitleMap, fileDescrMap, width, path, frame, cAlbum, cFiles, export, languageMap, rAlbum, init);
		
		Update update = new Update(variablesCollection);
		variablesCollection.update = update;
		
		cAlbumDialog = new CreateAlbumDialog(variablesCollection);
		variablesCollection.cAlbumDialog = cAlbumDialog;
		
		//Creates the frame and static includes
		createFrame();
		
	}
	
	private static void init() {
		
		init = new Init();
		
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
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		System.out.println("frame created.");
		System.out.println();
		
		//Function to create the Menu
		variablesCollection.menu = new Menu(variablesCollection);
		frame.setJMenuBar(variablesCollection.menu);
		System.out.println("menu created");
		System.out.println();
		
		//Creates a PaneController that controls the JPanels and the JScrollPane
		PaneController paneController = new PaneController(variablesCollection);
		variablesCollection.paneController = paneController;
		
		//Adds the JScrollPane from the paneController and validates the frame
		frame.add(paneController);
		frame.validate();
		
		//Makes the frame visibly
		frame.setVisible(true);
		
	}

}
