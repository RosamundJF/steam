package controller;
import java.sql.Timestamp;
import model.Users;
import model.sql.Sql;
//��¼Controller
public class Login_ctl {                                                                 										 //���
	//��ʼ���������˺�����
	public int startTestInput(Sql sql,Users users) {
		return sql.getUserlogin_sql().testInput(users);
	}
	//��ʼ��¼
	public int startLogin(Sql sql,Users users,Timestamp curTime,String logip) {
		return sql.getUserlogin_sql().login(users, curTime, logip);
	}
}

