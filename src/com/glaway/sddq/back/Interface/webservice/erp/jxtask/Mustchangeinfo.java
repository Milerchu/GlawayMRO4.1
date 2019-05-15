package com.glaway.sddq.back.Interface.webservice.erp.jxtask;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
//必换件信息
@XmlRootElement
public class Mustchangeinfo implements Serializable{
	/**
     * @Field @serialVersionUID : 
     */
    private static final long serialVersionUID = 1L;
    //移动类型
	@XmlElement
	public String mobiletype;
	//库存地点
	@XmlElement
	public String stockaddress;
	//物料编码
	@XmlElement
	public String itemnums;
	@XmlElement
	//数量
	public float amounts;
	@XmlElement
	//计量单位
	public String measurementunit;
	@XmlElement
	//预留号
	public String obligatenum;
	@XmlElement
	//预留行号
	public String obligatelinenum;
	@XmlElement
	//记录类型
	public String recordtype;
	@XmlTransient
	public String getMobiletype() {
		return mobiletype;
	}

	public void setMobiletype(String mobiletype) {
		this.mobiletype = mobiletype;
	}		
	@XmlTransient
	public String getStockaddress() {
		return stockaddress;
	}

	public void setStockaddress(String stockaddress) {
		this.stockaddress = stockaddress;
	}
	@XmlTransient
	public String getItemnums() {
		return itemnums;
	}

	public void setItemnums(String itemnums) {
		this.itemnums = itemnums;
	}

	@XmlTransient
	public String getMeasurementunit() {
		return measurementunit;
	}

	public void setMeasurementunit(String measurementunit) {
		this.measurementunit = measurementunit;
	}
	@XmlTransient
	public String getObligatenum() {
		return obligatenum;
	}

	public void setObligatenum(String obligatenum) {
		this.obligatenum = obligatenum;
	}
	@XmlTransient
	public String getObligatelinenum() {
		return obligatelinenum;
	}

	public void setObligatelinenum(String obligatelinenum) {
		this.obligatelinenum = obligatelinenum;
	}
	@XmlTransient
	public String getRecordtype() {
		return recordtype;
	}

	public void setRecordtype(String recordtype) {
		this.recordtype = recordtype;
	}
	@XmlTransient
	public float getAmounts() {
		return amounts;
	}

	public void setAmounts(float amounts) {
		this.amounts = amounts;
	}
}
