package controller;
import java.sql.Timestamp;
import model.sql.Sql;
//ע��Controller
public class Register_ctl {
	Sql sql;
	//����
	public Register_ctl(Sql sql) {
		this.sql=sql;
	}
	//��ʼע��
	public void startRegister(String act,String pwd,String nam,int sex,int securityId,String securityAnswer,Timestamp curTime) {
		sql.getUsers_sql().register(act, pwd, nam, sex, securityId, securityAnswer, curTime);
	}
	//��ʼ�������������
	public int startTestInput(String act,String nam,String pwd,String pwd2,String asw) {
		return sql.getUsers_sql().testInput(act, nam, pwd, pwd2, asw);
	}
	//��ʼ����������֤��
	public int startTestSecurityCode(String code) {
		return sql.getUsers_sql().testSecurityCode(code);
	}
}
