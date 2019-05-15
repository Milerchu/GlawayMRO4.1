package com.glaway.mro.app.imp.bean;

import java.io.IOException;

import com.glaway.mro.controller.AppBean;
import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.IJpoSet;

/**
 * 
 * <功能描述>
 * 数据导入程序AppBean
 *    功能：删除主记录时，级联删除子级记录。
 * @author zwliu
 *  
 * @date 2017-11-8 下午05:44:13
 * @version [GlawayMro4.1,2017-11-8]
 * @since   [GlawayMro4.1/模块版本]
 */
public class ImpIfaceAppBean extends AppBean
{
    @Override
    public int DELETE()
        throws MroException, IOException
    {
        super.DELETE();
        IJpoSet objecSet = getJpo().getJpoSet("IMPRELATION");
        if (objecSet != null || objecSet.count() > 0)
        {
            for (int i = 0; i < objecSet.count(); i++)
            {
                IJpoSet filedSet = objecSet.getJpo(i).getJpoSet("IMPATTRIBUTE");
                if (filedSet != null || filedSet.count() > 0)
                {
                    filedSet.deleteAll();
                }
            }
            objecSet.deleteAll();
        }
        return currentRow;
        
    }
}
