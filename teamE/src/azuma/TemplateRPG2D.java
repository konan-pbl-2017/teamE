package azuma;

import java.awt.Color;
import java.math.BigDecimal;

import javax.vecmath.Vector2d;

import framework.RWT.RWTContainer;
import framework.RWT.RWTFrame3D;
import framework.RWT.RWTVirtualController;
import framework.game2D.Sprite;
import framework.game2D.Velocity2D;
import framework.gameMain.BaseScenarioGameContainer;
import framework.gameMain.SimpleRolePlayingGame;
import framework.model3D.Universe;
import framework.scenario.Event;
import framework.scenario.ScenarioState;


public class TemplateRPG2D extends SimpleRolePlayingGame {

	private MapStage map;
	private Player player;
	private Unit unit[] =new Unit[35];
	private UnitBullet unitbullet[][] = new UnitBullet[35][20];
	private Sprite king;
	private Sprite enemy;
	int unitnum=0;
	boolean unitable=true;//城が置ける状態か否か:by.tiger
	// 速度によって物体が動いている時にボタンを押せるかどうかを判定するフラグ
	private boolean disableControl = false;
	private long BulletTime;
	
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
		// ユニットの配置
		for(int i=0;i<30;i++){
			unit[i] = new Unit("data\\RPG\\block.jpg");
			unit[i].setPosition(-1,-1);
			unit[i].setCollisionRadius(0.5);
			universe.place(unit[i]);
		}
		
		
		

		// mapを画面の中央に
		setMapCenter(14.0, 14.0);
		setViewRange(30, 30);

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
		//初期位置に戻す
		if(virtualController.isKeyDown(0, RWTVirtualController.BUTTON_A)){
			player.setPosition(14.0, 14.0);
		}
		// キャラが移動していなければ、キー操作の処理を行える。
		if(!disableControl){
			// キー操作の処理
		/*	if (virtualController.isKeyDown(0, RWTVirtualController.LEFT)&&virtualController.isKeyDown(0, RWTVirtualController.DOWN)) {
				player.setVelocity(-10.0, -10.0);
				disableControl = true;//斜め移動

			}*/			
			
			// 左
			if (virtualController.isKeyDown(0, RWTVirtualController.LEFT)) {
				player.setVelocity(-10.0, 0.0);
				disableControl = true;
				unitable=true;//城を置ける状態にする:by.tiger
			}
			// 右
			else if (virtualController.isKeyDown(0, RWTVirtualController.RIGHT)) {
				player.setVelocity(10.0, 0.0);
				disableControl = true;
				unitable=true;//城を置ける状態にする:by.tiger
			}
			// 上
			else if (virtualController.isKeyDown(0, RWTVirtualController.UP)) {
				player.setVelocity(0.0, 10.0);
				disableControl = true;
				unitable=true;//城を置ける状態にする:by.tiger
			}
			// 下
			else if (virtualController.isKeyDown(0, RWTVirtualController.DOWN)) {
				player.setVelocity(0.0, -10.0);
				disableControl = true;
				unitable=true;//城を置ける状態にする:by.tiger
			}
		}


		if (virtualController.isKeyDown(0, RWTVirtualController.BUTTON_B)&&unitable==true) {
			unit[unitnum].setPosition(player.getPosition());//城の配置
			unitnum++;
			if(unitnum>=30)unitnum=0;
			unitable=false;

		}
		
		//}
			
		//弾の発射
	
			for(int i=0; i<unitnum; i++){//ユニットの数だけ球を生成by東
				for(int j=0; j<20; j++){//球の数は20（HPが20なので）by東
					if(unit[i].unitHP>0 && unitbullet[i][j]==null){//ユニットのHPが切れてないと球を打つ
						if (System.currentTimeMillis() - BulletTime > 1000) {//一秒ごとに球を打つ
							unitbullet[i][j] = new UnitBullet("data\\images\\myBullet.gif");
							unitbullet[i][j].setPosition(unit[i].getPosition());
							unitbullet[i][j].setVelocity(new Velocity2D(0.0, -10.0));
							unit[i].addunitHP(-5);
							universe.place(unitbullet[i][j]);
							BulletTime = System.currentTimeMillis();
							}
						}	
					if (unitbullet[i][j] != null) unitbullet[i][j].motion(interval);
				}
				if(unit[i].unitBreak()){
					System.out.println("ユニット球切れしました");
					universe.displace(unit[i]);
				}
			}

		

		
		
		player.motion(interval, map);
		// 衝突判定:城の撃つ弾と敵の当たり判定に使う
		if (player.checkCollision(king)) {
			// プレイヤーと王様がぶつかった場合
			scenario.fire("王様とぶつかる");	// 「王様とぶつかる」というイベントを発生する（シナリオが進む）
		}
	}

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
