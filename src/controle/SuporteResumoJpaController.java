/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controle;

import controle.exceptions.NonexistentEntityException;
import entidades.SuporteResumo;
import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

/**
 *
 * @author Hilton
 */
public class SuporteResumoJpaController implements Serializable {

    public SuporteResumoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(SuporteResumo suporteResumo) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(suporteResumo);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(SuporteResumo suporteResumo) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            suporteResumo = em.merge(suporteResumo);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = suporteResumo.getIPonto();
                if (findSuporteResumo(id) == null) {
                    throw new NonexistentEntityException("The suporteResumo with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            SuporteResumo suporteResumo;
            try {
                suporteResumo = em.getReference(SuporteResumo.class, id);
                suporteResumo.getIPonto();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The suporteResumo with id " + id + " no longer exists.", enfe);
            }
            em.remove(suporteResumo);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<SuporteResumo> findSuporteResumoEntities() {
        return findSuporteResumoEntities(true, -1, -1);
    }

    public List<SuporteResumo> findSuporteResumoEntities(int maxResults, int firstResult) {
        return findSuporteResumoEntities(false, maxResults, firstResult);
    }

    private List<SuporteResumo> findSuporteResumoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(SuporteResumo.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public SuporteResumo findSuporteResumo(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(SuporteResumo.class, id);
        } finally {
            em.close();
        }
    }

    public int getSuporteResumoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<SuporteResumo> rt = cq.from(SuporteResumo.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
