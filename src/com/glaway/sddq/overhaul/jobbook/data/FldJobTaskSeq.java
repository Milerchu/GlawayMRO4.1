package com.glaway.sddq.overhaul.jobbook.data;

import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.IJpo;
import com.glaway.mro.jpo.IJpoSet;
import com.glaway.mro.jpo.JpoField;
import com.glaway.mro.util.StringUtil;

public class FldJobTaskSeq extends JpoField {

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
	                IJpoSet indexColSet = getJobbookTask();
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
	            setValue("" + (maxseq + 1));
	        }
	    }
	    
	    private IJpoSet getJobbookTask()
	        throws MroException
	    {
	        IJpoSet indexColSet = null;
	        if (getJpo() != null)
	        {
	            String jpnum = getJpo().getString("JPNUM");
	            indexColSet =
	                getUserServer().getJpoSet("JOBTASKRECORD",
	                    "JPNUM='" + StringUtil.getSafeSqlStr(jpnum) + "' and " + getJpo().getIdColName() + "!="
	                        + getJpo().getId());
	            indexColSet.reset();
	        }
	        return indexColSet;
	    }
}
