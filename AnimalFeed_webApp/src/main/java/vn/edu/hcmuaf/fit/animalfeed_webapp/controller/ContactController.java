package vn.edu.hcmuaf.fit.animalfeed_webapp.controller;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import vn.edu.hcmuaf.fit.animalfeed_webapp.dao.model.Contact;
import vn.edu.hcmuaf.fit.animalfeed_webapp.services.ContactService;

import java.io.IOException;

@WebServlet(value = "/contact")
public class ContactController extends HttpServlet {
    private ContactService contactService;

    @Override
    public void init() {
        this.contactService = new ContactService();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Set encoding for UTF-8 to handle Vietnamese characters
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");

        try {
            // Get parameters from the request
            String contactUser = request.getParameter("contact_user");
            String phone = request.getParameter("phone");
            String email = request.getParameter("email");
            String address = request.getParameter("address");
            String content = request.getParameter("content");

            // Validate required fields
            if (contactUser == null || contactUser.trim().isEmpty() ||
                    phone == null || phone.trim().isEmpty() ||
                    content == null || content.trim().isEmpty()) {
                request.setAttribute("error", "Vui lòng điền đầy đủ thông tin bắt buộc.");
                forwardToContactPage(request, response);
                return;
            }

            // Create and populate Contact object
            Contact contact = new Contact();
            contact.setContact_user(contactUser.trim());
            contact.setPhone(phone.trim());
            contact.setEmail(email != null ? email.trim() : "");
            contact.setAddress(address != null ? address.trim() : "");
            contact.setContent(content.trim());

            // Assign a default userId (adjust as needed)
            contact.setUserId(101); // Replace with actual user ID if using authentication

            // Save the contact using the service
            contactService.addContact(contact);

            // Set success message
            request.setAttribute("success", "Tin nhắn của bạn đã được gửi thành công!");
            forwardToContactPage(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Có lỗi xảy ra. Vui lòng thử lại sau.");
            forwardToContactPage(request, response);
        }
    }

    private void forwardToContactPage(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher("/views/web/help.jsp");
        dispatcher.forward(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Redirect to the contact page
        RequestDispatcher dispatcher = request.getRequestDispatcher("/views/web/help.jsp");
        dispatcher.forward(request, response);
    }
}
