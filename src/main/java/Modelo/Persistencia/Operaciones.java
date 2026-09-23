package Modelo.Persistencia;

import Modelo.Clases.Participante;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Operaciones {

    // ============================================
    // MÉTODO 1: INSCRIBIR PARTICIPANTE (25% de la nota)
    // ============================================
    public String inscribirParticipante(Participante p) {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            // 1. Obtener la conexión
            con = ConexionBD.getConnection();
            
            // 2. Desactivar el autocommit para manejar la transacción manualmente
            con.setAutoCommit(false);

            // 3. Validar si el correo ya existe
            String sqlValidar = "SELECT COUNT(*) FROM participantes WHERE correo = ?";
            ps = con.prepareStatement(sqlValidar);
            ps.setString(1, p.getCorreo());
            rs = ps.executeQuery();

            rs.next(); // Mover el cursor a la primera fila
            int cantidad = rs.getInt(1); // Obtener el conteo

            // 4. Si el correo ya existe, hacer rollback y retornar error
            if (cantidad > 0) {
                con.rollback();
                return "Error: El correo " + p.getCorreo() + " ya está registrado.";
            }

            // 5. Si no existe, proceder a insertar
            String sqlInsertar = "INSERT INTO participantes (nombre, correo, empresa) VALUES (?, ?, ?)";
            ps = con.prepareStatement(sqlInsertar);
            ps.setString(1, p.getNombre());
            ps.setString(2, p.getCorreo());
            ps.setString(3, p.getEmpresa());
            ps.executeUpdate();

            // 6. Si todo salió bien, hacer commit (guardar permanentemente)
            con.commit();
            return "Participante inscrito con éxito.";

        } catch (SQLException e) {
            // 7. Si ocurre cualquier error, hacer rollback para deshacer cambios
            try {
                if (con != null) {
                    con.rollback();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            return "Error en la base de datos: " + e.getMessage();
        } finally {
            // 8. Cerrar todos los recursos (muy importante para el 10% de calidad)
            try {
                if (rs != null) rs.close();
                if (ps != null) ps.close();
                if (con != null) con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    // ============================================
    // MÉTODO 2: LISTAR TODOS LOS PARTICIPANTES
    // ============================================
    public List<Participante> listarParticipantes() {
        List<Participante> lista = new ArrayList<>();
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            con = ConexionBD.getConnection();
            String sql = "SELECT idparticipante, nombre, correo, empresa FROM participantes";
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();

            while (rs.next()) {
                Participante p = new Participante(
                    rs.getInt("idparticipante"),
                    rs.getString("nombre"),
                    rs.getString("correo"),
                    rs.getString("empresa")
                );
                lista.add(p);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (ps != null) ps.close();
                if (con != null) con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return lista;
    }

    // ============================================
    // MÉTODO 3: BUSCAR POR EMPRESA
    // ============================================
    public List<Participante> buscarPorEmpresa(String empresa) {
        List<Participante> lista = new ArrayList<>();
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            con = ConexionBD.getConnection();
            String sql = "SELECT idparticipante, nombre, correo, empresa FROM participantes WHERE empresa = ?";
            ps = con.prepareStatement(sql);
            ps.setString(1, empresa);
            rs = ps.executeQuery();

            while (rs.next()) {
                Participante p = new Participante(
                    rs.getInt("idparticipante"),
                    rs.getString("nombre"),
                    rs.getString("correo"),
                    rs.getString("empresa")
                );
                lista.add(p);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (ps != null) ps.close();
                if (con != null) con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return lista;
    }

    // ============================================
    // MÉTODO 4: CONTAR PARTICIPANTES
    // ============================================
    public int contarParticipantes() {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        int total = 0;

        try {
            con = ConexionBD.getConnection();
            String sql = "SELECT COUNT(*) FROM participantes";
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();

            if (rs.next()) {
                total = rs.getInt(1);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (ps != null) ps.close();
                if (con != null) con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return total;
    }

    // ============================================
    // MÉTODO 5: ELIMINAR POR ID
    // ============================================
    public String eliminarPorId(int id) {
        Connection con = null;
        PreparedStatement ps = null;

        try {
            con = ConexionBD.getConnection();
            String sql = "DELETE FROM participantes WHERE idparticipante = ?";
            ps = con.prepareStatement(sql);
            ps.setInt(1, id);

            int filasAfectadas = ps.executeUpdate();

            if (filasAfectadas > 0) {
                return "Participante eliminado con éxito.";
            } else {
                return "No se encontró ningún participante con el ID: " + id;
            }

        } catch (SQLException e) {
            return "Error en la base de datos: " + e.getMessage();
        } finally {
            try {
                if (ps != null) ps.close();
                if (con != null) con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}