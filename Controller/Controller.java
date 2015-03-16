/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Model.*;
import java.util.List;
import java.util.Observable;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.proxy.HibernateProxy;

/**
 *
 * @author Frizky
 */
public class Controller extends Observable {
    
    private Session session;
    
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
    
//    public boolean updateKwek(Kwek newFlight, Kwek oldFlight) {
//        initSession();
//        boolean result = false;
//
//        if (newFlight.getNumber() != oldFlight.getNumber()) {
//            String query = String.format("update Flight set flightnumber = %s where flightnumber = %s", newFlight.getNumber(), oldFlight.getNumber());
//            try {
//                Transaction tx = session.beginTransaction();
//                session.evict(oldFlight);
//                Query hQuery = session.createQuery(query);
//                hQuery.executeUpdate();
//                tx.commit();
//                result = true;
//            } catch (Exception ex) {
//                Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, ex.getMessage(), ex);
//            }
//        } else {
//            if (update(newFlight)) {
//                result = true;
//            }
//        }
//        if (result) {
//            notifyObservers(oldFlight);
//        }
//        return result;
//    }

    public boolean save(Object obj) {
        boolean result = false;
        try {
            initSession();
            Transaction tx = session.beginTransaction();

            session.save(obj);
            tx.commit();

            result = true;
        } catch (Exception ex) {
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, ex.getMessage(), ex);
        }

        return result;
    }
    
    public List kwekList() {
        
        try {
            initSession();
            Transaction tx = session.beginTransaction();

            List objectsRetrieved =
                    session.createQuery("from Kwek order by kwekid desc").list();
            Kwek kwek = (Kwek) objectsRetrieved.get(0);
            
            return objectsRetrieved;

        } catch (Exception ex) {
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, ex.getMessage(), ex);
        }

        return null;
    }
        
}
