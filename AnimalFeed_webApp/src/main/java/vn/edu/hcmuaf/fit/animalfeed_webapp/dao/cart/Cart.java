package vn.edu.hcmuaf.fit.animalfeed_webapp.dao.cart;

import vn.edu.hcmuaf.fit.animalfeed_webapp.dao.CartDetailDao;
import vn.edu.hcmuaf.fit.animalfeed_webapp.dao.ProductDao;
import vn.edu.hcmuaf.fit.animalfeed_webapp.dao.UserDao;
import vn.edu.hcmuaf.fit.animalfeed_webapp.dao.model.CartDetail;
import vn.edu.hcmuaf.fit.animalfeed_webapp.dao.model.Product;
import vn.edu.hcmuaf.fit.animalfeed_webapp.dao.model.User;

import java.sql.SQLException;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

public class Cart {
    static Map<Integer, CartDetail> cartData = new HashMap<>();

    public boolean addProduct(Product product, int userId) {
        CartDetailDao cartDetailDao = new CartDetailDao();
        if (cartData.containsKey(product.getId())) {
            updateQuantity(product.getId(), cartData.get(product.getId()).getQuantity()+1);
            cartDetailDao.updateQuantity(convert(product, userId).getId(), cartData.get(product.getId()).getQuantity());
            return true;
        }
        cartData.put(product.getId(), convert(product, userId));
        cartDetailDao.insertCD(convert(product, userId));
        return true;
    }

    public boolean updateQuantity(int productId, int quantity) {
        if (!cartData.containsKey(productId)) {
            return false;
        }
        CartDetail cartDetail = cartData.get(productId);
        cartDetail.setQuantity(quantity);
        return true;
    }

    public boolean removeProduct(int productId, int userId) {
        CartDetailDao cartDetailDao = new CartDetailDao();
        ProductDao productDao = new ProductDao();
        Product product = productDao.getById(productId);
        cartDetailDao.deleteCD(convert(product, userId).getId());
        return cartData.remove(productId) != null;
    }

    public List<CartDetail> getCartDetails() {
        return new ArrayList<>(cartData.values());
    }

    public int getTotalQuantity() {
        AtomicInteger total = new AtomicInteger(0);
        cartData.forEach((key, value) -> total.addAndGet(value.getQuantity()));
        return total.get();
    }

    public double getTotalPrice() {
        AtomicReference<Double> sum = new AtomicReference<>(0.0);
        cartData.values().forEach(cartDetail -> sum.updateAndGet(value -> value + cartDetail.getTotal()));
        return sum.get();
    }

    private CartDetail convert(Product product, int userId) {
        CartDetail cartDetail = new CartDetail();
        cartDetail.setUserId(userId);
        cartDetail.setProductId(product.getId());
        cartDetail.setQuantity(1);
        cartDetail.setTotal(product.getPrice() * cartDetail.getQuantity());
        cartDetail.setStatus(0);
        return cartDetail;
    }

    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        Cart cart = new Cart();
        cart.addProduct(new ProductDao().getById(1), 1);
        System.out.println(cartData.toString());
    }


}
