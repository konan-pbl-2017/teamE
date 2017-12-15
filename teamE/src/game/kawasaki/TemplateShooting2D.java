package game.kawasaki;

import java.util.ArrayList;

import framework.RWT.RWTFrame3D;
import framework.RWT.RWTVirtualController;
import framework.game2D.Ground2D;
import framework.game2D.Velocity2D;
import framework.gameMain.SimpleShootingGame;
import framework.model3D.Universe;

public class TemplateShooting2D extends SimpleShootingGame {
	private MyBase myBaseSprite;//自分のユニット画像
	private MyShipBullet myShipBullet;//自分のユニットの弾幕画像 タワーで使えるかも？
	
	//ArrayListで管理
	private ArrayList<MyShipBullet> myShipBulletList = new ArrayList<MyShipBullet>();
	private ArrayList<MyShipBullet> myShipBulletFromMyShip = new ArrayList<MyShipBullet>();

	private EnemySpawn enemySprite;//敵のユニットが出てくるところの画像
	private ArrayList<EnemyUnit> enemyUnitList = new ArrayList<EnemyUnit>();
	private ArrayList<EnemyUnit> enemyUnitFromSpawn = new ArrayList<EnemyUnit>();

	private Ground2D stage;
	//private MapStage stage;
	
	private long lastMyShipBulletShootTime = 0;
	private long lastMyShipBulletShootDanamakuTime = 0;
	private long lastEnemyShootTime = 0;

	// あとで設計変更
	// Enemyクラスでこの値を使いたいため。
	public static final int RANGE = 30;

	@Override
	public void init(Universe universe) {

		// /////////////////////////////////////////////////////////
		//
		// 各登場物の初期化
		//
		// ////////////////////////////////////////////////////////
		myBaseSprite = new MyBase("data\\images\\MyShip.gif");
		myBaseSprite.setPosition(770.0, 0.0);
		universe.place(myBaseSprite);

		enemySprite = new EnemySpawn("data\\images\\Enemy.gif");
		enemySprite.setPosition(0.0, 0.0);
		enemySprite.setVelocity(5.0, 5.0);
		universe.place(enemySprite);

		stage = new Ground2D(null, "data\\images\\m101.jpg", windowSizeWidth,
				windowSizeHeight);
		universe.place(stage);

		// 表示範囲を決める（左上を原点としてその原点から幅、高さを計算する）
		setViewRange(RANGE, RANGE);//自分の飛行機の描写の大きさを指定　30＊30
	}

	@Override
	//マップの範囲を指定するためのフレームクラス？
	public RWTFrame3D createFrame3D() {
		// TODO Auto-generated method stub
		RWTFrame3D f = new RWTFrame3D();
		f.setSize(800, 800);
		// f.setExtendedState(Frame.MAXIMIZED_BOTH);
		f.setTitle("Template for Shooting 2DGame");
		return f;
	}

