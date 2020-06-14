package controller;
import model.Model;
import model.sql.Sql;
//�����ϷView������Ӧ��controller
public class AddGame_ctl {
	private Model model;
	private Sql sql;
	//����
	public AddGame_ctl(Model model,Sql sql) {
		this.model=model;
		this.sql=sql;
	}
	//��ʼ����������Ϸ
	public int startTestGame(String name,String price) {
		return sql.getGames_sql().testGame(name, price);
	}
	//����sql.getGames_sql()�е���ӷ���������Ϊgameģ��
	public void startAdd() {
		sql.getGames_sql().addGame(model.getGames());
	}
}
