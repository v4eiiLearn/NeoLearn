package ru.neoflex.service;

import ru.neoflex.entity.CreditProduct;

import javax.ejb.Local;
import java.math.BigDecimal;
import java.util.List;

@Local
public interface CreditProductService {
    List<CreditProduct> findSuitableCreditProduct(BigDecimal price, Integer term);
}
