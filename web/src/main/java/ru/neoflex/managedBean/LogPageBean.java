package ru.neoflex.managedBean;

import lombok.Getter;
import lombok.Setter;
import ru.neoflex.entity.User;
import ru.neoflex.repository.UserRepository;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ValueChangeEvent;
import javax.inject.Inject;

@ManagedBean(name = "logPage")
@ViewScoped
public class LogPageBean {

    @EJB
    private UserRepository userRepository;
    @EJB
    private BroadcastBean broadcastBean;

    @Getter @Setter
    private String login;
    @Getter @Setter
    private String psw;
    @Getter @Setter
    private User user;
    @Getter @Setter
    private String errorMsg;

    public String checkInputData() {
        user = userRepository.findByLoginAndPassword(login.trim(), psw.trim());
        if (user != null) {
            broadcastBean.setUser(user);
            errorMsg = "";
            return "/jsf/CreditCalcView?faces-redirect=true";
        }
        else {
            errorMsg = "Uncorrect login or password";
            return "";
        }
    }

}
