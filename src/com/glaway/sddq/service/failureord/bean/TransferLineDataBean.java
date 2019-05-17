package com.glaway.sddq.service.failureord.bean;

import com.glaway.mro.controller.DataBean;
import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.IJpoSet;

/**
 * 故障工单-返修信息-装箱单行/送修单行 DataBean
 *
 * @author zhuhao
 * @version [MRO4.1, 2019-05-17]
 * @since [MRO4.1,/模块版本]
 *
 */
public class TransferLineDataBean extends DataBean {

    @Override
    public void initJpoSet() throws MroException {

        super.initJpoSet();
        IJpoSet jpoSet = getJpoSet();
        if(getAppBean().getJpo() != null) {
            //增加工单编号过滤条件，只显示当前工单的装箱单行
            jpoSet.setQueryWhere("tasknum='"+getAppBean().getJpo().getString("ORDERNUM")+"'");
            jpoSet.reset();
        }

    }
}
