package com.glaway.sddq.service.failureord.data;

import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.IJpo;
import com.glaway.mro.jpo.IJpoSet;
import com.glaway.mro.jpo.JpoField;
import com.glaway.mro.jpo.type.MroType;
import com.glaway.mro.system.MroServer;
import com.glaway.mro.util.GWConstant;

/**
 * 
 * 服务计划编号绑定字段类
 * 
 * @author  ygao
 * @version  [版本号, 2017-10-18]
 * @since  [产品/模块版本]
 */
public class FldPreProductNum extends JpoField
{
    private static final long serialVersionUID = 1L;
    
    @Override
    public void action()
        throws MroException
    {
        //		super.action();
        //		MroType value = getInputMroType();
        //		IJpo servorderjpo = getJpo();
        //    	IJpoSet servplanset = MroServer.getMroServer().getJpoSet("SERVPLAN", MroServer.getMroServer().getSystemUserServer());
        //    	servplanset.setQueryWhere("SERVPLANNUM = '"+value.asString()+"'");
        //    	servplanset.reset();
        //    	if(!servplanset.isEmpty()){
        //    		IJpo servplan = servplanset.getJpo();
        //    		servorderjpo.setValue("SERVPLANNAME", servplan.getString("SERVPLANNAME"),GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
        //    		servorderjpo.setValue("ORDERPROJECT", servplan.getJpoSet("PROJECTINFO").getJpo().getString("PROJECTNUM"),GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
        //    	}
        super.action();
        MroType value = getInputMroType();
        IJpo exchangejpo = getJpo();
        IJpoSet assetset = MroServer.getMroServer().getJpoSet("ASSET", MroServer.getMroServer().getSystemUserServer());
        assetset.setQueryWhere("SQN = '" + value.asString() + "'");
        assetset.reset();
        if (!assetset.isEmpty())
        {
            IJpo asset = assetset.getJpo();
            //            servorderjpo.setValue("SERVPLANNAME", servplan.getString("SERVPLANNAME"),GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
            //            servorderjpo.setValue("ORDERPROJECT", servplan.getJpoSet("PROJECTINFO").getJpo().getString("PROJECTNUM"),GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
            exchangejpo.setValue("PRODUCTCODEDOWN", asset.getString("ITEMNUM"), GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
            exchangejpo.setValue("PRODUCTDESCDOWN",
                asset.getJpoSet("ITEM").getJpo().getString("description"),
                GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
            exchangejpo.setValue("SOFTVERSIONDOWN",
                asset.getString("softversion"),
                GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
        }
    }
    
    @Override
    public IJpoSet getList()
        throws MroException
    {
        //根据车型车号过滤弹出框数据
        String carnum = getJpo().getParent().getString("CARNUM");
        String model = getJpo().getParent().getString("CARMODELS");
        String where = "ancestor=(select assetnum from asset where carno='" + carnum + "' and cmodel='" + model + "')";
        setListWhere(where);
        return super.getList();
    }
    
}
