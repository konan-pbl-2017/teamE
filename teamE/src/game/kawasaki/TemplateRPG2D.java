package game.kawasaki;

import java.awt.Color;
import java.util.Random;
import java.math.BigDecimal;
import java.util.ArrayList;


import framework.RWT.RWTContainer;
import framework.RWT.RWTFrame3D;
import framework.RWT.RWTVirtualController;
import framework.game2D.Sprite;
import framework.gameMain.BaseScenarioGameContainer;
import framework.gameMain.SimpleRolePlayingGame;
import framework.model3D.Universe;
import framework.scenario.Event;
import framework.scenario.ScenarioState;
import template.shooting2D.EnemyBullet;
import template.shooting2D.MyShipBullet;

public class TemplateRPG2D extends SimpleRolePlayingGame {
	private MapStage map;
	private Player player;
	private Player player2;
	private Sprite king;
	private Sprite enemy;
	
	private MyBase myBase;
	//private ArrayList<E> enemyUnitList = new ArrayList<EnemyUnit>();
	//private ArrayList<EnemyUnit> enemyUnitFromSpawn = new ArrayList<EnemyUnit>();
	
	
	private EnemySpawn enemySpawn;
	private ArrayList<EnemyUnit> enemyUnitList = new ArrayList<EnemyUnit>();
	private ArrayList<EnemyUnit> enemyUnitFromSpawn = new ArrayList<EnemyUnit>();
	
	private long lastMyShipBulletShootTime = 0;
	private long lastMyShipBulletShootDanmakuTime = 0;
	private long lastEnemyShootTime = 0;
	private long lastEnemyMeetTime = 0;
	
	// 速度によって物体が動いている時にボタンを押せるかどうかを判定するフラグ
	private boolean disableControl = false;

	@Override
	public void init(Universe universe) {
		map = new MapStage();
		universe.place(map);
		camera.addTarget(map);

		// プレイヤーの配置
		player = new Player("data\\RPG\\player.png");
		player.setPosition(14.0, 14.0);
		player.setCollisionRadius(0.5);
		universe.place(player);
		
		// プレイヤー2の配置
		player2 = new Player("data\\RPG\\block.jpg");
		player2.setPosition(14.0, 14.0);
		player2.setCollisionRadius(0.5);
		universe.place(player2);
		
		
		
		// 自分の基地の配置
		myBase = new MyBase("data\\images\\kiti.gif");
		myBase.setPosition(30.0, 16.0);
		myBase.setCollisionRadius(0.5);
		universe.place(myBase);
		
		//敵の発生場所
		enemySpawn = new EnemySpawn("data\\images\\doukutu.jpg");
		enemySpawn.setPosition(0.0, 16.0);
		enemySpawn.setCollisionRadius(0.5);
		universe.place(enemySpawn);
		
		/*
		myBase = new Sprite("data\\RPG\\player.png");
		myBase.setPosition(0.0, 14.0);
		myBase.setCollisionRadius(0.5);
		universe.place(myBase);
		*/
		
		//敵のユニット発生
		//enemyUnitList = new ArrayList<enemyUnits>();
		//enemyUnitList = Sprite("data\\RPG\\monster.png");
		
		
		// mapを画面の中央に
		setMapCenter(0.0, 0.0);
		setViewRange(32, 32);
		
		// プレイヤーを画面の中央に
		setCenter(player2);
		
		
		// シナリオの設定
		setScenario("data\\TemplateRPG\\Scenario\\scenario2.xml");
		
	}
	
	@Override
	public void subInit(Universe universe) {
		enemy = new Sprite("data\\RPG\\monster.png", 10.0f);
		enemy.setPosition(15.0, 15.0);
		universe.place(enemy);
		
		// 敵を画面の中央に
		setSubCenter(enemy);
	}

	@Override
	public RWTFrame3D createFrame3D() {
		frame = new RWTFrame3D();
		frame.setSize(1000, 800);
		frame.setTitle("Template for 2D Role Playing Game");
		frame.setBackground(Color.BLACK);
		return frame;
	}
	
