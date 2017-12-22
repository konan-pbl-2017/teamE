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
    	/*paneltitle = new JLabel("これは"   
    			+getClass().getCanonicalName()+"クラスのパネルです");
    	paneltitle.setBounds(0, 5, 400, 40);
    	this.add(paneltitle);
    	*/
    	introduction1 = new JLabel("<html>----------------------------------------遊び方----------------------------------------<br><br>スポーン地点からicommonsめがけて攻めてくる敵を倒すゲームです。<br><br>タワーを設置し、攻めてくる敵からicommonsを守りきると勝利。<br><br>守りきれずにicommonsを爆破されたらGAMEOVERになります。<br><br>--------------------------------------操作説明--------------------------------------<br><br>V : タワーの設置<br><br>A W S T : 移動<<html>");
    	introduction1.setBounds(240, 0, 1100, 410);
    	introduction1.setFont(new Font("Arial", Font.PLAIN, 23));
    	this.add(introduction1);

    	btn1 = new JButton("戻る");
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
