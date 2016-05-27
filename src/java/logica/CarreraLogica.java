/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logica;

import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import modelo.Carrera;
import persistencia.CarreraFacadeLocal;

/**
 *
 * @author jsnar
 */
@Stateless
public class CarreraLogica implements CarreraLogicaLocal {
    @EJB
    CarreraFacadeLocal carreraDAO;
    
    @Override
    public void registrarCarrera(Carrera carrera) throws Exception {
        if(carrera==null){
            throw new Exception("Campos Vacios.");
        }else{
            if(carrera.getNumerocarrera()==null || carrera.getNumerocarrera()==0){
                throw new Exception("El número de la Carrera es Obligatorio.");
            }
            if(carrera.getNombrecarrera().equals("") || carrera.getNombrecarrera().equals("")){
                throw new Exception("El nombre de la Carrera es Obligatorio.");
            }
        }
        
        Carrera objCarrera = carreraDAO.find(carrera.getNumerocarrera());
        if(objCarrera!=null){
            throw new Exception("La carrera Ya Existe.");
        }else{
            carreraDAO.create(carrera);
        }
    }

    @Override
    public void modificarCarrera(Carrera carrera) throws Exception {
        if(carrera==null){
            throw new Exception("Campos Vacios.");
        }else{
            if(carrera.getNumerocarrera()==null || carrera.getNumerocarrera()==0){
                throw new Exception("El número de la Carrera es Obligatorio.");
            }
            if(carrera.getNombrecarrera().equals("") || carrera.getNombrecarrera().equals("")){
                throw new Exception("El nombre de la Carrera es Obligatorio.");
            }
        }
        
        Carrera objCarrera = carreraDAO.find(carrera.getNumerocarrera());
        if(objCarrera==null){
            throw new Exception("La carrera a Modificar No Existe.");
        }else{
            objCarrera.setNombrecarrera(carrera.getNombrecarrera());
            carreraDAO.edit(objCarrera);
        }
    }

    @Override
    public void eliminarCarrera(Carrera carrera) throws Exception {
        if(carrera==null){
            throw new Exception("Campos Vacios.");
        }else{
            if(carrera.getNumerocarrera()==null || carrera.getNumerocarrera()==0){
                throw new Exception("El número de la Carrera es Obligatorio.");
            }
        }
        
        Carrera objCarrera = carreraDAO.find(carrera.getNumerocarrera());
        if(objCarrera==null){
            throw new Exception("La carrera a Eliminar No Existe.");
        }else{
            if(objCarrera.getMateriaList().size()>0){
                throw new Exception("La carrera tiene materias Asociadas.");
            }
            carreraDAO.remove(carrera);
        }
    }

    @Override
    public Carrera consultarxCodigo(Integer codigo) throws Exception {
        if(codigo==null || codigo==0){
            throw new Exception("El código es Obligatorio.");
        }else{
            return carreraDAO.find(codigo);
        }
    }

    @Override
    public List<Carrera> consultarTodas() throws Exception {
        return carreraDAO.findAll();
    }

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
}
