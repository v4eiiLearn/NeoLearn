package ru.neoflex.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "payments", schema = "public", catalog = "creditCalc")
@NamedQuery(name = "findAllPayments", query = "SELECT p FROM Payments p")
public class Payments {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_payment")
    @Getter @Setter
    private Integer idPayment;
    @Basic(optional = false)
    @Getter @Setter
    private float payment;
    @Basic(optional = false)
    @Column(name = "payment_body")
    @Getter @Setter
    private float paymentBody;
    @Basic(optional = false)
    @Column(name = "payment_percent")
    @Getter @Setter
    private float paymentPercent;
    @Basic(optional = false)
    @Column(name = "monthly_balance")
    @Getter @Setter
    private float monthlyBalance;
    @Basic(optional = false)
    @Getter @Setter
    private float overpayment;
    @JoinColumn(name = "id_schedule", referencedColumnName = "id_schedule")
    @ManyToOne(optional = false)
    @Getter @Setter
    private PaymentSchedule idSchedule;

    public Payments(float payment, float paymentBody, float paymentPercent, float monthlyBalance, float overpayment, PaymentSchedule paymentSchedule) {
        this.monthlyBalance = monthlyBalance;
        this.payment = payment;
        this.paymentBody = paymentBody;
        this.paymentPercent = paymentPercent;
        this.overpayment = overpayment;
        this.idSchedule = paymentSchedule;
    }

    public Payments() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Payments that = (Payments) o;

        if (idPayment != that.idPayment) return false;
        if (Float.compare(that.payment, payment) != 0) return false;
        if (Float.compare(that.paymentBody, paymentBody) != 0) return false;
        if (Float.compare(that.paymentPercent, paymentPercent) != 0) return false;
        if (Float.compare(that.monthlyBalance, monthlyBalance) != 0) return false;
        if (Float.compare(that.overpayment, overpayment) != 0) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = idPayment;
        result = 31 * result + (payment != +0.0f ? Float.floatToIntBits(payment) : 0);
        result = 31 * result + (paymentBody != +0.0f ? Float.floatToIntBits(paymentBody) : 0);
        result = 31 * result + (paymentPercent != +0.0f ? Float.floatToIntBits(paymentPercent) : 0);
        result = 31 * result + (monthlyBalance != +0.0f ? Float.floatToIntBits(monthlyBalance) : 0);
        result = 31 * result + (overpayment != +0.0f ? Float.floatToIntBits(overpayment) : 0);
        return result;
    }
}
