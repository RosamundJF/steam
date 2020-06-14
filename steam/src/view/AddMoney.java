package view;

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
import javax.swing.JRadioButton;
import controller.Information_ctl;
import model.Users;
import tools.Constant;

public class AddMoney extends JFrame implements ActionListener{
	/**
	 * 
	 */
	private static final long serialVersionUID = -3611520484576944735L;
	private Image icon=(new ImageIcon(Constant.IMAGEPATH+"steamicon.png")).getImage();	
	private Image bg=(new ImageIcon(Constant.IMAGEPATH+"bg.jpg")).getImage();	
	private JRadioButton U1000 =new JRadioButton("1000");
	private JRadioButton U500 =new JRadioButton("500");
	private JRadioButton U200 =new JRadioButton("200");
	private JRadioButton U100 =new JRadioButton("100");
	private JButton charge =new JButton("��ֵ");
	private JButton exit =new JButton("����");
	private JComponent[] btns = {U1000,U500,U200,U100,charge,exit}; 
	private JFrame steam;
	private Information_ctl information_ctl;
	private Information information;
	private Users users;
	private int money=100;
	public AddMoney(JFrame steam,Information information,Information_ctl information_ctl,Users users) {
		this.setTitle("��ֵ");
		this.setIconImage(icon);
		this.setSize(600, 350);
		this.setLocationRelativeTo(steam);
		this.setLayout(null);
		this.setResizable(false);
		this.setDefaultCloseOperation(0);
		this.steam=steam;
		this.information_ctl=information_ctl;
		this.users=users;
		this.information=information;
		ButtonGroup money = new ButtonGroup();
		for(int i=0;i<btns.length;i++) {
			btns[i].setSize(70, 30);
			btns[i].setLocation(100+100*i, 100);
			this.add(btns[i]);
			if(i<4) {
				money.add((JRadioButton)btns[i]);
			}
		}
		U100.setSelected(true);
		charge.setLocation(200, 200);
		exit.setLocation(300, 200);
		charge.addActionListener(this);
		exit.addActionListener(this);
		this.setVisible(true);
	}
	public void actionPerformed(ActionEvent e) {
		Object source = e.getSource();
		//��ֵ����ѡ��
		if(U1000.isSelected()) {
			money=1000;
		}else {
			if(U500.isSelected()) {
				money=500;
			}else {
				if(U200.isSelected()) {
					money=200;
				}else money=100;
			}
		}
		//�˳���ť
		if(source==exit) {
			steam.setEnabled(true);
			this.dispose();
		}else {
			//��ֵ��ť
			if(source==charge) {
				int n=JOptionPane.showConfirmDialog(this,"��ȷ��Ҫ��ֵ"+money+"��","��ʼ��ֵ",JOptionPane.YES_NO_OPTION);	
				if(n==JOptionPane.YES_OPTION) 
				{
					information_ctl.Recharge(users, money);
					information.repaint();
					JOptionPane.showConfirmDialog(this, "��ֵ�ɹ�", "��ʾ",JOptionPane.CLOSED_OPTION);
				}
			}
		}
	}

	public void paint(Graphics g) {
		g.setColor(Color.WHITE);
		g.setFont(new Font("����", 1, 30));
		g.drawImage(bg, 0, 0, null);
		for(int i=0;i<btns.length;i++) {
			btns[i].repaint();
		}
		g.drawString("��ѡ����Ҫ��ֵ�Ľ��", 120	, 100);
	}
}
