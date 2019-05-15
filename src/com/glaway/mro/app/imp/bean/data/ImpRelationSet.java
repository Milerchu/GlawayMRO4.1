package com.glaway.mro.app.imp.bean.data;

import com.glaway.mro.jpo.JpoSet;

public class ImpRelationSet extends JpoSet {
	/**
     * 注释内容
     */
    private static final long serialVersionUID = -5243891854916031039L;
    
    public ImpRelation getJpoInstance()
    {
        return new ImpRelation();
    }
}
