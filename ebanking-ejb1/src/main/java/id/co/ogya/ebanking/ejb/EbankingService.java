package id.co.ogya.ebanking.ejb;

import javax.ejb.Remote;


import id.co.ogya.ebanking.ejb.response.InquiryBalanceResponse;
import id.co.ogya.ebanking.ejb.response.TransferResponse;

@Remote
public interface EbankingService {
	public boolean isConnected();
	public InquiryBalanceResponse cekSaldo(Long accountNo);
	public TransferResponse kirim(Long accountFrom,Long accountTo,Long amount,String notes,Long branchCode);
}