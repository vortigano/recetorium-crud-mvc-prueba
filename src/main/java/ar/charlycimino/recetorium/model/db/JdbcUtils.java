package ar.charlycimino.recetorium.model.db;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

/**
 * Clase utilitaria para obtener conexiones JDBC.
 * Lee la configuración desde src/main/resources/db.properties.
 *
 * Enseña:
 *  - no hardcodear credenciales en el código
 *  - centralizar la gestión de conexión (fácil de cambiar por un pool más adelante)
 */
public final class JdbcUtils {

    private static final String PROPS = "db.properties"; // prueba
    private static String url;
    private static String user;
    private static String password;

    static {
        try (InputStream is = JdbcUtils.class.getClassLoader().getResourceAsStream(PROPS)) {
            Properties p = new Properties();
            if (is == null) {
                throw new RuntimeException("No se encontró " + PROPS + " en resources. Crea src/main/resources/" + PROPS);
            }
            p.load(is);
            url = p.getProperty("jdbc.url");
            user = p.getProperty("jdbc.user");
            password = p.getProperty("jdbc.password");
            // no hacemos nada más aquí; DriverManager cargará driver automáticamente si está en classpath
        } catch (Exception e) {
            throw new RuntimeException("Error cargando configuración JDBC: " + e.getMessage(), e);
        }
    }

    private JdbcUtils() { /* util */ }

    /**
     * Devuelve una nueva conexión. El llamador debe cerrarla (try-with-resources).
     * En clase: explicar que en producción preferimos un DataSource con pool.
     */
    public static Connection getConnection() throws Exception {
        return DriverManager.getConnection(url, user, password);
    }
}
