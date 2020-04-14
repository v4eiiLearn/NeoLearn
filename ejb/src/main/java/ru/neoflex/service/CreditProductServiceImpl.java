package ru.neoflex.service;

import ru.neoflex.entity.CreditProduct;
import ru.neoflex.repository.CreditProductRepository;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateful;
import java.util.List;

@LocalBean
@Stateful
public class CreditProductServiceImpl implements CreditProductService {

    @EJB
    private CreditProductRepository creditProductRepository;

    @Override
    public List<CreditProduct> findSuitableCreditProduct(Long price, Integer term) {
        return creditProductRepository.findBetweenPriceAndTerm(price, term);
    }
}
