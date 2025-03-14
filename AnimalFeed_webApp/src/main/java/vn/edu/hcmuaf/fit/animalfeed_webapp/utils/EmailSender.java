    package vn.edu.hcmuaf.fit.animalfeed_webapp.utils;

    import javax.mail.*;
    import javax.mail.internet.InternetAddress;
    import javax.mail.internet.MimeMessage;
    import java.util.Properties;

    public class EmailSender {
        private static final String SMTP_HOST = "smtp.gmail.com";
        private static final int SMTP_PORT = 587;
        private static final String SMTP_USERNAME = "congvinh0839@gmail.com"; // Thay bằng email của bạn
        private static final String SMTP_PASSWORD = "cole fejo knco mbic"; // Mật khẩu ứng dụng

        public static void sendEmail(String to, String subject, String content) {
            // Cấu hình properties
            Properties props = new Properties();
            props.put("mail.smtp.auth", "true");
            props.put("mail.smtp.starttls.enable", "true");
            props.put("mail.smtp.host", SMTP_HOST);
            props.put("mail.smtp.port", SMTP_PORT);

            // Tạo authenticator
            Authenticator authenticator = new Authenticator() {
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(SMTP_USERNAME, SMTP_PASSWORD);
                }
            };

            // Tạo session
            Session session = Session.getInstance(props, authenticator);

            try {
                // Tạo message
                Message message = new MimeMessage(session);
                message.setFrom(new InternetAddress(SMTP_USERNAME));
                message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
                message.setSubject(subject);
                message.setText(content);

                // Gửi email
                Transport.send(message);
                System.out.println("Email sent successfully!");
            } catch (MessagingException e) {
                throw new RuntimeException("Failed to send email", e);
            }
        }
    //    public static void main(String[] args) {
    //        sendEmail(
    //                "daocongkhanh1991@gmail.com",
    //                "Test Subject",
    //                "This is a test email from JavaMail"
    //        );
    //    }
    }