package com.glaway.sddq.back.msgConfig.data;

import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.IJpoSet;
import com.glaway.mro.system.MroServer;
import com.glaway.mro.system.crontask.BaseStatefulJob;

/**
 * 
 * 根据消息配置去生成消息定制任务类
 * 
 * @author  hyhe
 * @version  [版本号, 2018-2-27]
 * @since  [产品/模块版本]
 */
public class MsgConfigCronTask extends BaseStatefulJob
{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
    public void execute()
        throws MroException
    {
        IJpoSet msgconfigSet = MroServer.getMroServer().getSysJpoSet("MSGCONFIG");
        msgconfigSet.setUserWhere("status='已激活'");
        msgconfigSet.reset();
        for (int index = 0; index < msgconfigSet.count(); index++)
        {
            MsgConfig jpo = (MsgConfig)msgconfigSet.getJpo(index);
            jpo.createMsg();
        }
    }
}
