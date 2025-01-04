package vn.edu.hcmuaf.fit.animalfeed_webapp.dao;

import org.jdbi.v3.core.Jdbi;
import org.jdbi.v3.core.JdbiException;
import vn.edu.hcmuaf.fit.animalfeed_webapp.dao.db.JdbiConnect;
import vn.edu.hcmuaf.fit.animalfeed_webapp.dao.model.CartDetail;
import vn.edu.hcmuaf.fit.animalfeed_webapp.dao.model.Category;

import java.sql.SQLException;
import java.util.ArrayList;

public class CartDetailDao {
    private static Jdbi jdbi = JdbiConnect.getJdbi();

    public CartDetail getCartDetailByUser(int id) {
        return jdbi.withHandle(handle -> handle.createQuery("select * from cart_details where user_id = :id and status = 1").bind("id", id).mapToBean(CartDetail.class).findOne().orElse(null));
    }

    public void insertCD(CartDetail cd) {
        int rowsInserted = jdbi.withHandle(handle ->
                handle.createUpdate("INSERT INTO cart_details (user_id, product_id, total, quantity, status) VALUES (:userId, :productId, :total, :quantity, :status)")
                        .bind("userId", cd.getUserId())
                        .bind("productId", cd.getProductId())
                        .bind("total", cd.getTotal())
                        .bind("quantity", cd.getQuantity())
                        .bind("status", cd.getStatus())
                        .execute()
        );
        System.out.println("Succesfull insert into database: " + rowsInserted);
    }

    public void updateQuantity(int productId, int userId, int quantity) {
        int rowsUpdate = jdbi.withHandle(handle ->
                handle.createUpdate("UPDATE cart_details SET quantity = :quantity WHERE product_id = :productId AND user_id = :userId ")
                        .bind("quantity", quantity)
                        .bind("productId", productId)
                        .bind("userId", userId)
                        .execute()
        );
        System.out.println("Succesfull change quantity in database: " + rowsUpdate);
    }

    public void updateStatus(int id, int status) {
        int rowsUpdate = jdbi.withHandle(handle ->
                handle.createUpdate("UPDATE cart_details SET status = :status WHERE id = :id")
                        .bind("status", status)
                        .bind("id", id)
                        .execute()
        );
        System.out.println("Successfully updated status in database: " + rowsUpdate);
    }

    // Method to delete a user by username
    public void deleteCD(int productId, int userId) {
        int rowsDeleted = jdbi.withHandle(handle ->
                handle.createUpdate("DELETE FROM cart_details WHERE product_id = :productId AND user_id = :userId")
                        .bind("productId", productId)
                        .bind("userId", userId)
                        .execute()
        );
        System.out.println(rowsDeleted);
    }

    // Method to list all users
    public ArrayList<CartDetail> getAllCD() {
        return (ArrayList<CartDetail>) jdbi.withHandle(handle -> handle.createQuery("select * from cart_details").mapToBean(CartDetail.class).list());
    }
}
