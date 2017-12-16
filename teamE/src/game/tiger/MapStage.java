package game.tiger;

import java.math.BigDecimal;

import framework.game2D.Map2D;

/**
 * 迷路ゲームのステージのクラス
 * @author T.Kuno
 *
 */
public class MapStage extends Map2D {
	// コンストラクタ
	public MapStage() {
		super(new String[]{
				"data\\RPG\\grass1.jpg",
				"data\\RPG\\grass2.jpg",
				"data\\RPG\\road1.jpg",
				"data\\RPG\\tree.jpg",		// ここから下は障害物
				"data\\RPG\\block.jpg",
				"data\\RPG\\sea1.jpg",
				"data\\RPG\\road1.jpg",
				"data\\towerdefence\\red-background.jpg"},
		3);
	}

	// 抽象メソッドの実装
	//
	@Override
	public  int[][] createMap() {
		int[][] map = {
				{ 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3,3,3},
				{ 3, 3, 3, 3, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 3,3,3},
				{ 3, 3, 3, 3, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 3,3,3},
				{ 3, 3, 3, 3, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 3,3,3},
				{ 3, 3, 3, 3, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 3,3,3},
				{ 3, 3, 3, 3, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 3,3,3},
				{ 3, 3, 3, 3, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 3,3,3},
				{ 3, 3, 3, 3, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 3,3,3},
				{ 6, 6, 6, 6, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 6,7,2},
				{ 3, 3, 3, 3, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 3,3,3},
				{ 3, 3, 3, 3, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 3,3,3},
				{ 3, 3, 3, 3, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 3,3,3},
				{ 3, 3, 3, 3, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 3,3,3},
				{ 3, 3, 3, 3, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 3,3,3},
				{ 3, 3, 3, 3, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 3,3,3},
				{ 3, 3, 3, 3, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 3,3,3},
				{ 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3,3,3}
		};


		return map;
	}



	public boolean checkGridPoint(Player mazeSpritePlayer) {
		// 丸め誤差処理用変数の生成
		double mazeSpritePositionX = new BigDecimal(mazeSpritePlayer
				.getPosition().getX()).setScale(1, BigDecimal.ROUND_DOWN)
				.doubleValue();
		double mazeSpritePositionY = new BigDecimal(mazeSpritePlayer
				.getPosition().getY()).setScale(1, BigDecimal.ROUND_DOWN)
				.doubleValue();

		// ステージの構成オブジェクトの位置とプレイヤーの位置が同じかどうかっ判定する
		for (int i = 0; i < this.getStageObjectList().size(); i++) {
			if (
					mazeSpritePositionX == this.getStageObjectList().get(i).getPosition().getX()
					&& mazeSpritePositionY == this.getStageObjectList().get(i).getPosition().getY()
				){
				return true;
			}

		}
		return false;
	}

}
