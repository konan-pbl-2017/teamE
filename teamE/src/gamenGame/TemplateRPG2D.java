// auther kawasaki
// 川崎の書いたプログラムに、Tigarのプログラムを追加し、敵とタワーの同時描写をテスト　12/21 11:23　→　成功
package gamenGame;

import java.awt.Color;
import java.util.Random;
import java.math.BigDecimal;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;


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
import framework.game2D.Sprite;
import framework.game2D.Velocity2D;
import template.shooting2D.TemplateShooting2D;

public class TemplateRPG2D extends SimpleRolePlayingGame {
	private MapStage map;
	private Player player;
	private Player player2;
	private Player castle[] = new Player[35];//addTigar
	private Sprite king;
	private Sprite enemy;
	public int i=0;//addTigar
	public int count; //by.kawasaki 敵を倒した数をカウント
	
	/*
	MainFrame mf;//画面遷移用by.kawasaki 12/22
	String str;
	*/
	
	int unitval = 0; //	置けるユニットの選択:by.tiger	##未実装##


	int castlenum = 0;//addTigar
	boolean castleable = true;// 城が置ける状態か否か:by.tiger
	// 速度によって物体が動いている時にボタンを押せるかどうかを判定するフラグ
	
	
	private MyBase myBase;
	//private ArrayList<E> enemyUnitList = new ArrayList<EnemyUnit>();
	//private ArrayList<EnemyUnit> enemyUnitFromSpawn = new ArrayList<EnemyUnit>();
	
	//by.kawasaki 敵のスポーン・ユニットを格納するArrayList
	private EnemySpawn enemySpawn;
	private ArrayList<EnemyUnit> enemyUnitList = new ArrayList<EnemyUnit>();
	private ArrayList<EnemyUnit> enemyUnitFromSpawn = new ArrayList<EnemyUnit>();
	
	//by.kawasaki if文で毎秒ループさせるためのタイム変数
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
		
		//by.kawasaki これは画面の中央にさせるための一時的なインスタンス 　
		//プレイヤー2の配置
		
		player2 = new Player("data\\RPG\\block.jpg");
		player2.setPosition(14.0, 14.0);
		player2.setCollisionRadius(0.5);
		universe.place(player2);
		
		
		//by.kawasaki 自分の守る基地
		// 自分の基地の配置
		myBase = new MyBase("data\\towerdefence\\yousai.jpg");
		myBase.setPosition(32.0, 14.0);
		myBase.setCollisionRadius(0.5);
		universe.place(myBase);
		
		//by. kawasaki 敵の発生場所
		enemySpawn = new EnemySpawn("data\\towerdefence\\spawn.jpg");
		enemySpawn.setPosition(0.0, 14.0);
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
		
		
		// mapを画面の中央に 　←　なぜか僕（川崎）の家だと失敗する笑　頭にきますよ！ｗ
		setMapCenter(14.0, 14.0);
		setViewRange(30, 30);
		
