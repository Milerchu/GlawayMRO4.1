package com.glaway.sddq.overhaul.plan.data;

import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.JpoField;
import com.glaway.mro.util.GWConstant;

/**
 * 
 * @ClassName FldSunYearMonth
 * @Description 根据每个月的数量自动计算总数量的字段类
 * @author public2175
 * @Date 2018-8-18 下午4:50:06
 * @version 1.0.0
 */
public class FldSunYearMonth extends JpoField {

    /**
     * @Field @serialVersionUID : 默认序列化ID
     */
    private static final long serialVersionUID = 1L;

    @Override
    public void action() throws MroException {
        int one = this.getJpo().getInt("ONE");
        int two = this.getJpo().getInt("TWO");
        int three = this.getJpo().getInt("THREE");
        int four = this.getJpo().getInt("FOUR");
        int five = this.getJpo().getInt("FIVE");
        int six = this.getJpo().getInt("SIX");
        int seven = this.getJpo().getInt("SEVEN");
        int eight = this.getJpo().getInt("EIGHT");
        int nine = this.getJpo().getInt("NINE");
        int ten = this.getJpo().getInt("TEN");
        int eleven = this.getJpo().getInt("ELEVEN");
        int twelve = this.getJpo().getInt("TWELVE");
        int sum = one + two + three + four + five + six + seven + eight + nine
                + ten + eleven + twelve;
        this.getJpo().setValue("TOTALAMOUNT", sum, GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
        if(this.getJpo().isNew()){
            this.getJpo().setValue("PLANAMOUNT", sum, GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
        }
        super.action();
    }
    
}
