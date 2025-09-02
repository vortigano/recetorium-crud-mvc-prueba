/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package ar.charlycimino.recetorium;

import ar.charlycimino.recetorium.controller.IngredienteController;
import ar.charlycimino.recetorium.model.repository.IngredienteRepository;
import ar.charlycimino.recetorium.model.repository.IngredienteRepositoryBinario;
import ar.charlycimino.recetorium.model.service.IngredienteService;
import ar.charlycimino.recetorium.view.Vista;

/**
 *
 * @author Charly Cimino
 */
public class Main {

    public static void main(String[] args) {
        IngredienteRepository repo = new IngredienteRepositoryBinario();
        IngredienteService service = new IngredienteService(repo);
        Vista vista = new Vista();
        IngredienteController controller = new IngredienteController(service, vista);
        controller.iniciar();
    }
}
