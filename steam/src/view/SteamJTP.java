package view;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import controller.table.ManageGames_ctl;
import controller.table.ManageLogin_ctl;
import controller.table.ManageOrders_ctl;
import controller.table.ManageUsers_ctl;
import controller.table.MyGames_ctl;
import controller.table.Store_ctl;
import model.Model;
import model.sql.Sql;
import model.table.Tables_sql;
import tools.Constant;
import view.administrator.ManageGames;
import view.administrator.ManageLogin;
import view.administrator.ManageOrders;
import view.administrator.ManageUsers;
public class SteamJTP extends JFrame{
	private Image icon=(new ImageIcon(Constant.IMAGEPATH+"steamicon.png")).getImage();	
	private JTabbedPane jtab = new JTabbedPane(JTabbedPane.TOP);
	private Tables_sql tables_sql;
	//��������ѡ���view��
	private Information information;
	private Store store;
	private MyGames mygames;
	private ManageGames managegames;
	private ManageUsers manageusers;
	private ManageOrders manageorders;
	private ManageLogin managelogin;
	//������view���Ӧ��controller��
	private Store_ctl store_ctl;
	private MyGames_ctl mygames_ctl;
	private ManageGames_ctl managegames_ctl;
	private ManageUsers_ctl manageusers_ctl;
	private ManageOrders_ctl manageorders_ctl;
	private ManageLogin_ctl  managelogin_ctl;
	
	public SteamJTP(Model model ,Sql sql) {
		this.setTitle("Steam");
		this.setIconImage(icon);
		this.setLocation(80,10); 
		this.setSize(1400, 800);
		this.setResizable(false);
		this.setDefaultCloseOperation(0);
		this.setVisible(true);
		tables_sql=new Tables_sql(model, sql);
		//ʵ������controller��
		store_ctl = new Store_ctl(model, sql,tables_sql);
		mygames_ctl = new MyGames_ctl(model, sql,tables_sql);
		managegames_ctl = new ManageGames_ctl(sql,tables_sql);
		manageorders_ctl = new ManageOrders_ctl(sql,tables_sql);
		managelogin_ctl = new ManageLogin_ctl(sql,tables_sql);
		manageusers_ctl = new ManageUsers_ctl(sql,tables_sql);
		
		jtab.add("Welcome",new Welcome());
		information = new Information(this,model,sql);
		jtab.add("�ҵ���Ϣ", information);
		
		if(model.getUsers().getIdentity()==1) {						//��������
		mygames=new MyGames(model,sql,mygames_ctl);
		store = new Store(model,sql,information,mygames,store_ctl);
		jtab.add("3A�̵�", store);
		jtab.add("�ҵ���Ϸ",mygames);
		jtab.add("����",new Sell());
		}
		else {
			managegames=new ManageGames(model,sql,this,managegames_ctl);
			manageusers=new ManageUsers(model, sql,manageusers_ctl);
			manageorders = new ManageOrders(sql,manageorders_ctl);
			managelogin = new ManageLogin(sql, managelogin_ctl,this);
			jtab.add("������Ϸ",managegames);
			jtab.add("�����û�",manageusers);
			jtab.add("�鿴����",manageorders);
			jtab.add("�鿴��¼",managelogin);
		}
		this.add(jtab);
	}

}
//��һ��ѡ�ҳ���ǻ�ӭ����Steam
class Welcome extends JPanel {
	private Image bg = new ImageIcon(Constant.IMAGEPATH+"welcome.jpg").getImage();
	public Welcome() {	
	}
	public void paint(Graphics g) {
		g.setFont(new Font(null, Font.BOLD, 30));      // ������ʽ
		g.setColor(Color.WHITE);
		g.drawImage(bg, 0, 0, this);
		g.drawString("Hi ~ ", 20, 40);
	}
}
//����
class Sell extends JPanel {
	private Image bg = new ImageIcon(Constant.IMAGEPATH+"sell.jpg").getImage();
	public void paint(Graphics g) {
		g.drawImage(bg, 0, 0, this);
	}
}
