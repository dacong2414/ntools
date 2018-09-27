package com.nuls.io.model.entity;

import java.io.Serializable;
import java.util.Date;

public class NulsBnb implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7419387030327267262L;

	private Long recId;

    private String price;

    private String amount;

    private String lastUpdateId;
    
    private String bidsOrAsk;
    
    private String total;
    
    private Date gmtCreate;
    
    private Date gmtMod;

	public String getTotal() {
		return total;
	}

	public void setTotal(String total) {
		this.total = total;
	}

	public Long getRecId() {
		return recId;
	}

	public void setRecId(Long recId) {
		this.recId = recId;
	}

	public String getBidsOrAsk() {
		return bidsOrAsk;
	}

	public void setBidsOrAsk(String bidsOrAsk) {
		this.bidsOrAsk = bidsOrAsk;
	}

	public Date getGmtCreate() {
		return gmtCreate;
	}

	public void setGmtCreate(Date gmtCreate) {
		this.gmtCreate = gmtCreate;
	}

	public Date getGmtMod() {
		return gmtMod;
	}

	public void setGmtMod(Date gmtMod) {
		this.gmtMod = gmtMod;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public String getLastUpdateId() {
		return lastUpdateId;
	}

	public void setLastUpdateId(String lastUpdateId) {
		this.lastUpdateId = lastUpdateId;
	}
	
	
}
