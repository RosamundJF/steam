package view.start;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Timestamp;
import javax.swing.*;

import controller.Register_ctl;
import model.sql.Sql;
import tools.Constant;
public class Register extends JFrame implements ActionListener{
	/**
	 * ע�ᴰ��
	 */
	private static final long serialVersionUID = 4148628129312677952L;
	private Image register_bg=(new ImageIcon(Constant.IMAGEPATH+"register.jpg")).getImage();	  
	private Image icon=(new ImageIcon(Constant.IMAGEPATH+"steamicon.png")).getImage();					
	private JButton btn_cfm =new JButton("������֤��");												
	private JButton btn_ext =new JButton("�˳�");
	private JButton btn_test =new JButton("��֤");
	private JButton[] btns = {btn_cfm,btn_ext,btn_test};
	private JRadioButton btn_boy = new JRadioButton("��",true);
	private JRadioButton btn_girl = new JRadioButton("Ů");
	private JRadioButton btn_food = new JRadioButton("ˮ��",true);
	private JRadioButton btn_animal = new JRadioButton("����");
	private JRadioButton btn_people = new JRadioButton("����");	
	private JRadioButton[] rbtns = {btn_boy,btn_girl,btn_food,btn_animal,btn_people};
	private JTextField text_act =new JTextField(10);
	private JTextField text_nam =new JTextField(10);
	private JTextField text_asw =new JTextField(10);
	private JTextField text_test =new JTextField(10);
	private JTextField[] texts = {text_nam,text_act,text_asw,text_test};
	private JPasswordField text_pwd =new JPasswordField(10);
	private JPasswordField text_pwd2 =new JPasswordField(10);
	private JPasswordField[] pfs= {text_pwd,text_pwd2};
	private JComponent[][] objects = {btns,rbtns,texts,pfs};
	private JTextArea about = new JTextArea("�ǳƺ��ܱ�����10�ַ�����"
			+"\n"+"�ǳƺ��ܱ��𰸲�Ҫ���ո��%"											
			+"\n"+ "�˺���QQ�ţ�����Ϊ6~12�����֡�"
			+"\n"+ "���������Ϣ������֤����֤"
			+"\n"+"лл��ϣ�",5,15);
	private JFrame log_frame;
	private Register_ctl register_ctl;
	private String act,nam,pwd,pwd2,asw,code;
	private int securityId=1,sex=1;
	private String[] words= {"�ǳƣ�","�˺ţ�","���룺","�ٴ��������룺","�Ա�","����ϲ���ģ�","�𰸣�","������֤��" };
	/*
	 * 
	 * ����
	 */
	public Register(JFrame jf,Sql sql) {
		this.setTitle("Steamע��");															//����
		this.setIconImage(icon);															//ͼ��
		this.setVisible(true);																//���ڿɼ�
		this.setSize(600,380);																//��С
		this.setLocationRelativeTo(null); 
		this.setLayout(null);																//ȡ������	
		this.setResizable(false);
		this.setDefaultCloseOperation(0);	
		log_frame=jf;
		register_ctl = new Register_ctl(sql);
		
		for(int i=0;i<texts.length;i++) {
			texts[i].setSize(180, 25);
			texts[i].setLocation(220, 10+i*35);
			this.add(texts[i]);
		}
		text_asw.setLocation(220, 230);
		text_test.setLocation(220, 269);
		text_test.setEnabled(false);
		for(int i=0;i<pfs.length;i++) {
			pfs[i].setSize(180, 25);
			pfs[i].setLocation(220, 10+2*37+i*37);
			this.add(pfs[i]);
		}
		ButtonGroup sex = new ButtonGroup();
		for(int i=0;i<2;i++) {
			rbtns[i].setSize(70, 25);
			rbtns[i].setLocation(230+i*80, 155);
			sex.add(rbtns[i]);
			this.add(rbtns[i]);
		}

		ButtonGroup security = new ButtonGroup();
		for(int i=2;i<5;i++) {
			rbtns[i].setSize(70, 25);
			rbtns[i].setLocation(230+(i-2)*80, 195);
			security.add(rbtns[i]);
			this.add(rbtns[i]);
		}

		for(int i=0;i<3;i++) {
			btns[i].setSize(100, 30);
			btns[i].setLocation(150*i, 300);
			this.add(btns[i]);
			btns[i].addActionListener(this);
		}
		btn_cfm.setBounds(420, 265, 100, 30);
		JOptionPane.showConfirmDialog(this, about,"ע�����",JOptionPane.DEFAULT_OPTION);

	}
	public void paint(Graphics g) {
		g.setColor(Color.WHITE);
		g.setFont(new Font("TimesRoman", 1, 18));
		g.drawImage(register_bg, 0, 0, this);
		for(int i =0;i<words.length;i++) {
			g.drawString(words[i], 90, 60+i*37);
		}
		g.drawString("��QQ�ţ�", 420, 95);
		g.drawString("6~12������", 420, 135);
		
		for(int i = 0 ; i<objects.length;i++) {
			for(int j=0;j<objects[i].length;j++) {
				objects[i][j].repaint();
			}
		}
	}
	
