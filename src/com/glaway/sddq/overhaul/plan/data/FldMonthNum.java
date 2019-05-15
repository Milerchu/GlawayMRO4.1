package com.glaway.sddq.overhaul.plan.data;

import com.glaway.mro.exception.AppException;
import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.IJpo;
import com.glaway.mro.jpo.IJpoSet;
import com.glaway.mro.jpo.JpoField;
import com.glaway.mro.util.GWConstant;

/**
 * 
 * 月度计划中月度字段类
 * 
 * @author  bchen
 * @version  [版本号, 2018-1-25]
 * @since  [产品/模块版本]
 */
public class FldMonthNum extends JpoField
{
    
    private static final long serialVersionUID = 1L;
    
    @Override
    public void action()
        throws MroException
    {
        IJpo jpo = this.getJpo();
        int month = this.getIntValue();
        
        if (month < 1 || month > 12)
        {
            throw new AppException("REPAIRPLANS", "montnnum");
        }
        if (jpo.getJpoSet("MONTHYEARPLAN").isEmpty())
        {
            this.getJpo().setValue("MONTHAMOUNT", 0, GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
        }
        else
        {
            IJpoSet monthyearplanSet = this.getJpo().getJpoSet("MONTHYEARPLAN");
            switch (month)
            {
                case 1:
                    int one = 0;
                    for (int i = 0; i < monthyearplanSet.count(); i++)
                    {
                        IJpo monthyearplan = monthyearplanSet.getJpo(i);
                        one += monthyearplan.getInt("ONE");
                    }
                    this.getJpo().setValue("MONTHAMOUNT", one, GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
                    break;
                case 2:
                    int two = 0;
                    for (int i = 0; i < monthyearplanSet.count(); i++)
                    {
                        IJpo monthyearplan = monthyearplanSet.getJpo(i);
                        two += monthyearplan.getInt("TWO");
                    }
                    this.getJpo().setValue("MONTHAMOUNT", two, GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
                    break;
                case 3:
                    int three = 0;
                    for (int i = 0; i < monthyearplanSet.count(); i++)
                    {
                        IJpo monthyearplan = monthyearplanSet.getJpo(i);
                        three += monthyearplan.getInt("THREE");
                    }
                    this.getJpo().setValue("MONTHAMOUNT", three, GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
                    break;
                case 4:
                    int four = 0;
                    for (int i = 0; i < monthyearplanSet.count(); i++)
                    {
                        IJpo monthyearplan = monthyearplanSet.getJpo(i);
                        four += monthyearplan.getInt("FOUR");
                    }
                    this.getJpo().setValue("MONTHAMOUNT", four, GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
                    break;
                case 5:
                    int five = 0;
                    for (int i = 0; i < monthyearplanSet.count(); i++)
                    {
                        IJpo monthyearplan = monthyearplanSet.getJpo(i);
                        five += monthyearplan.getInt("FIVE");
                    }
                    this.getJpo().setValue("MONTHAMOUNT", five, GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
                    break;
                case 6:
                    int six = 0;
                    for (int i = 0; i < monthyearplanSet.count(); i++)
                    {
                        IJpo monthyearplan = monthyearplanSet.getJpo(i);
                        six += monthyearplan.getInt("SIX");
                    }
                    this.getJpo().setValue("MONTHAMOUNT", six, GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
                    break;
                case 7:
                    int seven = 0;
                    for (int i = 0; i < monthyearplanSet.count(); i++)
                    {
                        IJpo monthyearplan = monthyearplanSet.getJpo(i);
                        seven += monthyearplan.getInt("SEVEN");
                    }
                    this.getJpo().setValue("MONTHAMOUNT", seven, GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
                    break;
                case 8:
                    int eight = 0;
                    for (int i = 0; i < monthyearplanSet.count(); i++)
                    {
                        IJpo monthyearplan = monthyearplanSet.getJpo(i);
                        eight += monthyearplan.getInt("EIGHT");
                    }
                    this.getJpo().setValue("MONTHAMOUNT", eight, GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
                    break;
                case 9:
                    int nine = 0;
                    for (int i = 0; i < monthyearplanSet.count(); i++)
                    {
                        IJpo monthyearplan = monthyearplanSet.getJpo(i);
                        nine += monthyearplan.getInt("NINE");
                    }
                    this.getJpo().setValue("MONTHAMOUNT", nine, GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
                    break;
                case 10:
                    int ten = 0;
                    for (int i = 0; i < monthyearplanSet.count(); i++)
                    {
                        IJpo monthyearplan = monthyearplanSet.getJpo(i);
                        ten += monthyearplan.getInt("TEN");
                    }
                    this.getJpo().setValue("MONTHAMOUNT", ten, GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
                    break;
                case 11:
                    int eleven = 0;
                    for (int i = 0; i < monthyearplanSet.count(); i++)
                    {
                        IJpo monthyearplan = monthyearplanSet.getJpo(i);
                        eleven += monthyearplan.getInt("ELEVEN");
                    }
                    this.getJpo().setValue("MONTHAMOUNT", eleven, GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
                    break;
                case 12:
                    int twelve = 0;
                    for (int i = 0; i < monthyearplanSet.count(); i++)
                    {
                        IJpo monthyearplan = monthyearplanSet.getJpo(i);
                        twelve += monthyearplan.getInt("TWELVE");
                    }
                    this.getJpo().setValue("MONTHAMOUNT", twelve, GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
                    break;
            }
        }
    }
}
