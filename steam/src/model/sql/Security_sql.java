package model.sql;
//���ܱ����йص�sql���
public class Security_sql {
	private Sql sql;
	public Security_sql(Sql sql) {
		this.sql=sql;
	}
	//��ȡĳ���ܱ�����
	public String getSecurity(int id) {	
		return (String)sql.returnRes(true, "select securityquestion from security where id = ?",id);
	}
}
