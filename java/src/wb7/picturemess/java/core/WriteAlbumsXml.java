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

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;

public class WriteAlbumsXml extends BufferedWriter{

	private HashMap<String, String[]> folderTitleMap;
	
	public WriteAlbumsXml(HashMap<String, String[]> folderTitleMap, String path) throws IOException {
		
		super(new FileWriter(new File(path + "albums.xml")));
		
		this.folderTitleMap = folderTitleMap;
		
	}

	public void writeAll() throws IOException {
		
		//Writes start
		write("<albums>\n");
		
		//Writes the content of the folderTitleMap using a loop
		for (String tmpFolder : folderTitleMap.keySet()) {
			
			String[] value = folderTitleMap.get(tmpFolder);
			
			write("  <album>\n");
			write("    <title>" + value[0] + "</title>\n");
			write("    <folder>" + tmpFolder + "</folder>\n");
			write("    <date>" + value[1] + "</date>\n");
			write("    <description>" + value[2] + "</description>\n");
			write("  </album>\n");
		}
		
		//Writes end
		write("</albums>\n");
		
	}
	
	
}
