package game.kawasaki;

import framework.game2D.FlyingActor2D;
import framework.game2D.Sprite;

public class EnemyUnit extends Sprite {
	public EnemyUnit(String imageFile) {
		super(imageFile, 1.0f);
		setCollisionRadius(0.2);
	}

	public int enemyHP = 100;
	public int enemyAttack = 10;

	// ////////////////////////////////////////////////////
	//
	// �e���E�B���h�E���ɂ��邩�ǂ����̃��\�b�h
	//
	// ///////////////////////////////////////////////////

	/**
	 * width��height�ŕ\�����E�B���h�E�T�C�Y���ɑ��݂��邩�𔻒肷��
	 * 
	 * @param width
	 *            --- �E�B���h�E�̕�
	 * @param height
	 *            --- �E�B���h�E�̍���
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
