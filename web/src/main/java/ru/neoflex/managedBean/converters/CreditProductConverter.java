//package ru.neoflex.managedBean.converters;
//
//import ru.neoflex.entity.CreditProduct;
//import ru.neoflex.repository.CreditProductRepository;
//
//import javax.ejb.EJB;
//import javax.faces.component.UIComponent;
//import javax.faces.context.FacesContext;
//import javax.faces.convert.Converter;
//import javax.faces.convert.FacesConverter;
//
//@FacesConverter("creditConv")
//public class CreditProductConverter implements Converter {
//
//    @EJB
//    private CreditProductRepository creditProductRepository;
//
//    @Override
//    public Object getAsObject(FacesContext context, UIComponent component, String value) {
//        String substring = value.substring(0, value.indexOf(" "));
//        System.out.println(substring);
//        return creditProductRepository.findByName(substring);
//    }
//
//    @Override
//    public String getAsString(FacesContext context, UIComponent component, Object value) {
//        CreditProduct c = (CreditProduct) value;
//        if (c != null)
//            return c.getProductName() + " " + c.getPercent() + " " + c.getType();
//        else
//            return "";
//    }
//}
