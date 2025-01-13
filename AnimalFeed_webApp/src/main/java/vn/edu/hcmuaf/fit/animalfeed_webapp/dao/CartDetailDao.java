package vn.edu.hcmuaf.fit.animalfeed_webapp.dao;

import org.jdbi.v3.core.Jdbi;
import vn.edu.hcmuaf.fit.animalfeed_webapp.dao.db.JdbiConnect;
import vn.edu.hcmuaf.fit.animalfeed_webapp.dao.model.CartDetail;
import vn.edu.hcmuaf.fit.animalfeed_webapp.dao.cart.CartItem;

import java.util.ArrayList;
import java.util.List;

public class CartDetailDao {
    private static Jdbi jdbi = JdbiConnect.getJdbi();

    public List<CartItem> getCartDetailByUser(int id) {
        return jdbi.withHandle(handle -> handle.createQuery(
                        "SELECT cd.*, p.name, p.img, p.price, p.description FROM cart_details cd JOIN products p ON cd.product_id = p.id WHERE cd.user_id = :id")
                .bind("id", id)
                .mapToBean(CartItem.class)
                .list());
    }

    public boolean getCDById(int productId, int userId) {
        return jdbi.withHandle(handle -> handle.createQuery(
                "SELECT * FROM cart_details WHERE product_id = :productId AND user_id = :userId")
                .bind("productId", productId)
                .bind("userId", userId)
                .mapToBean(CartDetail.class)
                .findOne()
                .isPresent());
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

    public void increaseQuantity(int productId, int userId) {
        int rowsUpdate = jdbi.withHandle(handle ->
                handle.createUpdate("UPDATE cart_details SET quantity = quantity + 1 WHERE product_id = :productId AND user_id = :userId ")
                        .bind("productId", productId)
                        .bind("userId", userId)
                        .execute()
        );
        System.out.println("Succesfull change quantity in database: " + rowsUpdate);
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

    public void updateStatus(int productId, int userId, int status) {
        int rowsUpdate = jdbi.withHandle(handle ->
                handle.createUpdate("UPDATE cart_details SET status = :status WHERE product_id = :productId AND user_id = :userId ")
                        .bind("status", status)
                        .bind("productId", productId)
                        .bind("userId", userId)
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

    public static void main(String[] args) {
        System.out.println(new CartDetailDao().getCartDetailByUser(2));
    }
}
