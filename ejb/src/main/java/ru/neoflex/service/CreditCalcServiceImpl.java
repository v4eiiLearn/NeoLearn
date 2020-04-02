package ru.neoflex.service;

import payments.exceptions.CreditDataException;
import payments.impl.CreditCalcImpl;
import ru.neoflex.entity.CreditProduct;
import ru.neoflex.payments.schema.Credit;
import ru.neoflex.payments.schema.Payment;
import ru.neoflex.payments.schema.Payments;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

public class CreditCalcServiceImpl implements CreditCalcService {
    //TODO: в БД заделать creditProduct поле type
    @Override
    public List<Payment> calculateAndReturnPayments(CreditProduct creditProduct, BigDecimal price, Integer term, GregorianCalendar calendar) {
        try {
            Credit c = new Credit();
            c.setPrice(price);
            c.setTerm(term);
            c.setDateIssue(DatatypeFactory.newInstance().newXMLGregorianCalendar(calendar));
            //!!!!!
            c.setType(null);
            c.setPercent(creditProduct.getPercent());
            Payments calculate = CreditCalcImpl.calculate(c);
            return calculate.getPaymentList();
        }
        catch (DatatypeConfigurationException | IOException | CreditDataException e) {
            //TODO: накрутить логгер
            e.printStackTrace();
        }
        return null;
    }
}
