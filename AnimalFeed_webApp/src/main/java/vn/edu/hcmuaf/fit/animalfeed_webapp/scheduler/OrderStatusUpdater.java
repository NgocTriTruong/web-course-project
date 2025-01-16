package vn.edu.hcmuaf.fit.animalfeed_webapp.scheduler;

import jakarta.servlet.ServletContextListener;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.annotation.WebListener;
import vn.edu.hcmuaf.fit.animalfeed_webapp.dao.model.Order;
import vn.edu.hcmuaf.fit.animalfeed_webapp.services.OrderService;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@WebListener
public class OrderStatusUpdater implements ServletContextListener {
    private ScheduledExecutorService scheduler;
    private OrderService orderService;

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        scheduler = Executors.newSingleThreadScheduledExecutor();
        orderService = new OrderService();

        // Run the status update check every 30 minutes
        scheduler.scheduleAtFixedRate(this::updateOrderStatuses, 0, 10, TimeUnit.SECONDS);
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        scheduler.shutdownNow();
    }

    private void updateOrderStatuses() {
        try {
            List<Order> orders = orderService.getAllOrders();
            LocalDateTime now = LocalDateTime.now();

            for (Order order : orders) {
                if (order.getStatus() == 1) { // Chờ xác nhận
                    long hoursElapsed = ChronoUnit.HOURS.between(order.getOrderDate(), now);
                    if (hoursElapsed >= 3) {
                        order.setStatus(2); // Update to Đang chuẩn bị
                        orderService.updateOrderStatus(order.getId(), 2);
                    }
                }
                else if (order.getStatus() == 2) { // Đang chuẩn bị
                    long daysElapsed = ChronoUnit.DAYS.between(order.getOrderDate(), now);
                    if (daysElapsed >= 1) {
                        order.setStatus(3); // Update to Đang vận chuyển
                        orderService.updateOrderStatus(order.getId(), 3);
                    }
                }
                else if (order.getStatus() == 3) { // Đang vận chuyển
                    long daysElapsed = ChronoUnit.DAYS.between(order.getOrderDate(), now);
                    if (daysElapsed >= 2) {
                        order.setStatus(4); // Update to Hoàn tất
                        orderService.updateOrderStatus(order.getId(), 4);
                        orderService.updateShippingDate(order.getId(), now);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}