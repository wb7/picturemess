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

package wb7.picturemess.java.core;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.HashMap;

public class CreateFiles {

	//The content of the albums.xml ordered by the directory-name. The Srting[] contains title, time and description.
	private HashMap<String, String[]> folderTitleMap = new HashMap<>();
	//All xmls in /desc ordered by the album-directory-name and it contains a map witch ordered by the picture-name and contain the description
	private HashMap<String, HashMap<String, String>> fileDescrMap = new HashMap<>();
	//The path to the directory with albums.xml, /desc etc.
	private String path;

	public CreateFiles(String path,
			HashMap<String, HashMap<String, String>> fileDescrMap,
			HashMap<String, String[]> folderTitleMap) {
		
		this.path = path;
		this.fileDescrMap = fileDescrMap;
		this.folderTitleMap = folderTitleMap;
		
	}

	public boolean createFiles(String album) {

		//Activates the loop if the argument is --all
		if(album.equalsIgnoreCase("--all")){
			
			for (String folder : folderTitleMap.keySet()) {
				if(!createFiles(folder))
					return false;
			}
			
			return true;
			
		}
		
		//A temporary hashMap.
		HashMap<String, String> newFileDescrMap = new HashMap<>();
		
		//The Image folder of the album.
		File folder = new File(path + "images/" + album + "/");
		
		//All files (pictures) in the folder
		for (File file : folder.listFiles()) {
			
			String filename = file.getName();
			//Adds the files to the newFileDescrMap and if in the fileDescrMap is a value the value will be copy.
			if(fileDescrMap.get(album).containsKey(filename)){
				newFileDescrMap.put(filename, fileDescrMap.get(album).get(filename));
			}else{
				newFileDescrMap.put(filename, "");
			}
		}
		
		
		try {
			//Opens the file
			BufferedWriter writer = new BufferedWriter(new FileWriter(new File(path + "desc/" + album + ".xml")));
		
			//Writes start
			writer.write("<album>\n");
			
			//Writes the content of the newFileDescrMap using a loop
			for (String file : newFileDescrMap.keySet()) {
				
				writer.write("  <file>\n");
				writer.write("    <filename>" + file + "</filename>\n");
				writer.write("    <description>" + newFileDescrMap.get(file) + "</description>\n");
				writer.write("  </file>\n");
			}
			
			//Writes end
			writer.write("</album>\n");
		
			//Closes the writer
			writer.close();
		
		} catch (Exception e) {
			//Catches all errors
			e.printStackTrace();
			return false;
		}
		
		//Outputs a message of success
		System.out.println(album + ".xml created / changed.");
		
		return true;
		
	}
	
}
