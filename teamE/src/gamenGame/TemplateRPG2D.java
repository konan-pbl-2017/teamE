// auther kawasaki
// ���̏������v���O�����ɁATigar�̃v���O������ǉ����A�G�ƃ^���[�̓����`�ʂ��e�X�g�@12/21 11:23�@���@����
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
	public int count; //by.kawasaki �G��|���������J�E���g
	
	/*
	MainFrame mf;//��ʑJ�ڗpby.kawasaki 12/22
	String str;
	*/
	
	int unitval = 0; //	�u���郆�j�b�g�̑I��:by.tiger	##������##


	int castlenum = 0;//addTigar
	boolean castleable = true;// �邪�u�����Ԃ��ۂ�:by.tiger
	// ���x�ɂ���ĕ��̂������Ă��鎞�Ƀ{�^���������邩�ǂ����𔻒肷��t���O
	
	
	private MyBase myBase;
	//private ArrayList<E> enemyUnitList = new ArrayList<EnemyUnit>();
	//private ArrayList<EnemyUnit> enemyUnitFromSpawn = new ArrayList<EnemyUnit>();
	
	//by.kawasaki �G�̃X�|�[���E���j�b�g���i�[����ArrayList
	private EnemySpawn enemySpawn;
	private ArrayList<EnemyUnit> enemyUnitList = new ArrayList<EnemyUnit>();
	private ArrayList<EnemyUnit> enemyUnitFromSpawn = new ArrayList<EnemyUnit>();
	
	//by.kawasaki if���Ŗ��b���[�v�����邽�߂̃^�C���ϐ�
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
		
		//by.kawasaki ����͉�ʂ̒����ɂ����邽�߂̈ꎞ�I�ȃC���X�^���X �@
		//�v���C���[2�̔z�u
		
		player2 = new Player("data\\RPG\\block.jpg");
		player2.setPosition(14.0, 14.0);
		player2.setCollisionRadius(0.5);
		universe.place(player2);
		
		
		//by.kawasaki �����̎���n
		// �����̊�n�̔z�u
		myBase = new MyBase("data\\towerdefence\\yousai.jpg");
		myBase.setPosition(32.0, 14.0);
		myBase.setCollisionRadius(0.5);
		universe.place(myBase);
		
		//by. kawasaki �G�̔����ꏊ
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
		
		//�G�̃��j�b�g����
		//enemyUnitList = new ArrayList<enemyUnits>();
		//enemyUnitList = Sprite("data\\RPG\\monster.png");
		
		
		// map����ʂ̒����� �@���@�Ȃ����l�i���j�̉Ƃ��Ǝ��s����΁@���ɂ��܂���I��
		setMapCenter(14.0, 14.0);
		setViewRange(30, 30);
		
		// �v���C���[����ʂ̒�����
		setCenter(player2);
		
		
		// �V�i���I�̐ݒ�
		setScenario("data\\TemplateRPG\\Scenario\\scenario2.xml");
		
	}
	
	// ���ꂢ��H�@by.kawasaki
	@Override
	public void subInit(Universe universe) {
		enemy = new Sprite("data\\RPG\\monster.png", 10.0f);
		enemy.setPosition(15.0, 15.0);
		universe.place(enemy);
		
		// �G����ʂ̒�����
		setSubCenter(enemy);
	}
	
	//�`�ʂ����ʂ̑傫���H
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
			/* ���̕����A�����Tigar�̂�ƌ����B���ɂ���܂�
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
			*/
			
			// by.tigar
			// ��
			if (virtualController.isKeyDown(0, RWTVirtualController.LEFT)) {
				player.setVelocity(-10.0, 0.0);
				disableControl = true;
				castleable = true;// ���u�����Ԃɂ���:by.tiger
			}
			// �E
			else if (virtualController.isKeyDown(0, RWTVirtualController.RIGHT)) {
				player.setVelocity(10.0, 0.0);
				disableControl = true;
				castleable = true;// ���u�����Ԃɂ���:by.tiger
			}
			// ��
			else if (virtualController.isKeyDown(0, RWTVirtualController.UP)) {
				player.setVelocity(0.0, 10.0);
				disableControl = true;
				castleable = true;// ���u�����Ԃɂ���:by.tiger
			}
			// ��
			else if (virtualController.isKeyDown(0, RWTVirtualController.DOWN)) {
				player.setVelocity(0.0, -10.0);
				disableControl = true;
				castleable = true;// ���u�����Ԃɂ���:by.tiger
			}
		}
		player.motion(interval, map);
		
		if (virtualController.isKeyDown(1, RWTVirtualController.UP))unitval = 1;
		if (virtualController.isKeyDown(1, RWTVirtualController.DOWN))unitval = 2;
		if (virtualController.isKeyDown(1, RWTVirtualController.RIGHT))unitval = 3;
		if (virtualController.isKeyDown(1, RWTVirtualController.LEFT))unitval = 4;
		//���j�b�g�̑I��:by.tiger	##������##

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
			}//30�܂ł͕��ʂɐ���:by.tiger
			i++;
			System.out.println(i);
			if (i>= 30){
			castle[i%30].setPosition(player.getPosition());
			}//30���߂���΍ŏ��ɒu�������j�b�g��������Ă���(+HP������������Ηǂ��ˁB):by.tiger
			castleable = false;

		}

		//---------------------�ǉ�---------
		
		// �G�̃A�N�V���������@by.kawasaki
		// �e���̔��ˁ@�@�����_���̊Ԋu�œG���o�����߂Ƀ����_���֐����g�p
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
			
		
		//�o�邱�Ƃ͐�΂ɖ����̂ł����A�ꉞ�����Ă����܂��� by.kawasaki 
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
			
		//�����̊�n�Ƃ̓����蔻��ɗ��p����.kawasaki
		//myBase.myBaseHP=100;
		/*
		for (int i = 0; i < enemyUnitList.size(); i++) {
				
			EnemyUnit enemyUnit = enemyUnitList.get(i);
			enemyUnit.HP = enemyUnitList.get(i).HP;
			lastEnemyMeetTime = System.currentTimeMillis();
			if (myBase.checkCollision(enemyUnit)) {
				//���̊Ԋu�ōU��
				//if (System.currentTimeMillis() - lastEnemyMeetTime > 100){
					System.out.println(myBase.myBaseHP);
					System.out.println(enemyUnit.HP);
					myBase.myBaseHP=(myBase.myBaseHP-enemySpawn.enemyAttack);
					enemyUnit.HP=enemyUnit.HP-10;//�G���_���[�W�󂯂�
					System.out.println("�G( " + i + " )����U�����󂯂��I");
					enemySpawn.bulletX =0 ;
				//}
			}
			System.out.println("Base HP = " + myBase.myBaseHP);
			System.out.println(i + "�Ԗ�" + "enemy HP = " + enemyUnit.HP);
		}
			
		//�G��|�����Ƃ��ɁA��ʂ������
		for (int i = 0; i < enemyUnitList.size(); i++) {
			EnemyUnit enemyUnit = enemyUnitList.get(i);
			//enemyUnit.motion(interval);		// �G�̒e�̈ړ�
			if (enemyUnit.HP < 0) {
				// �G�̒e������
				//universe.displace(enemyUnit);
				//enemyUnitList.remove(i);
			}
		}
		*/
		
		//-12/19�@by.kawasaki
		// �Փ˔���i�����̊�nor�^���[�@�Ɓ@�G�̃��j�b�g�j
		// �G���j�b�g�������鏈��
		
		//for (int i = 0; i < myShipBulletList.size(); i++) {
			//MyShipBullet myShipBullet = myShipBulletList.get(i);
			for (int j = 0; j < enemyUnitList.size(); j++) {
				EnemyUnit enemyUnit = enemyUnitList.get(j);
				if (myBase.checkCollision(enemyUnit)) {
					// ��n�̑O�ɓG������ƁA�U�����Ă�������鏈�� by.kawasaki
					System.out.println("�����̊�n" + myBase.myBaseHP);
					System.out.println(enemyUnit.HP);
					myBase.myBaseHP=(myBase.myBaseHP-enemySpawn.enemyAttack);
					enemyUnit.HP=enemyUnit.HP-1000;//�G���_���[�W�󂯂�
					System.out.println("�G( " + j + " )����U�����󂯂��I");
					enemySpawn.bulletX =0 ;
					
				}
				if (player.checkCollision(enemyUnit)) {
					// �J�[�\���̋߂��ɓG������ƁA���̓G��HP���O�ɂ���i##�e�X�g�@�\##�j 12/18 by.kawasaki
					enemyUnit.HP-=100;
				}
				if (myBase.checkCollision(enemyUnit)) {
					// ��n�̑O�ɓG������ƁA�U�����Ă�������鏈�� by.kawasaki
					System.out.println("�v���C���[" + /*i + */"���G�̒e" + j + "�ƏՓ˂����I");
				}
				
				//by.kawasaki 12/22 �^���[�̋߂��œG���j�b�g��HP�����炷���� 
				/*
				if (castle[j].checkCollision(enemyUnit)) {
					enemyUnit.HP-=20;
				}
				*/
				
				if(enemyUnit.HP <= 0) {
					// �G��HP���O�ȉ��ɂȂ�΁A��ʂ���������� by. kawasaki
					universe.displace(enemyUnit);
					enemyUnitList.remove(j);
					count++;
					System.out.println(count);
					//retCount();
				}
			}
		//}
		//-------------------------------------
			
		//�����̕ǂ��j��ꂽ�Ƃ��A�I������ by.kawasaki
		if(myBase.myBaseHP < 0){
			System.out.println("�|����܂���");
			System.exit(0);
		}
		//�G���P�O�ȏ�|������Q�[���I��
		
		if (count > 30) {
			System.exit(0);
		}
		
		
		// ���l�̏Փ˔��� �� ���ꂢ��H
		if (player.checkCollision(king)) {
			// �v���C���[�Ɖ��l���Ԃ������ꍇ
			scenario.fire("���l�ƂԂ���");	// �u���l�ƂԂ���v�Ƃ����C�x���g�𔭐�����i�V�i���I���i�ށj
		}
	}
	
	// ArrayList�ɂ��A�G�̃��j�b�g�̏����֌W by.kawasaki
	
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
	 * �G���j�b�g�����������X�g�ienemyBulletListFromEnemy�j��G���j�b�g�̃��X�g�ɐݒ肷��@by.kawasaki
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
	

	// ���ꂢ��H
	@Override
	public void action(String action, Event event, ScenarioState nextState) {
		// �V�i���I�i�s�ɂ�鐢�E�ւ̍�p�������ɏ���
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
	 * �Q�[���̃��C��
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
