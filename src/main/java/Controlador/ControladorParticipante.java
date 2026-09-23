package Controlador;

import Modelo.Clases.Participante;
import Modelo.Persistencia.Operaciones;
import java.util.List;

public class ControladorParticipante {

    // 1. Instanciamos la clase de Operaciones (el motor de la base de datos)
    private Operaciones operaciones;

    // 2. Constructor: al crear el controlador, inicializamos las operaciones
    public ControladorParticipante() {
        this.operaciones = new Operaciones();
    }

    // ============================================
    // MÉTODO 1: INSCRIBIR (Con validación de campos vacíos)
    // ============================================
    public String inscribirParticipante(String nombre, String correo, String empresa) {
        if (nombre == null || nombre.trim().isEmpty() ||
            correo == null || correo.trim().isEmpty() ||
            empresa == null || empresa.trim().isEmpty()) {
            return "Error: El nombre, el correo y la empresa son campos obligatorios.";
        }
        Participante nuevoParticipante = new Participante(0, nombre, correo, empresa);
        return operaciones.inscribirParticipante(nuevoParticipante);
    }

    // ============================================
    // MÉTODO 2: LISTAR TODOS
    // ============================================
    public List<Participante> listarParticipantes() {
        return operaciones.listarParticipantes();
    }

    // ============================================
    // MÉTODO 3: BUSCAR POR EMPRESA
    // ============================================
    public List<Participante> buscarPorEmpresa(String empresa) {
        if (empresa == null || empresa.trim().isEmpty()) {
            return null;
        }
        return operaciones.buscarPorEmpresa(empresa);
    }

    // ============================================
    // MÉTODO 4: CONTAR
    // ============================================
    public int contarParticipantes() {
        return operaciones.contarParticipantes();
    }

    // ============================================
    // MÉTODO 5: ELIMINAR POR ID
    // ============================================
    public String eliminarPorId(int id) {
        if (id <= 0) {
            return "Error: El ID debe ser un número mayor a 0.";
        }
        return operaciones.eliminarPorId(id);
    }
}