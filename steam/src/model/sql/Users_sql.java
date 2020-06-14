package model.sql;
import java.security.GeneralSecurityException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import model.Users;
import tools.Constant;
import tools.SendEmail;
//���û��йص�sql���
public class Users_sql { 
	private Sql sql;
	private SendEmail sendemail;
	private Object[][] rowData;
	private Users users;
	public Users_sql(Sql sql,Users users) {
		this.sql=sql;
		sendemail = new SendEmail(sql);
		this.users=users;
	}
	//��ȡID
	public int getId(String act) {
		return (int)sql.returnRes(true, "select id from users where account = ?", act);
	}
	//��ȡ�˺���Ϣ
	public String getAccount(int id) {
		return (String)sql.returnRes(true, "select account from users where id = ?", id);
	}
	//��ȡ����
	public String getPassword(String act) {
		return (String)sql.returnRes(true, "select password from users where account = ?", act);
	}
	//��ȡ�ǳ�
	public String getName(String act) {
		return (String)sql.returnRes(true, "select username from users where account = ?", act);
	}
	//��ȡ���
	public int getMoney(int id) {
		return (int)sql.returnRes(true,"select money from users where id = ?", id);
	}
	//��ȡ�ܱ�����
	public int getSecurityId(String act) {
		return (int)sql.returnRes(true,"select securityid from users where account = ?", act);
	}
	//��ȡ�ܱ���
	public String getSecurityAnswer(String act) {
		return (String)sql.returnRes(true, "select securityanswer from users where account = ?", act);
	}
	//��ȡ���
	public int getIdentity(String act) {	
		boolean player=(boolean)sql.returnRes(true,  "select identity from users where account = ?", act);
		if(player) {
			return 1;
		}else return 0;
	}
	//��ȡ�Ա�
	public int getSex(String act) {
		boolean boy=(boolean)sql.returnRes(true,"select sex from users where account = ?", act);
		if(boy) {
			return 1;
		}else return 0;
	}
	//��ȡ״̬
	public int getState(int id) {
		boolean enable=(boolean)sql.returnRes(true,"select state from users where id = ?", id);
		if(enable) {
			return 1;
		}else return 0;
	}
	//��ȡ��¼״̬
	public int getLogging(int id) {
		boolean logging=(boolean)sql.returnRes(true, "select logging from users where id = ?", id);
		if(logging) {
			return 1;
		}else return 0;
	}
	//�����û��ĵ�¼״̬
	public void setLogging(int id,int state) {
		sql.write("UPDATE users SET logging=? where id = ?", state,id);
	}
	//��ֵ
	public void recharge(Users users,int money) {
		sql.write("UPDATE users set money = ? where id = ?", (users.getMoney()+money),users.getId());
		//����ǰmodel�е�user������޸�
		users.setMoney(getMoney(users.getId()));
	}

