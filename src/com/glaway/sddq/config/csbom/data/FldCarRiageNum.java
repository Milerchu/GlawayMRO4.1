package com.glaway.sddq.config.csbom.data;

import com.glaway.mro.exception.AppException;
import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.IJpo;
import com.glaway.mro.jpo.IJpoSet;
import com.glaway.mro.jpo.JpoField;
import com.glaway.mro.system.MroServer;

/**
 * 车厢号字段类
 * 
 * @author hyhe
 * @version [版本号, 2018-5-8]
 * @since [产品/模块版本]
 */
public class FldCarRiageNum extends JpoField {

    /**
     * 注释内容
     */
    private static final long serialVersionUID = 1L;

    // @Override
    // public void init()
    // throws MroException
    // {
    // this.setLookupMap(new String[] {"CARRIAGENUM"}, new String[] {
    // "CARNUM"});
    // }

    // @Override
    // public IJpoSet getList() throws MroException {
    // String ancestor = this.getField("ancestor").getValue();
    // // this.setListObject("ASSETCSCARRIAGE");
    // // this.setListWhere("ASSETCSNUM='" + ancestor + "'");
    // // return super.getList();
    //
    // this.setListObject("ASSETCSCARRIAGE");
    // String cmodel = this.getJpo().getParent().getField("CMODEL").getValue();
    // String parent = this.getField("PARENT").getValue();
    // String where = "";
    // String carnum = null;// 车厢编号
    // IJpoSet jposet = getUserServer().getJpoSet("ASSETCS",
    // "ASSETCSLEVEL = 'ASSET' and CMODEL='" + cmodel + "'");
    // jposet.setUserWhere("PARENT='" + parent + "'");
    // jposet.reset();
    // if (jposet != null && jposet.count() > 0) {
    // // String ancestor = jposet.getJpo(0).getString("ANCESTOR");
    //
    // for (int j = 0; j < jposet.count(); j++) {
    // IJpo assetcs = jposet.getJpo(j);
    // if (carnum == null) {
    // carnum = "'" + assetcs.getString("CARRIAGENUM") + "'";
    // } else {
    // carnum = carnum + ",'"
    // + assetcs.getString("CARRIAGENUM") + "'";
    // }
    // }
    //
    // if (carnum != null) {
    // where = where + " ASSETCSNUM='" + ancestor
    // + "' and carnum not in("
    // + carnum + ")";
    //
    // this.setListWhere(where);
    // }
    // if (!StringUtil.isStrEmpty(where)) {
    // this.setListWhere(where);
    // }
    //
    // return super.getList();
    // } else {
    // this.setListWhere("ASSETCSNUM='" + ancestor + "'");
    // return super.getList();
    // }
    // }

    @Override
    public void validate() throws MroException {
        // 对车厢号进行校验，唯一性
        String carriagenum = this.getInputMroType().getStringValue();
        IJpo  parent = this.getJpo().getParent();
        if(parent != null){
            String ancestor = parent.getString("ancestor");
            //找到该车结构下的所有车厢
            IJpoSet zcJpoSet = MroServer.getMroServer().getSysJpoSet("ASSETCS",
                    "assetcslevel = 'CAR' and ANCESTOR = '" + ancestor + "' and carriagenum='"+carriagenum+"'");
            if(zcJpoSet != null && zcJpoSet.count() > 0){
                throw new AppException("assetcs", "cxisrepeat");
            }
        }
        super.validate();
    }

}
