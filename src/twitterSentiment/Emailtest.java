package twitterSentiment;

import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Date;
 
public class Emailtest {
	
	public Emailtest(){
		
	}
 
	public void start(String email, String topic, String total, double positive, double negative) {
		
		if(!email.contains("@")){
			email="glennocp@tcd.ie";
		}
		
		
		final String username = "twittersentiment1@gmail.com";
		final String password = "UCDconnect";
 
		Properties props = new Properties();
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.port", "587");
 
		Session session = Session.getInstance(props,
		  new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(username, password);
			}
		  });
 
		try {
 
			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress("twittersentiment1@gmail.com"));
			message.setRecipients(Message.RecipientType.TO,
				InternetAddress.parse(email));
			message.setSubject("Your Sentiment Analysis Results");
			message.setText("Dear "+email+","+"\n \n This was sent at "+ new Date()
				+ "\n\n Your result for "+topic+" had "+(int)positive+" positive tweet(s) and "+(int)negative+" negative tweet(s) from a total of "
						+ total+ " tweets. \n \n Thanks for using Colm Glennon's Sentiment Analyzer!");
 
			Transport.send(message);
 
			System.out.println("Done");
 
		} catch (MessagingException e) {
			System.out.println("Error Emailing");
			throw new RuntimeException(e);
		}
	}
}