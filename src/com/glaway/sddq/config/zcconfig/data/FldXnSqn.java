package com.glaway.sddq.config.zcconfig.data;

import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.IJpoSet;
import com.glaway.mro.jpo.JpoField;
import com.glaway.mro.util.GWConstant;

/**
 * @ClassName FldXnSqn
 * @Description 工具栏中的选择产品序列号字段类
 * @author public2175
 * @Date 2018-8-10 下午2:47:35
 * @version 1.0.0
 */
public class FldXnSqn extends JpoField {

    /**
     * @Field @serialVersionUID : 默认序列化ID
     */
    private static final long serialVersionUID = 1L;

    @Override
    public void init() throws MroException {
        this.setLookupMap(new String[] {"SQN", "ITEMNUM", "ASSETNUM", "LINENUM", "RNUM", "CONFIGNUM", "LOC", "LOCDESC",
                "SOFTVERSION" }, new String[] {"SQN", "ITEMNUM", "ASSETNUM", "LINENUM", "RNUM", "CONFIGNUM", "LOC",
                "LOCDESC", "SOFTVERSION" });
        this.setFlag(GWConstant.S_READONLY,true);
    }
    
    @Override
    public IJpoSet getList() throws MroException
    {
        this.setFlag(GWConstant.S_READONLY, false);

        this.setListObject("ASSET");
        //查找从ERP及MES系统通过接口发送到MRO系统中的数据，且该数据没有入库到MRO系统，MRO默认设置type=0
        this.setListWhere("assetlevel='ASSET' and type='0' and sqn is not null and LOCATION is null and iserp != '0'");
        return super.getList();
    }

    @Override
    public void action() throws MroException {
        super.action();
        this.setFlag(GWConstant.S_READONLY,true);

    }
}
