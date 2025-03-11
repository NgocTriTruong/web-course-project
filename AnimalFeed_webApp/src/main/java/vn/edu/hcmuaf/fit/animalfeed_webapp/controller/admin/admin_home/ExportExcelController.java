package vn.edu.hcmuaf.fit.animalfeed_webapp.controller.admin.admin_home;


import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import vn.edu.hcmuaf.fit.animalfeed_webapp.dao.dto.UserStats;
import vn.edu.hcmuaf.fit.animalfeed_webapp.services.OrderService;
import vn.edu.hcmuaf.fit.animalfeed_webapp.services.ProductService;
import vn.edu.hcmuaf.fit.animalfeed_webapp.services.UserService;

import java.io.IOException;
import java.util.List;

@WebServlet(name = "ExportExcelController", value = "/exportExcel")
public class ExportExcelController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ProductService productService = new ProductService();
        UserService userService = new UserService();
        OrderService orderService = new OrderService();

        int totalOrder = orderService.getTotalOrder();
        double totalRevenue = orderService.getTotalRevenue();
        int totalUser = userService.getTotalUser();
        int totalProduct = productService.getTotalProduct();

        List<UserStats> userStats = (List<UserStats>) request.getSession().getAttribute("getUserStats");

        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Thống kê chi tiết");

        // Tạo header
        Row headerRow = sheet.createRow(0);
        headerRow.createCell(0).setCellValue("STT");
        headerRow.createCell(1).setCellValue("Tên khách hàng");
        headerRow.createCell(2).setCellValue("Số điện thoại");
        headerRow.createCell(3).setCellValue("Đơn đã đặt");
        headerRow.createCell(4).setCellValue("Số sản phẩm");
        headerRow.createCell(5).setCellValue("Tổng tiền phải trả");

        // Điền dữ liệu
        int rowNum = 1;
        for (UserStats user : userStats) {
            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(rowNum - 1);
            row.createCell(1).setCellValue(user.getFullName());
            row.createCell(2).setCellValue(user.getPhone());
            row.createCell(3).setCellValue(user.getTotalOrders());
            row.createCell(4).setCellValue(user.getTotalProductsOrdered());
            row.createCell(5).setCellValue(user.getTotalAmountToPay());
        }

        // Thiết lập response
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setHeader("Content-Disposition", "attachment; filename=user_stats.xlsx");

        // Ghi file Excel vào response
        workbook.write(response.getOutputStream());
        workbook.close();

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

}