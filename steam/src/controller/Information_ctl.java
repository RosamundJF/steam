package controller;
import model.Users;
import model.sql.Sql;
//��Ϣ����Controller
public class Information_ctl {
	private Sql sql;
	public Information_ctl(Sql sql) {
		this.sql=sql;
	}
	//��ֵ���Ʒ���
	public void Recharge(Users users,int money) {
		//�����ݿ��е�����޸�
		sql.getUsers_sql().recharge(users,money);
	}

}
