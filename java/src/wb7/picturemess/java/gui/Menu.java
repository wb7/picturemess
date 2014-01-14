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

import java.awt.Desktop;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;

@SuppressWarnings("serial")
public class Menu extends JMenuBar{

	public Menu(final VariablesCollection variablesCollection) {
		
		super();
		
		//Creates the JMenu File and set Alt+F as mnemonic
		JMenu file = new JMenu(variablesCollection.languageMap.get("menu->file"));
		file.setMnemonic(KeyEvent.VK_F);
		add(file);
		
		//Creates the JMenuItem New album and set CTRL+N as mnemonic
		JMenuItem createAlbumItem = new JMenuItem(variablesCollection.languageMap.get("menu->new album"), KeyEvent.VK_N);
		createAlbumItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, ActionEvent.CTRL_MASK));
		//Adds the ActionListener
		createAlbumItem.addActionListener(new ActionListener() {
			@Override public void actionPerformed(ActionEvent actionEvent) {
				variablesCollection.cAlbumDialog.createAlbumDialog();
			}
		});
		file.add(createAlbumItem);
		
		//Creates the JMenuItem Update xmls and set CTRL+U as mnemonic
		JMenuItem updateXmlsItem = new JMenuItem(variablesCollection.languageMap.get("menu->update"), KeyEvent.VK_U);
		updateXmlsItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_U, ActionEvent.CTRL_MASK));
		//Adds the ActionListener
		updateXmlsItem.addActionListener(new ActionListener() {
			@Override public void actionPerformed(ActionEvent actionEvent) {
				if(variablesCollection.cFiles.createFiles("--all")){
					//Shows a success dialog
					System.out.println("Created / changed xml files.");
					JOptionPane.showConfirmDialog(variablesCollection.frame, variablesCollection.languageMap.get("succ pane->upd"), 
							variablesCollection.languageMap.get("succ title->upd"), JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE);
				}else{
					//Shows an error dialog
					System.out.println("Something went wrong while creating files! :(");
					JOptionPane.showConfirmDialog(variablesCollection.frame, variablesCollection.languageMap.get("error pane->upd"), variablesCollection.languageMap.get("error title->upd"), 
							JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE);	
				}
			}
		});
		file.add(updateXmlsItem);
		
		//Creates the JMenuItem Export and set CTRL+E as mnemonic
		JMenuItem exportItem = new JMenuItem(variablesCollection.languageMap.get("menu->export"), KeyEvent.VK_E);
		exportItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_E, ActionEvent.CTRL_MASK));
		//Adds the ActionListener
		exportItem.addActionListener(new ActionListener() {
			@Override public void actionPerformed(ActionEvent actionEvent) {
				if(variablesCollection.export.export()){
					//Shows a success dialog
					System.out.println("all files exported");
					JOptionPane.showConfirmDialog(variablesCollection.frame, variablesCollection.languageMap.get("succ pane->exp"), 
							variablesCollection.languageMap.get("succ title->exp"), JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE);
				}else{
					//Shows an error dialog
					System.out.println("Something went wrong while exporting files! :(");
					JOptionPane.showConfirmDialog(variablesCollection.frame, variablesCollection.languageMap.get("error pane->exp"), 
							variablesCollection.languageMap.get("error title->exp"), JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE);	
				}
			}
		});
		file.add(exportItem);
		
		//Creates the JMenuItem Show and set ALT+S as mnemonic
		JMenuItem showItem = new JMenuItem(variablesCollection.languageMap.get("menu->show"), KeyEvent.VK_S);
		showItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, ActionEvent.ALT_MASK));
		//Adds the ActionListener
		showItem.addActionListener(new ActionListener() {
			@Override public void actionPerformed(ActionEvent actionEvent) {
				try {
					Desktop.getDesktop().browse(new URI("file://" + (new File(variablesCollection.path + "output/index.html").getCanonicalPath().replace("\\", "/"))));
				} catch (IOException | URISyntaxException e) {

					e.printStackTrace();
					
					//Shows an error dialog
					System.out.println("Something went wrong while opening export! :(");
					JOptionPane.showConfirmDialog(variablesCollection.frame, variablesCollection.languageMap.get("error pane->show"), 
							variablesCollection.languageMap.get("error title->show"), JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE);	
				}
			}
		});
		file.add(showItem);
		
		//Creates the JMenu Album and set Alt+A as mnemonic
		JMenu album = new JMenu(variablesCollection.languageMap.get("menu->album"));
		album.setMnemonic(KeyEvent.VK_A);
		add(album);
		
		//Creates the JMenuItem Update xml(s) and set ALT+U as mnemonic
		JMenuItem albumUpdateItem = new JMenuItem(variablesCollection.languageMap.get("menu->album update"), KeyEvent.VK_U);
		albumUpdateItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_U, ActionEvent.ALT_MASK));
		//Adds the ActionListener
		albumUpdateItem.addActionListener(new ActionListener() {
			@Override public void actionPerformed(ActionEvent actionEvent) {

				//Updates selected albums
				boolean succes = true;
				for (String album : variablesCollection.selectedAlbums) {
					if(variablesCollection.cFiles.createFiles(album)){
						System.out.println("Created / changed file: " + album + ".xml");
					}else{
						System.out.println("Something went wrong while updating: " + album + ".xml :(");
						succes = false;
					}
				}
				
				if (succes) {
					//Shows a success dialog
					JOptionPane.showConfirmDialog(variablesCollection.frame, variablesCollection.languageMap.get("succ pane->album upd"), 
							variablesCollection.languageMap.get("succ title->album upd"), JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE);
				}else{
					//Shows an error dialog
					JOptionPane.showConfirmDialog(variablesCollection.frame, variablesCollection.languageMap.get("error pane->album upd"), 
							variablesCollection.languageMap.get("error title->album upd"), JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE);
				}
				
			}
		});
		album.add(albumUpdateItem);
		
		//Creates the JMenuItem Remove Album and set CTRL+R as mnemonic
		JMenuItem albumRemoveItem = new JMenuItem(variablesCollection.languageMap.get("menu->album remove"), KeyEvent.VK_R);
		albumRemoveItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_R, ActionEvent.CTRL_MASK));
		//Adds the ActionListener
		albumRemoveItem.addActionListener(new ActionListener() {
			@Override public void actionPerformed(ActionEvent actionEvent) {

				//Removes selected albums
				boolean succes = true;
				for (String album : variablesCollection.selectedAlbums) {
					if(variablesCollection.rAlbum.removeAlbum(album)){
						System.out.println("Removed album: " + album);
					}else{
						System.out.println("Something went wrong while removing: " + album + " :(");
						succes = false;
					}
				}
				
				if (succes) {
					//Shows a success dialog
					JOptionPane.showConfirmDialog(variablesCollection.frame, variablesCollection.languageMap.get("succ pane->album rem"), 
							variablesCollection.languageMap.get("succ title->album rem"), JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE);
				}else{
					//Shows an error dialog
					JOptionPane.showConfirmDialog(variablesCollection.frame, variablesCollection.languageMap.get("error pane->album rem"), 
							variablesCollection.languageMap.get("error title->album rem"), JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE);
				}
				
			}
		});
		album.add(albumRemoveItem);
		
	}
	
}
