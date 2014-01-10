/*
 *  Copyright 2014 vilaureu
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

import java.io.File;
import java.util.HashMap;

public class RemoveAlbum {

	private HashMap<String, String[]> folderTitleMap;
	private String path;

	public RemoveAlbum(String path, HashMap<String, String[]> folderTitleMap) {
		
		this.path = path;
		this.folderTitleMap = folderTitleMap;
		
	}

	public boolean removeAlbum(String folder){
		
		//Checks if the album exists
		if(folderTitleMap.containsKey(folder)){
			
			//Removes the Strings in the folderTitleMap
			folderTitleMap.remove(folder);
			
			//Deletes the folder and the content in desc/ and images/
			Export.delete(new File(path + "images/" + folder));
			Export.delete(new File(path + "desc/" + folder + ".xml"));
			
			try {
				
				//Writes the albums.xml with the change
				WriteAlbumsXml writeAlbumsXml = new WriteAlbumsXml(folderTitleMap, path);
				writeAlbumsXml.writeAll();
				writeAlbumsXml.close();
			
			} catch (Exception e) {
				//Catches all errors
				e.printStackTrace();
				return false;
			}
			
			return true;
			
		}else{
			
			System.err.println("Album dose not exist!");
			System.err.println();
			
			return false;
			
		}
		
	}
	
}
