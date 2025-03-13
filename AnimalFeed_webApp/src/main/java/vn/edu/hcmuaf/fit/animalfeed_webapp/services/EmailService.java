package vn.edu.hcmuaf.fit.animalfeed_webapp.services;

import vn.edu.hcmuaf.fit.animalfeed_webapp.utils.EmailSender;

import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.*;

public class EmailService {

    /**
     * Gửi email chứa mã xác nhận đến người dùng
     * @param email Địa chỉ email người nhận
     * @param code Mã xác nhận
     * @return true nếu gửi thành công, false nếu có lỗi
     */
    public boolean sendVerificationEmail(String email, String code) {
        try {
            String subject = "Mã xác minh đặt lại mật khẩu";
            String content = "<div style='font-family: Arial, sans-serif; max-width: 600px; margin: 0 auto; padding: 20px; border: 1px solid #ddd; border-radius: 5px;'>"
                    + "<h2 style='color: #3366cc;'>Yêu cầu đặt lại mật khẩu</h2>"
                    + "<p>Xin chào,</p>"
                    + "<p>Chúng tôi nhận được yêu cầu đặt lại mật khẩu từ tài khoản của bạn. Vui lòng sử dụng mã xác nhận sau đây:</p>"
                    + "<div style='background-color: #f5f5f5; padding: 10px; text-align: center; font-size: 24px; font-weight: bold; letter-spacing: 5px; margin: 20px 0;'>"
                    + code
                    + "</div>"
                    + "<p>Mã xác nhận này có hiệu lực trong 5 phút.</p>"
                    + "<p>Nếu bạn không yêu cầu đặt lại mật khẩu, vui lòng bỏ qua email này.</p>"
                    + "<p>Trân trọng,<br>Đội ngũ hỗ trợ</p>"
                    + "</div>";

            EmailSender.sendEmail(email, subject, content);
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
            String subject = "Mật khẩu của bạn đã được thay đổi";
            String content = "<div style='font-family: Arial, sans-serif; max-width: 600px; margin: 0 auto; padding: 20px; border: 1px solid #ddd; border-radius: 5px;'>"
                    + "<h2 style='color: #3366cc;'>Thông báo thay đổi mật khẩu</h2>"
                    + "<p>Xin chào,</p>"
                    + "<p>Mật khẩu tài khoản của bạn đã được thay đổi thành công.</p>"
                    + "<p>Nếu bạn không thực hiện thay đổi này, vui lòng liên hệ ngay với đội ngũ hỗ trợ.</p>"
                    + "<p>Trân trọng,<br>Đội ngũ hỗ trợ</p>"
                    + "</div>";

            EmailSender.sendEmail(email, subject, content);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public void sendEmail(String toEmail, String subject, String content) {
    }
}