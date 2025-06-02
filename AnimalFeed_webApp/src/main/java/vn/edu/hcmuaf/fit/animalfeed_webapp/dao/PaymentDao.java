package vn.edu.hcmuaf.fit.animalfeed_webapp.dao;

import org.jdbi.v3.core.Jdbi;
import vn.edu.hcmuaf.fit.animalfeed_webapp.dao.db.JdbiConnect;
import vn.edu.hcmuaf.fit.animalfeed_webapp.dao.model.Payment;

import java.util.List;

public class PaymentDao {
    private static Jdbi jdbi = JdbiConnect.getJdbi();

    public List<Payment> getAll() {
        try {
            return jdbi.withHandle(handle -> {
                return handle.createQuery("SELECT * FROM payments")
                        .mapToBean(Payment.class)
                        .list();
            });
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // Add payment to database
    public void addPayment(Payment payment) {
        try {
            jdbi.useHandle(handle -> {
                handle.createUpdate("INSERT INTO payments (date, method, order_id, user_id, status) " +
                                "VALUES (:payDate, :method, :orderId, :userId, :status)")
                        .bindBean(payment)
                        .execute();
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Get payment by id
    public Payment getPaymentById(int id) {
        try {
            return jdbi.withHandle(handle -> {
                return handle.createQuery("SELECT * FROM payments WHERE id = :id")
                        .bind("id", id)
                        .mapToBean(Payment.class)
                        .findOnly();
            });
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // Get payment by order id
    public Payment getPaymentByOrderId(int orderId) {
        try {
            return jdbi.withHandle(handle -> {
                return handle.createQuery("SELECT * FROM payments WHERE order_id = :orderId")
                        .bind("orderId", orderId)
                        .mapToBean(Payment.class)
                        .findFirst() // Trả về Optional<Payment>
                        .orElse(null); // Trả về null nếu không tìm thấy
            });
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    //update payment
    public void updatePayment(Payment payment) {
        try {
            jdbi.useHandle(handle -> {
                handle.createUpdate("UPDATE payments SET date = :payDate, method = :method, order_id = :orderId, user_id = :userId WHERE id = :id")
                        .bindBean(payment)
                        .execute();
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //update payment status
    public void updatePaymentStatus(int orderId, int status) {
        try {
            jdbi.useHandle(handle -> {
                handle.createUpdate("UPDATE payments SET status = :status WHERE orderId = :orderId")
                        .bind("orderId", orderId)
                        .bind("status", status)
                        .execute();
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
