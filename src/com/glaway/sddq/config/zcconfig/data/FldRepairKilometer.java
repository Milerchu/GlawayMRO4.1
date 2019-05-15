package com.glaway.sddq.config.zcconfig.data;

import io.netty.util.internal.StringUtil;

import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.JpoField;

/**
 * @ClassName FldRepairKilometer
 * @Description 入修走行公里数字段类
 * @author public2175
 * @Date 2018-9-4 下午3:46:38
 * @version 1.0.0
 */
public class FldRepairKilometer extends JpoField {

    /**
     * @Field @serialVersionUID : 默认序列化ID
     */
    private static final long serialVersionUID = 1L;

    @Override
    public void action() throws MroException {

        if (this.isValueChanged()) {
            if (this.getJpo() != null && !StringUtil.isNullOrEmpty(this.getJpo().getName())
                    && this.getJpo().getName().equals("ASSET")) {
                long lsvalue = this.getJpo().getLong("RUNKILOMETRE");
                long xhvalue = lsvalue - this.getLongValue();
                this.getJpo().setValue("REPAIRAFTERKILOMETER", xhvalue);
            }
            if (this.getJpo() != null && this.getJpo().getParent() != null && !StringUtil.isNullOrEmpty(this.getJpo().getName())
                    && this.getJpo().getName().equals("CHECKDYNAMIC")) {
                long lsvalue = this.getJpo().getParent().getLong("RUNKILOMETRE");
                long xhvalue = lsvalue - this.getLongValue();
                this.getJpo().setValue("REPAIRLOMETRE", xhvalue);
                if(this.getJpo().isNew()){
                    this.getJpo().getParent().setValue("REPAIRKILOMETER", this.getLongValue());
                }
            }
        }
        super.action();
    }

}
