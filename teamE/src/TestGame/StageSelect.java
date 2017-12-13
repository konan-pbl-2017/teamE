package TestGame;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
 
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

//　ステージセレクトの画面
 
public class StageSelect extends JPanel {
	
    JButton btn1,btn2,btn3;
    JLabel paneltitle;
    MainFrame mf;
    String str;
    
    public StageSelect(MainFrame m,String s){
        mf = m;
        str = s;
        this.setName("stsel");
        this.setLayout(null);
        this.setSize(400, 200);
        paneltitle = new JLabel("これは"
                +getClass().getCanonicalName()+"クラスのパネルです");
        paneltitle.setBounds(0, 5, 400, 40);
        this.add(paneltitle);
        
        btn1 = new JButton("ステージlに移動");
        btn1.setBounds(20, 50, 150, 40);
        btn1.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                pc(mf.PanelNames[2]);
            }
        });
        this.add(btn1);
        
        btn2 = new JButton("ステージ2に移動");
        btn2.setBounds(200, 50, 150, 40);
        btn2.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                pc(mf.PanelNames[3]);
            }
        });
        this.add(btn2);
        
        
        btn3 = new JButton("戻る");
        btn3.setBounds(20, 100, 150, 40);
        btn3.addActionListener(new ActionListener(){
        	//処理したい内容を記述、ここではパネルチェンジ
            public void actionPerformed(ActionEvent e){
                pc(mf.PanelNames[0]);
            }
        });
        this.add(btn3);
        
    }
    
    public void pc(String str){
        mf.PanelChange((JPanel)this, str);
    }
    
}
