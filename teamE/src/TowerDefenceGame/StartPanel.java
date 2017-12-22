package TowerDefenceGame;


import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
 
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

//�@�X�e�[�W�Z���N�g�̉��
 
public class StartPanel extends JPanel {
	
    JButton btn1,btn2,btn3;
    JLabel paneltitle,introduction;
    MainFrame mf;
    String str;
    
    public StartPanel(MainFrame m,String s){
        mf = m;
        str = s;
        this.setName("start");
        this.setLayout(null);
        this.setSize(1200, 600);
        /*paneltitle = new JLabel("�����"
                +getClass().getCanonicalName()+"�N���X�̃p�l���ł�");
        paneltitle.setBounds(0, 5, 400, 40);
        this.add(paneltitle);
        */
        introduction = new JLabel("<html>�^���[�f�B�t�F���X�Q�[��<html>");
    	introduction.setBounds(300, 20, 1100, 200);
    	introduction.setFont(new Font("Arial", Font.PLAIN, 50));
    	this.add(introduction);
    	
        btn1 = new JButton("�X�e�[�W�I���Ɉړ�");
        btn1.setBounds(450, 275, 300, 80);
        btn1.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                pc(mf.PanelNames[1]);
            }
        });
        this.add(btn1);
        
        btn2 = new JButton("�V�ѕ�");
        btn2.setBounds(450, 400, 300, 80);
        btn2.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                pc(mf.PanelNames[5]);
            }
        });
        this.add(btn2);
    }
    
    public void pc(String str){
        mf.PanelChange((JPanel)this, str);
    }
    
}
