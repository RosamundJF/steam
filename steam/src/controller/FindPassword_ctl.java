package controller;
import model.sql.Sql;
//�һ�����Controller
public class FindPassword_ctl {
	private Sql sql;	
	public FindPassword_ctl(Sql sql) {
		this.sql=sql;
	}
	//��ʼ�˶��ܱ�
	public int startFind(String act,int securityId,String securityAnswer) {	
		return sql.getUsers_sql().Find(act, securityId, securityAnswer);
	}
	//��ʼ��������
	public void startReplace(String act,String pwd) {
		sql.getUsers_sql().replacePassword(act, pwd);
	}
	//��ʼ�����������������ʽ
	public int startTestInput(String replace,String replace2) {
		return sql.getUsers_sql().testInput(replace, replace2);
	}
	//��ʼ���������ܱ��𰸸�ʽ
	public int startTestAnswer(String asw) {
		return sql.getUsers_sql().testAnswer(asw);
	}
	//��ʼ��������ܱ�ǰ�Ƿ��������˺�
	public int startTestInputAct(String act) {
		return sql.getUsers_sql().testInputAct(act);
	}
	//��ʼ����������֤��
	public int startTestSecurityCode(String code) {
		return sql.getUsers_sql().testSecurityCode(code);
	}
}
