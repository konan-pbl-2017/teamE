package game.tiger;

import java.awt.Color;
import java.math.BigDecimal;

import framework.RWT.RWTContainer;
import framework.RWT.RWTFrame3D;
import framework.RWT.RWTVirtualController;
import framework.game2D.Sprite;
import framework.gameMain.BaseScenarioGameContainer;
import framework.gameMain.SimpleRolePlayingGame;
import framework.model3D.Universe;
import framework.scenario.Event;
import framework.scenario.ScenarioState;

public class TemplateRPG2D extends SimpleRolePlayingGame {

	private MapStage map;
	private Player player;
	private Player castle[] = new Player[35];
	private Sprite king;
	private Sprite enemy;
	
	
	int unitval = 0; //	�u���郆�j�b�g�̑I��:by.tiger	##������##
	
	
	int castlenum = 0;
	boolean castleable = true;// �邪�u�����Ԃ��ۂ�:by.tiger
	// ���x�ɂ���ĕ��̂������Ă��鎞�Ƀ{�^���������邩�ǂ����𔻒肷��t���O
	private boolean disableControl = false;

	@Override
	public void init(Universe universe) {
		map = new MapStage();
		universe.place(map);
		camera.addTarget(map);

		// �v���C���[�̔z�u
		player = new Player("data\\player\\player.gif");
		player.setPosition(14.0, 14.0);
		player.setCollisionRadius(0.5);
		universe.place(player);
		// ���j�b�g�̔z�u
		for (int i = 0; i < 30; i++) {
			castle[i] = new Player("data\\RPG\\block.jpg");
			castle[i].setPosition(-1, -1);
			castle[i].setCollisionRadius(0.5);
			universe.place(castle[i]);
		}
		// map����ʂ̒�����
		setMapCenter(14.0, 14.0);
		setViewRange(30, 30);

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
		if (player.getVelocity().getX() == 0.0 && player.getVelocity().getY() == 0.0) {
			player.setPosition(
					new BigDecimal(player.getPosition().getX()).setScale(0, BigDecimal.ROUND_HALF_UP).doubleValue(),
					new BigDecimal(player.getPosition().getY()).setScale(0, BigDecimal.ROUND_HALF_UP).doubleValue());
		}

		// ���x��0.0�ɂ���t���O�������Ă���΁A���x��0�ɂ���
		if (resetVelocity) {
			player.setVelocity(0.0, 0.0);
			disableControl = false;
		}
		// �����ʒu�ɖ߂�
		if (virtualController.isKeyDown(0, RWTVirtualController.BUTTON_A)) {
			player.setPosition(14.0, 14.0);
		}
		// �L�������ړ����Ă��Ȃ���΁A�L�[����̏������s����B
		if (!disableControl) {
			// �L�[����̏���
			/*
			 * if (virtualController.isKeyDown(0,
			 * RWTVirtualController.LEFT)&&virtualController.isKeyDown(0,
			 * RWTVirtualController.DOWN)) { player.setVelocity(-10.0, -10.0);
			 * disableControl = true;//�΂߈ړ�
			 * 
			 * }
			 */
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

		if (virtualController.isKeyDown(0, RWTVirtualController.BUTTON_B) && castleable == true) {
			castle[castlenum].setPosition(player.getPosition());// ��̔z�u:by.tiger
			castlenum++;
			if (castlenum >= 30)
				castlenum = 0;
			castleable = false;

		}
		
		
		
		if (virtualController.isKeyDown(1, RWTVirtualController.UP)) {
			unitval = 1;
		}
		if (virtualController.isKeyDown(1, RWTVirtualController.DOWN)) {
			unitval = 2;
		}
		if (virtualController.isKeyDown(1, RWTVirtualController.RIGHT)) {
			unitval = 3;
		}
		if (virtualController.isKeyDown(1, RWTVirtualController.LEFT)) {
			unitval = 4;
		}	//���j�b�g�̑I��:by.tiger	##������##
		if (unitval==1)player.setImage("data\\player\\playerU.gif");
		if (unitval==2)player.setImage("data\\player\\playerD.gif");
		if (unitval==3)player.setImage("data\\player\\playerR.gif");
		if (unitval==4)player.setImage("data\\player\\playerL.gif");
		
		System.out.println("unitval:" + unitval);
		player.motion(interval, map);
		// �Փ˔���:��̌��e�ƓG�̓����蔻��Ɏg��
		if (player.checkCollision(king)) {
			// �v���C���[�Ɖ��l���Ԃ������ꍇ
			scenario.fire("���l�ƂԂ���"); // �u���l�ƂԂ���v�Ƃ����C�x���g�𔭐�����i�V�i���I���i�ށj
		}
	}

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
