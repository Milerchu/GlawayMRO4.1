package com.glaway.sddq.back.msgConfig.data;

import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.IJpoSet;
import com.glaway.mro.jpo.JpoField;

/**
 * 
 * 消息配置中的APP字段类
 * 
 * @author  hyhe
 * @version  [版本号, 2018-2-5]
 * @since  [产品/模块版本]
 */
public class FldMsgApp extends JpoField
{
    /**
     * 注释内容
     */
    private static final long serialVersionUID = 1L;
    
    @Override
    public IJpoSet getList()
        throws MroException
    {
        //过滤掉已经配置工作流的APP
        String where =
            "VISIBLE ='1' and APP in ('SBOMCONFIG','ZCSBOM','SSCONFIG','ZCCONFIG','CUSTUAV','PRJSETUP','MSTONETMP','SERVPLAN','SERVORDER','FAILUREORD','FAULTMANA','FLTCLSFR','TRCHECKORD','TRNSNOTICE','TRANSPLAN','JXRULE','JOBBOOK','JXSCHEME','YEARPLAN','MONTHPLAN','JXTASKORDE','ITEM','STOREROOM','INVENTORY','ITEMREQ','MATERREQ','TRANSFER')";
        IJpoSet appset = this.getUserServer().getJpoSet("SYS_APP", where);
        appset.reset();
        return appset;
    }
}
