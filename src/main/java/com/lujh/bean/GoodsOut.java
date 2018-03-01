package com.lujh.bean;

import com.lujh.util.IdCodeUtil;
import com.lujh.util.ListUtil;

import java.util.List;

/**
 * Created by lujianhao on 2018/2/6.
 */
public class GoodsOut {

    private String eid;

    private String name;

    private Integer price;

    private Integer discountprice;

    private Byte type;

    private String introduction;

    private Integer number;

    private String image;

    private List<String> introductionimage;

    private Integer sale;

    public GoodsOut() {
    }

    public GoodsOut(Goods goods) {
        if (goods == null) {
            return;
        }
        this.eid = IdCodeUtil.encode(goods.getId());
        this.name = goods.getName();
        this.price = goods.getPrice();
        this.discountprice = goods.getDiscountprice();
        this.type = goods.getType();
        this.introduction = goods.getIntroduction();
        this.number = goods.getNumber();
        this.image = goods.getImage();
        this.introductionimage = ListUtil.toList(goods.getIntroductionimage());
        this.sale = goods.getSale();
    }

    public String getEid() {
        return eid;
    }

    public void setEid(String eid) {
        this.eid = eid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
        this.introduction = introduction;
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
        this.image = image;
    }

    public List<String> getIntroductionimage() {
        return introductionimage;
    }

    public void setIntroductionimage(List<String> introductionimage) {
        this.introductionimage = introductionimage;
    }

    public Integer getSale() {
        return sale;
    }

    public void setSale(Integer sale) {
        this.sale = sale;
    }
}
