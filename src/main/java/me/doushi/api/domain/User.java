package me.doushi.api.domain;

/**
 * 用户实体类
 * Created by songlijun on 15/11/7.
 */
public class User {

    private Integer id ;
    private String nickName ;//'昵称'
    private String password;
    private String headImage ;//'用户头像'
    private String phone ;//'手机号',
    private Integer gender;//'性别1:男2:女'
    private String platformId;// '第三方登录平台id'
    private String platformName;// '第三方登录平台'



    public User() {
        super();
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", nickName='" + nickName + '\'' +
                ", headImage='" + headImage + '\'' +
                ", phone='" + phone + '\'' +
                ", gender=" + gender +
                ", platformId='" + platformId + '\'' +
                ", platformName='" + platformName + '\'' +
                '}';
    }

    public User(Integer id, String nickName, String password, String headImage, String phone, Integer gender, String platformId, String platformName) {
        this.id = id;
        this.nickName = nickName;
        this.password = password;
        this.headImage = headImage;
        this.phone = phone;
        this.gender = gender;
        this.platformId = platformId;
        this.platformName = platformName;
    }


    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getHeadImage() {
        return headImage;
    }

    public void setHeadImage(String headImage) {
        this.headImage = headImage;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Integer getGender() {
        return gender;
    }

    public void setGender(Integer gender) {
        this.gender = gender;
    }

    public String getPlatformId() {
        return platformId;
    }

    public void setPlatformId(String platformId) {
        this.platformId = platformId;
    }

    public String getPlatformName() {
        return platformName;
    }

    public void setPlatformName(String platformName) {
        this.platformName = platformName;
    }


}
