/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import utils.PuSelector;

/**
 *
 * @author dmt1
 */
public class HistoryFacade {
    EntityManager em = PuSelector.getEntityManagerFactory("pu").createEntityManager();
    
    public void SaveHistory(String week, String address){
   
    History history = new History();
    history.setArguments("Week: "+week + " address: " + address);
    
          try {
            em.getTransaction().begin();
            em.persist(history);
            em.getTransaction().commit();
        } finally{
        em.close();
        }
    
    
    }
    
     public void PostHistory(History h){
   

        try {
            em.getTransaction().begin();
            em.persist(h);
            em.getTransaction().commit();
        } finally{
        em.close();
        }
    
    
    }
    
    public List<DTOhistory> getAllHistory(){
       
        List<DTOhistory> myList = new ArrayList();
        
        try {
            myList = em.createQuery("SELECT h FROM History h").getResultList();
        } finally{
        em.close();
        }
        
        return myList;

}
    public static void main(String[] args) {
        System.out.println( new HistoryFacade().getAllHistory());
    }
}
 
    