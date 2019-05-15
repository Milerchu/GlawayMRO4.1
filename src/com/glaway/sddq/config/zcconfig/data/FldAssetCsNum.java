package com.glaway.sddq.config.zcconfig.data;

import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.IJpoSet;
import com.glaway.mro.jpo.JpoField;

/**
 * 
 * @ClassName FldAssetCsNum
 * @Description 整车SBOM编号字段类
 * @author public2175
 * @Date 2018-8-9 下午3:31:47
 * @version 1.0.0
 */
public class FldAssetCsNum extends JpoField {

    /**
     * @Field @serialVersionUID : 默认序列号ID
     */
    private static final long serialVersionUID = 1L;
    
    @Override
    public void init()
        throws MroException
    {
        this.setLookupMap(new String[] {"ZCSBOMNUM"}, new String[] {"ASSETCSNUM"});
    }
    
    @Override
    public IJpoSet getList()
            throws MroException
    {
        IJpoSet jposet = super.getList();
        jposet.setUserWhere("cmodel='"+this.getJpo().getString("cmodel")+"' and ASSETCSLEVEL='ASSET' and STATUS='可用'");
        return jposet;
    }

}
