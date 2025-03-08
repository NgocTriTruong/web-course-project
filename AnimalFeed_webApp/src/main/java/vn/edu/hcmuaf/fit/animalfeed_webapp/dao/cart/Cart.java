package vn.edu.hcmuaf.fit.animalfeed_webapp.dao.cart;

import vn.edu.hcmuaf.fit.animalfeed_webapp.dao.CartDetailDao;
import vn.edu.hcmuaf.fit.animalfeed_webapp.dao.DiscountDao;
import vn.edu.hcmuaf.fit.animalfeed_webapp.dao.model.CartDetail;
import vn.edu.hcmuaf.fit.animalfeed_webapp.dao.dto.CartItem;
import vn.edu.hcmuaf.fit.animalfeed_webapp.dao.model.Product;

import java.util.*;

public class Cart {
    private Map<Integer, CartItem> cartData = new HashMap<>();
    private DiscountDao discountDao;

    public Cart() {
        this.discountDao = new DiscountDao();
    }

    public double getDiscountedPrice(Product product) {
        if (product.getDiscountId() <= 0 || product.getDiscountId() == 1) {
            return product.getPrice();
        }
        return discountDao.calculateDiscountedPrice(product.getPrice(), product.getDiscountId());
    }

    public boolean addProduct(Product product, int userId, int quantity) {
        if (cartData.containsKey(product.getId())) {
            int newQuantity = cartData.get(product.getId()).getQuantity() + quantity;
            updateQuantity(product.getId(), newQuantity);
            return true;
        }

        CartDetail cartDetail = convert(product, userId, quantity);
        CartItem cartItem = new CartItem(cartDetail, product);

        // Ensure discounted price is applied
        double discountedPrice = getDiscountedPrice(product);
        cartItem.setUnitPrice(discountedPrice);
        cartItem.setTotal(discountedPrice * quantity);

        cartData.put(product.getId(), cartItem);
        return true;
    }

    public boolean updateQuantity(int productId, int quantity) {
        if (!cartData.containsKey(productId)) {
            return false;
        }
        CartItem cartItem = cartData.get(productId);
        cartItem.setQuantity(quantity);

        // Recalculate total with discounted price
        double discountedPrice = getDiscountedPrice(cartItem.getProduct());
        cartItem.setUnitPrice(discountedPrice);
        cartItem.setTotal(discountedPrice * quantity);
        return true;
    }

    public boolean removeProduct(int productId) {
        return cartData.remove(productId) != null;
    }

    public List<CartItem> getCartDetails() {
        return new ArrayList<>(cartData.values());
    }

    public int getTotalQuantity() {
        return cartData.values().stream()
                .filter(cartItem -> cartItem.getStatus() == 1)
                .mapToInt(CartItem::getQuantity)
                .sum();
    }

    public int getTotalItems() {
        return cartData.values().stream()
                .mapToInt(CartItem::getQuantity)
                .sum();
    }

    public double getTotalPrice() {
        return cartData.values().stream()
                .filter(cartItem -> cartItem.getStatus() == 1)
                .mapToDouble(CartItem::getTotal)
                .sum();
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

    private CartDetail convert(Product product, int userId, int quantity) {
        CartDetail cartDetail = new CartDetail();
        cartDetail.setUserId(userId);
        cartDetail.setProductId(product.getId());
        cartDetail.setQuantity(quantity);

        double discountedPrice = getDiscountedPrice(product);
        cartDetail.setTotal(discountedPrice * quantity);
        cartDetail.setStatus(0);
        return cartDetail;
    }

    public void syncDatabase(int userId) {
        CartDetailDao dao = new CartDetailDao();
        List<CartItem> dbItems = dao.getCartDetailByUser(userId);
        cartData.clear();
        for (CartItem item : dbItems) {
            Product product = item.getProduct();
            if (product != null) {
                double discountedPrice = getDiscountedPrice(product);
                item.setUnitPrice(discountedPrice);
                item.setTotal(discountedPrice * item.getQuantity());
            }
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