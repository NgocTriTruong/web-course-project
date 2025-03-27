package vn.edu.hcmuaf.fit.animalfeed_webapp.controller.user.address;

import vn.edu.hcmuaf.fit.animalfeed_webapp.dao.AddressDao;
import vn.edu.hcmuaf.fit.animalfeed_webapp.dao.model.Address;
import vn.edu.hcmuaf.fit.animalfeed_webapp.dao.model.User;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
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
        System.out.println("Address list for user " + userId + ": " + addressList); // Log để kiểm tra
        request.setAttribute("addressList", addressList);
        request.getRequestDispatcher("/views/web/chi_tiet_ca_nhan/so_dia_chi.jsp").forward(request, response);
    }
}