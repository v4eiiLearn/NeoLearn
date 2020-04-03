package ru.neoflex.service;

import payments.exceptions.CreditDataException;
import payments.impl.CreditCalcImpl;
import ru.neoflex.entity.CreditProduct;
import ru.neoflex.payments.schema.Credit;
import ru.neoflex.payments.schema.Payment;
import ru.neoflex.payments.schema.Payments;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.GregorianCalendar;
import java.util.List;

@LocalBean
@Stateless
public class CreditCalcServiceImpl implements CreditCalcService {
    @Override
    public List<Payment> calculateAndReturnPayments(CreditProduct creditProduct, BigDecimal price, Integer term, GregorianCalendar calendar) {
        try {
            Credit c = new Credit();
            c.setPrice(price);
            c.setTerm(term);
            c.setDateIssue(DatatypeFactory.newInstance().newXMLGregorianCalendar(calendar));
            c.setType(creditProduct.getType());
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
