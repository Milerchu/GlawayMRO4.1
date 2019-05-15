package com.glaway.sddq.overhaul.scheme.data;

import com.glaway.mro.jpo.IJpoSet;
import com.glaway.mro.jpo.JpoSet;

/**
 * 
 * 检修方案jpoSet
 * 
 * @author  hyhe
 * @version  [版本号, 2017-10-19]
 * @since  [产品/模块版本]
 */
public class RepairSchemeSet extends JpoSet implements IJpoSet{
	
   private static final long serialVersionUID = 1L;
   
   public RepairScheme getJpoInstance()
   {
       return new RepairScheme();
   }
}