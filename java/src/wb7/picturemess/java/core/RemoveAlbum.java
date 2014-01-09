package wb7.picturemess.java.core;

import java.io.File;
import java.util.HashMap;

public class RemoveAlbum {

	private HashMap<String, String[]> folderTitleMap;
	private String path;

	public RemoveAlbum(String path, HashMap<String, String[]> folderTitleMap) {
		
		this.path = path;
		this.folderTitleMap = folderTitleMap;
		
	}

	public boolean removeAlbum(String folder){
		
		//Checks if the album exists
		if(folderTitleMap.containsKey(folder)){
			
			//Removes the Strings in the folderTitleMap
			folderTitleMap.remove(folder);
			
			//Deletes the folder and the content in desc/ and images/
			Export.delete(new File(path + "images/" + folder));
			Export.delete(new File(path + "desc/" + folder + ".xml"));
			
			try {
				
				//Writes the albums.xml with the change
				WriteAlbumsXml writeAlbumsXml = new WriteAlbumsXml(folderTitleMap, path);
				writeAlbumsXml.writeAll();
				writeAlbumsXml.close();
			
			} catch (Exception e) {
				//Catches all errors
				e.printStackTrace();
				return false;
			}
			
			return true;
			
		}else{
			
			System.err.println("Album dose not exist!");
			System.err.println();
			
			return false;
			
		}
		
	}
	
}
