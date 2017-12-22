package gamenGamecp;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
 
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;


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

//�Q�[���{�҂̉��
public class MapPanel extends JPanel {
    
	
    MainFrame mf;
    String str;
    
    public MapPanel(MainFrame m,String s){
        mf = m;
        str = s;
        this.setName(s);
        this.setLayout(null);
        this.setSize(1200, 600);
        JLabel paneltitle = new JLabel("�����"
                +getClass().getCanonicalName()+"�N���X�̃p�l���ł�");
        paneltitle.setBounds(0, 5, 400, 40);
        this.add(paneltitle);
        
        JButton btn1 = new JButton("�Q�[���N���A");
        btn1.setBounds(150, 400, 300, 80);
        
        btn1.addActionListener(new ActionListener(){
        	public void actionPerformed(ActionEvent e){
                pc(mf.PanelNames[4]);//�N���A������A������Ăяo��
            }
        });
        this.add(btn1);
      
        
        JButton btn2 = new JButton("�Q�[���I�[�o�[");
        btn2.setBounds(750, 400, 300, 80);
        btn2.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                pc(mf.PanelNames[4]);///�Q�[���I�[�o�[������A������Ăяo��
            }
        });
        this.add(btn2);
    }

	public void pc(String str){
	    mf.PanelChange((JPanel)this, str);
	}
	    
}