		// プレイヤーを画面の中央に
		setCenter(player2);
		
		
		// シナリオの設定
		setScenario("data\\TemplateRPG\\Scenario\\scenario2.xml");
		
	}
	
	// これいる？　by.kawasaki
	@Override
	public void subInit(Universe universe) {
		enemy = new Sprite("data\\RPG\\monster.png", 10.0f);
		enemy.setPosition(15.0, 15.0);
		universe.place(enemy);
		
		// 敵を画面の中央に
		setSubCenter(enemy);
	}
	
	//描写する画面の大きさ？
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
			/* 川崎の部分、これをTigarのやつと交換。下にあります
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
			*/
			
			// by.tigar
			// 左
			if (virtualController.isKeyDown(0, RWTVirtualController.LEFT)) {
				player.setVelocity(-10.0, 0.0);
				disableControl = true;
				castleable = true;// 城を置ける状態にする:by.tiger
			}
			// 右
			else if (virtualController.isKeyDown(0, RWTVirtualController.RIGHT)) {
				player.setVelocity(10.0, 0.0);
				disableControl = true;
				castleable = true;// 城を置ける状態にする:by.tiger
			}
			// 上
			else if (virtualController.isKeyDown(0, RWTVirtualController.UP)) {
				player.setVelocity(0.0, 10.0);
				disableControl = true;
				castleable = true;// 城を置ける状態にする:by.tiger
			}
			// 下
			else if (virtualController.isKeyDown(0, RWTVirtualController.DOWN)) {
				player.setVelocity(0.0, -10.0);
				disableControl = true;
				castleable = true;// 城を置ける状態にする:by.tiger
			}
		}
		player.motion(interval, map);
		
		if (virtualController.isKeyDown(1, RWTVirtualController.UP))unitval = 1;
		if (virtualController.isKeyDown(1, RWTVirtualController.DOWN))unitval = 2;
		if (virtualController.isKeyDown(1, RWTVirtualController.RIGHT))unitval = 3;
		if (virtualController.isKeyDown(1, RWTVirtualController.LEFT))unitval = 4;
		//ユニットの選択:by.tiger	##未実装##

		if (unitval==1)player.setImage("data\\player\\playerU.gif");
		if (unitval==2)player.setImage("data\\player\\playerD.gif");
		if (unitval==3)player.setImage("data\\player\\playerR.gif");
		if (unitval==4)player.setImage("data\\player\\playerL.gif");


		if (virtualController.isKeyDown(0, RWTVirtualController.BUTTON_B) && castleable == true) {
			if (i<= 30){
			castle[i] = new Player("data\\RPG\\block.jpg");
			castle[i].setPosition(player.getPosition());
			castle[i].setCollisionRadius(0.5);
			universe.place(castle[i]);
			}//30個までは普通に生成:by.tiger
			i++;
			System.out.println(i);
			if (i>= 30){
			castle[i%30].setPosition(player.getPosition());
			}//30個を過ぎれば最初に置いたユニットから消していく(+HPを初期化すれば良いね。):by.tiger
			castleable = false;

		}

		//---------------------追加---------
		
		// 敵のアクション処理　by.kawasaki
		// 弾幕の発射　　ランダムの間隔で敵を出すためにランダム関数を使用
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
			
		
		//出ることは絶対に無いのですが、一応書いておきますね by.kawasaki 
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
			
		//自分の基地との当たり判定に利用する.kawasaki
		//myBase.myBaseHP=100;
		/*
		for (int i = 0; i < enemyUnitList.size(); i++) {
				
			EnemyUnit enemyUnit = enemyUnitList.get(i);
			enemyUnit.HP = enemyUnitList.get(i).HP;
			lastEnemyMeetTime = System.currentTimeMillis();
			if (myBase.checkCollision(enemyUnit)) {
				//次の間隔で攻撃
				//if (System.currentTimeMillis() - lastEnemyMeetTime > 100){
					System.out.println(myBase.myBaseHP);
					System.out.println(enemyUnit.HP);
					myBase.myBaseHP=(myBase.myBaseHP-enemySpawn.enemyAttack);
					enemyUnit.HP=enemyUnit.HP-10;//敵もダメージ受ける
					System.out.println("敵( " + i + " )から攻撃を受けた！");
					enemySpawn.bulletX =0 ;
				//}
			}
			System.out.println("Base HP = " + myBase.myBaseHP);
			System.out.println(i + "番目" + "enemy HP = " + enemyUnit.HP);
		}
			
		//敵を倒したときに、画面から消す
		for (int i = 0; i < enemyUnitList.size(); i++) {
			EnemyUnit enemyUnit = enemyUnitList.get(i);
			//enemyUnit.motion(interval);		// 敵の弾の移動
			if (enemyUnit.HP < 0) {
				// 敵の弾を消す
				//universe.displace(enemyUnit);
				//enemyUnitList.remove(i);
			}
		}
		*/
		
		//-12/19　by.kawasaki
		// 衝突判定（自分の基地orタワー　と　敵のユニット）
		// 敵ユニットが消える処理
		
		//for (int i = 0; i < myShipBulletList.size(); i++) {
			//MyShipBullet myShipBullet = myShipBulletList.get(i);
			for (int j = 0; j < enemyUnitList.size(); j++) {
				EnemyUnit enemyUnit = enemyUnitList.get(j);
				if (myBase.checkCollision(enemyUnit)) {
					// 基地の前に敵が来ると、攻撃してから消える処理 by.kawasaki
					System.out.println("自分の基地" + myBase.myBaseHP);
					System.out.println(enemyUnit.HP);
					myBase.myBaseHP=(myBase.myBaseHP-enemySpawn.enemyAttack);
					enemyUnit.HP=enemyUnit.HP-1000;//敵もダメージ受ける
					System.out.println("敵( " + j + " )から攻撃を受けた！");
					enemySpawn.bulletX =0 ;
					
				}
				if (player.checkCollision(enemyUnit)) {
					// カーソルの近くに敵がいると、その敵のHPを０にする（##テスト機能##） 12/18 by.kawasaki
					enemyUnit.HP-=100;
				}
				if (myBase.checkCollision(enemyUnit)) {
					// 基地の前に敵が来ると、攻撃してから消える処理 by.kawasaki
					System.out.println("プレイヤー" + /*i + */"が敵の弾" + j + "と衝突した！");
				}
				
				//by.kawasaki 12/22 タワーの近くで敵ユニットのHPを減らす処理 
				/*
				if (castle[j].checkCollision(enemyUnit)) {
					enemyUnit.HP-=20;
				}
				*/
				
				if(enemyUnit.HP <= 0) {
					// 敵のHPが０以下になれば、画面から消す処理 by. kawasaki
					universe.displace(enemyUnit);
					enemyUnitList.remove(j);
					count++;
					System.out.println(count);
					//retCount();
				}
			}
		//}
		//-------------------------------------
			
		//自分の壁が破られたとき、終了する by.kawasaki
		if(myBase.myBaseHP < 0){
			System.out.println("倒されました");
			System.exit(0);
		}
		//敵を１０個以上倒したらゲーム終了
		
		if (count > 30) {
			System.exit(0);
		}
		
		
		// 王様の衝突判定 ← これいる？
		if (player.checkCollision(king)) {
			// プレイヤーと王様がぶつかった場合
			scenario.fire("王様とぶつかる");	// 「王様とぶつかる」というイベントを発生する（シナリオが進む）
		}
	}
	
	// ArrayListによる、敵のユニットの処理関係 by.kawasaki
	
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
	 * 敵ユニットが入ったリスト（enemyBulletListFromEnemy）を敵ユニットのリストに設定する　by.kawasaki
	 * 
	 * @param enemyBulletListFromEnemy
	 */
	public void setEnemyUnit(ArrayList<EnemyUnit> enemyUnitListFromSpawn) {
		for (int i = 0; i < enemyUnitListFromSpawn.size(); i++) {
			universe.place(enemyUnitListFromSpawn.get(i));
			this.enemyUnitList.add(enemyUnitListFromSpawn.get(i));
			if(enemyUnitList.get(i).HP < 0){
				enemyUnitList.remove(i);
			}
		}
	}
	

	// これいる？
	@Override
	public void action(String action, Event event, ScenarioState nextState) {
		// シナリオ進行による世界への作用をここに書く
		if (action.equals("startFight")) {
			changeToSubContainer();
		} else if (action.equals("endFight")) {
			changeToMainContainer();
		}
	}
	
	/*
	public int retCount() {
		if(count > 10) {
			return 1;
		}else{
			return 0;
		}
	}
	*/

	/**
	 * ゲームのメイン
	 * 
	 * @param args
	 */
	/*
	public static void main(String[] args) {
		SimpleRolePlayingGame game = new TemplateRPG2D();
		game.setFramePolicy(5, 33, false);
		game.start();
	}
	*/
	

}
