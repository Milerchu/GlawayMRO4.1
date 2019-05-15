/*
 * 文 件 名:  AssetCsChildrenBean.java
 * 版    权:  Glaway Technologies Co., Ltd. Copyright 2010-2016,  All rights reserved
 * 描    述:  出所台帐 结构某节点下的子级DataBean
 * 修 改 人:  hyhe
 * 修改时间:  2016-4-28
 */
package com.glaway.sddq.config.csbom.bean;

import java.io.IOException;
import java.util.HashMap;

import com.glaway.mro.controller.DataBean;
import com.glaway.mro.controller.TreeBean;
import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.IJpo;
import com.glaway.mro.jpo.IJpoSet;
import com.glaway.mro.util.GWConstant;
import com.glaway.mro.util.StringUtil;
import com.glaway.sddq.tools.SddqConstant;

/**
 * 整车SBOM下的 DataBean
 * 
 * @author hyhe
 * @version [MRO4.0, 2016-4-28]
 * @since [MRO4.0/模块版本]
 */
public class AssetCsCarChildrenBean extends DataBean
{
    @Override
    public void addEditRowCallBackOk()
        throws MroException, IOException
    {
        super.addEditRowCallBackOk();
		// 重新加载节点的子层节点
//        ((TreeBean)parent).reloadNodeWithoutReset();
    }
    
    /**
	 * 新建窗口，点击取消按钮后，重新加载节点的子层节点
	 * 
	 * @throws MroException
	 */
    @Override
    public void resetAndFixPos()
        throws MroException
    {
        super.resetAndFixPos();
        try
        {
            ((TreeBean)parent).reloadNodeWithoutReset();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
    
    /**
	 * 重新刷新树
	 * 
	 * @throws MroException
	 */
    @Override
    public synchronized void reset()
        throws MroException
    {
        super.reset();
        try
        {
            ((TreeBean)parent).reloadNodeWithoutReset();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
    
    public int addCars() throws MroException, IOException{
    	IJpoSet beanJpoSet = this.getJpoSet();
    	if(beanJpoSet.count()>0){ 
			throw new MroException("#", "已有车厢不能批量添加");
    	}else{
    		IJpo appJpo = this.getAppBean().getJpo();
    		if(appJpo!=null){
    			String marshalling=appJpo.getString("MODELS.MARSHALLING");
    			String productline=appJpo.getString("MODELS.PRODUCTLINE");
    			String modelcode=appJpo.getString("MODELS.MODELCODE");
        		//MODELS.MARSHALLING
    			if(StringUtil.isStrNotEmpty(marshalling)){
    				int nums=addCarBy(marshalling,productline,modelcode);
    				
    				for(int i =0; i< nums;i++){
    					IJpo carJpo = beanJpoSet.addJpo();
    					String carriagenum="";
    					if(i<10){
    						carriagenum="0"+i;
    					}else{
    						carriagenum=i+"";
    					}
    					carJpo.setValue("CARRIAGENUM", carriagenum);
    					carJpo.setValue("itemnum", SddqConstant.CAR_ITEMNUM, GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
    					carJpo.setValue("assetcslevel", SddqConstant.CAR_ASSETLEVEL, GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
    				}
    			}
    		}
    	}
    	this.getAppBean().SAVE();
    	return 1;
    }

	private int  addCarBy(String marshalling,String productline,String modelcode) throws MroException {
		HashMap<String ,Integer> map =new HashMap<String ,Integer>();
		map.put("机车-B0-B0", 1);
		map.put("机车-C0-C0", 1);
		map.put("机车-2(B0-B0)", 2);
		map.put("机车-3(B0-B0)", 3);
		map.put("机车-B0-B0-B0", 1);
		map.put("动车-4M4T", 8);
		map.put("动车-6M2T", 8);
		map.put("动车-14M2T", 16);
		map.put("动车-8M8T", 16);
		map.put("动车-2M1T", 3);
		// 增加新添加的三个
		map.put("动车-6M0T", 6);
		map.put("动车-8M9T", 17);
		map.put("动车-7M1T", 8);
		// end
		map.put("动车-2M2T", 4);
		map.put("城轨-4M2T", 6);
		map.put("城轨-3M3T", 6);
		map.put("城轨-6M0T", 6);
		map.put("城轨-6M2T", 8);
		map.put("城轨-3M2T", 5);
		map.put("城轨-4M0T", 4);
		map.put("城轨-2M2T", 4);
		map.put("城轨-2M1T", 3);
		map.put("城轨-3M0T", 3);
		map.put("城轨-B0-B0", 1);
		int nums=0;
		String keys=productline+"-"+marshalling;
		if(map.containsKey(keys)){
			 nums =map.get(keys);
		}else{
			throw new MroException("#", "[" + modelcode + "] 车型未找到轴式设置");
		}
		
		return nums ;
	}
    
    
    
}
