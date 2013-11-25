import java.util.HashMap;

import wb7.picturemess.java.CreateFiles;
import wb7.picturemess.java.Export;
import wb7.picturemess.java.Init;

public class Main {

	private static String path = "";
	private static int width = 0;
	
	private static HashMap<String, String[]> folderTitleMap = new HashMap<>();
	private static HashMap<String, HashMap<String, String>> fileDescrMap = new HashMap<>();
	
	
	public static void main(String[] args) {
		
		init();
		
		if(args[0].equalsIgnoreCase("--create-files")){
			CreateFiles createFiles = new CreateFiles(path, fileDescrMap, folderTitleMap);
			createFiles.createFiles(args[1]);
		}else if(args[0].equalsIgnoreCase("--export")){
			Export export = new Export(path, fileDescrMap, folderTitleMap, width);
			export.export();
		}
		
	}


	private static void init() {
		
		Init init = new Init();
		
		if (init.loadConfig())
			System.out.println("config loaded.");
		else
			System.err.println("Something went wrong while loding config! :(");
		
		if (init.initAlbumsxml())
			System.out.println("albums.xml loaded.");
		else
			System.err.println("Something went wrong while loding albums.xml! :(");
		
		if(init.initXmls(null))
			System.out.println("album xmls loaded.");
		else
			System.err.println("Something went wrong while loding album xmls! :(");
		
		path = init.path;
		width = init.width;
		fileDescrMap = init.fileDescrMap;
		folderTitleMap = init.folderTitleMap;
		
	}
	
}
