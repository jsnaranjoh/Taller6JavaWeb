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
import logica.CarreraLogicaLocal;
import modelo.Carrera;
import org.primefaces.component.commandbutton.CommandButton;
import org.primefaces.component.inputtext.InputText;
import org.primefaces.event.SelectEvent;

/**
 *
 * @author jsnar
 */
@Named(value = "carreraVista")
@RequestScoped
public class CarreraVista {

    private InputText txtNumero;
    private InputText txtNombre;
    private CommandButton btnRegistrar;
    private CommandButton btnModificar;
    private CommandButton btnEliminar;
    private CommandButton btnLimpiar;
    private List<Carrera> listaCarreras;
    private Carrera selectedCarrera;
    
    @EJB
    private CarreraLogicaLocal carreraLogica;

    public InputText getTxtNumero() {
        return txtNumero;
    }

    public void setTxtNumero(InputText txtNumero) {
        this.txtNumero = txtNumero;
    }

    public InputText getTxtNombre() {
        return txtNombre;
    }

    public void setTxtNombre(InputText txtNombre) {
        this.txtNombre = txtNombre;
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

    public List<Carrera> getListaCarreras() {
        if(listaCarreras == null) {
            try {
                listaCarreras = carreraLogica.consultarTodas();
            } catch (Exception ex) {
                Logger.getLogger(CarreraVista.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return listaCarreras;
    }

    public void setListaCarreras(List<Carrera> listaCarreras) {
        this.listaCarreras = listaCarreras;
    }

    public Carrera getSelectedCarrera() {
        return selectedCarrera;
    }

    public void setSelectedCarrera(Carrera selectedCarrera) {
        this.selectedCarrera = selectedCarrera;
    }
 
    // Mostrar por interfaz la carrera seleccionada
    public void onRowSelect(SelectEvent event) {
        Carrera carreraSeleccionada = (Carrera) event.getObject();
        this.txtNumero.setValue(carreraSeleccionada.getNumerocarrera());
        this.txtNombre.setValue(carreraSeleccionada.getNombrecarrera());
        
        // Se deshabilita el botón registrar para permitir que la carrera se puede modificar o eliminar       
        this.btnRegistrar.setDisabled(true);
        this.btnModificar.setDisabled(false);
        this.btnEliminar.setDisabled(false);
        this.txtNumero.setDisabled(true);
    }
    
    // Limpia los campos y reinicia los valores
    public void limpiar(){
        this.txtNumero.setValue("");
        this.txtNombre.setValue("");
        this.txtNumero.setDisabled(false);
        this.btnRegistrar.setDisabled(false);
        this.btnModificar.setDisabled(true);
        this.btnEliminar.setDisabled(true);
    }

    // Método registrar
    public void action_registrar(){
        try {
            Carrera objCarrera = new Carrera();
            try { objCarrera.setNumerocarrera(Integer.parseInt(this.txtNumero.getValue().toString())); } catch(Exception ex) {}
            objCarrera.setNombrecarrera(this.txtNombre.getValue().toString());
            
            carreraLogica.registrarCarrera(objCarrera);
            listaCarreras = null;
            limpiar();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Información de creación de carrera", "La carrera fue registrada con éxito."));
            
        } catch (Exception ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error.", ex.getMessage()));
        }
    }
    
    // Método modificar
    public void action_modificar(){
        try {
            Carrera objCarrera = new Carrera();
            try { objCarrera.setNumerocarrera(Integer.parseInt(this.txtNumero.getValue().toString())); } catch(Exception ex) {}
            objCarrera.setNombrecarrera(this.txtNombre.getValue().toString());
            
            carreraLogica.modificarCarrera(objCarrera);
            listaCarreras = null;
            limpiar();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Información de modificación de carrera", "La carrera fue modificada con éxito."));
            
        } catch (Exception ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error.", ex.getMessage()));
        }
    }
    
    // Método eliminar
    public void action_eliminar(){
        try {
            Carrera objCarrera = new Carrera();
            try { objCarrera.setNumerocarrera(Integer.parseInt(this.txtNumero.getValue().toString())); } catch(Exception ex) {}
            objCarrera.setNombrecarrera(this.txtNombre.getValue().toString());
            
            carreraLogica.eliminarCarrera(objCarrera);
            listaCarreras = null;
            limpiar();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Información de eliminación de carrera", "La carrera fue eliminada con éxito."));
            
        } catch (Exception ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error.", ex.getMessage()));
        }
    }
    
    /**
     * Creates a new instance of CarreraVista
     */
    public CarreraVista() {
    }
    
}
