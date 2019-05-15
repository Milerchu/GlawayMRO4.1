package com.glaway.sddq.config.zcconfig.bean;

import java.io.IOException;

import com.glaway.mro.controller.DataBean;
import com.glaway.mro.controller.TreeBean;
import com.glaway.mro.exception.MroException;

/**
 * 装车配置中的 DataBean
 * 
 * @author  hyhe
 * @version  [MRO4.0, 2016-4-28]
 * @since  [MRO4.0/模块版本]
 */
public class AssetCarChildrenBean extends DataBean
{
    @Override
    public void addEditRowCallBackOk()
        throws MroException, IOException
    {
        super.addEditRowCallBackOk();
        //重新加载节点的子层节点
//        ((TreeBean)parent).reloadNodeWithoutReset();
    }
    
    /**
     * 新建窗口，点击取消按钮后，重新加载节点的子层节点
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
    
}
