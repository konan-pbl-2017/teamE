package game.kawasaki;

import java.util.ArrayList;

import framework.RWT.RWTFrame3D;
import framework.RWT.RWTVirtualController;
import framework.game2D.Ground2D;
import framework.game2D.Velocity2D;
import framework.gameMain.SimpleShootingGame;
import framework.model3D.Universe;

public class TemplateShooting2D extends SimpleShootingGame {
	private MyBase myBaseSprite;//�����̃��j�b�g�摜
	private MyShipBullet myShipBullet;//�����̃��j�b�g�̒e���摜 �^���[�Ŏg���邩���H
	
	//ArrayList�ŊǗ�
	private ArrayList<MyShipBullet> myShipBulletList = new ArrayList<MyShipBullet>();
	private ArrayList<MyShipBullet> myShipBulletFromMyShip = new ArrayList<MyShipBullet>();

	private EnemySpawn enemySprite;//�G�̃��j�b�g���o�Ă���Ƃ���̉摜
	private ArrayList<EnemyUnit> enemyUnitList = new ArrayList<EnemyUnit>();
	private ArrayList<EnemyUnit> enemyUnitFromSpawn = new ArrayList<EnemyUnit>();

	private Ground2D stage;
	//private MapStage stage;
	
	private long lastMyShipBulletShootTime = 0;
	private long lastMyShipBulletShootDanamakuTime = 0;
	private long lastEnemyShootTime = 0;

	// ���ƂŐ݌v�ύX
	// Enemy�N���X�ł��̒l���g���������߁B
	public static final int RANGE = 30;

	@Override
	public void init(Universe universe) {

		// /////////////////////////////////////////////////////////
		//
		// �e�o�ꕨ�̏�����
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

		// �\���͈͂����߂�i��������_�Ƃ��Ă��̌��_���畝�A�������v�Z����j
		setViewRange(RANGE, RANGE);//�����̔�s�@�̕`�ʂ̑傫�����w��@30��30
	}

	@Override
	//�}�b�v�͈̔͂��w�肷�邽�߂̃t���[���N���X�H
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
		// �e�o�ꕨ�̃A�N�V����
		//
		// ////////////////////////////////////////////////////////

		// �L�[����ɂ�鎩�@�̃A�N�V��������
		// ��
		/*
		if (virtualController.isKeyDown(0, RWTVirtualController.LEFT)) {
			myBaseSprite.moveLeft(5.0);
		}
		// �E
		if (virtualController.isKeyDown(0, RWTVirtualController.RIGHT)) {
			myBaseSprite.moveRight(5.0);
		}
		// ��
		if (virtualController.isKeyDown(0, RWTVirtualController.UP)) {
			myBaseSprite.moveUp(5.0);
		}
		// ��
		if (virtualController.isKeyDown(0, RWTVirtualController.DOWN)) {
			myBaseSprite.moveDown(5.0);
		}
		*/

		// ���@�̒e�̔���
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

		// ���@�̒e���̔���
		/*
		if (virtualController.isKeyDown(0, RWTVirtualController.BUTTON_B)) {
			if (System.currentTimeMillis() - lastMyShipBulletShootDanamakuTime > 1000) {
				myShipBulletFromMyShip = myBaseSprite.shootDanmaku();
				this.setMyShipBullet(myShipBulletFromMyShip);
				lastMyShipBulletShootDanamakuTime = System.currentTimeMillis();
			}
		}
		*/

		// �G���j�b�g�̃A�N�V��������
		// �G���j�b�g�̑O�i
		if (System.currentTimeMillis() - lastEnemyShootTime > 1000) {
			enemyUnitFromSpawn = enemySprite.shootDanmaku();
			this.setEnemyBullet(enemyUnitFromSpawn);
			lastEnemyShootTime = System.currentTimeMillis();
		}

		// /////////////////////////////////////////////////////////
		//
		// �e�o�ꕨ�𓮂�������
		//
		// ////////////////////////////////////////////////////////

		// �E�B���h�E���ɏo�悤�Ƃ������A���@�̈ʒu��[�ɌŒ肷��
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

