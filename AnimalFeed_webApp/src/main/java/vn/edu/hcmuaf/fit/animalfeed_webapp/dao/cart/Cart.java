package vn.edu.hcmuaf.fit.animalfeed_webapp.dao.cart;

import vn.edu.hcmuaf.fit.animalfeed_webapp.dao.CartDetailDao;
import vn.edu.hcmuaf.fit.animalfeed_webapp.dao.model.CartDetail;
import vn.edu.hcmuaf.fit.animalfeed_webapp.dao.model.Product;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

public class Cart {
    private Map<Integer, CartItem> cartData = new HashMap<>();

    public boolean addProduct(Product product, int userId) {
        if (cartData.containsKey(product.getId())) {
            updateQuantity(product.getId(), cartData.get(product.getId()).getQuantity()+1);
            return true;
        }
        CartDetail cartDetail = convert(product, userId);
        CartItem cartItem = new CartItem(cartDetail, product);

        cartData.put(product.getId(), cartItem);
        return true;
    }

    public boolean updateQuantity(int productId, int quantity) {
        if (!cartData.containsKey(productId)) {
            return false;
        }
        cartData.get(productId).setQuantity(quantity);
        return true;
    }

    public boolean removeProduct(int productId) {
        return cartData.remove(productId) != null;
    }

    public List<CartItem> getCartDetails() {
        return new ArrayList<>(cartData.values());
    }

    public int getTotalQuantity() {
        AtomicInteger total = new AtomicInteger(0);
        cartData.forEach((key, value) -> total.addAndGet(value.getQuantity()));
        return total.get();
    }

    public double getTotalPrice() {
        return cartData.values().stream()
                .filter(cartItem -> cartItem.getStatus() == 1) // Only include items with status = 1
                .mapToDouble(CartItem::getTotal) // Extract the total price
                .sum(); // Sum the filtered totals
    }

    public List<CartItem> getConfirmedCartItem() {
        List<CartItem> confirmedCartItems = new ArrayList<>();
        for (CartItem cartItem : cartData.values()) {
            if (cartItem.getStatus() == 1) {
                confirmedCartItems.add(cartItem);
            }
        }
        return confirmedCartItems;
    }

    private CartDetail convert(Product product, int userId) {
        CartDetail cartDetail = new CartDetail();
        cartDetail.setUserId(userId);
        cartDetail.setProductId(product.getId());
        cartDetail.setQuantity(1);
        cartDetail.setTotal(product.getPrice() * cartDetail.getQuantity());
        cartDetail.setStatus(1);
        return cartDetail;
    }

    public void syncDatabase(int userId) {
        CartDetailDao dao = new CartDetailDao();
        List<CartItem> dbItems = dao.getCartDetailByUser(userId);
        cartData.clear();
        for (CartItem item : dbItems) {
            cartData.put(item.getProductId(), item);
        }
    }

    public boolean updateStatus(int productId, int status) {
        if (!cartData.containsKey(productId)) {
            return false;
        }
        cartData.get(productId).setStatus(status);
        return true;
    }
}
