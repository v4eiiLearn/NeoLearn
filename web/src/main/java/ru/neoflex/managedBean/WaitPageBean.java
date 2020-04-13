package ru.neoflex.managedBean;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import java.io.IOException;

@ManagedBean(name = "waitBean")
@ViewScoped
public class WaitPageBean {

    private long readyTime;

    @EJB
    private BroadcastBean broadcastBean;

    public void checkReady() throws IOException {
        if (broadcastBean.getPaymentSchedule() != null) {
            FacesContext.getCurrentInstance().getExternalContext().redirect("SchedulePaymentsView.xhtml");
            FacesContext.getCurrentInstance().responseComplete();
        }
    }

}
