package vn.edu.hcmuaf.fit.animalfeed_webapp.dao;

import org.jdbi.v3.core.Jdbi;
import vn.edu.hcmuaf.fit.animalfeed_webapp.dao.db.JdbiConnect;
import vn.edu.hcmuaf.fit.animalfeed_webapp.dao.model.Address;

import java.util.ArrayList;
import java.util.List;

public class AddressDao {
    private static Jdbi jdbi = JdbiConnect.getJdbi();

    // Thêm địa chỉ mới
    public boolean addAddress(Address address) {
        int rowsInserted = jdbi.withHandle(handle ->
                handle.createUpdate("INSERT INTO addresses (user_id, detail, ward, district, province, note) VALUES (:userId, :detail, :ward, :district, :province, :note)")
                        .bind("userId", address.getUserId())
                        .bind("detail", address.getDetail())
                        .bind("ward", address.getWard())
                        .bind("district", address.getDistrict())
                        .bind("province", address.getProvince())
                        .bind("note", address.getNote()) // Đảm bảo trường note được bind
                        .execute()
        );
        return rowsInserted > 0;
    }

    // Lấy danh sách địa chỉ theo userId
    public List<Address> getAddressesByUserId(int userId) {
        return jdbi.withHandle(handle ->
                handle.createQuery("SELECT * FROM addresses WHERE user_id = :userId")
                        .bind("userId", userId)
                        .mapToBean(Address.class)
                        .list()
        );
    }

    // Lấy địa chỉ theo ID (nếu cần trong tương lai)
    public Address getAddressById(int id) {
        return jdbi.withHandle(handle ->
                handle.createQuery("SELECT * FROM addresses WHERE id = :id")
                        .bind("id", id)
                        .mapToBean(Address.class)
                        .findOne()
                        .orElse(null)
        );
    }

    // Xóa địa chỉ theo ID (nếu cần trong tương lai)
    public boolean deleteAddress(int id) {
        int rowsDeleted = jdbi.withHandle(handle ->
                handle.createUpdate("DELETE FROM addresses WHERE id = :id")
                        .bind("id", id)
                        .execute()
        );
        return rowsDeleted > 0;
    }

    // Cập nhật địa chỉ (nếu cần trong tương lai)
    public boolean updateAddress(Address address) {
        int rowsUpdated = jdbi.withHandle(handle ->
                handle.createUpdate("UPDATE addresses SET detail = :detail, ward = :ward, district = :district, province = :province, note = :note WHERE id = :id")
                        .bind("id", address.getId())
                        .bind("detail", address.getDetail())
                        .bind("ward", address.getWard())
                        .bind("district", address.getDistrict())
                        .bind("province", address.getProvince())
                        .bind("note", address.getNote())
                        .execute()
        );
        return rowsUpdated > 0;
    }

    // Test phương thức
    public static void main(String[] args) {
        AddressDao addressDAO = new AddressDao();
        List<Address> addresses = addressDAO.getAddressesByUserId(1); // Thay userId bằng giá trị thực tế để test
        for (Address address : addresses) {
            System.out.println(address);
        }
    }
}