package us.ac8jo.readGPX;

import java.util.ArrayList;
import java.util.List;

import us.ac8jo.DataClasses.GpsData;

public class GPXReader {
	public static void main(String argv[])
	{
		List<GpsData> GPSData =new ArrayList<GpsData>();
		
		GPSData=ReadGPXFile.readFile(argv[0]);
		int a=1;
	}
}
