/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controle;

import controle.exceptions.NonexistentEntityException;
import entidades.SuporteIntercorrencia;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import entidades.SuportePontoeletronico;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Hilton
 */
public class SuporteIntercorrenciaJpaController implements Serializable {

    public SuporteIntercorrenciaJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(SuporteIntercorrencia suporteIntercorrencia) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            SuportePontoeletronico IPonto = suporteIntercorrencia.getIPonto();
            if (IPonto != null) {
                IPonto = em.getReference(IPonto.getClass(), IPonto.getIPonto());
                suporteIntercorrencia.setIPonto(IPonto);
            }
            em.persist(suporteIntercorrencia);
            if (IPonto != null) {
                IPonto.getSuporteIntercorrenciaList().add(suporteIntercorrencia);
                IPonto = em.merge(IPonto);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(SuporteIntercorrencia suporteIntercorrencia) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            SuporteIntercorrencia persistentSuporteIntercorrencia = em.find(SuporteIntercorrencia.class, suporteIntercorrencia.getIIntercorrencia());
            SuportePontoeletronico IPontoOld = persistentSuporteIntercorrencia.getIPonto();
            SuportePontoeletronico IPontoNew = suporteIntercorrencia.getIPonto();
            if (IPontoNew != null) {
                IPontoNew = em.getReference(IPontoNew.getClass(), IPontoNew.getIPonto());
                suporteIntercorrencia.setIPonto(IPontoNew);
            }
            suporteIntercorrencia = em.merge(suporteIntercorrencia);
            if (IPontoOld != null && !IPontoOld.equals(IPontoNew)) {
                IPontoOld.getSuporteIntercorrenciaList().remove(suporteIntercorrencia);
                IPontoOld = em.merge(IPontoOld);
            }
            if (IPontoNew != null && !IPontoNew.equals(IPontoOld)) {
                IPontoNew.getSuporteIntercorrenciaList().add(suporteIntercorrencia);
                IPontoNew = em.merge(IPontoNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = suporteIntercorrencia.getIIntercorrencia();
                if (findSuporteIntercorrencia(id) == null) {
                    throw new NonexistentEntityException("The suporteIntercorrencia with id " + id + " no longer exists.");
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
            SuporteIntercorrencia suporteIntercorrencia;
            try {
                suporteIntercorrencia = em.getReference(SuporteIntercorrencia.class, id);
                suporteIntercorrencia.getIIntercorrencia();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The suporteIntercorrencia with id " + id + " no longer exists.", enfe);
            }
            SuportePontoeletronico IPonto = suporteIntercorrencia.getIPonto();
            if (IPonto != null) {
                IPonto.getSuporteIntercorrenciaList().remove(suporteIntercorrencia);
                IPonto = em.merge(IPonto);
            }
            em.remove(suporteIntercorrencia);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<SuporteIntercorrencia> findSuporteIntercorrenciaEntities() {
        return findSuporteIntercorrenciaEntities(true, -1, -1);
    }

    public List<SuporteIntercorrencia> findSuporteIntercorrenciaEntities(int maxResults, int firstResult) {
        return findSuporteIntercorrenciaEntities(false, maxResults, firstResult);
    }

    private List<SuporteIntercorrencia> findSuporteIntercorrenciaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(SuporteIntercorrencia.class));
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

    public SuporteIntercorrencia findSuporteIntercorrencia(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(SuporteIntercorrencia.class, id);
        } finally {
            em.close();
        }
    }

    public int getSuporteIntercorrenciaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<SuporteIntercorrencia> rt = cq.from(SuporteIntercorrencia.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
    
 //Método Novo
    public List<SuporteIntercorrencia> findSuporteIntercorrenciaDoPonto(int i_ponto) {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createQuery("select i from SuporteIntercorrencia i where i.iPonto.iPonto = "+i_ponto+" ORDER BY i.dtInicio ASC" );
            return q.getResultList();
        } finally {
            em.close();
        }
    }
    
}
