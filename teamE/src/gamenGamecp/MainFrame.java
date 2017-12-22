package gamenGamecp;

//�@��ʑJ�ڂ��������邽�߂̃v���O����
import javax.swing.JFrame;
import javax.swing.JPanel;
 
public class MainFrame extends JFrame{
    
    public String[] PanelNames = {"start","stsel","map1","map2","result","rule"};
    StartPanel start = new StartPanel(this,PanelNames[0]);
    StageSelect stsel = new StageSelect(this,PanelNames[1]);
    MapPanel map1 = new MapPanel(this,PanelNames[2]);
    MapPanel map2 = new MapPanel(this,PanelNames[3]);
    ResultPanel result = new ResultPanel(this,PanelNames[4]);
    Rule rule = new Rule(this,PanelNames[5]);
    
    int gamestate = 0;

     
    public MainFrame(){
        this.add(start);start.setVisible(true);//�Q�[���N�����A�ŏ��̃p�l��������\��
        this.add(rule);rule.setVisible(false);
        this.add(stsel);stsel.setVisible(false);
        this.add(map1);map1.setVisible(false);
        this.add(map2);map2.setVisible(false);
        this.add(result);result.setVisible(false);
        this.setBounds(100, 100, 1200, 600);//��ʂ̃T�C�Y�ύX
    }
    
    //���C�����\�b�h
    public static void main(String[] args) {
        MainFrame mf = new MainFrame();
        mf.setDefaultCloseOperation(EXIT_ON_CLOSE);//�o�c�������ƏI��
        mf.setVisible(true);
    }
    
    //�����P : ���݂̃p�l�� , �����Q: �ړ��������p�l��
    public void PanelChange(JPanel jp, String str){
        System.out.println("���݂̃p�l��:" + jp.getName());
        String name = jp.getName();
        
        //���݂̃p�l������������
        if(name==PanelNames[0]){
            start = (StartPanel)jp;
            start.setVisible(false);
        }else if(name==PanelNames[1]){
            stsel = (StageSelect)jp;
            stsel.setVisible(false);
        }else if(name==PanelNames[2]){
            map1 = (MapPanel)jp;
            map1.setVisible(false);
        }else if(name==PanelNames[3]){
            map2 = (MapPanel)jp;
            map2.setVisible(false);
        }else if(name==PanelNames[4]){
            result = (ResultPanel)jp;
            result.setVisible(false);
        }else if(name==PanelNames[5]){
            rule = (Rule)jp;
            rule.setVisible(false);
        }
        
        
        //���ɑJ�ڂ������p�l����\�����鏈��
        if(str==PanelNames[0]){
            start.setVisible(true);
        }else if(str==PanelNames[1]){
            stsel.setVisible(true);
        }else if(str==PanelNames[2]){
            //�Q�[���{�҂��Đ�����
            TemplateRPG2D game = new TemplateRPG2D();// �N���X�̌^��SimpleRolePlayingGame����ύX���Ă��܂� by.kawasaki 12/22
            game.setFramePolicy(5, 33, false);
            game.start();
            if (game.count >= 10) {
            	game.stop();
            	result.setVisible(true);
            	System.out.println("�Q�[�����I�����܂�");
            }
            
            //map1.setVisible(true);
        }else if(str==PanelNames[3]){
            map2.setVisible(true);
        }else if(str==PanelNames[4]){
            result.setVisible(true);
        }else if(str==PanelNames[5]){
            rule.setVisible(true);
        }
    }
}
