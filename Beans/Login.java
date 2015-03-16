/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Beans;

import Controller.Controller;
import Model.Account;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

/**
 *
 * @author Frizky
 */
@SessionScoped 
@ManagedBean(name = "login")
public class Login {
    
    private String username, password;
    private Account account;
    
    private Controller controller;
    
    public String login() {
        controller = Controller.Instance();
        account = controller.login(username, password);
        return "index";
    }

    public String logout() {
        account = null;
        return "index";
    }

    public boolean isLoggedIn() {
        return account != null;
    }

    public Account getCurrentUser() {
        return account;
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