/*
 * 文 件 名:  FldLocation.java
 * 版    权:  Glaway Technologies Co., Ltd. Copyright 2010-2016,  All rights reserved
 * 描    述:  <描述>
 * 修 改 人:  yyang
 * 修改时间:  2016-5-10
 */
package com.glaway.sddq.material.location.data;

import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.IJpo;
import com.glaway.mro.jpo.IJpoSet;
import com.glaway.mro.jpo.JpoField;
import com.glaway.mro.system.MroServer;
import com.glaway.mro.util.GWConstant;
import com.glaway.mro.util.StringUtil;

/**
 *  Locations 表中location 字段类，控制字段读写和祖代关系维护
 * @author  yyang
 * @version  [版本号, 2016-5-10]
 * @since  [产品/模块版本]
 */
public class FldLocation extends JpoField
{
    
    /**
     * 注释内容
     */
    private static final long serialVersionUID = 7366618044871731505L;
    
    /**
     * @throws MroException
     */
    @Override
    public void init()
        throws MroException
    {
        super.init();
        if (!this.getJpo().isNew())
        {
            this.setFlag(GWConstant.S_READONLY, true);
        }
        else
        {
            setHierarchyAndAncestor();
        }
    }
    
    /**
     * 验证位置值是否合法
     * @throws MroException
     */
    @Override
    public void validate()
        throws MroException
    {
        super.validate();
        String inputValue = this.getInputMroType().asString();
        if (!StringUtil.isStrEmpty(inputValue))
        {
            IJpoSet locSet = MroServer.getMroServer().getSysJpoSet("LOCATIONS", " location ='" + StringUtil.getSafeSqlStr(inputValue) + "' ");
            locSet.reset();
            if (!locSet.isEmpty())
            {
                String[] par = {inputValue};
                throw new MroException("locations", "locationExist", par);
            }
        }
    }
    
    /**
     * 新增数据设置location 编号后，在层级关系表和祖代关系表中写入数据。并将前值关联的层级和祖代关系数据删除
     * @throws MroException
     */
    @Override
    public void action()
        throws MroException
    {
        super.action();
        setHierarchyAndAncestor();
    }
    
    public void setHierarchyAndAncestor()
        throws MroException
    {
        IJpo jpo = this.getJpo();
            String value = getValue();
            String perValue = getPreviousMroType().getStringValue();
            if (!StringUtil.isStrEmpty(value))
            {
                IJpoSet locancestorSet = jpo.getJpoSet("LOCANCESTOR");
                IJpo locancestor = locancestorSet.addJpo();
                locancestor.setValue("ANCESTOR", value);
                locancestor.setValue("LOCATION", value);

                IJpoSet lochierarchySet = jpo.getJpoSet("LOCHIERARCHY");
                IJpo lochierarchy = lochierarchySet.addJpo();
                lochierarchy.setValue("LOCATION", value);

                if (!StringUtil.isStrEmpty(perValue))
                {
                    IJpoSet oldLocancestorSet = MroServer.getMroServer().getSysJpoSet("LOCANCESTOR");
                    oldLocancestorSet.setUserWhere("LOCATION ='" + StringUtil.getSafeSqlStr(perValue) + "' ");
                    oldLocancestorSet.reset();
                    oldLocancestorSet.deleteAll();
                    oldLocancestorSet.save();
                    
                    IJpoSet oldLochierarchySet = MroServer.getMroServer().getSysJpoSet("LOCHIERARCHY");
                    oldLochierarchySet.setUserWhere("LOCATION ='" + StringUtil.getSafeSqlStr(perValue) + "' ");
                    oldLochierarchySet.reset();
                    oldLochierarchySet.deleteAll();
                    oldLochierarchySet.save();
                }
            }
    }
}
