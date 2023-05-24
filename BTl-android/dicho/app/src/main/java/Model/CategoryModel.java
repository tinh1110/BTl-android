package Model;

public class CategoryModel {
    String name;
    String description;
    String discount;
    String img_Url;
    String type;

    public CategoryModel() {
    }

    public CategoryModel(String name, String description, String discount, String img_Url) {
        this.name = name;
        this.description = description;
        this.discount = discount;
        this.img_Url = img_Url;
    }

    public String getType() {
        return type;
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



    public String getDiscount() {
        return discount;
    }



    public String getImg_Url() {
        return img_Url;
    }


}
