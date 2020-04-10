package ru.neoflex.service;

import payments.exceptions.CreditDataException;
import payments.impl.CreditCalcImpl;
import payments.schema.Credit;
import payments.schema.Payment;
import payments.schema.Payments;
import ru.neoflex.entity.CreditProduct;
import ru.neoflex.entity.PaymentSchedule;
import ru.neoflex.repository.PaymentScheduleRepository;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

@LocalBean
@Stateless
public class CreditCalcServiceImpl implements CreditCalcService {

    @EJB
    private PaymentScheduleRepository paymentScheduleRepository;

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
            e.printStackTrace();
        }
        return null;
    }

    public PaymentSchedule issueCredit(String clientName, String userLogin, List<Payment> paymentList) {
        PaymentSchedule paymentSchedule = new PaymentSchedule();
        paymentSchedule.setClientName(clientName);
        paymentSchedule.setUserLogin(userLogin);
        paymentSchedule.setDateGenerate(new Date());
        List<ru.neoflex.entity.Payments> payments = new ArrayList<>();
        for (Payment value : paymentList) {
            payments.add(new ru.neoflex.entity.Payments(value, paymentSchedule));
        }
        paymentSchedule.setPaymentsCollection(payments);
        paymentScheduleRepository.create(paymentSchedule);
        return paymentSchedule;
    }

}
