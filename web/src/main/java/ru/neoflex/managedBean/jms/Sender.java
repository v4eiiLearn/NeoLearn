package ru.neoflex.managedBean.jms;

import payments.impl.CreditSer;
import payments.schema.Credit;

import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.faces.bean.ApplicationScoped;
import javax.inject.Inject;
import javax.jms.JMSContext;
import javax.jms.JMSException;
import javax.jms.ObjectMessage;
import javax.jms.Queue;

@ApplicationScoped
@Stateless
public class Sender {

    @Resource(mappedName = "java:/jms/responceQueue")
    private Queue queue;

    @Inject
    private JMSContext context;

    public void sendMessage(Credit credit, String correlationID) {
        ObjectMessage objectMessage = context.createObjectMessage();
        CreditSer cs = new CreditSer(credit);
        try {
            objectMessage.setJMSCorrelationID(correlationID);
            objectMessage.setObjectProperty("corID", correlationID);
            objectMessage.setObject(cs);
            context.createProducer().send(queue, objectMessage);
        }
        catch (JMSException e) {
            e.printStackTrace();
        }
    }

}