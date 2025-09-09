package ar.charlycimino.recetorium.model.repository;

import ar.charlycimino.recetorium.model.Ingrediente;
import ar.charlycimino.recetorium.model.db.JdbcUtils;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Charly Cimino Aprendé más Java en mi canal:
 * https://www.youtube.com/c/CharlyCimino Encontrá más código en mi repo de
 * GitHub: https://github.com/CharlyCimino
 */


import ar.charlycimino.recetorium.model.db.JdbcUtils;
import ar.charlycimino.recetorium.model.Ingrediente;
import ar.charlycimino.recetorium.model.repository.IngredienteRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Implementación JDBC del repositorio de ingredientes.
 * - Usa PreparedStatement (previene inyección SQL).
 * - Maneja generated keys al insertar.
 * - Cierra recursos con try-with-resources.
 *
 * Reglas educativas respetadas:
 * - Lógica imperativa tradicional.
 * - Un solo return por método (al final).
 */
public class IngredienteRepositoryJdbc implements IngredienteRepository {

    // Consultas SQL básicas
    private static final String SQL_FIND_ALL = "SELECT id, nombre, foto, color FROM ingrediente";
    private static final String SQL_FIND_BY_ID = "SELECT id, nombre, foto, color FROM ingrediente WHERE id = ?";
    private static final String SQL_INSERT = "INSERT INTO ingrediente (nombre, foto, color) VALUES (?, ?, ?)";
    private static final String SQL_UPDATE = "UPDATE ingrediente SET nombre = ?, foto = ?, color = ? WHERE id = ?";
    private static final String SQL_DELETE = "DELETE FROM ingrediente WHERE id = ?";
    private static final String SQL_EXISTS = "SELECT 1 FROM ingrediente WHERE id = ? LIMIT 1";

    @Override
    public List<Ingrediente> findAll() {
        List<Ingrediente> resultado = new ArrayList<>();
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = JdbcUtils.getConnection();
            ps = conn.prepareStatement(SQL_FIND_ALL);
            rs = ps.executeQuery();
            while (rs.next()) {
                Ingrediente ing = hydrateIngrediente(rs);
                resultado.add(ing);
            }
        } catch (Exception e) {
            throw new RuntimeException("Error findAll ingredientes", e);
        } finally {
            // Cerrar recursos (si no usamos try-with-resources, cerramos manualmente)
            try { if (rs != null) rs.close(); } catch (Exception ignored) {}
            try { if (ps != null) ps.close(); } catch (Exception ignored) {}
            try { if (conn != null) conn.close(); } catch (Exception ignored) {}
        }
        return resultado;
    }

    @Override
    public Ingrediente findById(int id) {
        Ingrediente resultado = null;
        try (Connection conn = JdbcUtils.getConnection();
             PreparedStatement ps = conn.prepareStatement(SQL_FIND_BY_ID)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    resultado = hydrateIngrediente(rs);
                }
            }
        } catch (Exception e) {
            throw new RuntimeException("Error findById ingrediente", e);
        }
        return resultado;
    }

    @Override
    public Ingrediente save(Ingrediente ingrediente) {
        Ingrediente resultado = null;
        // Si id es null o menor/igual 0 -> insert, sino update
        if (ingrediente.getId() <= 0) {
            // INSERT + recuperar generated key
            try (Connection conn = JdbcUtils.getConnection();
                 PreparedStatement ps = conn.prepareStatement(SQL_INSERT, Statement.RETURN_GENERATED_KEYS)) {
                ps.setString(1, ingrediente.getNombre());
                ps.setString(2, ingrediente.getFoto());
                ps.setString(3, ingrediente.getColor());
                ps.executeUpdate();
                try (ResultSet keys = ps.getGeneratedKeys()) {
                    if (keys.next()) {
                        int generatedId = keys.getInt(1);
                        ingrediente.setId(generatedId);
                    }
                }
                resultado = ingrediente;
            } catch (Exception e) {
                throw new RuntimeException("Error insert ingrediente", e);
            }
        } else {
            // UPDATE
            try (Connection conn = JdbcUtils.getConnection();
                 PreparedStatement ps = conn.prepareStatement(SQL_UPDATE)) {
                ps.setString(1, ingrediente.getNombre());
                ps.setString(2, ingrediente.getFoto());
                ps.setString(3, ingrediente.getColor());
                ps.setInt(4, ingrediente.getId());
                ps.executeUpdate();
                resultado = ingrediente;
            } catch (Exception e) {
                throw new RuntimeException("Error update ingrediente", e);
            }
        }
        return resultado;
    }

    @Override
    public void deleteById(int id) {
        try (Connection conn = JdbcUtils.getConnection();
             PreparedStatement ps = conn.prepareStatement(SQL_DELETE)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (Exception e) {
            throw new RuntimeException("Error deleteById ingrediente", e);
        }
    }

    @Override
    public boolean existsById(int id) {
        boolean existe = false;
        try (Connection conn = JdbcUtils.getConnection();
             PreparedStatement ps = conn.prepareStatement(SQL_EXISTS)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    existe = true;
                }
            }
        } catch (Exception e) {
            throw new RuntimeException("Error existsById ingrediente", e);
        }
        return existe;
    }

    // Mapea un ResultSet (fila) a un objeto Ingrediente
    private Ingrediente hydrateIngrediente(ResultSet rs) throws SQLException {
        Ingrediente ing = new Ingrediente();
        ing.setId(rs.getInt("id"));
        ing.setNombre(rs.getString("nombre"));
        ing.setFoto(rs.getString("foto"));
        ing.setColor(rs.getString("color"));
        return ing;
    }
}

