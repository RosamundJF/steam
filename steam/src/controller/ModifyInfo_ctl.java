package controller;

import model.Model;
import model.sql.Sql;
//�޸���ϢController
public class ModifyInfo_ctl {
	private Model model;
	private Sql sql;
	public ModifyInfo_ctl(Model model, Sql sql) {
		this.model=model;
		this.sql=sql;
	}
	//��ʼ����Ϣ
	public void startModify() {
		sql.getUsers_sql().modifyInfo(model.getUsers());
	}
	//��ʼ����������ƺ��Ա�
	public int testInput(String nam,int sex) {
		return sql.getUsers_sql().testInput(nam, sex);
	}
	//��ʼ������֤��
	public void startSend() {
		sql.getUsers_sql().send();
	}
	//��ʼ�������ľ������������
	public int startTestReplacePwd(String oldPwd,String newPwd) {
		return sql.getUsers_sql().testReplacePwd(oldPwd, newPwd);
	}
	//��ʼ����������֤��
	public int startTestCode(String yzm) {
		return sql.getUsers_sql().testCode(yzm);
	}
	//��ʼ���������ܱ�
	public int startTestASW(String asw) {
		return sql.getUsers_sql().testASW(asw);
	}
	
}
