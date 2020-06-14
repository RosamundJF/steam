package model.table;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import model.Model;
import model.sql.Sql;
import tools.Constant;
import tools.Pageinfo;
public class Tables_sql {
	protected Model model;									//����ģ��	
	protected Sql sql;										//����sql���
	public Tables_sql(Model model,Sql sql) {
		this.model=model;
		this.sql=sql;
	}
	//���õ�ǰҳ����������ֵ
	public void updataRow(JTable table,Pageinfo pageinfo,int[] tableInf,DefaultTableModel tableModel,Object[] columnNames,Object[][] rowData) {
		if(pageinfo.getPage()*20>tableInf[0]) {											//��������һҳ
			tableInf[1]=(tableInf[0]-(pageinfo.getPage()-1)*20);									
			tableModel.setRowCount(tableInf[1]);										//�����ģ������
			table.setModel(tableModel);												//�ѱ��ģ�͸�����Ӧ��ͼ���table
		}else {																		//������м��ҳ��
			tableInf[1]=20;
			tableModel.setRowCount(tableInf[1]);
			table.setModel(tableModel);
		}
		//��ʼ����������
		for(int row=0;row<tableInf[1];row++) {
			for(int col=0;col<tableInf[2];col++) {
				table.setValueAt(rowData[row][col], row, col);
			}
		}
	}
	//��������ID�Ƿ���ȷ
	public int testInputId(String id) {
		int res = 0 ; 
		if(id.length()==0) {
			res = 1;
		}else {
			if(!id.matches(Constant.REGEX_ID) || Integer.valueOf(id).intValue()<1 ) {					//IDһ���Ǵ���1�ģ�������ƥ��
				res = 2;
			}
		}
		return res;
	}
	//���ҳ����������ж�
	public int testPage(String id,int page,int vaild) {
		int done = 3;
		if(testInputId(id)==0) {																		//�Ƚ���testInputId
			if(Integer.valueOf(id).intValue()>(int)(vaild/20+1)) {									//���ҳ��������ҳ��
				done = 4;
			}else {
				if(Integer.valueOf(id).intValue()==page) {												//����ڵ�ǰҳ
					done = 5;
				}		
			}
			return done;
		}else {
			return testInputId(id);
		}
	}
	//�ж����������
	public int testSearch(String search) {
		int res = 0;
		if(search.length()==0) {
			res=1;
		}else {
			if(search.length()>15 || search.contains("%") || search.contains(" ")) {
				res=2;
			}
		}
		return res;
	}
	//��ѯID�Ƿ����
		public boolean isUserIdExist(int id) {
			boolean res=false;
			try {
				ResultSet result =(ResultSet)sql.returnRes(false,"select id from users" );
				//�˺Ŵ��ڵĻ�res��true
				while(result.next()) {		
					if(id== result.getInt(1)) {
						res = true;
					}
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return res;
		}

	//�������ϷID�Ƿ�"��"����(����Ա)
	public boolean gameIdExist(String id) {
		boolean res=false;
		if(!sql.getGames_sql().gameIdExist(Integer.valueOf(id).intValue())) {
			res=true;
		}
		return res;
	}
	public void publicUpdata(JTable table,Pageinfo pageinfo,int[] tableInf,DefaultTableModel tableModel,Object[] columnNames,Object[][] rowData) {
		tableModel.setColumnIdentifiers(columnNames);
		updataRow(table, pageinfo, tableInf, tableModel, columnNames, rowData);
		table.updateUI();
	}
	/**
	 * ������Ϸ
	 */
	public Object[][] manageGamesGetRowData(Pageinfo pageinfo,String name,boolean del) {	
		return sql.getGames_sql().getAllGamesData(pageinfo.getPage(),pageinfo.getCategoryid(),name,del);		
	}	
	public void manageGamesUpdata(JTable table,Pageinfo pageinfo,String name,int[] tableInf,DefaultTableModel tableModel,Object[] columnNames,Object[][] rowData,boolean del) {
		tableInf[0]=sql.getGames_sql().getGamesNumber(pageinfo.getCategoryid(),name,del);						//������												
		rowData=manageGamesGetRowData(pageinfo,name,del);	//���������
		publicUpdata(table, pageinfo, tableInf, tableModel, columnNames, rowData);
	}
	/**
	 * �����¼
	 */
	public Object[][] manageLoginGetRowData(Pageinfo pageinfo,int id) {
		return  sql.getUserlogin_sql().getAllLogin(pageinfo.getPage(),id); 
	}
	public void manageLoginUpdata(JTable table,Pageinfo pageinfo,String id,int[] tableInf,DefaultTableModel tableModel,Object[] columnNames,Object[][] rowData) {
		tableInf[0]=sql.getUserlogin_sql().getAllLoginNumber(Integer.valueOf(id).intValue());
		rowData=manageLoginGetRowData(pageinfo,Integer.valueOf(id).intValue());
		publicUpdata(table, pageinfo, tableInf, tableModel, columnNames, rowData);
	}
	/**
	 * ������
	 */
	public Object[][] manageOrdersGetRowData(Pageinfo pageinfo,int id) {
		return  sql.getUserorders_sql().getAllOrders(pageinfo.getPage(),id); 
	}
	public void manageOrdersUpdata(JTable table,Pageinfo pageinfo,String id,int[] tableInf,DefaultTableModel tableModel,Object[] columnNames,Object[][] rowData) {
		tableInf[0]=sql.getUserorders_sql().getAllOrdersNumber(Integer.valueOf(id).intValue());
		rowData=manageOrdersGetRowData(pageinfo,Integer.valueOf(id).intValue());
		publicUpdata(table, pageinfo, tableInf, tableModel, columnNames, rowData);
	}
	/**
	 * �����û�
	 */
	public Object[][] manageUsersGetRowData(Pageinfo pageinfo,String username) {
		return  sql.getUsers_sql().getAllUsers(pageinfo.getPage(),username); 
	}
	//�����û�
	public void Freeze(int id) {
		int state = sql.getUsers_sql().getState(id);			//����û���ǰ��״̬
		if(state==1) state=0;
		else state=1;
		sql.getUsers_sql().freezeUser(id, state);
	}
	public void manageUsersUpdata(JTable table,Pageinfo pageinfo,String username,int[] tableInf,DefaultTableModel tableModel,Object[] columnNames,Object[][] rowData) {	
		tableInf[0]=sql.getUsers_sql().getAllUsersNumber(username);
		rowData=manageUsersGetRowData(pageinfo,username);
		publicUpdata(table, pageinfo, tableInf, tableModel, columnNames, rowData);
	}
	/**
	 * �ҵ���Ϸ
	 */
	public Object[][] MyGamesGetRowData(Pageinfo pageinfo) {
		return sql.getUserorders_sql().getAllMyGames(sql,pageinfo.getPage(), model.getUsers().getId());
	}

	public void MyGamesUpdata(JTable table,Pageinfo pageinfo,String o,int[] tableInf,DefaultTableModel tableModel,Object[] columnNames,Object[][] rowData) {
		tableInf[0]=sql.getUserorders_sql().allGamesNumber(model.getUsers().getId());		//��ø�ҳ������
		rowData=MyGamesGetRowData(pageinfo);										//���������
		publicUpdata(table, pageinfo, tableInf, tableModel, columnNames, rowData);
	}
	/**
	 * �̵�
	 */
	public Object[][] storeGetRowData(Pageinfo pageinfo,String name) {
		return sql.getGames_sql().getAllGamesData(pageinfo.getPage(),pageinfo.getCategoryid(),name,false);
	}
	//��������
	public void storeUpdata(JTable table,Pageinfo pageinfo,String name,int[] tableInf,DefaultTableModel tableModel,Object[] columnNames,Object[][] rowData) {
		tableInf[0]=sql.getGames_sql().getGamesNumber(pageinfo.getCategoryid(),name,false);
		rowData=storeGetRowData(pageinfo,name);																		//���������
		publicUpdata(table, pageinfo, tableInf, tableModel, columnNames, rowData);
	}
	//������Ϸ
	public int buy(int id) {
		int res=0;
		if(sql.getGames_sql().testGame(id)) {													//������Ϸ�Ƿ��¼ܻ�ɾ��
			if(sql.getUserorders_sql().isUserHave(model.getUsers().getId(), id)) {				//�Ƿ���ӵ��
				if(model.getUsers().getMoney()>sql.getGames_sql().getPrice(id)) {				//����Ƿ����
					//���ù��򷽷�
					sql.getUserorders_sql().buy(model.getUsers().getId(), id, sql.getGames_sql().getPrice(id),model.getUsers().getMoney(), new Timestamp(System.currentTimeMillis()));
					model.getUsers().setMoney(model.getUsers().getMoney()-sql.getGames_sql().getPrice(id));
					res=2;
				}else res = 1;
			}
		}else res=3;
		return res;
	}
	//�������ϷID�Ƿ�"��"����
	public boolean storeGameIdExist(String id) {
		boolean res=false;
		if(!sql.getGames_sql().gameIdExist(Integer.valueOf(id).intValue())) {
			res=true;
		}
		return res;
	}
}
