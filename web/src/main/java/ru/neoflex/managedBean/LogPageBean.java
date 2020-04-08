package ru.neoflex.managedBean;

import lombok.Getter;
import lombok.Setter;
import ru.neoflex.entity.User;
import ru.neoflex.repository.UserRepository;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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

    public void findUser() {
        if (user == null) {
            user = userRepository.findByLoginAndPassword(login.trim(), psw.trim());
        }
        broadcastBean.setUser(user);
    }

}
