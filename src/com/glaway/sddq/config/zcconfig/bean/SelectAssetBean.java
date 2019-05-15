package com.glaway.sddq.config.zcconfig.bean;

import java.io.IOException;

import com.glaway.mro.controller.DataBean;
import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.IJpoSet;
import com.glaway.mro.util.GWConstant;

/**
 * 
 * 选择产品
 * 
 * @author  hyhe
 * @version  [版本号, 2017-11-17]
 * @since  [产品/模块版本]
 */
public class SelectAssetBean extends DataBean
{
    @Override
    public void initJpoSet()
        throws MroException
    {
        super.initJpoSet();
        IJpoSet assetSet = this.jpoSet;
        assetSet.setUserWhere("ASSETLEVEL='ASSET' and TYPE='1'");
    }
    
    public int execute()
        throws MroException, IOException
    {
//        List<IJpo> assets = getSelections();
//        Iterator<IJpo> e = assets.iterator();
//        DataBean selectAssetBean = this.getDialog().getCreatorBean();
//        IJpo assetAncestor = selectAssetBean.getJpoSet().getParent();
//        while (e.hasNext())
//        {
//            IJpo asset = e.next();
//            String installNum = asset.getString("ASSETNUM");
//            String sqn = asset.getString("SQN");
//            IJpoSet installMboset = MroServer.getMroServer().getSysJpoSet("ASSET");
//            installMboset.setUserWhere("assetnum in (select assetnum from assettree where ancestor ='" + installNum
//                + "')");
//        }
//        selectAssetBean.reloadSelfAndSubs();
        return GWConstant.NOACCESS_SAMEMETHOD;
    }
}
