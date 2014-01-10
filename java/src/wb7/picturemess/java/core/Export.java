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

package wb7.picturemess.java.core;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

import javax.imageio.ImageIO;

public class Export {

	//The path to the directory with albums.xml, /desc etc.
	private String path = "";
	//The width of the thumbnails.
	private int width = 0;
	
	//The content of the albums.xml ordered by the directory-name. The Srting[] contains title, time and description.
	private HashMap<String, String[]> folderTitleMap = new HashMap<>();
	//All xmls in /desc ordered by the album-directory-name and it contains a map witch ordered by the picture-name and contain the description
	private HashMap<String, HashMap<String, String>> fileDescrMap = new HashMap<>();

	public Export(String path, HashMap<String, HashMap<String, String>> fileDescrMap, HashMap<String, String[]> folderTitleMap, int width) {
		
		this.path = path;
		this.fileDescrMap = fileDescrMap;
		this.folderTitleMap = folderTitleMap;
		this.width = width;
		
	}
	
	public boolean export() {
		
		//Outputs start message
		System.out.println("export files");
		System.out.println();
		
		//Deletes /output if it exists
		File output = new File(path + "output/");
		if(output.exists()){
			delete(output);
			System.out.println("Old files deleted.");
			System.out.println();
		}
		
		//Copies the /inc folder
		System.out.println("copy includes");
		if(!copy(new File(path + "inc/"), output.getAbsolutePath()))
			return false;
		System.out.println("All includes copied.");
		System.out.println();
		
		//Copies and scales the includes
		if(!copyImages())
			return false;
		
		//Generate the html files
		if(!createHtml())
			return false;
		
		//Outputs a message of success
		System.out.println("exported files");
		
		return true;
		
	}

	private boolean createHtml() {
		
		//Outputs start message
		System.out.println("creat htmls");
		
		//Gets the footer and the include
		String footer = getFooter();
		String includes = getIncludes();
		
		//Gets the list of albums
		String list = getList();
		
		try {
			
			//Opens a writer and a reader
			BufferedReader reader = new BufferedReader(new FileReader(new File(path + "tpl/index.tpl")));
			BufferedWriter writer = new BufferedWriter(new FileWriter(new File(path + "output/index.html")));
			
			//Writes the lines from the tpl/index.tpl to the output/index.html using a loop
			String line;
			while ((line = reader.readLine()) != null) {
			
				//Replaces the include, the footers and the list
				line = line.replace("{INCLUDES}", includes);
				line = line.replace("{FOOTER}", footer);
				line = line.replace("{LIST}", list);
				
				//Writes the (replaced)line
				writer.write(line);
				writer.newLine();
			
			}
			
			//Closes reader and writer
			reader.close();
			writer.close();
			
		} catch (Exception e) {
			//Catches all errors
			e.printStackTrace();
			return false;
		}
		
		//Outputs a message of success
		System.out.println("output/index.html created.");
		
		//A loop to write htmls for all albums
		for (String album : folderTitleMap.keySet()) {
			
			//Gets the title, the date and the description form the folderTitleMap
			String title = folderTitleMap.get(album)[0];
			String date = folderTitleMap.get(album)[1];
			String description = folderTitleMap.get(album)[2];
			
			//Gets the picture part for this album
			String pictures = getPictures(album);
			
			try {
				
				//Opens a writer and a reader
				BufferedReader reader = new BufferedReader(new FileReader(new File(path + "tpl/page.tpl")));
				BufferedWriter writer = new BufferedWriter(new FileWriter(new File(path + "output/" + album + ".html")));
				
				//Writes the lines from the tpl/page.tpl to the output/<album>.html using a loop
				String line;
				while ((line = reader.readLine()) != null) {
				
					//Replaces the include, the footers etc.
					line = line.replace("{INCLUDES}", includes);
					line = line.replace("{FOOTER}", footer);
					line = line.replace("{TITLE}", title);
					line = line.replace("{DATE}", date);
					line = line.replace("{DESCRIPTION}", description);
					line = line.replace("{PICTURES}", pictures);
					
					//Writes the (replaced)line
					writer.write(line);
					writer.newLine();
				
				}
				
				//Closes reader and writer
				reader.close();
				writer.close();
				
			} catch (Exception e) {
				//Catches all errors
				e.printStackTrace();
				return false;
			}
			
			//Outputs a message of success
			System.out.println("output/" + album + ".html created.");
			
		}
		
		//Outputs a message of success
		System.out.println("created htmls");
		System.out.println();
		
		return true;
		
	}

	private String getList() {
		
		String returnString = "";
		
		//A loop to read a list with all albums
		for (String album : fileDescrMap.keySet()) {
			
			//Gets the title and the description
			String title = folderTitleMap.get(album)[0];
			String description = folderTitleMap.get(album)[2];
			
			try {
				
				//Opens and a reader
				BufferedReader reader = new BufferedReader(new FileReader(new File(path + "tpl/index_tile.tpl")));
				
				//Reads the lines from the tpl/index_tile.tpl using a loop
				String line;
				while ((line = reader.readLine()) != null) {
					
					//Replaces the title, the link to the <album>.html, etc.
					line = line.replace("{TITLE}", title);
					line = line.replace("{LINK}", album + ".html");
					line = line.replace("{DESCRIPTION}", description);
					
					//Adds the line to the returnString
					returnString += line + "\n";
					
				}
				
				//Closes reader
				reader.close();
				
			} catch (Exception e) {
				//Catches all errors
				e.printStackTrace();
			}
			
		}
		
		return returnString;
		
	}

