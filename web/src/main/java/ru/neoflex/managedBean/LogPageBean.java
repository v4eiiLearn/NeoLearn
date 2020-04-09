package ru.neoflex.managedBean;

import lombok.Getter;
import lombok.Setter;
import ru.neoflex.entity.User;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

@ManagedBean(name = "logPage")
@ViewScoped
public class LogPageBean {
    @Getter @Setter
    private String login;
    @Getter @Setter
    private String psw;
    @Getter @Setter
    private User user;
    @Getter @Setter
    private String errorMsg;
}
