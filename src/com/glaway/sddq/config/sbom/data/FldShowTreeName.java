package com.glaway.sddq.config.sbom.data;

import io.netty.util.internal.StringUtil;

import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.JpoField;
import com.glaway.mro.util.GWConstant;

/**
 * 
 * 在树节点上拼接显示描述字段类
 * 
 * @author  hyhe
 * @version  [版本号, 2017-11-9]
 * @since  [产品/模块版本]
 */
public class FldShowTreeName extends JpoField
{
    
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
                String desc = this.getField("MODELS.MODELDESC").getValue();//描述
                this.setValue(desc, GWConstant.P_NOUPDATE);
            }else  if (this.getField("ASSETCSLEVEL").getValue().equals("CAR"))
            {
                String desc = this.getField("carriagenum").getValue();//描述
                this.setValue(desc, GWConstant.P_NOUPDATE);
            }
            else
            {
//                String itemno = this.getField("ITEMNUM").getValue();//车型
            	String desc = this.getField("DESCRIPTION").getValue();//PLM名称
                String locdesc = this.getField("LOCDESC").getValue();//位置描述
                String loc = this.getField("RNUM").getValue();//位号
                if(loc == null){
                	loc = "";
                }
                if(!StringUtil.isNullOrEmpty(locdesc)){
                	this.setValue(loc+"="+locdesc, GWConstant.P_NOUPDATE);
                }else{
                	if(!StringUtil.isNullOrEmpty(desc)){
                    	this.setValue(loc+"="+desc, GWConstant.P_NOUPDATE);
                    }else{
                    	desc = this.getField("ITEM.DESCRIPTION").getValue();
                    	this.setValue(loc+"="+desc, GWConstant.P_NOUPDATE);
                    }
                }
            }
        }
    }
}
