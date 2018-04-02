package gmoldes.services;

import gmoldes.components.contract.new_contract.ContractParts;
import gmoldes.utilities.EmailData;
import gmoldes.utilities.EmailParameters;
import javax.mail.MessagingException;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import java.nio.file.Path;

public class ContractAgentNotificator {

    public ContractAgentNotificator() {
    }

    public Boolean sendEmailToContractAgent(Path path, String fileName, ContractParts contractParts) throws AddressException {
        Boolean isSendOk = false;

        EmailData emailData = EmailData.create()
                .withEmailFrom(new InternetAddress(EmailParameters.EMAIL_FROM_TO_SEND_CONTRACT))
                .withEmailTo(new InternetAddress(EmailParameters.EMAIL_TO_SEND_CONTRACT))
                .withEmailDeliveryNotification(new InternetAddress(EmailParameters.EMAIL_DELIVERY_NOTIFICATION))
                .withEmailSubject(EmailParameters.TEXT_NEW_CONTRACT_IN_MAIL_SUBJECT + contractParts.getSelectedEmployee() +
                        " [" + contractParts.getSelectedEmployer() + "]")
                .withEmailMessageText(EmailParameters .STANDARD_TEXT_SEND_CONTRACT_DATA)
                .withAttachedPath(path)
                .withAttachedName(fileName)
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
