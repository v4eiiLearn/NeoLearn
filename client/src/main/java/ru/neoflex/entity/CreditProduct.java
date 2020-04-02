package ru.neoflex.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "credit_product", schema = "public", catalog = "creditCalc")
@NamedQueries(
        {
        @NamedQuery(name = "findAllCreditProduct", query = "SELECT c FROM CreditProduct c"),
        @NamedQuery(name = "findProductBetweenPriceAndTerm",
                query = "SELECT c FROM CreditProduct c WHERE (:price BETWEEN minPrice AND maxPrice) AND (:term BETWEEN minTerm AND maxPrice)")
})

public class CreditProduct {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_product")
    @Getter @Setter
    private Integer idProduct;
    @Basic(optional = false)
    @Column(name = "product_name")
    @Getter @Setter
    private String productName;
    @Basic(optional = false)
    @Column(name = "min_term")
    @Getter @Setter
    private Integer minTerm;
    @Basic(optional = false)
    @Column(name = "max_term")
    @Getter @Setter
    private Integer maxTerm;
    @Basic(optional = false)
    @Column(name = "min_price")
    @Getter @Setter
    private BigDecimal minPrice;
    @Basic(optional = false)
    @Column(name = "max_price")
    @Getter @Setter
    private BigDecimal maxPrice;
    @Basic(optional = false)
    @Getter @Setter
    private float percent;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CreditProduct that = (CreditProduct) o;

        if (idProduct != that.idProduct) return false;
        if (minTerm != that.minTerm) return false;
        if (maxTerm != that.maxTerm) return false;
        if (Float.compare(that.percent, percent) != 0) return false;
        if (productName != null ? !productName.equals(that.productName) : that.productName != null) return false;
        if (minPrice != null ? !minPrice.equals(that.minPrice) : that.minPrice != null) return false;
        if (maxPrice != null ? !maxPrice.equals(that.maxPrice) : that.maxPrice != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = idProduct;
        result = 31 * result + (productName != null ? productName.hashCode() : 0);
        result = 31 * result + minTerm;
        result = 31 * result + maxTerm;
        result = 31 * result + (minPrice != null ? minPrice.hashCode() : 0);
        result = 31 * result + (maxPrice != null ? maxPrice.hashCode() : 0);
        result = 31 * result + (percent != +0.0f ? Float.floatToIntBits(percent) : 0);
        return result;
    }
}
