package azuma;

import framework.game2D.Sprite;

public class Unit extends Sprite{
	
	int unitHP=20;
	
	public Unit(String imageFile) {
		super(imageFile);
	}
	
	public boolean unitBreak() {
		if (unitHP <= 0) {
			return true;
		} else {
			return false;
		}
	}

	public void addunitHP(int value) {
		unitHP += value;
	}

	public int getunitHP() {
		return unitHP;
	}

	public void setEnemyHP(int enemyHP) {
		this.unitHP = enemyHP;
	}

}
