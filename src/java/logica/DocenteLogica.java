/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logica;

import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import modelo.Docente;
import org.apache.commons.codec.digest.DigestUtils;
import persistencia.DocenteFacadeLocal;

/**
 *
 * @author jsnar
 */
@Stateless
public class DocenteLogica implements DocenteLogicaLocal {

    @EJB
    DocenteFacadeLocal docenteDAO;
    
    @Override
    public void registrarDocente(Docente docente) throws Exception {
        if(docente == null){
            throw new Exception("Campos vacíos.");
        }
        else{
            if(docente.getDocumentodocente() == null || docente.getDocumentodocente() == 0){
                throw new Exception("Campo Documento Docente Obligatorio.");
            }
            if(docente.getNombredocente().equals("") || docente.getNombredocente() == null){
                throw new Exception("Campo Nombre Docente Obligatorio.");
            }
            if(docente.getApellidodocente().equals("") || docente.getApellidodocente() == null){
                throw new Exception("Campo Apellido Docente Obligatorio.");
            }
            if(docente.getCorreodocente().equals("") || docente.getCorreodocente() == null){
                throw new Exception("Campo E-mail Docente Obligatorio.");
            }
            if(!docente.getCorreodocente().contains("@") && (!docente.getCorreodocente().endsWith(".com") || !docente.getCorreodocente().endsWith(".es"))){
                throw new Exception("E-mail inválído. Ejemplos válidos: \"example@something.com\" o \"example@something.es\"");
            }
            if(docente.getProfesiondocente().equals("") || docente.getProfesiondocente() == null){
                throw new Exception("Campo Profesión Docente Obligatorio.");
            }
            if(docente.getTelefonodocente().equals("") || docente.getTelefonodocente() == null){
                throw new Exception("Campo Teléfono Obligatorio.");
            }
            if(docente.getClavedocente().equals("") || docente.getClavedocente() == null){
                throw new Exception("Campo Clave Docente Obligatorio");
            }
        }
        
        Docente objDocente = docenteDAO.find(docente.getDocumentodocente());
        if(objDocente != null){
            throw new Exception("Docente ya existe.");
        }
        else{
            String claveEncriptada = DigestUtils.md5Hex(docente.getClavedocente());
            docente.setClavedocente(claveEncriptada);
            docenteDAO.create(docente);
        }
    }

    @Override
    public void modificarDocente(Docente docente) throws Exception {
        if(docente == null){
            throw new Exception("Campos vacíos.");
        }
        else{
            if(docente.getDocumentodocente() == 0 || docente.getDocumentodocente() == null){
                throw new Exception("Campo Documento Docente Obligatorio.");
            }
            if(docente.getNombredocente().equals("") || docente.getNombredocente() == null){
                throw new Exception("Campo Nombre Docente Obligatorio.");
            }
            if(docente.getApellidodocente().equals("") || docente.getApellidodocente() == null){
                throw new Exception("Campo Apellido Docente Obligatorio.");
            }
            if(docente.getCorreodocente().equals("") || docente.getCorreodocente() == null){
                throw new Exception("Campo E-mail Docente Obligatorio.");
            }
            if(!docente.getCorreodocente().contains("@") && (!docente.getCorreodocente().endsWith(".com") || !docente.getCorreodocente().endsWith(".es"))){
                throw new Exception("E-mail inválído. Ejemplos válidos: \"example@something.com\" o \"example@something.es\"");
            }
            
            if(docente.getTelefonodocente().equals("") || docente.getTelefonodocente() == null){
                throw new Exception("Campo Teléfono Obligatorio.");
            }
            
            if(docente.getProfesiondocente().equals("") || docente.getProfesiondocente() == null){
                throw new Exception("Campo Profesión Docente Obligatorio.");
            }
            
        }
        
        Docente objDocente = docenteDAO.find(docente.getDocumentodocente());
        if(objDocente == null){
            throw new Exception("El docente a modificar no existe.");
        }
        else{
            objDocente.setNombredocente(docente.getNombredocente());
            objDocente.setApellidodocente(docente.getApellidodocente());
            objDocente.setCorreodocente(docente.getCorreodocente());
            objDocente.setTelefonodocente(docente.getTelefonodocente());
            objDocente.setProfesiondocente(docente.getProfesiondocente());
            if(!docente.getClavedocente().equals("")) {
                String claveEncriptada = DigestUtils.md5Hex(docente.getClavedocente());
                objDocente.setClavedocente(claveEncriptada);
            }
            docenteDAO.edit(objDocente);
        }
    }

    @Override
    public void eliminarDocente(Docente docente) throws Exception {
        if(docente == null){
            throw new Exception("Campos vacíos.");
        }
        else{
            if(docente.getDocumentodocente() == 0 || docente.getDocumentodocente() == null){
                throw new Exception("El documento de Docente es Obligatorio.");
            }
        }
        
        Docente objDocente = docenteDAO.find(docente.getDocumentodocente());
        if(objDocente == null){
            throw new Exception("El Docente a eliminar no existe.");
        }
        else{
            if(objDocente.getMateriaList().size() > 0){
                throw new Exception("El docente tiene materias asociadas.");
            }
            docenteDAO.remove(docente);
        }
    }

    @Override
    public Docente consultarxcodigo(Integer codigo) throws Exception {
        if(codigo == null || codigo == 0){
            throw new Exception("El Código es Obligatorio");
        }
        else{
            return docenteDAO.find(codigo);
        }
    }

    @Override
    public List<Docente> consultarTodos() throws Exception {
        return docenteDAO.findAll();
    }

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
}
