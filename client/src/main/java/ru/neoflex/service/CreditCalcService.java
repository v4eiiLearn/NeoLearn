package ru.neoflex.service;

import payments.schema.Payment;
import ru.neoflex.entity.CreditProduct;

import javax.ejb.Local;
import java.math.BigDecimal;
import java.util.GregorianCalendar;
import java.util.List;

@Local
public interface CreditCalcService {
    List<Payment> calculateAndReturnPayments(CreditProduct creditProduct, BigDecimal price, Integer term, GregorianCalendar calendar);
}
