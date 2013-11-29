import java.util.HashMap;

import wb7.picturemess.java.core.CreateAlbum;
import wb7.picturemess.java.core.CreateFiles;
import wb7.picturemess.java.core.Export;
import wb7.picturemess.java.core.Init;

public class Main_core {

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
				if(createFiles.createFiles(args[1]))
					System.out.println("Created / changed xml files.");
				else
					System.err.println("Something went wrong while creating files! :(");
			}else
				System.err.println("Please give one album or --all as argument.");
		}else if(args[0].equalsIgnoreCase("--export")){
			Export export = new Export(path, fileDescrMap, folderTitleMap, width);
			if(export.export())
				System.out.println("Exported files to output.");
			else
				System.err.println("Something went wrong while exporting files! :(");
		}else if(args[0].equalsIgnoreCase("--create-album")){
			CreateAlbum createAlbum = new CreateAlbum(path, folderTitleMap);
			if(args.length > 1){
				String title;
				if(args.length > 2)
					title = args[2];
				else
					title = null;
				if(createAlbum.createAlbum(args[1], title))
					System.out.println("Created album " + args[1] + ".");
				else
					System.err.println("Something went wrong while creating an album! :(");
			}else
				System.err.println("Please give an folder to create the album there.");
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
