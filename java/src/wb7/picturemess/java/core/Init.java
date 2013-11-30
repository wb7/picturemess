package wb7.picturemess.java.core;

import java.io.File;
import java.util.HashMap;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class Init {

	//The path to the directory with albums.xml, /desc etc.
	public static String path = "";
	//The width of the thumbnails.
	public static int width = 0;
	
	//The content of the albums.xml ordered by the directory-name. The Srting[] contains title, time and description.
	public static HashMap<String, String[]> folderTitleMap = new HashMap<>();
	//All xmls in /desc ordered by the album-directory-name and it contains a map witch ordered by the picture-name and contain the description
	public static HashMap<String, HashMap<String, String>> fileDescrMap = new HashMap<>();
	
	public boolean loadConfig() {
		
		try{
		
			//Loads the config.xml file
			File file = new File("config.xml");
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = dbf.newDocumentBuilder();
			Document doc = builder.parse(file);
			doc.getDocumentElement().normalize();
			
			//Gets the path from the config.xml and write it into the String
			NodeList pathNodeList = doc.getElementsByTagName("path");
			Element pathElement = (Element) pathNodeList.item(0);
			NodeList pathChildNodes = pathElement.getChildNodes();
			path = ((Node) pathChildNodes.item(0)).getNodeValue();
			
			//Gets the with from the config.xml and write it into the Integer
			NodeList widthNodeList = doc.getElementsByTagName("width");
			Element widthElement = (Element) widthNodeList.item(0);
			NodeList widthChildNodes = widthElement.getChildNodes();
			try{
				width = Integer.parseInt(((Node) widthChildNodes.item(0)).getNodeValue());
			}catch (NumberFormatException e) {
				e.printStackTrace();
				System.err.println("The width \"" + width + "\" is no Integer.");
			}
			
		}catch (Exception e) {
			//Catches all errors
			e.printStackTrace();
			return false;
		}
		return true;
		
	}

	public boolean initXmls(String xml) {
		
		//xml == null if it is started from an other class.
		if(xml == null){
			
			//The loop will loads all xmls that contains in the albums.xml.
			for (String folder : folderTitleMap.keySet()) {
				fileDescrMap.put(folder, new HashMap<String, String>());
				//Breaks the initXmls if an error is detected.
				if(!initXmls(folder))
					return false;
			}
			
			return true;
			
		}
		
		//The file to the xml
		File fileXML = new File(path + "desc/" + xml + ".xml");
		
		//If the xml dose not exists it must not be loaded.
		if(!fileXML.exists())
			return true;
		
		try {
			
			//Loads the xml
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = dbf.newDocumentBuilder();
			Document doc = builder.parse(fileXML);
			doc.getDocumentElement().normalize();
			
			//Gets all file elements
			NodeList nodeList = doc.getElementsByTagName("file");
			
			//The loop to read the file elements.
			for (int i = 0; i < nodeList.getLength(); i++) {
				
				//Gets the file element
				Node node = nodeList.item(i);
				Element element = (Element) node;
				
				//Gets the filename from the file element and write it into the String
				NodeList fileNodeList = element.getElementsByTagName("filename");
				Element fileElement = (Element) fileNodeList.item(0);
				NodeList fileChildNodes = fileElement.getChildNodes();
				String file = ((Node) fileChildNodes.item(0)).getNodeValue();
				
				//Gets the description from the file element
				NodeList descriptionNodeList = element.getElementsByTagName("description");
				Element descriptionElement = (Element) descriptionNodeList.item(0);
				NodeList descriptionChildNodes = descriptionElement.getChildNodes();
				
				//If it contains null it will contain ""
				String description;
				try{
					description = ((Node) descriptionChildNodes.item(0)).getNodeValue();
				}catch (java.lang.NullPointerException e) {
					description = "";
				}
				
				//Puts the filename and the description into the fileDescrMap.
				fileDescrMap.get(xml).put(file, description);
			}
		
		} catch (Exception e) {
			//Catches all errors
			e.printStackTrace();
			return false;
		}
		
		//Outputs an message of success
		System.out.println(fileXML.getName() + " loded.");
		
		return true;
	}
	
	public boolean initAlbumsxml() {
		
		try {
			 
			//Loads the albums.xml file
			File file = new File(path + "albums.xml");
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = dbf.newDocumentBuilder();
			Document doc = builder.parse(file);
			doc.getDocumentElement().normalize();
			
			//Gets all album elements
			NodeList nodeList = doc.getElementsByTagName("album");
			
			//The loop to read the album elements.
			for (int i = 0; i < nodeList.getLength(); i++) {
				
				//Gets the album element
				Node node = nodeList.item(i);
				Element element = (Element) node;
				
				//Gets the folder from the album element and write it into the String
				NodeList folderNodeList = element.getElementsByTagName("folder");
				Element folderElement = (Element) folderNodeList.item(0);
				NodeList folderChildNodes = folderElement.getChildNodes();
				String folder = ((Node) folderChildNodes.item(0)).getNodeValue();
				
				//Gets the title from the album element and write it into the String
				NodeList titleNodeList = element.getElementsByTagName("title");
				Element titleElement = (Element) titleNodeList.item(0);
				NodeList titleChildNodes = titleElement.getChildNodes();
				String title = ((Node) titleChildNodes.item(0)).getNodeValue();
				
				//Gets the date from the album element and write it into the String
				NodeList dateNodeList = element.getElementsByTagName("date");
				Element dateElement = (Element) dateNodeList.item(0);
				NodeList dateChildNodes = dateElement.getChildNodes();
				String date = ((Node) dateChildNodes.item(0)).getNodeValue();
				
				//Gets the description from the album element and write it into the String
				NodeList descriptionNodeList = element.getElementsByTagName("description");
				Element descriptionElement = (Element) descriptionNodeList.item(0);
				NodeList descriptionChildNodes = descriptionElement.getChildNodes();
				String description = ((Node) descriptionChildNodes.item(0)).getNodeValue();
			
				//Puts the folder and the other things into the folderTitleMap.
				folderTitleMap.put(folder, new String[]{title, date, description});
				
			}
		
		} catch (Exception e) {
			//Catches all errors
			e.printStackTrace();
			return false;
		}
		
		return true;

	}

}
