package controller;

import model.Model;
import model.sql.Sql;
//�޸���ϷController
public class ModifyGames_ctl {
	private Sql sql;
	private Model model;
	public ModifyGames_ctl(Model model, Sql sql) {
		this.sql=sql;
		this.model=model;
	} 
	//��ʼ����Ϸ
	public void startModify() {
		sql.getGames_sql().modifyGame(model.getGames());
	}
	//��ʼɾ��Ϸ
	public int startDelet() {
		return sql.getGames_sql().fakeDelet();
	}
	//��ʼ�������ļ۸�
	public int startTestInput(String price,int state){
		return sql.getGames_sql().testInput(price, state);
	}
}
