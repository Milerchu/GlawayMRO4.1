package com.glaway.sddq.back.msgConfig.data;

import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.JpoField;

/**
 * 
 * 接收类型字段类
 * 
 * @author  hyhe
 * @version  [版本号, 2018-2-6]
 * @since  [产品/模块版本]
 */
public class FldReceiveType extends JpoField
{
    
    /**
     * 注释内容
     */
    private static final long serialVersionUID = 1L;
    
    @Override
    public void init()
        throws MroException
    {
        super.init();
        String type = this.getJpo().getString("RECEIVETYPE");
        if (type.equals("指定人员"))
        {
            this.getField("ReceiveUser").setUserLookup("PERSON");
        }
        else if (type.equals("人员组"))
        {
            this.getField("ReceiveUser").setUserLookup("PERSONGROUP");
        }
        else if (type.equals("指定角色"))
        {
            this.getField("ReceiveUser").setUserLookup("ROLE");
        }
    }
    
    @Override
    public void action()
        throws MroException
    {
        super.action();
        String type = this.getJpo().getString("RECEIVETYPE");
        this.getField("ReceiveUser").setValueNull();
        if (type.equals("指定人员"))
        {
            this.getField("ReceiveUser").setUserLookup("PERSON");
        }
        else if (type.equals("人员组"))
        {
            this.getField("ReceiveUser").setUserLookup("PERSONGROUP");
        }
        else if (type.equals("指定角色"))
        {
            this.getField("ReceiveUser").setUserLookup("ROLE");
        }
    }
}
