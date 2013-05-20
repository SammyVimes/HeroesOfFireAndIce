package com.danilov.heroesfai.Battlefield;

import java.util.ArrayList;

import com.danilov.heroesfai.units.Unit;

public class BattleQueue {

	ArrayList<Unit> units = new ArrayList<Unit>();
	private int currentUnit;
	
	public BattleQueue(){	
		currentUnit = 0;
	}
	
	public void addToQueue(Unit unit){
		units.add(unit);
	}
	
	public void deleteFromQueue(Unit unit){
		units.remove(unit);
	}
	
	public void nextUnit(){
		if(currentUnit == units.size() - 1){
			currentUnit = 0;
		}else{
			currentUnit++;
		}
	}
	
	public Unit getUnit(){
		return units.get(currentUnit);
	}
}
