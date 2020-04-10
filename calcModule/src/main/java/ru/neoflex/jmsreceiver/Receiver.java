package ru.neoflex.jmsreceiver;

import payments.exceptions.CreditDataException;
import payments.impl.CreditCalcImpl;
import payments.impl.CreditSer;
import payments.impl.PaymentSer;
import payments.schema.Credit;
import payments.schema.CreditType;
import payments.schema.Payment;
import payments.schema.Payments;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.EJB;
import javax.ejb.MessageDriven;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@MessageDriven(name = "Receiver", activationConfig = {
        @ActivationConfigProperty(propertyName = "destinationLookup",
                propertyValue = "java:/jms/responceQueue"),
        @ActivationConfigProperty(propertyName = "destinationType",
                propertyValue = "javax.jms.Queue"),
        @ActivationConfigProperty(propertyName = "acknowledgeMode",
                propertyValue = "Auto-acknowledge")
})
public class Receiver implements MessageListener {

    @EJB
    private Sender sender;

    @Override
    public void onMessage(Message message) {
        try {
            ObjectMessage msg = null;
            if (message instanceof ObjectMessage) {
                msg = (ObjectMessage) message;
            }
            CreditSer cs = (CreditSer) msg.getObject();
            Credit c = new Credit();
            c.setPrice(cs.getPrice());
            c.setPercent(cs.getPercent());
            c.setTerm(cs.getTerm());
            c.setDateIssue(DatatypeFactory.newInstance().newXMLGregorianCalendar(cs.getDateIssue()));
            c.setType(CreditType.fromValue(cs.getCreditType()));
            Payments calculate = CreditCalcImpl.calculate(c);
            List<Payment> paymentList = calculate.getPaymentList();
            List<PaymentSer> finalList = new ArrayList<>();
            for (Payment value : paymentList) {
                finalList.add(new PaymentSer(value));
            }
            sender.sendMessage(finalList);
        }
        catch (JMSException | IOException | CreditDataException | DatatypeConfigurationException e) {
            e.printStackTrace();
        }

    }
}