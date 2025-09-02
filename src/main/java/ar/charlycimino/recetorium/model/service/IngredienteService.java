
package ar.charlycimino.recetorium.model.service;

import ar.charlycimino.recetorium.model.Ingrediente;
import ar.charlycimino.recetorium.model.repository.IngredienteRepository;
import java.util.List;

/**
 *
 * @author Charly Cimino
 * Aprendé más Java en mi canal: https://www.youtube.com/c/CharlyCimino
 * Encontrá más código en mi repo de GitHub: https://github.com/CharlyCimino
 */
public class IngredienteService {
    private IngredienteRepository repository;

    public IngredienteService(IngredienteRepository repository) {
        this.repository = repository;
    }

    public IngredienteService() {
    }

    public void setRepository(IngredienteRepository repository) {
        this.repository = repository;
    }

    public List<Ingrediente> listar() {
        return repository.findAll();
    }

    public Ingrediente buscar(int id) {
        return repository.findById(id);
    }

    public Ingrediente guardar(Ingrediente ingrediente) {
        return repository.save(ingrediente);
    }

    public void eliminar(int id) {
        repository.deleteById(id);
    }

    public boolean existe(int id) {
        return repository.existsById(id);
    }
}
