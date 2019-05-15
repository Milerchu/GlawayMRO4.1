package com.glaway.sddq.material.materborrow.bean;

import java.io.IOException;

import com.glaway.mro.controller.AppBean;
import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.IJpo;
import com.glaway.mro.jpo.IJpoSet;
/**
 * 
 * <配件借用应用程序APPBEAN>
 * 
 * @author  public2795
 * @version  [版本号, 2019-1-25]
 * @since  [产品/模块版本]
 */
public class MaterBorrowAppBean extends AppBean {
	/**
	 * 
	 * <配件借用续借按钮功能/>
	 * @throws MroException
	 * @throws IOException [参数说明]
	 *
	 */
	public void SECONDBORROW() throws MroException, IOException {
		String status=this.getJpo().getString("status");//状态
		String SECONDBORDATE=this.getJpo().getString("SECONDBORDATE");//续借时间
		if(status.equalsIgnoreCase("新建")||status.equalsIgnoreCase("待审批")||status.equalsIgnoreCase("全部归还")||status.equalsIgnoreCase("关闭")){
			throw new MroException("该状态下不能续借");
		}else{
			if(!SECONDBORDATE.isEmpty()){
				throw new MroException("只能续借一次，且已经续借");
			}
		}
	}

	@Override
	public int ROUTEWF() throws Exception {
		// TODO Auto-generated method stub
		IJpo MATERBORROWJPO = getJpo();
		String MATERBORROWid=MATERBORROWJPO.getString("MATERBORROWid");
		IJpoSet MATERBORROWline = MATERBORROWJPO.getJpoSet("materborrowline");
		if(MATERBORROWline.isEmpty()){
			throw new MroException("借用行为空，不能发送流程");
		}
		IJpoSet docset=MATERBORROWJPO.getJpoSet("DOCLINKS");
		if(docset.isEmpty()){
			throw new MroException("请先上传附件在发送工作流");
		}
		return super.ROUTEWF();
	}
	
}