	public void actionPerformed(ActionEvent e) {
		Object source = e.getSource();
		//ȷ�ϰ�ť
		b:if (source==btn_cfm) 														
		{		
			if(btn_girl.isSelected()) {
				sex=0;
			}else sex = 1;

			if(btn_animal.isSelected()) {
				securityId=2;
			}else {
				if(btn_people.isSelected()) 
					securityId=3;
				else securityId=1;
			}

			act=text_act.getText();
			nam=text_nam.getText();
			pwd=text_pwd.getText();
			pwd2=text_pwd2.getText();
			asw=text_asw.getText();
			switch(register_ctl.startTestInput(act, nam, pwd,pwd2, asw)){
			case 0:
				JOptionPane.showConfirmDialog(this, "������QQ�����﷢����֤��"+"\n"+"�������֤","��ʾ",JOptionPane.DEFAULT_OPTION);
				break;
			case 1:
				JOptionPane.showConfirmDialog(this, "����д����","����",JOptionPane.DEFAULT_OPTION);
				break b;
			case 2:
				JOptionPane.showConfirmDialog(this, "�������10�ַ�����","����",JOptionPane.DEFAULT_OPTION);
				break b;
			case 3:
				JOptionPane.showConfirmDialog(this, "QQ�Ÿ�ʽ����ȷ","����",JOptionPane.DEFAULT_OPTION);
				break b;
			case 4:
				JOptionPane.showConfirmDialog(this, "�����ʽ����ȷ","����",JOptionPane.DEFAULT_OPTION);
				break b;
			case 5:
				JOptionPane.showConfirmDialog(this, "QQ���ظ�������� ","��ʾ",JOptionPane.DEFAULT_OPTION);
				break b;
			case 6:
				JOptionPane.showConfirmDialog(this, "��Ҫ���пո��%","��ʾ",JOptionPane.DEFAULT_OPTION);
				break b;
			case 7:
				JOptionPane.showConfirmDialog(this, "�������벻һ�� ","��ʾ",JOptionPane.DEFAULT_OPTION);
				break b;
			}	
			text_pwd.setEnabled(false);
			text_pwd2.setEnabled(false);
			btn_cfm.setEnabled(false);
			for(int i=0;i<texts.length;i++) {
				texts[i].setEnabled(false);
			}
			for(int i=0;i<rbtns.length;i++) {
				rbtns[i].setEnabled(false);
			}
			text_test.setEnabled(true);
			btn_test.setEnabled(true);
		}	
		//��֤��ť
		if(source==btn_test) {
			code = text_test.getText();
			switch (register_ctl.startTestSecurityCode(code)) {
			case 0 :
				register_ctl.startRegister(act, pwd, nam, sex,securityId,asw,new Timestamp(System.currentTimeMillis()));
				JOptionPane.showConfirmDialog(this, "ע��ɹ����Ͽ��¼�� ^_^ ","��ʾ",JOptionPane.DEFAULT_OPTION);
				log_frame.setEnabled(true);
				this.dispose();	
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
		//�˳�
		if (source==btn_ext) 														
		{		
			int n=JOptionPane.showConfirmDialog(this,"��ȷ��Ҫ�˳�ע����","�˳�ע��",JOptionPane.YES_NO_OPTION);	
			if(n==JOptionPane.YES_OPTION) 
			{
				log_frame.setEnabled(true);
				this.dispose();
			}
		}	

	}
}
