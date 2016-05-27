/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logica;

import java.io.File;
import java.util.List;
import java.util.Random;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import jxl.Sheet;
import jxl.Workbook;
import modelo.Estudiante;
import org.apache.commons.codec.digest.DigestUtils;
import persistencia.EstudianteFacadeLocal;

/**
 *
 * @author jsnar
 */
@Stateless
public class EstudianteLogica implements EstudianteLogicaLocal {

    @EJB
    EstudianteFacadeLocal estudianteDAO;
    
    @Override
    public void registrarEstudiante(Estudiante estudiante) throws Exception {
        if(estudiante == null){
            throw new Exception("Campos vacíos.");
        }
        else{
            if(estudiante.getDocumentoestudiante() == null || estudiante.getDocumentoestudiante() == 0){
                throw new Exception("Campo Documento Estudiante Obligatorio.");
            }
            if(estudiante.getNombreestudiante().equals("") || estudiante.getNombreestudiante() == null){
                throw new Exception("Campo Nombre Estudiante Obligatorio.");
            }
            if(estudiante.getApellidoestudiante().equals("") || estudiante.getApellidoestudiante() == null){
                throw new Exception("Campo Apellido Estudiante Obligatorio.");
            }
            if(estudiante.getCorreoestudiante().equals("") || estudiante.getCorreoestudiante() == null){
                throw new Exception("Campo E-mail Estudiante Obligatorio.");
            }
            if(!estudiante.getCorreoestudiante().contains("@") && 
                    (!estudiante.getCorreoestudiante().endsWith(".com") || !estudiante.getCorreoestudiante().endsWith(".es"))){
                throw new Exception("E-mail inválído. Ejemplos válidos: \"example@something.com\" o \"example@something.es\"");
            }
            if(estudiante.getSemestreestudiante() == null || estudiante.getSemestreestudiante() == 0){
                throw new Exception("Campo Semestre Estudiante Obligatorio.");
            }
            if(estudiante.getClaveestudiante().equals("") || estudiante.getClaveestudiante() == null){
                throw new Exception("Campo Clave Estudiante Obligatorio.");
            }
        }
        
        Estudiante objEstudiante = estudianteDAO.find(estudiante.getDocumentoestudiante());
        if(objEstudiante != null){
            throw new Exception("Estudiante ya existe.");
        }
        else{
            String claveEncriptada = this.encriptarClave(estudiante.getClaveestudiante());
            estudiante.setClaveestudiante(claveEncriptada);
            estudianteDAO.create(estudiante);
        }
    }

    @Override
    public void modificarEstudiante(Estudiante estudiante) throws Exception {
        if(estudiante == null){
            throw new Exception("Campos vacíos.");
        }
        else{
            if(estudiante.getDocumentoestudiante() == 0 || estudiante.getDocumentoestudiante() == null){
                throw new Exception("Campo Documento Estudiante Obligatorio.");
            }
            if(estudiante.getNombreestudiante().equals("") || estudiante.getNombreestudiante() == null){
                throw new Exception("Campo Nombre Estudiante Obligatorio.");
            }
            if(estudiante.getApellidoestudiante().equals("") || estudiante.getApellidoestudiante() == null){
                throw new Exception("Campo Apellido Estudiante Obligatorio.");
            }
            if(estudiante.getCorreoestudiante().equals("") || estudiante.getCorreoestudiante() == null){
                throw new Exception("Campo E-mail Estudiante Obligatorio.");
            }
            if(!estudiante.getCorreoestudiante().contains("@") && 
                    (!estudiante.getCorreoestudiante().endsWith(".com") || !estudiante.getCorreoestudiante().endsWith(".es"))){
                throw new Exception("E-mail inválído. Ejemplos válidos: \"example@something.com\" o \"example@something.es\"");
            }
            if(estudiante.getSemestreestudiante() == 0 || estudiante.getSemestreestudiante() == null){
                throw new Exception("Campo Semestre Estudiante Obligatorio.");
            }
            
        }
        
        Estudiante objEstudiante = estudianteDAO.find(estudiante.getDocumentoestudiante());
        if(objEstudiante == null){
            throw new Exception("Estudiante a modificar no existe.");
        }
        else{
            objEstudiante.setNombreestudiante(estudiante.getNombreestudiante());
            objEstudiante.setApellidoestudiante(estudiante.getApellidoestudiante());
            objEstudiante.setCorreoestudiante(estudiante.getCorreoestudiante());
            objEstudiante.setSemestreestudiante(estudiante.getSemestreestudiante());
            if(!estudiante.getClaveestudiante().equals("")) {
                String claveEncriptada = this.encriptarClave(estudiante.getClaveestudiante());
                objEstudiante.setClaveestudiante(claveEncriptada);
            }
            estudianteDAO.edit(objEstudiante);
        }
    }

