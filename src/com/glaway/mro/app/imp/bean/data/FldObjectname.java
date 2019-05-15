package com.glaway.mro.app.imp.bean.data;

import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.IJpo;
import com.glaway.mro.jpo.IJpoSet;
import com.glaway.mro.jpo.JpoField;
import com.glaway.mro.util.GWConstant;
import com.glaway.mro.util.StringUtil;

/**
 * 导入对象唯一性校验
 * @author txu
 *
 */
public class FldObjectname extends JpoField
{
    /**
	 * 
	 */
	private static final long serialVersionUID = -6043772145408682868L;

	/**
     * 唯一性校验
     */
    @Override
    public void validate()
        throws MroException
    {
        String curvalue = getInputMroType().asString();
        IJpo faceJpo = getJpo().getParent();
        IJpoSet objectSet = faceJpo.getJpoSet("IMPRELATION");
        if (objectSet != null && objectSet.count() > 0)
        {
            for (int i = 0; i < objectSet.count(); i++)
            {
                if (getJpo() != objectSet.getJpo(i))
                {
                    String objtemp = objectSet.getJpo(i).getString("OBJECTNAME");
                    if (StringUtil.isEqualIgnoreCase(curvalue, objtemp))
                    {
                        String[] params = {objtemp};
                        throw new MroException("imp", "dupobject", params);
                    }
                }
                
            }
        }
        
        super.validate();
    }
    
    @Override
    public void action() throws MroException {
    	getJpo().getField("IMPCLASS").setValueNull(GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
    	getJpo().getField("DESCRIPTION").setValueNull(GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
    	super.action();
    }
    
}
