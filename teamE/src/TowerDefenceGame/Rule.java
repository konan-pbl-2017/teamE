package TowerDefenceGame;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;


public class Rule extends JPanel {
	
    JButton btn1,btn2,btn3;
    JLabel paneltitle,introduction1;
    MainFrame mf;
    String str;
    public Rule (MainFrame m,String s){
    	mf = m;
    	str = s;
    	this.setName("rule");
    	this.setLayout(null);
    	this.setSize(1200, 600);
    	/*paneltitle = new JLabel("�����"   
    			+getClass().getCanonicalName()+"�N���X�̃p�l���ł�");
    	paneltitle.setBounds(0, 5, 400, 40);
    	this.add(paneltitle);
    	*/
    	introduction1 = new JLabel("<html>----------------------------------------�V�ѕ�----------------------------------------<br><br>�X�|�[���n�_����icommons�߂����čU�߂Ă���G��|���Q�[���ł��B<br><br>�^���[��ݒu���A�U�߂Ă���G����icommons����肫��Ə����B<br><br>��肫�ꂸ��icommons�𔚔j���ꂽ��GAMEOVER�ɂȂ�܂��B<br><br>--------------------------------------�������--------------------------------------<br><br>V : �^���[�̐ݒu<br><br>A W S T : �ړ�<<html>");
    	introduction1.setBounds(240, 0, 1100, 410);
    	introduction1.setFont(new Font("Arial", Font.PLAIN, 23));
    	this.add(introduction1);

    	btn1 = new JButton("�߂�");
    	btn1.setBounds(450, 400, 300, 80);
    	btn1.addActionListener(new ActionListener(){
    		public void actionPerformed(ActionEvent e){
    			pc(mf.PanelNames[0]);
    		}
    	});
    	this.add(btn1);
    }
	public void pc(String str){
    	mf.PanelChange((JPanel)this, str);
    }
}
