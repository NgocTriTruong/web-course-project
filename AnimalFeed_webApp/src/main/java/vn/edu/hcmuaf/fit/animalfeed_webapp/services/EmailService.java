package vn.edu.hcmuaf.fit.animalfeed_webapp.services;

import javax.mail.*;
import javax.mail.internet.*;
import java.util.Properties;

public class EmailService {

    // Cấu hình email (thay bằng thông tin của bạn)
    private static final String SMTP_HOST = "smtp.gmail.com";
    private static final String SMTP_PORT = "587";
    private static final String EMAIL_USERNAME = "congvinh0839@gmail.com"; // Thay bằng email của bạn
    private static final String EMAIL_PASSWORD = "cole fejo knco mbic";   // Thay bằng mật khẩu ứng dụng (App Password) của Gmail

    /**
     * Gửi email chứa mã xác nhận đến người dùng
     * @param email Địa chỉ email người nhận
     * @param code Mã xác nhận
     * @return true nếu gửi thành công, false nếu có lỗi
     */
    public boolean sendVerificationEmail(String email, String code) {
        try {
            String subject = "Mã Xác Minh Đăng Ký Tài Khoản";
            String content = "<!DOCTYPE html>" +
                    "<html>" +
                    "<head>" +
                    "<meta charset='UTF-8'>" +
                    "<style>" +
                    "body { font-family: Arial, sans-serif; max-width: 600px; margin: 0 auto; padding: 20px; }" +
                    ".container { border: 1px solid #ddd; border-radius: 5px; padding: 20px; }" +
                    "h2 { color: #3366cc; }" +
                    ".code { background-color: #f5f5f5; padding: 15px; text-align: center; font-size: 28px; " +
                    "font-weight: bold; letter-spacing: 8px; margin: 20px 0; border-radius: 5px; }" +
                    "</style>" +
                    "</head>" +
                    "<body>" +
                    "<div class='container'>" +
                    "<h2>Mã Xác Minh Đăng Ký</h2>" +
                    "<p>Xin chào,</p>" +
                    "<p>Chúng tôi đã nhận được yêu cầu đăng ký tài khoản của bạn. Dưới đây là mã xác minh:</p>" +
                    "<div class='code'>" + code + "</div>" +
                    "<p>Mã này có hiệu lực trong <strong>5 phút</strong>. Vui lòng nhập mã vào form xác minh để hoàn tất đăng ký.</p>" +
                    "<p>Nếu bạn không yêu cầu đăng ký, vui lòng bỏ qua email này.</p>" +
                    "<p>Trân trọng,<br>Đội ngũ AnimalFeed</p>" +
                    "</div>" +
                    "</body>" +
                    "</html>";

            sendEmail(email, subject, content);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Gửi thông báo mật khẩu đã được thay đổi
     * @param email Địa chỉ email người nhận
     * @return true nếu gửi thành công, false nếu có lỗi
     */
    public boolean sendPasswordChangedNotification(String email) {
        try {
            String subject = "Mật Khẩu Của Bạn Đã Được Thay Đổi";
            String content = "<!DOCTYPE html>" +
                    "<html>" +
                    "<head>" +
                    "<meta charset='UTF-8'>" +
                    "<style>" +
                    "body { font-family: Arial, sans-serif; max-width: 600px; margin: 0 auto; padding: 20px; }" +
                    ".container { border: 1px solid #ddd; border-radius: 5px; padding: 20px; }" +
                    "h2 { color: #3366cc; }" +
                    "</style>" +
                    "</head>" +
                    "<body>" +
                    "<div class='container'>" +
                    "<h2>Thông Báo Thay Đổi Mật Khẩu</h2>" +
                    "<p>Xin chào,</p>" +
                    "<p>Mật khẩu tài khoản của bạn đã được thay đổi thành công.</p>" +
                    "<p>Nếu bạn không thực hiện thay đổi này, vui lòng liên hệ ngay với chúng tôi qua email này.</p>" +
                    "<p>Trân trọng,<br>Đội ngũ AnimalFeed</p>" +
                    "</div>" +
                    "</body>" +
                    "</html>";

            sendEmail(email, subject, content);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Gửi email với nội dung HTML
     * @param toEmail Địa chỉ email người nhận
     * @param subject Tiêu đề email
     * @param content Nội dung HTML của email
     */
    public void sendEmail(String toEmail, String subject, String content) {
        // Cấu hình Properties cho SMTP
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", SMTP_HOST);
        props.put("mail.smtp.port", SMTP_PORT);

        // Tạo phiên gửi email
        Session session = Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(EMAIL_USERNAME, EMAIL_PASSWORD);
            }
        });

        try {
            // Tạo đối tượng MimeMessage
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(EMAIL_USERNAME));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail));
            message.setSubject(subject);
            message.setContent(content, "text/html; charset=utf-8");

            // Gửi email
            Transport.send(message);
            System.out.println("Email sent successfully to " + toEmail);
        } catch (MessagingException e) {
            throw new RuntimeException("Failed to send email: " + e.getMessage());
        }
    }
}