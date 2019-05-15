package com.glaway.sddq.material.detectedmater.bean;

import java.io.IOException;

import com.glaway.mro.controller.DataBean;
import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.IJpo;
import com.glaway.mro.jpo.IJpoSet;
import com.glaway.mro.system.MroServer;
import com.glaway.mro.util.GWConstant;

/**
 * 
 * 状态解锁和解锁记录
 * 
 * @author  bchen
 * @version  [版本号, 2018-6-2]
 * @since  [产品/模块版本]
 */
public class DetectedStatusBean extends DataBean
{
    @Override
    public int execute()
        throws MroException
    {
        
        //1.获取当前新状态
        String newstatus = getJpo().getString("STATUS");
        //2.更改当前记录状态为新获取到的状态
        IJpoSet detectedmaterset =
            MroServer.getMroServer().getJpoSet("DETECTEDMATER", MroServer.getMroServer().getSystemUserServer());
        for (int i = 0; i < detectedmaterset.count(); i++)
        {
            IJpo parent = detectedmaterset.getJpo(i);
            String status = parent.getString("STATUS");
            if (status.equals("已锁定"))
            {
                String detectednum = parent.getString("DETECTEDNUM");
                parent.setValue("STATUS", newstatus);
           
                IJpoSet jpoSet = this.page.getJpoSet("DETECTEDCHANGED");
                IJpo addJpo = jpoSet.addJpo();
                addJpo.setValue("CHANGEBY", this.getUserName());
                addJpo.setValue("CHANGEDATE", MroServer.getMroServer().getDate());
                addJpo.setValue("DETECTEDNUM", detectednum);
                jpoSet.save(); 
                detectedmaterset.save();
                this.reset();
                //3.将当前状态以及时间存入历史表
                //return GWConstant.NOACCESS_SAMEMETHOD;               
            } 
           try {
			this.getAppBean().SAVE();
			} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}     
        }       
        return currentRow;
    }
}
