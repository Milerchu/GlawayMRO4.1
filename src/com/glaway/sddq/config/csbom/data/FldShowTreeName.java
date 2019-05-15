package com.glaway.sddq.config.csbom.data;

import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.JpoField;
import com.glaway.mro.util.GWConstant;

/**
 * 
 * 整车SBOM显示树节点名称字段类
 * 
 * @author  hyhe
 * @version  [版本号, 2018-1-15]
 * @since  [产品/模块版本]
 */
public class FldShowTreeName extends JpoField
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
        if (this.getJpo() != null && this.getJpo().getAppName() != null && this.getJpo().getAppName().equals("ZCSBOM"))
        {
            if (this.getField("ASSETCSLEVEL").getValue().equals("ASSET"))
            {
                String desc = this.getField("DESCRIPTION").getValue();//描述
                this.setValue(desc, GWConstant.P_NOUPDATE);
            }
            else
            {
//                String itemno = this.getField("ITEMNUM").getValue();//车型
                String desc = this.getField("LOCDESC").getValue();//车号
                this.setValue(desc, GWConstant.P_NOUPDATE);
            }
        }
    }
    
}
