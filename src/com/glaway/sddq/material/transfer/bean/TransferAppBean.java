package com.glaway.sddq.material.transfer.bean;

import java.io.IOException;

import com.glaway.mro.controller.AppBean;
import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.IJpo;
import com.glaway.mro.page.PageControl;
import com.glaway.mro.page.control.Tab;
import com.glaway.mro.util.StringUtil;
import com.glaway.sddq.material.transfer.data.Transfer;

/**
 * 
 * 调拨单DataBean
 * 
 * @author  qhsong
 * @version [GlawayMro4.0, 2017-11-8]
 * @since   [GlawayMro4.0/调拨单]
 */
public class TransferAppBean extends AppBean
{
    public int RECEIVE()
    {
        int r = 1;
        return r;
    }
    
    @Override
    public void setCurrentRow(int rowNum)
        throws MroException
    {
        super.setCurrentRow(rowNum);
        //切换记录刷新界面
        refreshPage();
    }
    
    @Override
    public void afterTabChange(Tab currTab)
        throws IOException, MroException
    {
        //标签页切换时刷新界面
        refreshPage();
        super.afterTabChange(currTab);
    }
    
    @Override
    protected void afterSetValue(String attribute)
        throws MroException
    {
        super.afterSetValue(attribute);
        refreshPage();
    }
    
    /**
     * 
     * 根据TRANSFERTYPE字段的值刷新界面 
     * @throws MroException 
     *
     */
    public void refreshPage()
        throws MroException
    {
        
        IJpo transferJpo = getJpo();
        if (transferJpo != null)
        {
            String zxdnum = transferJpo.getString("zxdnum");
            String TRANSFERTYPE = transferJpo.getString("TRANSFERTYPE");
            PageControl dbzxdbutten = getPageControl().getControlByXmlId("1527160296170");//调拨装箱单按钮
            PageControl toolzxdbutten = getPageControl().getControlByXmlId("152716101592432");//工具装箱单按钮
            PageControl dbtrans = getPageControl().getControlByXmlId("dbtrans");//调拨单信息
            PageControl tooltrans = getPageControl().getControlByXmlId("tooltrans");//工具调拨单信息
            PageControl dbline = getPageControl().getControlByXmlId("dbline");//调拨单行
            PageControl toolline = getPageControl().getControlByXmlId("toolline");//工具调拨单行
            try
            {
                if (dbzxdbutten != null && toolzxdbutten != null && dbtrans != null && tooltrans != null && dbline != null && toolline != null)
                {
                	 if(TRANSFERTYPE.equalsIgnoreCase("工具")){
                		 tooltrans.show();
                		 toolline.show();
                		 dbtrans.hide();
                		 dbline.hide();
                		 if(!zxdnum.equalsIgnoreCase("")){
                			 toolzxdbutten.show();
                		 }else{
                			 toolzxdbutten.hide();
                		 }
                	 }else{
                		 tooltrans.hide();
                		 toolline.hide();
                		 dbtrans.show();
                		 dbline.show();
                		 if(!zxdnum.equalsIgnoreCase("")){
                			 dbzxdbutten.show();
                		 }else{
                			 dbzxdbutten.hide();
                		 }
                	 }
                }
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
    }
}
