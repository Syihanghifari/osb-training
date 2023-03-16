package id.co.ogya.ebanking.ejb.request;

import java.io.Serializable;

public class TransferRequest implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long accountFrom;
	private Long accountTo;
	private Long amount;
	private String notes;
	private Long branchCode;

	public Long getBranchCode() {
		return branchCode;
	}

	public void setBranchCode(Long branchCode) {
		this.branchCode = branchCode;
	}

	public Long getAccountFrom() {
		return accountFrom;
	}

	public void setAccountFrom(Long accountFrom) {
		this.accountFrom = accountFrom;
	}

	public Long getAccountTo() {
		return accountTo;
	}

	public void setAccountTo(Long accountTo) {
		this.accountTo = accountTo;
	}

	public Long getAmount() {
		return amount;
	}

	public void setAmount(Long amount) {
		this.amount = amount;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public String toString() {
		return accountFrom + "," + accountTo + "," + amount + "," + notes + ",";
	}

}
