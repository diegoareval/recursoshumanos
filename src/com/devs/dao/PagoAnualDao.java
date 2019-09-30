/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.devs.dao;

import com.devs.daogeneral.DaoGeneral;
import com.devs.entities.Pagosanual;
import com.devs.util.HibernateUtil;
import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

/**
 *
 * @author DiegoArevalo
 */
public class PagoAnualDao extends DaoGeneral{
      //Establecer conexion con la base de datos
    SessionFactory factory = HibernateUtil.getSessionFactory();
    Session session = null;
    
     //metodo que permite listar todos los registros de la base de datos
    public List<Pagosanual> listAll() {
       
//lista que contendra todos los registros de todos los bancos
        List<Pagosanual> listado = null;
        
        //transaccion que permite ejecutar la consulta exitosamente o no ejecutarla
        Transaction tx = null;
        try {
            session = factory.openSession();
            tx = session.beginTransaction();

            //hquery que permite obtener todos los registros
            Query query = session.createQuery("From Pagosanual");
            //permite llenar la lista de todos los bancos
            listado =(List<Pagosanual>) query.list();
            //permite ejecutar la consulta
            tx.commit();

        } catch (HibernateException e) {
            if (tx != null) {
                //en caso de excepcion permite volver a el estado normal
                tx.rollback();
            }
            System.out.println("Excepcion: "+e.getMessage());
        } finally {
            session.close();
        }
        return listado;
    }
    //metodo que permite realizar una busqueda de registros por medio de un parametro
    //BancoDao bancodao=new BancoDao();
    //bancodao.listallparameter()
    public List<Pagosanual> listAllParameter(String parameter) {
       //atributo que contendra los registros encontrados
        List<Pagosanual> listado = null;
        Transaction tx = null;
        try {
            //abrir la conexion
            session = factory.openSession();
            tx = session.beginTransaction();
           //query que permite encontrar los registros por medio de una busqueda
           //select * from bancos where descripcion like %'sco'%
            Query query = session.createQuery("From Pagosanual where idpago like '%"+parameter+"%'");
            listado =(List<Pagosanual>) query.list();
            //permite ejecutar toda la consulta
            tx.commit();

        } catch (HibernateException e) {
            if (tx != null) {
                //permite volver al estado normal
                tx.rollback();
            }
            System.out.println("Error: "+e.getMessage());
        } finally {
            session.close();
        }
        return listado;
    }
    
     public List<Pagosanual> buscarporID(int parameter) {
       //atributo que contendra los registros encontrados
        List<Pagosanual> listado = null;
        Transaction tx = null;
        try {
            //abrir la conexion
            session = factory.openSession();
            tx = session.beginTransaction();
           //query que permite encontrar los registros por medio de una busqueda
           //select * from bancos where descripcion like %'sco'%
            Query query = session.createQuery("From Pagosanual where idpago= '"+parameter+"%'");
            listado =(List<Pagosanual>) query.list();
            //permite ejecutar toda la consulta
            tx.commit();

        } catch (HibernateException e) {
            if (tx != null) {
                //permite volver al estado normal
                tx.rollback();
            }
            System.out.println("Error: "+e.getMessage());
        } finally {
            session.close();
        }
        return listado;
    }
}
