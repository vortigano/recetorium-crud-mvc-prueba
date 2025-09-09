package ar.charlycimino.recetorium.controller;

import ar.charlycimino.recetorium.model.Ingrediente;
import ar.charlycimino.recetorium.model.repository.IngredienteRepositoryBinario;
import ar.charlycimino.recetorium.model.repository.IngredienteRepositoryJdbc;
import ar.charlycimino.recetorium.model.service.IngredienteService;
import ar.charlycimino.recetorium.view.Vista;

/**
 *
 * @author Charly Cimino Aprendé más Java en mi canal:
 * https://www.youtube.com/c/CharlyCimino Encontrá más código en mi repo de
 * GitHub: https://github.com/CharlyCimino
 */
public class IngredienteController {

    private final IngredienteService ingredienteService;
    private final Vista vista;

    public IngredienteController(IngredienteService service, Vista vista) {
        this.ingredienteService = service;
        this.vista = vista;
    }

    public void iniciar() {
        try {
            ingredienteService.setRepository(new IngredienteRepositoryJdbc());
            menu();
        } catch (Exception e) {
            vista.mostrarError(e.getMessage());
        }
    }

    private void menu() {
        boolean salir = false;
        while (!salir) {
            try {
                int opcion = vista.mostrarMenu();
                switch (opcion) {
                    case 1: {
                        Ingrediente ing = vista.leerIngrediente();
                        ingredienteService.guardar(ing);
                        vista.mostrarLinea("Ingrediente creado");
                        break;
                    }
                    case 2: {
                        int id = vista.leerId();
                        Ingrediente ing = ingredienteService.buscar(id);
                        if (ing != null) {
                            vista.mostrarIngrediente(ing);
                        } else {
                            vista.mostrarLinea("Ingrediente no encontrado");
                        }
                        break;
                    }
                    case 3:
                        vista.mostrarIngredientes(ingredienteService.listar());
                        break;
                    case 4: {
                        int id = vista.leerId();
                        ingredienteService.eliminar(id);
                        vista.mostrarLinea("Ingrediente eliminado");
                        break;
                    }
                    case 5:
                        salir = true;
                        break;
                    default:
                        vista.mostrarError("Opción inválida");
                        break;
                }
            } catch (Exception e) {
                vista.mostrarError(e.getMessage());
            }
        }
    }
}
