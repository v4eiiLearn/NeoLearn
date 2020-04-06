package ru.neoflex.managedBean;

import lombok.Getter;
import lombok.Setter;
import ru.neoflex.entity.PaymentSchedule;
import ru.neoflex.entity.User;
import ru.neoflex.enums.UserRole;
import ru.neoflex.repository.PaymentScheduleRepository;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ValueChangeEvent;
import java.util.List;

@ManagedBean
@ViewScoped
public class SchedulesViewBean {

    @EJB
    private PaymentScheduleRepository paymentScheduleRepository;
    @EJB
    private BroadcastBean broadcastBean;

    @Getter @Setter
    private List<PaymentSchedule> paymentSchedules;
    @Getter @Setter
    private User user;

    @PostConstruct
    public void init() {
        user = broadcastBean.getUser();
        if (user.getUserRole().equals(UserRole.SUPERVISOR))
            paymentSchedules = paymentScheduleRepository.findAll();
        else
            paymentSchedules = paymentScheduleRepository.findByUserLogin(user.getUserLogin());
    }

    public String toSchedule(PaymentSchedule paymentSchedule) {
        broadcastBean.setPaymentSchedule(paymentSchedule);
        return "/jsf/SchedulePaymentsView?faces-redirect=true";
    }

    public String toCreditCalc() {
        return "/jsf/CreditCalcView?faces-redirect=true";
    }

    public void filterByClientName(ValueChangeEvent event) {
        paymentSchedules = paymentScheduleRepository.findByClientName((String) event.getNewValue());
    }

}
