package id.co.ogya.ebanking.ejb.test;

import id.co.ogya.ebanking.ejb.EbankingService;
import id.co.ogya.ebanking.ejb.request.InquiryBalanceRequest;
import id.co.ogya.ebanking.ejb.request.TransferRequest;
import id.co.ogya.ebanking.ejb.response.InquiryBalanceResponse;
import id.co.ogya.ebanking.ejb.response.TransferResponse;
import id.co.ogya.ebanking.ejb.util.ServiceFactory;

public class TestEbanking {
	public static void main(String[] args) {
//		String simpleDataSourceAccessJNDIName =
//				"EbankingServiceImpl#id.co.ogya.ebanking.ejb.EbankingService";
		
//		ServiceFactory serviceFactory = new ServiceFactory();
//		try{
//			EbankingService ebankingService = 
//					(EbankingService) serviceFactory.getService(simpleDataSourceAccessJNDIName);
//			boolean isAbleToConnect = ebankingService.isConnected();
//			System.out.println("is Able to connect " + isAbleToConnect);
//			InquiryBalanceResponse response = new InquiryBalanceResponse();
//			InquiryBalanceRequest coba = new InquiryBalanceRequest();
//			coba.setAccountNo(300L);
//			response = ebankingService.cekSaldo(coba);
//			System.out.println(response.getAccountBalance());
			
//			TransferResponse response = new TransferResponse();
//			TransferRequest coba = new TransferRequest();
//			coba.setAccountFrom(100L);
//			coba.setAccountTo(200L);
//			coba.setAmount(50000L);
//			coba.setNotes("coba test");
//			response = ebankingService.kirim(coba);
//			System.out.println(response.getReferenceNumber());
//			System.out.println(response.getErrorMessage());
//			InsertNasabah insertNasabah = new InsertNasabah();
//			//test database
//			insertNasabah.setNoNasabah(20L);
//			insertNasabah.setNik(100000L);
//			insertNasabah.setNamaLengkap("jajaja");
//			insertNasabah.setTempatLahir("malang");
//			insertNasabah.setTanggalLahir(null);
//			insertNasabah.setCabang("surabaya");
//			
//			simpleDataSourceAccess.insertPesertaTraining(insertNasabah);
//			InsertNasabah getNasabah = simpleDataSourceAccess.getNasabah(5L);
//			System.out.println(getNasabah.getNoNasabah());
//			System.out.println(getNasabah.getNik());
//			System.out.println(getNasabah.getNamaLengkap());
//			System.out.println(getNasabah.getTempatLahir());
//			System.out.println(getNasabah.getTanggalLahir());
//			System.out.println(getNasabah.getCabang());
//			
//			UpdateNasabahRequest updateNasabah = new UpdateNasabahRequest();
//			updateNasabah.setNoNasabah(20L);
//			updateNasabah.setNamaLengkap("sfsdf");
//			updateNasabah.setNik(1900L);
//			updateNasabah.setCabang("jogasdja");
//			simpleDataSourceAccess.updateNasabah(updateNasabah);
//		}catch(Exception e){
//			System.err.println(e.getMessage());
//		}
	}
}
