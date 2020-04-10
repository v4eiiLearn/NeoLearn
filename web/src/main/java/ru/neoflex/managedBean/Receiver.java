package ru.neoflex.managedBean;

import lombok.Getter;
import lombok.Setter;
import payments.impl.PaymentSer;
import payments.schema.Payment;
import ru.neoflex.service.CreditCalcServiceImpl;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.EJB;
import javax.ejb.MessageDriven;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.inject.Named;
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
                propertyValue = "Auto-acknowledge")
})
@ManagedBean(name = "beand")
@ApplicationScoped
public class Receiver implements MessageListener {

    @EJB
    private BroadcastBean broadcastBean;
    @EJB
    private CreditCalcServiceImpl creditCalcService;

    @Getter @Setter
    private boolean calcReady = false;

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
            calcReady = true;
        }
        catch (JMSException | DatatypeConfigurationException e) {
            e.printStackTrace();
        }
    }

    public String toSchedule() {
        return "/jsf/SchedulePaymentsView.xhtml?faces-redirect=true";
    }

}
