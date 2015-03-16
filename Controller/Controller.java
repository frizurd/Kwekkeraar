
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Model.Account;
import Model.Kwek;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author Frizky
 */
public class Controller extends Observable {
    
    private Session session;
    private static Controller controller;
    
    public static Controller Instance() {
        if (controller == null) {
            controller = new Controller();
        }

        return controller;
    }
    
    private void initSession() {
        if (session == null || HibernateUtil.getSessionFactory().isClosed()) {
            session = HibernateUtil.getSessionFactory().openSession();
        }
    }
    
    public boolean update(Object obj) {
        try {
            initSession();
            Transaction tx = session.beginTransaction();
            
            session.update(obj);
            tx.commit();
            return true;
        } catch (Exception ex) {
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, ex.getMessage(), ex);
            return false;
        }
    }

    public boolean save(Object obj) {
        boolean result = false;
        try {
            initSession();
            Transaction tx = session.beginTransaction();

            session.save(obj);
            tx.commit();

            result = true;
        } catch (Exception ex) {
            Logger.getLogger(Controller.class.getName()).
                    log(Level.SEVERE, ex.getMessage(), ex);
        }

        return result;
    }
    
    public boolean delete(Object obj) {
        boolean result = false;
        try {
            initSession();
            Transaction tx = session.beginTransaction();
            
            session.delete(obj);
            tx.commit();
            
            result = true;
        } catch (Exception ex) {
            Logger.getLogger(Controller.class.getName()).
                    log(Level.SEVERE, ex.getMessage(), ex);
        }
        
        return result;
    }
    
    public ArrayList<Account> allUsers() {
        
        try {
            initSession();
            Transaction tx = session.beginTransaction();

            ArrayList<Account> objectsRetrieved = 
                    (ArrayList<Account>) session.createQuery("from Account "
                    + "where accounttype <> 3 order by username asc").list();
            //Account account = objectsRetrieved.get(0);
            
            return objectsRetrieved;
            

        } catch (Exception ex) {
            Logger.getLogger(Controller.class.getName()).
                    log(Level.SEVERE, ex.getMessage(), ex);
        }

        return null;
    }
    
    public ArrayList<Kwek> allKwekList(boolean limit) {
        
        try {
            initSession();
            Transaction tx = session.beginTransaction();

            
            Query query = session.createQuery("from Kwek order by kwekid desc");
            if (limit) {
                query.setMaxResults(10);
            }
            ArrayList<Kwek> objectsRetrieved = (ArrayList<Kwek>) query.list();
            
            //Kwek kwek = objectsRetrieved.get(0);
            
            return objectsRetrieved;
            

        } catch (Exception ex) {
            Logger.getLogger(Controller.class.getName()).
                    log(Level.SEVERE, ex.getMessage(), ex);
        }

        return null;
    }
    
    public ArrayList<Kwek> userKwekList(Account a) {
        
        try {
            initSession();
            Transaction tx = session.beginTransaction();

            Query query = session.createQuery("from Kwek where accountid = :id order by kwekid desc");
            query.setParameter("id", a.getAccountid());
            
            ArrayList<Kwek> objectsRetrieved = (ArrayList<Kwek>) query.list();
                    
            //Kwek kwek = objectsRetrieved.get(0);
            
            return objectsRetrieved;

        } catch (Exception ex) {
            Logger.getLogger(Controller.class.getName()).
                    log(Level.SEVERE, ex.getMessage(), ex);
        }

        return null;
    }
    
    public Account login(String user, String pass) {
        try {
            initSession();
            Transaction tx = session.beginTransaction();
            
            Query query = session.createQuery("from Account "
                    + "where username = :username and password = :password ");
            
            query.setParameter("username", user);
            query.setParameter("password", pass);
            
            List results = query.list();

            if (!results.isEmpty()) {
                Account account = (Account) results.get(0);
                return account;
            } else {
                return null;
            }
            
        } catch (Exception ex) {
            Logger.getLogger(Controller.class.getName()).
                    log(Level.SEVERE, ex.getMessage(), ex);
        }
        
        return null;
    }

    public Session getSession() {
        return session;
    }
    
    
    
}
