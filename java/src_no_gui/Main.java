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
		
		if(args.length < 1){
			System.err.println("Please give one command.");
			return;
		}
		
		if(args[0].equalsIgnoreCase("--create-files")){
			if(args.length >= 2){
				CreateFiles createFiles = new CreateFiles(path, fileDescrMap, folderTitleMap);
				if(createFiles.createFiles(args[1])){
					System.out.println("Created / changed xml files.");
				}else{
					System.err.println("Something went wrong while creating files! :(");
				}
			}else
				System.err.println("Please give one album or --all as argument.");
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
		
		System.out.println();
		
		if (init.initAlbumsxml())
			System.out.println("albums.xml loaded.");
		else
			System.err.println("Something went wrong while loding albums.xml! :(");
		
		System.out.println();
		
		if(init.initXmls(null))
			System.out.println("album xmls loaded.");
		else
			System.err.println("Something went wrong while loding album xmls! :(");
		
		System.out.println();
		
		path = init.path;
		width = init.width;
		fileDescrMap = init.fileDescrMap;
		folderTitleMap = init.folderTitleMap;
		
	}
	
}
