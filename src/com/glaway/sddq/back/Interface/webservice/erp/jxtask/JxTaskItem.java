package com.glaway.sddq.back.Interface.webservice.erp.jxtask;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
//检修工序
@XmlRootElement
public class JxTaskItem implements Serializable {
	/**
     * @Field @serialVersionUID : 
     */
    private static final long serialVersionUID = 1L;
    
    //序列
    @XmlElement
    public String sn;
    //序号
	@XmlElement
	public String seq;
	//工作中心
	@XmlElement
	public String workcenter;
	//工厂
	@XmlElement
	public String factory; 
	//控制码
	@XmlElement
	public String controlcode; 
	//描述
	@XmlElement
	public String description;
	//人工作业
	@XmlElement
	public String manualwork;
	//机器动力作业
	@XmlElement
	public String machinework;
	//间接费用作业
	@XmlElement
	public String indirectcostwork;
	
	@XmlTransient
	public String getSeq() {
		return seq;
	}
	public void setSeq(String seq) {
		this.seq = seq;
	}
	@XmlTransient
	public String getWorkcenter() {
		return workcenter;
	}
	public void setWorkcenter(String workcenter) {
		this.workcenter = workcenter;
	}
	@XmlTransient
	public String getFactory() {
		return factory;
	}
	public void setFactory(String factory) {
		this.factory = factory;
	}
	@XmlTransient
	public String getControlcode() {
		return controlcode;
	}
	public void setControlcode(String controlcode) {
		this.controlcode = controlcode;
	}
	@XmlTransient
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	@XmlTransient
	public String getSn() {
		return sn;
	}
	public void setSn(String sn) {
		this.sn = sn;
	}
	@XmlTransient
	public String getManualwork() {
		return manualwork;
	}
	public void setManualwork(String manualwork) {
		this.manualwork = manualwork;
	}
	@XmlTransient
	public String getMachinework() {
		return machinework;
	}
	public void setMachinework(String machinework) {
		this.machinework = machinework;
	}
	@XmlTransient
	public String getIndirectcostwork() {
		return indirectcostwork;
	}
	public void setIndirectcostwork(String indirectcostwork) {
		this.indirectcostwork = indirectcostwork;
	}

	
}
