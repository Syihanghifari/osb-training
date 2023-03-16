package id.co.ogya.ebanking.ejb;

import javax.ejb.Remote;

import id.co.ogya.ebanking.ejb.impl.InvalidInputException;
import id.co.ogya.ebanking.ejb.request.InquiryBalanceRequest;
import id.co.ogya.ebanking.ejb.request.TransferRequest;
import id.co.ogya.ebanking.ejb.response.InquiryBalanceResponse;
import id.co.ogya.ebanking.ejb.response.TransferResponse;

@Remote
public interface EbankingService {
	public boolean isConnected();
	public InquiryBalanceResponse cekSaldo(InquiryBalanceRequest inquiryBalanceRequest);
	public TransferResponse kirim(TransferRequest transferRequest);
//	public TransferResponse transferRtgs(TransferRequest transferRequest);
}
