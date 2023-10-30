package com.david.ORMappingMap;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class ORMappingMapApp {
    private static SessionFactory factory;

    public static void main(String[] args) {

        try {
            factory = new Configuration().configure().buildSessionFactory();
        } catch (Throwable ex) {
            System.err.println("Failed to create sessionFactory object." + ex);
            throw new ExceptionInInitializerError(ex);
        }

        ORMappingMapApp ME = new ORMappingMapApp();
        /* Let us have a set of certificates for the first employee  */
        HashMap map1 = new HashMap();
        map1.put("ElectronicsAndCommunicationEngineering", new Certificate("BE"));
        map1.put("TelecommunicationAndSoftwareEngineering", new Certificate("MS"));
        map1.put("HumanResourceManagement", new Certificate("MSW"));
        map1.put("OperationsManagement", new Certificate("DMS"));

        /* Add employee records in the database */
        Integer empID1 = ME.addEmployee("Arun", "David", 5000, map1);

        /* Another set of certificates for the second employee  */
        HashMap map2 = new HashMap();
        map2.put("ElectronicsAndCommunicationEngineering", new Certificate("BE"));
        map2.put("CommunicationSystem", new Certificate("ME"));
        map2.put("SocialConcerns", new Certificate("MSW"));
        map2.put("Sensors", new Certificate("PhD"));

        /* Add another employee record in the database */
        Integer empID2 = ME.addEmployee("Jenifer", "Prarthana", 7000, map2);

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
    public Integer addEmployee(String fname, String lname, int salary, HashMap cert) {
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
                Map certificates = employee.getCertificates();
                System.out.println("Certificate: " + (((Certificate) certificates.get("ElectronicsAndCommunicationEngineering")).getName()) + " - ElectronicsAndCommunicationEngineering");
                System.out.println("Certificate: " + (((Certificate) certificates.get("TelecommunicationAndSoftwareEngineering")).getName())+ " - TelecommunicationAndSoftwareEngineering");
                System.out.println("Certificate: " + (((Certificate) certificates.get("HumanResourceManagement")).getName())+ " - HumanResourceManagement");
                System.out.println("Certificate: " + (((Certificate) certificates.get("OperationsManagement")).getName())+ " - OperationsManagement");
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
