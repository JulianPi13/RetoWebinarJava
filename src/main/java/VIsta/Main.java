package Vista;

import Controlador.ControladorParticipante;
import Modelo.Clases.Participante;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ControladorParticipante controlador = new ControladorParticipante();
        int opcion;

        do {
            // Mostrar el menú
            System.out.println("\n=================================");
            System.out.println("   MENÚ DE INSCRIPCIÓN WEBINAR   ");
            System.out.println("=================================");
            System.out.println("1. Inscribir participante");
            System.out.println("2. Listar todos los participantes");
            System.out.println("3. Buscar participantes por empresa");
            System.out.println("4. Contar total de participantes");
            System.out.println("5. Eliminar participante por ID");
            System.out.println("6. Salir");
            System.out.print("Seleccione una opción: ");
            
            opcion = scanner.nextInt();
            scanner.nextLine(); 

            switch (opcion) {
                case 1:
                    System.out.println("--- INSCRIBIR PARTICIPANTE ---");
                    System.out.print("Ingrese el nombre: ");
                    String nombre = scanner.nextLine();
                    
                    System.out.print("Ingrese el correo: ");
                    String correo = scanner.nextLine();
                    
                    System.out.print("Ingrese la empresa: ");
                    String empresa = scanner.nextLine();
                    
                    String resultado = controlador.inscribirParticipante(nombre, correo, empresa);
                    System.out.println(resultado);
                    break;

                case 2:
                    System.out.println("--- LISTA DE PARTICIPANTES ---");
                    List<Participante> lista = controlador.listarParticipantes();
                    
                    if (lista.isEmpty()) {
                        System.out.println("No hay participantes inscritos en el sistema.");
                    } else {
                        for (Participante p : lista) {
                            System.out.println(p.toString()); 
                        }
                    }
                    break;

                case 3:
                    System.out.println("--- BUSCAR POR EMPRESA ---");
                    System.out.print("Ingrese el nombre de la empresa: ");
                    String empresaBuscar = scanner.nextLine();
                    
                    List<Participante> listaEmpresa = controlador.buscarPorEmpresa(empresaBuscar);
                    
                    if (listaEmpresa.isEmpty()) {
                        System.out.println("No se encontraron participantes de la empresa: " + empresaBuscar);
                    } else {
                        for (Participante p : listaEmpresa) {
                            System.out.println(p.toString());
                        }
                    }
                    break;

                case 4:
                    System.out.println("--- CONTAR PARTICIPANTES ---");
                    int total = controlador.contarParticipantes();
                    System.out.println("Total de participantes inscritos: " + total);
                    break;

                case 5:
                    System.out.println("--- ELIMINAR PARTICIPANTE ---");
                    System.out.print("Ingrese el ID del participante a eliminar: ");
                    int idEliminar = scanner.nextInt();
                    scanner.nextLine(); 
                    
                    String resEliminar = controlador.eliminarPorId(idEliminar);
                    System.out.println(resEliminar);
                    break;

                case 6:
                    System.out.println("Saliendo de la aplicación. ¡Hasta luego!");
                    break;

                default:
                    System.out.println("Opción no válida. Por favor, intente de nuevo.");
            }
            
        } while (opcion != 6); 
        
        scanner.close();
    }
}                                                                                                                        