	private String getPictures(String album) {
		
		String returnString = "";
		
		//A loop to read all pictures for the album
		for (String filename : fileDescrMap.get(album).keySet()) {
			
			//Gets the description for this picture
			String description = fileDescrMap.get(album).get(filename);
			
			try {
				
				//Opens a reader
				BufferedReader reader = new BufferedReader(new FileReader(new File(path + "tpl/page_tile.tpl")));
				
				//Reads the lines from the tpl/page_tile.tpl using a loop
				String line;
				while ((line = reader.readLine()) != null) {
					
					//Replaces the folder, the filename, etc.
					line = line.replace("{FOLDER}", album);
					line = line.replace("{FILENAME}", filename);
					line = line.replace("{DESCRIPTION}", description);
					
					//Adds the line to the returnString
					returnString += line + "\n";
					
				}
				
				//Closes reader
				reader.close();
				
			} catch (Exception e) {
				//Catches all errors
				e.printStackTrace();
			}
			
		}
		
		return returnString;
		
	}

	private String getIncludes() {
		
		String returnString = "";
		
		try {
			
			//Opens a reader
			BufferedReader reader = new BufferedReader(new FileReader(new File(path + "tpl/inc.tpl")));
			
			//Reads the lines from the tpl/inc.tpl using a loop
			String line;
			while ((line = reader.readLine()) != null) {
				
				//Adds the line to the returnString
				returnString += line + "\n";
				
			}

			//Closes reader
			reader.close();
			
		} catch (Exception e) {
			//Catches all errors
			e.printStackTrace();
		}
		
		return returnString;
		
	}

	private String getFooter() {
		
		String time = (new SimpleDateFormat("dd.MM.yyyy HH:mm")).format(Calendar.getInstance().getTime());
		String year = (new SimpleDateFormat("yyyy")).format(Calendar.getInstance().getTime());
		
		String returnString = "";
		
		try {
			
			//Opens a reader
			BufferedReader reader = new BufferedReader(new FileReader(new File(path + "tpl/footer.tpl")));
			
			//Reads the lines from the tpl/footer.tpl using a loop
			String line;
			while ((line = reader.readLine()) != null) {
			
				//Replaces the time and the year
				line = line.replace("{TIME}", time);
				line = line.replace("{YEAR}", year);
				
				//Adds the line to the returnString
				returnString += line + "\n";
				
			}

			//Closes reader
			reader.close();
			
		} catch (Exception e) {
			//Catches all errors
			e.printStackTrace();
		}
		
		return returnString;
	}

	private boolean copy(File file, String to) {
		
		//Copies the file
		try {
			Files.copy(file.toPath(), (new File(to)).toPath());
		} catch (Exception e) {
			//Catches all errors
			e.printStackTrace();
			return false;
		}
		
		//Outputs a message of success
		System.out.println(file.getName() + " copied.");
		
		//After that it will check for an directory. If that is true it will copied the files in the directory.
		if(file.isDirectory())
			for (File otherFile : file.listFiles()) {
				String fileString = to + "/" + otherFile.getName();
				if(!copy(otherFile, fileString))
					return false;
			}
		
		return true;
		
	}
	
	private boolean copyImages(){
		
		//Outputs start message
		System.out.println("copy images");
		
		//Make the folders for the pictures
		new File(path + "output/images/").mkdir();
		new File(path + "output/thumbs/").mkdir();
		
		//This loop is for the albums
		for (String album : fileDescrMap.keySet()) {
						
			//Make the folders for the pictures form the <album>
			new File(path + "output/images/" + album).mkdir();
			new File(path + "output/thumbs/" + album).mkdir();
			
			//This loop is for the pictures in the album
			for (String file : fileDescrMap.get(album).keySet()) {
				
				try {
					//Copies the pictures and outputs an message of success
					Files.copy((new File(path + "images/" + album + "/" + file)).toPath(), (new File(path + "output/images/" + album + "/" + file)).toPath());
					System.out.println("images/" + album + "/" + file + " copied.");
					
					//Copies and scales the pictures and outputs an message of success
					scale(new File(path + "images/" + album + "/" + file), new File(path + "output/thumbs/" + album + "/" + file));
					System.out.println("images/" + album + "/" + file + " scaled and copied.");
				} catch (Exception e) {
					//Catches all errors
					e.printStackTrace();
					return false;
				}
				
			}
			
		}
		
		//Outputs a message of success
		System.out.println("copied images");
		System.out.println();
		
		return true;
		
	}
		  
	private void scale(File file, File fileTo) {
		
		try{
		
			//Reads the image
			BufferedImage image = ImageIO.read(file);
			
			//Generates the height
			int height = (int)( ((double)width)/(((double)image.getWidth())/((double)image.getHeight())));
			
			//Scales and writes the picture into the thumbs folder
			BufferedImage imageThumbnail = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
			imageThumbnail.createGraphics().drawImage(image.getScaledInstance(width, height, Image.SCALE_SMOOTH),0,0,null);
			String[] type = file.getName().split("\\.");
			ImageIO.write(imageThumbnail, type[type.length-1], fileTo);
		
		}catch (Exception e) {
			//Catches all errors
			e.printStackTrace();
		}
				
	}

	public static void delete(File file) {
		
		//Deletes the file recursive
		if(file.isDirectory())
			for (File otherFile : file.listFiles()) {
				delete(otherFile);
			}
		file.delete();
		
	}

	
}
