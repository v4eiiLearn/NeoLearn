package ru.neoflex.jmsreceiver;

import payments.impl.PaymentSer;

import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.jms.JMSContext;
import javax.jms.JMSException;
import javax.jms.ObjectMessage;
import javax.jms.Queue;
import java.util.ArrayList;
import java.util.List;

@Stateless
public class Sender {

    @Resource(mappedName = "java:/jms/requestQueue")
    private Queue queue;

    @Inject
    private JMSContext context;

    public void sendMessage(List<PaymentSer> paymentList, String correlationID) {
        ObjectMessage mapMessage = context.createObjectMessage();
        try {
            mapMessage.setJMSCorrelationID(correlationID);
            mapMessage.setObject((ArrayList<PaymentSer>) paymentList);
            context.createProducer().send(queue, mapMessage);
        }
        catch (JMSException e) {
            e.printStackTrace();
        }
    }

}
