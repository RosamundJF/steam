package model.sql;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
//�����ݿ�����
public class GetConn_sql {
	private static String driver="com.mysql.cj.jdbc.Driver";
	private static String url ="jdbc:mysql://localhost:3306/steam2?characterEncoding=utf8&useSSL=false&serverTimezone=UTC&rewriteBatchedStatements=true&allowPublicKeyRetrieval=true";
	private static String user="root";
	private static String pwd="12345";
	private Connection conn ;     
	//�������ӣ����Ӷ����ڲ���ʵ������Socket������һ��Զ�����ӡ��ȽϺ�ʱ������Connection��������һ��Ҫ�� ��
	// �����Ŀ����У�Ϊ�����Ч�ʣ����������ӳ����������Ӷ���
	//�������ݿ�
	public GetConn_sql() {
		try {
			Class.forName(driver);
			conn = DriverManager.getConnection(url, user, pwd);
			if(conn!=null) {
				System.out.println("���ݿ����ӳɹ�");
			}
			conn.setAutoCommit(false);										//ȡ���Զ��ύ
		}catch(Exception e) {
			e.printStackTrace();
			try {
				conn.rollback();											//��������ʱ�ع�
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}
	}  
	
	public Connection getConn() {
		return conn;
	}
}
