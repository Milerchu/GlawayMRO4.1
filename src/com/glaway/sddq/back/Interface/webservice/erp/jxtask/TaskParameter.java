package com.glaway.sddq.back.Interface.webservice.erp.jxtask;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

@XmlRootElement
public class TaskParameter implements Serializable {
    
    /**
     * @Field @serialVersionUID : 
     */
    private static final long serialVersionUID = 1L;
    //ERP车型
    @XmlElement
    public String erpcmodel;
	//车号
	@XmlElement
	public String carno;
	//修程
	@XmlElement
	public String repairprocess;
	//工厂
	@XmlElement
	public String factory;
	//办事处
	@XmlElement
	public String whichoffice;
	@XmlElement
	//检修产品编码
	public String jxcode;
	@XmlElement
	//检修产品编码
	public String jxdesc;
	@XmlElement
	//库存地点
	public String stock;
	@XmlElement
	//调度员
	public String dispatcher;		
	//数量
	@XmlElement
	public int amount;
	//责任人
	@XmlElement
	public String liableperson;
	//计划开始时间
	@XmlElement
	public String planstarttime;
	//计划结束时间
	@XmlElement
	public String planendtime;
	//ERP生产订单编号
	@XmlElement
	public String productionordernum;
	//备注
	@XmlElement
	public String remark;
	
	//检修工序
	//@XmlElement
	public List<JxTaskItem> jxtaskitem = new ArrayList<JxTaskItem>();   

	//必换件
	//@XmlElement
	public List<Mustchangeinfo> mustchangeinfo = new ArrayList<Mustchangeinfo>();
	
	@XmlTransient
	public String getCarno() {
		return carno;
	}
	public void setCarno(String carno) {
		this.carno = carno;
	}
	@XmlTransient
	public String getRepairprocess() {
		return repairprocess;
	}
	public void setRepairprocess(String repairprocess) {
		this.repairprocess = repairprocess;
	}
	@XmlTransient
	public int getAmount() {
		return amount;
	}
	public void setAmount(int amount) {
		this.amount = amount;
	}
	@XmlTransient
	public String getLiableperson() {
		return liableperson;
	}
	public void setLiableperson(String liableperson) {
		this.liableperson = liableperson;
	}
	@XmlTransient
	public String getPlanstarttime() {
		return planstarttime;
	}
	public void setPlanstarttime(String planstarttime) {
		this.planstarttime = planstarttime;
	}
	@XmlTransient
	public String getPlanendtime() {
		return planendtime;
	}
	public void setPlanendtime(String planendtime) {
		this.planendtime = planendtime;
	}
	@XmlTransient
	public String getProductionordernum() {
		return productionordernum;
	}
	public void setProductionordernum(String productionordernum) {
		this.productionordernum = productionordernum;
	}
	@XmlTransient
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	@XmlTransient
	public List<JxTaskItem> getJxtaskitem() {
		return jxtaskitem;
	}
	public void setJxtaskitem(List<JxTaskItem> jxtaskitem) {
		this.jxtaskitem = jxtaskitem;
	}	
	@XmlTransient
	public List<Mustchangeinfo> getMustchangeinfo() {
		return mustchangeinfo;
	}
	public void setMustchangeinfo(List<Mustchangeinfo> mustchangeinfo) {
		this.mustchangeinfo = mustchangeinfo;
	}
	@XmlTransient
	public String getWhichoffice() {
		return whichoffice;
	}
	public void setWhichoffice(String whichoffice) {
		this.whichoffice = whichoffice;
	}
	@XmlTransient
	public String getJxcode() {
		return jxcode;
	}
	public void setJxcode(String jxcode) {
		this.jxcode = jxcode;
	}
	@XmlTransient
	public String getErpcmodel() {
		return erpcmodel;
	}
	public void setErpcmodel(String erpcmodel) {
		this.erpcmodel = erpcmodel;
	}	
	@XmlTransient
	public String getStock() {
		return stock;
	}
	public void setStock(String stock) {
		this.stock = stock;
	}
	@XmlTransient
	public String getDispatcher() {
		return dispatcher;
	}
	public void setDispatcher(String dispatcher) {
		this.dispatcher = dispatcher;
	}
	@XmlTransient
	public String getFactory() {
		return factory;
	}
	
	public void setFactory(String factory) {
		this.factory = factory;
	}
	@XmlTransient
	public String getJxdesc() {
		return jxdesc;
	}
	public void setJxdesc(String jxdesc) {
		this.jxdesc = jxdesc;
	}	
}
