package sk.qbsw.core.pay.base.util;

import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;

import org.junit.Before;
import org.junit.Test;

import sk.qbsw.core.pay.base.Currency;
import sk.qbsw.core.pay.base.PaymentActions;
import sk.qbsw.core.pay.base.PaymentImpl;
import sk.qbsw.core.pay.base.PaymentPersistence;
import sk.qbsw.core.pay.base.PaymentProcessor;
import sk.qbsw.core.pay.base.PaymentRealization;
import sk.qbsw.core.pay.dummy.DummyInitParams;
import sk.qbsw.core.pay.dummy.DummyPaymentProcessorFactory;
import sk.qbsw.core.pay.dummy.DummyResponse;

public class PaymentProcessorTest {

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testCreate() {

		DummyInitParams initparams = new DummyInitParams();
		DummyPaymentProcessorFactory factory = new DummyPaymentProcessorFactory(initparams);
		PaymentProcessor processor = factory.createPaymentProcessor();

		PaymentImpl payment = new PaymentImpl();
		payment.setAmount(new BigDecimal("10"));
		payment.setCurrency(Currency.EUR);
		payment.setRemittanceInformation("ref123");
		payment.setKs(300L);
		payment.setVs(1234567890L);
		payment.setSs(1234567890L);

		//realizacia sa ulozi do DB. 
		PaymentRealization realization = processor.createPayment(payment);

		String id = realization.getPaymentId();
		String url = realization.getUrlToCall();
		assertEquals("12345678901234567890300", id);
		assertEquals("\\", url);

	}

	@Test
	public void testConfirm() {

		DummyInitParams initparams = new DummyInitParams();
		DummyPaymentProcessorFactory factory = new DummyPaymentProcessorFactory(initparams);
		PaymentProcessor processor = factory.createPaymentProcessor();
		PaymentActions actions = new PaymentActions() {

			@Override
			public void handleUncertain(PaymentRealization payment) {
			}

			@Override
			public void handleSuccess(PaymentRealization payment) {
				assertEquals("12345678901234567890300", payment.getPaymentId());

			}

			@Override
			public void handleFraud(PaymentRealization paymentRealization) {

			}

			@Override
			public void handleCancel() {

			}

			@Override
			public void handleCancel(PaymentRealization payment) {

			}
		};
		processor.setActions(actions);
		PaymentPersistence persistence = new PaymentPersistence() {

			@Override
			public void update(PaymentRealization payment) {
				assertEquals("12345678901234567890300", payment.getPaymentId());
			}

			@Override
			public PaymentRealization getPaymentById(String id) {
				PaymentRealization realization = new PaymentRealization();
				realization.setPaymentId(id);
				realization.setGetCall(true);
				realization.setUrlToCall("\\");
				return realization;
			}
		};
		processor.setPersistence(persistence);

		DummyResponse resp = new DummyResponse();
		resp.setId("12345678901234567890300");
		PaymentRealization realization = processor.handleBankPaymentResponse(resp);

		String id = realization.getPaymentId();
		String url = realization.getUrlToCall();
		assertEquals("12345678901234567890300", id);
		assertEquals("\\", url);
		assertEquals("Dummy processed", realization.getBankResponse());

	}

}