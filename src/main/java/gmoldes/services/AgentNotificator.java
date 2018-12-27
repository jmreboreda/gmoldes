package gmoldes.services;

import gmoldes.domain.email.EmailDataCreationDTO;
import gmoldes.services.email.EmailConstants;
import gmoldes.services.email.EmailData;
import gmoldes.services.email.EmailParameters;
import gmoldes.services.email.EmailSender;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;

public class AgentNotificator {

    public AgentNotificator() {
    }

    public Boolean sendEmailToContractsAgent(EmailDataCreationDTO emailDataCreationDTO) throws AddressException {

        Boolean isSendOk = false;

        EmailData emailData = EmailData.create()
                .withEmailFrom(new InternetAddress(EmailParameters.EMAIL_FROM_TO_SEND_CONTRACT))
                .withEmailTo(new InternetAddress(EmailParameters.EMAIL_TO_SEND_CONTRACT))
                .withEmailDeliveryNotification(new InternetAddress(EmailParameters.EMAIL_DELIVERY_NOTIFICATION))
                .withEmailSubject(EmailConstants.STANDARD_TEXT_GESTORIAGM + emailDataCreationDTO.getVariationTypeText() + emailDataCreationDTO.getEmployee() +
                        " [" + emailDataCreationDTO.getEmployer() + "]")
                .withEmailMessageText(EmailConstants .STANDARD_TEXT_SEND_CONTRACT_DATA + EmailConstants.STANDARD_LOPD_TEXT_SEND_MAIL)
                .withAttachedPath(emailDataCreationDTO.getPath())
                .withAttachedName(emailDataCreationDTO.getFileName())
                .build();
        EmailSender emailSender = new EmailSender();
        try {
            isSendOk = emailSender.sendEmail(emailData);
        } catch (MessagingException e) {
            e.printStackTrace();
        }

        return isSendOk;
    }
}
