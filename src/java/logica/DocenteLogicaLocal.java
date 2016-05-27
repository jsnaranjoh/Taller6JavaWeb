/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logica;

import java.util.List;
import javax.ejb.Local;
import modelo.Docente;

/**
 *
 * @author jsnar
 */
@Local
public interface DocenteLogicaLocal {
    public void registrarDocente(Docente docente) throws Exception;
    public void modificarDocente(Docente docente) throws Exception;
    public void eliminarDocente(Docente docente) throws Exception;
    public Docente consultarxcodigo(Integer codigo) throws Exception;
    public List<Docente> consultarTodos() throws Exception;
}
