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

import java.awt.Color;
import java.awt.Font;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class AlbumPanel extends JPanel implements MouseListener{

	private int number;
	private String folder;
	private String[] descStrings;
	private VariablesCollection variablesCollection;
	
	public AlbumPanel(String folder, int i,
			VariablesCollection variablesCollection) {
		
		//Creates the JPanel without a LayoutManager
		super(null);
		
		this.variablesCollection = variablesCollection;
		this.folder = folder;
		number = i;
		
		//Gets the description, title, ... for the album
		descStrings = variablesCollection.folderTitleMap.get(folder);
		
		//Creates the JPanel
		createPanel();
		
		addMouseListener(this);
		
	}
	
	private void createPanel() {
		
		//Sets the size and the location
		setBounds(50, 80 + number*200, 400, 150);
		
		//Sets the colour
		float[] color = Color.RGBtoHSB(230, 230, 230, null);
		setBackground(Color.getHSBColor(color[0], color[1], color[2]));
		
		//Creates the title label
		JLabel titleLabel = new JLabel(descStrings[0]);
		titleLabel.setBounds(10, 10, 300, 40);
		titleLabel.setFont(new Font(null, Font.BOLD, 18));
		add(titleLabel);
		
		//Creates the description label
		JLabel descLabel = new JLabel(descStrings[2]);
		descLabel.setBounds(10, 40, 300, 40);
		descLabel.setFont(new Font(null, Font.BOLD, 14));
		add(descLabel);
		
		//Creates the date label
		JLabel dateLabel = new JLabel(descStrings[1]);
		dateLabel.setBounds(10, 70, 300, 40);
		dateLabel.setFont(new Font(null, 0, 14));
		add(dateLabel);
		
		//Creates the folder label
		JLabel folderLabel = new JLabel("desc/" + folder + "/");
		folderLabel.setBounds(10, 100, 300, 40);
		add(folderLabel);
		
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		
		//Checks the ClickCount
		if(e.getClickCount() == 1){
			
			//Checks the colour 
			if(getBackground().getRed() == 230){
				
				//Changes the colour
				float[] color = Color.RGBtoHSB(220, 220, 230, null);
				setBackground(Color.getHSBColor(color[0], color[1], color[2]));
	
				//Sets a border
				float[] color2 = Color.RGBtoHSB(100, 100, 200, null);
				setBorder(BorderFactory.createLineBorder(Color.getHSBColor(color2[0], color2[1], color2[2])));
				
				//Adds the album to the selected albums
				variablesCollection.selectedAlbums.add(folder);
				
			}else{
				
				//Sets the colour to the default colour
				float[] color = Color.RGBtoHSB(230, 230, 230, null);
				setBackground(Color.getHSBColor(color[0], color[1], color[2]));
				
				//Removes the border
				setBorder(null);
				
				//Removes the album from the selected albums
				variablesCollection.selectedAlbums.remove(folder);
				
			}
			
		}
		
	}

	@Override public void mouseEntered(MouseEvent e) {}
	@Override public void mouseExited(MouseEvent e) {}
	@Override public void mousePressed(MouseEvent e) {}
	@Override public void mouseReleased(MouseEvent e) {}
	
}
