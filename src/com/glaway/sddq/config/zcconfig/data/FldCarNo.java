package com.glaway.sddq.config.zcconfig.data;

import com.glaway.mro.exception.AppException;
import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.IJpoSet;
import com.glaway.mro.jpo.JpoField;

/**
 * 
 * 现场配置车号字段类
 * 
 * @author  hyhe
 * @version  [版本号, 2017-11-20]
 * @since  [产品/模块版本]
 */
public class FldCarNo extends JpoField
{
    
    private static final long serialVersionUID = 1L;

    @Override
    public void validate()
        throws MroException
    {
        String carno = getInputMroType().asString();
        String cmodel = this.getJpo().getString("cmodel");
        if(cmodel != null && !cmodel.equals("")){
            IJpoSet allAssetSet = getUserServer().getJpoSet("ASSET", "CARNO='" + carno + "' and cmodel = '"+cmodel+"'and ASSETLEVEL='ASSET'");
            if (!allAssetSet.isEmpty())
            {
                String p[] = {carno};
                throw new AppException("ASSET", "carnorepeat", p);
            }
        }else{
            throw new AppException("ASSET", "cmodelisnull");
        }
        super.validate();
    }
}
