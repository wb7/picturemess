package wb7.picturemess.java;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class Init {

	public String path = "";
	public int width = 0;
	
	public HashMap<String, String[]> folderTitleMap = new HashMap<>();
	public HashMap<String, HashMap<String, String>> fileDescrMap = new HashMap<>();
	
	public boolean loadConfig() {
		
		try{
		
			File file = new File("config.xml");
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = dbf.newDocumentBuilder();
			Document doc = builder.parse(file);
			doc.getDocumentElement().normalize();
			
			NodeList pathNodeList = doc.getElementsByTagName("path");
			Element pathElement = (Element) pathNodeList.item(0);
			NodeList pathChildNodes = pathElement.getChildNodes();
			path = ((Node) pathChildNodes.item(0)).getNodeValue();
			
			NodeList widthNodeList = doc.getElementsByTagName("width");
			Element widthElement = (Element) widthNodeList.item(0);
			NodeList widthChildNodes = widthElement.getChildNodes();
			try{
				width = Integer.parseInt(((Node) widthChildNodes.item(0)).getNodeValue());
			}catch (NumberFormatException e) {
				e.printStackTrace();
				System.err.println("The width \"" + width + "\" is no Integer.");
			}
			
		}catch (ParserConfigurationException | SAXException | IOException e) {
			e.printStackTrace();
			return false;
		}
		return true;
		
	}

	public boolean initXmls(String xml) {
		
		if(xml == null){
			
			for (String folder : folderTitleMap.keySet()) {
				fileDescrMap.put(folder, new HashMap<String, String>());
				if(!initXmls(folder))
					return false;
			}
			
			return true;
			
		}
		
		File fileXML = new File(path + "desc\\" + xml + ".xml");
		
		if(!fileXML.exists())
			return true;
		
		try {
			
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = dbf.newDocumentBuilder();
			Document doc = builder.parse(fileXML);
			doc.getDocumentElement().normalize();
			
			NodeList nodeList = doc.getElementsByTagName("file");
			
			for (int i = 0; i < nodeList.getLength(); i++) {
				
				Node node = nodeList.item(i);
				Element element = (Element) node;
				
				NodeList fileNodeList = element.getElementsByTagName("filename");
				Element fileElement = (Element) fileNodeList.item(0);
				NodeList fileChildNodes = fileElement.getChildNodes();
				String file = ((Node) fileChildNodes.item(0)).getNodeValue();
				
				NodeList descriptionNodeList = element.getElementsByTagName("description");
				Element descriptionElement = (Element) descriptionNodeList.item(0);
				NodeList descriptionChildNodes = descriptionElement.getChildNodes();
				
				String description;
				try{
					description = ((Node) descriptionChildNodes.item(0)).getNodeValue();
				}catch (java.lang.NullPointerException e) {
					description = "";
				}
				
				fileDescrMap.get(xml).put(file, description);
			}
		
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		
		System.out.println(fileXML.getName() + " loded.");
		
		return true;
	}
	
	public boolean initAlbumsxml() {
		
		try {
			 
			File file = new File(path + "albums.xml");
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = dbf.newDocumentBuilder();
			Document doc = builder.parse(file);
			doc.getDocumentElement().normalize();
			
			NodeList nodeList = doc.getElementsByTagName("album");
			
			for (int i = 0; i < nodeList.getLength(); i++) {
				
				Node node = nodeList.item(i);
				Element element = (Element) node;
				
				NodeList folderNodeList = element.getElementsByTagName("folder");
				Element folderElement = (Element) folderNodeList.item(0);
				NodeList folderChildNodes = folderElement.getChildNodes();
				String folder = ((Node) folderChildNodes.item(0)).getNodeValue();
				
				NodeList titleNodeList = element.getElementsByTagName("title");
				Element titleElement = (Element) titleNodeList.item(0);
				NodeList titleChildNodes = titleElement.getChildNodes();
				String title = ((Node) titleChildNodes.item(0)).getNodeValue();
				
				NodeList dateNodeList = element.getElementsByTagName("date");
				Element dateElement = (Element) dateNodeList.item(0);
				NodeList dateChildNodes = dateElement.getChildNodes();
				String date = ((Node) dateChildNodes.item(0)).getNodeValue();
				
				NodeList descriptionNodeList = element.getElementsByTagName("description");
				Element descriptionElement = (Element) descriptionNodeList.item(0);
				NodeList descriptionChildNodes = descriptionElement.getChildNodes();
				String description = ((Node) descriptionChildNodes.item(0)).getNodeValue();
			
				folderTitleMap.put(folder, new String[]{title, date, description});
				
			}
		
		} catch (ParserConfigurationException | SAXException | IOException e) {
			e.printStackTrace();
			return false;
		}
		
		return true;

	}

}
