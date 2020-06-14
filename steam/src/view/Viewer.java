package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableCellRenderer;

import controller.table.Tables_ctl;
import model.sql.Sql;
import tools.Pageinfo;

public class Viewer extends JPanel implements ActionListener{
	public Pageinfo pageinfo;
	public Sql sql;
	public JTable table;
	public JPanel option,self;
	public JButton btn_up,btn_down,btn_first,btn_last,btn_search,btn_jump;
	private JButton[] btns= new JButton[5];
	private String[] btnsText= {"��ҳ","��һҳ","��һҳ","ĩҳ","��ת"};
	public JTextField text_jump =new JTextField(2);
	public JTextField text_search =new JTextField(10);
	private JLabel jlb,jlb0,jlb1;
	private Tables_ctl ct;
	public Object source;
	public String str_jump,str_search="";
	public String zero ="0";
	public Viewer(Sql sql,Tables_ctl ct) {
		this.setSize(1400,800);
		this.setLayout(new BorderLayout());
		this.setVisible(true);
		this.sql=sql;
		this.ct=ct;
		pageinfo = new Pageinfo();
		option=new JPanel();
		option.setBackground(Color.LIGHT_GRAY);
		option.setLayout(null);
		option.setPreferredSize(new Dimension(1400,100));
		text_jump.setBounds(1210, 0, 40, 40);
		option.add(text_jump);

		jlb = new JLabel("����: "+ct.getVaild()+" ��");
		jlb0=new JLabel("ҳ���� "+1+" / "+(int)(ct.getVaild()/20+1));
		jlb1= new JLabel("��������Ҫ��ת��ҳ��");
		jlb.setBounds(130,0,100,40);
		jlb0.setBounds(10, 0, 100, 40);
		jlb1.setBounds(1050, 0, 150, 40);
		option.add(jlb);
		option.add(jlb0);
		option.add(jlb1);

		for(int i=0;i<btns.length;i++) {
			btns[i] = new JButton(btnsText[i]);
			btns[i].setBounds(500+i*100, 0,80, 40);
			option.add(btns[i]);
			btns[i].addActionListener(this);
		}
		btns[4].setBounds(1260,00,80,40);
		btn_first=btns[0];
		btn_up=btns[1];
		btn_down=btns[2];
		btn_last=btns[3];
		btn_jump=btns[4];

		btn_up.setEnabled(false);
		btn_first.setEnabled(false);

		if((int)(ct.getVaild()/20)==0) {
			btn_down.setEnabled(false);
			btn_last.setEnabled(false);
		}

	}
	public void setTable(JTable table) {
		// ���ñ��������ɫ
		table.setForeground(Color.BLACK);                   // ������ɫ
		table.setFont(new Font(null, Font.PLAIN, 14));      // ������ʽ
		//		table.setGridColor(Color.GRAY);                     // ������ɫ

		// ���ñ�ͷ
		table.getTableHeader().setFont(new Font(null, Font.BOLD, 14));  // ���ñ�ͷ����������ʽ
		table.getTableHeader().setForeground(Color.RED);                // ���ñ�ͷ����������ɫ

		// �����и�
		table.setRowHeight(30);

		DefaultTableCellRenderer r   = new DefaultTableCellRenderer();   //���Ԫģ��
		r.setHorizontalAlignment(JLabel.CENTER);   							//���þ���
		table.setDefaultRenderer(Object.class, r);							//����table����α��Ԫģ��

		// �� ��ͷ ��ӵ���������
		this.add(table.getTableHeader(), BorderLayout.NORTH);
		// �� ������� ��ӵ���������
		this.add(table, BorderLayout.CENTER);
		//�����
		this.add(option, BorderLayout.SOUTH);

	}
	public void actionPerformed(ActionEvent e) {}
	//ҳ��ı䷽��
	public void pageChange() {
		//��һҳ
		if(source==btn_up && pageinfo.getPage()>1) {					
			table.setModel(ct.getTableModel());
			pageinfo.setPage(pageinfo.getPage()-1);
			ct.startUpdata(table, pageinfo,str_search);
		}
		//��һҳ
		if(source==btn_down && (pageinfo.getPage() < (int)(ct.getVaild()/20)+1 )) {  
			table.setModel(ct.getTableModel());
			pageinfo.setPage(pageinfo.getPage()+1);
			ct.startUpdata(table, pageinfo,str_search);
		}
		//��ҳ
		if(source==btn_first) {
			pageinfo.setPage(1);
			updata();
		}
		//ĩҳ
		if(source==btn_last) {
			pageinfo.setPage((int)(ct.getVaild()/20)+1);
			updata();
		}
		//ҳ��Ϊ��ҳ
		if(pageinfo.getPage()==1) {
			btn_up.setEnabled(false);
			btn_first.setEnabled(false);
		}else {
			btn_up.setEnabled(true);
			btn_first.setEnabled(true);
		}
		//ҳ��Ϊĩҳ
		if(pageinfo.getPage()==((int)(ct.getVaild()/20+1))) {
			btn_down.setEnabled(false);
			btn_last.setEnabled(false);
		}else {
			btn_down.setEnabled(true);
			btn_last.setEnabled(true);
		}
		//���ֻ��һҳ
		if((int)(ct.getVaild()/20+1)==1) {
			btn_first.setEnabled(false);
			btn_up.setEnabled(false);
			btn_down.setEnabled(false);
			btn_last.setEnabled(false);
		}
		jlb0.setText("ҳ���� "+pageinfo.getPage()+" / "+(int)(ct.getVaild()/20+1));
		jlb.setText("����: "+ct.getVaild()+" ��");
	}
	//ҳ����ת
	public void pageJump() {
		c:if(source==btn_jump) {
			str_jump=text_jump.getText();
			switch(ct.testPage(str_jump, pageinfo.getPage())) {
			case 1 :
				JOptionPane.showConfirmDialog(this, "������Ҫ��ת��ҳ��","����",JOptionPane.DEFAULT_OPTION);
				break c;
			case 2:
				JOptionPane.showConfirmDialog(this, "��������ȷ��ҳ��","����",JOptionPane.DEFAULT_OPTION);
				break c;
			case 3:
				break ;
			case 4:
				JOptionPane.showConfirmDialog(this, "�����ڸ�ҳ��","����",JOptionPane.DEFAULT_OPTION);
				break c;
			case 5:
				JOptionPane.showConfirmDialog(this, "�����ڵ�ǰҳ","����",JOptionPane.DEFAULT_OPTION);
				break c;
			}
			pageinfo.setPage(Integer.valueOf(str_jump).intValue());
			ct.startUpdata(table, pageinfo, str_search);
		}
	}
	public void updata(){
		table.setModel(ct.getTableModel());
		ct.startUpdata(table, pageinfo,str_search);
	}
	public void updataJLabel() {
		jlb0.setText("ҳ���� "+pageinfo.getPage()+" / "+(int)(ct.getVaild()/20+1));
		jlb.setText("����: "+ct.getVaild()+" ��");
	}
}
