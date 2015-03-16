/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Beans;

import Controller.Controller;
import Model.Account;
import Model.Kwek;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

/**
 *
 * @author Frizky
 */

@ManagedBean(name = "kwek")
@RequestScoped
public class KwekBean {

    private ArrayList<Kwek> latestKweks;
    private String message;
    private Account myAccount, userAccount;
    private Controller controller;
    
    public KwekBean() {
        latestKweks = new ArrayList<Kwek>();
        controller = Controller.Instance();
    }

    public void submitKwek() {
        Kwek kwek = new Kwek();
        kwek.setMessage(message);
        kwek.setAccount(myAccount);
        
        controller.save(kwek);
    }
    
    public void kwaikKwek(Kwek kwek) {

        Set accounts = new HashSet(0);
        accounts = kwek.getAccounts();
        accounts.add(myAccount);
        kwek.setAccounts(accounts);

        controller.update(kwek);

        addMessage(new FacesMessage(FacesMessage.SEVERITY_INFO,
                "Kwek by " + kwek.getAccount().getUsername() + " Kwaiked!", null));

    }
    
    public void removeKwaik(Kwek kwek) {

        Set accounts = new HashSet(0);
        accounts = kwek.getAccounts();
        accounts.remove(myAccount);
        kwek.setAccounts(accounts);

        controller.update(kwek);

        addMessage(new FacesMessage(FacesMessage.SEVERITY_INFO,
                "Kwek by " + kwek.getAccount().getUsername() + " un-kwaiked!", null));

    }
    
    private void addMessage(FacesMessage message) {
        FacesContext.getCurrentInstance().addMessage(null, message);
    }
    
    public ArrayList<Kwek> getLatestKweks() {
        return latestKweks;
    }
    
    public ArrayList<Kwek> kweksHaveLimit(boolean limit) {
        latestKweks = controller.allKwekList(limit);

        return latestKweks;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Account getMyAccount() {
        return myAccount;
    }

    public void setMyAccount(Account account) {
        this.myAccount = account;
    }

    public Account getUserAccount() {
        return userAccount;
    }

    public void setUserAccount(Account userAccount) {
        this.userAccount = userAccount;
    }
    
}
