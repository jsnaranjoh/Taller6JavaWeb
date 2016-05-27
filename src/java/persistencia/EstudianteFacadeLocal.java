/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package persistencia;

import java.util.List;
import javax.ejb.Local;
import modelo.Estudiante;

/**
 *
 * @author DILOVE
 */
@Local
public interface EstudianteFacadeLocal {

    void create(Estudiante estudiante);

    void edit(Estudiante estudiante);

    void remove(Estudiante estudiante);

    Estudiante find(Object id);

    List<Estudiante> findAll();

    List<Estudiante> findRange(int[] range);

    int count();
    
    List<Estudiante> findxsemester(Integer s);
    
    Estudiante consultarxDocumento(Long documento);
    
    List<Estudiante> consultarxMateria(Integer codM);
}
