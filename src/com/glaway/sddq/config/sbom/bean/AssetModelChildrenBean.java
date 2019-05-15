/*
 * 文 件 名:  AssetModelChildrenBean.java
 * 版    权:  Glaway Technologies Co., Ltd. Copyright 2010-2016,  All rights reserved
 * 描    述:  型号组件列表DataBean
 * 修 改 人:  hyhe
 * 修改时间:  2016-4-25
 */
package com.glaway.sddq.config.sbom.bean;

import java.io.IOException;

import com.glaway.mro.controller.DataBean;
import com.glaway.mro.controller.TreeBean;
import com.glaway.mro.exception.MroException;

/**
 * 型号组件列表DataBean
 * 
 * @author  hyhe
 * @version  [MRO4.0, 2016-4-25]
 * @since  [MRO4.0/型号管理]
 */
public class AssetModelChildrenBean extends DataBean
{
    @Override
    public void addEditRowCallBackOk()
        throws MroException, IOException
    {
        super.addEditRowCallBackOk();
        //重新加载节点的子层节点
        ((TreeBean)parent).reloadNodeWithoutReset();
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
            EXCEPTIONLOGGER.error(e);
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
            EXCEPTIONLOGGER.error(e);
        }
    }
}
