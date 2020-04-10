package ru.neoflex.managedBean;

import lombok.Getter;
import lombok.Setter;
import payments.impl.PaymentSer;
import payments.schema.Payment;
import ru.neoflex.entity.PaymentSchedule;
import ru.neoflex.service.CreditCalcServiceImpl;

import javax.annotation.PostConstruct;
import javax.ejb.ActivationConfigProperty;
import javax.ejb.EJB;
import javax.ejb.MessageDriven;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;
import javax.servlet.http.HttpServletRequest;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@ManagedBean
@ViewScoped
public class SchedulePaymentsBean {

    @EJB
    private BroadcastBean broadcastBean;

    @Getter @Setter
    private PaymentSchedule paymentSchedule;


    @PostConstruct
    public void init() {
        if (broadcastBean.getPaymentSchedule() != null)
            paymentSchedule = broadcastBean.getPaymentSchedule();
    }

    public String returnToSchedules() {
        return "/jsf/SchedulesView?faces-redirect=true";
    }

}
