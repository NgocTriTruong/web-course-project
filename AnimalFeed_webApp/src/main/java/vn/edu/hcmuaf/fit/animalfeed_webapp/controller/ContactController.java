package vn.edu.hcmuaf.fit.animalfeed_webapp.controller;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import vn.edu.hcmuaf.fit.animalfeed_webapp.dao.model.Category;
import vn.edu.hcmuaf.fit.animalfeed_webapp.dao.model.Contact;
import vn.edu.hcmuaf.fit.animalfeed_webapp.dao.model.User;
import vn.edu.hcmuaf.fit.animalfeed_webapp.services.CategoryService;
import vn.edu.hcmuaf.fit.animalfeed_webapp.services.ContactService;

import java.io.IOException;
import java.util.List;
import java.util.regex.Pattern;

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

        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");

        try {
            // Get parameters from the request
            String contactUser = request.getParameter("contact_user");
            String phone = request.getParameter("phone");
            String email = request.getParameter("email");
            String address = request.getParameter("address");
            String content = request.getParameter("message");

            // Validate required fields
            if (contactUser == null || contactUser.trim().isEmpty() ||
                    phone == null || phone.trim().isEmpty() ||
                    content == null || content.trim().isEmpty()) {
                request.setAttribute("error", "Vui lòng điền đầy đủ thông tin bắt buộc.");
                forwardToContactPage(request, response);
                return;
            }

            if (!Pattern.matches("^(0|\\+84)(\\d{9})$", phone.trim())) {
                request.setAttribute("error", "Số điện thoại không hợp lệ.");
                forwardToContactPage(request, response);
                return;
            }

            if (email != null && !email.trim().isEmpty() &&
                    !Pattern.matches("^[A-Za-z0-9+_.-]+@(.+)$", email.trim())) {
                request.setAttribute("error", "Email không hợp lệ.");
                forwardToContactPage(request, response);
                return;
            }

            Contact contact = new Contact();
            contact.setContact_user(contactUser.trim());
            contact.setPhone(phone.trim());
            contact.setEmail(email != null ? email.trim() : "");
            contact.setAddress(address != null ? address.trim() : "");
            contact.setContent(content.trim());
            contact.setUserId(user.getId());

            contactService.addContact(contact);
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
        CategoryService categoryService = new CategoryService();
        // Lấy danh sách danh mục
        List<Category> categories = categoryService.getAll();

        // Gửi dữ liệu tới JSP
        request.setAttribute("categoriesData", categories);
        // Redirect to the contact page
        RequestDispatcher dispatcher = request.getRequestDispatcher("/views/web/help.jsp");
        dispatcher.forward(request, response);
    }
}
