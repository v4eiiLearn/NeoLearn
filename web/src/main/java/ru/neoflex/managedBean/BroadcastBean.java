package ru.neoflex.managedBean;

import lombok.Getter;
import lombok.Setter;
import ru.neoflex.entity.CreditProduct;
import ru.neoflex.entity.PaymentSchedule;
import ru.neoflex.entity.User;

import javax.ejb.LocalBean;
import javax.ejb.Singleton;

@LocalBean
@Singleton(name = "broadcast")
public class BroadcastBean {

    @Getter @Setter
    private User user;
    @Getter @Setter
    private CreditProduct creditProduct;
    @Getter @Setter
    private PaymentSchedule paymentSchedule;
    @Getter @Setter
    private String login;
    @Getter @Setter
    private String psw;

}
