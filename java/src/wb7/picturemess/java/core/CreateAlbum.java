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
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

public class CreateAlbum {

	private HashMap<String, String[]> folderTitleMap;
	private String path;

	public CreateAlbum(String path, HashMap<String, String[]> folderTitleMap) {
		
		this.path = path;
		this.folderTitleMap = folderTitleMap;
		
	}
	
	public Boolean createAlbum(String folder, String title, String description) {
		
		//Checks if the album exists
		if(!folderTitleMap.containsKey(folder)){
		
			//Creates the actually date
			String date = (new SimpleDateFormat("dd MMMMM yyyy")).format(Calendar.getInstance().getTime());
			
			//It will put the Strings in the folderTitleMap
			folderTitleMap.put(folder, new String[]{title, date, description});
			
			try {
				//Opens and creates the file
				BufferedWriter writer = new BufferedWriter(new FileWriter(new File(path + "albums.xml")));
				
				//Writes start
				writer.write("<albums>\n");
				
				//Writes the content of the folderTitleMap using a loop
				for (String tmpFolder : folderTitleMap.keySet()) {
					
					String[] value = folderTitleMap.get(tmpFolder);
					
					writer.write("  <album>\n");
					writer.write("    <title>" + value[0] + "</title>\n");
					writer.write("    <folder>" + tmpFolder + "</folder>\n");
					writer.write("    <date>" + value[1] + "</date>\n");
					writer.write("    <description>" + value[2] + "</description>\n");
					writer.write("  </album>\n");
				}
				
				//Writes end
				writer.write("</albums>\n");
				
				//Closes the writer
				writer.close();
			
			} catch (Exception e) {
				//Catches all errors
				e.printStackTrace();
				return false;
			}
			
			return true;
			
		}else{
			
			System.err.println("Album already exists!");
			System.err.println();
			
			return false;
			
		}
		
	}

}
