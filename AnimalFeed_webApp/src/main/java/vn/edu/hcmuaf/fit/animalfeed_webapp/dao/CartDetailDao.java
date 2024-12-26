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

    public Category getCDById(int id) {
        return jdbi.withHandle(handle -> handle.createQuery("select * from cart_details where id = :id").bind("id", id).mapToBean(Category.class).findOne().orElse(null));
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
        System.out.println(rowsInserted);
    }

    public void updateQuantity(int id, int quantity) {
        int rowsUpdate = jdbi.withHandle(handle ->
                handle.createUpdate("UPDATE cart_details SET quantity = :quantity WHERE id = :id")
                        .bind("quantity", quantity)
                        .bind("id", id)
                        .execute()
        );
        System.out.println(rowsUpdate);
    }

    // Method to delete a user by username
    public void deleteCD(int id) {
        int rowsDeleted = jdbi.withHandle(handle ->
                handle.createUpdate("DELETE FROM cart_details WHERE id = :id")
                        .bind("id", id)
                        .execute()
        );
        System.out.println(rowsDeleted);
    }

    // Method to list all users
    public ArrayList<CartDetail> getAllCD() {
        return (ArrayList<CartDetail>) jdbi.withHandle(handle -> handle.createQuery("select * from cart_details").mapToBean(CartDetail.class).list());
    }
}
