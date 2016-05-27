/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vista;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.servlet.ServletContext;
import logica.EstudianteLogicaLocal;
import logica.MateriaLogicaLocal;
import logica.MatriculaLogicaLocal;
import modelo.Estudiante;
import modelo.Materia;
import modelo.Matricula;
import modelo.MatriculaPK;
import org.primefaces.component.commandbutton.CommandButton;
import org.primefaces.component.inputtext.InputText;
import org.primefaces.component.selectonemenu.SelectOneMenu;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.event.SelectEvent;

/**
 *
 * @author jsnar
 */
@Named(value = "matriculaVista")
@RequestScoped
public class MatriculaVista {

    private SelectOneMenu cmbEstudiantes;
    private ArrayList<SelectItem> itemsEstudiantes;
    
    private SelectOneMenu cmbMaterias;
    private ArrayList<SelectItem> itemsMaterias;
    
    private InputText txtNota;
    
    private SelectOneMenu cmbEstados;
    
    private CommandButton btnRegistrar;
    private CommandButton btnModificar;
    private CommandButton btnEliminar;
    private CommandButton btnLimpiar;
    
    private List<Matricula> listaMatriculas;
    private Matricula selectedMatricula;
    
    @EJB
    private MatriculaLogicaLocal matriculaLogica;
    
    @EJB
    private EstudianteLogicaLocal estudianteLogica;
    
    @EJB
    private MateriaLogicaLocal materiaLogica;

    public SelectOneMenu getCmbEstudiantes() {
        return cmbEstudiantes;
    }

    public void setCmbEstudiantes(SelectOneMenu cmbEstudiantes) {
        this.cmbEstudiantes = cmbEstudiantes;
    }

