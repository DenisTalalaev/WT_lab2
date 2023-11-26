package bsuir.dtalalaev.lab2.entities;
public class Product {
    private int productId;
    private String productName;
    private String productDescription;
    private double productPrice;
    private byte[] productImage; // Изменили тип на массив байт
    private int productCount;

    // Конструкторы, геттеры и сеттеры

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
}
