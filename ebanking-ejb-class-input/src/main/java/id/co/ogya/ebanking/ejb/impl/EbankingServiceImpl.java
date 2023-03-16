package id.co.ogya.ebanking.ejb.impl;

import java.sql.Connection;
import java.util.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

import javax.ejb.Stateless;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.naming.NamingException;

import id.co.ogya.ebanking.ejb.EbankingService;
import id.co.ogya.ebanking.ejb.request.InquiryBalanceRequest;
import id.co.ogya.ebanking.ejb.request.TransferRequest;
import id.co.ogya.ebanking.ejb.response.InquiryBalanceResponse;
import id.co.ogya.ebanking.ejb.response.TransferResponse;
import id.co.ogya.ebanking.ejb.util.DataSourceServiceFactory;
import id.co.ogya.ebanking.ejb.util.ServiceFactory;

@Stateless(mappedName = "EbankingServiceImpl1", name = "EbankingServiceImpl1")
public class EbankingServiceImpl implements EbankingService {
//
	public boolean isConnected() {
		boolean isAbleToConnect = false;

		DataSourceServiceFactory dataSourceServiceFactory = new DataSourceServiceFactory();
		try {
			Connection connection = dataSourceServiceFactory.getConnection();
			if (!connection.isClosed()) {
				isAbleToConnect = true;
			}
			connection.close();
		} catch (SQLException e) {
			System.err.println("SQLException : " + e.getMessage());
		} catch (Exception e) {
			System.err.println("Exception : " + e.getMessage());
		}
		return isAbleToConnect;
	}

	public InquiryBalanceResponse cekSaldo(InquiryBalanceRequest inquiryBalanceRequest) {
	InquiryBalanceResponse response = new InquiryBalanceResponse();
		DataSourceServiceFactory dataSourceServiceFactory = new DataSourceServiceFactory();
		try {
			Connection conn = dataSourceServiceFactory.getConnection();
			String sqlSelectQuery = "SELECT ACCOUNT_BALANCED FROM TBL_NASABAH WHERE ACCOUNT_NO = ?";
			PreparedStatement preparedStatement = conn.prepareStatement(sqlSelectQuery);
			preparedStatement.setLong(1, inquiryBalanceRequest.getAccountNo());
			ResultSet resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				response.setAccountBalance(resultSet.getLong("ACCOUNT_BALANCED"));
			}
			if (response.getAccountBalance().equals(null)) {
				response.setErrorCode(null);
				response.setErrorMessage("No rekening tidak ditemukan");
			} else {
				response.setErrorCode(null);
				response.setErrorMessage(null);
			}
			preparedStatement.close();
			dataSourceServiceFactory.closeConnection();
		} catch (Exception e) {
			response.setErrorCode(null);
			response.setErrorMessage("No rekening tidak ditemukan");
			return response;
		}
		return response;
	}

	public TransferResponse kirim(TransferRequest transferRequest) {
		TransferResponse response = new TransferResponse();
		try {
			if (validateNo(transferRequest.getAccountFrom())) {
				response.setErrorMessage("no pengirim tidak ditemukan");
			} else if (validateNo(transferRequest.getAccountTo())) {
				response.setErrorMessage("no penerima tidak ditemukan");
			} else {
				if (validateSaldo(transferRequest.getAccountFrom()) < transferRequest.getAmount()) {
					response.setErrorMessage("saldo anda tidak cukup");
				} else {
					System.out.println("kelar validasi");
					String reference = UUID.randomUUID().toString().replace("-", "");
					updateSaldoPengirim(transferRequest.getAmount(), transferRequest.getAccountFrom());
					updateSaldoPenerima(transferRequest.getAmount(), transferRequest.getAccountTo());
					executeQuery(transferRequest, reference);
					sendMessage(transferRequest);
					response.setErrorMessage("Transfer Berhasil");
					response.setReferenceNumber(reference);
				}
			}
		} catch (Exception e) {
			System.err.println("Exception : " + e.getMessage());
		}
		return response;
	}
	
