package ru.neoflex.service;

import ru.neoflex.entity.CreditProduct;
import ru.neoflex.repository.CreditProductRepository;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.inject.Inject;
import java.math.BigDecimal;
import java.util.List;

@LocalBean
@Stateless
public class CreditProductServiceImpl implements CreditProductService {

    @Inject
    private CreditProductRepository creditProductRepository;

    @Override
    public List<CreditProduct> findSuitableCreditProduct(BigDecimal price, Integer term) {
        return creditProductRepository.findBetweenPriceAndTerm(price, term);
    }
}
