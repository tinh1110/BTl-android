package Model;

import java.io.Serializable;

public class CartModel implements Serializable {
    String productName;
    String productPrice;
    String currentDate;
    String currentTime;
    String totalQuantity;
    int totalPrice;
    private String id;

    public CartModel() {
    }

    public CartModel(String productName, String productPrice, String currentDate, String currentTime, String totalQuantity, int totalPrice) {
        this.productName = productName;
        this.productPrice = productPrice;
        this.currentDate = currentDate;
        this.currentTime = currentTime;
        this.totalQuantity = totalQuantity;
        this.totalPrice = totalPrice;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getProductName() {
        return productName;
    }

    public String getProductPrice() {
        return productPrice;
    }

    public String getCurrentDate() {
        return currentDate;
    }

    public String getCurrentTime() {
        return currentTime;
    }

    public String getTotalQuantity() {
        return totalQuantity;
    }

    public int getTotalPrice() {
        return totalPrice;
    }
}
