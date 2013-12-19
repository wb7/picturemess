/*
 *  Copyright 2013 vilaureu
 *   
 *     This file is part of sjkevDB.
 *
 *  sjkevDB is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  sjkevDB is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with sjkevDB.  If not, see <http://www.gnu.org/licenses/>.
 * 
 */

package vilaureu.sjkevDB;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedHashMap;

public class SjkevDBReader {
	
	//Creates a clear LinkedHashMap
	private LinkedHashMap<String, String> map = new LinkedHashMap<>();
	//This are default configurations
	private String split = ": ";
	private String backslashN = "\\n";
	private String backslashR = "\\r";
	
	private File file;
	
	//Function to configure all (best for complex projects)
	public SjkevDBReader(File file, String split, String backslashN, String backslashR) {
		
		this.file = file;
		this.split = split;
		this.backslashN = backslashN;
		this.backslashR = backslashR;
		
	}
	
	//Function to configure the split-String (best for simple databases)
	public SjkevDBReader(File file, String split) {
		
		this.file = file;
		this.split = split;
		
	}
	
	//Function to configure nothing and use all defaults (best for config-files)
	public SjkevDBReader(File file) {
		
		this.file = file;
		
	}
	
	//Reads the file and puts the content into a LinkedHashMap with two Strings
	public LinkedHashMap<String, String> read() throws IOException {
		
		//Clears the map
		map.clear();
		
		//Opens the file
		BufferedReader reader = new BufferedReader(new FileReader(file));
		
		//Reads all lines
		String line;
		for (line = reader.readLine(); line != null; line = reader.readLine()) {
			
			//convertLine()
			String[] convertedLine = convertLine(line);
			
			//Puts the convertedString into the map
			map.put(convertedLine[0], convertedLine[1]);
			
		}
		
		//Closes the file
		reader.close();
		
		//Returns the map with the content of the file
		return map;
		
	}

	//Replaces the line breaks and splits the line
	private String[] convertLine(String line) {
		
		//Replaces the line break symbols to real line breaks
		line = line.replace(backslashN, "\n");
		line = line.replace(backslashR, "\r");
		
		//Splits the line by the split String
		String[] splitedLine = line.split(split, 2);
		
		//Returns the convertedLine
		return splitedLine;
		
	}
	
}
