package com.devs.entities;
// Generated sep 12, 2019 10:05:33 a.m. by Hibernate Tools 4.3.1



/**
 * Obligaciones generated by hbm2java
 */
public class Obligaciones  implements java.io.Serializable {


     private Integer idobligacion;
     private Contrato contrato;
     private String descripcion;

    public Obligaciones() {
    }

    public Obligaciones(Contrato contrato, String descripcion) {
       this.contrato = contrato;
       this.descripcion = descripcion;
    }
   
    public Integer getIdobligacion() {
        return this.idobligacion;
    }
    
    public void setIdobligacion(Integer idobligacion) {
        this.idobligacion = idobligacion;
    }
    public Contrato getContrato() {
        return this.contrato;
    }
    
    public void setContrato(Contrato contrato) {
        this.contrato = contrato;
    }
    public String getDescripcion() {
        return this.descripcion;
    }
    
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }




}

