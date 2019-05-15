package com.glaway.sddq.overhaul.scheme.data;

import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.IJpo;
import com.glaway.mro.jpo.IJpoSet;
import com.glaway.mro.jpo.Jpo;
import com.glaway.mro.util.GWConstant;

/**
 * 
 * 检修方案交车检查项JPO
 * 
 * @author  bchen
 * @version  [版本号, 2018-5-19]
 * @since  [产品/模块版本]
 */
public class JcJobTestRecord extends Jpo implements IJpo
{
    /**
     * 注释内容
     */
    private static final long serialVersionUID = 1L;
    
    public void jccopyTask(IJpo jcjobtestrecord, IJpo newjp)
        throws MroException
    {
        String oldjcjobtestrecordTaskNum = jcjobtestrecord.getString("TASKNUM");
        String schemenum = newjp.getString("SCHEMENUM");
        String[] attrs = {"TESTPROJECT", "TESTCONTENT","SEQ","DESCRIPTION"};
        setValue(jcjobtestrecord, attrs, attrs, GWConstant.P_NOVALIDATION);
        setValue("SCHEMENUM", schemenum, GWConstant.P_NOVALIDATION);
        String newTaskNum = getString("TASKNUM");
        
        IJpoSet jcjobtestrecordingset =
            jcjobtestrecord.getJpoSet("$JCJOBTESTRECORDING", "JCJOBTESTRECORDING", "TASKNUM='"
                + oldjcjobtestrecordTaskNum + "'");
        String[] taskrecordattrs = { "LOC","STANDARDVALUE","SEQ","UNIT","TESTPROJECT"};
        IJpoSet newjcjobtestrecordingset = getJpoSet("JCJOBTESTRECORDING", "JCJOBTESTRECORDING", "1=0");
        for (int i = 0; i < jcjobtestrecordingset.count(); i++)
        {
            IJpo newJpo = newjcjobtestrecordingset.addJpo();
            newJpo.setValue(jcjobtestrecordingset.getJpo(i),
                taskrecordattrs,
                taskrecordattrs,
                GWConstant.P_NOVALIDATION);
            newJpo.setValue("SCHEMENUM", schemenum, GWConstant.P_NOVALIDATION);
            newJpo.setValue("TASKNUM", newTaskNum, GWConstant.P_NOVALIDATION);
        }
    }
}
