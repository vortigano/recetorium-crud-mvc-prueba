
package ar.charlycimino.recetorium.view;

import ar.charlycimino.recetorium.model.Ingrediente;
import java.util.List;
import java.util.Scanner;

/**
 *
 * @author Charly Cimino
 * Aprendé más Java en mi canal: https://www.youtube.com/c/CharlyCimino
 * Encontrá más código en mi repo de GitHub: https://github.com/CharlyCimino
 */
public class Vista {
    private final Scanner input;
 
    public Vista() {
        input = new Scanner(System.in);
    }   

    public int mostrarMenu() {
        System.out.println("\n--- Menú Ingredientes ---");
        System.out.println("1. Crear ingrediente");
        System.out.println("2. Ver ingrediente por ID");
        System.out.println("3. Listar ingredientes");
        System.out.println("4. Eliminar ingrediente");
        System.out.println("5. Salir");
        return leerEntero("Opción: ");
    }

    public Ingrediente leerIngrediente() {
        int id = leerId();
        System.out.print("Nombre: ");
        String nombre = input.nextLine();
        System.out.print("Foto (ruta/nombre): ");
        String foto = input.nextLine();
        System.out.print("Color: ");
        String color = input.nextLine();
        return new Ingrediente(id, nombre, foto, color);
    }
    
    public int leerId() {
        return leerEntero("Ingrese ID: ");
    }

    public int leerEntero(String msg) {
        System.out.print(msg);
        return Integer.parseInt(input.nextLine());
    }

    public void mostrarIngredientes(List<Ingrediente> ingredientes) {
        for (Ingrediente i : ingredientes) {
            mostrarIngrediente(i);
        }
    }
    
    public void mostrarIngrediente(Ingrediente ingrediente) {
        mostrarLinea(ingrediente.toString());
    }

    public void mostrarLinea(String msg) {
        System.out.println(msg);
    }
    
    public void mostrarError(String msg) {
        System.err.println(msg);
    }
}
