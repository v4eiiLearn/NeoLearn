package ru.neoflex.managedBean;

import lombok.Getter;
import lombok.Setter;
import ru.neoflex.entity.CreditProduct;
import ru.neoflex.entity.User;
import ru.neoflex.service.CreditProductServiceImpl;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ValueChangeEvent;
import java.math.BigDecimal;
import java.util.List;

@ManagedBean(name = "ccViewBean")
@ViewScoped
public class CreditCalcBean {

    @EJB
    private CreditProductServiceImpl creditProductService;
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

    @PostConstruct
    public void init() {
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

    //TODO: не переходит, до входа в метод кидает
    // WARNING: FacesMessage(s) have been enqueued, but may not have been displayed.
    // sourceId=j_idt10:products[severity=(ERROR 2), summary=(Conversion Error setting value 'Продукт1 9.4 DIFF' for 'null Converter'. ), detail=(Conversion Error setting value 'Продукт1 9.4 DIFF' for 'null Converter'. )]
    // Как идея, расчитывать здесь, передавая лист с расчетами.
    public String toIssueCredit() {
        broadcastBean.setClientName(clientName);
        broadcastBean.setCreditProduct(selectedCreditProduct);
        broadcastBean.setPrice(new BigDecimal(price));
        broadcastBean.setTerm(term);
        return "/jsf/SchedulePaymentsView?faces-redirect=true";
    }


}
