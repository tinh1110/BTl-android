package Model;

import java.io.Serializable;

public class ViewallModel implements Serializable {
    String name;
    String description;
    String rate="5.0";
    String type;
    int price;
    String img_Url;

    public ViewallModel() {
    }
    public ViewallModel(String type) {
        this.type = type;
    }

    public ViewallModel(String name, String description, String types, int price, String img_Url) {
        this.name = name;
        this.description = description;
        this.type = types;
        this.price = price;
        this.img_Url = img_Url;
    }

    public ViewallModel(String name, String description, String rate, String type, int price, String img_Url) {
        this.name = name;
        this.description = description;
        this.rate = rate;
        this.type = type;
        this.price = price;
        this.img_Url = img_Url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }


    public String getRate() {
        return rate;
    }


    public String getType() {
        return this.type;
    }


    public int getPrice() {
        return price;
    }


    public String getImg_Url() {
        return img_Url;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public void setImg_Url(String img_Url) {
        this.img_Url = img_Url;
    }

    public void setType(String types) {
        this.type = types;
    }
}
