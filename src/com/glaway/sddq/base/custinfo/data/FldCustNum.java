package com.glaway.sddq.base.custinfo.data;

import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.IJpo;
import com.glaway.mro.jpo.IJpoSet;
import com.glaway.mro.jpo.JpoField;
import com.glaway.mro.util.StringUtil;

/**
 * 客户资料客户编号字段类
 * @author  txu
 * @version  GlawayMro4.0, 2016-6-29
 * @since  GlawayMro4.0/系统
 */
public class FldCustNum extends JpoField
{
    /**
     * 唯一序列
     */
    private static final long serialVersionUID = 1L;

    /**
     * 进行唯一性校验
     * @throws MroException 异常
     */
    @Override
    public void validate()
        throws MroException
    {
        super.validate();
        String curCustNum = getInputMroType().asString();
        IJpoSet custSet = getUserServer().getJpoSet("CUSTINFO");
        if (custSet != null && !custSet.isEmpty())
        {
            for (int i = 0; i < custSet.count(); i++)
            {
                IJpo custIJpo = custSet.getJpo(i);
                if (!StringUtil.isNull(custIJpo.getString("CUSTNUM")))
                {
                    if (StringUtil.isEqual(custIJpo.getString("CUSTNUM"), curCustNum))
                    {
                        throw new MroException("custinfo", "numduplicate");
                    }
                }
            }
        }
    }
}
