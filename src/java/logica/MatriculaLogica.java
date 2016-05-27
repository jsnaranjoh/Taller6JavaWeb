/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logica;

import java.io.File;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import jxl.Sheet;
import jxl.Workbook;
import modelo.Estudiante;
import modelo.Materia;
import modelo.Matricula;
import modelo.MatriculaPK;
import persistencia.EstudianteFacadeLocal;
import persistencia.MateriaFacadeLocal;
import persistencia.MatriculaFacadeLocal;

/**
 *
 * @author jsnar
 */
@Stateless
public class MatriculaLogica implements MatriculaLogicaLocal {

    @EJB
    MatriculaFacadeLocal matriculaDAO;
    
    @EJB
    EstudianteFacadeLocal estudianteDAO;
    
    @EJB
    MateriaFacadeLocal materiaDAO;
    
    @Override
    public void registrarMatricula(Matricula matricula) throws Exception {
        if(matricula == null){
            throw new Exception("Campos vacíos.");
        }
        else{
            if(matricula.getEstudiante().getDocumentoestudiante() == null){
                throw new Exception("No se ha seleccionado ningún Estudiante.");
            }
            if(matricula.getMateria().getNumeromateria() == null){
                throw new Exception("No se ha seleccionado ninguna Materia.");
            }
            if(matricula.getNota() == null){
                throw new Exception("Campo Nota Obligatorio.");
            }
            if(matricula.getEstado() == null || matricula.getEstado().equals("0")){
                throw new Exception("Campo Estado Obligatorio.");
            }
        }
        
        Matricula objMatricula = matriculaDAO.find(matricula.getMatriculaPK());
        if(objMatricula != null){
            throw new Exception("Matrícula ya existe.");
        }
        else{
            matriculaDAO.create(matricula);
        }
    }

    @Override
    public void modificarMatricula(Matricula matricula) throws Exception {
                if(matricula == null){
            throw new Exception("Campos vacíos.");
        }
        else{
            if(matricula.getEstudiante() == null){
                throw new Exception("No se ha seleccionado ningún Estudiante.");
            }
            if(matricula.getMateria() == null){
                throw new Exception("No se ha seleccionado ninguna Materia.");
            }
            if(matricula.getNota() == null){
                throw new Exception("Campo Nota Obligatorio.");
            }
            if(matricula.getEstado().equals("") || matricula.getEstado() == null){
                throw new Exception("Campo Estado Obligatorio.");
            }
        }
        
        Matricula objMatricula = matriculaDAO.find(matricula.getMatriculaPK());
        if(objMatricula == null){
            throw new Exception("La Matrícula a modificar no existe.");
        }
        else{
            objMatricula.setNota(matricula.getNota());
            objMatricula.setEstado(matricula.getEstado());
            matriculaDAO.edit(matricula);
        }
    }

    @Override
    public void eliminarMatricula(Matricula matricula) throws Exception {
        Matricula objMatricula = matriculaDAO.find(matricula.getMatriculaPK());
        
        if(objMatricula == null){
            throw new Exception("La Matrícula a eliminar no existe.");
        }
        else{
            matriculaDAO.remove(matricula);
        }
    }

    @Override
    public Matricula consultarxCodigo(Integer codigo) throws Exception {
        if(codigo == 0 || codigo == null){
            throw new Exception("El código es Obligatorio.");
        }
        else{
            return matriculaDAO.find(codigo);
        }
    }

    @Override
    public List<Matricula> consultarTodas() throws Exception {
        return matriculaDAO.findAll();
    }

    @Override
    public String importarMatriculas(String archivo) throws Exception {
        Workbook archivoExcel = Workbook.getWorkbook(new File(archivo));
        //Se carga la primera hoja
        Sheet hoja = archivoExcel.getSheet(0);
        int numFilas = hoja.getRows();

        Integer matriculasInsertadas = 0;
        Integer matriculasExistentes = 0;

        for(int fila=1; fila<numFilas; fila++) {
            Estudiante e = estudianteDAO.find(Long.parseLong(hoja.getCell(0, fila).getContents()));
            if(e == null) {
                throw new Exception("El estudiante no está registrado.");
            }
            
            Materia m = materiaDAO.find(Integer.parseInt(hoja.getCell(1, fila).getContents()));
            if(m == null) {
                throw new Exception("La materia no existe.");
            }
            
            MatriculaPK matriculaPK = new MatriculaPK(e.getDocumentoestudiante(), m.getNumeromateria());
            
            Matricula matricula = new Matricula();
            matricula.setMatriculaPK(matriculaPK);
            matricula.setEstudiante(e);
            matricula.setMateria(m);
            matricula.setNota(0.);
            matricula.setEstado("ACTIVO");

            if(matriculaDAO.find(matriculaPK) == null) {
                matriculaDAO.create(matricula);
                matriculasInsertadas++;
            } else {
                matriculasExistentes++;
            }
        }
        return "Se importaron " + matriculasInsertadas + " matrículas, ya existían " + matriculasExistentes + " matrículas.";
    }
}
