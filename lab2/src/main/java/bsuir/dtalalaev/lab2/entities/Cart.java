package bsuir.dtalalaev.lab2.entities;

import bsuir.dtalalaev.lab2.dbcontrollers.DataBase;

public class Cart {
    private int cartId;
    private int userId;
    private int productId;
    private int count;

    private Product product;

    public Cart(int cartId, int userId, int productId, int count) {
        this.cartId = cartId;
        this.userId = userId;
        this.productId = productId;
        this.count = count;
        this.product = DataBase.getProductById(productId);
    }

    public int getCartId() {
        return cartId;
    }

    public int getUserId() {
        return userId;
    }

    public int getProductId() {
        return productId;
    }

    public int getCount() {
        return count;
    }

    public Product getProduct() {
        return product;
    }
}
