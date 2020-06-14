package view.start;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.sql.Timestamp;

import javax.swing.*;

import controller.Login_ctl;
import model.Model;
import model.sql.Sql;
import tools.Constant;
public class Login extends JFrame implements ActionListener{
	/**
	 * ��¼ҳ��
	 */
	private static final long serialVersionUID = 2967935310757063606L;
	private Image Login_bg=(new ImageIcon(Constant.IMAGEPATH+"login.jpg")).getImage();	   //��¼����
	private Image icon=(new ImageIcon(Constant.IMAGEPATH+"steamicon.png")).getImage();					
	private JButton btn_reg =new JButton("ע��");												
	private JButton btn_log =new JButton("��¼");
	private JButton btn_ext =new JButton("�˳�");
	private JButton btn_abt =new JButton("��");	
	private JButton btn_pwd =new JButton("��������");	
	private JButton[] btns = {btn_log,btn_reg,btn_ext,btn_abt,btn_pwd};
	private JTextArea about = new JTextArea("����-����-������",1,1);		//����
	private JTextField text_act =new JTextField(10);
	private JPasswordField text_pwd =new JPasswordField(10);	
	private JRadioButton btn_adm = new JRadioButton("����Ա");
	private JRadioButton btn_ply = new JRadioButton("���",true);	
	private JComponent[] objects = {btn_reg,btn_log,btn_ext,btn_abt,btn_pwd,text_act,text_pwd,btn_adm,btn_ply};
	private InetAddress ip = null;
	private Model model=new Model();
	private Login_ctl login_ctl ;
	private Sql sql = new Sql(model);
	public Login() {
		this.setTitle("Steam��¼");															//����
		this.setIconImage(icon);															//ͼ��
		this.setVisible(true);																//���ڿɼ�
		this.setSize(600,383);							//��С
		this.setLocationRelativeTo(null); 
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);								//�رմ���
		this.setLayout(null);																//ȡ������	
		this.setResizable(false);
		login_ctl = new Login_ctl();

		for(int i=0;i<btns.length;i++) {
			btns[i].setSize(70, 30);
			btns[i].setLocation(300+i*100, 300);
			btns[i].addActionListener(this);
			this.add(btns[i]);
		}
		btn_abt.setBounds(535, 5, 50, 30);
		btn_pwd.setBounds(5, 5, 100, 30);

		text_act.setBounds(100,260,160,25);
		text_pwd.setBounds(100,302,160,25);
		this.add(text_act);
		this.add(text_pwd);

		ButtonGroup identity = new ButtonGroup();
		btn_adm.setBounds(400,260,70,25);
		btn_ply.setBounds(500,260,70,25);
		identity.add(btn_adm);
		identity.add(btn_ply);
		this.add(btn_adm);
		this.add(btn_ply);
	} 

	public void actionPerformed(ActionEvent e) {
		Object source = e.getSource();
		// ��������¼��ť 
		b:if (source==btn_log) 														
		{	
			//��ݸ���
			if(btn_adm.isSelected()) {
				model.getUsers().setIdentity(0); 
			}else model.getUsers().setIdentity(1); 

			model.getUsers().setAccount(text_act.getText());
			model.getUsers().setPassword(text_pwd.getText());
			switch(login_ctl.startTestInput(sql,model.getUsers())) {
			case 0 :break;
			case 1:
				JOptionPane.showConfirmDialog(this, "����д����","����",JOptionPane.DEFAULT_OPTION);
				break b;
			case 2:
				JOptionPane.showConfirmDialog(this, "�˺Ż������ʽ����","����",JOptionPane.DEFAULT_OPTION);
				break b;
			}

			try {
				ip=InetAddress.getLocalHost();														//�������ַ����Ϊ������
			} catch (UnknownHostException e1) {
				e1.printStackTrace();
			}

			String logip=ip.getHostAddress();														//�ѱ�����ip��ֵ��logip

			switch(login_ctl.startLogin(sql,model.getUsers(),new Timestamp(System.currentTimeMillis()),logip)) {									//��ʼע�룬���ж�
			case 0 :
				JOptionPane.showConfirmDialog(this, "�˺Ų�����","����",JOptionPane.DEFAULT_OPTION);
				break;
			case 1 :
				JOptionPane.showConfirmDialog(this, "��¼�ɹ�","��ʾ",JOptionPane.DEFAULT_OPTION);
				this.dispose();
				new Wait2(model,sql);
				break;
			case 2 :
				JOptionPane.showConfirmDialog(this, "�������","����",JOptionPane.DEFAULT_OPTION);
				break;
			case 3 :
				JOptionPane.showConfirmDialog(this, "��ݴ���","����",JOptionPane.DEFAULT_OPTION);
				break;
			case 4 :
				JOptionPane.showConfirmDialog(this, "���˺��ѱ�����","����",JOptionPane.DEFAULT_OPTION);
				break;
			case 5 :
				JOptionPane.showConfirmDialog(this, "���˺��ѵ�¼","����",JOptionPane.DEFAULT_OPTION);
				break;
			}
		}	
		//ע��
		if (source==btn_reg) 														
		{		


		}	
		//�˳�
		if (source==btn_ext) 														
		{		
			int n=JOptionPane.showConfirmDialog(this,"��ȷ��Ҫ�˳�Steam��","�˳�Steam",JOptionPane.YES_NO_OPTION);	
			if(n==JOptionPane.YES_OPTION) this.dispose();
		}
		//����
		if (source==btn_abt) 														
		{		
			JOptionPane.showConfirmDialog(this, about,"����",JOptionPane.DEFAULT_OPTION);
		}	
		//��������
		if (source==btn_pwd) 														
		{		
			this.setEnabled(false);
			new FindPassword(this,sql);
		}	
	}

	public void paint(Graphics g) {
		g.setColor(Color.WHITE);
		g.setFont(new Font("TimesRoman", 1, 18));
		g.drawImage(Login_bg, 0, 10, this);
		g.drawString("QQ�� : ",20,308);
		g.drawString("���� : ",20,350);
		g.drawString("��½��ʽ : ",300,308);
		for(int i=0;i<objects.length;i++) {
			objects[i].repaint();
		}

	}
}

