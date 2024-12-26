package vn.edu.hcmuaf.fit.animalfeed_webapp.dao.cart;

import vn.edu.hcmuaf.fit.animalfeed_webapp.dao.model.CartDetail;
import vn.edu.hcmuaf.fit.animalfeed_webapp.dao.model.Product;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

public class Cart {
    Map<Integer, CartDetail> cartData = new HashMap<>();

    public boolean addProduct(Product product) {
        if (cartData.containsKey(product.getId())) {
            updateQuantity(product.getId(), cartData.get(product.getId()).getQuantity()+1);
            return true;
        }
        cartData.put(product.getId(), convert(product));
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

    public boolean removeProduct(int productId) {
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

    private CartDetail convert(Product product) {
        CartDetail cartDetail = new CartDetail();
//        cartDetail.setUserId(product.getId());
        cartDetail.setProductId(product.getId());
        cartDetail.setQuantity(1);
        cartDetail.setTotal(product.getPrice() * cartDetail.getQuantity());
        cartDetail.setStatus(0);
        return cartDetail;
    }
}
