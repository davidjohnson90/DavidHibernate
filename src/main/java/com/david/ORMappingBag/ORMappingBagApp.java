package com.david.ORMappingBag;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

public class ORMappingBagApp {
    private static SessionFactory factory;

    public static void main(String[] args) {

        try {
            factory = new Configuration().configure().buildSessionFactory();
        } catch (Throwable ex) {
            System.err.println("Failed to create sessionFactory object." + ex);
            throw new ExceptionInInitializerError(ex);
        }

        ORMappingBagApp ME = new ORMappingBagApp();
        /* Let us have a set of certificates for the first employee  */
        ArrayList list1 = new ArrayList();
        list1.add(new Certificate("BE"));
        list1.add(new Certificate("MS"));
        list1.add(new Certificate("MSW"));
        list1.add(new Certificate("DMS"));

        /* Add employee records in the database */
        Integer empID1 = ME.addEmployee("Arun", "David", 5000, list1);

        /* Another set of certificates for the second employee  */
        ArrayList list2 = new ArrayList();
        list2.add(new Certificate("BE"));
        list2.add(new Certificate("ME"));
        list2.add(new Certificate("MSW"));
        list2.add(new Certificate("PhD"));

        /* Add another employee record in the database */
        Integer empID2 = ME.addEmployee("Jenifer", "Prarthana", 7000, list2);

        /* List down all the employees */
        ME.listEmployees();

        /* Update employee's salary records */
        ME.updateEmployee(empID1, 10000);

        /* Delete an employee from the database */
        ME.deleteEmployee(empID2);

        /* List down all the employees */
        ME.listEmployees();

    }

    /* Method to add an employee record in the database */
    public Integer addEmployee(String fname, String lname, int salary, ArrayList cert) {
        Session session = factory.openSession();
        Transaction tx = null;
        Integer employeeID = null;

        try {
            tx = session.beginTransaction();
            Employee employee = new Employee(fname, lname, salary);
            employee.setCertificates(cert);
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

    /* Method to list all the employees detail */
    public void listEmployees() {
        Session session = factory.openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            List employees = session.createQuery("FROM Employee").list();
            for (Iterator iterator1 = employees.iterator(); iterator1.hasNext(); ) {
                Employee employee = (Employee) iterator1.next();
                System.out.print("First Name: " + employee.getFirstName());
                System.out.print(", Last Name: " + employee.getLastName());
                System.out.println(", Salary: " + employee.getSalary());
                Collection certificates = employee.getCertificates();
                for (Iterator iterator2 = certificates.iterator(); iterator2.hasNext(); ) {
                    Certificate certName = (Certificate) iterator2.next();
                    System.out.println("Certificate: " + certName.getName());
                }
            }
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

    /* Method to update salary for an employee */
    public void updateEmployee(Integer EmployeeID, int salary) {
        Session session = factory.openSession();
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

    /* Method to delete an employee from the records */
    public void deleteEmployee(Integer EmployeeID) {
        Session session = factory.openSession();
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
