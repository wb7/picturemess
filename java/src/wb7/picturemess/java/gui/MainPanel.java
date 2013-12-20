package wb7.picturemess.java.gui;

import java.awt.Color;
import java.awt.Font;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;

import javax.swing.JLabel;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class MainPanel extends JPanel{

	@SuppressWarnings("unused")
	private String path;
	private HashMap<String, String[]> folderTitleMap;
	@SuppressWarnings("unused")
	private HashMap<String, HashMap<String, String>> fileDescrMap;
	private LinkedHashMap<String, String> languageMap;

	public MainPanel(String path, HashMap<String, String[]> folderTitleMap,
			HashMap<String, HashMap<String, String>> fileDescrMap, LinkedHashMap<String, String> languageMap) {
		
		super(null);
		
		this.path = path;
		this.folderTitleMap = folderTitleMap;
		this.fileDescrMap = fileDescrMap;
		this.languageMap = languageMap;
		
		createPanel();
		
	}

	private void createPanel() {
		
		JLabel headline = new JLabel(languageMap.get("main panel->headline"));
		headline.setBounds(50, 20, 200, 50);
		headline.setFont(new Font(null, Font.BOLD, 20));
		add(headline);
		
		int i = 0;
		for (String folder : folderTitleMap.keySet()) {
			
			AlbumPanel albumPanel = new AlbumPanel(folder, i, folderTitleMap, fileDescrMap, path, languageMap);
			
			add(albumPanel);
			
			i++;
			
		}
		
	}

}
