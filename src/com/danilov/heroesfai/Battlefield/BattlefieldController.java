package com.danilov.heroesfai.Battlefield;

import org.andengine.entity.scene.IOnSceneTouchListener;
import org.andengine.entity.scene.Scene;
import org.andengine.input.touch.TouchEvent;

import com.danilov.heroesfai.BattlefieldActivity;
import com.danilov.heroesfai.units.Unit;
import com.danilov.util.Pair;

public class BattlefieldController{
	
	private Cell cell;
	private Cell battlefield[][];
	private int cellYQuantity;
	private int cellXQuantity;
	private static BattleQueue battleQueue;
	
	public BattlefieldController(Cell cell, Cell battlefield[][], int cellXQuantity, int cellYQuantity){
		this.cell = cell;
		this.battlefield = battlefield;
		this.cellXQuantity = cellXQuantity;
		this.cellYQuantity = cellYQuantity;
	}

	public boolean onTouch() {
		if(cell.getState() == Cell.WHITE){
			battleQueue.getUnit().setCell(cell);
			battleQueue.nextUnit();
			drawUnitTree(battleQueue.getUnit());
		}
		return false;
	}
	
	private void drawUnitTree(Unit u){
		Pair<Integer, Integer> p = u.getCellPosition();
		int cellX = p.getFirst();
		int cellY = p.getSecond();
		for(int i = 0; i < cellYQuantity; i++){
			for(int j = 0; j < cellXQuantity; j++){
				boolean flag = false;
				if(cellX <= j + 1 && cellX >= j - 1){
					if(cellY <= i + 1 && cellY >= i - 1){
						if(j != cellX || i != cellY){
							flag = true;
						}
						if(battlefield[i][j].getUnit() != null){
							flag = false;
						}
					}
				}
				if(flag){
					battlefield[i][j].setWhite();
				}else{
					battlefield[i][j].setGrey();
				}
			}
		}
	}
	
	//CALL THIS METHOD BEFORE CREATING OBJECT OF THIS CLASS
	public static void setBattleQueue(BattleQueue queue){
		battleQueue = queue;
	}

}
