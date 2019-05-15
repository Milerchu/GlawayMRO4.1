package com.glaway.sddq.config.zcconfig.data;

import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.IJpo;
import com.glaway.mro.jpo.IJpoSet;
import com.glaway.mro.jpo.JpoField;

/**
 * @ClassName FldRunKilometre
 * @Description 累计走行公里字段类
 * @author public2175
 * @Date 2018-9-4 下午2:37:02
 * @version 1.0.0
 */
public class FldRunKilometre extends JpoField {

    /**
     * @Field @serialVersionUID : 默认序列化ID
     */
    private static final long serialVersionUID = 1L;

    @Override
    public void action() throws MroException {

        if (this.isValueChanged()) {
            // 更新装车配置中的修后走行公里的值
            long rxvalue = this.getJpo().getLong("REPAIRKILOMETER");// 入修走行公里数
            long lsvalue = this.getLongValue();
            long xhvalue = lsvalue - rxvalue;
            this.getJpo().setValue("REPAIRAFTERKILOMETER", xhvalue);
            // 更新检修动态中的所有记录的修后走行公里的值
            IJpoSet jposet = this.getJpo().getJpoSet("CHECKDYNAMIC");
            if (jposet != null && jposet.count() > 0) {
                for (int index = 0; index < jposet.count(); index++) {
                    IJpo jpo = jposet.getJpo(index);
                    long rxValue = jpo.getLong("repairkilometer");
                    long xhValue = lsvalue - rxValue;
                    jpo.setValue("REPAIRLOMETRE", xhValue);
                }
            }
        }
        super.action();
    }

}
