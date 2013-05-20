package com.danilov.heroesfai.Battlefield;

import com.danilov.heroesfai.units.Unit;
import com.danilov.util.Pair;

public class BattlefieldController implements AbstractController{
	
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

	public void onTouch() {
		if(cell.getState() == Cell.WHITE){
			moveUnit();
		}
	}
	
	private void moveUnit(){
		findCell(battleQueue.getUnit()).setUnit(null);
		battleQueue.getUnit().setCell(cell.getPositionInField(), cell.getPositionToPlace());
		cell.setUnit(battleQueue.getUnit());
		battleQueue.nextUnit();
		drawUnitTree(battleQueue.getUnit());
	}
	
	private Cell findCell(Unit u){
		Cell cell = null;
		for(int i = 0; i < cellYQuantity; i++){
			for(int j = 0; j < cellXQuantity; j++){
				if(battlefield[i][j].getUnit() == u){
					cell = battlefield[i][j];
					break;
				}
			}
		}
		return cell;
	}
	
	
	public void drawUnitTree(Unit u){
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
