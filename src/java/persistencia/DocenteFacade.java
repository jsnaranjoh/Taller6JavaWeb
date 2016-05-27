/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package persistencia;

import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import modelo.Docente;

/**
 *
 * @author DILOVE
 */
@Stateless
public class DocenteFacade extends AbstractFacade<Docente> implements DocenteFacadeLocal {
    @PersistenceContext(unitName = "Laboratorio6JavaPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public DocenteFacade() {
        super(Docente.class);
    }

    @Override
    public List<Docente> consultarDocentexNombre(String nombre) {
        String consulta = "select d from Docente d where d.nombredocente like'%"+nombre+"%'";
        Query query = em.createQuery(consulta);
        return query.getResultList();
    }
    
}
