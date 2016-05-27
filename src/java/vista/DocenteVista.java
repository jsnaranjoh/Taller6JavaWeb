/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vista;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import logica.DocenteLogicaLocal;
import modelo.Docente;
import org.primefaces.component.commandbutton.CommandButton;
import org.primefaces.component.inputtext.InputText;
import org.primefaces.event.SelectEvent;

/**
 *
 * @author jsnar
 */
@Named(value = "docenteVista")
@RequestScoped
public class DocenteVista {

    private InputText txtDocumento;
    private InputText txtNombre;
    private InputText txtApellido;
    private InputText txtCorreo;
    private InputText txtTelefono;
    private InputText txtProfesion;
    private String txtClave;
    
    private CommandButton btnRegistrar;
    private CommandButton btnModificar;
    private CommandButton btnEliminar;
    private CommandButton btnLimpiar;
    
    private List<Docente> listaDocentes;
    private Docente selectedDocente;
    
    @EJB
    private DocenteLogicaLocal docenteLogica;
    
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

    public InputText getTxtTelefono() {
        return txtTelefono;
    }

    public void setTxtTelefono(InputText txtTelefono) {
        this.txtTelefono = txtTelefono;
    }

    public InputText getTxtProfesion() {
        return txtProfesion;
    }

    public void setTxtProfesion(InputText txtProfesion) {
        this.txtProfesion = txtProfesion;
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

    public List<Docente> getListaDocentes() {
        if(listaDocentes == null) {
            try {
                listaDocentes = docenteLogica.consultarTodos();
            } catch (Exception ex) {
                Logger.getLogger(DocenteVista.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return listaDocentes;
    }

    public void setListaDocentes(List<Docente> listaDocentes) {
        this.listaDocentes = listaDocentes;
    }

    public Docente getSelectedDocente() {
        return selectedDocente;
    }

    public void setSelectedDocente(Docente selectedDocente) {
        this.selectedDocente = selectedDocente;
    }    
    
    // Mostrar por interfaz el docente seleccionado
    public void onRowSelect(SelectEvent event) {
        this.selectedDocente = (Docente) event.getObject();
        
        this.txtDocumento.setValue(selectedDocente.getDocumentodocente());
        this.txtNombre.setValue(selectedDocente.getNombredocente());
        this.txtApellido.setValue(selectedDocente.getApellidodocente());
        this.txtCorreo.setValue(selectedDocente.getCorreodocente());
        this.txtTelefono.setValue(selectedDocente.getTelefonodocente());
        this.txtProfesion.setValue(selectedDocente.getProfesiondocente());
        
        // Se deshabilita el botón registrar para permitir que el docente se puede modificar o eliminar       
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
        this.txtTelefono.setValue("");
        this.txtProfesion.setValue("");
        
        this.txtDocumento.setDisabled(false);
        this.btnRegistrar.setDisabled(false);
        this.btnModificar.setDisabled(true);
        this.btnEliminar.setDisabled(true);
    }
    
    // Método registrar
    public void action_registrar(){
        try {
            Docente objDocente = new Docente();
            try { objDocente.setDocumentodocente(Long.parseLong(this.txtDocumento.getValue().toString())); } catch(Exception ex) {}
            objDocente.setNombredocente(this.txtNombre.getValue().toString());
            objDocente.setApellidodocente(this.txtApellido.getValue().toString());
            objDocente.setCorreodocente(this.txtCorreo.getValue().toString());
            objDocente.setProfesiondocente(this.txtProfesion.getValue().toString());
            objDocente.setTelefonodocente(this.txtTelefono.getValue().toString());
            objDocente.setClavedocente(this.txtClave);
            
            docenteLogica.registrarDocente(objDocente);
            listaDocentes = null;
            limpiar();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Información de creación de docente", "El docente fue registrado con éxito."));
            
        } catch (Exception ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error.", ex.getMessage()));
        }
    }
    
    // Método modificar
    public void action_modificar(){
        try{
            Docente objDocente = new Docente();
            try { objDocente.setDocumentodocente(Long.parseLong(this.txtDocumento.getValue().toString())); } catch(Exception ex) {}
            objDocente.setNombredocente(this.txtNombre.getValue().toString());
            objDocente.setApellidodocente(this.txtApellido.getValue().toString());
            objDocente.setCorreodocente(this.txtCorreo.getValue().toString());
            objDocente.setProfesiondocente(this.txtProfesion.getValue().toString());
            objDocente.setTelefonodocente(this.txtTelefono.getValue().toString());
            objDocente.setClavedocente(this.txtClave);
            
            docenteLogica.modificarDocente(objDocente);
            listaDocentes = null;
            limpiar();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Información de modificación de docente", "El docente fue modificado con éxito."));
            
        } catch (Exception ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error.", ex.getMessage()));
        }
    }
    
    // Método eliminar
    public void action_eliminar(){
        try{
            Docente objDocente = new Docente();
            try { objDocente.setDocumentodocente(Long.parseLong(this.txtDocumento.getValue().toString())); } catch(Exception ex) {}
            objDocente.setNombredocente(this.txtNombre.getValue().toString());
            objDocente.setApellidodocente(this.txtApellido.getValue().toString());
            objDocente.setCorreodocente(this.txtCorreo.getValue().toString());
            objDocente.setProfesiondocente(this.txtProfesion.getValue().toString());
            objDocente.setTelefonodocente(this.txtTelefono.getValue().toString());
            objDocente.setClavedocente(this.txtClave);
            
            docenteLogica.eliminarDocente(objDocente);
            listaDocentes = null;
            limpiar();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Información de eliminación de docente", "El docente fue eliminado con éxito."));
            
        } catch (Exception ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error.", ex.getMessage()));
        }
    }
    
    /**
     * Creates a new instance of DocenteVista
     */
    public DocenteVista() {
    }
    
}