    @Override
    public void eliminarEstudiante(Estudiante estudiante) throws Exception {
        if(estudiante == null){
            throw new Exception("Campos vacíos.");
        }
        else{
            if(estudiante.getDocumentoestudiante() == 0 || estudiante.getDocumentoestudiante() == null){
                throw new Exception("El Documento de Estudiante es Obligatorio.");
            }
        }
        
        Estudiante objEstudiante = estudianteDAO.find(estudiante.getDocumentoestudiante());
        if(objEstudiante == null){
            throw new Exception("El Estudiante a eliminar no existe.");
        }
        else{
            if(objEstudiante.getMatriculaList().size() > 0){
                throw new Exception("El Estudiante tiene matrículas asociadas.");
            }
            estudianteDAO.remove(estudiante);
        }
    }

    @Override
    public Estudiante consultarxcodigo(Integer codigo) throws Exception {
        if(codigo==null || codigo==0){
            throw new Exception("El código es Obligatorio.");
        }else{
            return estudianteDAO.find(codigo);
        }
    }

    @Override
    public List<Estudiante> consultarTodos() throws Exception {
        return estudianteDAO.findAll();
    }

    @Override
    public String importarEstudiantes(String archivo) throws Exception {
        Workbook archivoExcel = Workbook.getWorkbook(new File(archivo));
        Sheet hoja = archivoExcel.getSheet(0);
        int numFilas = hoja.getRows();
        
        Integer estudiantesRegistrados = 0;
        Integer estudiantesExistentes = 0;
        
        for(int fila = 1; fila < numFilas; fila++){
            Estudiante estudiante = new Estudiante();
            
            estudiante.setDocumentoestudiante(Long.parseLong(hoja.getCell(0, fila).getContents()));
            estudiante.setNombreestudiante(hoja.getCell(1, fila).getContents());
            estudiante.setApellidoestudiante(hoja.getCell(2, fila).getContents());
            estudiante.setCorreoestudiante(hoja.getCell(3, fila).getContents());
            estudiante.setSemestreestudiante(Integer.parseInt(hoja.getCell(4, fila).getContents()));
            estudiante.setClaveestudiante(this.encriptarClave(this.obtenerClaveAleatoria()));
            
            if(estudianteDAO.find(estudiante.getDocumentoestudiante()) == null){
                estudianteDAO.create(estudiante);
                estudiantesRegistrados++;
            }
            else{
                estudiantesExistentes++;
            }
        }
        return "Se importaron " + estudiantesRegistrados + " estudiantes, ya existían " + estudiantesExistentes + " estudiantes.";
    }

    //Obtención de una clava aleatoria para el estudiante
    @Override
    public String obtenerClaveAleatoria() {
        char[] nums = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9'};
        char[] charKeys = new char[6];
        String clave;

        //Armo la clave de 6 dígitos
        Random r = new Random();
        for(int i=0; i<charKeys.length; i++) {
            char caracter = nums[r.nextInt(nums.length)];
            charKeys[i] = caracter;
        }
        
        clave = new String(charKeys);
        return clave;
    }

    @Override
    public String encriptarClave(String clave) throws Exception {
        String claveEncriptada = DigestUtils.md5Hex(clave);
        return claveEncriptada;
    }
}
