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
	
	
	public Unit(Pair<Integer, Integer> absPos, Pair<Float, Float> factPos, ITextureRegion unitTexture, MyEntity layer, VertexBufferObjectManager manager){
		this.x = factPos.getFirst();
		this.y = factPos.getSecond();
		this.layer = layer;
		this.manager = manager;
		this.unitTexture = unitTexture;
		sprite = new Sprite(x, y, unitTexture, manager);
		layer.attachChild(sprite);
		cellX = absPos.getFirst();
		cellY = absPos.getSecond();
	}
	
	
	public Unit(Pair<Integer, Integer> absPos, Pair<Float, Float> factPos, ITextureRegion unitTexture, boolean flipped, MyEntity layer, VertexBufferObjectManager manager){
		this.x = factPos.getFirst();
		this.y = factPos.getSecond();
		this.layer = layer;
		this.manager = manager;
		this.unitTexture = unitTexture;
		sprite = new Sprite(x, y, unitTexture, manager);
		sprite.setFlippedHorizontal(flipped);
		layer.attachChild(sprite);
		cellX = absPos.getFirst();
		cellY = absPos.getSecond();
	}
	
	public void setCell(Pair<Integer, Integer> absPos, Pair<Float, Float> factPos){
		int tmpCellX = absPos.getFirst();
		int tmpCellY = absPos.getSecond();
		if(this.cellX > tmpCellX && !isFlipped()){
			flip();
		}else if(this.cellX < tmpCellX && isFlipped()){
			flip();
		}
		setUnitPosition(factPos);
		this.cellX = tmpCellX;
		this.cellY = tmpCellY;
	}
	
	private void setUnitPosition(Pair<Float, Float> factPos){
		sprite.setPosition(factPos.getFirst(), factPos.getSecond());
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
