/**
 * 
 */
package us.ac8jo.readGPX;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import us.ac8jo.DataClasses.GpsData;

/**
 * @author arohne
 *
 */
public class ReadGPXFile {
	public static List<GpsData> readFile(String fileName){
		List<GpsData> gpsData = new ArrayList<GpsData>();
		try{
			File fGpxFile = new File(fileName);
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(fGpxFile);
			doc.getDocumentElement().normalize();
			//System.out.println("Root Element: "+doc.getDocumentElement().getNodeName());
			NodeList nlTrk = doc.getElementsByTagName("trkpt");
			//System.out.println("There are " +nlTrk.getLength() +" trk elements");
			for (int items = 0; items < nlTrk.getLength(); items++) {
				Element eTrk = (Element) nlTrk.item(items);

				// System.out.println(eTrk.getAttribute("lat")); // <~~ Works
				// System.out.println(eTrk.hasChildNodes()); // <~~ returns TRUE
				for (int cn = 0; cn < eTrk.getChildNodes().getLength(); cn++) {
					if (eTrk.getChildNodes().item(cn).getNodeName().toString().equalsIgnoreCase("time")) 
					{
						GpsData gps = new GpsData();
						gps.Latitude=Double.parseDouble(eTrk.getAttribute("lat"));
						gps.Longitude=Double.parseDouble(eTrk.getAttribute("lon"));
						DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss'Z'"); 
						gps.ZuluTime=formatter.parse(eTrk.getChildNodes().item(cn).getTextContent().toString());
						if(gpsData.size()>0)
						{
							double p1=(gps.Longitude-gpsData.get(gpsData.size()-1).Longitude)*Math.cos(0.5*(gps.Latitude+gpsData.get(gpsData.size()-1).Latitude));
							double p2=gps.Latitude-gpsData.get(gpsData.size()-1).Latitude;
							double dist=3.2808399*637100*Math.sqrt(p1*p1+p2*p2);
							gps.Distance=dist;
							// Speed
						}
						gpsData.add(gps);
						/*
						 * p1 = (lonp - lon1) cos ( 0.5*(latp+lat1) ) //convert lat/lon to radians 
						 * p2 = (latp - lat1)
						 * distance = EarthRadius * sqrt( p1*p1 + p2*p2)
						 * use EarthRadius = 6371000 meters and your distance will be in meters
						 */
						
					}
					// System.out.println(cn+" - "
					// +eTrk.getChildNodes().item(cn).getNodeName());
				}
			}

			//System.out.println(eTrk.getChildNodes().getLength()); // <~~ returns 3
			
			
			//System.out.println(eTrk.hasAttribute("time")); // <~~ returns false
			//System.out.println(eTrk.getAttribute("time")); // <~~ does not work (prints empty line)
			
			//Node nTrk=nlTrk.item(0);
			//NodeList nlTrkSeg = (NodeList) nlTrk.item(0);
			//System.out.println("There are " +nlTrkSeg.getLength() +" trkSeg elements");
			
			/*
			
			for(int tfLine=0;tfLine<nlTrkSeg.getLength();tfLine++){
				Node nNode = nlTrkSeg.item(tfLine);
				if(nNode.getNodeType()==Node.ELEMENT_NODE){
					if(nNode!=null){
						Element trkPt = (Element) nNode;
						System.out.println("lat= " +getTagValue("lat",trkPt));
					}
				}
			}
			*/
			
			int a=1;
			return gpsData;
		}catch (Exception e){
			e.printStackTrace();
		}
		return gpsData;
	
	}
		
	private static String getTagValue(String sTag, Element eElement) {
		NodeList nlList = eElement.getElementsByTagName(sTag).item(0).getChildNodes();
		 
        Node nValue = (Node) nlList.item(0);
 
	return nValue.getNodeValue();
	}	
}


