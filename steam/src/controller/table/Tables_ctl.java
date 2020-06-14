package controller.table;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import model.Model;
import model.sql.Sql;
import model.table.Tables_sql;
import tools.Pageinfo;
/*
 * ������������table������ͼ���controller�ĸ��࣬Ϊ�����࣬���а�������table������ͼ���controller�󲿷ֳ�Ա�����뷽��
 * 
 */
public abstract class Tables_ctl {
	protected Model model;									//����ģ��	
	protected Sql sql;										//����sql���
	protected int vaild,rowNumber,colNumber;				//��ҳ�Ĳ�ѯ������Ч������������ý�飩������
	protected Object[] columnNames;							//��ͷ
	protected Object[][] rowData;							//�������
	protected DefaultTableModel tableModel;					//���ģ��
	protected Tables_sql tables_sql ;
	protected int[] tableInf=new int[3];
	//���ñ���������
	public void setTableInf() {
		tableInf[0]=vaild;
		tableInf[1]=rowNumber;
		tableInf[2]=colNumber;
	}
	//���ر�ͷ
	public Object[] getColumnNames() {						
		return columnNames;
	}
	//���ظ�ҳ��ѯ������Ч����
	public int getVaild() {
		return tableInf[0];
	}
	
	//���ظ�table�ı��ģ��
	public DefaultTableModel getTableModel() {
		return tableModel;
	}
	//���õ�ǰҳ����������ֵ
	public void updataRow(JTable table,Pageinfo pageinfo) {
		tables_sql.updataRow(table, pageinfo,tableInf, tableModel, columnNames, rowData);
	}
	//��������ID�Ƿ���ȷ
	public int testInputId(String id) {
		return tables_sql.testInputId(id);
	}
	//���ҳ����������ж�
	public int testPage(String id,int page) {
		return tables_sql.testPage(id, page,getVaild());
	}
	//�ж����������
	public int testSearch(String search) {
		return tables_sql.testSearch(search);
	}
	//������û�ID�Ƿ����(����Ա)
	public boolean userIdExist(String id) {
		return tables_sql.isUserIdExist(Integer.valueOf(id).intValue());
	}
	//�������ϷID�Ƿ�"��"����(����Ա)
	public boolean gameIdExist(String id) {
		return tables_sql.gameIdExist(id);
	}
	//����ÿ�����Ʋ��updata�����岻һ������ͼ�㸸��Viewer��Ҫ�õ���Controller�е�������������Խ��÷�������Ϊ����
	public abstract void startUpdata(JTable table,Pageinfo pageinfo,String str_search) ;

}


