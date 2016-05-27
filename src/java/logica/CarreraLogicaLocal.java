/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logica;

import java.util.List;
import javax.ejb.Local;
import modelo.Carrera;

/**
 *
 * @author jsnar
 */
@Local
public interface CarreraLogicaLocal {
    public void registrarCarrera(Carrera carrera) throws Exception;
    public void modificarCarrera(Carrera carrera) throws Exception;
    public void eliminarCarrera(Carrera carrera) throws Exception;
    public Carrera consultarxCodigo (Integer codigo) throws Exception;
    public List<Carrera> consultarTodas() throws Exception;
}
