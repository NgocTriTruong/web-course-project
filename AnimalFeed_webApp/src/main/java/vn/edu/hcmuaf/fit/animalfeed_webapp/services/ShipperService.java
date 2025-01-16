package vn.edu.hcmuaf.fit.animalfeed_webapp.services;

import vn.edu.hcmuaf.fit.animalfeed_webapp.dao.ShipperDao;
import vn.edu.hcmuaf.fit.animalfeed_webapp.dao.model.Shipper;

import java.util.List;

public class ShipperService {
    private final ShipperDao shipperDao;

    public ShipperService() {
        this.shipperDao = new ShipperDao(); // Khởi tạo đối tượng DAO
    }

    // Thêm shipper mới
    public void addShipper(String name, double salary, String phone, int status, int adminUserId) {
        // Kiểm tra các trường hợp dữ liệu không hợp lệ
        if (name == null || name.isEmpty() || phone == null || phone.isEmpty()) {
            throw new IllegalArgumentException("Tên và số điện thoại không được để trống.");
        }
        if (salary <= 0) {
            throw new IllegalArgumentException("Lương phải lớn hơn 0.");
        }
        if (status != 1 && status != 2) {
            throw new IllegalArgumentException("Trạng thái không hợp lệ.");
        }

        // Tạo đối tượng shipper
        Shipper shipper = new Shipper();
        shipper.setName(name);
        shipper.setSalary(salary);
        shipper.setPhone(phone);
        shipper.setStatus(status);

        // Thêm shipper vào cơ sở dữ liệu qua DAO
        try {
            shipperDao.addShipper(shipper, adminUserId);
        } catch (Exception e) {
            System.err.println("Error in service layer: " + e.getMessage());
            throw new RuntimeException("Không thể thêm shipper. Lỗi hệ thống.");
        }
    }


    // Lấy tất cả các shipper
    public List<Shipper> getAllShippers() {
        return shipperDao.getAllShippers();
    }

    // Cập nhật thông tin shipper
    public void updateShipper(int shipperId, String name, double salary, String phone, int status, int adminUserId) {
        // Kiểm tra các trường hợp dữ liệu không hợp lệ
        if (shipperId <= 0) {
            throw new IllegalArgumentException("ID shipper không hợp lệ.");
        }
        if (name == null || name.isEmpty() || phone == null || phone.isEmpty()) {
            throw new IllegalArgumentException("Tên và số điện thoại không được để trống.");
        }
        if (salary <= 0) {
            throw new IllegalArgumentException("Lương phải lớn hơn 0.");
        }
        if (status != 1 && status != 2) {
            throw new IllegalArgumentException("Trạng thái không hợp lệ.");
        }

        // Tạo đối tượng shipper
        Shipper shipper = new Shipper();
        shipper.setId(shipperId);
        shipper.setName(name);
        shipper.setSalary(salary);
        shipper.setPhone(phone);
        shipper.setStatus(status);

        // Cập nhật shipper trong database qua DAO
        shipperDao.updateShipper(shipper, adminUserId);
    }

    // Xóa shipper theo ID
    public void deleteShipper(int shipperId, int adminUserId) {
        if (shipperId <= 0) {
            throw new IllegalArgumentException("ID shipper không hợp lệ.");
        }

        // Xóa shipper qua DAO
        shipperDao.deleteShipper(shipperId, adminUserId);
    }

    // Lấy thông tin shipper theo ID
    public Shipper getShipperById(int shipperId) {
        if (shipperId <= 0) {
            throw new IllegalArgumentException("ID shipper không hợp lệ.");
        }

        // Lấy thông tin shipper qua DAO
        return shipperDao.getShipperById(shipperId);
    }
}
