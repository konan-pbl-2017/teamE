package TestGame;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
 
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

//ゲーム本編の画面
public class MapPanel extends JPanel {
    
    MainFrame mf;
    String str;
    
    public MapPanel(MainFrame m,String s){
        mf = m;
        str = s;
        this.setName(s);
        this.setLayout(null);
        this.setSize(1200, 600);
        /*JLabel paneltitle = new JLabel("これは"
                +getClass().getCanonicalName()+"クラスのパネルです");
        paneltitle.setBounds(0, 5, 400, 40);
        this.add(paneltitle);
        */
        JButton btn1 = new JButton("ゲームクリア");
        btn1.setBounds(150, 400, 300, 80);
        btn1.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                pc(mf.PanelNames[4]);//クリアしたら、これを呼び出す
            }
        });
        this.add(btn1);
        
        JButton btn2 = new JButton("ゲームオーバー");
        btn2.setBounds(750, 400, 300, 80);
        btn2.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                pc(mf.PanelNames[4]);///ゲームオーバーしたら、これを呼び出す
            }
        });
        this.add(btn2);
    }

	public void pc(String str){
	    mf.PanelChange((JPanel)this, str);
	}
	    
}