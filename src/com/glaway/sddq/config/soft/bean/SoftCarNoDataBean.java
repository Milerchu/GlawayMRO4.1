package com.glaway.sddq.config.soft.bean;

import java.io.IOException;
import java.util.List;

import com.glaway.mro.controller.DataBean;
import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.IJpo;
import com.glaway.mro.jpo.IJpoSet;
import com.glaway.mro.system.MroServer;

/**
 * 
 * 软件配置库功能-选择车号
 * 
 * @author  public2175
 * @version  [版本号, 2018-6-7]
 * @since  [产品/模块版本]
 */
public class SoftCarNoDataBean extends DataBean {
	
	@Override
    public void initialize()
        throws MroException
    {
        IJpo soft = this.getAppBean().getJpo();
        DataBean carnoDataBean = this.getAppBean().getDataBean("1527677682833");
        IJpoSet jpoSet = carnoDataBean.getJpoSet();
        if(jpoSet.count() > 0){
        	StringBuffer assetnumlist = new StringBuffer();
        	for(int index=0;index<jpoSet.count();index ++){
        		assetnumlist.append("'"+jpoSet.getJpo(index).getString("assetnum")+"',");
        	}
        	String assetnumsql = assetnumlist.toString().substring(0, assetnumlist.toString().length()-1);
            this.getJpoSet().setQueryWhere("ASSETLEVEL='ASSET' and type='2' and CMODEL = '"+soft.getString("cmodel")+"' and assetnum not in ("+assetnumsql+")");
        }else{
            this.getJpoSet().setQueryWhere("ASSETLEVEL='ASSET' and type='2' and CMODEL = '"+soft.getString("cmodel")+"'");
        }
        this.getJpoSet().reset();
        
        super.initialize();
    }
	
	 @Override
	    public int dialogok()
	        throws IOException, MroException
	    {
	        DataBean carnoDataBean = this.getAppBean().getDataBean("1527677682833");
	        
	        IJpoSet softcano =
	            MroServer.getMroServer().getJpoSet("SOFTCONFIGCAR", MroServer.getMroServer().getSystemUserServer());
	        
	        String softconfignum = this.getAppBean().getString("SOFTCONFIGNUM");
	        // 获取当前选择的车辆
	        List<IJpo> list = getJpoSet().getSelections();
	        if (!list.isEmpty())
	        {
	            for (int i = 0; i < list.size(); i++)
	            {
	                IJpo carnoJpo = list.get(i);
                    IJpo rpjpo = softcano.addJpo();
                    rpjpo.setValue("cmodel", carnoJpo.getString("cmodel"));
                    rpjpo.setValue("carno", carnoJpo.getString("carno"));
                    rpjpo.setValue("assetnum", carnoJpo.getString("assetnum"));
                    rpjpo.setValue("SITEID", this.getAppBean().getJpo().getString("SITEID"));
                    rpjpo.setValue("ORGID", this.getAppBean().getJpo().getString("ORGID"));
                    rpjpo.setValue("SOFTCONFIGNUM", softconfignum);
	            }
	        }
	        softcano.save();
	        carnoDataBean.resetAndReload();
	        return super.dialogok();
	    }

}
