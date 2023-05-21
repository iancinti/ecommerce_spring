package net.vatri.ecommerce.cart;

import net.vatri.ecommerce.models.Order;

import java.util.Set;

public interface CartService {
    String createNewCart();
    void addProduct(String cartId, CartItem cartItem);
    void removeProduct(String cartId, String productId);
    void setProductQuantity(String cartId, String productId, int quantity);
    Set<CartItem> getItems(String cartId);
    Order createOrder(String cartId, Order order);
}

