package com.ort.casievaluacion.Utilidades;

public class Utilidades
{
    public static final String TABLA="tabla";
    public static final String CAMPO_NOMBRE="nombre";
    public static final String CAMPO_APELLIDO="apellido";
    public static final String CAMPO_MAIL="email";
    public static final String CAMPO_PASSWORD="pass";

    public static final String CREAR_TABLA="CREATE TABLE " +
            "" + TABLA + " ("+CAMPO_NOMBRE + " " +
            "TEXT, " + CAMPO_APELLIDO + " TEXT," +
            CAMPO_MAIL + " TEXT," +
            CAMPO_PASSWORD + " TEXT)";
}
