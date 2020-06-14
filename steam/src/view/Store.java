package view;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;

import controller.table.Store_ctl;
import model.Model;
import model.sql.Sql;
import tools.Pageinfo;

public class Store extends Viewer implements ActionListener{
	private Store_ctl store_ctl;
	private Information information;
	private MyGames mygames;
	private JButton btn_buy;
	private JLabel jlb2,jlb3,jlb4;
	private JComboBox jcb; 
	private JTextField text_buy =new JTextField(3);
	private String str_buy;
	private String[] jcb_content,categoryNumber;

	public Store(Model model,Sql sql,Information information,MyGames mygames,Store_ctl store_ctl) {
		super(sql,store_ctl);
		this.store_ctl=store_ctl;
		this.information=information;
		this.mygames=mygames;
		this.sql=sql;

		text_buy.setBounds(950,50,40,40);
		text_search.setBounds(340, 50, 150, 40);		

		jcb_content=new String[sql.getCategory_sql().getCategoryNumber()+1];		//��ȡ��������������+1
		jcb_content[0]="ȫ��";
		categoryNumber = sql.getCategory_sql().getAllCategories();              //��ȡ���͵�����
		for(int i=1;i<jcb_content.length;i++) {
			jcb_content[i]=categoryNumber[i-1];
		}
		jcb = new JComboBox(jcb_content);  
		jcb.setBounds(700, 50, 80, 40);
		jcb.addActionListener(this);

		btn_buy=new JButton("����");
		btn_search=new JButton("����");
		btn_buy.setBounds(1000,50,80,40);
		btn_search.setBounds(500,50,80,40);	
		btn_buy.addActionListener(this);
		btn_search.addActionListener(this);

		jlb2= new JLabel("������������");
		jlb3= new JLabel("��������Ҫ�����ID");
		jlb4= new JLabel("��������Ҫ��������Ϸ��");
		jlb2.setBounds(600, 50, 100, 40);
		jlb3.setBounds(800, 50, 150, 40);
		jlb4.setBounds(190, 50, 150, 40);
		JComponent[] objects = {text_buy,text_search,jcb,btn_buy,btn_search,jlb2,jlb3,jlb4};
		for(int i=0;i<objects.length;i++) {
			option.add(objects[i]);
		}

		// ����һ�����ָ�� ���������� �� ��ͷ
		table = new JTable(store_ctl.getRowData(pageinfo,str_search), store_ctl.getColumnNames()){
			public boolean isCellEditable(int row, int column){
				return false;
			}
		};
		setTable(table);
		table.addMouseListener(new MouseListener(){
			public void mouseClicked(MouseEvent e) {
				text_buy.setText(table.getValueAt(table.getSelectedRow(),1).toString());  
				str_buy=text_buy.getText();			
				if(e.getClickCount()==2) {
					purchase();
				}
			}
			public void mousePressed(MouseEvent e) {
			}
			public void mouseReleased(MouseEvent e) {
			}
			public void mouseEntered(MouseEvent e) {}
			public void mouseExited(MouseEvent e) {}
		}); 
	}	

	public void actionPerformed(ActionEvent e) {
		source = e.getSource();
		str_search=text_search.getText();
		//������Ϸ
		a:if(source==btn_search) {
			if(store_ctl.testSearch(str_search)==2)
			{
				JOptionPane.showConfirmDialog(this, "��������ȷ����Ϸ��","����",JOptionPane.DEFAULT_OPTION);
				break a;
			}
			table.setModel(store_ctl.getTableModel());
			pageinfo.setPage(1);
			store_ctl.startUpdata(table, pageinfo,str_search);
		}

		//������Ϸ
		b:if(source==btn_buy) {
			//			str_buy=text_buy.getText();			
			switch(store_ctl.testInputId(str_buy)) {
			case 0 :break;
			case 1 :
				JOptionPane.showConfirmDialog(this, "������Ҫ�����id","����",JOptionPane.DEFAULT_OPTION);
				break b;
			case 2 :
				JOptionPane.showConfirmDialog(this, "��������ȷ��id","����",JOptionPane.DEFAULT_OPTION);
				break b;
			}

			if(store_ctl.gameIdExist(str_buy)){
				JOptionPane.showConfirmDialog(this, "�����ڸ���Ϸid","����",JOptionPane.DEFAULT_OPTION);
				break b;
			}
			purchase();
		}
		//�������Ͳ�ѯ
		if(source==jcb) {
			pageinfo.setPage(1);
			pageinfo.setCategoryid(jcb.getSelectedIndex());
			store_ctl.startUpdata(table, pageinfo,str_search);
		}
		pageJump();
		pageChange();
	}
	public void purchase() {
		int n=JOptionPane.showConfirmDialog(this,"��ȷ��Ҫ�������Ϸ��?","��ʾ",JOptionPane.YES_NO_OPTION);	
		if(n==JOptionPane.YES_OPTION) {
			switch(store_ctl.buy(Integer.valueOf(str_buy).intValue())) {
			case 0:
				JOptionPane.showConfirmDialog(this, "����ӵ�и���Ϸ","��ʾ",JOptionPane.DEFAULT_OPTION);
				break;
			case 1:
				JOptionPane.showConfirmDialog(this, "��������","��ʾ",JOptionPane.DEFAULT_OPTION);
				break;
			case 2:
				JOptionPane.showConfirmDialog(this, "����ɹ�","��ϲ",JOptionPane.DEFAULT_OPTION);
				information.repaint();
				updata();
				text_buy.setText("");
				mygames.getMygames_ctl().updateVaild();
				mygames.updata();
				mygames.pageChange();
				mygames.updataJLabel();
				break;
			case 3:
				JOptionPane.showConfirmDialog(this, "����Ϸ���¼�","��ʾ",JOptionPane.DEFAULT_OPTION);
				break;
			}
		}
	}
}
