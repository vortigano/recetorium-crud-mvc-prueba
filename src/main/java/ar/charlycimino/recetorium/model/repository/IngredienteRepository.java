
package ar.charlycimino.recetorium.model.repository;

/**
 *
 * @author Charly Cimino
 * Aprendé más Java en mi canal: https://www.youtube.com/c/CharlyCimino
 * Encontrá más código en mi repo de GitHub: https://github.com/CharlyCimino
 */
import ar.charlycimino.recetorium.model.Ingrediente;
import java.util.List;

public interface IngredienteRepository {
    List<Ingrediente> findAll();
    Ingrediente findById(int id);
    Ingrediente save(Ingrediente ingrediente);
    void deleteById(int id);
    boolean existsById(int id);
}
