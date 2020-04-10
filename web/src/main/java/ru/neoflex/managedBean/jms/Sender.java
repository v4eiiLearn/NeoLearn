package ru.neoflex.managedBean.jms;

import payments.impl.CreditSer;
import payments.schema.Credit;

import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.faces.bean.ApplicationScoped;
import javax.inject.Inject;
import javax.jms.*;

@ApplicationScoped
@Stateless
public class Sender {

    @Resource(mappedName = "java:/jms/responceQueue")
    private Queue queue;

    @Inject
    private JMSContext context;

    public void sendMessage(Credit credit) {
        ObjectMessage objectMessage = context.createObjectMessage();
        CreditSer cs = new CreditSer(credit);
        try {
            objectMessage.setObject(cs);
            context.createProducer().send(queue, objectMessage);
        }
        catch (JMSException e) {
            e.printStackTrace();
        }
    }

}