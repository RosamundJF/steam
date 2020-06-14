package controller.table;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import model.Model;
import tools.Pageinfo;
import model.sql.Sql;
import model.table.Tables_sql;
//�ҵ���ϷTableController
public class MyGames_ctl extends Tables_ctl{
	private Object[] columnNames2 = {"���","��ϷID", "����", "����", "�۸�", "����ʱ��","״̬"};		//��ͷ
	//����
	public MyGames_ctl(Model model,Sql sql,Tables_sql tables_sql) {
		this.model=model;
		this.sql=sql;
		this.tables_sql=tables_sql;
		vaild = sql.getUserorders_sql().allGamesNumber(model.getUsers().getId());				//���ݶ�����ѯ�������Ϸ����
		colNumber=7;																			//����
		tableModel=new DefaultTableModel(0,colNumber);
		rowData= new  Object[vaild][colNumber];
		columnNames = columnNames2;
		setTableInf();
	}
	//���²鵽������
	public void updateVaild() {
		vaild = sql.getUserorders_sql().allGamesNumber(model.getUsers().getId());		
	}
	//����������
	public Object[][] getRowData(Pageinfo pageinfo) {
		return tables_sql.MyGamesGetRowData(pageinfo);
	}
	//��������
	public void startUpdata(JTable table,Pageinfo pageinfo,String o) {
		tables_sql.MyGamesUpdata(table, pageinfo, o, tableInf, tableModel, columnNames2, rowData);									//ˢ�¸ñ��
	}
}
