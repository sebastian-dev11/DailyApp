/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package conexion;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

class Conexion {
    
    private static final String URL = "jdbc:mysql://localhost:3306/dailyapp";
    private static final String USUARIO = "root"; 
    private static final String PASSWORD = ""; 

    public static Connection conectar() {
        Connection conexion = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conexion = DriverManager.getConnection(URL, USUARIO, PASSWORD);
            System.out.println("Conexión exitosa a la base de datos.");
        } catch (ClassNotFoundException | SQLException e) {
            System.err.println("Error al conectar: " + e.getMessage());
        }
        return conexion;
    }

    public static void insertarUsuario(String nombre, String correo, String contrasena, String fecha_Registro) {
        String sql = "INSERT INTO usuarios (nombre, correo, contrasena, fecha_registro) VALUES (?, ?, ?, ?)";
        
        try (Connection conexion = conectar(); 
             PreparedStatement statement = conexion.prepareStatement(sql)) {

            statement.setString(1, nombre);
            statement.setString(2, correo);
            statement.setString(3, contrasena);
            statement.setString(4, fecha_Registro);

            int filasAfectadas = statement.executeUpdate();
            System.out.println("Usuario insertado correctamente. Filas afectadas: " + filasAfectadas);
            
        } catch (SQLException e) {
            System.err.println("Error al insertar usuario: " + e.getMessage());
        }
    }

    public static void consultarUsuarios() {
        String sql = "SELECT * FROM usuarios";

        try (Connection conexion = conectar();
             PreparedStatement statement = conexion.prepareStatement(sql);
             ResultSet resultado = statement.executeQuery()) {

            System.out.println("Lista de usuarios:");
            while (resultado.next()) {
                int id = resultado.getInt("id_usuario");
                String nombre = resultado.getString("nombre");
                String correo = resultado.getString("correo");
                String fechaRegistro = resultado.getString("fecha_registro");

                System.out.println("ID: " + id);
                System.out.println("Nombre: " + nombre);
                System.out.println("Correo: " + correo);
                System.out.println("Fecha_registro: " + fechaRegistro);
                System.out.println("-------------------------");
            }

        } catch (SQLException e) {
            System.err.println("Error al consultar usuarios: " + e.getMessage());
        }
    }

    public static void eliminarUsuario(int id_usuario) {
        String sql = "DELETE FROM usuarios WHERE id_usuario = ?";

        try (Connection conexion = conectar();
             PreparedStatement statement = conexion.prepareStatement(sql)) {

            statement.setInt(1, id_usuario);

            int filasAfectadas = statement.executeUpdate();
            if (filasAfectadas > 0) {
                System.out.println(" Usuario eliminado correctamente. ID: " + id_usuario);
            } else {
                System.out.println(" No se encontró un usuario con ID: " + id_usuario);
            }

        } catch (SQLException e) {
            System.err.println(" Error al eliminar usuario: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        // Insertar usuarios
        insertarUsuario("Steven Diaz", "StevenDiaz@mail.com", "3678475", "2025-01-30");
        

        // Consultar usuarios
        consultarUsuarios();

        // Eliminar un usuario (Ejemplo: eliminar usuario con ID 1)
        eliminarUsuario(1);

        // Consultar usuarios nuevamente para ver cambios
        consultarUsuarios();
    }

}