	//��ѯ�Ƿ��и�����˺�
	public boolean testAccountExist(String act) {
		boolean res=false;
		try {
			ResultSet result =(ResultSet)sql.returnRes(false,"select account from users" );
			//�˺Ŵ��ڵĻ�res��true
			while(result.next()) {		
				if(act.equals( result.getString(1))) {
					res = true;
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return res;
	}
	//MD5���ܷ���
	public String md5(String pwd) {
		return (String)sql.returnRes(true, "select md5(?)",pwd);
	}
	//ע��ɹ���ʼ�����ݿ�д������
	public void register(String act,String pwd,String nam,int sex,int securityId,String securityAnswer,Timestamp curTime) {
		sql.write("insert into users(account,password,username,money,securityid,securityanswer,sex,regtime) values(?,?,?,?,?,?,?,?)",act,
				md5(pwd),nam,500,securityId,securityAnswer,sex,sql.dateToInt(curTime));
	}
	//ע��ʱ���ע�����������
	public int testInput(String act,String nam,String pwd,String pwd2,String asw) {
		int res = 0;
		if(act.length()==0 || nam.length()==0 || pwd.length()==0 || pwd2.length()==0 || asw.length() == 0){
			res=1;
		}else {
			if(nam.length()>10 || pwd.length()>12 || pwd2.length()>12|| asw.length() >10 ){
				res = 2;
			}else {
				if(nam.contains(" ") || asw.contains(" ") || nam.contains("%") || asw.contains("%")) {
					res = 6;
				}else {
					if(sql.getUsers_sql().testRepatition(act)) {               //�Ƿ��ظ�
						res = 5;
					}else {
						if(!act.matches(Constant.REGEX_NUM)) {
							res=3;
						}else {
							if(!pwd.matches(Constant.REGEX_NUM)) {
								res=4;
							}else {
								if(!pwd.equals(pwd2)) {
									res=7;
								}
							}
						}
					}
				}
			}
		}
		if(res==0) {
			sendemail.makeSecurityCode();
			try {
				sendemail.sendSecurityCode(act+"@qq.com");
			} catch (GeneralSecurityException e) {
				e.printStackTrace();
			}
		}
		return res ;
	}
	//(ע��)ע��ʱ������������֤��
	public int testSecurityCode(String code) {
		int res = 0;
		if(code.length()==0) {
			res = 1;
		}else {
			if(code.length()>6) {
				res = 2;
			}else {
				if(!code.equals(sendemail.getSecurityCode())) {  //�ж���֤���Ƿ���ȷ
					res=3;
				}
			}
		}
		return res;
	}
	//����˺��Ƿ��ظ�
	public boolean testRepatition(String act) {
		boolean res = false;
		try {
			ResultSet result = (ResultSet)sql.returnRes(false, "select account from users");
			while(result.next()) {
				if(result.getString("account").equals(act)) {
					res =  true;
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return res;
	}

	//�޸���Ϣ
	public void modifyInfo(Users users) {
		sql.write("UPDATE users SET username=?,password=?,sex=?,securityid=?,securityanswer=? WHERE id=?",  users.getUsername(),md5(users.getPassword()),
				users.getSex(),users.getSecurityId(),users.getSecurityAnswer(),users.getId());
	}
	//��������
	public void replacePassword(String account,String pwd) {
		sql.write("UPDATE users SET password=? WHERE account =?", md5(pwd), account);
	}
	//��ȡȫ���û���Ա����
	public int getAllUsersNumber(String username) {
		ResultSet res;
		int number=0;
		try {
			res=(ResultSet)sql.returnRes(false,"select count(*) from users where identity = '1' and username like '%"+username+"%'");
			res.next();
			number = res.getInt(1);
		} catch (SQLException e) {
			e.printStackTrace();
		}	
		return number ;
	}
	//��ȡȫ���û�����
	public Object[][] getAllUsers(int page,String username){
		ResultSet res;
		rowData= new Object[getAllUsersNumber(username)][9];
		int number=(page-1)*20+1,usersnumber = getAllUsersNumber(username),row=0;
		if(page*20>usersnumber) {
			res=(ResultSet)sql.returnRes(false,"select * from users where identity = '1' and username like '%"+username+"%' limit ?,?",(page-1)*20, (usersnumber-(page-1)*20));
		}else res=(ResultSet)sql.returnRes(false,"select * from users where identity = '1' and username like '%"+username+"%' limit ?,?", (page-1)*20,20);
		try {
			while(res.next()) {	
				rowData[row][0] = number ;
				rowData[row][1] = res.getInt(1);
				rowData[row][2] = res.getLong(3);		
				rowData[row][3] =res.getString(5);
				rowData[row][4] = res.getInt(6);
				if(res.getInt(7)==1) {
					rowData[row][5] = "��";
				}else rowData[row][5] = "Ů";
				rowData[row][6] = sql.getSecurity_sql().getSecurity(res.getInt(8)); 
				rowData[row][7] = sql.intToDate( res.getString(10));
				if(res.getInt(11)==1) {
					rowData[row][8] = "�ɵ�¼";
				}else {
					rowData[row][8] = "�ѱ�����";
				}
				row++;
				number++;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}	
		return rowData;
	}
	//����Ա�����û�
	public void freezeUser(int id,int state) {
		sql.write("UPDATE users SET state = ? where id = ?", state,id);
	}
	//(�һ�����)�˶��ܱ�
	public int Find(String act,int securityId,String securityAnswer) {
		int res = 0;
		b:if(testAccountExist(act)) {							//���ж��˺��Ƿ����
			if(securityId != getSecurityId(act)) {				//���жϸ��˺��Ƿ�ѡ���˸��ܱ�
				res=1;
				break b;
			}
			if(securityAnswer.equals(getSecurityAnswer(act))) {	//ƥ���ܱ���
				res =2 ;
			}else res = 3;
		}
		return res;
	}
	//(�һ�����)�����������������ʽ
	public int testInput(String replace,String replace2) {
		int res = 0; 
		if(replace.length()==0 || replace2.length()==0) {
			res = 1;
		}else {
			if(!replace.matches(Constant.REGEX_NUM)  || !replace2.matches(Constant.REGEX_NUM)) {
				res = 2;
			}else {
				if(!replace.equals(replace2)) {
					res = 3;
				}
			}
		}
		return res;
	}
	//(�һ�����)���������ܱ��𰸸�ʽ
	public int testAnswer(String asw) {
		int res = 0; 
		if(asw.length()==0) {
			res = 1;
		}else {
			if(asw.length()>10) {
				res = 2;
			}
		}
		return res;
	}
	//(�һ�����)�����ܱ�ǰ�Ƿ��������˺ţ�����ʼ�����ʼ�
	public int testInputAct(String act) {
		int res = 0;
		if(act.length()==0) {
			res = 1;
		}else {
			if(!act.matches(Constant.REGEX_NUM)) {
				res = 2;
			}else {
				if(!testAccountExist(act)) {
					res = 3;
				}
			}
		}
		if(res==0) {											
			sendemail.makeSecurityCode();								//����sendemail����һ����֤��
			try {
				sendemail.sendSecurityCode(act+"@qq.com");				//��qq����
			} catch (GeneralSecurityException e) {
				e.printStackTrace();
			}
		}
		return res;
	}
	//(�޸���Ϣ)����������ƺ��Ա�
	public int testInput(String nam,int sex) {
		int res = 0; 
		//���ж��Ƿ��������޸�
		if(nam.equals(users.getUsername())&&sex==users.getSex()) {
			res = 1;
		}else {
			if(nam.length()==0 ) {
				res = 2;
			}else {
				if(nam.contains(" ") ) {					//�Ƿ��пո�
					res = 4;
				}else {
					if(nam.length()>10) {
						res = 3;
					}
				}
			}
		}
		return res;
	}
	//(�޸���Ϣ)������֤��
	public void send() {
		sendemail.makeSecurityCode();
		try {
			sendemail.sendSecurityCode(users.getAccount()+"@qq.com");
		} catch (GeneralSecurityException e) {
			e.printStackTrace();
		}
	}
	//���޸���Ϣ���������ľ������������
	public int testReplacePwd(String oldPwd,String newPwd) {
		int res=0;
		if(oldPwd.length()==0 || newPwd.length()==0 ) {
			res = 1;
		}else {
			if(!oldPwd.matches(Constant.REGEX_NUM) || !newPwd.matches(Constant.REGEX_NUM)) {
				res = 2;
			}else {
				if(!oldPwd.equals(users.getPassword())) {
					res=3;
				}
			}
		}
		return res;
	}
	//(�޸���Ϣ)����������֤��
	public int testCode(String yzm) {
		int res = 0;
		if(yzm.length()==0) {
			res = 1;
		}else {
			if(!yzm.equals(sendemail.getSecurityCode())) {
				res = 2;
			}
		}
		return res;
	}
	//���޸���Ϣ����ʼ���������ܱ�
	public int testASW(String asw) {
		int res = 0;
		if(asw.length()==0) {
			res = 1;
		}else {
			if(asw.length()>10) {
				res = 2;
			}else {
				if(asw.contains(" ")) {
					res = 3;
				}
			}
		}
		return res;
	}
}
