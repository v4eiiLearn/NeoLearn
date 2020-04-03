package ru.neoflex.managedBean;

import lombok.Getter;
import lombok.Setter;
import ru.neoflex.entity.CreditProduct;
import ru.neoflex.entity.User;

import javax.ejb.LocalBean;
import javax.ejb.Singleton;
import java.math.BigDecimal;

@LocalBean
@Singleton(name = "broadcast")
public class BroadcastBean {

    @Getter @Setter
    private User user;
    @Getter @Setter
    private CreditProduct creditProduct;
    @Getter @Setter
    private String clientName;
    @Getter @Setter
    private BigDecimal price;
    @Getter @Setter
    private Integer term;

}
