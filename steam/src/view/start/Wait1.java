package view.start;
import java.awt.*;
import javax.swing.ImageIcon;
import javax.swing.JFrame;

import tools.Constant;
public class Wait1 extends JFrame{
	/**
	 * ��һ�����붯��
	 */
	private static final long serialVersionUID = -6252620521894905030L;
	private Image wait1_bg=(new ImageIcon(Constant.IMAGEPATH+"wait1.jpg")).getImage();	   //��¼����
	private Image icon=(new ImageIcon(Constant.IMAGEPATH+"steamicon.png")).getImage();			
	public Wait1() {
		this.setTitle("������...");															//����
		this.setIconImage(icon);															//ͼ��
		this.setVisible(true);																//���ڿɼ�
		this.setSize(441,320);							//��С
		this.setLocationRelativeTo(null); 
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);								//�رմ���
		this.setLayout(null);																//ȡ������	
		this.setResizable(false);
	}
	public static void main(String[] args) {
		new Wait1();
	}
	public void drawDelay (Graphics g) {
		g.setColor(Color.WHITE);
		g.drawImage(wait1_bg, 0, 20, this);
		g.drawRect(15, 288, 405,14);
		for(int i=20;i<=410;) {
			g.draw3DRect(i, 290, 10, 10,true);
			i+=20;
			try {
				Thread.sleep(50);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		this.dispose();
	}
	public void paint(Graphics g) {
		drawDelay(g);
		new Login();
	}
}
