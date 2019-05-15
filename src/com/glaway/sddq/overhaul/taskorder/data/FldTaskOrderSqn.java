package com.glaway.sddq.overhaul.taskorder.data;

import com.alibaba.druid.util.StringUtils;
import com.glaway.mro.exception.AppException;
import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.IJpo;
import com.glaway.mro.jpo.IJpoSet;
import com.glaway.mro.jpo.JpoField;
import com.glaway.mro.system.MroServer;

/**
 * 
 * 检修工单产品序列号
 * 
 * @author  hyhe
 * @version  [版本号, 2018-1-24]
 * @since  [产品/模块版本]
 */
public class FldTaskOrderSqn extends JpoField
{
    /**
     * 注释内容
     */
    private static final long serialVersionUID = 1L;
    
    @Override
    public void init()
        throws MroException
    {
        this.setLookupMap(new String[] {"ASSETNUM", "SQN","LOTNUM"}, new String[] {"ASSETNUM", "SQN","LOTNUM"});
    }
    
    @Override
    public IJpoSet getList()
        throws MroException
    {
        this.setListObject("ASSET");
        String cmodel = this.getJpo().getString("CMODEL");
        String carno = this.getJpo().getString("CARNO");
        if (StringUtils.isEmpty(carno))
        {
          throw  new AppException("jxtaskorder","carnonull");  
        }
        IJpoSet jposet =
            MroServer.getMroServer().getSysJpoSet("ASSET", "cmodel='" + cmodel + "' and carno='" + carno + "'");
        if (!jposet.isEmpty())
        {
            IJpo jpo = jposet.getJpo(0);
            String ancestor = jpo.getString("ANCESTOR");
            this.setListWhere("assetlevel !='ASSET' and type = '2' and ancestor='" + ancestor + "'");
        }
        return super.getList();
    }
}
