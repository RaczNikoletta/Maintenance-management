/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mm.rest.service;

import com.mm.rest.UserContent;
import com.mm.rest.db.DbConnection;
import com.mm.rest.models.TestModel;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.PersistenceException;
import jakarta.persistence.Query;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author david
 */
public class TestService {
    static EntityManagerFactory factory;
    static EntityManager entityManager;
    
    public static void update(Integer id, String name, String kepzes, String password){
        begin();
        
        TestModel existTM = new TestModel();
        existTM.setTid(id);
        existTM.setName(name);
        existTM.setKepzes(kepzes);
        existTM.setPassword(password);
        
        entityManager.merge(existTM);
        
        close();
    }
    
    public static boolean create(UserContent u){
        try{
            begin();
        
            TestModel newTM = new TestModel();
            newTM.setName(u.getName());
            newTM.setKepzes(u.getKepzes());
            newTM.setPassword(u.getPassword());

            entityManager.persist(newTM);

            close();
            return true;
        }catch(PersistenceException ex){
            //Logger.getLogger(DbConnection.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
        
    }
    
    public static void remove(Integer id){
        begin();
        TestModel tmRef = entityManager.getReference(TestModel.class, id);
        entityManager.remove(tmRef);
        close();
    }
    
    public static List<TestModel> query(){
        begin();
        //A Model osztaly nevet hasznaljuk I guess
        String jquery = "SELECT t FROM TestModel t";
        //String jquery = "SELECT * FROM TestModel"; // NEM JO
        
        Query query = entityManager.createQuery(jquery);
        List<TestModel> listTM = query.getResultList();
        
        
        
        close();
        
        return listTM;
    }
    
    public static void retrieve(Integer id){
        begin();
        
        TestModel tm = entityManager.find(TestModel.class, id);
        
        close();
        System.out.println(tm.getTid());
        System.out.println(tm.getName());
        System.out.println(tm.getKepzes());
        System.out.println(tm.getPassword());
    }
    
    private static void begin(){
        factory = Persistence.createEntityManagerFactory("TestUnit");// TestUnit = a persistence.xml ben levo persistenceunit
        entityManager = factory.createEntityManager();
        
        entityManager.getTransaction().begin();
    }
    
    private static void close(){
        entityManager.getTransaction().commit();
        entityManager.close();
        factory.close();
    }
}
