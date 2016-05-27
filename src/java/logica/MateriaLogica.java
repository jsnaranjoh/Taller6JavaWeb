/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logica;

import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import modelo.Materia;
import persistencia.MateriaFacadeLocal;

/**
 *
 * @author jsnar
 */
@Stateless
public class MateriaLogica implements MateriaLogicaLocal {

    @EJB
    MateriaFacadeLocal materiaDAO;
    
    @Override
    public void registrarMateria(Materia materia) throws Exception {
        if(materia == null){
            throw new Exception("Campos vacíos.");   
        }
        else{
            if(materia.getNumeromateria()== null){
                throw new Exception("El número de la Materia es Obligatorio.");
            }
            if(materia.getNombremateria().equals("") || materia.getNombremateria() == null){
                throw new Exception("El nombre de la Materia es Obligatorio.");
            }
            if(materia.getCreditosmateria() == null || materia.getCreditosmateria() == 0){
                throw new Exception("Toda materia tiene al menos 1 crédito.");
            }
            if(materia.getNumerocarrera() == null || materia.getNumerocarrera().getNumerocarrera() == 0){
                throw new Exception("Toda materia debe pertenecer a una carrera.");
            }
        }
        
        Materia objMateria = materiaDAO.find(materia.getNumeromateria());
        if(objMateria != null){
            throw new Exception("Materia ya existe.");
        }
        else{
            materiaDAO.create(materia);
        }
    }

    @Override
    public void modificarMateria(Materia materia) throws Exception {
        if(materia==null){
            throw new Exception("Campos Vacios.");
        }else{
            if(materia.getNumeromateria()==null || materia.getNumeromateria()==0){
                throw new Exception("El número de la Materia es Obligatorio.");
            }
            if(materia.getNombremateria().equals("") || materia.getNombremateria().equals("")){
                throw new Exception("El nombre de la Materia es Obligatorio.");
            }
            if(materia.getCreditosmateria() == 0 || materia.getCreditosmateria() == null){
                throw new Exception("Toda materia tiene al menos 1 crédito.");
            }
            if(materia.getNumerocarrera() == null){
                throw new Exception("Toda materia debe pertenecer a una carrera.");
            }
        }
        
        Materia objMateria = materiaDAO.find(materia.getNumeromateria());
        if(objMateria == null){
            throw new Exception("La materia a Modificar No Existe.");
        }else{
            objMateria.setNombremateria(materia.getNombremateria());
            objMateria.setCreditosmateria(materia.getCreditosmateria());
            objMateria.setNumerocarrera(materia.getNumerocarrera());
            materiaDAO.edit(objMateria);
        }        
    }

    @Override
    public void eliminarMateria(Materia materia) throws Exception {
        if(materia == null){
            throw new Exception("Campos vacíos.");
        }
        else{
            if(materia.getNumeromateria()==null || materia.getNumeromateria()==0){
                throw new Exception("El número de la Materia es Obligatorio.");
            }            
        }
        
        Materia objMateria = materiaDAO.find(materia.getNumeromateria());
        if(objMateria == null){
            throw new Exception("La materia a eliminar no existe.");
        }
        else{
            if(objMateria.getDocenteList().size() > 0){
                throw new Exception("La materia tiene docentes asociados.");
            }
            if(objMateria.getMatriculaList().size() > 0){
                throw new Exception("La materia tiene matrículas asociadas.");
            }
            materiaDAO.remove(materia);
        }
    }

    @Override
    public Materia consultarxCodigo(Integer codigo) throws Exception {
        if(codigo==null || codigo==0){
            throw new Exception("El código es Obligatorio.");
        }else{
            return materiaDAO.find(codigo);
        }        
    }

    @Override
    public List<Materia> consultarTodas() throws Exception {
        return materiaDAO.findAll();
    }

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
}
