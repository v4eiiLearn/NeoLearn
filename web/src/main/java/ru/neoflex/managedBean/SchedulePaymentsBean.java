package ru.neoflex.managedBean;

import lombok.Getter;
import lombok.Setter;
import ru.neoflex.entity.PaymentSchedule;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

@ManagedBean
@ViewScoped
public class SchedulePaymentsBean {

    @EJB
    private BroadcastBean broadcastBean;

    @Getter @Setter
    private PaymentSchedule paymentSchedule;


    @PostConstruct
    public void init() {
         paymentSchedule = broadcastBean.getPaymentSchedule();
    }

    public String returnToSchedules() {
        return "/jsf/SchedulesView?faces-redirect=true";
    }

}
