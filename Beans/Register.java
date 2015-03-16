/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Beans;

import Controller.Controller;
import Model.Account;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

/**
 *
 * @author Frizky
 */
@SessionScoped 
@ManagedBean(name = "register")

public class Register {
    
    private String username, password;
    private Controller controller;
    private Account account;

    public Register() {
        controller = Controller.Instance();
    }
    
    public String registerAccount() {
        account = new Account(username, password, 1);
        
        controller.save(account);
        
        addMessage(new FacesMessage(FacesMessage.SEVERITY_INFO, 
                "Your account " + username + " has been created!", null));
        return "index";
    }
    
    private void addMessage(FacesMessage message) {
        FacesContext.getCurrentInstance().addMessage(null, message);
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }
    
}