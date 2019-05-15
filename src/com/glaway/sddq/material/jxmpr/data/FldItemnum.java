package com.glaway.sddq.material.jxmpr.data;

import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.IJpoSet;
import com.glaway.mro.jpo.JpoField;

/**
 * 检修领料单选择物料编码字段类
 * 
 * @author zzx
 * @version [GlawayMro4.1, 2018-7-30]
 * @since [GlawayMro4.1/检修领料单]
 */
public class FldItemnum extends JpoField {

    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 6714808953825812433L;

    /**
     * @throws MroException
     */

    /**
     * 获取可选择列表数据
     * 
     * @return
     * @throws MroException
     */
    @Override
    public IJpoSet getList() throws MroException {
        setListObject("locbinitem");
        String stockaddress = this.getJpo().getString("STOCKADDRESS");
        String listSql = "";
        listSql = "location='" + stockaddress + "' and itemnum in (select itemnum from sys_item)";
        setListWhere(listSql); 
        return super.getList();
    }
}
