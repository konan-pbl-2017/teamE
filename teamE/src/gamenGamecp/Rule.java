package gamenGamecp;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;


public class Rule extends JPanel {
	
    JButton btn1,btn2,btn3;
    JLabel paneltitle;
    MainFrame mf;
    String str;
    public Rule (MainFrame m,String s){
    	mf = m;
    	str = s;
    	this.setName("rule");
    	this.setLayout(null);
    	this.setSize(1200, 600);
    	paneltitle = new JLabel("�����"   
    			+getClass().getCanonicalName()+"�N���X�̃p�l���ł�");
    	paneltitle.setBounds(0, 5, 400, 40);
    	this.add(paneltitle);
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