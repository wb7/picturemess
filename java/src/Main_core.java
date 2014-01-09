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

import java.util.HashMap;

import wb7.picturemess.java.core.CreateAlbum;
import wb7.picturemess.java.core.CreateFiles;
import wb7.picturemess.java.core.Export;
import wb7.picturemess.java.core.Init;
import wb7.picturemess.java.core.RemoveAlbum;

public class Main_core {

	//The path to the directory with albums.xml, /desc etc.
	private static String path = "";
	//The width of the thumbnails.
	private static int width = 0;
	
	//The content of the albums.xml ordered by the directory-name. The Srting[] contains title, time and description.
	private static HashMap<String, String[]> folderTitleMap = new HashMap<>();
	//All xmls in /desc ordered by the album-directory-name and it contains a map witch ordered by the picture-name and contain the description
	private static HashMap<String, HashMap<String, String>> fileDescrMap = new HashMap<>();
	
	
	public static void main(String[] args) {
		
		//Loads the config.xml the albums.xml and the xml files for the albums.
		init();
		
		//Returns an error if no command is given.
		if(args.length < 1){
			System.err.println("Please give one command.");
			return;
		}
		
		//Checks the entered command
		if(args[0].equalsIgnoreCase("--create-files")){
			//Checks for an argument
			if(args.length >= 2){
				CreateFiles createFiles = new CreateFiles(path, fileDescrMap, folderTitleMap);
				//Returns an error if something went wrong.
				if(createFiles.createFiles(args[1]))
					System.out.println("Created / changed xml files.");
				else
					System.err.println("Something went wrong while creating files! :(");
			}else
				System.err.println("Please give one album or --all as argument.");
		}else if(args[0].equalsIgnoreCase("--export")){
			Export export = new Export(path, fileDescrMap, folderTitleMap, width);
			//Returns an error if something went wrong.
			if(export.export())
				System.out.println("Exported files to output.");
			else
				System.err.println("Something went wrong while exporting files! :(");
		}else if(args[0].equalsIgnoreCase("--create-album")){
			CreateAlbum createAlbum = new CreateAlbum(path, folderTitleMap);
			//Checks for an argument
			if(args.length > 1){
				//If no more arguments are given the title and the description will be a automatically generated String.
				String title;
				if(args.length > 2)
					title = args[2];
				else
					title = "New Album - " + args[1];
				String description;
				if(args.length > 3)
					description = args[3];
				else
					description = "An awesome album with great pictures.";
				
				//Returns an error if something went wrong.
				if(createAlbum.createAlbum(args[1], title, description))
					System.out.println("Created album " + args[1] + ".");
				else
					System.err.println("Something went wrong while creating an album! :(");
			}else
				System.err.println("Please give an folder to create the album there.");
		}else if(args[0].equalsIgnoreCase("--remove-album")){
				//Checks for an argument
				if(args.length >= 2){
					RemoveAlbum removeAlbum = new RemoveAlbum(path, folderTitleMap);
					//Returns an error if something went wrong.
					if(removeAlbum.removeAlbum(args[1]))
						System.out.println("Removed album.");
					else
						System.err.println("Something went wrong while removing album! :(");
				}else
					System.err.println("Please give an album.");
		}else
			System.err.println("Please give an command.");
		
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
	
}
