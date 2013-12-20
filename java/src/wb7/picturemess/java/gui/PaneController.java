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

package wb7.picturemess.java.gui;

import java.util.HashMap;
import java.util.LinkedHashMap;

import javax.swing.JPanel;
import javax.swing.JScrollPane;

@SuppressWarnings("serial")
public class PaneController extends JScrollPane{

	@SuppressWarnings("unused")
	private HashMap<String, String[]> folderTitleMap;
	@SuppressWarnings("unused")
	private String path;
	@SuppressWarnings("unused")
	private HashMap<String, HashMap<String, String>> fileDescrMap;
	private JPanel mainPanel;
	@SuppressWarnings("unused")
	private LinkedHashMap<String, String> languageMap;

	public PaneController(String path, HashMap<String, String[]> folderTitleMap, HashMap<String, HashMap<String, String>> fileDescrMap, LinkedHashMap<String, String> languageMap) {
		
		this.path = path;
		this.folderTitleMap = folderTitleMap;
		this.fileDescrMap = fileDescrMap;
		this.languageMap = languageMap;
		
		//Creates the mainJPanel
		mainPanel = new MainPanel(path, folderTitleMap, fileDescrMap, languageMap);
		
		//Sets the mainPanle as View of the JScrollPane
		setViewportView(mainPanel);
		
	}
	
}
