/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Beans;

import Controller.Controller;
import Model.Kwek;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

/**
 *
 * @author Frizky
 */

@ManagedBean(name = "kwek")
@RequestScoped
public class KwekBean {

    private List<Kwek> latestKweks;
    
    public KwekBean() {

    }

    public List getLatestKweks() {
        Controller c = new Controller();
        latestKweks = c.kwekList();
        return latestKweks;
    }
    
}
