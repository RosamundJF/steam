package view.administrator;

import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Timestamp;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import controller.AddGame_ctl;
import model.Model;
import model.sql.Sql;
import tools.Constant;

public class AddGame extends JFrame implements ActionListener{  
	/**
	 * �����Ϸ��ͼ��
	 */
	private static final long serialVersionUID = -4567273164475073558L;
	private Image icon=(new ImageIcon(Constant.IMAGEPATH+"steamicon.png")).getImage();					
	private JButton btn_cfm =new JButton("���");
	private JButton btn_ext =new JButton("����");
	private JTextField text_price =new JTextField(10);
	private JTextField text_nam =new JTextField(10);
	private JLabel[] jlbs = new JLabel[3];
	private String[] jlbsText = {"���ƣ�","��Ǯ��","���ͣ�"};
	private JComboBox jcb;
	private String name,price;
	private String[] jcb_content;
	private Model model;
	private JFrame steamJframe;
	private ManageGames managegames;
	private AddGame_ctl addgame_ctl ;
	public AddGame(Model model,Sql sql,JFrame jf,ManageGames mg) {
		this.setTitle("�����Ϸ��Ϣ");														//����
		this.setIconImage(icon);															//ͼ��
		this.setVisible(true);																//���ڿɼ�
		this.setSize(600,350);																//��С
		this.setLocationRelativeTo(null); 
		this.setLayout(null);																//ȡ������	
		this.setResizable(false);
		this.setDefaultCloseOperation(0);
		this.model=model;
		steamJframe=jf;
		managegames=mg;
		addgame_ctl = new AddGame_ctl(model, sql);
		
		jcb_content = sql.getCategory_sql().getAllCategories();
		jcb = new JComboBox(jcb_content);  
        jcb.setBounds(220, 115, 180, 25);
        jcb.addActionListener(this);
        this.add(jcb);
        
        for(int i=0;i<jlbs.length;i++) {
        	jlbs[i] = new JLabel(jlbsText[i]);
        	jlbs[i].setBounds(150, 35+40*i, 50, 25);
        	this.add(jlbs[i]);
        }
        
        text_nam.setBounds(220, 35, 180, 25);
		text_price.setBounds(220, 75, 180, 25);
		this.add(text_nam);
		this.add(text_price);
				
		btn_cfm.setBounds(150, 250, 70, 30);	
		btn_ext.setBounds(350, 250, 70, 30);	
		this.add(btn_cfm);
		this.add(btn_ext);		
		btn_cfm.addActionListener(this);
		btn_ext.addActionListener(this);
	}
	public void actionPerformed(ActionEvent e) {
		Object source = e.getSource();
		name=text_nam.getText();
		price=text_price.getText();	
		//ȷ��
		b:if(source==btn_cfm) {
			switch(addgame_ctl.startTestGame(name, price)) {
			case 0 :break;
			case 1 :
				JOptionPane.showConfirmDialog(this, "����д����","����",JOptionPane.DEFAULT_OPTION);
				break b;
			case 2 :
				JOptionPane.showConfirmDialog(this, "����д��ȷ�ļ۸�","����",JOptionPane.DEFAULT_OPTION);
				break b;
			case 3 :
				JOptionPane.showConfirmDialog(this, "��Ϸ���ظ�","����",JOptionPane.DEFAULT_OPTION);
				break b;
			case 4 :
				JOptionPane.showConfirmDialog(this, "��Ҫ���пո��%","����",JOptionPane.DEFAULT_OPTION);
				break b;
			}
			model.getGames().setCategoryid(jcb.getSelectedIndex()+1);
			model.getGames().setName(name);
			model.getGames().setPrice(Integer.valueOf(price).intValue());
			model.getGames().setRegtime(new Timestamp(System.currentTimeMillis()).toString());
			model.getGames().setState(1);
			addgame_ctl.startAdd();
			JOptionPane.showConfirmDialog(this, "��ӳɹ�","��ʾ",JOptionPane.DEFAULT_OPTION);
			steamJframe.setEnabled(true);
			managegames.updata();
			managegames.pageChange();
			managegames.updataJLabel();
			this.dispose();
			
		}
		//�˳�
		if (source==btn_ext) 														
		{		
				steamJframe.setEnabled(true);
				this.dispose();
		}	
		
	}
}
