package ru.neoflex.managedBean;

import lombok.Getter;
import lombok.Setter;
import ru.neoflex.entity.CreditProduct;
import ru.neoflex.entity.User;
import ru.neoflex.payments.schema.Payment;
import ru.neoflex.service.CreditCalcServiceImpl;
import ru.neoflex.service.CreditProductServiceImpl;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ValueChangeEvent;
import javax.inject.Inject;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

@ManagedBean(name = "ccViewBean")
@ViewScoped
public class CreditCalcBean {

    @EJB
    private CreditProductServiceImpl creditProductService;
    @EJB
    private CreditCalcServiceImpl creditCalcService;
    @EJB
    private BroadcastBean broadcastBean;

    @Getter @Setter
    private Long price;
    @Getter @Setter
    private Integer term;
    @Getter @Setter
    private List<CreditProduct> suitableCreditProducts;
    @Getter @Setter
    private CreditProduct selectedCreditProduct;
    @Getter @Setter
    private User user;
    @Getter @Setter
    private boolean showIssueFields;
    @Getter @Setter
    private String clientName;


    @Getter @Setter
    private List<Payment> paymentList;

    @PostConstruct
    public void init() {
//        logPageBean.findUser();
        user = broadcastBean.getUser();
        showIssueFields = false;
    }

    public void checkProducts() {
        if (suitableCreditProducts != null)
            suitableCreditProducts.clear();
        if (price != null && term != null)
            suitableCreditProducts = creditProductService.findSuitableCreditProduct(price, term);
    }

    public void priceChangeListener(ValueChangeEvent event) {
        price = (Long) event.getNewValue();
        checkProducts();
        showIssueFields = suitableCreditProducts.size() > 0;
    }

    public void termChangeListener(ValueChangeEvent event) {
        term = (Integer) event.getNewValue();
        checkProducts();
        showIssueFields = suitableCreditProducts.size() > 0;
    }

    public String toIssueCredit() {
        broadcastBean.setPaymentSchedule(creditCalcService.issueCredit(clientName, user.getUserLogin(),
                creditCalcService.calculateAndReturnPayments(selectedCreditProduct, new BigDecimal(price), term,
                    new GregorianCalendar(Calendar.getInstance().getTimeZone()))));
        return "/jsf/SchedulePaymentsView?faces-redirect=true";
    }

    public String toSchedules() {
        return "/jsf/SchedulesView?faces-redirect=true";
    }

}
