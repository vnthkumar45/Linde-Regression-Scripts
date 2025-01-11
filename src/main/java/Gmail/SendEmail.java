package Gmail;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage; 

public class SendEmail { 
		
	public static void main(String Subject, String ToEmailID, String FromEmailID,String Password) {

       // final String username = "lindept02@gmail.com";
       // final String password = "ntwvkdrnofbqjzjs";

        Properties prop = new Properties();
		prop.put("mail.smtp.host", "smtp.gmail.com");
        prop.put("mail.smtp.port", "587");
        prop.put("mail.smtp.auth", "true");
        prop.put("mail.smtp.starttls.enable", "true"); //TLS
        prop.put("mail.smtp.debug", "true");
        prop.put("mail.smtp.socketFactory.port", "465");
        prop.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        prop.put("mail.smtp.socketFactory.fallback", "false");
        
        Session session = Session.getInstance(prop,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(FromEmailID, Password);
                    }
                });

        try {

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(FromEmailID));
            message.setRecipients(
                    Message.RecipientType.TO,
                    InternetAddress.parse(ToEmailID)
            );
            message.setSubject(Subject);
            message.setText(Subject
                    + "\n\n Please do not spam my email!");

            Transport.send(message);

            System.out.println("Done - Sent Email");

        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}
