package model.sql;
import java.sql.ResultSet;
import java.sql.SQLException;
//�����ͱ��йص�sql���
public class Category_sql {
	private String[] allCategories;
	private Sql sql;
	public Category_sql(Sql sql) {
		this.sql=sql;
	}
	//������͵ĸ���
	public int getCategoryNumber() {
		ResultSet res;
		int a =0;
		try {
			res = (ResultSet)sql.returnRes(false,"select count(*) from category");
			res.next();
			a=res.getInt(1);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return a ;
	}
	//���ĳ����������
	public String getCategory(int id) {
		return (String)sql.returnRes(true, "select categories from category where id = ?",id);
	}
	//��ȡ������������ַ�������
	public String[] getAllCategories() {
		allCategories=new String[getCategoryNumber()];
		int t=0;
		try {
			ResultSet res=(ResultSet)sql.returnRes(false,"select categories from category");
			while(res.next()) {
				allCategories[t]=res.getString("categories");
				t++;
			}
		} catch (SQLException e) {

			e.printStackTrace();
		}	
		return allCategories;
	}
}
