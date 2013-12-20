package wb7.picturemess.java.gui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Insets;
import java.awt.Panel;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.nio.channels.SelectableChannel;
import java.util.HashMap;
import java.util.LinkedHashMap;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.BevelBorder;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;
import javax.swing.plaf.BorderUIResource;

public class AlbumPanel extends JPanel implements MouseListener{

	private String path;
	private HashMap<String, String[]> folderTitleMap;
	private HashMap<String, HashMap<String, String>> fileDescrMap;
	private Object languageMap;
	private String[] descStrings;
	private int number;
	private String folder;

	public AlbumPanel(String folder, int number, HashMap<String, String[]> folderTitleMap, HashMap<String, HashMap<String, String>> fileDescrMap, String path, LinkedHashMap<String, String> languageMap) {
		
		super(null);
		
		this.folder = folder;
		this.number = number;
		this.path = path;
		this.folderTitleMap = folderTitleMap;
		this.fileDescrMap = fileDescrMap;
		this.languageMap = languageMap;
		
		descStrings = folderTitleMap.get(folder);
		
		createPanel();
		
		addMouseListener(this);
		
	}
	
	private void createPanel() {
		
		setBounds(50, 80 + number*200, 400, 150);
		
		float[] color = Color.RGBtoHSB(230, 230, 230, null);
		setBackground(Color.getHSBColor(color[0], color[1], color[2]));
		
		JLabel titleLabel = new JLabel(descStrings[0]);
		titleLabel.setBounds(10, 10, 300, 40);
		titleLabel.setFont(new Font(null, Font.BOLD, 18));
		add(titleLabel);
		
		JLabel descLabel = new JLabel(descStrings[2]);
		descLabel.setBounds(10, 40, 300, 40);
		descLabel.setFont(new Font(null, Font.BOLD, 14));
		add(descLabel);
		
		JLabel dateLabel = new JLabel(descStrings[1]);
		dateLabel.setBounds(10, 70, 300, 40);
		dateLabel.setFont(new Font(null, 0, 14));
		add(dateLabel);
		
		JLabel folderLabel = new JLabel("desc/" + folder + "/");
		folderLabel.setBounds(10, 100, 300, 40);
		add(folderLabel);
		
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		
		if(e.getClickCount() == 1){
			
			if(getBackground().getRed() == 230){
				
				float[] color = Color.RGBtoHSB(220, 220, 230, null);
				setBackground(Color.getHSBColor(color[0], color[1], color[2]));
	
				float[] color2 = Color.RGBtoHSB(100, 100, 200, null);
				setBorder(BorderFactory.createLineBorder(Color.getHSBColor(color2[0], color2[1], color2[2])));
				
			}else{
				
				float[] color = Color.RGBtoHSB(230, 230, 230, null);
				setBackground(Color.getHSBColor(color[0], color[1], color[2]));
				
				setBorder(null);
				
			}
			
		}
		
	}

	@Override public void mouseEntered(MouseEvent e) {}
	@Override public void mouseExited(MouseEvent e) {}
	@Override public void mousePressed(MouseEvent e) {}
	@Override public void mouseReleased(MouseEvent e) {}
	
}
