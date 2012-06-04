/**
 * 
 */
package us.ac8jo.readGPX;

import java.io.File;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * @author arohne
 *
 */
public class ReadGPXFile {
	public static void readFile(String fileName){
		try{
			File fGpxFile = new File(fileName);
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(fGpxFile);
			doc.getDocumentElement().normalize();
			System.out.println("Root Element: "+doc.getDocumentElement().getNodeName());
			NodeList nlTrk = doc.getElementsByTagName("trkpt");
			System.out.println("There are " +nlTrk.getLength() +" trk elements");
			Element eTrk = (Element) nlTrk.item(0);
			
			System.out.println(eTrk.getAttribute("lat"));  // <~~ Works
			System.out.println(eTrk.hasChildNodes());      // <~~ returns TRUE
			System.out.println(eTrk.hasAttribute("time")); // <~~ returns false
			System.out.println(eTrk.getAttribute("time")); // <~~ does not work (prints empty line)
			
			Node nTrk=nlTrk.item(0);
			NodeList nlTrkSeg = (NodeList) nlTrk.item(0);
			System.out.println("There are " +nlTrkSeg.getLength() +" trkSeg elements");
			
			
			
			for(int tfLine=0;tfLine<nlTrkSeg.getLength();tfLine++){
				Node nNode = nlTrkSeg.item(tfLine);
				if(nNode.getNodeType()==Node.ELEMENT_NODE){
					if(nNode!=null){
						Element trkPt = (Element) nNode;
						System.out.println("lat= " +getTagValue("lat",trkPt));
					}
				}
			}
			
			
			int a=1;
		}catch (Exception e){
			e.printStackTrace();
		}
	
	}
		
	private static String getTagValue(String sTag, Element eElement) {
		NodeList nlList = eElement.getElementsByTagName(sTag).item(0).getChildNodes();
		 
        Node nValue = (Node) nlList.item(0);
 
	return nValue.getNodeValue();
	}	
}


