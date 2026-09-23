/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo.Persistencia;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionBD {
    
    // Se declaran como constantes (final) porque no van a cambiar
    private static final String URL = "jdbc:mysql://localhost:3306/my_db";
    private static final String USUARIO = "root";
    private static final String CONTRASENA = "123abcdE*"; // Tu contraseña real

    // Método único y limpio para obtener la conexión
    public static Connection getConnection() throws SQLException {
        // Esto intenta conectar. Si falla, lanza la excepción para que Operaciones la maneje
        return DriverManager.getConnection(URL, USUARIO, CONTRASENA);
    }
}