//	public TransferResponse transferRtgs(TransferRequest transferRequest) {
//		TransferResponse response = new TransferResponse();
//		try {
//			if (validateNo(transferRequest.getAccountFrom())) {
//				response.setErrorMessage("no pengirim tidak ditemukan");
//			} else if (validateNo(transferRequest.getAccountTo())) {
//				response.setErrorMessage("no penerima tidak ditemukan");
//			} else {
//				if (validateSaldo(transferRequest.getAccountFrom()) < transferRequest.getAmount()) {
//					response.setErrorMessage("saldo anda tidak cukup");
//				} else {
//					
//					String reference = UUID.randomUUID().toString().replace("-", "");
//					updateSaldoPengirim(transferRequest.getAmount(), transferRequest.getAccountFrom());
//					updateSaldoPenerima(transferRequest.getAmount(), transferRequest.getAccountTo());
//					executeQuery(transferRequest, reference);
//					sendMessage(transferRequest);
//					response.setErrorMessage("Transfer Berhasil");
//					response.setReferenceNumber(reference);
//				}
//			}
//		} catch (Exception e) {
//			System.err.println("Exception : " + e.getMessage());
//		}
//		return response;
//	}

	private void executeQuery(TransferRequest transferRequest, String reference) {
		DataSourceServiceFactory dataSourceServiceFactory = new DataSourceServiceFactory();
		try {
			Connection conn = dataSourceServiceFactory.getConnection();
			String sqlSelectQuery = "INSERT INTO TBL_TRANSFER_LOG values(?,?,?,?,SYSDATE,?)";
			PreparedStatement preparedStatement = conn.prepareStatement(sqlSelectQuery);
			preparedStatement.setString(1, reference);
			preparedStatement.setLong(2, transferRequest.getAccountFrom());
			preparedStatement.setLong(3, transferRequest.getAccountTo());
			preparedStatement.setLong(4, transferRequest.getAmount());
			preparedStatement.setString(5, transferRequest.getNotes());
			preparedStatement.executeQuery();
			preparedStatement.close();
			dataSourceServiceFactory.closeConnection();
		} catch (Exception e) {
			System.err.println("Exception : " + e.getMessage());
		}
	}

	private boolean validateNo(Long no) {
		boolean valid = true;
		DataSourceServiceFactory dataSourceServiceFactory = new DataSourceServiceFactory();
		try {
			Connection conn = dataSourceServiceFactory.getConnection();
			String sqlSelectQuery = "SELECT * FROM TBL_NASABAH WHERE ACCOUNT_NO = ?";
			PreparedStatement preparedStatement = conn.prepareStatement(sqlSelectQuery);
			preparedStatement.setLong(1, no);
			ResultSet resultSet = preparedStatement.executeQuery();
			if (resultSet.next()) {
				valid = false;
			}
			preparedStatement.close();
			dataSourceServiceFactory.closeConnection();
		} catch (Exception e) {
			System.err.println("Exception : " + e.getMessage());
		}
		return valid;
	}

	private Long validateSaldo(Long no) {
		Long saldo = null;
		DataSourceServiceFactory dataSourceServiceFactory = new DataSourceServiceFactory();
		try {
			Connection conn = dataSourceServiceFactory.getConnection();
			String sqlSelectQuery = "SELECT ACCOUNT_BALANCED FROM TBL_NASABAH WHERE ACCOUNT_NO = ?";
			PreparedStatement preparedStatement = conn.prepareStatement(sqlSelectQuery);
			preparedStatement.setLong(1, no);
			ResultSet resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				saldo = resultSet.getLong("ACCOUNT_BALANCED");
			}
			preparedStatement.close();
			dataSourceServiceFactory.closeConnection();
		} catch (Exception e) {
			System.err.println("Exception : " + e.getMessage());
		}
		return saldo;
	}

	private void updateSaldoPengirim(Long jumlah, Long no) {
		DataSourceServiceFactory dataSourceServiceFactory = new DataSourceServiceFactory();
		try {
			Connection conn = dataSourceServiceFactory.getConnection();
			String sqlSelectQuery = "UPDATE TBL_NASABAH SET ACCOUNT_BALANCED = ACCOUNT_BALANCED - ? WHERE ACCOUNT_NO = ?";
			PreparedStatement preparedStatement = conn.prepareStatement(sqlSelectQuery);
			preparedStatement.setLong(1, jumlah);
			preparedStatement.setLong(2, no);
			preparedStatement.executeQuery();
			preparedStatement.close();
			dataSourceServiceFactory.closeConnection();
		} catch (Exception e) {
			System.err.println("Exception : " + e.getMessage());
		}
	}

	private void updateSaldoPenerima(Long jumlah, Long no) {
		DataSourceServiceFactory dataSourceServiceFactory = new DataSourceServiceFactory();
		try {
			Connection conn = dataSourceServiceFactory.getConnection();
			String sqlSelectQuery = "UPDATE TBL_NASABAH SET ACCOUNT_BALANCED = ACCOUNT_BALANCED + ? WHERE ACCOUNT_NO = ?";
			PreparedStatement preparedStatement = conn.prepareStatement(sqlSelectQuery);
			preparedStatement.setLong(1, jumlah);
			preparedStatement.setLong(2, no);
			preparedStatement.executeQuery();
			preparedStatement.close();
			dataSourceServiceFactory.closeConnection();
		} catch (Exception e) {
			System.err.println("Exception : " + e.getMessage());
		}
	}

	private void sendMessage(TransferRequest transferRequest) throws SQLException {
		javax.jms.Connection connection = null;
		Session session = null;
		MessageProducer sender = null;

		String connectionFactoryName = "jms.TrainingCF";
		String TopicName = "jms.NotificationListener";
		ServiceFactory serviceFactory = new ServiceFactory();
		try {
			ConnectionFactory cf = (ConnectionFactory) serviceFactory.getService(connectionFactoryName);
			connection = (javax.jms.Connection) cf.createConnection();
			session = ((javax.jms.Connection) connection).createSession(false, Session.AUTO_ACKNOWLEDGE);

			Destination destination = (Destination) serviceFactory.getService(TopicName);
			sender = session.createProducer(destination);

			((javax.jms.Connection) connection).start();

			Date date = new Date();
			String dates = date.toString();
			TextMessage textMessage = session.createTextMessage();
			String data = transferRequest.toString() + dates;
			textMessage.setText(data);

			sender.send(textMessage);

			connection.close();

		} catch (NamingException n) {
			n.printStackTrace();
		} catch (JMSException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (sender != null) {
					sender.close();
				}
				if (session != null) {
					session.close();
				}
				if (connection != null) {
					connection.close();
				}
			} catch (JMSException e) {
				e.printStackTrace();
			}
		}
	}
}
