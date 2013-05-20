package com.danilov.heroesfai;

import java.util.HashMap;

import org.andengine.engine.Engine;
import org.andengine.engine.LimitedFPSEngine;
import org.andengine.engine.camera.Camera;
import org.andengine.engine.options.EngineOptions;
import org.andengine.engine.options.ScreenOrientation;
import org.andengine.engine.options.resolutionpolicy.FillResolutionPolicy;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.background.Background;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.ui.activity.BaseGameActivity;

import com.danilov.heroesfai.Battlefield.BattleQueue;
import com.danilov.heroesfai.Battlefield.BattlefieldController;
import com.danilov.heroesfai.Battlefield.Cell;
import com.danilov.heroesfai.units.Unit;
import com.danilov.heroesfai.units.UnitMap;
import com.danilov.util.MyEntity;
import com.danilov.util.Pair;

public class BattlefieldActivity extends BaseGameActivity {

	private static final String CELL_GREY = "CELL_GREY";
	private static final String CELL_RED = "CELL_RED";
	private static final String CELL_WHITE = "CELL_WHITE";
	
	private Camera camera;
	private Scene scene;
	private MyEntity bottomLayer;
	private MyEntity topLayer;
	private static final int CAMERA_WIDTH = 800;
	private static final int CAMERA_HEIGHT = 480;
	private HashMap<String, ITextureRegion > textureHashMap = new HashMap<String, ITextureRegion>();
	public static Unit u;
	private int cellXQuantity = 5;
	private int cellYQuantity = 5;
	private Cell battlefield[][] = new Cell[cellYQuantity][cellXQuantity];
	
	
	public EngineOptions onCreateEngineOptions() {
		camera = new Camera(0, 0, CAMERA_WIDTH, CAMERA_HEIGHT);
	    EngineOptions engineOptions = new EngineOptions(true, ScreenOrientation.LANDSCAPE_FIXED, 
	    new FillResolutionPolicy(), camera);
	    return engineOptions;
	}
	
	@Override
	public Engine onCreateEngine(EngineOptions pEngineOptions) {
	    return new LimitedFPSEngine(pEngineOptions, 60);
	}

	@Override
	public void onCreateResources(OnCreateResourcesCallback pOnCreateResourcesCallback) throws Exception {
		loadTextures();
		setCellResources();
		pOnCreateResourcesCallback.onCreateResourcesFinished();

	}

	@Override
	public void onCreateScene(OnCreateSceneCallback pOnCreateSceneCallback)
			throws Exception {
		scene = new Scene();
		scene.setBackground(new Background(0.09804f, 0.6274f, 0.8784f));
		mEngine.setScene(scene);
		bottomLayer = new MyEntity(scene);
		topLayer = new MyEntity(scene);
		scene.attachChild(bottomLayer);
		scene.attachChild(topLayer);
		createBattlefield();
		pOnCreateSceneCallback.onCreateSceneFinished(scene);
	}

	@Override
	public void onPopulateScene(Scene pScene,
			OnPopulateSceneCallback pOnPopulateSceneCallback) throws Exception {
		pOnPopulateSceneCallback.onPopulateSceneFinished();
	}
	
	private void test(){
		BattleQueue bq = new BattleQueue();
		u = new Unit(battlefield[1][0], textureHashMap.get("pikeman"), topLayer, mEngine.getVertexBufferObjectManager());
		bq.addToQueue(u);
		drawUnitTree(u);
		u = new Unit(battlefield[3][3], textureHashMap.get("pikeman"), topLayer, mEngine.getVertexBufferObjectManager());
		bq.addToQueue(u);
		BattlefieldController.setBattleQueue(bq);
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
						flag = true;
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
	
	private void loadTextures(){
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/");
		ITextureRegion tmpRegion;
		BitmapTextureAtlas myTexture = new BitmapTextureAtlas(getTextureManager(), 512, 512, TextureOptions.DEFAULT);
		tmpRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(myTexture, this, "cell_grey.png", 0, 0);
		textureHashMap.put(CELL_GREY, tmpRegion);
		tmpRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(myTexture, this, "cell_red.png", 75, 0);
		textureHashMap.put(CELL_RED, tmpRegion);
		tmpRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(myTexture, this, "cell_white.png", 150, 0);
		textureHashMap.put(CELL_WHITE, tmpRegion);
		myTexture.load();
		new UnitMap();
		BitmapTextureAtlas unitTexture = new BitmapTextureAtlas(getTextureManager(), 128, 128, TextureOptions.DEFAULT);
		tmpRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(unitTexture, this, UnitMap.getByKey("pikeman"), 0, 0);
		textureHashMap.put("pikeman", tmpRegion);
		unitTexture.load();
	}
	
	private void createBattlefield(){
		float yOffset = 52;
		for(int i = 0; i < cellYQuantity; i++){
			float xOffset = 212;
			for(int j = 0; j < cellXQuantity; j++){
				battlefield[i][j] = new Cell(xOffset, yOffset, j, i, battlefield, cellXQuantity, cellYQuantity, bottomLayer, mEngine.getVertexBufferObjectManager());
				xOffset += 75;
			}
			yOffset += 75;
		}
		test();
	}
	
	
	private void setCellResources(){
		Cell.setCellResources(textureHashMap.get(CELL_GREY), 0);
		Cell.setCellResources(textureHashMap.get(CELL_WHITE), 1);
		Cell.setCellResources(textureHashMap.get(CELL_RED), 2);
	}


}
