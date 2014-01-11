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

import java.awt.Font;
import javax.swing.JLabel;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class MainPanel extends JPanel{

	private VariablesCollection variablesCollection;

	public MainPanel(VariablesCollection variablesCollection) {
		
		//Creates the JPanel without a LayoutManager
		super(null);
		
		this.variablesCollection = variablesCollection;
		
		//Creates the JPanel
		createPanel();
		
	}

	private void createPanel() {
		
		//Creates the headline
		JLabel headline = new JLabel(variablesCollection.languageMap.get("main panel->headline"));
		headline.setBounds(50, 20, 200, 50);
		headline.setFont(new Font(null, Font.BOLD, 20));
		add(headline);
		
		//Creates all AlbumPanels
		int i = 0;
		for (String folder : variablesCollection.folderTitleMap.keySet()) {
			
			AlbumPanel albumPanel = new AlbumPanel(folder, i, variablesCollection);
			
			add(albumPanel);
			
			i++;
			
		}
		
	}

}
