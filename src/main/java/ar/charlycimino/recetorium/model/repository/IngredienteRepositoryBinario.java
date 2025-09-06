
package ar.charlycimino.recetorium.model.repository;

import ar.charlycimino.recetorium.model.Ingrediente;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Charly Cimino
 * Aprendé más Java en mi canal: https://www.youtube.com/c/CharlyCimino
 * Encontrá más código en mi repo de GitHub: https://github.com/CharlyCimino
 */
public class IngredienteRepositoryBinario implements IngredienteRepository {
    
    private String FILENAME = "ingredientes.dat";
    private File file;
    
    public IngredienteRepositoryBinario() {        
        this.file = new File(FILENAME);
        if (!file.exists()) {
            throw new RuntimeException("No existe el archivo " + FILENAME);
        }
    }

    private List<Ingrediente> loadFromFile() {
        List<Ingrediente> ingredientes;
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            ingredientes = (List<Ingrediente>) ois.readObject();
        } catch (EOFException e) {
            ingredientes = new ArrayList<>();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("loadFromFile " + e.getMessage());
        }
        return ingredientes;
    }

    private void saveToFile(List<Ingrediente> ingredientes) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file))) {
            oos.writeObject(ingredientes);
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public List<Ingrediente> findAll() {
        return loadFromFile();
    }

    @Override
    public Ingrediente findById(int id) {
        List<Ingrediente> ingredientes = loadFromFile();
        Ingrediente encontrado = null;
        int i = 0;
        while(i < ingredientes.size() && ingredientes.get(i).getId() != id) {
            i++;
        }
        if (i < ingredientes.size()) {
            encontrado = ingredientes.get(i);
        }
        return encontrado;
    }

    @Override
    public Ingrediente save(Ingrediente ingrediente) {
        if (existsById(ingrediente.getId())) {
          //  throw new RuntimeException("Ya existe ingrediente con id " + ingrediente.getId());
          updateById(ingrediente.getId(), ingrediente);
        }
        else
        {
          List<Ingrediente> ingredientes = loadFromFile();
          ingredientes.add(ingrediente);
          saveToFile(ingredientes);
        }
        return ingrediente;
    }
    
    @Override
    public void deleteById(int id) {
        Ingrediente encontrado = findById(id);
        if (encontrado != null) {
            List<Ingrediente> ingredientes = loadFromFile();
            ingredientes.remove(encontrado);
            saveToFile(ingredientes);
        }
    }

    @Override
    public boolean existsById(int id) {
        return findById(id) != null;
    }
    
    private Ingrediente updateById(int id, Ingrediente ingrediente)
    {
      Ingrediente encontrado = findById(id);
      if (encontrado != null) {
        List<Ingrediente> ingredientes = loadFromFile();
        int indice = ingredientes.indexOf(encontrado);
        ingredientes.set(indice, ingrediente);
        saveToFile(ingredientes);
      }
      return encontrado;
    }
}
