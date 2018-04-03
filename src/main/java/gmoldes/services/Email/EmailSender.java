package gmoldes.services.Email;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.*;
import java.util.Properties;

public class EmailSender {

    private Session session;
    private MimeMultipart multiPart = new MimeMultipart();

    public EmailSender() {

        Properties props = new Properties();
        props.put("mail.smtp.host", EmailParameters.MAIL_SMTP_HOST);
        props.setProperty("mail.smtp.starttls.enable", "true");
        props.setProperty("mail.smtp.port",EmailParameters.MAIL_SMTP_PORT);
        props.setProperty("mail.smtp.user", EmailParameters.MAIL_SMTP_USER);
        props.setProperty("mail.smtp.auth", "true");

        session = Session.getDefaultInstance(props, null);
        session.setDebug(true);
    }

    public Boolean sendEmail(EmailData emailData) throws MessagingException {
        Boolean sendOk = true;
        if(emailData.getAttachedPath() != null) {
            BodyPart attached = new MimeBodyPart();
            attached.setDataHandler(new DataHandler(new FileDataSource(emailData.getAttachedPath().toString())));
            attached.setFileName(emailData.getAttachedName());
            multiPart.addBodyPart(attached);
        }
        BodyPart messageText = new MimeBodyPart();
        messageText.setText(emailData.getEmailMessageText());
        multiPart.addBodyPart(messageText);

        MimeMessage message = new MimeMessage(session);
        message.setFrom(emailData.getEmailFrom());
        message.addRecipient(Message.RecipientType.TO, emailData.getEmailTo());
        message.setSubject(emailData.getEmailSubject());
        message.addHeader("Disposition-Notification-To", emailData.getEmailDeliveryNotification().toString());
//        message.addHeader("Return-Receipt-To", emailData.getEmailDeliveryNotification().toString());

        message.setContent(multiPart);

        Transport t = session.getTransport(EmailParameters.MAIL_TRANSPORT_PROTOCOL);
        t.connect(EmailParameters.MAIL_SMTP_USER, EmailParameters.MAIL_SMTP_PASSWD);
        try {
            t.sendMessage(message, message.getAllRecipients());
        }catch(SendFailedException e){
            e.printStackTrace();
            sendOk = false;
        }
        t.close();

        return sendOk;
    }
}
