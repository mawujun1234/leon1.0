package com.mawujun.plugins.baiduMapAll;

import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.utils.CoordinateConverter;

public class Taa {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		CoordinateConverter converter  = new CoordinateConverter();  
		//converter.from(CoordType.CoordType_BD09); 
		converter.from(CoordinateConverter.CoordType.COMMON);
		LatLng sourceLatLng=new LatLng(29.81955,121.515903);
		
		converter.coord(sourceLatLng);  
		LatLng desLatLng = converter.convert(); 
		System.out.println(desLatLng);

	}

}
