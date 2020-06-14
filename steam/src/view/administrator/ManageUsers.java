package view.administrator;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;

import controller.table.ManageUsers_ctl;
import model.Model;
import model.sql.Sql;
import view.Viewer;

public class ManageUsers extends Viewer implements ActionListener{
	private ManageUsers_ctl manageusers_ctl;
	private JButton btn_freeze;
	private JLabel jlb2,jlb3;
	private JTextField text_freeze =new JTextField(10);
	private String str_id;

	public ManageUsers(Model model,Sql sql,ManageUsers_ctl manageusers_ctl) {
		super(sql,manageusers_ctl);
		this.manageusers_ctl=manageusers_ctl;

		text_freeze.setBounds(850,50,40,40);
		text_search.setBounds(400, 50, 150, 40);
		btn_freeze=new JButton("����");
		btn_search=new JButton("����");
		btn_freeze.setBounds(900,50,80,40);
		btn_search.setBounds(600,50,80,40);
		btn_freeze.addActionListener(this);
		btn_search.addActionListener(this);
		jlb2= new JLabel("��������Ҫ����/�ⶳ��ID");
		jlb3= new JLabel("�������û����ǳ�");
		jlb2.setBounds(700, 50, 150, 40);
		jlb3.setBounds(290, 50, 150, 40);
		JComponent[] objects= {text_freeze,text_search,btn_freeze,btn_search,jlb2,jlb3};
		for(int i=0;i<objects.length;i++) {
			option.add(objects[i]);
		}
	
		// ����һ�����ָ�� ���������� �� ��ͷ
		table = new JTable(manageusers_ctl.getRowData(pageinfo,str_search), manageusers_ctl.getColumnNames()){
			   public boolean isCellEditable(int row, int column){
			       return false;
			   }
			};
		setTable(table);
		table.addMouseListener(new MouseListener(){
            public void mouseClicked(MouseEvent e) {
            	text_freeze.setText(table.getValueAt(table.getSelectedRow(),1).toString());  
            	str_id=text_freeze.getText();
            	if(e.getClickCount()==2) {
            		text_freeze.setText(table.getValueAt(table.getSelectedRow(),1).toString());  
                	str_id=text_freeze.getText();
            		freeze();
				}
            }
			public void mousePressed(MouseEvent e) {}
			public void mouseReleased(MouseEvent e) {}
			public void mouseEntered(MouseEvent e) {}
			public void mouseExited(MouseEvent e) {}
		}); 
	}	

	public void actionPerformed(ActionEvent e) {
		source = e.getSource();
		//������ť
		a:if(source==btn_search) {
			pageinfo.setPage(1);
			switch(manageusers_ctl.testSearch(str_search)) {
			case 0:break;
			case 1:
				manageusers_ctl.startUpdata(table, pageinfo,str_search);
				break a;
			case 2:
				JOptionPane.showConfirmDialog(this, "��������ȷ���û���","����",JOptionPane.DEFAULT_OPTION);
				break a;
			}
			table.setModel(manageusers_ctl.getTableModel());
			pageinfo.setPage(1);
			manageusers_ctl.startUpdata(table, pageinfo,str_search);
		}
		//����/�ⶳĳ���û�
		b:if(source==btn_freeze) {
			switch(manageusers_ctl.testInputId(str_id)) {
			case 0:break;
			case 1:
				JOptionPane.showConfirmDialog(this, "������Ҫ����/�ⶳ��ID","����",JOptionPane.DEFAULT_OPTION);
				break b;
			case 2:
				JOptionPane.showConfirmDialog(this, "��������ȷ��ID","����",JOptionPane.DEFAULT_OPTION);
				break b;
			}
			if(!manageusers_ctl.userIdExist(str_id)) {
				JOptionPane.showConfirmDialog(this, "�����ڸ�ID","����",JOptionPane.DEFAULT_OPTION);
				break b;
			}
			freeze();
		}
		pageJump();
		pageChange();
	}
	public void freeze() {
		text_freeze.setText("");
		manageusers_ctl.StartFreeze(Integer.valueOf(str_id).intValue());
		JOptionPane.showConfirmDialog(this, "�޸ĳɹ�","��ʾ",JOptionPane.DEFAULT_OPTION);
		updata();
	}
}
