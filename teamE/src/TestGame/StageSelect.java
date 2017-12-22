package TestGame;


import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
 
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

//�@�X�e�[�W�Z���N�g�̉��
 
public class StageSelect extends JPanel {
	
    JButton btn1,btn2,btn3;
    JLabel paneltitle,introduction;
    MainFrame mf;
    String str;
    
    public StageSelect(MainFrame m,String s){
        mf = m;
        str = s;
        this.setName("stsel");
        this.setLayout(null);
        this.setSize(1200, 600);
        /*paneltitle = new JLabel("�����"
                +getClass().getCanonicalName()+"�N���X�̃p�l���ł�");
        paneltitle.setBounds(0, 5, 400, 40);
        this.add(paneltitle);
        */
        
        introduction = new JLabel("<html>STAGE SELECT<html>");
        introduction.setBounds(415, 20, 1100, 200);
    	introduction.setFont(new Font("Arial", Font.PLAIN, 50));
    	this.add(introduction);
    	
        btn1 = new JButton("�X�e�[�W1�Ɉړ�");
        btn1.setBounds(150, 275, 300, 80);
        btn1.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                pc(mf.PanelNames[2]);
            }
        });
        this.add(btn1);
        
        btn2 = new JButton("�X�e�[�W2�Ɉړ�");
        btn2.setBounds(750, 275, 300, 80);
        btn2.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                pc(mf.PanelNames[3]);
            }
        });
        this.add(btn2);
        
        
        btn3 = new JButton("�߂�");
        btn3.setBounds(450, 400, 300, 80);
        btn3.addActionListener(new ActionListener(){
        	//�������������e���L�q�A�����ł̓p�l���`�F���W
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
