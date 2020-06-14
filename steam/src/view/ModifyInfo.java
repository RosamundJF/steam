package view;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import controller.ModifyInfo_ctl;
import model.Model;
import model.sql.Sql;
import tools.Constant;
public class ModifyInfo extends JFrame implements ActionListener{
	/**
	 * 
	 */
	private static final long serialVersionUID = -2962695245784056088L;
	private Image register_bg=(new ImageIcon(Constant.IMAGEPATH+"forget.jpg")).getImage();	  
	private Image icon=(new ImageIcon(Constant.IMAGEPATH+"steamicon.png")).getImage();					
	private JRadioButton btn_boy = new JRadioButton("��");
	private JRadioButton btn_girl = new JRadioButton("Ů");
	private JRadioButton btn_food = new JRadioButton("ˮ��");
	private JRadioButton btn_animal = new JRadioButton("����");
	private JRadioButton btn_people = new JRadioButton("����");	
	private JButton btn_replaceInf =new JButton("�޸���Ϣ");	
	private JButton btn_replaceInf2 =new JButton("ȷ���޸�");	
	private JButton btn_replacePwd = new JButton("�޸�����");	
	private JButton btn_replacePwd2 = new JButton("ȷ���޸�");	
	private JButton btn_replaceSecurity = new JButton("�޸��ܱ�");	
	private JButton btn_replaceSecurity2 = new JButton("ȷ���޸�");	
	private JButton btn_test = new JButton("��֤");	
	private JButton btn_ext =new JButton("����");
	private JPasswordField text_oldPwd =new JPasswordField(10);
	private JPasswordField text_newPwd =new JPasswordField(10);
	private JTextField text_nam =new JTextField(10);
	private JTextField text_asw =new JTextField(10);
	private JTextField text_test =new JTextField(10);
	private JComponent[] objects = {btn_boy,btn_girl,btn_food,btn_animal,btn_people,btn_replaceInf,btn_replaceInf2,btn_replacePwd,btn_replacePwd2,
			btn_replaceSecurity,btn_replaceSecurity2,btn_test,btn_ext,text_oldPwd,text_newPwd,text_nam,text_asw,text_test};
	private JTextArea about = new JTextArea("�ǳƺ��ܱ�����10�ַ�����"
			+"\n"+"�ǳƺ��ܱ��𰸲�Ҫ���ո��%"											
			+"\n"+ "�˺���QQ�ţ�����Ϊ6~12�����֡�"
			+"\n"+ "���������Ϣ������֤����֤"
			+"\n"+"лл��ϣ�",5,15);
	private String nam,oldPwd,newPwd,asw,test;
	private int securityId=1,sex,textW=180,textH=25,textX=220;
	private JFrame steamJFrame;
	private Model model;
	private ModifyInfo_ctl modifyinfo_ctl ;
	public ModifyInfo(JFrame jf ,Model model,Sql sql) {
		this.setTitle("Steam�޸���Ϣ");														//����
		this.setIconImage(icon);															//ͼ��
		this.setVisible(true);																//���ڿɼ�
		this.setSize(600,600);																//��С
		this.setLocationRelativeTo(null); 
		this.setLayout(null);																//ȡ������	
		this.setResizable(false);
		this.setDefaultCloseOperation(0);	
		this.model=model;
		steamJFrame=jf;
		modifyinfo_ctl = new ModifyInfo_ctl(model,sql);
		text_nam.setText(model.getUsers().getUsername());

		text_nam.setBounds(textX, 10, textW, textH);
		text_oldPwd.setBounds(textX, 190, textW, textH);
		text_newPwd.setBounds(textX, 235, textW, textH);
		text_test.setBounds(textX, 400, textW, textH);
		text_asw.setBounds(textX, 495, textW, textH);
		setJRadioButton();

		int btnX=10,btnY=10;
		btn_replaceInf.setLocation(btnX, btnY);
		btn_replacePwd.setLocation(btnX, btnY+150);
		btn_replaceSecurity.setLocation(btnX, btnY+350);
		btn_replaceInf2.setLocation(btnX+420, 50);
		btn_replacePwd2.setLocation(btnX+420,260);
		btn_replaceSecurity2.setLocation(btnX+420, 495);
		btn_test.setLocation(430,400);
		btn_ext.setLocation(10, 500);			
		for(int i=5;i<13;i++) {
			((JButton)objects[i]).addActionListener(this);
			((JButton)objects[i]).setSize(100, 30);
		}
		for(int i=0;i<objects.length;i++) {
			this.add(objects[i]);
			objects[i].setEnabled(false);
		}
		text_nam.setText(model.getUsers().getUsername());
		btn_replaceInf.setEnabled(true);
		btn_replacePwd.setEnabled(true);
		btn_replaceSecurity.setEnabled(true);
		btn_ext.setEnabled(true);
		
		JOptionPane.showConfirmDialog(this, about,"�޸Ĺ���",JOptionPane.DEFAULT_OPTION);
	}
	public void setJRadioButton(){
		ButtonGroup sex = new ButtonGroup();
		btn_boy.setBounds(220, 45, 70, 25);
		btn_girl.setBounds(310, 45, 70, 25);
		sex.add(btn_boy);
		sex.add(btn_girl);

		if(model.getUsers().getSex()==1) btn_boy.setSelected(true);
		else btn_girl.setSelected(true);
		
		ButtonGroup security = new ButtonGroup();
		btn_food.setBounds(220, 450, 70, 25);
		btn_animal.setBounds(300, 450, 70, 25);
		btn_people.setBounds(380, 450, 70, 25);
		security.add(btn_food);
		security.add(btn_animal);
		security.add(btn_people);
	}

