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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;

import javax.swing.JFrame;

import wb7.picturemess.java.core.CreateAlbum;
import wb7.picturemess.java.core.CreateFiles;
import wb7.picturemess.java.core.Export;
import wb7.picturemess.java.core.RemoveAlbum;

public class VariablesCollection {

	public LinkedHashMap<String, String> languageMap;
	public Export export;
	public CreateFiles cFiles;
	public CreateAlbum cAlbum;
	public String path;
	public int width;
	public HashMap<String, HashMap<String, String>> fileDescrMap;
	public HashMap<String, String[]> folderTitleMap;
	public JFrame frame;
	public ArrayList<String> selectedAlbums = new ArrayList<>();
	public CreateAlbumDialog cAlbumDialog;
	public Menu menu;
	public RemoveAlbum rAlbum;

	//A Collection for variables
	public VariablesCollection(HashMap<String, String[]> folderTitleMap, HashMap<String,
			HashMap<String, String>> fileDescrMap, int width, String path, JFrame frame, CreateAlbum cAlbum, CreateFiles cFiles,
			Export export, LinkedHashMap<String, String> languageMap, RemoveAlbum rAlbum) {
		
		this.folderTitleMap = folderTitleMap;
		this.fileDescrMap = fileDescrMap;
		this.width = width;
		this.path = path;
		this.frame = frame;
		this.cAlbum = cAlbum;
		this.cFiles = cFiles;
		this.export = export;
		this.languageMap = languageMap;
		this.rAlbum = rAlbum;
		
	}

}