	@Override
	protected RWTContainer createRWTContainer() {
		container = new ScenarioGameContainer();
		return container;
	}
	
	// 戦闘用画面の作成
	public BaseScenarioGameContainer createSubRWTContainer() {
		subContainer = new FightContainer();
		return subContainer;
	}
	
	@Override
	public void progress(RWTVirtualController virtualController, long interval) {
		// 迷路ゲームステージを構成するオブジェクトの位置とプレイヤーの位置をもとに速度を0にするかどうかを調べる。
		boolean resetVelocity = map.checkGridPoint(player);

		// 誤差による位置修正を行うため、プレイヤーのx成分とy成分が0.0の時、位置の値を切り上げる
		if (player.getVelocity().getX() == 0.0
				&& player.getVelocity().getY() == 0.0) {
			player.setPosition(new BigDecimal(player
					.getPosition().getX())
			.setScale(0, BigDecimal.ROUND_HALF_UP).doubleValue(),
			new BigDecimal(player.getPosition().getY())
			.setScale(0, BigDecimal.ROUND_HALF_UP)
			.doubleValue());
		}

		// 速度が0.0にするフラグが立っていれば、速度を0にする
		if (resetVelocity) {
			player.setVelocity(0.0, 0.0);
			disableControl = false;
		}
		// キャラが移動していなければ、キー操作の処理を行える。
		if(!disableControl){
			// キー操作の処理
			// 左
			if (virtualController.isKeyDown(0, RWTVirtualController.LEFT)) {
				player.setVelocity(-4.0, 0.0);
				disableControl = true;
			}
			// 右
			else if (virtualController.isKeyDown(0, RWTVirtualController.RIGHT)) {
				player.setVelocity(4.0, 0.0);
				disableControl = true;
	
			}
			// 上
			else if (virtualController.isKeyDown(0, RWTVirtualController.UP)) {
				player.setVelocity(0.0, 4.0);
				disableControl = true;
			}
			// 下
			else if (virtualController.isKeyDown(0, RWTVirtualController.DOWN)) {
				player.setVelocity(0.0, -4.0);
				disableControl = true;
			}
		}
		player.motion(interval, map);
		
		//---------------------追加---------
		
		// 敵のアクション処理
		// 弾幕の発射
		 Random rnd = new Random();
		if (System.currentTimeMillis() - lastEnemyShootTime > rnd.nextInt(100000)) {
			enemyUnitFromSpawn = enemySpawn.goEnemyUnits();
			this.setEnemyUnit(enemyUnitFromSpawn);
			lastEnemyShootTime = System.currentTimeMillis();
		}

		// /////////////////////////////////////////////////////////
		//
		// 各登場物を動かす処理
		//
		// ////////////////////////////////////////////////////////

		// ウィンドウ内に出ようとした時、自機の位置を端に固定する
			/*
		if (!(myShipSprite.isInScreen(viewRangeWidth, viewRangeHeight))) {
			if (myShipSprite.getPosition().getX() >= viewRangeWidth / 2) {
				myShipSprite.setPosition(viewRangeWidth / 2, myShipSprite.getPosition().getY());
			}
			if (myShipSprite.getPosition().getX() <= -1.0 * viewRangeWidth / 2) {
				myShipSprite.setPosition(-1.0 * viewRangeWidth / 2, myShipSprite.getPosition().getY());
			}
			if (myShipSprite.getPosition().getY() >= viewRangeHeight / 2) {
				myShipSprite.setPosition(myShipSprite.getPosition().getX(), viewRangeHeight / 2);
			}
			if (myShipSprite.getPosition().getY() <= -1.0 * viewRangeHeight / 2) {
				myShipSprite.setPosition(myShipSprite.getPosition().getX(), -1.0 * viewRangeHeight / 2);
			}
			myShipSprite.motion(interval);
		}
		*/
			
		
			// 敵の弾を動かす。同時にウィンドウ外に出てしまったかどうかを判定し、出てしまったらウインドウから弾を消す。
			for (int i = 0; i < enemyUnitList.size(); i++) {
				EnemyUnit enemyUnit = enemyUnitList.get(i);
				enemyUnit.motion(interval);		// 敵の弾の移動
				if (enemyUnit.isInScreen((int)viewRangeWidth, (int)viewRangeHeight) == false) {
					// 敵の弾を消す
					universe.displace(enemyUnit);
					enemyUnitList.remove(i);
				}
			}

			// /////////////////////////////////////////////////////////
			//
			// 各登場物を動かした後の処理
			//
			// ////////////////////////////////////////////////////////

			// プレイヤーと〇〇の衝突判定
			// 衝突判定（プレイヤーと敵の弾）
			//myBase.myBaseHP=100;
			
			for (int i = 0; i < enemyUnitList.size(); i++) {
				
				EnemyUnit enemyUnit = enemyUnitList.get(i);
				lastEnemyMeetTime = System.currentTimeMillis();
				if (myBase.checkCollision(enemyUnit)) {
					//次の間隔で攻撃
					//if (System.currentTimeMillis() - lastEnemyMeetTime > 1000){
						System.out.println(myBase.myBaseHP);
						//myBase.myBaseHP=(myBase.myBaseHP-enemySpawn.enemyAttack);
						enemyUnit.enemyHP=enemyUnit.enemyHP-1;
						System.out.println("敵( " + i + " )から攻撃を受けた！");
					//}
				}
			}
			
			//敵を倒したときに、画面から消す
			for (int i = 0; i < enemyUnitList.size(); i++) {
				EnemyUnit enemyUnit = enemyUnitList.get(i);
				enemyUnit.motion(interval);		// 敵の弾の移動
				if (enemyUnit.enemyHP < 0) {
					// 敵の弾を消す
					universe.displace(enemyUnit);
					enemyUnitList.remove(i);
				}
			}
			
			//自分の壁が破られたとき
			if(myBase.myBaseHP < 0){
				System.out.println("倒されました");
				System.exit(0);
			}
			
		
		
		
		// 衝突判定
		if (player.checkCollision(king)) {
			// プレイヤーと王様がぶつかった場合
			scenario.fire("王様とぶつかる");	// 「王様とぶつかる」というイベントを発生する（シナリオが進む）
		}
	}
	
