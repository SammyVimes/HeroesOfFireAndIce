package com.danilov.heroesfai.Battlefield;

import org.andengine.entity.sprite.Sprite;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

import com.danilov.heroesfai.units.Unit;
import com.danilov.util.MyEntity;
import com.danilov.util.Pair;





public class Cell{
	
	public static int GREY = 0;
	public static int WHITE = 1;
	public static int RED = 2;
	
	private Sprite sprite;
	private int curState;
	private MyEntity layer;
	private static ITextureRegion cellTextures[] = new ITextureRegion[3];
	private VertexBufferObjectManager manager;
	private int fieldX;
	private int fieldY;
	private float x;
	private float y;
	private Unit unit;
	private AbstractController controller;
	
	public Cell(float x, float y, int fieldX, int fieldY, MyEntity layer, VertexBufferObjectManager manager){
		curState = GREY;
		this.x = x;
		this.y = y;
		this.fieldX = fieldX;
		this.fieldY = fieldY;
		this.layer = layer;
		this.manager = manager;
		sprite = new MySprite(x, y, cellTextures[GREY], manager);
		layer.attachChild(sprite);
		registerTouchArea();
	}
	
	public void setController(AbstractController controller){
		this.controller = controller;
	}
	
	private void registerTouchArea(){
		layer.getScene().registerTouchArea(sprite);
	}
	
	private void unregisterTouchArea(){
		layer.getScene().unregisterTouchArea(sprite);
	}
	
	public Pair<Float, Float> getPositionToPlace(){
		return new Pair<Float, Float>(x + sprite.getWidth()/4, y - sprite.getHeight()/3);
	}
	
	public Pair<Integer, Integer> getPositionInField(){
		return new Pair<Integer, Integer>(fieldX, fieldY);
	}
	
	public Sprite getSprite(){
		return sprite;
	}
	
	public void setRed(){
		curState = RED;
		sprite.detachSelf();
		unregisterTouchArea();
		sprite = new MySprite(x, y, cellTextures[RED], manager);
		layer.attachChild(sprite);
		registerTouchArea();
	}
	
	public void setWhite(){
		curState = WHITE;
		sprite.detachSelf();
		unregisterTouchArea();
		sprite = new MySprite(x, y, cellTextures[WHITE], manager);
		layer.attachChild(sprite);
		registerTouchArea();
	}
	
	public void setGrey(){
		curState = GREY;
		sprite.detachSelf();
		unregisterTouchArea();
		sprite = new MySprite(x, y, cellTextures[GREY], manager);
		layer.attachChild(sprite);
		registerTouchArea();
	}
	
	public int getState(){
		return curState;
	}
	
	public void setUnit(Unit unit){
		this.unit = unit;
	}
	
	public Unit getUnit(){
		return unit;
	}
	
	/*CALL THIS METHOD BEFORE CREATING ANY CELLS!*/
	public static void setCellResources(ITextureRegion texture, int id){
		cellTextures[id] = texture;
	}
	
	private class MySprite extends Sprite{

		public MySprite(float pX, float pY, ITextureRegion pTextureRegion,
				VertexBufferObjectManager pSpriteVertexBufferObject) {
			super(pX, pY, pTextureRegion, pSpriteVertexBufferObject);
		}
		
		@Override
        public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY) {
			if(pSceneTouchEvent.isActionUp()){
				controller.onTouch();
			}
			return false;
		}
		
	}
	
	
	
}
