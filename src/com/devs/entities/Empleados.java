package com.devs.entities;
// Generated sep 12, 2019 10:05:33 a.m. by Hibernate Tools 4.3.1


import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

/**
 * Empleados generated by hbm2java
 */
public class Empleados  implements java.io.Serializable {


     private Integer idempleado;
     private Puestos puestos;
     private String nombres;
     private String apellidos;
     private String dui;
     private String direccion;
     private String telefono;
     private String correo;
     private String estado;
     private BigDecimal salario;
     private Set pagomensuals = new HashSet(0);
     private Set contratos = new HashSet(0);
     private Set pagosanuals = new HashSet(0);

    public Empleados() {
    }

    public Empleados(Puestos puestos, String nombres, String apellidos, String dui, String direccion, String telefono, String correo, String estado, BigDecimal salario, Set pagomensuals, Set contratos, Set pagosanuals) {
       this.puestos = puestos;
       this.nombres = nombres;
       this.apellidos = apellidos;
       this.dui = dui;
       this.direccion = direccion;
       this.telefono = telefono;
       this.correo = correo;
       this.estado = estado;
       this.salario = salario;
       this.pagomensuals = pagomensuals;
       this.contratos = contratos;
       this.pagosanuals = pagosanuals;
    }
   
    public Integer getIdempleado() {
        return this.idempleado;
    }
    
    public void setIdempleado(Integer idempleado) {
        this.idempleado = idempleado;
    }
    public Puestos getPuestos() {
        return this.puestos;
    }
    
    public void setPuestos(Puestos puestos) {
        this.puestos = puestos;
    }
    public String getNombres() {
        return this.nombres;
    }
    
    public void setNombres(String nombres) {
        this.nombres = nombres;
    }
    public String getApellidos() {
        return this.apellidos;
    }
    
    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }
    public String getDui() {
        return this.dui;
    }
    
    public void setDui(String dui) {
        this.dui = dui;
    }
    public String getDireccion() {
        return this.direccion;
    }
    
    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }
    public String getTelefono() {
        return this.telefono;
    }
    
    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }
    public String getCorreo() {
        return this.correo;
    }
    
    public void setCorreo(String correo) {
        this.correo = correo;
    }
    public String getEstado() {
        return this.estado;
    }
    
    public void setEstado(String estado) {
        this.estado = estado;
    }
    public BigDecimal getSalario() {
        return this.salario;
    }
    
    public void setSalario(BigDecimal salario) {
        this.salario = salario;
    }
    public Set getPagomensuals() {
        return this.pagomensuals;
    }
    
    public void setPagomensuals(Set pagomensuals) {
        this.pagomensuals = pagomensuals;
    }
    public Set getContratos() {
        return this.contratos;
    }
    
    public void setContratos(Set contratos) {
        this.contratos = contratos;
    }
    public Set getPagosanuals() {
        return this.pagosanuals;
    }
    
    public void setPagosanuals(Set pagosanuals) {
        this.pagosanuals = pagosanuals;
    }




}