    public ArrayList<SelectItem> getItemsEstudiantes() {
        try {
            List<Estudiante> listaE = estudianteLogica.consultarTodos();
            itemsEstudiantes = new ArrayList<>();
            
            for(Estudiante e: listaE){
                itemsEstudiantes.add(new SelectItem(e.getDocumentoestudiante(), e.getNombreestudiante() + " " + e.getApellidoestudiante()));
            }
        } catch (Exception ex) {
            Logger.getLogger(MatriculaVista.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return itemsEstudiantes;
    }

    public void setItemsEstudiantes(ArrayList<SelectItem> itemsEstudiantes) {
        this.itemsEstudiantes = itemsEstudiantes;
    }

    public SelectOneMenu getCmbMaterias() {
        return cmbMaterias;
    }

    public void setCmbMaterias(SelectOneMenu cmbMaterias) {
        this.cmbMaterias = cmbMaterias;
    }

    public ArrayList<SelectItem> getItemsMaterias() {
        try {
            List<Materia> listaM = materiaLogica.consultarTodas();
            itemsMaterias = new ArrayList<>();
            
            for(Materia m: listaM){
                itemsMaterias.add(new SelectItem(m.getNumeromateria(), m.getNombremateria()));
            }
        } catch (Exception ex) {
            Logger.getLogger(MatriculaVista.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return itemsMaterias;
    }

    public void setItemsMaterias(ArrayList<SelectItem> itemsMaterias) {
        this.itemsMaterias = itemsMaterias;
    }
    
    public InputText getTxtNota() {
        return txtNota;
    }

    public void setTxtNota(InputText txtNota) {
        this.txtNota = txtNota;
    }

    public SelectOneMenu getCmbEstados() {
        return cmbEstados;
    }

    public void setCmbEstados(SelectOneMenu cmbEstados) {
        this.cmbEstados = cmbEstados;
    }

    public List<Matricula> getListaMatriculas() {
        if(listaMatriculas == null) {
            try {
                listaMatriculas = matriculaLogica.consultarTodas();
            } catch (Exception ex) {
                Logger.getLogger(MatriculaVista.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return listaMatriculas;
    }

    public void setListaMatriculas(List<Matricula> listaMatriculas) {
        this.listaMatriculas = listaMatriculas;
    }

    public Matricula getSelectedMatricula() {
        return selectedMatricula;
    }

    public void setSelectedMatricula(Matricula selectedMatricula) {
        this.selectedMatricula = selectedMatricula;
    }

    public CommandButton getBtnRegistrar() {
        return btnRegistrar;
    }

    public void setBtnRegistrar(CommandButton btnRegistrar) {
        this.btnRegistrar = btnRegistrar;
    }

    public CommandButton getBtnModificar() {
        return btnModificar;
    }

    public void setBtnModificar(CommandButton btnModificar) {
        this.btnModificar = btnModificar;
    }

    public CommandButton getBtnEliminar() {
        return btnEliminar;
    }

    public void setBtnEliminar(CommandButton btnEliminar) {
        this.btnEliminar = btnEliminar;
    }

    public CommandButton getBtnLimpiar() {
        return btnLimpiar;
    }

    public void setBtnLimpiar(CommandButton btnLimpiar) {
        this.btnLimpiar = btnLimpiar;
    }
    
    
    //Mostrar por interfaz la matrícula seleccionada
    public void onRowSelect(SelectEvent event) {
        this.selectedMatricula = (Matricula) event.getObject();
        
        this.cmbEstudiantes.setValue(selectedMatricula.getEstudiante().getDocumentoestudiante());
        this.cmbMaterias.setValue(selectedMatricula.getMateria().getNumeromateria());
        this.txtNota.setValue(selectedMatricula.getNota());
        this.cmbEstados.setValue(selectedMatricula.getEstado());
        
        // Se deshabilita el botón registrar para permitir que la matricula se puede modificar o eliminar       
        this.btnRegistrar.setDisabled(true);
        this.btnModificar.setDisabled(false);
        this.btnEliminar.setDisabled(false);
    }
    
    //Limpia los campos y reinicia los valores
    public void limpiar(){
        this.cmbEstudiantes.setValue("");
        this.cmbMaterias.setValue("");
        this.txtNota.setValue("");
        this.cmbEstados.setValue("");
        
        this.btnRegistrar.setDisabled(false);
        this.btnModificar.setDisabled(true);
        this.btnEliminar.setDisabled(true);
    }
    
    // Método registrar
    public void action_registrar(){
        try {
            Estudiante objEstudiante = new Estudiante();
            try { objEstudiante.setDocumentoestudiante(Long.parseLong(this.cmbEstudiantes.getValue().toString())); } catch(Exception ex) {}
            Materia objMateria = new Materia();
            try { objMateria.setNumeromateria(Integer.parseInt(this.cmbMaterias.getValue().toString())); } catch(Exception ex) {}
            
            Matricula objMatricula = new Matricula();
            try {
                MatriculaPK objMatriculaPK = new MatriculaPK(objEstudiante.getDocumentoestudiante(), objMateria.getNumeromateria());
                objMatricula.setMatriculaPK(objMatriculaPK);
            } catch(Exception ex) {}
            objMatricula.setEstudiante(objEstudiante);
            objMatricula.setMateria(objMateria);
            try { objMatricula.setNota(Double.parseDouble(this.txtNota.getValue().toString())); } catch(Exception ex) {}
            objMatricula.setEstado(this.cmbEstados.getValue().toString());
            
            matriculaLogica.registrarMatricula(objMatricula);
            listaMatriculas = null;
            limpiar();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Información de creación de matrícula", "La matrícula fue hecha con éxito."));

        } catch (Exception ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error.", ex.getMessage()));
        }
    }
    
    public void action_modificar(){
        try {
            Estudiante objEstudiante = new Estudiante();
            try { objEstudiante.setDocumentoestudiante(Long.parseLong(this.cmbEstudiantes.getValue().toString())); } catch(Exception ex) {}
            Materia objMateria = new Materia();
            try { objMateria.setNumeromateria(Integer.parseInt(this.cmbMaterias.getValue().toString())); } catch(Exception ex) {}
            
            Matricula objMatricula = new Matricula();
            try {
                MatriculaPK objMatriculaPK = new MatriculaPK(objEstudiante.getDocumentoestudiante(), objMateria.getNumeromateria());
                objMatricula.setMatriculaPK(objMatriculaPK);
            } catch(Exception ex) {}
            objMatricula.setEstudiante(objEstudiante);
            objMatricula.setMateria(objMateria);
            try { objMatricula.setNota(Double.parseDouble(this.txtNota.getValue().toString())); } catch(Exception ex) {}
            objMatricula.setEstado(this.cmbEstados.getValue().toString());
            
            matriculaLogica.modificarMatricula(objMatricula);
            listaMatriculas = null;
            limpiar();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Información de modificación de matrícula", "La matrícula fue modificada con éxito."));

        } catch (Exception ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error.", ex.getMessage()));
        }
    }
    
    public void action_eliminar(){
        try {
            Estudiante objEstudiante = new Estudiante();
            try { objEstudiante.setDocumentoestudiante(Long.parseLong(this.cmbEstudiantes.getValue().toString())); } catch(Exception ex) {}
            Materia objMateria = new Materia();
            try { objMateria.setNumeromateria(Integer.parseInt(this.cmbMaterias.getValue().toString())); } catch(Exception ex) {}
            
            Matricula objMatricula = new Matricula();
            try {
                MatriculaPK objMatriculaPK = new MatriculaPK(objEstudiante.getDocumentoestudiante(), objMateria.getNumeromateria());
                objMatricula.setMatriculaPK(objMatriculaPK);
            } catch(Exception ex) {}
            objMatricula.setEstudiante(objEstudiante);
            objMatricula.setMateria(objMateria);
            try { objMatricula.setNota(Double.parseDouble(this.txtNota.getValue().toString())); } catch(Exception ex) {}
            objMatricula.setEstado(this.cmbEstados.getValue().toString());
            
            matriculaLogica.eliminarMatricula(objMatricula);
            listaMatriculas = null;
            limpiar();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Información de eliminación de matrícula", "La matrícula fue eliminada con éxito."));

        } catch (Exception ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error.", ex.getMessage()));
        }
    }
    
    public void handleFileUpload(FileUploadEvent event) {
        ServletContext servletContext = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
        String path = servletContext.getRealPath("/");
        File f = new File (path + "excel");
        f.mkdir();
        String rutaDestino = (String) servletContext.getRealPath("/excel");

        try {
            copyFile(rutaDestino, event.getFile().getFileName(), event.getFile().getInputstream());
            String resultado = matriculaLogica.importarMatriculas(rutaDestino + "\\" + event.getFile().getFileName());
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Información de importación", resultado));
           
        } catch(Exception ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", ex.getMessage()));
        }
    }
    
    public void copyFile(String rutaDestino, String fileName, InputStream in) {
        try {
            OutputStream out = new FileOutputStream(new File(rutaDestino + "\\" + fileName));
            int read = 0;
            byte[] bytes = new byte[1024];

            while ((read = in.read(bytes)) != -1) {
                out.write(bytes, 0, read);
            }
            in.close();
            out.flush();
            out.close();
        } catch(Exception ex) {
            Logger.getLogger(MatriculaVista.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * Creates a new instance of MatriculaVista
     */
    public MatriculaVista() {
    }
    
}
