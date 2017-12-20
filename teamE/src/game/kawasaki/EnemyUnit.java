package game.kawasaki;

import java.util.ArrayList;

import framework.game2D.FlyingActor2D;
import framework.game2D.Sprite;
import framework.game2D.Velocity2D;

public class EnemyUnit extends Sprite {
	public EnemyUnit(String imageFile) {
		super(imageFile, 1.0f);
		setCollisionRadius(0.2);
	}

	// 弾幕の最大数

	public int HP = 10000;
	public int enemyAttack = 10;
	
	public double bulletX, bulletY;
	// 弾幕の最大数
	private final int MAX_DANMAKU = 1;
	// 弾の発射時の敵からの位置
	private final int BULLET_DISTANCE = 1;
		
	

	// ////////////////////////////////////////////////////
	//
	// 弾がウィンドウ内にいるかどうかのメソッド
	//
	// ///////////////////////////////////////////////////

	/**
	 * widthとheightで表されるウィンドウサイズ内に存在するかを判定する
	 * 
	 * @param width
	 *            --- ウィンドウの幅
	 * @param height
	 *            --- ウィンドウの高さ
	 * @return
	 */
	public boolean isInScreen(double width, double height) {
		if (this.getPosition().getX() < width / 1.0
				&& this.getPosition().getX() > -1.0 * width / 1.0) {
			if (this.getPosition().getY() < height / 1.0
					&& this.getPosition().getY() > -1.0 * height / 1.0) {
				return true;
			}
		}
		return false;
	}
	
	

}
