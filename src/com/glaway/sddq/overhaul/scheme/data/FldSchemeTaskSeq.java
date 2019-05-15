package com.glaway.sddq.overhaul.scheme.data;

import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.IJpo;
import com.glaway.mro.jpo.IJpoSet;
import com.glaway.mro.jpo.JpoField;
import com.glaway.mro.util.StringUtil;

public class FldSchemeTaskSeq extends JpoField {


	private static final long serialVersionUID = 1L;
    
    @Override
    public void init()
        throws MroException
    {
        if (getJpo().isNew())
        {
            IJpo indexJpo = getJpo().getParent();
            int maxseq = 0;
            if (indexJpo != null)
            {
                IJpoSet indexColSet = getJobTestRecord();
                if (indexColSet != null)
                {
                    maxseq = (int)indexColSet.max("SEQ");
                    indexColSet.destroy();
                }
            }
            for (int i = 0, count = getJpo().getJpoSet().count(); i < count; i++)
            {
                IJpo other = getJpo().getJpoSet().getJpo(i);
                if (other != getJpo() && other != null)
                {
                    if (maxseq < other.getInt("SEQ"))
                    {
                        maxseq = other.getInt("SEQ");
                    }
                }
            }
            this.getJpo().setValue("seq" ,(maxseq + 1));
            //setValue("" + (maxseq + 1));
        }
    }
    
    private IJpoSet getJobTestRecord()
        throws MroException
    {
        IJpoSet indexColSet = null;
        if (getJpo() != null)
        {
            String schemenum = getJpo().getParent().getString("SCHEMENUM");
            String tasknum = getJpo().getParent().getString("TASKNUM");
            indexColSet =
                getUserServer().getJpoSet("SCHEMEJOBTASKRECORD",
                    "schemenum='" + StringUtil.getSafeSqlStr(schemenum) + "' and tasknum='"+tasknum+"' and " + getJpo().getIdColName() + " !="
                        + getJpo().getId());
            indexColSet.reset();
        }
        return indexColSet;
    }
}
