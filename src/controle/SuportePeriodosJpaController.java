/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controle;

import controle.exceptions.NonexistentEntityException;
import entidades.SuportePeriodos;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import entidades.SuportePontoeletronico;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Hilton
 */
public class SuportePeriodosJpaController {

    private EntityManagerFactory emf = null;

    public SuportePeriodosJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(SuportePeriodos suportePeriodos) {
        if (suportePeriodos.getSuportePontoeletronicoList() == null) {
            suportePeriodos.setSuportePontoeletronicoList(new ArrayList<SuportePontoeletronico>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<SuportePontoeletronico> attachedSuportePontoeletronicoList = new ArrayList<SuportePontoeletronico>();
            for (SuportePontoeletronico suportePontoeletronicoListSuportePontoeletronicoToAttach : suportePeriodos.getSuportePontoeletronicoList()) {
                suportePontoeletronicoListSuportePontoeletronicoToAttach = em.getReference(suportePontoeletronicoListSuportePontoeletronicoToAttach.getClass(), suportePontoeletronicoListSuportePontoeletronicoToAttach.getIPonto());
                attachedSuportePontoeletronicoList.add(suportePontoeletronicoListSuportePontoeletronicoToAttach);
            }
            suportePeriodos.setSuportePontoeletronicoList(attachedSuportePontoeletronicoList);
            em.persist(suportePeriodos);
            for (SuportePontoeletronico suportePontoeletronicoListSuportePontoeletronico : suportePeriodos.getSuportePontoeletronicoList()) {
                SuportePeriodos oldIPeriodoOfSuportePontoeletronicoListSuportePontoeletronico = suportePontoeletronicoListSuportePontoeletronico.getIPeriodo();
                suportePontoeletronicoListSuportePontoeletronico.setIPeriodo(suportePeriodos);
                suportePontoeletronicoListSuportePontoeletronico = em.merge(suportePontoeletronicoListSuportePontoeletronico);
                if (oldIPeriodoOfSuportePontoeletronicoListSuportePontoeletronico != null) {
                    oldIPeriodoOfSuportePontoeletronicoListSuportePontoeletronico.getSuportePontoeletronicoList().remove(suportePontoeletronicoListSuportePontoeletronico);
                    oldIPeriodoOfSuportePontoeletronicoListSuportePontoeletronico = em.merge(oldIPeriodoOfSuportePontoeletronicoListSuportePontoeletronico);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(SuportePeriodos suportePeriodos) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            SuportePeriodos persistentSuportePeriodos = em.find(SuportePeriodos.class, suportePeriodos.getIPeriodo());
            List<SuportePontoeletronico> suportePontoeletronicoListOld = persistentSuportePeriodos.getSuportePontoeletronicoList();
            List<SuportePontoeletronico> suportePontoeletronicoListNew = suportePeriodos.getSuportePontoeletronicoList();
            List<SuportePontoeletronico> attachedSuportePontoeletronicoListNew = new ArrayList<SuportePontoeletronico>();
            for (SuportePontoeletronico suportePontoeletronicoListNewSuportePontoeletronicoToAttach : suportePontoeletronicoListNew) {
                suportePontoeletronicoListNewSuportePontoeletronicoToAttach = em.getReference(suportePontoeletronicoListNewSuportePontoeletronicoToAttach.getClass(), suportePontoeletronicoListNewSuportePontoeletronicoToAttach.getIPonto());
                attachedSuportePontoeletronicoListNew.add(suportePontoeletronicoListNewSuportePontoeletronicoToAttach);
            }
            suportePontoeletronicoListNew = attachedSuportePontoeletronicoListNew;
            suportePeriodos.setSuportePontoeletronicoList(suportePontoeletronicoListNew);
            suportePeriodos = em.merge(suportePeriodos);
            for (SuportePontoeletronico suportePontoeletronicoListOldSuportePontoeletronico : suportePontoeletronicoListOld) {
                if (!suportePontoeletronicoListNew.contains(suportePontoeletronicoListOldSuportePontoeletronico)) {
                    suportePontoeletronicoListOldSuportePontoeletronico.setIPeriodo(null);
                    suportePontoeletronicoListOldSuportePontoeletronico = em.merge(suportePontoeletronicoListOldSuportePontoeletronico);
                }
            }
            for (SuportePontoeletronico suportePontoeletronicoListNewSuportePontoeletronico : suportePontoeletronicoListNew) {
                if (!suportePontoeletronicoListOld.contains(suportePontoeletronicoListNewSuportePontoeletronico)) {
                    SuportePeriodos oldIPeriodoOfSuportePontoeletronicoListNewSuportePontoeletronico = suportePontoeletronicoListNewSuportePontoeletronico.getIPeriodo();
                    suportePontoeletronicoListNewSuportePontoeletronico.setIPeriodo(suportePeriodos);
                    suportePontoeletronicoListNewSuportePontoeletronico = em.merge(suportePontoeletronicoListNewSuportePontoeletronico);
                    if (oldIPeriodoOfSuportePontoeletronicoListNewSuportePontoeletronico != null && !oldIPeriodoOfSuportePontoeletronicoListNewSuportePontoeletronico.equals(suportePeriodos)) {
                        oldIPeriodoOfSuportePontoeletronicoListNewSuportePontoeletronico.getSuportePontoeletronicoList().remove(suportePontoeletronicoListNewSuportePontoeletronico);
                        oldIPeriodoOfSuportePontoeletronicoListNewSuportePontoeletronico = em.merge(oldIPeriodoOfSuportePontoeletronicoListNewSuportePontoeletronico);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = suportePeriodos.getIPeriodo();
                if (findSuportePeriodos(id) == null) {
                    throw new NonexistentEntityException("The suportePeriodos with id " + id + " no longer exists.");
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
            SuportePeriodos suportePeriodos;
            try {
                suportePeriodos = em.getReference(SuportePeriodos.class, id);
                suportePeriodos.getIPeriodo();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The suportePeriodos with id " + id + " no longer exists.", enfe);
            }
            List<SuportePontoeletronico> suportePontoeletronicoList = suportePeriodos.getSuportePontoeletronicoList();
            for (SuportePontoeletronico suportePontoeletronicoListSuportePontoeletronico : suportePontoeletronicoList) {
                suportePontoeletronicoListSuportePontoeletronico.setIPeriodo(null);
                suportePontoeletronicoListSuportePontoeletronico = em.merge(suportePontoeletronicoListSuportePontoeletronico);
            }
            em.remove(suportePeriodos);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<SuportePeriodos> findSuportePeriodosEntities9() {
        return findSuportePeriodosEntities(true, -1, -1);
    }

    public List<SuportePeriodos> findSuportePeriodosEntities(int maxResults, int firstResult) {
        return findSuportePeriodosEntities(false, maxResults, firstResult);
    }

    private List<SuportePeriodos> findSuportePeriodosEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(SuportePeriodos.class));
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

    public SuportePeriodos findSuportePeriodos(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(SuportePeriodos.class, id);
        } finally {
            em.close();
        }
    }

    public int getSuportePeriodosCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<SuportePeriodos> rt = cq.from(SuportePeriodos.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

    //Método Novo
    public List<SuportePeriodos> findSuportePeriodosEntitiesOrdenado() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(SuportePeriodos.class));
            Query q = em.createQuery("select a FROM SuportePeriodos a ORDER BY a.iPeriodo DESC");
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public List<SuportePeriodos> findSuportePeriodosBuscaPorData(String dtRegistro) {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createQuery("select a FROM SuportePeriodos a WHERE '"+dtRegistro+"' BETWEEN a.dtInicio AND a.dtFim");
            return q.getResultList();
        } finally {
            em.close();
        }
    }

}
