package com.glaway.sddq.service.transplan.data;

import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.JpoField;

/**
 * @author Miller
 * @date 2019/08/18
 */
public class FldTransDistCarnums extends JpoField {

    @Override
    public void validate() throws MroException {

        super.validate();
        //当前输入值
        String inputVal = this.getInputMroType().asString().trim();
        String reg = "^[^,]+[0-9A-Za-z,]+";
        if(!inputVal.matches(reg)){
            throw new MroException("车号格式有误！");
        }

    }
}
