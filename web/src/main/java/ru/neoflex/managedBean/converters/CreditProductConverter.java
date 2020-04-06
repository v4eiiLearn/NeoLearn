package ru.neoflex.managedBean.converters;

import ru.neoflex.entity.CreditProduct;
import ru.neoflex.repository.CreditProductRepository;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;

//@FacesConverter(managed = true, value = "creditConverter", forClass = CreditProduct.class)
@ManagedBean
@RequestScoped
public class CreditProductConverter implements Converter {

    @EJB
    private CreditProductRepository creditProductRepository;

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        String substring = value.substring(0, value.indexOf(" "));
        return creditProductRepository.findByName(substring);
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        CreditProduct c = (CreditProduct) value;
        if (c != null)
            return c.getProductName() + " " + c.getPercent() + " " + c.getType();
        else
            return "";
    }
}
