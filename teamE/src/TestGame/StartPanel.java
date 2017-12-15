package TestGame;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
 
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

//�@�X�e�[�W�Z���N�g�̉��
 
public class StartPanel extends JPanel {
	
    JButton btn,btn2,btn3;
    JLabel paneltitle;
    MainFrame mf;
    String str;
    
    public StartPanel(MainFrame m,String s){
        mf = m;
        str = s;
        this.setName("start");
        this.setLayout(null);
        this.setSize(1200, 600);
        paneltitle = new JLabel("�����"
                +getClass().getCanonicalName()+"�N���X�̃p�l���ł�");
        paneltitle.setBounds(0, 5, 400, 40);
        this.add(paneltitle);
        
        btn = new JButton("�X�e�[�W�I���Ɉړ�");
        btn.setBounds(450, 400, 300, 80);
        btn.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                pc(mf.PanelNames[1]);
            }
        });
        this.add(btn);
        
    }
    
    public void pc(String str){
        mf.PanelChange((JPanel)this, str);
    }
    
}
