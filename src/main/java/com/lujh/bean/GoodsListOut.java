package com.lujh.bean;

import com.lujh.util.IdCodeUtil;

/**
 * Created by lujianhao on 2018/2/7.
 */
public class GoodsListOut {

    private String eid;

    private String name;

    private Integer discountprice;

    private String image;

    public GoodsListOut() {
    }

    public GoodsListOut(Goods goods) {
        this.eid = IdCodeUtil.encode(goods.getId());
        this.name = goods.getName();
        this.discountprice = goods.getDiscountprice();
        this.image = goods.getImage();
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

    public Integer getDiscountprice() {
        return discountprice;
    }

    public void setDiscountprice(Integer discountprice) {
        this.discountprice = discountprice;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
