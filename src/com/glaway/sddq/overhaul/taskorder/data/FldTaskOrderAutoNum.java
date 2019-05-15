package com.glaway.sddq.overhaul.taskorder.data;

import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.IJpo;
import com.glaway.mro.jpo.IJpoSet;
import com.glaway.mro.jpo.JpoField;
import com.glaway.mro.util.StringUtil;

/**
 * 
 * 检修工单任务项自动序号
 * 
 * @author  bchen
 * @version  [版本号, 2018-1-26]
 * @since  [产品/模块版本]
 */
public class FldTaskOrderAutoNum extends JpoField
{
    /**
     * 注释内容
     */
  /*  private static final long serialVersionUID = 1L;
    
    @Override
    public void init()
        throws MroException
    {
        if (getJpo().isNew())
        {
            IJpo jpo = getJpo().getParent();
            int maxseq = 0;
            if (jpo != null)
            {
                IJpoSet index = getTaskOrderJpoSet();
                
                if (index != null)
                {
                    maxseq = (int)index.max("SEQ");
                    index.destroy();
                }
            }
            for (int i = 0, count = getJpo().getJpoSet().count(); i < count; i++)
            {
                IJpo others = getJpo().getJpoSet().getJpo(i);
                if (others != getJpo() && others != null)
                {
                    if (maxseq < others.getInt("SEQ"))
                    {
                        maxseq = others.getInt("SEQ");
                    }
                }
            }
            setValue("" + (maxseq + 5));
        }
    }
    
    private IJpoSet getTaskOrderJpoSet()
        throws MroException
    {
        String jxtasknum = getJpo().getString("JXTASKNUM");
        String assetnum = getJpo().getParent().getString("ASSETNUM");
        IJpoSet index =
            getUserServer().getJpoSet("JXTASKITEM",
                "JXTASKNUM='" + StringUtil.getSafeSqlStr(jxtasknum) + "' and assetnum = '" + assetnum + "' and "
                    + getJpo().getIdColName() + "!=" + getJpo().getId());
        index.reset();
        return index;
    }*/
    
}
