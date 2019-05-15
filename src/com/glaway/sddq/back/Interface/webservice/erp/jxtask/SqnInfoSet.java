package com.glaway.sddq.back.Interface.webservice.erp.jxtask;

import java.io.Serializable;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * 
 * @ClassName SqnInfoSet
 * @Description MES或者ERP传递的生产产品的序列号及其他相关细信息集合
 * @author public2175
 * @Date 2018-8-8 下午7:52:20
 * @version 1.0.0
 */
@XmlRootElement
public class SqnInfoSet implements Serializable{
    
    public List<SqnInfo> SqnInfoList;
    
    public List<SqnInfo> getSqnInfoList() {
        return SqnInfoList;
    }

    public void setSqnInfoList(List<SqnInfo> sqnInfoList) {
        this.SqnInfoList = sqnInfoList;
    }
    
}
