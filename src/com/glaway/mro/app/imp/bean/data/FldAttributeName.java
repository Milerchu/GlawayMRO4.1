package com.glaway.mro.app.imp.bean.data;

import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.IJpo;
import com.glaway.mro.jpo.IJpoSet;
import com.glaway.mro.jpo.JpoField;
import com.glaway.mro.util.StringUtil;

/**
 * 导入字段做唯一性校验
 * @author txu
 *
 */
public class FldAttributeName extends JpoField
{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1868563433563643978L;

	@Override
    public IJpoSet getList()
        throws MroException
    {
        IJpoSet jpoSet = super.getList();
        StringBuilder sb = new StringBuilder(100);
        sb.append("OBJECTNAME = '"+getJpo().getParent().getField("OBJECTNAME").getMroType().asString()+"' ");
        IJpoSet attrnameSet = getJpo().getJpoSet();
        boolean isEmpty = true;
        boolean appended = false;
        for (int i = 0, count = attrnameSet.count(); i < count; i++)
        {
            IJpo attrname = attrnameSet.getJpo(i);
            if (StringUtil.isStrEmpty(attrname.getString("ATTRIBUTENAME")))
            {
                continue;
            }
            
            if(!appended) {
                sb.append("AND FIELDNAME NOT IN (");
            } else {
                sb.append(",");
            }
            sb.append("'").append(StringUtil.getSafeSqlStr(attrname.getString("ATTRIBUTENAME"))).append("'");
            appended = true;
            if(i > 0 && i % 100 == 0) {
                appended = false;
                sb.append(")");
            }
            isEmpty = false;
        }
        if(appended && !isEmpty) {
            sb.append(")");
        }
        jpoSet.setUserWhere(sb.toString());
        jpoSet.reset();
        return jpoSet;
    }
}
