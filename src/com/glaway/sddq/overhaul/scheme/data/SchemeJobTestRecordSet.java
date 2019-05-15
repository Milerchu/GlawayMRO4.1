package com.glaway.sddq.overhaul.scheme.data;

import com.glaway.mro.jpo.IJpoSet;
import com.glaway.mro.jpo.JpoSet;
/**
 * 
 * 检修方案试验项目JpoSet
 * 
 * @author  chenbin
 * @version  [版本号, 2018-7-17]
 * @since  [产品/模块版本]
 */
public class SchemeJobTestRecordSet extends JpoSet implements IJpoSet {

	  /**
     * 注释内容
     */
    private static final long serialVersionUID = 1L;
    
    public SchemeJobTestRecord getJpoInstance()
    {
        return new SchemeJobTestRecord();
    }
}
