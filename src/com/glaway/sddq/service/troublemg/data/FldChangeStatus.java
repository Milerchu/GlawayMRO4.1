package com.glaway.sddq.service.troublemg.data;

import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.IJpo;
import com.glaway.mro.jpo.JpoField;
import com.glaway.mro.util.StringUtil;

/**
 * 
 * 状态变更控制字段类
 * 
 * @author  hzhu
 * @version  [Mro4.0, 2018-1-25]
 * @since  [Mro4.0/项目问题跟踪]
 */
public class FldChangeStatus extends JpoField
{
    
    /**
     * 注释内容
     */
    private static final long serialVersionUID = 2249581199030514861L;
    
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
            currStatus = custInfo.getString("dealstatus");
            if (!StringUtil.isNull(currStatus) && StringUtil.isEqual(currStatus, tempStatus))
            {
                throw new MroException("custinfo", "statusnochange");
            }
        }
    }
}
