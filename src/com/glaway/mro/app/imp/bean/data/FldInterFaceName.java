package com.glaway.mro.app.imp.bean.data;

import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.IJpoSet;
import com.glaway.mro.jpo.JpoField;

/**
 * 
 * <功能描述>
 * 数据导入接口名称唯一性校验
 * @author zwliu
 *
 * @date 2017-11-8 上午09:31:24
 * @version [GlawayMro4.1,2017-11-8]
 * @since   [GlawayMro4.1/模块版本]
 */

public class FldInterFaceName extends JpoField
{
    /**
     * 唯一性校验
     */
    @Override
    public void validate()
        throws MroException
    {
        String curvalue = getInputMroType().asString();
        if (curvalue != null && curvalue != "")
        {
            IJpoSet objectSet = getUserServer().getJpoSet("impiface", "IFACENAME = '" + curvalue + "'");
            
            if (objectSet != null && objectSet.count() > 0)
            {
                throw new MroException("imp", "namerepeat");
            }
        }
        super.validate();
    }
}
