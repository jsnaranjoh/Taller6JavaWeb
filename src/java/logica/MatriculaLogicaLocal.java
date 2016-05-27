/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logica;

import java.util.List;
import javax.ejb.Local;
import modelo.Matricula;

/**
 *
 * @author jsnar
 */
@Local
public interface MatriculaLogicaLocal {
    public void registrarMatricula(Matricula matricula) throws Exception;
    public void modificarMatricula(Matricula matricula) throws Exception;
    public void eliminarMatricula(Matricula matricula) throws Exception;
    public Matricula consultarxCodigo (Integer codigo) throws Exception;
    public List<Matricula> consultarTodas() throws Exception;
    public String importarMatriculas(String archivo) throws Exception;
}
