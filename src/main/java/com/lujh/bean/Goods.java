package com.lujh.bean;

public class Goods {
    private Integer id;

    private String name;

    private Integer price;

    private Integer discountprice;

    private Byte type;    // 1热销 2主打 3特价 4服装 5电器 6食品 7书籍

    private String introduction;

    private Integer number;

    private String image;

    private String introductionimage;

    private Integer sale;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Integer getDiscountprice() {
        return discountprice;
    }

    public void setDiscountprice(Integer discountprice) {
        this.discountprice = discountprice;
    }

    public Byte getType() {
        return type;
    }

    public void setType(Byte type) {
        this.type = type;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction == null ? null : introduction.trim();
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image == null ? null : image.trim();
    }

    public String getIntroductionimage() {
        return introductionimage;
    }

    public void setIntroductionimage(String introductionimage) {
        this.introductionimage = introductionimage == null ? null : introductionimage.trim();
    }

    public Integer getSale() {
        return sale;
    }

    public void setSale(Integer sale) {
        this.sale = sale;
    }
}