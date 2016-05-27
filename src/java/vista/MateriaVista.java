/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vista;

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
import logica.CarreraLogicaLocal;
import logica.MateriaLogicaLocal;
import modelo.Carrera;
import modelo.Materia;
import org.primefaces.component.commandbutton.CommandButton;
import org.primefaces.component.inputtext.InputText;
import org.primefaces.component.selectonemenu.SelectOneMenu;
import org.primefaces.event.SelectEvent;

/**
 *
 * @author jsnar
 */
@Named(value = "materiaVista")
@RequestScoped
public class MateriaVista {

    private InputText txtNumero;
    private InputText txtNombre;
    private InputText txtCreditos;
    
    private SelectOneMenu cmbCarreras;
    private ArrayList<SelectItem> itemsCarreras;
    
    private CommandButton btnRegistrar;
    private CommandButton btnModificar;
    private CommandButton btnEliminar;
    private CommandButton btnLimpiar;
    
    private List<Materia> listaMaterias;
    private Materia selectedMateria;
    
    @EJB
    private MateriaLogicaLocal materiaLogica;
    
    @EJB
    private CarreraLogicaLocal carreraLogica;

    public SelectOneMenu getCmbCarreras() {
        return cmbCarreras;
    }

    public void setCmbCarreras(SelectOneMenu cmbCarreras) {
        this.cmbCarreras = cmbCarreras;
    }

    public ArrayList<SelectItem> getItemsCarreras() {
        try {
            List<Carrera> listaC = carreraLogica.consultarTodas();
            itemsCarreras = new ArrayList<>();
            
            for(Carrera c: listaC){
                itemsCarreras.add(new SelectItem(c.getNumerocarrera(), c.getNombrecarrera()));
            }
        } catch (Exception ex) {
            Logger.getLogger(CarreraVista.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return itemsCarreras;
    }

    public void setItemsCarreras(ArrayList<SelectItem> itemsCarreras) {
        this.itemsCarreras = itemsCarreras;
    }  
    
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

    public InputText getTxtCreditos() {
        return txtCreditos;
    }

    public void setTxtCreditos(InputText txtCreditos) {
        this.txtCreditos = txtCreditos;
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

    public List<Materia> getListaMaterias() {
        if(listaMaterias == null) {
            try {
                listaMaterias = materiaLogica.consultarTodas();
            } catch (Exception ex) {
                Logger.getLogger(CarreraVista.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return listaMaterias;
    }

    public void setListaMaterias(List<Materia> listaMaterias) {
        this.listaMaterias = listaMaterias;
    }

    public Materia getSelectedMateria() {
        return selectedMateria;
    }

    public void setSelectedMateria(Materia selectedMateria) {
        this.selectedMateria = selectedMateria;
    }    

    // Mostrar por interfaz la materia seleccionada
    public void onRowSelect(SelectEvent event) {        
        this.selectedMateria = (Materia) event.getObject();
        
        this.txtNumero.setValue(selectedMateria.getNumeromateria());
        this.txtNombre.setValue(selectedMateria.getNombremateria());
        this.txtCreditos.setValue(selectedMateria.getCreditosmateria());
        this.cmbCarreras.setValue(selectedMateria.getNumerocarrera().getNumerocarrera());
        
        // Se deshabilita el botón registrar para permitir que la materia se puede modificar o eliminar       
        this.btnRegistrar.setDisabled(true);
        this.btnModificar.setDisabled(false);
        this.btnEliminar.setDisabled(false);
        this.txtNumero.setDisabled(true);
    }
    
    // Limpia los campos y reinicia los valores
    public void limpiar(){
        this.txtNumero.setValue("");
        this.txtNombre.setValue("");
        this.txtCreditos.setValue("");
        this.cmbCarreras.setValue("");
        
        this.txtNumero.setDisabled(false);
        this.btnRegistrar.setDisabled(false);
        this.btnModificar.setDisabled(true);
        this.btnEliminar.setDisabled(true);
    }

    // Método registrar
    public void action_registrar(){
        try {
            Carrera objCarrera = new Carrera();
            try { objCarrera.setNumerocarrera(Integer.parseInt(this.cmbCarreras.getValue().toString())); } catch(Exception ex) {}
            
            Materia objMateria = new Materia();
            try { objMateria.setNumeromateria(Integer.parseInt(this.txtNumero.getValue().toString())); } catch(Exception ex) {}
            objMateria.setNombremateria(this.txtNombre.getValue().toString());
            try { objMateria.setCreditosmateria(Integer.parseInt(this.txtCreditos.getValue().toString())); } catch(Exception ex) {}
            objMateria.setNumerocarrera(objCarrera);
            
            materiaLogica.registrarMateria(objMateria);
            listaMaterias = null;
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
            try { objCarrera.setNumerocarrera(Integer.parseInt(this.cmbCarreras.getValue().toString())); } catch(Exception ex) {}
            
            Materia objMateria = new Materia();
            try { objMateria.setNumeromateria(Integer.parseInt(this.txtNumero.getValue().toString())); } catch(Exception ex) {}
            objMateria.setNombremateria(this.txtNombre.getValue().toString());
            try { objMateria.setCreditosmateria(Integer.parseInt(this.txtCreditos.getValue().toString())); } catch(Exception ex) {}
            objMateria.setNumerocarrera(objCarrera);
            
            materiaLogica.modificarMateria(objMateria);
            listaMaterias = null;
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
            try { objCarrera.setNumerocarrera(Integer.parseInt(this.cmbCarreras.getValue().toString())); } catch(Exception ex) {}
            
            Materia objMateria = new Materia();
            try { objMateria.setNumeromateria(Integer.parseInt(this.txtNumero.getValue().toString())); } catch(Exception ex) {}
            objMateria.setNombremateria(this.txtNombre.getValue().toString());
            try { objMateria.setCreditosmateria(Integer.parseInt(this.txtCreditos.getValue().toString())); } catch(Exception ex) {}
            objMateria.setNumerocarrera(objCarrera);
            
            materiaLogica.eliminarMateria(objMateria);
            listaMaterias = null;
            limpiar();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Información de eliminación de carrera", "La carrera fue eliminada con éxito."));
            
        } catch (Exception ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error.", ex.getMessage()));
        }
    }
    
    /**
     * Creates a new instance of MateriaVista
     */
    public MateriaVista() {
    }
    
}
