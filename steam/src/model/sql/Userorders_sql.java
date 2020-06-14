package model.sql;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
//���û������йص�sql���
public class Userorders_sql {
	private Object[][] rowData1;
	private Object[][] rowData2;
	private Sql sql;
	public Userorders_sql(Sql sql) {
		this.sql=sql;
	}
	//������Ϸ��ʱ��
	public String getBuyTime(int userid,int gameid) {	
		return (String)sql.returnRes(true, "select FROM_UNIXTIME(buytime,'%Y��%m��%d') from userorders where userid = ? and gameid = ?", userid,gameid);
	}
	//����Ϸ���۵ĸ���
	public int sellNumber(int gameid) {
		int res = 0;
		try {
			ResultSet result = (ResultSet)sql.returnRes(false,"select count(*) from userorders where gameid = ?", gameid);
			if(result!=null) {
				result.next();
				res = result.getInt(1);
			}
		} catch (SQLException e) {

			e.printStackTrace();
		}	
		return res;
	}
	//��ѯ����Ƿ�ӵ�и���Ϸ
	public boolean isUserHave(int userid,int gameid) {
		boolean res =true;
		int haveGameId;
		try {
			ResultSet result = (ResultSet)sql.returnRes(false,"select gameid from userorders where userid = ?", userid);
			b:while(result.next()) {
				haveGameId=result.getInt("gameid");
				if(gameid==haveGameId) {
					res=false;
					break b;
				}
			}
		} catch (SQLException e) {

			e.printStackTrace();
		}	
		return res;
	}
	//������Ϸ
	public void buy(int userid,int gameid,int price,int userMoney,Timestamp curTime) {
			sql.write("insert into userorders(userid,gameid,price,buytime) values(?,?,?,?)", userid,gameid,price,sql.dateToInt(curTime));
			sql.write("update users set money = ? where id = ?", (userMoney-price),userid);	
	}
	//��ѯ�ҵ�������Ϸ����
	public int allGamesNumber(int id) {
		int number=0;
		try {	
			ResultSet result = (ResultSet)sql.returnRes(false, "SELECT count(*) from userorders where userid = ?", id);
			result.next();
			number =result.getInt(1);
		} catch (SQLException e) {
			e.printStackTrace();
		}	
		return number ;
	}
	//��ȡ�ҵ�������Ϸ���ҵ���Ϸ��
	public Object[][] getAllMyGames(Sql sql,int page,int userid){
		ResultSet result;
		int number=(page-1)*20+1,row=0,vaild=allGamesNumber(userid);
		if(vaild<20) {
			rowData1 = new Object[vaild][7];
		}else {
			rowData1 = new Object[20][7];
		}
		try {
			if(page*20>vaild) {
				result=(ResultSet)sql.returnRes(false, "select gameid from userorders where userid= ? limit ?,?", userid,(page-1)*20,(vaild-(page-1)*20));
			}else 	result=(ResultSet)sql.returnRes(false, "select gameid from userorders where userid= ? limit ?,?", userid,(page-1)*20,20);
			while(result.next()) {
				rowData1[row][0] = number;
				rowData1[row][1] = result.getInt(1);
				rowData1[row][2] = sql.getGames_sql().getName(result.getInt(1));
				rowData1[row][3] = sql.getCategory_sql().getCategory(sql.getGames_sql().getCategoryId(result.getInt(1)));
				rowData1[row][4] = sql.getGames_sql().getPrice(result.getInt(1));
				rowData1[row][5] = getBuyTime(userid,result.getInt(1));
				if(sql.getGames_sql().getState(result.getInt(1))==1) {
					rowData1[row][6] = "����";
				}else {
					rowData1[row][6] = "��ʱ�¼�";
				}
				row++;
				number++;
			}
		} catch (SQLException e) {

			e.printStackTrace();
		}	
		return rowData1;
	}
	//��ȡȫ����������
	public int getAllOrdersNumber(int id) {
		ResultSet res;
		int number=0;
		if(id==0) res=(ResultSet)sql.returnRes(false,"select count(*) from userorders ") ;
		else res=(ResultSet)sql.returnRes(false,"select count(*) from userorders where userid = ?",id) ; 
		try {
			res.next();
			number=res.getInt(1);
		} catch (SQLException e) {
			e.printStackTrace();
		}	
		return number ;
	}
	//��ȡȫ����������(����Ա)��������
	public Object[][] getAllOrders(int page,int id){
		ResultSet res;
		rowData2 = new Object[getAllOrdersNumber(id)][7];
		int number=(page-1)*20+1,ordersnumber = getAllOrdersNumber(id),row=0;
		if(id==0) {
			if(page*20>ordersnumber) {
				res = (ResultSet)sql.returnRes(false, "select * from userorders limit ?,?", (page-1)*20,(ordersnumber-(page-1)*20));
			}else res = (ResultSet)sql.returnRes(false, "select * from userorders limit ?,?", (page-1)*20,20);
		}else {
			if(page*20>ordersnumber) {
				res = (ResultSet)sql.returnRes(false,"select * from userorders where userid = ? limit ?,? ", id,(page-1)*20,(ordersnumber-(page-1)*20));
			}else res = (ResultSet)sql.returnRes(false,"select * from userorders where userid = ? limit ?,? ", id,(page-1)*20,20);
		}
			try {
				while(res.next()) {	
					rowData2[row][0] = number ;
					rowData2[row][1] = res.getInt(1);
					rowData2[row][2] = res.getInt(2);
					rowData2[row][3] = sql.getUsers_sql().getAccount(res.getInt(2));
					rowData2[row][4] = sql.getGames_sql().getName(res.getInt(3));
					rowData2[row][5] = res.getInt(4);
					rowData2[row][6] = sql.intToDate(res.getString(5));
					row++;
					number++;
				}
			} catch (SQLException e) {
				// TODO �Զ����ɵ� catch ��
				e.printStackTrace();
			}
		
		return rowData2;
	}
}
