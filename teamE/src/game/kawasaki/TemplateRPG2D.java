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
	
	// ���x�ɂ���ĕ��̂������Ă��鎞�Ƀ{�^���������邩�ǂ����𔻒肷��t���O
	private boolean disableControl = false;

	@Override
	public void init(Universe universe) {
		map = new MapStage();
		universe.place(map);
		camera.addTarget(map);

		// �v���C���[�̔z�u
		player = new Player("data\\RPG\\player.png");
		player.setPosition(14.0, 14.0);
		player.setCollisionRadius(0.5);
		universe.place(player);
		
		// �v���C���[2�̔z�u
		player2 = new Player("data\\RPG\\block.jpg");
		player2.setPosition(14.0, 14.0);
		player2.setCollisionRadius(0.5);
		universe.place(player2);
		
		
		
		// �����̊�n�̔z�u
		myBase = new MyBase("data\\images\\kiti.gif");
		myBase.setPosition(30.0, 16.0);
		myBase.setCollisionRadius(0.5);
		universe.place(myBase);
		
		//�G�̔����ꏊ
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
		
		//�G�̃��j�b�g����
		//enemyUnitList = new ArrayList<enemyUnits>();
		//enemyUnitList = Sprite("data\\RPG\\monster.png");
		
		
		// map����ʂ̒�����
		setMapCenter(0.0, 0.0);
		setViewRange(32, 32);
		
		// �v���C���[����ʂ̒�����
		setCenter(player2);
		
		
		// �V�i���I�̐ݒ�
		setScenario("data\\TemplateRPG\\Scenario\\scenario2.xml");
		
	}
	
	@Override
	public void subInit(Universe universe) {
		enemy = new Sprite("data\\RPG\\monster.png", 10.0f);
		enemy.setPosition(15.0, 15.0);
		universe.place(enemy);
		
		// �G����ʂ̒�����
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
	
	// �퓬�p��ʂ̍쐬
	public BaseScenarioGameContainer createSubRWTContainer() {
		subContainer = new FightContainer();
		return subContainer;
	}
	
	@Override
	public void progress(RWTVirtualController virtualController, long interval) {
		// ���H�Q�[���X�e�[�W���\������I�u�W�F�N�g�̈ʒu�ƃv���C���[�̈ʒu�����Ƃɑ��x��0�ɂ��邩�ǂ����𒲂ׂ�B
		boolean resetVelocity = map.checkGridPoint(player);

		// �덷�ɂ��ʒu�C�����s�����߁A�v���C���[��x������y������0.0�̎��A�ʒu�̒l��؂�グ��
		if (player.getVelocity().getX() == 0.0
				&& player.getVelocity().getY() == 0.0) {
			player.setPosition(new BigDecimal(player
					.getPosition().getX())
			.setScale(0, BigDecimal.ROUND_HALF_UP).doubleValue(),
			new BigDecimal(player.getPosition().getY())
			.setScale(0, BigDecimal.ROUND_HALF_UP)
			.doubleValue());
		}

		// ���x��0.0�ɂ���t���O�������Ă���΁A���x��0�ɂ���
		if (resetVelocity) {
			player.setVelocity(0.0, 0.0);
			disableControl = false;
		}
		// �L�������ړ����Ă��Ȃ���΁A�L�[����̏������s����B
		if(!disableControl){
			// �L�[����̏���
			// ��
			if (virtualController.isKeyDown(0, RWTVirtualController.LEFT)) {
				player.setVelocity(-4.0, 0.0);
				disableControl = true;
			}
			// �E
			else if (virtualController.isKeyDown(0, RWTVirtualController.RIGHT)) {
				player.setVelocity(4.0, 0.0);
				disableControl = true;
	
			}
			// ��
			else if (virtualController.isKeyDown(0, RWTVirtualController.UP)) {
				player.setVelocity(0.0, 4.0);
				disableControl = true;
			}
			// ��
			else if (virtualController.isKeyDown(0, RWTVirtualController.DOWN)) {
				player.setVelocity(0.0, -4.0);
				disableControl = true;
			}
		}
		player.motion(interval, map);
		
		//---------------------�ǉ�---------
		
		// �G�̃A�N�V��������
		// �e���̔���
		 Random rnd = new Random();
		if (System.currentTimeMillis() - lastEnemyShootTime > rnd.nextInt(100000)) {
			enemyUnitFromSpawn = enemySpawn.goEnemyUnits();
			this.setEnemyUnit(enemyUnitFromSpawn);
			lastEnemyShootTime = System.currentTimeMillis();
		}

		// /////////////////////////////////////////////////////////
		//
		// �e�o�ꕨ�𓮂�������
		//
		// ////////////////////////////////////////////////////////

		// �E�B���h�E���ɏo�悤�Ƃ������A���@�̈ʒu��[�ɌŒ肷��
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
			
		
			// �G�̒e�𓮂����B�����ɃE�B���h�E�O�ɏo�Ă��܂������ǂ����𔻒肵�A�o�Ă��܂�����E�C���h�E����e�������B
			for (int i = 0; i < enemyUnitList.size(); i++) {
				EnemyUnit enemyUnit = enemyUnitList.get(i);
				enemyUnit.motion(interval);		// �G�̒e�̈ړ�
				if (enemyUnit.isInScreen((int)viewRangeWidth, (int)viewRangeHeight) == false) {
					// �G�̒e������
					universe.displace(enemyUnit);
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
			//myBase.myBaseHP=100;
			
			for (int i = 0; i < enemyUnitList.size(); i++) {
				
				EnemyUnit enemyUnit = enemyUnitList.get(i);
				lastEnemyMeetTime = System.currentTimeMillis();
				if (myBase.checkCollision(enemyUnit)) {
					//���̊Ԋu�ōU��
					//if (System.currentTimeMillis() - lastEnemyMeetTime > 1000){
						System.out.println(myBase.myBaseHP);
						//myBase.myBaseHP=(myBase.myBaseHP-enemySpawn.enemyAttack);
						enemyUnit.enemyHP=enemyUnit.enemyHP-1;
						System.out.println("�G( " + i + " )����U�����󂯂��I");
					//}
				}
			}
			
			//�G��|�����Ƃ��ɁA��ʂ������
			for (int i = 0; i < enemyUnitList.size(); i++) {
				EnemyUnit enemyUnit = enemyUnitList.get(i);
				enemyUnit.motion(interval);		// �G�̒e�̈ړ�
				if (enemyUnit.enemyHP < 0) {
					// �G�̒e������
					universe.displace(enemyUnit);
					enemyUnitList.remove(i);
				}
			}
			
			//�����̕ǂ��j��ꂽ�Ƃ�
			if(myBase.myBaseHP < 0){
				System.out.println("�|����܂���");
				System.exit(0);
			}
			
		
		
		
		// �Փ˔���
		if (player.checkCollision(king)) {
			// �v���C���[�Ɖ��l���Ԃ������ꍇ
			scenario.fire("���l�ƂԂ���");	// �u���l�ƂԂ���v�Ƃ����C�x���g�𔭐�����i�V�i���I���i�ށj
		}
	}
	
	//--------------------------Shooting progress�̂��Ƃ̕�������Ђ��ς��Ă���
	
	/**
	 * �e�������������X�g�imyShipBulletFromMyShip�j���v���C���[�̒e�̃��X�g�ɐݒ肷��
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
	 * �e�������������X�g�ienemyBulletListFromEnemy�j��G�̒e�̃��X�g�ɐݒ肷��
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
		// �V�i���I�i�s�ɂ�鐢�E�ւ̍�p�������ɏ���
		if (action.equals("startFight")) {
			changeToSubContainer();
		} else if (action.equals("endFight")) {
			changeToMainContainer();
		}
	}
	

	/**
	 * �Q�[���̃��C��
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		SimpleRolePlayingGame game = new TemplateRPG2D();
		game.setFramePolicy(5, 33, false);
		game.start();
	}

}
