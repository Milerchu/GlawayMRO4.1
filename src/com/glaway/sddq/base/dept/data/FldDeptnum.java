package com.glaway.sddq.base.dept.data;

import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.IJpo;
import com.glaway.mro.jpo.IJpoSet;
import com.glaway.mro.jpo.JpoField;
import com.glaway.mro.system.security.SecurityService;
import com.glaway.mro.util.StringUtil;

/**
 * 
 * <功能描述>
 * 表SYS_DEPT.DEPTNUM的字段类
 * @author  jxiang
 * @version  [版本号, 2016-5-23]
 * @since  [产品/模块版本]
 */
public class FldDeptnum extends JpoField
{
    /**
	 * 注释内容
	 */
	private static final long serialVersionUID = 1L;

	@Override
    public void validate()
        throws MroException
    {
        super.validate();
        if (!getInputMroType().isNull())
        {
            String deptnum = getInputMroType().asString();
            
            int charIndex = SecurityService.hasSpecialChar(deptnum);
            if (charIndex >= 0)
            {
                String[] p = {deptnum.substring(charIndex, charIndex + 1)};
                throw new MroException("signature", "valueInvalidChar", p);
            }
            
            String where =
                "SYS_DEPTID<>" + getJpo().getInt("SYS_DEPTID") + " and deptnum='" + StringUtil.getSafeSqlStr(deptnum)
                    + "'";
            IJpoSet deptSet = getUserServer().getJpoSet("SYS_DEPT", where);
            boolean isRepeat = false;
            
            try
            {
                if (!deptSet.isEmpty())
                {
                    isRepeat = true;
                }
                
                if (!isRepeat)
                {
                    IJpoSet currdeptJposet = getJpo().getJpoSet();
                    if (!currdeptJposet.isEmpty())
                    {
                        for (int i = 0; i < currdeptJposet.count(); i++)
                        {
                            IJpo deptJpo = currdeptJposet.getJpo(i);
                            if (deptJpo != getJpo())
                            {
                                if (deptnum.equals(deptJpo.getString("deptnum")))
                                {
                                    isRepeat = true;
                                    break;
                                }
                            }
                        }
                    }
                }
                
                if (isRepeat)
                {
                    String[] p = {"部门编号 " + deptnum + " 已存在！"};
                    throw new MroException("dept", "validate", p);
                }
            }
            finally
            {
                deptSet.destroy();
            }
        }
    }
}
