package vn.edu.hcmuaf.fit.animalfeed_webapp.controller.admin.admin_contact;

import com.google.gson.Gson;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import vn.edu.hcmuaf.fit.animalfeed_webapp.dao.model.Contact;
import vn.edu.hcmuaf.fit.animalfeed_webapp.services.ContactService;
import vn.edu.hcmuaf.fit.animalfeed_webapp.services.EmailService;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet(name = "ContactAdmin", value = "/contact-admin")
public class ContactAdmin extends HttpServlet {
    private ContactService contactService;
    private EmailService emailService;
    private Gson gson;

    @Override
    public void init() throws ServletException {
        emailService = new EmailService();
        contactService = new ContactService();
        gson = new Gson();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=UTF-8");

        List<Contact> contacts = contactService.getAllContacts();
        request.setAttribute("contacts", contacts);

        RequestDispatcher dispatcher = request.getRequestDispatcher("/views/admin/contactManagemet.jsp");
        dispatcher.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        String action = request.getParameter("action");

        if ("sendEmail".equals(action)) {
            try {
                String toEmail = request.getParameter("email");
                String subject = request.getParameter("subject");
                String content = request.getParameter("content");

                if (toEmail == null || subject == null || content == null) {
                    sendJsonResponse(response, false, "Thiếu thông tin cần thiết");
                    return;
                }

                emailService.sendEmail(toEmail, subject, content);
                sendJsonResponse(response, true, "Email đã được gửi thành công");

            } catch (Exception e) {
                e.printStackTrace();
                sendJsonResponse(response, false, "Lỗi khi gửi email: " + e.getMessage());
            }
        } else if ("deleteContact".equals(action)) {
            try {
                int id = Integer.parseInt(request.getParameter("id"));
                contactService.deleteContact(id);
                sendJsonResponse(response, true, "Xóa liên hệ thành công");
            } catch (NumberFormatException e) {
                sendJsonResponse(response, false, "ID không hợp lệ");
            } catch (Exception e) {
                e.printStackTrace();
                sendJsonResponse(response, false, "Lỗi khi xóa liên hệ: " + e.getMessage());
            }
        } else {
            sendJsonResponse(response, false, "Action không hợp lệ" + action);
        }
    }

    private void sendJsonResponse(HttpServletResponse response, boolean success, String message) throws IOException {
        Map<String, Object> jsonResponse = new HashMap<>();
        jsonResponse.put("status", success ? "success" : "error");
        jsonResponse.put("message", message);

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        // Gửi dữ liệu dưới dạng JSON
        response.getWriter().write(gson.toJson(jsonResponse));
        response.getWriter().flush();
    }

}
