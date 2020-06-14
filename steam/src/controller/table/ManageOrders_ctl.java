package controller.table;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import tools.Pageinfo;
import model.sql.Sql;
import model.table.Tables_sql;
//������TableController
public class ManageOrders_ctl extends Tables_ctl{
	private Object[] columnNames2 = {"���","����ID", "�û�ID","�û��˺�", "��Ϸ����", "�����۸�", "����ʱ��"};
	public  ManageOrders_ctl(Sql sql,Tables_sql tables_sql){
		this.sql=sql;
		this.tables_sql=tables_sql;
		vaild = sql.getUserorders_sql().getAllOrdersNumber(0);
		colNumber=7;
		tableModel=new DefaultTableModel(0,colNumber);
		rowData= new  Object[vaild][colNumber];
		columnNames = columnNames2;
		setTableInf();
	}		
	//��ñ�ͷ����
	public Object[][] getRowData(Pageinfo pageinfo,int id) {
			return  tables_sql.manageOrdersGetRowData(pageinfo, id); 
	}
	//��������
	public void startUpdata(JTable table,Pageinfo pageinfo,String id) {
		tables_sql.manageOrdersUpdata(table, pageinfo, id, tableInf, tableModel, columnNames2, rowData);
	}
	
}
