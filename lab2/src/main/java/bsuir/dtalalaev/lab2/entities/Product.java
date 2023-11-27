package bsuir.dtalalaev.lab2.entities;

import java.util.Arrays;

/**
Class for Product entity
Product - entity with name, description, price and image.
Useed for manipulate products in catalog

 */
public class Product {
    private int productId;
    private String productName;
    private String productDescription;
    private double productPrice;
    private byte[] productImage;
    private int productCount;


    public Product(int productId, String productName, String productDescription,
                   double productPrice, byte[] productImage, int productCount) {
        this.productId = productId;
        this.productName = productName;
        this.productDescription = productDescription;
        this.productPrice = productPrice;
        this.productImage = productImage;
        this.productCount = productCount;
    }

    public int getProductId() {
        return productId;
    }

    public String getProductName() {
        return productName;
    }

    public String getProductDescription() {
        return productDescription;
    }

    public double getProductPrice() {
        return productPrice;
    }

    public byte[] getProductImage() {
        return productImage;
    }

    public int getProductCount() {
        return productCount;
    }

    @Override
    public String toString() {
        return "Product{" +
                "productId=" + productId +
                ", productName='" + productName + '\'' +
                ", productDescription='" + productDescription + '\'' +
                ", productPrice=" + productPrice +
                ", productCount=" + productCount +
                '}';
    }
}
