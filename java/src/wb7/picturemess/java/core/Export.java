package wb7.picturemess.java.core;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

import javax.imageio.ImageIO;

public class Export {

	private HashMap<String, String[]> folderTitleMap;
	private HashMap<String, HashMap<String, String>> fileDescrMap;
	private String path;
	private int width;

	public Export(String path, HashMap<String, HashMap<String, String>> fileDescrMap, HashMap<String, String[]> folderTitleMap, int width) {
		
		this.path = path;
		this.fileDescrMap = fileDescrMap;
		this.folderTitleMap = folderTitleMap;
		this.width = width;
		
	}
	
	public boolean export() {
		
		System.out.println("export files");
		System.out.println();
		
		File output = new File(path + "output/");
		if(output.exists()){
			delete(output);
			System.out.println("Old files deleted.");
			System.out.println();
		}
		
		System.out.println("copy includes");
		if(!copy(new File(path + "inc/"), output.getAbsolutePath()))
			return false;
		System.out.println("All includes copied.");
		System.out.println();
		
		if(!copyImages())
			return false;
		
		if(!createHtml())
			return false;
		
		System.out.println("exported files");
		
		return true;
		
	}

	private boolean createHtml() {
		
		System.out.println("creat htmls");
		
		String footer = getFooter();
		String includes = getIncludes();
		
		String list = getList();
		
		try {
			
			BufferedReader reader = new BufferedReader(new FileReader(new File(path + "tpl/index.tpl")));
			BufferedWriter writer = new BufferedWriter(new FileWriter(new File(path + "output/index.html")));
			
			String line;
			while ((line = reader.readLine()) != null) {
			
				line = line.replace("{INCLUDES}", includes);
				line = line.replace("{FOOTER}", footer);
				line = line.replace("{LIST}", list);
				
				writer.write(line);
				writer.newLine();
			
			}
			
			reader.close();
			writer.close();
			
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		
		System.out.println("output/index.html created.");
		
		for (String album : folderTitleMap.keySet()) {
			
			String title = folderTitleMap.get(album)[0];
			String date = folderTitleMap.get(album)[1];
			String description = folderTitleMap.get(album)[2];
					
			String pictures = getPictures(album);
			
			try {
				
				BufferedReader reader = new BufferedReader(new FileReader(new File(path + "tpl/page.tpl")));
				BufferedWriter writer = new BufferedWriter(new FileWriter(new File(path + "output/" + album + ".html")));
				
				String line;
				while ((line = reader.readLine()) != null) {
				
					line = line.replace("{INCLUDES}", includes);
					line = line.replace("{FOOTER}", footer);
					line = line.replace("{TITLE}", title);
					line = line.replace("{DATE}", date);
					line = line.replace("{DESCRIPTION}", description);
					line = line.replace("{PICTURES}", pictures);
					
					writer.write(line);
					writer.newLine();
				
				}
				
				reader.close();
				writer.close();
				
			} catch (IOException e) {
				e.printStackTrace();
				return false;
			}
			
			System.out.println("output/" + album + ".html created.");
			
		}
		
		System.out.println("created htmls");
		System.out.println();
		
		return true;
		
	}

	private String getList() {
		
		String returnString = "";
		
		for (String album : fileDescrMap.keySet()) {
			
			String title = folderTitleMap.get(album)[0];
			String description = folderTitleMap.get(album)[2];
			
			try {
				
				BufferedReader reader = new BufferedReader(new FileReader(new File(path + "tpl/index_tile.tpl")));
				
				String line;
				while ((line = reader.readLine()) != null) {
					
					line = line.replace("{TITLE}", title);
					line = line.replace("{LINK}", album + ".html");
					line = line.replace("{DESCRIPTION}", description);
					
					returnString += line + "\n";
					
				}
				
				reader.close();
				
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		}
		
		return returnString;
		
	}

	private String getPictures(String album) {
		
		String returnString = "";
		
		for (String filename : fileDescrMap.get(album).keySet()) {
			
			String description = fileDescrMap.get(album).get(filename);
			
			try {
				
				BufferedReader reader = new BufferedReader(new FileReader(new File(path + "tpl/page_tile.tpl")));
				
				String line;
				while ((line = reader.readLine()) != null) {
					
					line = line.replace("{FOLDER}", album);
					line = line.replace("{FILENAME}", filename);
					line = line.replace("{DESCRIPTION}", description);
					
					returnString += line + "\n";
					
				}
				
				reader.close();
				
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		}
		
		return returnString;
		
	}

	private String getIncludes() {
		
		String returnString = "";
		
		try {
			
			BufferedReader reader = new BufferedReader(new FileReader(new File(path + "tpl/inc.tpl")));
			
			String line;
			while ((line = reader.readLine()) != null) {
				
				returnString += line + "\n";
				
			}
			
			reader.close();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return returnString;
		
	}

	private String getFooter() {
		
		String time = (new SimpleDateFormat("dd.MM.yyyy HH:mm")).format(Calendar.getInstance().getTime());
		String year = (new SimpleDateFormat("yyyy")).format(Calendar.getInstance().getTime());
		
		String returnString = "";
		
		try {
			
			BufferedReader reader = new BufferedReader(new FileReader(new File(path + "tpl/footer.tpl")));
			
			String line;
			while ((line = reader.readLine()) != null) {
			
				line = line.replace("{TIME}", time);
				line = line.replace("{YEAR}", year);
				
				returnString += line + "\n";
				
			}
			
			reader.close();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return returnString;
	}

	private boolean copy(File file, String to) {
		
		try {
			Files.copy(file.toPath(), (new File(to)).toPath());
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		
		System.out.println(file.getName() + " copied.");
		
		if(file.isDirectory())
			for (File otherFile : file.listFiles()) {
				String fileString = to + "/" + otherFile.getName();
				if(!copy(otherFile, fileString))
					return false;
			}
		
		return true;
		
	}
	
	private boolean copyImages(){
		
		System.out.println("copy images");
		
		new File(path + "output/images/").mkdir();
		new File(path + "output/thumbs/").mkdir();
		
		for (String album : fileDescrMap.keySet()) {
						
			new File(path + "output/images/" + album).mkdir();
			new File(path + "output/thumbs/" + album).mkdir();
			
			for (String file : fileDescrMap.get(album).keySet()) {
				
				try {
					Files.copy((new File(path + "images/" + album + "/" + file)).toPath(), (new File(path + "output/images/" + album + "/" + file)).toPath());
					System.out.println("images/" + album + "/" + file + " copied.");
					
					scale(new File(path + "images/" + album + "/" + file), new File(path + "output/thumbs/" + album + "/" + file));
					System.out.println("images/" + album + "/" + file + " scaled and copied.");
				} catch (IOException e) {
					e.printStackTrace();
					return false;
				}
				
			}
			
		}
		System.out.println("copied images");
		System.out.println();
		
		return true;
		
	}
		  
	private void scale(File file, File fileTo) {
		
		try{
		
			BufferedImage image = ImageIO.read(file);
			
			int height = (int)( ((double)width)/(((double)image.getWidth())/((double)image.getHeight())));
			
			BufferedImage imageThumbnail = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
			imageThumbnail.createGraphics().drawImage(image.getScaledInstance(width, height, Image.SCALE_SMOOTH),0,0,null);
			String[] type = file.getName().split("\\.");
			ImageIO.write(imageThumbnail, type[type.length-1], fileTo);
		
		}catch (IOException e) {
			e.printStackTrace();
		}
				
	}

	private static void delete(File file) {
		
		if(file.isDirectory())
			for (File otherFile : file.listFiles()) {
				delete(otherFile);
			}
		file.delete();
		
	}

	
}
