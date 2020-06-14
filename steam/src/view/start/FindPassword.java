package view.start;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import controller.FindPassword_ctl;
import model.sql.Sql;
import tools.Constant;

public class FindPassword extends JFrame implements ActionListener{
	private Image register_bg=(new ImageIcon(Constant.IMAGEPATH+"forget.jpg")).getImage();	  
	private Image icon=(new ImageIcon(Constant.IMAGEPATH+"steamicon.png")).getImage();					
	private JTextField text_act =new JTextField(10);
	private JTextField text_asw =new JTextField(10);
	private JTextField text_verify =new JTextField(10);
	private JPasswordField text_replace =new JPasswordField(10);
	private JPasswordField text_replace2 =new JPasswordField(10);
	private JRadioButton btn_food = new JRadioButton("ˮ��",true);
	private JRadioButton btn_animal = new JRadioButton("����");
	private JRadioButton btn_people = new JRadioButton("����");	
	private JRadioButton[] rbtns = {btn_food,btn_animal,btn_people};
	private JButton btn_replace = new JButton("����");
	private JButton btn_cfm =new JButton("ȷ��");												
	private JButton btn_ext =new JButton("����");
	private JButton btn_send =new JButton("�����ܱ���");												
	private JButton btn_verify =new JButton("��֤");
	private JButton[] btns = {btn_replace,btn_cfm,btn_ext,btn_send,btn_verify};
	private JComponent[] objects = {btn_food,btn_animal,btn_people,btn_replace,btn_cfm,btn_ext,btn_send,btn_verify,text_act,text_asw,text_replace,text_replace2,text_verify};
	private String act,asw;
	private int securityId=1;
	private FindPassword_ctl findpassword_ctl;
	private JFrame logFrame;
	private Sql sql;
	private String paintText[]= {"�˺ţ�","����ϲ���ģ�","�𰸣�","��������","������������","������֤��"};
	private JTextArea about = new JTextArea("�������������˺�"
			+"\n"+"ѡ�������ܱ�����д�ܱ��𰸺�"											
			+"\n"+ "�����޸��������"
			+"\n"+ "��������ܱ����������ܱ���ť"
			+"\n"+"������֤�룬��֤�ɹ�����ʾ�ܱ�����",5,15);
	public FindPassword(JFrame jf,Sql sql) {
		this.setTitle("Steam�һ�����");															//����
		this.setIconImage(icon);															//ͼ��
		this.setSize(600,600);							//��С
		this.setLocationRelativeTo(null); 
		this.setLayout(null);																//ȡ������	
		this.setResizable(false);
		this.setDefaultCloseOperation(0);	
		this.sql=sql;
		logFrame = jf;
		findpassword_ctl = new FindPassword_ctl(sql);
		for(int i=8;i<objects.length;i++) {
			objects[i].setBounds(220, 150+(i-8)*50, 180, 25);
		}
		text_act.setLocation(220, 100);
		text_verify.setLocation(220, 500);
		ButtonGroup security = new ButtonGroup();
		for(int i=0;i<rbtns.length;i++) {
			rbtns[i].setBounds(225+80*i,150,70,25);
			security.add(rbtns[i]);
		}
		for(int i=0;i<btns.length;i++) {
			btns[i].setSize(70, 30);
			btns[i].addActionListener(this);
		}
		btns[3].setSize(100, 30);
		btn_cfm.setLocation(420, 200);
		btn_replace.setLocation(220, 350);
		btn_send.setLocation(320, 350);
		btn_verify.setLocation(420, 500);	
		btn_ext.setLocation(10, 520);

		text_verify.setEnabled(false);
		text_replace.setEnabled(false);
		text_replace2.setEnabled(false);
		btn_replace.setEnabled(false);
		btn_verify.setEnabled(false);

		for(int i=0;i<objects.length;i++) {
			this.add(objects[i]);
		}
		this.setVisible(true);																//���ڿɼ�
		JOptionPane.showConfirmDialog(this, about,"������ʾ",JOptionPane.DEFAULT_OPTION);
	}
	public void paint(Graphics g) {
		g.setColor(Color.WHITE);
		g.setFont(new Font("TimesRoman", 1, 18));
		g.drawImage(register_bg, 0, 0, this);

		g.drawString("(6~12������)", 410, 295);

		for(int i=0;i<paintText.length-1;i++) {
			g.drawString(paintText[i], 100, 150+i*50);
		}
		g.drawString(paintText[5], 100,550);
		for(int i=0;i<objects.length;i++) {
			objects[i].repaint();
		}
	}
	public void actionPerformed(ActionEvent e) {
		Object source = e.getSource();			//��ȡ�¼�Դ
		//ȷ�ϰ�ť
		a:if (source==btn_cfm){	
			if(btn_animal.isSelected()) {
				securityId=2;
			}else {
				if(btn_people.isSelected()) 
					securityId=3;
				else securityId=1;
			}
			act=text_act.getText();
			asw=text_asw.getText();
			//���������ݵ��жϵĽ��
			switch(findpassword_ctl.startTestAnswer(asw)) {
			case 0:break;
			case 1:
				JOptionPane.showConfirmDialog(this, "����д����","����",JOptionPane.DEFAULT_OPTION);
				break a;
			case 2:
				JOptionPane.showConfirmDialog(this, "����д��ȷ","����",JOptionPane.DEFAULT_OPTION);
				break a;
			}
			switch (findpassword_ctl.startFind(act, securityId, asw)){
			case 0:
				JOptionPane.showConfirmDialog(this, "���˺Ų�����","����",JOptionPane.DEFAULT_OPTION);
				break;
			case 1:
				JOptionPane.showConfirmDialog(this, "����˻�û��ѡ����ܱ�����","��ʾ",JOptionPane.DEFAULT_OPTION);
				break;
			case 2:
				JOptionPane.showConfirmDialog(this, "�������������","�˶Գɹ�",JOptionPane.DEFAULT_OPTION);
				text_replace.setEnabled(true);
				text_replace2.setEnabled(true);
				text_act.setEnabled(false);
				text_asw.setEnabled(false);
				btn_replace.setEnabled(true);
				btn_cfm.setEnabled(false);
				btn_send.setEnabled(false);
				btn_food.setEnabled(false);
				btn_animal.setEnabled(false);
				btn_people.setEnabled(false);
				break;
			case 3:
				JOptionPane.showConfirmDialog(this, "�ܱ��𰸴���","��ʾ",JOptionPane.DEFAULT_OPTION);
				break;
			}
		}
		//�޸�����
		b:if (source==btn_replace){		  
			String str_replace = text_replace.getText();
			String str_replace2 = text_replace2.getText();
			switch(findpassword_ctl.startTestInput(str_replace,str_replace2)) {
			case 0 :
				findpassword_ctl.startReplace(act, str_replace);
				JOptionPane.showConfirmDialog(this, "���óɹ�","��ʾ",JOptionPane.DEFAULT_OPTION);
				logFrame.setEnabled(true);
				this.dispose();
				break;
			case 1 :
				JOptionPane.showConfirmDialog(this, "����д����","����",JOptionPane.DEFAULT_OPTION);
				break b;
			case 2 :
				JOptionPane.showConfirmDialog(this, "����������ʽ����","����",JOptionPane.DEFAULT_OPTION);
				break b;
			case 3 :
				JOptionPane.showConfirmDialog(this, "���������뱣��һ��","����",JOptionPane.DEFAULT_OPTION);
				break b;
			}
		}
		//�˳�
		if (source==btn_ext) 														
		{		
			logFrame.setEnabled(true);
			this.dispose();
		}
		//�����ʼ�
		c:if(source==btn_send) {
			String act = text_act.getText();
			switch(findpassword_ctl.startTestInputAct(act)) {
			case 0:
				JOptionPane.showConfirmDialog(this, "������QQ�����﷢����֤��"+"\n"+"�������֤","��ʾ",JOptionPane.DEFAULT_OPTION);
				break;
			case 1:
				JOptionPane.showConfirmDialog(this, "�������������˺�","��ʾ",JOptionPane.DEFAULT_OPTION);
				break c;
			case 2:
				JOptionPane.showConfirmDialog(this, "��������ȷ���˺�","��ʾ",JOptionPane.DEFAULT_OPTION);
				break c;
			case 3:
				JOptionPane.showConfirmDialog(this, "���˺Ų�����","��ʾ",JOptionPane.DEFAULT_OPTION);
				break c;
			}
			text_act.setEnabled(false);
			text_asw.setEnabled(false);
			text_replace.setEnabled(false);
			text_verify.setEnabled(true);
			btn_food.setEnabled(false);
			btn_animal.setEnabled(false);
			btn_people.setEnabled(false);
			btn_cfm.setEnabled(false);
			btn_send.setEnabled(false);
			btn_verify.setEnabled(true);
		}
		//��֤
		if(source==btn_verify) {
			act = text_act.getText();
			String code = text_verify.getText();
			switch (findpassword_ctl.startTestSecurityCode(code)) {
			case 0 :
				JOptionPane.showConfirmDialog(this, "�����ܱ����ܱ�����"+"\n"+sql.getSecurity_sql().getSecurity(sql.getUsers_sql().getSecurityId(act))
						+"\n"+sql.getUsers_sql().getSecurityAnswer(act),"���",JOptionPane.DEFAULT_OPTION);
				text_verify.setText("");
				text_act.setEnabled(true);
				btn_food.setEnabled(true);
				btn_animal.setEnabled(true);
				btn_people.setEnabled(true);
				text_asw.setEnabled(true);
				text_verify.setEnabled(false);
				btn_verify.setEnabled(false);
				btn_send.setEnabled(true);
				btn_cfm.setEnabled(true);
				break;
			case 1 :
				JOptionPane.showConfirmDialog(this, "��������֤�� ","��ʾ",JOptionPane.DEFAULT_OPTION);
				break;
			case 2 :
				JOptionPane.showConfirmDialog(this, "��������ȷ����֤�� ","��ʾ",JOptionPane.DEFAULT_OPTION);
				break;
			case 3 :
				JOptionPane.showConfirmDialog(this, "��֤�����","��ʾ",JOptionPane.DEFAULT_OPTION);
				break;
			}
		}
	}
}
