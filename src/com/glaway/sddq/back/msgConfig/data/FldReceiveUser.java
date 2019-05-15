package com.glaway.sddq.back.msgConfig.data;

import org.apache.axis.utils.StringUtils;

import com.glaway.mro.exception.AppException;
import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.IJpoSet;
import com.glaway.mro.jpo.JpoField;

/**
 * 
 * 接收人配置字段类
 * 
 * @author  hyhe
 * @version  [版本号, 2018-2-6]
 * @since  [产品/模块版本]
 */
public class FldReceiveUser extends JpoField
{
    /**
     * 注释内容
     */
    private static final long serialVersionUID = 1L;
    
    @Override
    public IJpoSet getList()
        throws MroException
    {
        String type = this.getJpo().getString("RECEIVETYPE");
        if (StringUtils.isEmpty(type))
        {
            throw new AppException("msgconfig", "selectType");
        }
        if (type.equals("指定人员"))
        {
            this.setListObject("SYS_PERSON");
            setLookupMap(new String[] {"RECEIVEUSER", "RECEIVEUSERDESC"}, new String[] {"personid", "DISPLAYNAME"});
        }
        else if (type.equals("人员组"))
        {
            setListObject("sys_persongroup");
            setLookupMap(new String[] {"RECEIVEUSER", "RECEIVEUSERDESC"}, new String[] {"persongroup", "DESCRIPTION"});
        }
        else if (type.equals("指定角色"))
        {
            setListObject("SYS_ROLE");
            setLookupMap(new String[] {"RECEIVEUSER", "RECEIVEUSERDESC"}, new String[] {"MAXROLE", "DESCRIPTION"});
        }
        return super.getList();
    }
    
}
