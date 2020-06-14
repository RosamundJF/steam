package controller.table;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import tools.Pageinfo;
import model.sql.Sql;
import model.table.Tables_sql;

//������ϷTableController
public class ManageGames_ctl extends Tables_ctl{
	private Object[] columnNames2 = {"���","��ϷID", "����", "����", "�۸�", "�ϼ�����","��������","״̬"};			//��ͷ
	private boolean del=false;																					//�Ƿ�鿴��ɾ������Ϸ
	public ManageGames_ctl(Sql sql,Tables_sql tables_sql) {
		this.sql=sql;
		this.tables_sql=tables_sql;
		vaild = sql.getGames_sql().getGamesNumber(0,"",false);														//��ʼ����
		colNumber=8;																								//����
		tableModel=new DefaultTableModel(0,colNumber);																//Tableģ��
		rowData= new  Object[vaild][colNumber];																		//������
		columnNames = columnNames2;																					//��ͷ
		setTableInf();																								//���ñ�Ļ�������
	}	
	public void setDel(boolean del) {
		this.del=del;
	}
	public Boolean getDel() {
		return del;
	}
	//��ñ�ͷ��Ϣ
	public Object[][] getRowData(Pageinfo pageinfo,String name) {
		return tables_sql.manageGamesGetRowData(pageinfo, name,del);
	}	
	//����table����
	public void startUpdata(JTable table,Pageinfo pageinfo,String name) {
		tables_sql.manageGamesUpdata(table, pageinfo, name, tableInf, tableModel, columnNames2, rowData,del);
	}
	//�ָ���Ϸ״̬Ϊ0
	public void recoverGame(int id) {
		sql.getGames_sql().recoverState(id);
	}
	//������ɾ��
	public void realDelete(int id) {
		sql.getGames_sql().deletGame(id);
	}
}
