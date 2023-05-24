package user;

public class User {
    private String email,name,password,profileImg,sex,phone,address;

    public User() {
    }

    public User(String email, String name, String password, String sex, String phone, String address) {
        this.email = email;
        this.name = name;
        this.password = password;
        this.sex = sex;
        this.phone = phone;
        this.address = address;
    }

    public String getProfileImg() {
        return profileImg;
    }

    public String getSex() {
        return sex;
    }

    public String getPhone() {
        return phone;
    }

    public String getAddress() {
        return address;
    }

    public void setProfileImg(String profileImg) {
        this.profileImg = profileImg;
    }

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }
}
