package com.danilov.heroesfai.units;

import java.util.HashMap;

public class UnitMap {
	
	private static HashMap<String, String> unitsHashMap = new HashMap<String, String>();
	
	public UnitMap(){
		unitsHashMap.put("pikeman", "pikeman.png");
	}
	
	public HashMap<String, String> getUnitsMap(){
		return unitsHashMap;
	}
	
	public static String getByKey(String key){
		return unitsHashMap.get(key);
	}
}
