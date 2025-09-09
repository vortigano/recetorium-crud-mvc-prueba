package ar.charlycimino.recetorium.model.repository;

import ar.charlycimino.recetorium.model.Ingrediente;
import ar.charlycimino.recetorium.model.db.JdbcUtils;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Alumno
 */
public class IngredienteRepositoryJdbc implements IngredienteRepository{

    @Override
    public List<Ingrediente> findAll() {
        List<Ingrediente> lista = new ArrayList<>();
        System.out.println("YES");
        try (
             Connection con = JdbcUtils.getConnection();
             PreparedStatement ps = con.prepareStatement("SELECT * FROM ingrediente");
             ResultSet rs = ps.executeQuery();
            )
        {
            while(rs.next())
            {
                Ingrediente ing  = rsToIngrediente(rs);
                lista.add(ing);
            }
        }catch (Exception e) {
            //se captura la excepción y se relanza de tipo runtime exception
            throw new RuntimeException(e.getMessage(), e);
        }
        return lista;
    }
    
    //Mapea un ResoultSet (fila) a un objeto Ingrediente
    private Ingrediente rsToIngrediente(ResultSet rs) throws SQLException {
        Ingrediente ing = new Ingrediente();
        ing.setId(rs.getInt("id"));
        ing.setNombre(rs.getString("nombre"));
        ing.setFoto(rs.getString("foto"));
        //ing.setColor(rs.getColor("color"));
        return ing;
    }

    @Override
    public Ingrediente findById(int id) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public Ingrediente save(Ingrediente ingrediente) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void deleteById(int id) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public boolean existsById(int id) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
    
}