	//--------------------------Shooting progressのあとの部分からひっぱってきた
	
	/**
	 * 弾幕が入ったリスト（myShipBulletFromMyShip）をプレイヤーの弾のリストに設定する
	 * 
	 * @param myShipBulletFromMyShip
	 */
	/*
	public void setMyShipBullet(ArrayList<MyShipBullet> myShipBulletFromMyShip) {
		for (int i = 0; i < myShipBulletFromMyShip.size(); i++) {
			universe.place(myShipBulletFromMyShip.get(i));
			this.myShipBulletList.add(myShipBulletFromMyShip.get(i));
		}
	}
	*/

	/**
	 * 弾幕が入ったリスト（enemyBulletListFromEnemy）を敵の弾のリストに設定する
	 * 
	 * @param enemyBulletListFromEnemy
	 */
	public void setEnemyUnit(ArrayList<EnemyUnit> enemyUnitListFromSpawn) {
		for (int i = 0; i < enemyUnitListFromSpawn.size(); i++) {
			universe.place(enemyUnitListFromSpawn.get(i));
			this.enemyUnitList.add(enemyUnitListFromSpawn.get(i));
		}
	}
	
	//------------------------------------------------------------------

	@Override
	public void action(String action, Event event, ScenarioState nextState) {
		// シナリオ進行による世界への作用をここに書く
		if (action.equals("startFight")) {
			changeToSubContainer();
		} else if (action.equals("endFight")) {
			changeToMainContainer();
		}
	}
	

	/**
	 * ゲームのメイン
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		SimpleRolePlayingGame game = new TemplateRPG2D();
		game.setFramePolicy(5, 33, false);
		game.start();
	}

}
