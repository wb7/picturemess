import java.util.HashMap;

import wb7.picturemess.java.core.CreateAlbum;
import wb7.picturemess.java.core.CreateFiles;
import wb7.picturemess.java.core.Export;
import wb7.picturemess.java.core.Init;

public class Main_core {

	//The path to the directory with albums.xml, /desc etc.
	private static String path = "";
	//The width of the thumbnails.
	private static int width = 0;
	
	//The content of the albums.xml ordered by the directory-name. The Srting[] contains title, time and description.
	private static HashMap<String, String[]> folderTitleMap = new HashMap<>();
	//All xmls in /desc ordered by the album-directory-name and it contains a map witch ordered by the picture-name and contain the description
	private static HashMap<String, HashMap<String, String>> fileDescrMap = new HashMap<>();
	
	
	public static void main(String[] args) {
		
		//Loads the config.xml the albums.xml and the xml files for the albums.
		init();
		
		//Returns an error if no command is given.
		if(args.length < 1){
			System.err.println("Please give one command.");
			return;
		}
		
		//Checks the entered command
		if(args[0].equalsIgnoreCase("--create-files")){
			//Checks for an argument
			if(args.length >= 2){
				CreateFiles createFiles = new CreateFiles(path, fileDescrMap, folderTitleMap);
				//Returns an error if something went wrong.
				if(createFiles.createFiles(args[1]))
					System.out.println("Created / changed xml files.");
				else
					System.err.println("Something went wrong while creating files! :(");
			}else
				System.err.println("Please give one album or --all as argument.");
		}else if(args[0].equalsIgnoreCase("--export")){
			Export export = new Export(path, fileDescrMap, folderTitleMap, width);
			//Returns an error if something went wrong.
			if(export.export())
				System.out.println("Exported files to output.");
			else
				System.err.println("Something went wrong while exporting files! :(");
		}else if(args[0].equalsIgnoreCase("--create-album")){
			CreateAlbum createAlbum = new CreateAlbum(path, folderTitleMap);
			//Checks for an argument
			if(args.length > 1){
				//If no more arguments are given the title and the description will be a automatically generated String.
				String title;
				if(args.length > 2)
					title = args[2];
				else
					title = "New Album - " + args[1];
				String description;
				if(args.length > 3)
					description = args[3];
				else
					description = "An awesome album with great pictures.";
				
				//Returns an error if something went wrong.
				if(createAlbum.createAlbum(args[1], title, description))
					System.out.println("Created album " + args[1] + ".");
				else
					System.err.println("Something went wrong while creating an album! :(");
			}else
				System.err.println("Please give an folder to create the album there.");
		}
		
	}


	private static void init() {
		
		Init init = new Init();
		
		//Loads the config.xml. Returns an error if something went wrong.
		if (init.loadConfig())
			System.out.println("config loaded.");
		else
			System.err.println("Something went wrong while loding config! :(");
		
		//A clear line for better structure.
		System.out.println();
		
		//Loads the albums.xml. Returns an error if something went wrong.
		if (init.initAlbumsxml())
			System.out.println("albums.xml loaded.");
		else
			System.err.println("Something went wrong while loding albums.xml! :(");
		
		//A clear line for better structure.
		System.out.println();
		
		//Loads the other xmls. Returns an error if something went wrong.
		if(init.initXmls(null))
			System.out.println("album xmls loaded.");
		else
			System.err.println("Something went wrong while loding album xmls! :(");
		
		//A clear line for better structure.
		System.out.println();
		
		path = init.path;
		width = init.width;
		fileDescrMap = init.fileDescrMap;
		folderTitleMap = init.folderTitleMap;
		
	}
	
}
