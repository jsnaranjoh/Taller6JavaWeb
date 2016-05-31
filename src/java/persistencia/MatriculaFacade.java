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
import modelo.Matricula;

/**
 *
 * @author DILOVE
 */
@Stateless
public class MatriculaFacade extends AbstractFacade<Matricula> implements MatriculaFacadeLocal {
    @PersistenceContext(unitName = "Taller6JavaWebPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public MatriculaFacade() {
        super(Matricula.class);
    }

    @Override
    public List<Matricula> consultarxMateria(Integer numero) {
        String consulta = "SELECT m FROM Matricula m WHERE m.materia.numeromateria='"+numero+"'";
        Query query = em.createQuery(consulta);
        return query.getResultList();
    }
    
}
