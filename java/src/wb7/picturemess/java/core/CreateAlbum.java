package wb7.picturemess.java.core;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
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
		
		//Checks if the album exists
		if(!folderTitleMap.containsKey(folder)){
		
			//Creates the actually date
			String date = (new SimpleDateFormat("dd MMMMM yyyy")).format(Calendar.getInstance().getTime());
			
			//Checks for the title and if no title is detected it will be generated using the folder. After that it will put the Strings in the folderTitleMap
			if(title == null)
				folderTitleMap.put(folder, new String[]{"New Album - " + folder, date , "An awesome album with great pictures."});
			else
				folderTitleMap.put(folder, new String[]{title, date, "An awesome album with great pictures."});
			
			try {
				//Opens and creates the file
				BufferedWriter writer = new BufferedWriter(new FileWriter(new File(path + "albums.xml")));
				
				//Writes start
				writer.write("<albums>\n");
				
				//Writes the content of the folderTitleMap using a loop
				for (String tmpFolder : folderTitleMap.keySet()) {
					
					String[] value = folderTitleMap.get(tmpFolder);
					
					writer.write("  <album>\n");
					writer.write("    <title>" + value[0] + "</title>\n");
					writer.write("    <folder>" + tmpFolder + "</folder>\n");
					writer.write("    <date>" + value[1] + "</date>\n");
					writer.write("    <description>" + value[2] + "</description>\n");
					writer.write("  </album>\n");
				}
				
				//Writes end
				writer.write("</albums>\n");
				
				//Closes the writer
				writer.close();
			
			} catch (Exception e) {
				//Catches all errors
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
