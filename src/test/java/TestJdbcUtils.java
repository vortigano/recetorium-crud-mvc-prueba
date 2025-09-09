
import ar.charlycimino.recetorium.model.db.JdbcUtils;
import java.sql.Connection;

/**
 *
 * @author Alumno
 */
public class TestJdbcUtils {
    public static void main(String[] args) {
        try (Connection con = JdbcUtils.getConnection()) {
            System.out.println("Conexion establecida");
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }
}
