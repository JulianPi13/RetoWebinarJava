/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo.Clases;

/**
 *
 * @author Julian
 */
public class Participante {
    int idparticipante ;
    String nombre ;
    String correo;
    String empresa;

    public int getIdparticipante() {
        return idparticipante;
    }

    public void setIdparticipante(int idparticipante) {
        this.idparticipante = idparticipante;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getEmpresa() {
        return empresa;
    }

    public void setEmpresa(String empresa) {
        this.empresa = empresa;
    }

    public Participante(int idparticipante, String nombre, String correo, String empresa) {
        this.idparticipante = idparticipante;
        this.nombre = nombre;
        this.correo = correo;
        this.empresa = empresa;
    }

    @Override
    public String toString() {
        return "Participante{" + "idparticipante=" + idparticipante + ", nombre=" + nombre + ", correo=" + correo + ", empresa=" + empresa + '}';
    }
    
    
    
}
