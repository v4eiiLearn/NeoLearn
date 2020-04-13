package ru.neoflex.managedBean;

import lombok.Getter;
import lombok.Setter;
import payments.schema.Credit;
import payments.schema.Payment;
import ru.neoflex.entity.CreditProduct;
import ru.neoflex.entity.User;
import ru.neoflex.managedBean.jms.Sender;
import ru.neoflex.repository.UserRepository;
import ru.neoflex.service.CreditProductServiceImpl;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import java.math.BigDecimal;
import java.util.*;

@ManagedBean(name = "ccViewBean")
@ViewScoped
public class CreditCalcBean {

    @EJB
    private CreditProductServiceImpl creditProductService;
    @EJB
    private UserRepository userRepository;
    @EJB
    private BroadcastBean broadcastBean;
    @EJB
    private Sender sender;

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
    @Getter @Setter
    private String correlationID;

    @PostConstruct
    public void init() {
        user = userRepository.findByLogin(FacesContext.getCurrentInstance().getExternalContext().getRemoteUser());
        broadcastBean.setUser(user);
        showIssueFields = false;
    }

    public void checkProducts() {
        if (suitableCreditProducts != null)
            suitableCreditProducts.clear();
        if (price != null && term != null)
            suitableCreditProducts = creditProductService.findSuitableCreditProduct(price, term);
    }

    public void logOut() {
        try {
            ((HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest()).logout();
        }
        catch (ServletException e) {
            e.printStackTrace();
        }
    }

    public void priceChangeListener(ValueChangeEvent event) {
        price = (Long) event.getNewValue();
        checkProducts();
        if (suitableCreditProducts != null)
            showIssueFields = suitableCreditProducts.size() > 0;
    }

    public void termChangeListener(ValueChangeEvent event) {
        term = (Integer) event.getNewValue();
        checkProducts();
        if (suitableCreditProducts != null)
            showIssueFields = suitableCreditProducts.size() > 0;
    }

    public String toIssueCredit() {
        try {
            Credit c = new Credit();
            c.setPercent(selectedCreditProduct.getPercent());
            c.setType(selectedCreditProduct.getType());
            c.setDateIssue(DatatypeFactory.newInstance().newXMLGregorianCalendar(
                    new GregorianCalendar(Calendar.getInstance().getTimeZone())));
            c.setTerm(term);
            c.setPrice(new BigDecimal(price));
            broadcastBean.setClientName(clientName);
            correlationID = UUID.randomUUID().toString();
            sender.sendMessage(c, correlationID);
            return "/jsf/WaitPageView?faces-redirect=true";
        }
        catch (DatatypeConfigurationException e) {
            e.printStackTrace();
        }
        return "/jsf/template/content?faces-redirect=true";
    }

    public String toSchedules() {
        return "/jsf/SchedulesView?faces-redirect=true";
    }

}
