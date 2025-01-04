package vn.edu.hcmuaf.fit.animalfeed_webapp.dao;

import org.jdbi.v3.core.Jdbi;
import vn.edu.hcmuaf.fit.animalfeed_webapp.dao.db.JdbiConnect;
import vn.edu.hcmuaf.fit.animalfeed_webapp.dao.model.Order;
import vn.edu.hcmuaf.fit.animalfeed_webapp.dao.model.OrderDetail;

import java.sql.SQLException;
import java.util.ArrayList;

public class OrderDetailDao {
    private static Jdbi jdbi = JdbiConnect.getJdbi();


    public void insertOrderDetail(OrderDetail orderDetail) {
        int rowsInserted = jdbi.withHandle(handle ->
                handle.createUpdate("INSERT INTO order_details (order_id, product_id, quantity, total_price) VALUES (:order_id, :product_id, :quantity, :total_price)")
                        .bind("order_id", orderDetail.getOrderId())
                        .bind("product_id", orderDetail.getProductId())
                        .bind("quantity", orderDetail.getQuantity())
                        .bind("total_price", orderDetail.getTotalPrice())
                        .execute()
        );
        System.out.println(rowsInserted);
    }
}
