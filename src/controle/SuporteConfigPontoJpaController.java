/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controle;

import controle.exceptions.NonexistentEntityException;
import entidades.SuporteConfigPonto;
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
public class SuporteConfigPontoJpaController implements Serializable {

    public SuporteConfigPontoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(SuporteConfigPonto suporteConfigPonto) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(suporteConfigPonto);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(SuporteConfigPonto suporteConfigPonto) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            suporteConfigPonto = em.merge(suporteConfigPonto);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = suporteConfigPonto.getIConfigPonto();
                if (findSuporteConfigPonto(id) == null) {
                    throw new NonexistentEntityException("The suporteConfigPonto with id " + id + " no longer exists.");
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
            SuporteConfigPonto suporteConfigPonto;
            try {
                suporteConfigPonto = em.getReference(SuporteConfigPonto.class, id);
                suporteConfigPonto.getIConfigPonto();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The suporteConfigPonto with id " + id + " no longer exists.", enfe);
            }
            em.remove(suporteConfigPonto);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<SuporteConfigPonto> findSuporteConfigPontoEntities() {
        return findSuporteConfigPontoEntities(true, -1, -1);
    }

    public List<SuporteConfigPonto> findSuporteConfigPontoEntities(int maxResults, int firstResult) {
        return findSuporteConfigPontoEntities(false, maxResults, firstResult);
    }

    private List<SuporteConfigPonto> findSuporteConfigPontoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(SuporteConfigPonto.class));
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

    public SuporteConfigPonto findSuporteConfigPonto(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(SuporteConfigPonto.class, id);
        } finally {
            em.close();
        }
    }

    public int getSuporteConfigPontoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<SuporteConfigPonto> rt = cq.from(SuporteConfigPonto.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
