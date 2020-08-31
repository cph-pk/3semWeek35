/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facades;

import dto.EmployeeDTO;
import entities.Employee;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

/**
 *
 * @author Per
 */
public class EmployeeFacade {

    private static EmployeeFacade instance;
    private static EntityManagerFactory emf;

    //Private Constructor to ensure Singleton
    public EmployeeFacade() {
    }

    /**
     *
     * @param _emf
     * @return an instance of this facade class.
     */
    public static EmployeeFacade getEmployeeFacade(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new EmployeeFacade();
        }
        return instance;
    }

    private EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    
    /**
     * Get employee by ID
     *
     * @param id
     * @return
     */
    public EmployeeDTO getEmployeeById(Long id) {
        EntityManager em = emf.createEntityManager();
        try {
            Employee e = em.find(Employee.class, id);
            EmployeeDTO eDTO = new EmployeeDTO(e);
            return eDTO;
        } finally {
            em.close();
        }
    }

    /**
     * Get employee by name
     *
     * @param name
     * @return
     */
    public EmployeeDTO getEmployeesByName(String name) {
        EntityManager em = emf.createEntityManager();
        try {
            Query query = em.createQuery("Select e FROM Employee e WHERE e.Name = :name");
            query.setParameter("name", name);
            query.setMaxResults(1);
            Employee result = (Employee) query.getSingleResult();
            EmployeeDTO eDTO = new EmployeeDTO(result);
            return eDTO;
        } finally {
            em.close();
        }
    }

    /**
     * Get all employees
     *
     * @return
     */
    public List<EmployeeDTO> getAllEmployees() {
        EntityManager em = emf.createEntityManager();

        try {
            TypedQuery<Employee> query
                    = em.createQuery("Select e FROM Employee e", Employee.class);
            List<EmployeeDTO> pdtolist = new ArrayList();
            List<Employee> employees = query.getResultList();
            employees.stream().forEach(p -> {
                pdtolist.add(new EmployeeDTO(p));
            });
            return pdtolist;
        } finally {
            em.close();
        }
    }

    /**
     * Get employee with highest salary
     *
     * @return
     */
    public EmployeeDTO getEmployeesWithHighestSalary() {
        EntityManager em = emf.createEntityManager();
        try {
            Query query = em.createQuery("Select e FROM Employee e ORDER BY e.Salary DESC");
            query.setMaxResults(1);
            Employee result = (Employee) query.getSingleResult();
            EmployeeDTO eDTO = new EmployeeDTO(result);
            return eDTO;
        } finally {
            em.close();
        }
    }

    /**
     * Create employees
     *
     * @param name
     * @param address
     * @param salery
     * @return
     */
    public Employee createEmployee(String name, String address, int salery) {
        Employee employee = new Employee(name, address, salery);
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(employee);
            em.getTransaction().commit();
            return employee;
        } finally {
            em.close();
        }
    }

}
