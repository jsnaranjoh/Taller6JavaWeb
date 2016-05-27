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
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;
import logica.EstudianteLogicaLocal;
import modelo.Estudiante;
import org.primefaces.component.commandbutton.CommandButton;
import org.primefaces.component.inputtext.InputText;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.event.SelectEvent;

/**
 *
 * @author jsnar
 */
@Named(value = "estudianteVista")
@RequestScoped
public class EstudianteVista {

    private InputText txtDocumento;
    private InputText txtNombre;
    private InputText txtApellido;
    private InputText txtCorreo;
    private InputText txtSemestre;
    private String txtClave;
    
    private CommandButton btnRegistrar;
    private CommandButton btnModificar;
    private CommandButton btnEliminar;
    private CommandButton btnLimpiar;
    
    private List<Estudiante> listaEstudiantes;
    private Estudiante selectedEstudiante;

    @EJB
    private EstudianteLogicaLocal estudianteLogica;
    
    public InputText getTxtDocumento() {
        return txtDocumento;
    }

    public void setTxtDocumento(InputText txtDocumento) {
        this.txtDocumento = txtDocumento;
    }

    public InputText getTxtNombre() {
        return txtNombre;
    }

    public void setTxtNombre(InputText txtNombre) {
        this.txtNombre = txtNombre;
    }

    public InputText getTxtApellido() {
        return txtApellido;
    }

    public void setTxtApellido(InputText txtApellido) {
        this.txtApellido = txtApellido;
    }

    public InputText getTxtCorreo() {
        return txtCorreo;
    }

    public void setTxtCorreo(InputText txtCorreo) {
        this.txtCorreo = txtCorreo;
    }

    public InputText getTxtSemestre() {
        return txtSemestre;
    }

    public void setTxtSemestre(InputText txtSemestre) {
        this.txtSemestre = txtSemestre;
    }
    
    public String getTxtClave() {
        return txtClave;
    }

    public void setTxtClave(String txtClave) {
        this.txtClave = txtClave;
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

    public List<Estudiante> getListaEstudiantes() {
        if(listaEstudiantes == null) {
            try {
                listaEstudiantes = estudianteLogica.consultarTodos();
            } catch (Exception ex) {
                Logger.getLogger(EstudianteVista.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return listaEstudiantes;
    }

    public void setListaEstudiantes(List<Estudiante> listaEstudiantes) {
        this.listaEstudiantes = listaEstudiantes;
    }

    public Estudiante getSelectedEstudiante() {
        return selectedEstudiante;
    }

    public void setSelectedEstudiante(Estudiante selectedEstudiante) {
        this.selectedEstudiante = selectedEstudiante;
    }

    // Mostrar por interfaz el estudiante seleccionado
    public void onRowSelect(SelectEvent event) {
        this.selectedEstudiante = (Estudiante) event.getObject();
        
        this.txtDocumento.setValue(selectedEstudiante.getDocumentoestudiante());
        this.txtNombre.setValue(selectedEstudiante.getNombreestudiante());
        this.txtApellido.setValue(selectedEstudiante.getApellidoestudiante());
        this.txtCorreo.setValue(selectedEstudiante.getCorreoestudiante());
        this.txtSemestre.setValue(selectedEstudiante.getSemestreestudiante());
        
        // Se deshabilita el botón registrar para permitir que el estudiante se puede modificar o eliminar       
        this.btnRegistrar.setDisabled(true);
        this.btnModificar.setDisabled(false);
        this.btnEliminar.setDisabled(false);
        this.txtDocumento.setDisabled(true);
    }
    
    // Limpia los campos y reinicia los valores
    public void limpiar(){
        this.txtDocumento.setValue("");
        this.txtNombre.setValue("");
        this.txtApellido.setValue("");
        this.txtCorreo.setValue("");
        this.txtSemestre.setValue("");
        
        this.txtDocumento.setDisabled(false);
        this.btnRegistrar.setDisabled(false);
        this.btnModificar.setDisabled(true);
        this.btnEliminar.setDisabled(true);
    }
    
    // Método registrar
    public void action_registrar(){
        try {
            Estudiante objEstudiante = new Estudiante();
            try { objEstudiante.setDocumentoestudiante(Long.parseLong(this.txtDocumento.getValue().toString())); } catch(Exception ex) {}
            objEstudiante.setNombreestudiante(this.txtNombre.getValue().toString());
            objEstudiante.setApellidoestudiante(this.txtApellido.getValue().toString());
            objEstudiante.setCorreoestudiante(this.txtCorreo.getValue().toString());
            try { objEstudiante.setSemestreestudiante(Integer.parseInt(this.txtSemestre.getValue().toString())); } catch(Exception ex) {}
            objEstudiante.setClaveestudiante(this.txtClave);
            
            estudianteLogica.registrarEstudiante(objEstudiante);
            listaEstudiantes = null;
            limpiar();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Información de creación de estudiante", "El estudiante fue registrado con éxito."));
            
        } catch (Exception ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error.", ex.getMessage()));
        }
    }
    
    // Método modificar
    public void action_modificar(){
        try{
            Estudiante objEstudiante = new Estudiante();
            try { objEstudiante.setDocumentoestudiante(Long.parseLong(this.txtDocumento.getValue().toString())); } catch(Exception ex) {}
            objEstudiante.setNombreestudiante(this.txtNombre.getValue().toString());
            objEstudiante.setApellidoestudiante(this.txtApellido.getValue().toString());
            objEstudiante.setCorreoestudiante(this.txtCorreo.getValue().toString());
            try { objEstudiante.setSemestreestudiante(Integer.parseInt(this.txtSemestre.getValue().toString())); } catch(Exception ex) {}
            objEstudiante.setClaveestudiante(this.txtClave);
            
            estudianteLogica.modificarEstudiante(objEstudiante);
            listaEstudiantes = null;
            limpiar();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Información de modificación de estudiante", "El estudiante fue modificado con éxito."));
            
        } catch (Exception ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error.", ex.getMessage()));
        }
    }
    
    // Método eliminar
    public void action_eliminar(){
        try{
            Estudiante objEstudiante = new Estudiante();
            try { objEstudiante.setDocumentoestudiante(Long.parseLong(this.txtDocumento.getValue().toString())); } catch(Exception ex) {}
            objEstudiante.setNombreestudiante(this.txtNombre.getValue().toString());
            objEstudiante.setApellidoestudiante(this.txtApellido.getValue().toString());
            objEstudiante.setCorreoestudiante(this.txtCorreo.getValue().toString());
            try { objEstudiante.setSemestreestudiante(Integer.parseInt(this.txtSemestre.getValue().toString())); } catch(Exception ex) {}
            objEstudiante.setClaveestudiante(this.txtClave);
            
            estudianteLogica.eliminarEstudiante(objEstudiante);
            listaEstudiantes = null;
            limpiar();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Información de eliminación de estudiante", "El estudiante fue eliminado con éxito."));
            
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
            String resultado = estudianteLogica.importarEstudiantes(rutaDestino + "\\" + event.getFile().getFileName());
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
     * Creates a new instance of EstudianteVista
     */
    public EstudianteVista() {
    }
    
}
