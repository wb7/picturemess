package wb7.picturemess.java.core;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;

public class WriteAlbumsXml extends BufferedWriter{

	private HashMap<String, String[]> folderTitleMap;
	
	public WriteAlbumsXml(HashMap<String, String[]> folderTitleMap, String path) throws IOException {
		
		super(new FileWriter(new File(path + "albums.xml")));
		
		this.folderTitleMap = folderTitleMap;
		
	}

	public void writeAll() throws IOException {
		
		//Writes start
		write("<albums>\n");
		
		//Writes the content of the folderTitleMap using a loop
		for (String tmpFolder : folderTitleMap.keySet()) {
			
			String[] value = folderTitleMap.get(tmpFolder);
			
			write("  <album>\n");
			write("    <title>" + value[0] + "</title>\n");
			write("    <folder>" + tmpFolder + "</folder>\n");
			write("    <date>" + value[1] + "</date>\n");
			write("    <description>" + value[2] + "</description>\n");
			write("  </album>\n");
		}
		
		//Writes end
		write("</albums>\n");
		
	}
	
	
}
