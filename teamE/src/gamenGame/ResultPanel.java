package gamenGame;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;


public class ResultPanel extends JPanel{
	
    JButton btn1,btn2,btn3;
    JLabel paneltitle;
    MainFrame mf;
    String str;
    
    public ResultPanel(MainFrame m,String s){
        mf = m;
        str = s;
        this.setName("result");
        this.setLayout(null);
        this.setSize(1200, 600);
        paneltitle = new JLabel("これは"
                +getClass().getCanonicalName()+"クラスのパネルです");
        paneltitle.setBounds(0, 5, 400, 40);
        this.add(paneltitle);
        
        
        
        btn1 = new JButton("ステージ選択に戻る");
        btn1.setBounds(750, 400, 300, 80);
        btn1.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                pc(mf.PanelNames[1]);
            }
        });
        this.add(btn1);
        
        
        btn2 = new JButton("スタート画面に戻る");
        btn2.setBounds(150, 400, 300, 80);
        btn2.addActionListener(new ActionListener(){
        	//処理したい内容を記述、ここではパネルチェンジ
            public void actionPerformed(ActionEvent e){
                pc(mf.PanelNames[0]);
            }
        });
        this.add(btn2);
        
    }
    
    public void pc(String str){
        mf.PanelChange((JPanel)this, str);
    }
	
}