	public void paint(Graphics g) {
		int StringX=160;
		g.setColor(Color.WHITE);
		g.setFont(new Font("TimesRoman", 1, 18));
		g.drawImage(register_bg, 0, 0, this);
		g.drawString("�ǳƣ�", StringX, 60);
		g.drawString("�Ա�", StringX, 95);
		for(int i=0;i<50;i++) {
			g.drawString("--", 15*i, 170);
		}
		g.drawString("�����룺", StringX, 240);
		g.drawString("�����룺", StringX, 280);
		for(int i=0;i<50;i++) {
			g.drawString("--", 15*i, 360);
		}
		g.drawString("������֤��", 100, 450);
		g.drawString("����ϲ������", 100,500);
		g.drawString("�𰸣�", StringX, 545);
		for(int i=0;i<objects.length;i++) {
			objects[i].repaint();
		}
	}

	public void actionPerformed(ActionEvent e) {
		Object source = e.getSource();
		//����޸����밴ť
		if(source==btn_replacePwd) {
			int n=JOptionPane.showConfirmDialog(this,"��׼��Ҫ�޸�������","�޸�����",JOptionPane.YES_NO_OPTION);	
			if(n==JOptionPane.YES_OPTION) {
				text_oldPwd.setEnabled(true);
				text_newPwd.setEnabled(true);
				btn_replacePwd2.setEnabled(true);
				btn_replaceInf.setEnabled(false);
				btn_replacePwd.setEnabled(false);
				btn_replaceSecurity.setEnabled(false);
			}
		}
		//ȷ���޸�����
		a:if(source==btn_replacePwd2) {
			oldPwd = text_oldPwd.getText();
			newPwd = text_newPwd.getText();
			switch(modifyinfo_ctl.startTestReplacePwd(oldPwd, newPwd)) {
			case 0:break;
			case 1:
				JOptionPane.showConfirmDialog(this, "����д����","��ʾ",JOptionPane.DEFAULT_OPTION);
				break a;
			case 2:
				JOptionPane.showConfirmDialog(this, "�����ʽ����ȷ","��ʾ",JOptionPane.DEFAULT_OPTION);
				break a;
			case 3:
				JOptionPane.showConfirmDialog(this, "��������д����","��ʾ",JOptionPane.DEFAULT_OPTION);
				break a;
			}
			model.getUsers().setPassword(newPwd);
			modifyinfo_ctl.startModify();
			JOptionPane.showConfirmDialog(this, "�޸ĳɹ�","��ʾ",JOptionPane.DEFAULT_OPTION);
			steamJFrame.repaint();
			steamJFrame.setEnabled(true);
			this.dispose();
		}
		//����޸��ܱ���ť
		if(source==btn_replaceSecurity) {
			int n=JOptionPane.showConfirmDialog(this,"��׼��Ҫ�޸��ܱ���","�޸��ܱ�",JOptionPane.YES_NO_OPTION);	
			if(n==JOptionPane.YES_OPTION) {
				btn_replaceInf.setEnabled(false);
				btn_replacePwd.setEnabled(false);
				btn_replaceSecurity.setEnabled(false);
				text_test.setEnabled(true);
				btn_test.setEnabled(true);
				modifyinfo_ctl.startSend();
				JOptionPane.showConfirmDialog(this, "������QQ�����﷢����֤��"+"\n"+"�������֤","��ʾ",JOptionPane.DEFAULT_OPTION);
			}
		}
		//��֤��֤��
		c:if(source==btn_test) {
			test = text_test.getText();
			switch(modifyinfo_ctl.startTestCode(test)) {
			case 0:break;
			case 1:
				JOptionPane.showConfirmDialog(this, "��������֤��","��ʾ",JOptionPane.DEFAULT_OPTION);
				break c;
			case 2:
				JOptionPane.showConfirmDialog(this, "��֤�벻��ȷ","��ʾ",JOptionPane.DEFAULT_OPTION);
				break c;
			}
			JOptionPane.showConfirmDialog(this, "�������ܱ�","��ʾ",JOptionPane.DEFAULT_OPTION);
			text_test.setEnabled(false);
			btn_test.setEnabled(false);
			btn_people.setEnabled(true);
			btn_animal.setEnabled(true);
			btn_food.setEnabled(true);
			text_asw.setEnabled(true);
			btn_food.setSelected(true);
			btn_replaceSecurity2.setEnabled(true);
		}
		d:if(source==btn_replaceSecurity2) {
			asw = text_asw.getText();
			switch(modifyinfo_ctl.startTestASW(asw)) {
			case 0:break;
			case 1:
				JOptionPane.showConfirmDialog(this, "����д����","��ʾ",JOptionPane.DEFAULT_OPTION);
				break d;
			case 2:
				JOptionPane.showConfirmDialog(this, "�ܱ���ʽ����ȷ","��ʾ",JOptionPane.DEFAULT_OPTION);
				break d;
			case 3:
				JOptionPane.showConfirmDialog(this, "�벻Ҫ���пո�","��ʾ",JOptionPane.DEFAULT_OPTION);
				break d;
			}
			if(btn_animal.isSelected()) {
				securityId=2;
			}else {
				if(btn_people.isSelected()) 
					securityId=3;
				else securityId=1;
			}
			model.getUsers().setSecurityId(securityId);
			model.getUsers().setSecurityAnswer(asw);
			modifyinfo_ctl.startModify();
			JOptionPane.showConfirmDialog(this, "�޸ĳɹ�","��ʾ",JOptionPane.DEFAULT_OPTION);
			steamJFrame.setEnabled(true);
			this.dispose();
		}
		//����޸���Ϣ��ť
		if(source==btn_replaceInf) {
			int n=JOptionPane.showConfirmDialog(this,"��׼��Ҫ�޸Ļ�����Ϣ��","�޸���Ϣ",JOptionPane.YES_NO_OPTION);	
			if(n==JOptionPane.YES_OPTION) {
				text_nam.setEnabled(true);
				btn_boy.setEnabled(true);
				btn_girl.setEnabled(true);
				btn_replaceInf2.setEnabled(true);
				btn_replaceInf.setEnabled(false);
				btn_replacePwd.setEnabled(false);
				btn_replaceSecurity.setEnabled(false);
			}
		}
		//�޸Ļ�����Ϣ
		b:if (source==btn_replaceInf2) 														
		{		
			if(btn_girl.isSelected()) {
				sex=0;
			}else sex=1;
			nam=text_nam.getText();
			switch(modifyinfo_ctl.testInput(nam,sex)) {
			case 0:break;
			case 1:
				JOptionPane.showConfirmDialog(this, "�������޸�","����",JOptionPane.DEFAULT_OPTION);
				break b;
			case 2:
				JOptionPane.showConfirmDialog(this, "����д����","����",JOptionPane.DEFAULT_OPTION);
				break b;
			case 3:
				JOptionPane.showConfirmDialog(this, "��ʽ����ȷ","����",JOptionPane.DEFAULT_OPTION);
				break b;
			case 4:
				JOptionPane.showConfirmDialog(this, "��Ҫ���пո�","����",JOptionPane.DEFAULT_OPTION);
				break b;
			}
			int n=JOptionPane.showConfirmDialog(this,"��ȷ���޸���Ϣ��","�޸���Ϣ",JOptionPane.YES_NO_OPTION);	
			if(n==JOptionPane.YES_OPTION) {
				model.getUsers().setUsername(nam);
				model.getUsers().setSex(sex);
				modifyinfo_ctl.startModify();
				JOptionPane.showConfirmDialog(this, "�޸ĳɹ�","��ʾ",JOptionPane.DEFAULT_OPTION);
				steamJFrame.repaint();
				steamJFrame.setEnabled(true);
				this.dispose();
			}else {
				break b;
			}
		}	
		//�˳�
		if (source==btn_ext) 														
		{		
			steamJFrame.setEnabled(true);
			this.dispose();
		}	
	}
}
