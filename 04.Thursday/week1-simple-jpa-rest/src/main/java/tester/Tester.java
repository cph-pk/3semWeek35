/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tester;

import entities.Employee;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author Per
 */
public class Tester {
    public static void main(String[] args) {
         EntityManagerFactory emf = Persistence.createEntityManagerFactory("pu");
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(new Employee("Kurt", "Wonnegut", 335567));
            em.persist(new Employee("Hanne", "Olsen", 435867));
            em.persist(new Employee("Jan", "Olsen", 411567));
            em.persist(new Employee("Irene", "Petersen", 33567));
            em.persist(new Employee("Tian", "Wonnegut", 56567));
            em.getTransaction().commit();
        } finally {
            em.close();
            emf.close();
        }
    }
}
