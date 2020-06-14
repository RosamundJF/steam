package tools;
import java.security.GeneralSecurityException;
import java.util.Properties;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import com.sun.mail.util.MailSSLSocketFactory;

import model.sql.Sql;
public class SendEmail {
	private Sql sql;
	private String securityCode;
	public SendEmail(Sql sql) {
		this.sql=sql;
	}
	//������֤��
	public void makeSecurityCode() {
		securityCode = sql.getUsers_sql().md5(Integer.toString((int) (10000*Math.random()))).substring(0, 5);
	}
	//�����һ�����ɵ���֤��
	public String getSecurityCode() {
		return securityCode;
	}
    public void sendSecurityCode(String to) throws GeneralSecurityException {
        // �����˵�������
        String from = "993608769@qq.com";

        // ָ�������ʼ�������Ϊ smtp.qq.com
        String host = "smtp.qq.com";  //QQ �ʼ�������

        // ��ȡϵͳ����
        Properties properties = System.getProperties();

        // �����ʼ�������
        properties.setProperty("mail.smtp.host", host);
        properties.put("mail.smtp.auth", "true");
       
        //����SSL�����֤
        MailSSLSocketFactory sf = new MailSSLSocketFactory();
        sf.setTrustAllHosts(true);									//��������������
        properties.put("mail.smtp.ssl.enable", "true");				//���������֤
        properties.put("mail.smtp.ssl.socketFactory", sf);			
        // ��ȡĬ��session����
        Session session = Session.getDefaultInstance(properties,new Authenticator(){
            public PasswordAuthentication getPasswordAuthentication()
            {
                return new PasswordAuthentication("993608769@qq.com", "xkpyhxhmbyovbeji"); 		//�������ʼ��û�������Ȩ��
            }
        });

        try{
            // ����Ĭ�ϵ� MimeMessage ����
            MimeMessage message = new MimeMessage(session);
            
            // Set From: ͷ��ͷ�ֶ�
            message.setFrom(new InternetAddress(from));

            // Set To: ͷ��ͷ�ֶ�
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));

            // Set Subject: ͷ��ͷ�ֶ�
            message.setSubject("������֤����");

            // ������Ϣ��
            message.setText(securityCode);

            // ������Ϣ
            Transport.send(message);
            System.out.println("Sent message successfully....");
        }catch (MessagingException mex) {
            mex.printStackTrace();
        }
    }
}