package ru.neoflex.managedBean;

import lombok.Getter;
import lombok.Setter;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import java.util.Date;

@ManagedBean
@RequestScoped
public class DateTimeBean {
    @Getter @Setter
    private Date currDate = new Date();
}
