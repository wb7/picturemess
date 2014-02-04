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

package wb7.picturemess.java.gui;

import java.util.HashMap;

import wb7.picturemess.java.core.Init;

public class InitGui {

	private Init init;
	public VariablesCollection variablesCollection;
	public HashMap<String, String[]> folderTitleMap;
	public HashMap<String, HashMap<String, String>> fileDescrMap;
	public int width;
	public String path;

	public InitGui() {
		init = new Init();
		
	}
	
	public boolean init() {
		
		boolean succes = true;
		
		//Loads the config.xml. Returns an error if something went wrong.
		if (init.loadConfig())
			System.out.println("config loaded.");
		else{
			System.err.println("Something went wrong while loding config! :(");
			succes = false;
		}
		
		//A clear line for better structure.
		System.out.println();
		
		//Loads the albums.xml. Returns an error if something went wrong.
		if (init.initAlbumsxml())
			System.out.println("albums.xml loaded.");
		else{
			System.err.println("Something went wrong while loding albums.xml! :(");
			succes = false;
		}
		
		//A clear line for better structure.
		System.out.println();
		
		//Loads the other xmls. Returns an error if something went wrong.
		if(init.initXmls(null))
			System.out.println("album xmls loaded.");
		else{
			System.err.println("Something went wrong while loding album xmls! :(");
			succes = false;
		}
		
		//A clear line for better structure.
		System.out.println();
		
		path = init.path;
		width = init.width;
		fileDescrMap = init.fileDescrMap;
		folderTitleMap = init.folderTitleMap;
		
		return succes;
		
	}
	
}
