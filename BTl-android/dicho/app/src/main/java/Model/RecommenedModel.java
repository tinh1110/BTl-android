package Model;

public class RecommenedModel {
    String name;
    String description;
    String rating;
    String price;
    String img_Url;
    private String type;

    public RecommenedModel() {
    }

    public RecommenedModel(String name, String description, String rating, String price, String img_Url) {
        this.name = name;
        this.description = description;
        this.rating = rating;
        this.price = price;
        this.img_Url = img_Url;
    }

    public String getType() {
        return type;
    }

    public String getName() {
        return name;
    }


    public String getDescription() {
        return description;
    }



    public String getRating() {
        return rating;
    }



    public String getImg_Url() {
        return img_Url;
    }


}
