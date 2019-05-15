package com.glaway.sddq.material.locbin.data;

import com.glaway.mro.jpo.JpoSet;
/**
 * 
 * <仓位表JPoSet>
 * 
 * @author  public2795
 * @version  [版本号, 2019-1-8]
 * @since  [产品/模块版本]
 */
public class LocbinSet extends JpoSet {

    @Override
    public Locbin getJpoInstance()
    {
        return new Locbin();
    }
}
