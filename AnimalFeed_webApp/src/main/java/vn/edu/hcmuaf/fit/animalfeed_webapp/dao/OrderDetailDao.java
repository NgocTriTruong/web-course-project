package vn.edu.hcmuaf.fit.animalfeed_webapp.dao;

import org.jdbi.v3.core.Jdbi;
import vn.edu.hcmuaf.fit.animalfeed_webapp.dao.db.JdbiConnect;
import vn.edu.hcmuaf.fit.animalfeed_webapp.dao.dto.OrderDetailsWithProduct;
import vn.edu.hcmuaf.fit.animalfeed_webapp.dao.model.OrderDetail;
import vn.edu.hcmuaf.fit.animalfeed_webapp.dao.model.Product;

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
        System.out.println("Rows inserted: " + rowsInserted);
    }

    public ArrayList<OrderDetail> getODsByOrderId(int orderId) {
        return (ArrayList<OrderDetail>) jdbi.withHandle(handle -> handle.createQuery("select * from order_details where order_id = :orderId")
                .bind("orderId", orderId)
                .mapToBean(OrderDetail.class)
                .list());
    }

    public ArrayList<OrderDetailsWithProduct> getOrderDetailsWithProductByOrderId(int orderId) {
        return jdbi.withHandle(handle -> {
            String sql = """
                SELECT od.*, p.*
                FROM order_details od
                JOIN products p ON od.product_id = p.id
                WHERE od.order_id = :orderId
                """;

            return handle.createQuery(sql)
                    .bind("orderId", orderId)
                    .reduceRows(new ArrayList<OrderDetailsWithProduct>(), (list, row) -> {
                        OrderDetail orderDetail = new OrderDetail(
                                row.getColumn("id", Integer.class),
                                row.getColumn("order_id", Integer.class),
                                row.getColumn("product_id", Integer.class),
                                row.getColumn("quantity", Integer.class),
                                row.getColumn("total_price", Double.class)
                        );

                        Product product = new Product();
                        product.setId(row.getColumn("id", Integer.class));
                        product.setName(row.getColumn("name", String.class));
                        product.setDescription(row.getColumn("description", String.class));
                        product.setPrice(row.getColumn("price", Double.class));
                        product.setImg(row.getColumn("img", String.class));
                        // Set other product properties as needed

                        list.add(new OrderDetailsWithProduct(orderDetail, product));
                        return list;
                    });
        });
    }

    // Thêm phương thức deleteOrderDetailsByOrderId
    public void deleteOrderDetailsByOrderId(int orderId) {
        int rowsDeleted = jdbi.withHandle(handle ->
                handle.createUpdate("DELETE FROM order_details WHERE order_id = :orderId")
                        .bind("orderId", orderId)
                        .execute()
        );
        System.out.println("Rows deleted: " + rowsDeleted);
    }
}