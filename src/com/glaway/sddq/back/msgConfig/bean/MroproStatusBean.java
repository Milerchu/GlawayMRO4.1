package com.glaway.sddq.back.msgConfig.bean;

import java.io.IOException;

import com.glaway.mro.controller.DataBean;
import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.IJpo;
import com.glaway.mro.util.GWConstant;
import com.glaway.sddq.back.msgConfig.data.Mropro;
/**
 * 
 * 系统问题记录状态变更
 * 
 * @author  chenbin
 * @version  [版本号, 2018-7-27]
 * @since  [产品/模块版本]
 */
public class MroproStatusBean extends DataBean {

	@Override
	public int execute() throws MroException, IOException {
		// TODO Auto-generated method stub
		 IJpo mr = getAppBean().getJpo();
	        //列表数据为空时
	        if (mr == null)
	        {
	            return GWConstant.NOACCESS_SAMEMETHOD;
	        }
	        Mropro ct = (Mropro)mr;
	        checkSave();
	        
	        ct.changestatus(getString("status"), getString("memo"));
	        try
	        {
	            getAppBean().SAVE();
	            this.reloadPage();
	        }
	        catch (IOException e)
	        {
	            e.printStackTrace();
	        }
	        return GWConstant.NOACCESS_SAMEMETHOD;
	    }
	}
