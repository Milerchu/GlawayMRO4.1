package com.glaway.sddq.config.zcconfig.bean;

import java.io.IOException;

import com.glaway.mro.controller.DataBean;
import com.glaway.mro.exception.MroException;
import com.glaway.mro.util.GWConstant;

/**
 * 走行公里记录DataBean
 * 
 * @author hyhe
 * @version [版本号, 2018-5-29]
 * @since [产品/模块版本]
 */
public class WalkDataBean extends DataBean {

    @Override
    public void addEditRowCallBackOk() throws MroException, IOException {
        super.addEditRowCallBackOk();
        long b1 = this.getJpo().getLong("RUNKILOMETRE");
        long b2 = this.getAppBean().getJpo().getLong("RUNKILOMETRE");
        long kilometre = b1 - b2;
        this.getJpo().setValue("kilometre", kilometre, GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
        this.getAppBean()
                .getJpo()
                .setValue("RUNKILOMETRE", b1);
        this.getAppBean().getJpo()
                .setValue("TJDATE", this.getJpo().getString("COUNTDATE"), GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
        
        this.getAppBean().SAVE();
    }

}
