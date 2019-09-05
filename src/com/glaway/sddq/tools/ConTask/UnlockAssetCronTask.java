package com.glaway.sddq.tools.ConTask;

import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.IJpo;
import com.glaway.mro.jpo.IJpoSet;
import com.glaway.mro.system.MroServer;
import com.glaway.mro.system.crontask.BaseStatefulJob;
import com.glaway.mro.util.GWConstant;

/**
 * 解锁异常锁定序列号件
 * @author Miller
 * @date 2019/09/05
 */
public class UnlockAssetCronTask extends BaseStatefulJob {

    private static final long serialVersionUID = 1590831542692517841L;

    @Override
    public void execute() throws MroException {
        super.execute();

        //查找所有异常被锁定零件
        IJpoSet assetSet = MroServer.getMroServer().getSysJpoSet("ASSET");
        assetSet.setUserWhere("islocked=1 and (assetnum in (select assetnum from exchangerecord where isdo=1 ) " +
                "or assetnum in(select newassetnum from exchangerecord where isdo=1 ))");
        assetSet.reset();

        if(assetSet != null && assetSet.count() > 0){

            for(int idx = 0; idx < assetSet.count(); idx++){
                IJpo asset = assetSet.getJpo(idx);
                asset.setValue("islocked", 0, GWConstant.P_NOVALIDATION_AND_NOACTION);
            }

            assetSet.save();
        }

    }
}
