/*
 * 文 件 名:  LocationSet.java
 * 版    权:  Glaway Technologies Co., Ltd. Copyright 2010-2016,  All rights reserved
 * 描    述:  <描述>
 * 修 改 人:  yyang
 * 修改时间:  2016-5-9
 */
package com.glaway.sddq.material.location.data;

import groovy.ui.SystemOutputInterceptor;

import com.glaway.mro.jpo.JpoSet;

/**
 * <功能描述>位置信息
 * @author  yyang
 * @version  [版本号, 2016-5-9]
 * @since  [产品/模块版本]
 */
public class LocationSet extends JpoSet
{
    
    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 5837456078061613993L;
    
    /**
     * @return
     */
    @Override
    public Locations getJpoInstance()
    {
        return new Locations();
    }
    
    /**
     * @return
     */
    @Override
    public boolean isRowStamping()
    {
        return false;
    }
    
    @Override
    public boolean toBeSaved() {
    	String appname= this.getAppName();
    	if(null!=appname){
    		if(appname.equalsIgnoreCase("MATERREQ")){
        		return false;
        	}
    	} 
    	return super.toBeSaved();
    }
    
}
