package com.glaway.sddq.config.sbom.data;

import com.glaway.mro.jpo.IJpoSet;
import com.glaway.mro.jpo.JpoSet;

/**
 * 
 * @author hyhe
 *
 */
public class ModelSbomSet extends JpoSet implements IJpoSet{
	
	 /**
     * 默认序列化ID
     */
    private static final long serialVersionUID = 1L;
    
    public ModelSbom getJpoInstance()
    {
        return new ModelSbom();
    }
}
