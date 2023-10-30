package com.david.hibernateDemo;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import java.util.Iterator;
import java.util.List;

public class ManageEmployeeApp {
    private static SessionFactory sessionFactory;

    public static void main(String[] args) {

        try {
            sessionFactory = new Configuration().configure().buildSessionFactory();
        } catch (Throwable throwable) {
            System.err.println("Failed to create sessionFactory object." + throwable);
            throw new ExceptionInInitializerError(throwable);
        }

        ManageEmployeeApp manageEmployeeApp = new ManageEmployeeApp();

        /* Add few employee records in database */
        Integer empID1 = manageEmployeeApp.addEmployee("Arun", "David", 1000);
        Integer empID2 = manageEmployeeApp.addEmployee("Jenifer", "Prarthana", 5000);
        Integer empID3 = manageEmployeeApp.addEmployee("David", "Johnson", 10000);

        System.out.println("Listing employees after adding the employee data");
        /* List down all the employees */
        manageEmployeeApp.listEmployees();

        /* Update employee's records */
        manageEmployeeApp.updateEmployee(empID1, 5000);
        manageEmployeeApp.updateEmployee(empID2, 7000);

        /* Delete an employee from the database */
        manageEmployeeApp.deleteEmployee(empID3);

        System.out.println("Listing employees after CRUD operations");
        /* List down new list of the employees */
        manageEmployeeApp.listEmployees();
    }

    /* Method to CREATE an employee in the database */
    public Integer addEmployee(String fname, String lname, int salary) {
        Session session = sessionFactory.openSession();
        Transaction tx = null;
        Integer employeeID = null;

        try {
            tx = session.beginTransaction();
            Employee employee = new Employee(fname, lname, salary);
            employeeID = (Integer) session.save(employee);
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
        return employeeID;
    }

    /* Method to  READ all the employees */
    public void listEmployees() {
        Session session = sessionFactory.openSession();
        Transaction transaction = null;

        try {
            transaction = session.beginTransaction();
            List employees = session.createQuery("FROM Employee").list();
            for (Iterator iterator = employees.iterator(); iterator.hasNext(); ) {
                Employee employee = (Employee) iterator.next();
                System.out.print("First Name: " + employee.getFirstName());
                System.out.print(", Last Name: " + employee.getLastName());
                System.out.println(", Salary: " + employee.getSalary());
            }
            transaction.commit();
        } catch (HibernateException e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

    /* Method to UPDATE salary for an employee */
    public void updateEmployee(Integer EmployeeID, int salary) {
        Session session = sessionFactory.openSession();
        Transaction tx = null;

        try {
            tx = session.beginTransaction();
            Employee employee = (Employee) session.get(Employee.class, EmployeeID);
            employee.setSalary(salary);
            session.update(employee);
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

    /* Method to DELETE an employee from the records */
    public void deleteEmployee(Integer EmployeeID) {
        Session session = sessionFactory.openSession();
        Transaction tx = null;

        try {
            tx = session.beginTransaction();
            Employee employee = (Employee) session.get(Employee.class, EmployeeID);
            session.delete(employee);
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
    }
}
