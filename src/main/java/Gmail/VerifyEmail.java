package Gmail;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;

import javax.mail.BodyPart;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Part;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.internet.MimeBodyPart;
import javax.mail.search.SubjectTerm;

import com.testautomationguru.utility.PDFUtil;

public class VerifyEmail {

	public static void GmailUtils( ) throws Exception {

		String subjectToSearch = "MyHR Request Resolved - Reference: 38461";
		Properties props = System.getProperties();
		props.setProperty("mail.store.protocol", "imap");
		props.setProperty("mail.imaps.partialfetch", "false");
		props.put("mail.imap.ssl.enable", "true");
		props.put("mail.mime.base64.ignoreerrors", "true");

		Session session = Session.getDefaultInstance(props, null);
		Store store = session.getStore("imap");
		store.connect("imap.gmail.com", 993, "Sabine.de.hr@gmail.com", "fnbwuaepkguyuxho");

		Folder folder = store.getFolder("INBOX");
		folder.open(Folder.READ_ONLY);

		System.out.println("Total Messages:" + folder.getMessageCount());
		System.out.println("Unread Messages:" + folder.getUnreadMessageCount());

		Message[] messages = folder.search(new SubjectTerm(subjectToSearch), folder.getMessages());

		for (Message mail : messages) {

			System.out.println("***************************************************");
			System.out.println("MESSAGE : \n");
			System.out.println("Subject: " + mail.getSubject());
			if (mail.getSubject().equals(subjectToSearch)) {

				System.out.println("From: " + mail.getFrom()[0]);
				System.out.println("To: " + mail.getAllRecipients()[0]);
				System.out.println("Date: " + mail.getReceivedDate());
				String Body =getEmailBody(mail).trim();
				 String modifiedText = Body.replaceAll("<a\\s+href=\"[^\"]*\">([^<]*)</a>", "$1");
				System.out.println("Body: \n" + modifiedText);
				// System.out.println("Has Attachments: " + hasAttachments(mail));
				break;

			}
		}
	}

	public static boolean hasAttachments(Message email) throws Exception {

		// suppose 'message' is an object of type Message
		String contentType = email.getContentType();
		System.out.println(contentType);

		if (contentType.toLowerCase().contains("multipart/mixed")) {
			// this message must contain attachment
			Multipart multiPart = (Multipart) email.getContent();

			for (int i = 0; i < multiPart.getCount(); i++) {
				MimeBodyPart part = (MimeBodyPart) multiPart.getBodyPart(i);
				if (Part.ATTACHMENT.equalsIgnoreCase(part.getDisposition())) {
					System.out.println("Attached filename is:" + part.getFileName());

					MimeBodyPart mimeBodyPart = (MimeBodyPart) part;
					String fileName = mimeBodyPart.getFileName();

					String destFilePath = System.getProperty("user.dir") + "\\Resources\\";

					File fileToSave = new File(fileName);
					mimeBodyPart.saveFile(destFilePath + fileToSave);

					// download the pdf file in the resource folder to be read by PDFUTIL api.

					PDFUtil pdfUtil = new PDFUtil();
					String pdfContent = pdfUtil.getText(destFilePath + fileToSave);

					System.out.println("******---------------********");
					System.out.println("\n");
					System.out.println("Started reading the pdfContent of the attachment:==");

					System.out.println(pdfContent);

					System.out.println("\n");
					System.out.println("******---------------********");

					Path fileToDeletePath = Paths.get(destFilePath + fileToSave);
					Files.delete(fileToDeletePath);
				}
			}

			return true;
		}

		return false;
	}

	public static String getEmailBody(Message email) throws IOException, MessagingException {

		Object content = email.getContent();

		if (content instanceof String) {

			String bodyText = (String) content;

			// Remove extra whitespace and trim the text
			//bodyText = bodyText.trim().replaceAll("\\s+", " ");
			bodyText = bodyText.replaceAll("\\s+", " ").replaceAll("(\r\n|\r|\n)+", "\n");;

			return bodyText.trim();
		} else if (content instanceof Multipart) {
			Multipart multiPart = (Multipart) content;
			StringBuilder body = new StringBuilder();

			for (int i = 0; i < multiPart.getCount(); i++) {
				BodyPart bodyPart = multiPart.getBodyPart(i);
				if (bodyPart.isMimeType("text/plain")) {
					String plainText = (String) bodyPart.getContent();
					// Remove extra whitespace and trim the text
					//plainText = plainText.trim().replaceAll("\\s+", " ");
					plainText = plainText.replaceAll("\\s+", " ").replaceAll("(\r\n|\r|\n)+", "\n");;
					plainText = plainText.trim();
					body.append(plainText);
				}
			}

			// System.out.println(body.toString());

			return body.toString().trim();
		}

		return "";
		/*
		 * String line, emailContentEncoded; StringBuffer bufferEmailContentEncoded =
		 * new StringBuffer(); BufferedReader reader = new BufferedReader(new
		 * InputStreamReader(email.getInputStream())); while ((line = reader.readLine())
		 * != null) { bufferEmailContentEncoded.append(line); }
		 * 
		 * System.out.println("**************************************************");
		 * 
		 * System.out.println(bufferEmailContentEncoded);
		 * 
		 * System.out.println("**************************************************");
		 * 
		 * emailContentEncoded = bufferEmailContentEncoded.toString();
		 * 
		 * if (email.getContentType().toLowerCase().contains("multipart/related")) {
		 * 
		 * emailContentEncoded =
		 * emailContentEncoded.substring(emailContentEncoded.indexOf("base64") + 6);
		 * emailContentEncoded = emailContentEncoded.substring(0,
		 * emailContentEncoded.indexOf("Content-Type") - 1);
		 * 
		 * System.out.println(emailContentEncoded);
		 * 
		 * String emailContentDecoded = new String(new
		 * Base64().decode(emailContentEncoded.toString().getBytes()));
		 * System.out.println(emailContentDecoded); return emailContentDecoded; }
		 * 
		 * return emailContentEncoded;
		 */

	}
}
