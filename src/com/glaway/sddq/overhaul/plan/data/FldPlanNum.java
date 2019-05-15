package com.glaway.sddq.overhaul.plan.data;

import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.IJpoSet;
import com.glaway.mro.jpo.JpoField;

/**
 * 
 * 月度计划中关联的年度计划的字段类
 * 
 * @author  hyhe
 * @version  [版本号, 2017-10-20]
 * @since  [产品/模块版本]
 */
public class FldPlanNum extends JpoField
{
    
    private static final long serialVersionUID = 1L;
    
    @Override
    public void init()
        throws MroException
    {
        this.setLookupMap(new String[] {"YEARPLANNUM"}, new String[] {"PLANNUM"});
    }
    
    public IJpoSet getList()
        throws MroException
    {
        this.setListObject("REPAIRPLANS");
        this.setListWhere("TYPE='1' and status ='已审批'");
        return super.getList();
    }
}
/*    @Override
    public void action()
        throws MroException
    {
        super.action();
        if (this.isValueChanged())
        {
            IJpoSet jposet = this.getJpo().getJpoSet("YEARPLAN");
            if (jposet != null && !jposet.isEmpty())
            {
                IJpo planJpo = jposet.getJpo(0);
                this.getJpo().setValue("SCHEMENUM",
                    planJpo.getString("SCHEMENUM"),
                    GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
                IJpo jpo = this.getJpo();
                int month = jpo.getInt("MONTH");
                if (jpo.getJpoSet("MONTHYEARPLAN").isEmpty())
                {
                    this.getJpo().setValue("MONTHAMOUNT", 0, GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
                }
                else
                {
                    switch (month)
                    {
                        case 1:
                            this.getJpo().setValue("MONTHAMOUNT",
                                jpo.getJpoSet("MONTHYEARPLAN").getJpo(0).getInt("ONE"),
                                GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
                            break;
                        case 2:
                            this.getJpo().setValue("MONTHAMOUNT",
                                jpo.getJpoSet("MONTHYEARPLAN").getJpo(0).getInt("TWO"),
                                GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
                            break;
                        case 3:
                            this.getJpo().setValue("MONTHAMOUNT",
                                jpo.getJpoSet("MONTHYEARPLAN").getJpo(0).getInt("THREE"),
                                GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
                            break;
                        case 4:
                            this.getJpo().setValue("MONTHAMOUNT",
                                jpo.getJpoSet("MONTHYEARPLAN").getJpo(0).getInt("FOUR"),
                                GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
                            break;
                        case 5:
                            this.getJpo().setValue("MONTHAMOUNT",
                                jpo.getJpoSet("MONTHYEARPLAN").getJpo(0).getInt("FIVE"),
                                GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
                            break;
                        case 6:
                            this.getJpo().setValue("MONTHAMOUNT",
                                jpo.getJpoSet("MONTHYEARPLAN").getJpo(0).getInt("SIX"),
                                GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
                            break;
                        case 7:
                            this.getJpo().setValue("MONTHAMOUNT",
                                jpo.getJpoSet("MONTHYEARPLAN").getJpo(0).getInt("SEVEN"),
                                GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
                            break;
                        case 8:
                            this.getJpo().setValue("MONTHAMOUNT",
                                jpo.getJpoSet("MONTHYEARPLAN").getJpo(0).getInt("EIGHT"),
                                GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
                            break;
                        case 9:
                            this.getJpo().setValue("MONTHAMOUNT",
                                jpo.getJpoSet("MONTHYEARPLAN").getJpo(0).getInt("NINE"),
                                GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
                            break;
                        case 10:
                            this.getJpo().setValue("MONTHAMOUNT",
                                jpo.getJpoSet("MONTHYEARPLAN").getJpo(0).getInt("TEN"),
                                GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
                            break;
                        case 11:
                            this.getJpo().setValue("MONTHAMOUNT",
                                jpo.getJpoSet("MONTHYEARPLAN").getJpo(0).getInt("ELEVEN"),
                                GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
                            break;
                        case 12:
                            this.getJpo().setValue("MONTHAMOUNT",
                                jpo.getJpoSet("MONTHYEARPLAN").getJpo(0).getInt("TWELVE"),
                                GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
                            break;
                    }
                }
            }
        }
    }*/

