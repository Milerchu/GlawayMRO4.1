package com.glaway.mro.app.imp.bean.data;

import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.IJpo;
import com.glaway.mro.jpo.IJpoSet;
import com.glaway.mro.jpo.JpoField;
import com.glaway.mro.util.StringUtil;

/**
 * 
 * @author ygao
 *
 */
public class FldExcelcolnum extends JpoField
{
    /**
	 * 
	 */
	private static final long serialVersionUID = -1211009704803722664L;

	/**
     * 校验唯一性
     */
    @Override
    public void action()
        throws MroException
    {
        String curvalue = getInputMroType().asString();
        IJpo objectJpo = getJpo().getParent();
        IJpoSet colSet = objectJpo.getJpoSet("IMPATTRIBUTE");
        if (colSet != null && colSet.count() > 0)
        {
            for (int i = 0; i < colSet.count(); i++)
            {
                if (getJpo() != colSet.getJpo(i))
                {
                    String colnumtemp = colSet.getJpo(i).getString("EXCELCOLNUM");
                    if (StringUtil.isEqualIgnoreCase(curvalue, colnumtemp))
                    {
                        String[] params = {colnumtemp};
                        throw new MroException("imp", "dupcol", params);
                    }
                }
                
            }
        }
        super.action();
    }
}
