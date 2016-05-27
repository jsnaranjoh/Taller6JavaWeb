/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package persistencia;

import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import modelo.Estudiante;

/**
 *
 * @author DILOVE
 */
@Stateless
public class EstudianteFacade extends AbstractFacade<Estudiante> implements EstudianteFacadeLocal {
    @PersistenceContext(unitName = "Laboratorio6JavaPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public EstudianteFacade() {
        super(Estudiante.class);
    }

    @Override
    public List<Estudiante> findxsemester(Integer s) {
        String consulta = "select e from Estudiante e where e.semestreestudiante="+s;
        Query query = em.createQuery(consulta);
        return query.getResultList();
    }

    @Override
    public Estudiante consultarxDocumento(Long documento) {
        String consulta = "select e from Estudiante e where e.documentoestudiante="+documento;
        try{
            Query query = em.createQuery(consulta);
            return (Estudiante) query.getSingleResult();
        }catch(NoResultException nre){
            return null;
        }
    }

    @Override
    public List<Estudiante> consultarxMateria(Integer codM) {
        String consulta = "select e from Estudiante e, Matricula m where"
                + "m.materia.numeromateria="+codM+" and m.estudiante.documentoestudiante=e.documentoestudiante";
        Query query = em.createQuery(consulta);
        return query.getResultList();
    }
    
}
