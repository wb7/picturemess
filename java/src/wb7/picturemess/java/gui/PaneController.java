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

import javax.swing.JScrollPane;

@SuppressWarnings("serial")
public class PaneController extends JScrollPane{

	private MainPanel mainPanel;
	private VariablesCollection variablesCollection;

	public PaneController(VariablesCollection variablesCollection) {
		
		this.variablesCollection = variablesCollection;
		
		//Creates the mainJPanel
		mainPanel = new MainPanel(variablesCollection);
		
		//Sets the mainPanle as View of the JScrollPane
		setViewportView(mainPanel);
		
	}
	
	public void update() {
		
		//Removes the old MainPanel
		remove(mainPanel);
		
		//Creates a new MainPanel
		mainPanel = new MainPanel(variablesCollection);

		//Sets the new mainPanle as View of the JScrollPane
		setViewportView(mainPanel);
		
	}
	
}
