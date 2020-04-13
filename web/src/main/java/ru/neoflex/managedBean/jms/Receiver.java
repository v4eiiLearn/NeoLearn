package ru.neoflex.managedBean.jms;

import payments.impl.PaymentSer;
import payments.schema.Payment;
import ru.neoflex.managedBean.BroadcastBean;
import ru.neoflex.service.CreditCalcServiceImpl;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.EJB;
import javax.ejb.MessageDriven;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import java.util.ArrayList;
import java.util.List;

@MessageDriven(name = "Receiver", activationConfig = {
        @ActivationConfigProperty(propertyName = "destinationLookup",
                propertyValue = "java:/jms/requestQueue"),
        @ActivationConfigProperty(propertyName = "destinationType",
                propertyValue = "javax.jms.Queue"),
        @ActivationConfigProperty(propertyName = "acknowledgeMode",
                propertyValue = "Auto-acknowledge"),
        @ActivationConfigProperty(propertyName = "messageSelector",
                propertyValue = "JMSCorrelationID = '08f7647d-8517-4fe6-80e8-0f7b2922bd66'")
})
@ManagedBean
@ApplicationScoped
public class Receiver implements MessageListener {

    @EJB
    private BroadcastBean broadcastBean;
    @EJB
    private CreditCalcServiceImpl creditCalcService;

    @Override
    public void onMessage(Message message) {
        try {
            ObjectMessage msg = null;
            if (message instanceof ObjectMessage) {
                msg = (ObjectMessage) message;
            }
            List<PaymentSer> paymentSerList = (List<PaymentSer>) msg.getObject();
            List<Payment> payments = new ArrayList<>();
            for (PaymentSer value : paymentSerList) {
                Payment p = new Payment();
                p.setDatePayment(DatatypeFactory.newInstance().newXMLGregorianCalendar(value.getDatePayment()));
                p.setMonthlyBalance(value.getMonthlyBalance());
                p.setOverpayment(value.getOverpayment());
                p.setPayment(value.getPayment());
                p.setPaymentForBody(value.getPaymentForBody());
                p.setPaymentForPercent(value.getPaymentForPercent());
                payments.add(p);
            }
            broadcastBean.setPaymentSchedule(creditCalcService
                    .issueCredit(broadcastBean.getClientName(), broadcastBean.getUser().getUserLogin(), payments));
        }
        catch (JMSException | DatatypeConfigurationException e) {
            e.printStackTrace();
        }
    }

}
