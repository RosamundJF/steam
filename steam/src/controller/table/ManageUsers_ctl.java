package controller.table;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import tools.Pageinfo;
import model.sql.Sql;
import model.table.Tables_sql;
//�����û�TableController
public class ManageUsers_ctl extends Tables_ctl{
	private Object[] columnNames2 = {"���","ID", "�˺�", "�ǳ�","���","�Ա�","�ܱ�","ע��ʱ��","״̬"};
	public  ManageUsers_ctl(Sql sql,Tables_sql tables_sql) {
		this.sql=sql;
		this.tables_sql=tables_sql;
		vaild = sql.getUsers_sql().getAllUsersNumber("");
		colNumber=9;
		tableModel=new DefaultTableModel(0,colNumber);
		rowData= new  Object[vaild][colNumber];
		columnNames = columnNames2;
		setTableInf();
	}		
	//��ñ�ͷ����
	public Object[][] getRowData(Pageinfo pageinfo,String username) {
		return  tables_sql.manageUsersGetRowData(pageinfo, username); 
	}
	//�����û�
	public void StartFreeze(int id) {
		tables_sql.Freeze(id);
	}
	//��������
	public void startUpdata(JTable table,Pageinfo pageinfo,String username) {
		tables_sql.manageUsersUpdata(table, pageinfo, username, tableInf, tableModel, columnNames2, rowData);
	}
}
