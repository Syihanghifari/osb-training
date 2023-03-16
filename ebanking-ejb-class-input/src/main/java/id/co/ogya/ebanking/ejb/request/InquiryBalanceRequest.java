package id.co.ogya.ebanking.ejb.request;

import java.io.Serializable;

public class InquiryBalanceRequest implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long accountNo;
	public Long getAccountNo() {
		return accountNo;
	}
	public void setAccountNo(Long accountNo) {
		this.accountNo = accountNo;
	}
}
