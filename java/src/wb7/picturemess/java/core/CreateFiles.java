package wb7.picturemess.java.core;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;

public class CreateFiles {

	private HashMap<String, String[]> folderTitleMap;
	private HashMap<String, HashMap<String, String>> fileDescrMap;
	private String path;

	public CreateFiles(String path,
			HashMap<String, HashMap<String, String>> fileDescrMap,
			HashMap<String, String[]> folderTitleMap) {
		
		this.path = path;
		this.fileDescrMap = fileDescrMap;
		this.folderTitleMap = folderTitleMap;
		
	}

	public boolean createFiles(String album) {

		if(album.equalsIgnoreCase("--all")){
			
			for (String folder : folderTitleMap.keySet()) {
				if(!createFiles(folder))
					return false;
			}
			
			return true;
			
		}
		
		HashMap<String, String> newFileDescrMap = new HashMap<>();
		
		File folder = new File(path + "images/" + album + "/");
		
		for (File file : folder.listFiles()) {
			
			String filename = file.getName();
			
			if(fileDescrMap.get(album).containsKey(filename)){
				newFileDescrMap.put(filename, fileDescrMap.get(album).get(filename));

			}else{
				newFileDescrMap.put(filename, "");
			}
		}
		
		
		try {
			
			BufferedWriter writer = new BufferedWriter(new FileWriter(new File(path + "desc/" + album + ".xml")));
		
			writer.write("<album>\n");
			
			for (String file : newFileDescrMap.keySet()) {
				
				writer.write("  <file>\n");
				writer.write("    <filename>" + file + "</filename>\n");
				writer.write("    <description>" + newFileDescrMap.get(file) + "</description>\n");
				writer.write("  </file>\n");
			}
			
			writer.write("</album>\n");
		
			writer.close();
		
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		
		System.out.println(album + ".xml created / changed.");
		
		return true;
		
	}
	
}
