package com.danilov.heroesfai.units;

import org.andengine.entity.sprite.Sprite;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

import com.danilov.heroesfai.Battlefield.Cell;
import com.danilov.util.MyEntity;
import com.danilov.util.Pair;



public class Unit{
	
	
	private Sprite sprite;
	private MyEntity layer;
	private ITextureRegion unitTexture;
	private VertexBufferObjectManager manager;
	private float x;
	private float y;
	private int cellX;
	private int cellY;
	private Cell cell;
	
	
	public Unit(Cell cell, ITextureRegion unitTexture, MyEntity layer, VertexBufferObjectManager manager){
		this.x = cell.getPositionToPlace().getFirst();
		this.y = cell.getPositionToPlace().getSecond();
		this.cell = cell;
		this.layer = layer;
		this.manager = manager;
		this.unitTexture = unitTexture;
		sprite = new Sprite(x, y, unitTexture, manager);
		layer.attachChild(sprite);
		Pair<Integer, Integer> p = cell.getPositionInField();
		cellX = p.getFirst();
		cellY = p.getSecond();
	}
	
	
	public Unit(Cell cell, ITextureRegion unitTexture, boolean flipped, MyEntity layer, VertexBufferObjectManager manager){
		this.x = cell.getPositionToPlace().getFirst();
		this.y = cell.getPositionToPlace().getSecond();
		this.cell = cell;
		this.layer = layer;
		this.manager = manager;
		this.unitTexture = unitTexture;
		sprite = new Sprite(x, y, unitTexture, manager);
		sprite.setFlippedHorizontal(flipped);
		layer.attachChild(sprite);
		Pair<Integer, Integer> p = cell.getPositionInField();
		cellX = p.getFirst();
		cellY = p.getSecond();
	}
	
	public void setCell(Cell cell){
		Pair<Integer, Integer> p = cell.getPositionInField();
		int tmpCellX = p.getFirst();
		int tmpCellY = p.getSecond();
		this.cell.setUnit(null);
		if(this.cellX > tmpCellX && !isFlipped()){
			flip();
		}else if(this.cellX < tmpCellX && isFlipped()){
			flip();
		}
		setUnitPosition(cell);
		cell.setUnit(this);
		this.cellX = tmpCellX;
		this.cellY = tmpCellY;
		this.cell = cell;
	}
	
	private void setUnitPosition(Cell cell){
		Pair<Float, Float> p = cell.getPositionToPlace();
		sprite.setPosition(p.getFirst(), p.getSecond());
	}
	
	private void flip(){
		boolean isFlipped = sprite.isFlippedHorizontal();
		sprite.setFlippedHorizontal(!isFlipped);
	}
	
	private boolean isFlipped(){
		return sprite.isFlippedHorizontal();
	}
	
	public Sprite getSprite(){
		return sprite;
	}
	
	public Pair<Integer, Integer> getCellPosition(){
		return new Pair<Integer, Integer>(cellX, cellY);
	}
	
	
}
