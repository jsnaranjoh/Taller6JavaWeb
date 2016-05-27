/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logica;

import java.util.List;
import javax.ejb.Local;
import modelo.Estudiante;

/**
 *
 * @author jsnar
 */
@Local
public interface EstudianteLogicaLocal {
    public void registrarEstudiante(Estudiante estudiante) throws Exception;
    public void modificarEstudiante(Estudiante estudiante) throws Exception;
    public void eliminarEstudiante(Estudiante estudiante) throws Exception;
    public Estudiante consultarxcodigo(Integer codigo) throws Exception;
    public List<Estudiante> consultarTodos() throws Exception;
    public String importarEstudiantes(String archivo) throws Exception;
    public String obtenerClaveAleatoria() throws Exception;
    public String encriptarClave(String clave) throws Exception;
}
