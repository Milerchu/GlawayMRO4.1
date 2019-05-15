package com.glaway.sddq.back.Interface.webservice.erp.sxtransfer;

import java.util.List;

/**
 * 
 * <功能描述>送修单行信息实例类
 * 
 * @author 20167088
 * @version [版本号, 2018-7-17]
 * @since [产品/模块版本]
 */
public class ZfunErptoMroResponse {
	private String TRANSFERNUM;
	private String SENDORG;
	private String SXTYPE;
	private String COURIERNUM;
	private String CREATEBY;
	private String CREATEDATE;
	private String LISTTYPE;
	private String REPAIRORG;
	private String RECEIVESTOREROOM;
	private String SENDSTOREROOM;
	private String SENDDATE;
	private String COMPANY;
	private String CONTACTBY;
	private String CONTACTPHONE;
	private String STATUS;
	private String MSG;
	private String REASON;
	private String CREATEDATESTIME;

	private String CREATEBYDISPLAYNAME;
	private String RECIVEBY;
	private String RECIVEBYDISPLAYNAME;
	private String RECIVEDATE;
	private String RECIVETIME;
	private String PLANREPAURDATE;

	public String getCREATEBYDISPLAYNAME() {
		return CREATEBYDISPLAYNAME;
	}

	public void setCREATEBYDISPLAYNAME(String cREATEBYDISPLAYNAME) {
		CREATEBYDISPLAYNAME = cREATEBYDISPLAYNAME;
	}

	public String getRECIVEBY() {
		return RECIVEBY;
	}

	public void setRECIVEBY(String rECIVEBY) {
		RECIVEBY = rECIVEBY;
	}

	public String getRECIVEBYDISPLAYNAME() {
		return RECIVEBYDISPLAYNAME;
	}

	public void setRECIVEBYDISPLAYNAME(String rECIVEBYDISPLAYNAME) {
		RECIVEBYDISPLAYNAME = rECIVEBYDISPLAYNAME;
	}

	public String getRECIVEDATE() {
		return RECIVEDATE;
	}

	public void setRECIVEDATE(String rECIVEDATE) {
		RECIVEDATE = rECIVEDATE;
	}

	public String getRECIVETIME() {
		return RECIVETIME;
	}

	public void setRECIVETIME(String rECIVETIME) {
		RECIVETIME = rECIVETIME;
	}

	public String getCREATEDATESTIME() {
		return CREATEDATESTIME;
	}

	public void setCREATEDATESTIME(String cREATEDATESTIME) {
		CREATEDATESTIME = cREATEDATESTIME;
	}

	private List<ZfunErptoMroResponses> LINE;

	public List<ZfunErptoMroResponses> getLINE() {
		return LINE;
	}

	public void setLINE(List<ZfunErptoMroResponses> lINE) {
		LINE = lINE;
	}

	public String getTRANSFERNUM() {
		return TRANSFERNUM;
	}

	public void setTRANSFERNUM(String tRANSFERNUM) {
		TRANSFERNUM = tRANSFERNUM;
	}

	public String getSENDORG() {
		return SENDORG;
	}

	public void setSENDORG(String sENDORG) {
		SENDORG = sENDORG;
	}

	public String getSXTYPE() {
		return SXTYPE;
	}

	public void setSXTYPE(String sXTYPE) {
		SXTYPE = sXTYPE;
	}

	public String getCOURIERNUM() {
		return COURIERNUM;
	}

	public void setCOURIERNUM(String cOURIERNUM) {
		COURIERNUM = cOURIERNUM;
	}

	public String getCREATEBY() {
		return CREATEBY;
	}

	public void setCREATEBY(String cREATEBY) {
		CREATEBY = cREATEBY;
	}

	public String getCREATEDATE() {
		return CREATEDATE;
	}

	public void setCREATEDATE(String cREATEDATE) {
		CREATEDATE = cREATEDATE;
	}

	public String getLISTTYPE() {
		return LISTTYPE;
	}

	public void setLISTTYPE(String lISTTYPE) {
		LISTTYPE = lISTTYPE;
	}

	public String getREPAIRORG() {
		return REPAIRORG;
	}

	public void setREPAIRORG(String rEPAIRORG) {
		REPAIRORG = rEPAIRORG;
	}

	public String getRECEIVESTOREROOM() {
		return RECEIVESTOREROOM;
	}

	public void setRECEIVESTOREROOM(String rECEIVESTOREROOM) {
		RECEIVESTOREROOM = rECEIVESTOREROOM;
	}

	public String getSENDSTOREROOM() {
		return SENDSTOREROOM;
	}

	public void setSENDSTOREROOM(String sENDSTOREROOM) {
		SENDSTOREROOM = sENDSTOREROOM;
	}

	public String getSENDDATE() {
		return SENDDATE;
	}

	public void setSENDDATE(String sENDDATE) {
		SENDDATE = sENDDATE;
	}

	public String getCONTACTBY() {
		return CONTACTBY;
	}

	public void setCONTACTBY(String cONTACTBY) {
		CONTACTBY = cONTACTBY;
	}

	public String getCONTACTPHONE() {
		return CONTACTPHONE;
	}

	public void setCONTACTPHONE(String cONTACTPHONE) {
		CONTACTPHONE = cONTACTPHONE;
	}

	public String getSTATUS() {
		return STATUS;
	}

	public void setSTATUS(String sTATUS) {
		STATUS = sTATUS;
	}

	public String getMSG() {
		return MSG;
	}

	public void setMSG(String mSG) {
		MSG = mSG;
	}

	public String getREASON() {
		return REASON;
	}

	public void setREASON(String rEASON) {
		REASON = rEASON;
	}

	public String getCOMPANY() {
		return COMPANY;
	}

	public void setCOMPANY(String cOMPANY) {
		COMPANY = cOMPANY;
	}
	public String getPLANREPAURDATE() {
		return PLANREPAURDATE;
	}

	public void setPLANREPAURDATE(String pLANREPAURDATE) {
		PLANREPAURDATE = pLANREPAURDATE;
	}
}
