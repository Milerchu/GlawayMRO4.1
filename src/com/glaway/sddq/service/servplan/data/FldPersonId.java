package com.glaway.sddq.service.servplan.data;

import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.IJpo;
import com.glaway.mro.jpo.IJpoSet;
import com.glaway.mro.jpo.JpoField;
import com.glaway.mro.jpo.type.MroType;
import com.glaway.mro.system.MroServer;
import com.glaway.mro.util.StringUtil;

/**
 * 
 * <选择项目人员代码>
 * 
 * @author  ygao
 * @version  [版本号, 2017-10-19]
 * @since  [产品/模块版本]
 */
public class FldPersonId extends JpoField
{
    
    /**
     * 注释内容
     */
    private static final long serialVersionUID = 1L;
    
    @Override
    public void action()
        throws MroException
    {
        super.action();
        MroType value = getInputMroType();
        IJpo prjTeammemberjpo = getJpo();
        IJpoSet prjmemberset =
            MroServer.getMroServer().getJpoSet("PROJECTTEAMEMBER", MroServer.getMroServer().getSystemUserServer());
        prjmemberset.setQueryWhere("PRJTEAMMEMBERNUM = '" + value.asString() + "'");
        prjmemberset.reset();
        if (!prjmemberset.isEmpty())
        {
            IJpo prjmember = prjmemberset.getJpo();
            prjTeammemberjpo.setValue("PERSONNAME", prjmember.getString("DISPLAYNAME"));
            prjTeammemberjpo.setValue("DEPTNUM", prjmember.getString("DEPTNUM"));
            prjTeammemberjpo.setValue("PASSAGEWAY", prjmember.getString("PASSAGEWAY"));
            prjTeammemberjpo.setValue("GARDE", prjmember.getString("GARDE"));
            //            prjTeammemberjpo.setValue("COMMUNICATIONPLAN", projectinfo.getString("COMMUNICATIONPLAN"));
            //            prjTeammemberjpo.setValue("WORKORDERNUM", projectinfo.getString("WORKORDERNUM"));
        }
        
    }
    
    @Override
    public IJpoSet getList()
        throws MroException
    {
        IJpoSet servplanset = getJpo().getJpoSet("SERVPLAN");
        StringBuffer personidbuff = new StringBuffer();
        String personids = "";
        if (!servplanset.isEmpty())
        {
            IJpo servplan = servplanset.getJpo();
            IJpoSet prjmbset = servplan.getJpoSet("PROJECTTEAMEMBER");
            if (!prjmbset.isEmpty())
            {
                for (int i = 0; i < prjmbset.count(); i++)
                {
                    IJpo prjmb = prjmbset.getJpo(i);
                    String personid = prjmb.getString("PERSONID");
                    personidbuff.append("'" + personid + "'" + ",");
                }
                personids = personidbuff.toString().substring(0, personidbuff.toString().length() - 1);
                
            }
        }
        
        //String personid = personidbuff.toString().substring(0,personidbuff.toString().length()-1);
        String where = "";
        if (StringUtil.isStrEmpty(personids))
        {
            where = "personid in ('')";
        }
        else
        {
            where = "personid in (" + personids + ")";
        }
        setListWhere(where);
        return super.getList();
    }
    
}
