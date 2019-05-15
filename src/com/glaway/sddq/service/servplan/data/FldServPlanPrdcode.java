package com.glaway.sddq.service.servplan.data;

import com.glaway.mro.jpo.IJpo;
import com.glaway.mro.jpo.IJpoSet;
import com.glaway.mro.jpo.JpoField;
import com.glaway.mro.jpo.type.MroType;
import com.glaway.mro.system.MroServer;
/**
 * 
 * <功能描述>
 * 
 * @author  ygao
 * @version  [版本号, 2017-12-12]
 * @since  [产品/模块版本]
 */
public class FldServPlanPrdcode extends JpoField
{

    /**
     * 注释内容
     */
    private static final long serialVersionUID = 4074258639338709679L;
    
    @Override
    public void action() throws com.glaway.mro.exception.MroException {
        super.action();
        MroType value = getInputMroType();
        IJpo sparelistjpo = getJpo();
        IJpoSet itemset = MroServer.getMroServer().getJpoSet("sys_item", MroServer.getMroServer().getSystemUserServer());
        itemset.setQueryWhere("itemnum = '"+value.asString()+"'");
        itemset.reset();
        if(!itemset.isEmpty()){
            IJpo item = itemset.getJpo();
            String desc = item.getString("description");
            String spec = item.getString("SPECIFICATION");
            sparelistjpo.setValue("PRODUCTDESC", desc);
            sparelistjpo.setValue("MODEL", spec);
        }
    }
}