	@Override
	public void progress(RWTVirtualController virtualController, long interval) {

		// /////////////////////////////////////////////////////////
		//
		// 各登場物のアクション
		//
		// ////////////////////////////////////////////////////////

		// キー操作による自機のアクション処理
		// 左
		/*
		if (virtualController.isKeyDown(0, RWTVirtualController.LEFT)) {
			myBaseSprite.moveLeft(5.0);
		}
		// 右
		if (virtualController.isKeyDown(0, RWTVirtualController.RIGHT)) {
			myBaseSprite.moveRight(5.0);
		}
		// 上
		if (virtualController.isKeyDown(0, RWTVirtualController.UP)) {
			myBaseSprite.moveUp(5.0);
		}
		// 下
		if (virtualController.isKeyDown(0, RWTVirtualController.DOWN)) {
			myBaseSprite.moveDown(5.0);
		}
		*/

		// 自機の弾の発射
		/*
		if (virtualController.isKeyDown(0, RWTVirtualController.BUTTON_A)) {
			if (System.currentTimeMillis() - lastMyShipBulletShootTime > 1000) {
				myShipBullet = new MyShipBullet("data\\images\\myBullet.gif");
				myShipBullet.setPosition(myBaseSprite.getPosition());
				myShipBullet.setVelocity(new Velocity2D(0.0, 7.0));
				universe.place(myShipBullet);
				myShipBulletList.add(myShipBullet);
				lastMyShipBulletShootTime = System.currentTimeMillis();
			}
		}
		*/

		// 自機の弾幕の発射
		/*
		if (virtualController.isKeyDown(0, RWTVirtualController.BUTTON_B)) {
			if (System.currentTimeMillis() - lastMyShipBulletShootDanamakuTime > 1000) {
				myShipBulletFromMyShip = myBaseSprite.shootDanmaku();
				this.setMyShipBullet(myShipBulletFromMyShip);
				lastMyShipBulletShootDanamakuTime = System.currentTimeMillis();
			}
		}
		*/

		// 敵ユニットのアクション処理
		// 敵ユニットの前進
		if (System.currentTimeMillis() - lastEnemyShootTime > 1000) {
			enemyUnitFromSpawn = enemySprite.shootDanmaku();
			this.setEnemyBullet(enemyUnitFromSpawn);
			lastEnemyShootTime = System.currentTimeMillis();
		}

		// /////////////////////////////////////////////////////////
		//
		// 各登場物を動かす処理
		//
		// ////////////////////////////////////////////////////////

		// ウィンドウ内に出ようとした時、自機の位置を端に固定する
		if (!(myBaseSprite.isInScreen(viewRangeWidth, viewRangeHeight))) {
			if (myBaseSprite.getPosition().getX() >= viewRangeWidth / 2) {
				myBaseSprite.setPosition(viewRangeWidth / 2, myBaseSprite.getPosition().getY());
			}
			if (myBaseSprite.getPosition().getX() <= -1.0 * viewRangeWidth / 2) {
				myBaseSprite.setPosition(-1.0 * viewRangeWidth / 2, myBaseSprite.getPosition().getY());
			}
			if (myBaseSprite.getPosition().getY() >= viewRangeHeight / 2) {
				myBaseSprite.setPosition(myBaseSprite.getPosition().getX(), viewRangeHeight / 2);
			}
			if (myBaseSprite.getPosition().getY() <= -1.0 * viewRangeHeight / 2) {
				myBaseSprite.setPosition(myBaseSprite.getPosition().getX(), -1.0 * viewRangeHeight / 2);
			}
			myBaseSprite.motion(interval);
		}

		// プレイヤーの弾を動かす
		for (int i = 0; i < myShipBulletList.size(); i++) {
			MyShipBullet myShipBullet = myShipBulletList.get(i);
			myShipBullet.motion(interval);		// プレイヤーの弾の移動
			//自分の弾が、画面外に出たら
			if (myShipBullet.isInScreen(viewRangeWidth, viewRangeHeight) == false) {
				// プレイヤーの弾を消す
				universe.displace(myShipBullet);
				myShipBulletList.remove(i);// .add()の逆、リストから排除
			}
		}

		// 敵（スプライト）を動かす
		//enemySprite.motion(interval);

		// 敵の弾を動かす。同時にウィンドウ外に出てしまったかどうかを判定し、出てしまったらウインドウから弾を消す。
		for (int i = 0; i < enemyUnitList.size(); i++) {
			EnemyUnit enemyBullet = enemyUnitList.get(i);
			enemyBullet.motion(interval);		// 敵の弾の移動
			if (enemyBullet.isInScreen(viewRangeWidth, viewRangeHeight) == false) {
				// 敵の弾を消す
				universe.displace(enemyBullet);
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
		for (int i = 0; i < enemyUnitList.size(); i++) {
			EnemyUnit enemyBullet = enemyUnitList.get(i);
			if (myBaseSprite.checkCollision(enemyBullet)) {
				if(myBaseSprite.myBaseHP <= 0){
					System.out.println("壁が破られました！");
					break ;
				}else{
					System.out.println("敵の弾と衝突した！");
					myBaseSprite.myBaseHP-=10;
				}
			}
		}
		
		

		// 衝突判定（プレイヤーの弾と敵の弾）
		for (int i = 0; i < myShipBulletList.size(); i++) {
			MyShipBullet myShipBullet = myShipBulletList.get(i);
			for (int j = 0; j < enemyUnitList.size(); j++) {
				EnemyUnit enemyBullet = enemyUnitList.get(j);
				if (myShipBullet.checkCollision(enemyBullet)) {
					// 敵の弾を消す
					System.out.println("プレイヤーの弾" + i + "が敵の弾" + j + "と衝突した！");
					universe.displace(enemyBullet);
					enemyUnitList.remove(j);
				}
			}
		}

		// 衝突判定（プレイヤーの弾と敵）
		for (int i = 0; i < myShipBulletList.size(); i++) {
			MyShipBullet myShipBullet = myShipBulletList.get(i);
			if (myShipBullet.checkCollision(enemySprite)) {
				System.out.println("プレイヤーの弾が敵に衝突した！");
				enemySprite.addEnemyHP(-1);
				System.out.println("敵のHP" + enemySprite.getEnemyHP());

				if (enemySprite.shootDown()) {
					System.out.println("敵を倒した！");
				}

			}
		}

		// 衝突判定（プレイヤーと敵）
		if (myBaseSprite.checkCollision(enemySprite)) {
			System.out.println("敵と衝突した！");
		}

	}

	/**
	 * 弾幕が入ったリスト（myShipBulletFromMyShip）をプレイヤーの弾のリストに設定する
	 * 
	 * @param myShipBulletFromMyShip
	 */
	public void setMyShipBullet(ArrayList<MyShipBullet> myShipBulletFromMyShip) {
		for (int i = 0; i < myShipBulletFromMyShip.size(); i++) {
			universe.place(myShipBulletFromMyShip.get(i));
			this.myShipBulletList.add(myShipBulletFromMyShip.get(i));
		}
	}

	/**
	 * 弾幕が入ったリスト（enemyBulletListFromEnemy）を敵の弾のリストに設定する
	 * 
	 * @param enemyBulletListFromEnemy
	 */
	public void setEnemyBullet(ArrayList<EnemyUnit> enemyBulletListFromEnemy) {
		for (int i = 0; i < enemyBulletListFromEnemy.size(); i++) {
			universe.place(enemyBulletListFromEnemy.get(i));
			this.enemyUnitList.add(enemyBulletListFromEnemy.get(i));
		}
	}

	/**
	 * ゲームのメイン
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		TemplateShooting2D game = new TemplateShooting2D();
		game.setFramePolicy(5, 33, false);
		game.start();
	}

}
