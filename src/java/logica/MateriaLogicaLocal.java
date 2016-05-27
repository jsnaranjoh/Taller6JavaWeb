/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logica;

import java.util.List;
import javax.ejb.Local;
import modelo.Materia;

/**
 *
 * @author jsnar
 */
@Local
public interface MateriaLogicaLocal {
    public void registrarMateria(Materia materia) throws Exception;
    public void modificarMateria(Materia materia) throws Exception;
    public void eliminarMateria(Materia materia) throws Exception;
    public Materia consultarxCodigo(Integer codigo) throws Exception;
    public List<Materia> consultarTodas() throws Exception;
}
