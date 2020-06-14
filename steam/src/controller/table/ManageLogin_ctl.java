package controller.table;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import model.sql.Sql;
import model.table.Tables_sql;
import tools.Pageinfo;
//��½��¼TableController
public class ManageLogin_ctl extends Tables_ctl{
	//��ͷ
	private Object[] columnNames2 = {"���","��¼ID", "�û�ID", "�û��˺�", "��¼ʱ��", "��¼IP"};
	//����
	public ManageLogin_ctl(Sql sql,Tables_sql tables_sql) {
		this.sql=sql;
		this.tables_sql=tables_sql;
		vaild = sql.getUserlogin_sql().getAllLoginNumber(0);
		colNumber=6;
		tableModel=new DefaultTableModel(0,colNumber);
		rowData= new  Object[vaild][colNumber];
		columnNames = columnNames2;
		setTableInf();
	}		
	//ȡ��������
	public Object[][] getRowData(Pageinfo pageinfo,int id) {
			return  tables_sql.manageLoginGetRowData(pageinfo, id);
	}
	//��������
	public void startUpdata(JTable table,Pageinfo pageinfo,String id) {
		tables_sql.manageLoginUpdata(table, pageinfo, id, tableInf, tableModel, columnNames2, rowData);
	}
}
