package com.glaway.sddq.material.detectedmater.data;

import com.glaway.mro.jpo.IJpoSet;
import com.glaway.mro.jpo.JpoSet;

/**
 * 
 * 有效性检测JpoSet
 * 
 * @author  bchen
 * @version  [版本号, 2018-6-2]
 * @since  [产品/模块版本]
 */
public class DetectedMaterSet extends JpoSet implements IJpoSet
{
    private static final long serialVersionUID = 1L;
    
    public DetectedMater getJpoInstance()
    {
        return new DetectedMater();
    }
}
