package view.start;
import java.awt.*;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

import model.Model;
import model.Users;
import model.sql.Sql;
import tools.Constant;
import view.SteamJTP;
public class Wait2 extends Wait1{
	/**
	 * ��һ�����붯��
	 */
	private static final long serialVersionUID = -6252620521894905030L;
	private Sql sql;
	private Model model;
	public Wait2(Model model,Sql sql) {
		this.sql=sql;
		this.model=model;
	}
	public void paint(Graphics g) {
		drawDelay(g);
		new SteamJTP(model,sql);
		if(model.getUsers().getIdentity()==1) new Sell();
	}
}
class Sell extends JFrame {
	private Image icon=(new ImageIcon(Constant.IMAGEPATH+"steamicon.png")).getImage();		
	private ImageIcon bg = new ImageIcon(Constant.IMAGEPATH+"advertisement.jpg");
	private JLabel jb = new JLabel(bg);
	public Sell() {
		this.setTitle("�＾����");
		this.setIconImage(icon);															//ͼ��
		this.setVisible(true);																//���ڿɼ�
		this.setSize(600,698);							//��С
		this.setLocationRelativeTo(null); 
		this.setDefaultCloseOperation(1);								//�رմ���
		this.setLayout(null);																//ȡ������	
		this.setResizable(false);
		jb.setBounds(0, 0, 600, 698);
		this.add(jb);
	}
}
