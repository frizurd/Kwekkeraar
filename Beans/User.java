/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Beans;

import Controller.Controller;
import Model.Account;
import Model.Kwek;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

/**
 *
 * @author Frizky
 */
@SessionScoped	
@ManagedBean(name = "user")
public class User implements Serializable {
    
    private Account myAccount;
    private Account userAccount;
    
    private Account editUser;
    private int typeAccount;
    
    private ArrayList<Kwek> userKweks;
    private Controller controller;

    public User() {
        userKweks = new ArrayList<Kwek>();
        controller = Controller.Instance();
    }

    public String amountOfKweks() {
        int amount = 0;
        String amountKweks;

        ArrayList<Kwek> kweks = controller.userKwekList(userAccount);
        for (Kwek k : kweks) {
            amount++;
        }

        if (amount == 1) {
            amountKweks = amount + " kwek";
            return amountKweks;
        } else {
            amountKweks = amount + " kweks";
            return amountKweks;
        }

    }
    
    public int kwekAmount(Account account) {
        ArrayList<Kwek> kweks = controller.userKwekList(account);
        int amount = 0;
        
        if(kweks.size() >= 1) {
            amount = kweks.size();
        }
        
        return amount;
        }
    
    public void addFavorite() {
        if(myAccount == null) {
          
            addMessage(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "Adding favorite failed!", null));

        } else {
            Set favorites = new HashSet(0);
            favorites = myAccount.getFavorites();
            favorites.add(userAccount);
            myAccount.setFavorites(favorites);
            
            controller.update(myAccount);
            
            addMessage(new FacesMessage(FacesMessage.SEVERITY_INFO,
                    "Added to favorites!", null));

        }
    }

    public void removeFavorite() {

        Set favorites = new HashSet(0);
        favorites = myAccount.getFavorites();
        
        if (favorites.contains(userAccount)) {
            favorites.remove(userAccount);
            myAccount.setFavorites(favorites);

            controller.update(myAccount);
            
            addMessage(new FacesMessage(FacesMessage.SEVERITY_INFO,
                    "Removed from favorites!", null));
        }
    }
    

    public String removeAccount() {
        controller.delete(myAccount);
        
        addMessage(new FacesMessage(FacesMessage.SEVERITY_INFO,
                "Your account " + myAccount.getUsername() +
                " has been successfully deleted!", null));
        
        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
        
        return "index";
    }
    
    public String upgradePlatinum() {
        ArrayList<Kwek> kweks = controller.userKwekList(myAccount);
        
        if (kweks.size() >= 200) {
            myAccount.setAccounttype(2);
            controller.update(myAccount);
        }
        
        addMessage(new FacesMessage(FacesMessage.SEVERITY_INFO,
                "Your account has been upgraded to a platinum account!", null));
        
        return "index";
    }
    
    public String accountTypeName(int number) {
        String type = "error";
        
        if(number == 1) {
            type = "Normal Account";
        } else if (number == 2) {
            type = "Platinum Account";
        } else if (number == 3) {
            type = "Administrator";
        }
        
        return type;
    }

    private void addMessage(FacesMessage message) {
        FacesContext.getCurrentInstance().addMessage(null, message);
    }
    
    public ArrayList<Account> showAllAccounts() {
        ArrayList<Account> allUsers = controller.allUsers();
        
        return allUsers;
    }
    
    public ArrayList<Kwek> getLatestUserKweks() {
        userKweks = controller.userKwekList(userAccount);
        
        userAccount = userKweks.get(0).getAccount();
        
        return userKweks;
    }

    public Account getUserAccount() {
        return userAccount;
    }

    public void setUserAccount(Account userAccount) {
        this.userAccount = userAccount;
    }

    public ArrayList<Kwek> getUserKweks() {
        return userKweks;
    }

    public void setUserKweks(ArrayList<Kwek> userKweks) {
        this.userKweks = userKweks;
    }

    public Account getMyAccount() {
        return myAccount;
    }

    public void setMyAccount(Account myAccount) {
        this.myAccount = myAccount;
    }
    
    public void editUserInfo() {
        editUser.setAccounttype(0);
        controller.update(editUser);

        addMessage(new FacesMessage(FacesMessage.SEVERITY_INFO,
                "User account type was changed!", null));
    }

    public Account getEditUser() {
        return editUser;
    }

    public void setEditUser(Account editUser) {
        this.editUser = editUser;
    }

    public int getTypeAccount() {
        return typeAccount;
    }

    public void setTypeAccount(int typeAccount) {
        this.typeAccount = typeAccount;
    }
    
    

}
