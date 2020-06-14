package model.sql;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

import model.Users;
import tools.Constant;
//���û���¼���йص�sql���
public class Userlogin_sql {
	private Object[][] rowData; 
	private Sql sql;
	public Userlogin_sql(Sql sql) {
		this.sql=sql;
	}
	//���������˺�����
	public int testInput(Users users) {
		int res = 0; 
		if(users.getAccount().length()==0 || users.getPassword().length() == 0) {
			res = 1;
		}else {
			if(!users.getAccount().matches(Constant.REGEX_NUM) || !users.getPassword().matches(Constant.REGEX_NUM)) {
				res = 2;
			}
		}
		return res;
	}
	//��¼
	public int login(Users users,Timestamp curTime,String logip) {
		int res = 0; 
		if(sql.getUsers_sql().testAccountExist(users.getAccount())) {															//�ȼ���˺��Ƿ����
			if(users.getIdentity()==sql.getUsers_sql().getIdentity(users.getAccount())) {										//�ڼ������Ƿ���ȷ
				if(sql.getUsers_sql().md5(users.getPassword()).equals(sql.getUsers_sql().getPassword(users.getAccount()))) {	//�ټ�������Ƿ���ȷ
					if(sql.getUsers_sql().getState(sql.getUsers_sql().getId(users.getAccount()))==1) {							//�ټ���˺��Ƿ񱻶���
						if(sql.getUsers_sql().getLogging(sql.getUsers_sql().getId(users.getAccount()))==0) {					//�ټ���˺��Ƿ��Ѿ���¼
							res=1;
							//��ʣ���û���Ϣ����
							users.setId(sql.getUsers_sql().getId(users.getAccount()));
							users.setSecurityId(sql.getUsers_sql().getSecurityId(users.getAccount()));
							users.setSecurityAnswer(sql.getUsers_sql().getSecurityAnswer(users.getAccount()));
							users.setSex(sql.getUsers_sql().getSex(users.getAccount()));
							users.setUsername(sql.getUsers_sql().getName(users.getAccount()));
							users.setMoney(sql.getUsers_sql().getMoney(users.getId()));
							sql.getGames_sql().setIdentity(users.getIdentity());												//����ҵ���ݴ���ȥ	
							sql.getUserlogin_sql().insertLogin(users.getIdentity(),users.getId(), curTime, logip);				//�ѵ�¼����Ϣ��¼�ڵ�¼����
							sql.getUsers_sql().setLogging(users.getId(),1);														//�ı���ҵĵ�¼״̬
						}else {
							res=5;
						}
					}else {
						res=4;
					}
				}else {
					res=2;
				}
			}else {
				res=3;
			}
		}
		return res;
	}
	//д���¼��¼
	public void insertLogin(int iden,int userId,Timestamp curTime,String ip) {
			sql.write("insert into userlogin(Identity,userid,logtime,logip) values(?,?,?,?)", iden,userId,sql.dateToInt(curTime),ip);
	}
	//��ȡȫ��¼����
	public int getAllLoginNumber(int id) {
		int number=0;
		ResultSet res=null;
		try {
			if(id==0) res=(ResultSet)sql.returnRes(false, "select count(*) from userlogin where identity = 1");
			else res=(ResultSet)sql.returnRes(false, "select count(*) from userlogin where identity = 1 and userid = ?",id); 
			res.next();
			number = res.getInt(1);
		} catch (SQLException e) {
			e.printStackTrace();
		}	
		return number ;
	}
	//��ȡȫ����¼����
	public Object[][] getAllLogin(int page,int id){
		ResultSet res=null;
		rowData= new Object[getAllLoginNumber(id)][6];
		int number=(page-1)*20+1,loginnumber = getAllLoginNumber(id),row=0;
		if(id==0) {
			if(page*20>loginnumber) {
				res=(ResultSet)sql.returnRes(false, "select * from userlogin where identity = 1 limit ?,?", (page-1)*20, (loginnumber-(page-1)*20));
			}else res=(ResultSet)sql.returnRes(false, "select * from userlogin where identity = 1 limit ?,?", (page-1)*20, 20);
		}
		else {
			if(page*20>loginnumber) {
				res=(ResultSet)sql.returnRes(false, "select * from userlogin where identity = 1 and userid = ? limit ?,? ",id, (page-1)*20, (loginnumber-(page-1)*20));
			}else res=(ResultSet)sql.returnRes(false,"select * from userlogin where identity = 1 and userid = ? limit ?,? ", id,(page-1)*20, 20);
		}
		try {
			while(res.next()) {	
				rowData[row][0] = number ;
				rowData[row][1] = res.getInt(1);
				rowData[row][2] = res.getInt(3);
				rowData[row][3] = sql.getUsers_sql().getAccount(res.getInt(3));
				rowData[row][4] = sql.intToDate(res.getString(4));
				rowData[row][5] = res.getString(5);
				row++;
				number++;
			}
		} catch (SQLException e) {

			e.printStackTrace();
		}	
		return rowData;
	}
}
