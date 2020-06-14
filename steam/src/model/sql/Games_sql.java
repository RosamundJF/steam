package model.sql;
import java.sql.ResultSet;
import java.sql.SQLException;

import model.Games;
import tools.Constant;
//����Ϸ���йص�sql��Ϸ
public class Games_sql{
	private Sql sql;
	private int identity,colNumber;
	private Object[][] rowData;
	private Games games;
	public Games_sql(Sql sql,Games games) {
		this.sql=sql;
		if(identity==1) colNumber=7;									
		else colNumber=8;
		this.games=games;
	}
	public void setIdentity(int identity) {
		this.identity = identity;
	}
	//�����Ϸ����
	public String getName(int id){
		return (String)sql.returnRes(true, "select name from games where id = ?",id);
	}
	//�����Ϸ�ϼ�ʱ��
	public long getRegtimeInt(int id) {
		return (long)sql.returnRes(true, "select regtime from games where id = ?",id);
	}
	public String getRegtime(int id){
		return (String)sql.returnRes(true, "select FROM_UNIXTIME(regtime,'%Y��%m��%d') from games where id = ?",id);
	}
	//�����ϷID
	public int getId(String name){
		return (int)sql.returnRes(true, "select id from games where name = ?",name);
	}
	//�����Ϸ����
	public int getCategoryId(int id){
		return (int)sql.returnRes(true, "select categoryid from games where id = ?",id);
	}
	//�����Ϸ�۸�
	public int getPrice(int id){
		return (int)sql.returnRes(true, "select price from games where id =  ?",id);
	}
	//�����Ϸ״̬
	public int getState(int id){
		int state=1;
		ResultSet res = (ResultSet)sql.returnRes(false,"select state from games where id = ?",id);
		try {
			res.next();
			state=res.getInt(1);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return state;
	}
	public void recoverState(int id) {
		sql.write("UPDATE games SET state=? WHERE id=?", 0,id);
	}
	//�����Ϸ�ĸ���
	public int getGamesNumber(int categoryid,String name,boolean del) {
		int number=0;
		ResultSet res=null;
		try {
			if(categoryid==0) {
				if(del) {
					res =(ResultSet)sql.returnRes(false,"select count(*) from games where state = '2' and name like '%"+name+"%'"); 
				}else {
				if(identity==1) res =(ResultSet)sql.returnRes(false,"select count(*) from games where state = '1' and name like '%"+name+"%'");      //�������¼ܣ�
				else res =(ResultSet)sql.returnRes(false,"select count(*) from games where state not in ('2') and name like '%"+name+"%'");								}//���¼�
			}else {
				if(del) {
					res =(ResultSet)sql.returnRes(false,"select count(*) from games where state = '2' and name like '%"+name+"%' and categoryid=?",categoryid); 
				}else {
				if(identity==1) 	
					res =(ResultSet)sql.returnRes(false,"select count(*) from games where state = '1' and name like '%"+name+"%' and categoryid=?",categoryid);//�����¼�
				else res =(ResultSet)sql.returnRes(false,"select count(*) from games where state not in ('2') and name like '%"+name+"%' and categoryid= ? ",categoryid);}//���¼�
			}
			res.next();
			number=res.getInt(1);
		} catch (SQLException e) {

			e.printStackTrace();
		}	
		return number ;
	}
	//���ȫ������Ϸ���ݣ������Ƿ�Ϊ����Ա���Ƿ�����ѯ���Ƿ��������ģ����ѯ���Ƿ�鿴��ɾ����Ϸ���Ƿ�ﵽ���һҳ������12�������
	public Object[][] getAllGamesData(int page,int categoryid,String name,boolean del){
		ResultSet res;
		rowData = new Object[getGamesNumber(categoryid, name,del)][colNumber];
		int vaild=getGamesNumber(categoryid,name,del),row=0,number=(page-1)*20+1;
		if(categoryid==0) {
			if(page*20>vaild) {
				if(del) {
					res=(ResultSet)sql.returnRes(false,"select * from games where state = '2' and name like '%"+name+"%' limit ?,?",(page-1)*20,(vaild-(page-1)*20));
				}else {
					if(identity==1) res=(ResultSet)sql.returnRes(false,"select * from games where state = '1' and name like '%"+name+"%' limit ?,?",(page-1)*20,(vaild-(page-1)*20));			//�����¼�								
					else res=(ResultSet)sql.returnRes(false,"select * from games where state not in ('2') and name like '%"+name+"%' limit ?,?",(page-1)*20,(vaild-(page-1)*20));	}//���¼�	
			}else {
				if(del) {
					res=(ResultSet)sql.returnRes(false,"select * from games where state = '2' and name like '%"+name+"%' limit ?,?",(page-1)*20,20);
				}else {
					if(identity==1) res=(ResultSet)sql.returnRes(false,"select * from games where state = '1' and name like '%"+name+"%' limit ?,?",(page-1)*20,20);			//�����¼�								
					else res=(ResultSet)sql.returnRes(false,"select * from games where state not in ('2') and name like '%"+name+"%' limit ?,?",(page-1)*20,20);}	//���¼�	
			}
		}else {
			if(page*20>vaild) {
				if(del) {
					res=(ResultSet)sql.returnRes(false,"select * from games where state = '2' and categoryid=? and name like '%"+name+"%' limit ?,?",categoryid,(page-1)*20,(vaild-(page-1)*20));	
				}else {
					if(identity==1)  res=(ResultSet)sql.returnRes(false,"select * from games where state = '1' and categoryid=? and name like '%"+name+"%' limit ?,?",categoryid,(page-1)*20,(vaild-(page-1)*20));	//�����¼�
					else 	res=(ResultSet)sql.returnRes(false,"select * from games where state not in ('2') and categoryid=? and name like '%"+name+"%' limit ?,?",categoryid,(page-1)*20,(vaild-(page-1)*20));	}//���¼�		
			}else {
				if(del) {
					 res=(ResultSet)sql.returnRes(false,"select * from games where state = '2' and categoryid=? and name like '%"+name+"%' limit ?,?",categoryid,(page-1)*20,20);
				}else {
					if(identity==1)  res=(ResultSet)sql.returnRes(false,"select * from games where state = '1' and categoryid=? and name like '%"+name+"%' limit ?,?",categoryid,(page-1)*20,20);	//�����¼�
					else 	res=(ResultSet)sql.returnRes(false,"select * from games where where state not in ('2') and categoryid=? and name like '%"+name+"%' limit ?,?",categoryid,(page-1)*20,20);	}//���¼�		
			}
		}

		try {
			while(res.next()) {
				rowData[row][0] = number ;
				rowData[row][1] = res.getInt(1);
				rowData[row][2] = res.getString(2);
				rowData[row][3] = sql.getCategory_sql().getCategory(res.getInt(3));
				rowData[row][4] = res.getInt(4);
				rowData[row][5] = getRegtime(res.getInt(1));
				rowData[row][6] = sql.getUserorders_sql().sellNumber(res.getInt(1));
				if(identity==0 && !del) {
					if(res.getInt(6)==1)
						rowData[row][7] = "����";
					else rowData[row][7] = "���¼�";
				}else	rowData[row][7] = "��ɾ��";
				row++;
				number++;
			}
		} catch (SQLException e) {

			e.printStackTrace();
		}	
		return rowData;
	}
	//����Ա�޸���Ϸ
	public void modifyGame(Games games) {
		sql.write("UPDATE games SET price=?,state=? WHERE id=?", games.getPrice(),games.getState(),games.getId());
		sql.write("UPDATE userorders SET state=? WHERE id=?", games.getState(),games.getId());
	}
		//����Աɾ����Ϸ
		public void deletGame(int id) {	
			sql.write("DELETE from games WHERE id=?",id);
		}
	//����Ա�����Ϸ�������Ϸ�Ƿ��ظ�
	public boolean testRepatition(String name) {
		boolean res = false ;
		try {
			//��ʼ������Ϸ�Ƿ��ظ�
			ResultSet resulet = (ResultSet)sql.returnRes(false, "SELECT name FROM games");
			while(resulet.next()) {
				if(resulet.getString("name").equals(name)) {
					res =  true;
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return res;
	}
	//����Ա�����Ϸ
	public void addGame(Games games) {		
		sql.write("insert into games(name,categoryid,price,regtime,state) values(?,?,?,?,?)",  games.getName(),games.getCategoryid(),games.getPrice(),
				sql.dateToInt(games.getRegtime()),games.getState());	
	}
	//������Ϸ�Ƿ����¼ܻ�ɾ������ң�
	public boolean testGame(int id) {
		boolean res=false;
		int gameid;
		try {
			ResultSet resulet = (ResultSet)sql.returnRes(false, "select id from games WHERE state=1");
			//�˺Ŵ��ڵĻ�res��true
			while(resulet.next()) {
				gameid = resulet.getInt("id");		
				if(gameid==id) {
					res = true;
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return res;
	}
	//�����ϷID�Ƿ����(����Ա)������Ϊtrue
	public boolean gameIdExist(int id) {
		boolean res=false;
		try {
			ResultSet resulet = (ResultSet)sql.returnRes(false, "select * from games");
			while(resulet.next()) {		
				if(id==resulet.getInt("id")) {
					res = true;
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return res;
	}

	//����������Ϸ
	public int testGame(String name,String price) {
		int res = 0;
		if(price.length()==0 || name.length()==0) {
			res = 1;
		}else {
			if(!price.matches(Constant.REGEX_PRICE)) {
				res = 2;
			}else {
				if(testRepatition(name)) {
					res = 3;
				}else {
					if(name.contains(" ") || name.contains("%")) {
						res = 4;
					}

				}
			}
		}
		return res;
	}
	//α-ɾ��Ϸ
	public int fakeDelet() {
		int res = 1;
		if(games.getId()==78) {         
			res = 2;
		}else {
			if( sql.getUserorders_sql().sellNumber(games.getId())==0) {
				sql.write("UPDATE games SET state=2 WHERE id=?", games.getId());
				res = 0;
			}
		}
		return res;
	}
	//�������ļ۸�
	public int testInput(String price,int state){
		int res =0;
		if(!price.matches(Constant.REGEX_PRICE)) {
			res = 1;
		}else {
			if(price.length()==0) {
				res = 2;
			}else {
				//�ж��Ƿ��������޸�
				if(Integer.valueOf(price).intValue()== getPrice(games.getId()) && state == getState(games.getId())) {
					res = 3;
				}else {
					if(games.getId()==78) {
						res = 4;
					}
				}
			}	
		}
		return res;
	}
}
