package ru.neoflex.managedBean;

import lombok.Getter;
import lombok.Setter;
import ru.neoflex.entity.CreditProduct;
import ru.neoflex.payments.schema.Payment;
import ru.neoflex.service.CreditCalcServiceImpl;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

@ManagedBean
@ViewScoped
public class SchedulePaymentsBean {

    @EJB
    private BroadcastBean broadcastBean;
    @EJB
    private CreditCalcServiceImpl creditCalcService;

    @Getter @Setter
    private String clientName;
    @Getter @Setter
    private CreditProduct creditProduct;
    @Getter @Setter
    private Integer term;
    @Getter @Setter
    private BigDecimal price;
    @Getter @Setter
    private List<Payment> paymentList;

    @PostConstruct
    public void init() {
        clientName = broadcastBean.getClientName();
        creditProduct = broadcastBean.getCreditProduct();
        term = broadcastBean.getTerm();
        price = broadcastBean.getPrice();
        paymentList = creditCalcService.calculateAndReturnPayments(creditProduct, price, term,
                new GregorianCalendar(Calendar.getInstance().getTimeZone()));
    }

}
