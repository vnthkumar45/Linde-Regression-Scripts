package Gmail;

import java.util.Properties;
import java.util.stream.Stream;

import javax.mail.Address;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.internet.InternetAddress;

public class Notification_Verification { 
		
	 public static void main(String[] args) {
	        // Setup properties for accessing the email server
	        Properties properties = new Properties();
	        properties.put("mail.imap.host", "smtp.gmail.com");
	        properties.put("mail.imap.port", "587");
	        properties.put("mail.imap.ssl.enable", "true");

	        // Connect to the email server
	        Session session = Session.getDefaultInstance(properties);

	        try {
	            // Connect to the mailbox with your credentials
	            Store store = session.getStore("imap");
	            store.connect("lindept02@gmail.com", "rrktmewvmyhwfbed");

	            // Access the inbox
	            Folder inbox = store.getFolder("INBOX");
	            inbox.open(Folder.READ_ONLY);

	            // Stream through the email messages
	            Stream<Message> emailStream = Stream.of(inbox.getMessages());

	            // Filter and verify email details
	            emailStream
	                .filter(email -> {
						try {
							return verifySubject(email, "Your Email Subject");
						} catch (MessagingException e1) {
							
							e1.printStackTrace();
						}
						return false;
					})
	                .filter(email -> {
						try {
							return verifyContent(email, "Your Email Content");
						} catch (Exception e1) {
							
							e1.printStackTrace();
						}
						return false;
					})
	                .filter(email -> {
						try {
							return verifyRecipient(email, "recipient@example.com");
						} catch (MessagingException e1) {
							
							e1.printStackTrace();
						}
						return false;
					})
	                .forEach(email -> {
	                    // Verification successful, do something with the email
	                    System.out.println("Email verification successful!");
	                    try {
	                        System.out.println("Subject: " + email.getSubject());
	                        System.out.println("Content: " + email.getContent().toString());
	                        System.out.println("Recipient: " + email.getRecipients(Message.RecipientType.TO)[0]);
	                    } catch (Exception e) {
	                        e.printStackTrace();
	                    }
	                });

	            // Close the mailbox
	            inbox.close(false);
	            store.close();

	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	    }

	    public static boolean verifySubject(Message email, String expectedSubject) throws MessagingException {
	        return email.getSubject().equals(expectedSubject);
	    }

	    private static boolean verifyContent(Message email, String expectedContent) throws Exception {
	        return email.getContent().toString().contains(expectedContent);
	    }

	    private static boolean verifyRecipient(Message email, String expectedRecipient) throws MessagingException {
	        Address[] recipients = email.getRecipients(Message.RecipientType.TO);
	        if (recipients != null && recipients.length > 0) {
	            return ((InternetAddress) recipients[0]).getAddress().equals(expectedRecipient);
	        }
	        return false;
	    }
}
