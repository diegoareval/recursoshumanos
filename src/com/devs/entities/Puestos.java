package com.devs.entities;
// Generated sep 12, 2019 10:05:33 a.m. by Hibernate Tools 4.3.1


import java.util.HashSet;
import java.util.Set;

/**
 * Puestos generated by hbm2java
 */
public class Puestos  implements java.io.Serializable {


     private Integer idpuesto;
     private Area area;
     private String puesto;
     private String idarea;
     private Set empleadoses = new HashSet(0);

    public Puestos() {
    }

	
    public Puestos(Area area) {
        this.area = area;
    }
    public Puestos(Area area, String puesto, String idarea, Set empleadoses) {
       this.area = area;
       this.puesto = puesto;
       this.idarea = idarea;
       this.empleadoses = empleadoses;
    }
   
    public Integer getIdpuesto() {
        return this.idpuesto;
    }
    
    public void setIdpuesto(Integer idpuesto) {
        this.idpuesto = idpuesto;
    }
    public Area getArea() {
        return this.area;
    }
    
    public void setArea(Area area) {
        this.area = area;
    }
    public String getPuesto() {
        return this.puesto;
    }
    
    public void setPuesto(String puesto) {
        this.puesto = puesto;
    }
    public String getIdarea() {
        return this.idarea;
    }
    
    public void setIdarea(String idarea) {
        this.idarea = idarea;
    }
    public Set getEmpleadoses() {
        return this.empleadoses;
    }
    
    public void setEmpleadoses(Set empleadoses) {
        this.empleadoses = empleadoses;
    }
@Override
public String toString(){
    return puesto.toString();
}



}

