package wb7.picturemess.java.core;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

public class CreateAlbum {

	private HashMap<String, String[]> folderTitleMap;
	private String path;

	public CreateAlbum(String path, HashMap<String, String[]> folderTitleMap) {
		
		this.path = path;
		this.folderTitleMap = folderTitleMap;
		
	}
	
	public Boolean createAlbum(String folder, String title) {
		
		if(!folderTitleMap.containsKey(folder)){
		
			String date = (new SimpleDateFormat("dd MMMMM yyyy")).format(Calendar.getInstance().getTime());
			if(title == null)
				folderTitleMap.put(folder, new String[]{"New Album - " + folder, date , "An awesome album with great pictures."});
			else
				folderTitleMap.put(folder, new String[]{title, date, "An awesome album with great pictures."});
			
			try {
				
				BufferedWriter writer = new BufferedWriter(new FileWriter(new File(path + "albums.xml")));
			
				writer.write("<albums>\n");
				
				for (String tmpFolder : folderTitleMap.keySet()) {
					
					String[] value = folderTitleMap.get(tmpFolder);
					
					writer.write("  <album>\n");
					writer.write("    <title>" + value[0] + "</title>\n");
					writer.write("    <folder>" + tmpFolder + "</folder>\n");
					writer.write("    <date>" + value[1] + "</date>\n");
					writer.write("    <description>" + value[2] + "</description>\n");
					writer.write("  </album>\n");
				}
				
				writer.write("</albums>\n");
			
				writer.close();
			
			} catch (IOException e) {
				e.printStackTrace();
				return false;
			}
			
			return true;
			
		}else{
			
			System.err.println("Album already exists!");
			System.err.println();
			
			return false;
			
		}
		
	}

}
