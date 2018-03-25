package gmoldes.utilities;

import javax.mail.internet.InternetAddress;
import java.nio.file.Path;

public class EmailData {

    private InternetAddress emailFrom;
    private InternetAddress emailTo;
    private InternetAddress emailDeliveryNotification;
    private String emailSubject;
    private String emailMessageText;
    private Path attachedPath;
    private String attachedName;

    public EmailData(InternetAddress emailFrom,
            InternetAddress emailTo,
            InternetAddress emailDeliveryNotification,
            String emailSubject,
            String emailMessageText,
            Path attachedPath,
            String attachedName){

        this.emailFrom = emailFrom;
        this.emailTo = emailTo;
        this.emailDeliveryNotification = emailDeliveryNotification;
        this.emailSubject = emailSubject;
        this.emailMessageText =emailMessageText;
        this.attachedPath = attachedPath;
        this.attachedName = attachedName;
    }

    public InternetAddress getEmailFrom() {
        return emailFrom;
    }

    public void setEmailFrom(InternetAddress emailFrom) {
        this.emailFrom = emailFrom;
    }

    public InternetAddress getEmailTo() {
        return emailTo;
    }

    public void setEmailTo(InternetAddress emailTo) {
        this.emailTo = emailTo;
    }

    public InternetAddress getEmailDeliveryNotification() {
        return emailDeliveryNotification;
    }

    public void setEmailDeliveryNotification(InternetAddress emailDeliveryNotification) {
        this.emailDeliveryNotification = emailDeliveryNotification;
    }

    public String getEmailSubject() {
        return emailSubject;
    }

    public void setEmailSubject(String emailSubject) {
        this.emailSubject = emailSubject;
    }

    public String getEmailMessageText() {
        return emailMessageText;
    }

    public void setEmailMessageText(String emailMessageText) {
        this.emailMessageText = emailMessageText;
    }

    public Path getAttachedPath() {
        return attachedPath;
    }

    public void setAttachedPath(Path attachedPath) {
        this.attachedPath = attachedPath;
    }

    public String getAttachedName() {
        return attachedName;
    }

    public void setAttachedName(String attachedName) {
        this.attachedName = attachedName;
    }

    public static EmailDataBuilder create() {
        return new EmailDataBuilder();
    }

    public static class EmailDataBuilder {

        private InternetAddress emailFrom;
        private InternetAddress emailTo;
        private InternetAddress emailDeliveryNotification;
        private String emailSubject;
        private String emailMessageText;
        private Path attachedPath;
        private String attachedName;

        public EmailDataBuilder withEmailFrom(InternetAddress emailFrom) {
            this.emailFrom = emailFrom;
            return this;
        }

        public EmailDataBuilder withEmailTo(InternetAddress emailTo) {
            this.emailTo = emailTo;
            return this;
        }

        public EmailDataBuilder withEmailDeliveryNotification(InternetAddress emailDeliveryNotification) {
            this.emailDeliveryNotification = emailDeliveryNotification;
            return this;
        }

        public EmailDataBuilder withEmailSubject(String emailSubject) {
            this.emailSubject = emailSubject;
            return this;
        }

        public EmailDataBuilder withEmailMessageText(String emailMessageText) {
            this.emailMessageText = emailMessageText;
            return this;
        }

        public EmailDataBuilder withAttachedPath(Path attachedPath) {
            this.attachedPath = attachedPath;
            return this;
        }

        public EmailDataBuilder withAttachedName(String attachedName) {
            this.attachedName = attachedName;
            return this;
        }

        public EmailData build() {
            return new EmailData(this.emailFrom, this.emailTo, this.emailDeliveryNotification, this.emailSubject, this.emailMessageText, this.attachedPath,
                    this.attachedName);
        }
    }
}

