package azuma;

import framework.game2D.Sprite;

public class Unit extends Sprite{
	
	int unitHP = 20;
	
	public Unit(String imageFile) {
		super(imageFile);
	}
	
	public boolean shootDown() {
		if (unitHP <= 0) {
			return true;
		} else {
			return false;
		}
	}

	public void addEnemyHP(int value) {
		unitHP += value;
	}

	public int getEnemyHP() {
		return unitHP;
	}

	public void setEnemyHP(int enemyHP) {
		this.unitHP = enemyHP;
	}

}
