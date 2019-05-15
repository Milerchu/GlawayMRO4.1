package com.glaway.sddq.service.failurelib.data;

import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.IJpo;
import com.glaway.mro.jpo.JpoField;
import com.glaway.mro.util.StringUtil;

/**
 * 故障管理状态变更新状态字段类
 * @author  hzhu
 * @version  GlawayMro4.0, 2017-2-10
 * @since  GlawayMro4.0/系统
 */
public class FldChangeStatus extends JpoField
{
    
    /**
     * 唯一序列
     */
    private static final long serialVersionUID = 1L;
    
    /**
     * 校验选中状态是否与原状态一样
     * @throws MroException 异常
     */
    @Override
    public void validate()
        throws MroException
    {
        super.validate();
        String tempStatus = getInputMroType().asString();
        IJpo custInfo = getJpo().getParent();
        String currStatus = "";
        if (custInfo != null)
        {
            currStatus = custInfo.getString("status");
            if (!StringUtil.isNull(currStatus) && StringUtil.isEqual(currStatus, tempStatus))
            {
                throw new MroException("failurelib", "statusnochange");
            }
        }
    }
}
