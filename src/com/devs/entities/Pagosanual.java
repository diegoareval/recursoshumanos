package com.devs.entities;
// Generated sep 12, 2019 10:05:33 a.m. by Hibernate Tools 4.3.1


import java.math.BigDecimal;

/**
 * Pagosanual generated by hbm2java
 */
public class Pagosanual  implements java.io.Serializable {


     private Integer idpago;
     private Empleados empleados;
     private BigDecimal salarioanual;
     private BigDecimal isss;
     private BigDecimal afp;
     private BigDecimal aguinaldo;
     private BigDecimal vacaciones;

    public Pagosanual() {
    }

    public Pagosanual(Empleados empleados, BigDecimal salarioanual, BigDecimal isss, BigDecimal afp, BigDecimal aguinaldo, BigDecimal vacaciones) {
       this.empleados = empleados;
       this.salarioanual = salarioanual;
       this.isss = isss;
       this.afp = afp;
       this.aguinaldo = aguinaldo;
       this.vacaciones = vacaciones;
    }
   
    public Integer getIdpago() {
        return this.idpago;
    }
    
    public void setIdpago(Integer idpago) {
        this.idpago = idpago;
    }
    public Empleados getEmpleados() {
        return this.empleados;
    }
    
    public void setEmpleados(Empleados empleados) {
        this.empleados = empleados;
    }
    public BigDecimal getSalarioanual() {
        return this.salarioanual;
    }
    
    public void setSalarioanual(BigDecimal salarioanual) {
        this.salarioanual = salarioanual;
    }
    public BigDecimal getIsss() {
        return this.isss;
    }
    
    public void setIsss(BigDecimal isss) {
        this.isss = isss;
    }
    public BigDecimal getAfp() {
        return this.afp;
    }
    
    public void setAfp(BigDecimal afp) {
        this.afp = afp;
    }
    public BigDecimal getAguinaldo() {
        return this.aguinaldo;
    }
    
    public void setAguinaldo(BigDecimal aguinaldo) {
        this.aguinaldo = aguinaldo;
    }
    public BigDecimal getVacaciones() {
        return this.vacaciones;
    }
    
    public void setVacaciones(BigDecimal vacaciones) {
        this.vacaciones = vacaciones;
    }




}

