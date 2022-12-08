package com.example.practica14;
public class Alumno {
    private String nombre;
    private String apellidos;
    private String curso;
    //Constructor y getters
    public Alumno(String nombre, String apellidos, String curso) {
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.curso = curso;
    }
    public String getNombre() {
        return nombre;
    }
    public String getApellidos() {
        return apellidos;
    }
    public String getCurso() {
        return curso;
    }
}
