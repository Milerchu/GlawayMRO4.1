package com.glaway.sddq.back.Interface.webservice.erp.jxtask;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 * 
 * @ClassName SqnInfo
 * @Description MES或者ERP传递的生产产品的序列号及其他相关细信息
 * @author public2175
 * @Date 2018-8-8 下午7:22:07
 * @version 1.0.0
 */
@XmlRootElement
public class SqnInfo implements Serializable{

    /**
     * @Field @serialVersionUID : 默认序列号ID
     */
    private static final long serialVersionUID = 1L;
    
    //产品序列号
    @XmlElement
    public String sqn;
    
    //生产订单号
    @XmlElement
    public String productionordernum;
    
    //工厂代码
    @XmlElement
    public String factory;
    
    //工厂描述
    @XmlElement
    public String factorydesc;
    
    //物料编码
    @XmlElement
    public String itemnum;
    
    //物料描述
    @XmlElement
    public String itemdesc;
    
    //库存地点
    @XmlElement
    public String loc;
    
    //日期
    @XmlElement
    public String addDate;

    @XmlTransient
    public String getSqn() {
        return sqn;
    }

    
    public void setSqn(String sqn) {
        this.sqn = sqn;
    }

    @XmlTransient
    public String getProductionordernum() {
        return productionordernum;
    }

    
    public void setProductionordernum(String productionordernum) {
        this.productionordernum = productionordernum;
    }

    @XmlTransient
    public String getFactory() {
        return factory;
    }

    
    public void setFactory(String factory) {
        this.factory = factory;
    }

    @XmlTransient
    public String getFactorydesc() {
        return factorydesc;
    }

    
    public void setFactorydesc(String factorydesc) {
        this.factorydesc = factorydesc;
    }

    @XmlTransient
    public String getItemnum() {
        return itemnum;
    }

    
    public void setItemnum(String itemnum) {
        this.itemnum = itemnum;
    }

    @XmlTransient
    public String getItemdesc() {
        return itemdesc;
    }

    
    public void setItemdesc(String itemdesc) {
        this.itemdesc = itemdesc;
    }

    @XmlTransient
    public String getLoc() {
        return loc;
    }

    
    public void setLoc(String loc) {
        this.loc = loc;
    }

    @XmlTransient
    public String getAddDate() {
        return addDate;
    }

    
    public void setAddDate(String addDate) {
        this.addDate = addDate;
    }
}