		// �v���C���[�̒e�𓮂���
		for (int i = 0; i < myShipBulletList.size(); i++) {
			MyShipBullet myShipBullet = myShipBulletList.get(i);
			myShipBullet.motion(interval);		// �v���C���[�̒e�̈ړ�
			//�����̒e���A��ʊO�ɏo����
			if (myShipBullet.isInScreen(viewRangeWidth, viewRangeHeight) == false) {
				// �v���C���[�̒e������
				universe.displace(myShipBullet);
				myShipBulletList.remove(i);// .add()�̋t�A���X�g����r��
			}
		}

		// �G�i�X�v���C�g�j�𓮂���
		//enemySprite.motion(interval);

		// �G�̒e�𓮂����B�����ɃE�B���h�E�O�ɏo�Ă��܂������ǂ����𔻒肵�A�o�Ă��܂�����E�C���h�E����e�������B
		for (int i = 0; i < enemyUnitList.size(); i++) {
			EnemyUnit enemyBullet = enemyUnitList.get(i);
			enemyBullet.motion(interval);		// �G�̒e�̈ړ�
			if (enemyBullet.isInScreen(viewRangeWidth, viewRangeHeight) == false) {
				// �G�̒e������
				universe.displace(enemyBullet);
				enemyUnitList.remove(i);
			}
		}

		// /////////////////////////////////////////////////////////
		//
		// �e�o�ꕨ�𓮂�������̏���
		//
		// ////////////////////////////////////////////////////////

		// �v���C���[�ƁZ�Z�̏Փ˔���
		// �Փ˔���i�v���C���[�ƓG�̒e�j
		for (int i = 0; i < enemyUnitList.size(); i++) {
			EnemyUnit enemyBullet = enemyUnitList.get(i);
			if (myBaseSprite.checkCollision(enemyBullet)) {
				if(myBaseSprite.myBaseHP <= 0){
					System.out.println("�ǂ��j���܂����I");
					break ;
				}else{
					System.out.println("�G�̒e�ƏՓ˂����I");
					myBaseSprite.myBaseHP-=10;
				}
			}
		}
		
		

		// �Փ˔���i�v���C���[�̒e�ƓG�̒e�j
		for (int i = 0; i < myShipBulletList.size(); i++) {
			MyShipBullet myShipBullet = myShipBulletList.get(i);
			for (int j = 0; j < enemyUnitList.size(); j++) {
				EnemyUnit enemyBullet = enemyUnitList.get(j);
				if (myShipBullet.checkCollision(enemyBullet)) {
					// �G�̒e������
					System.out.println("�v���C���[�̒e" + i + "���G�̒e" + j + "�ƏՓ˂����I");
					universe.displace(enemyBullet);
					enemyUnitList.remove(j);
				}
			}
		}

		// �Փ˔���i�v���C���[�̒e�ƓG�j
		for (int i = 0; i < myShipBulletList.size(); i++) {
			MyShipBullet myShipBullet = myShipBulletList.get(i);
			if (myShipBullet.checkCollision(enemySprite)) {
				System.out.println("�v���C���[�̒e���G�ɏՓ˂����I");
				enemySprite.addEnemyHP(-1);
				System.out.println("�G��HP" + enemySprite.getEnemyHP());

				if (enemySprite.shootDown()) {
					System.out.println("�G��|�����I");
				}

			}
		}

		// �Փ˔���i�v���C���[�ƓG�j
		if (myBaseSprite.checkCollision(enemySprite)) {
			System.out.println("�G�ƏՓ˂����I");
		}

	}

	/**
	 * �e�������������X�g�imyShipBulletFromMyShip�j���v���C���[�̒e�̃��X�g�ɐݒ肷��
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
	 * �e�������������X�g�ienemyBulletListFromEnemy�j��G�̒e�̃��X�g�ɐݒ肷��
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
	 * �Q�[���̃��C��
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		TemplateShooting2D game = new TemplateShooting2D();
		game.setFramePolicy(5, 33, false);
		game.start();
	}

}
