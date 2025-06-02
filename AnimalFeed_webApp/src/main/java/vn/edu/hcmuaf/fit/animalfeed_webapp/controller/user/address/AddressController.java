package vn.edu.hcmuaf.fit.animalfeed_webapp.controller.user.address;

import vn.edu.hcmuaf.fit.animalfeed_webapp.dao.AddressDao;
import vn.edu.hcmuaf.fit.animalfeed_webapp.dao.model.Address;
import vn.edu.hcmuaf.fit.animalfeed_webapp.dao.model.Category;
import vn.edu.hcmuaf.fit.animalfeed_webapp.dao.model.User;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import vn.edu.hcmuaf.fit.animalfeed_webapp.services.CategoryService;

import java.io.IOException;
import java.util.List;

@WebServlet("/location_user")
public class AddressController extends HttpServlet {
    private AddressDao addressDao;

    @Override
    public void init() throws ServletException {
        addressDao = new AddressDao();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (request.getSession().getAttribute("user") == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        int userId = ((User) request.getSession().getAttribute("user")).getId();
        List<Address> addressList = addressDao.getAddressesByUserId(userId);
        if (addressList == null || addressList.isEmpty()) {
            System.out.println("Không có địa chỉ nào cho userId: " + userId);
        } else {
            System.out.println("Address list for user " + userId + ": " + addressList);
            for (Address addr : addressList) {
                if (addr.getId() == 0) {
                    System.out.println("Lỗi: Address ID = 0 cho địa chỉ: " + addr);
                } else {
                    System.out.println("Address ID: " + addr.getId());
                }
            }
        }

        CategoryService categoryService = new CategoryService();
        List<Category> categories = categoryService.getAll();

        request.setAttribute("categoriesData", categories);
        request.setAttribute("addressList", addressList);
        request.getRequestDispatcher("/views/web/chi_tiet_ca_nhan/so_dia_chi.jsp").forward(request, response);
    }
}