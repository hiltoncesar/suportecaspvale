/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controle;

import controle.exceptions.NonexistentEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import entidades.SuporteAnexos;
import entidades.SuporteCategorias;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Hilton
 */
public class SuporteCategoriasJpaController implements Serializable {

    public SuporteCategoriasJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(SuporteCategorias suporteCategorias) {
        if (suporteCategorias.getSuporteAnexosList() == null) {
            suporteCategorias.setSuporteAnexosList(new ArrayList<SuporteAnexos>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<SuporteAnexos> attachedSuporteAnexosList = new ArrayList<SuporteAnexos>();
            for (SuporteAnexos suporteAnexosListSuporteAnexosToAttach : suporteCategorias.getSuporteAnexosList()) {
                suporteAnexosListSuporteAnexosToAttach = em.getReference(suporteAnexosListSuporteAnexosToAttach.getClass(), suporteAnexosListSuporteAnexosToAttach.getIAnexo());
                attachedSuporteAnexosList.add(suporteAnexosListSuporteAnexosToAttach);
            }
            suporteCategorias.setSuporteAnexosList(attachedSuporteAnexosList);
            em.persist(suporteCategorias);
            for (SuporteAnexos suporteAnexosListSuporteAnexos : suporteCategorias.getSuporteAnexosList()) {
                SuporteCategorias oldICategoriaOfSuporteAnexosListSuporteAnexos = suporteAnexosListSuporteAnexos.getICategoria();
                suporteAnexosListSuporteAnexos.setICategoria(suporteCategorias);
                suporteAnexosListSuporteAnexos = em.merge(suporteAnexosListSuporteAnexos);
                if (oldICategoriaOfSuporteAnexosListSuporteAnexos != null) {
                    oldICategoriaOfSuporteAnexosListSuporteAnexos.getSuporteAnexosList().remove(suporteAnexosListSuporteAnexos);
                    oldICategoriaOfSuporteAnexosListSuporteAnexos = em.merge(oldICategoriaOfSuporteAnexosListSuporteAnexos);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(SuporteCategorias suporteCategorias) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            SuporteCategorias persistentSuporteCategorias = em.find(SuporteCategorias.class, suporteCategorias.getICategoria());
            List<SuporteAnexos> suporteAnexosListOld = persistentSuporteCategorias.getSuporteAnexosList();
            List<SuporteAnexos> suporteAnexosListNew = suporteCategorias.getSuporteAnexosList();
            List<SuporteAnexos> attachedSuporteAnexosListNew = new ArrayList<SuporteAnexos>();
            for (SuporteAnexos suporteAnexosListNewSuporteAnexosToAttach : suporteAnexosListNew) {
                suporteAnexosListNewSuporteAnexosToAttach = em.getReference(suporteAnexosListNewSuporteAnexosToAttach.getClass(), suporteAnexosListNewSuporteAnexosToAttach.getIAnexo());
                attachedSuporteAnexosListNew.add(suporteAnexosListNewSuporteAnexosToAttach);
            }
            suporteAnexosListNew = attachedSuporteAnexosListNew;
            suporteCategorias.setSuporteAnexosList(suporteAnexosListNew);
            suporteCategorias = em.merge(suporteCategorias);
            for (SuporteAnexos suporteAnexosListOldSuporteAnexos : suporteAnexosListOld) {
                if (!suporteAnexosListNew.contains(suporteAnexosListOldSuporteAnexos)) {
                    suporteAnexosListOldSuporteAnexos.setICategoria(null);
                    suporteAnexosListOldSuporteAnexos = em.merge(suporteAnexosListOldSuporteAnexos);
                }
            }
            for (SuporteAnexos suporteAnexosListNewSuporteAnexos : suporteAnexosListNew) {
                if (!suporteAnexosListOld.contains(suporteAnexosListNewSuporteAnexos)) {
                    SuporteCategorias oldICategoriaOfSuporteAnexosListNewSuporteAnexos = suporteAnexosListNewSuporteAnexos.getICategoria();
                    suporteAnexosListNewSuporteAnexos.setICategoria(suporteCategorias);
                    suporteAnexosListNewSuporteAnexos = em.merge(suporteAnexosListNewSuporteAnexos);
                    if (oldICategoriaOfSuporteAnexosListNewSuporteAnexos != null && !oldICategoriaOfSuporteAnexosListNewSuporteAnexos.equals(suporteCategorias)) {
                        oldICategoriaOfSuporteAnexosListNewSuporteAnexos.getSuporteAnexosList().remove(suporteAnexosListNewSuporteAnexos);
                        oldICategoriaOfSuporteAnexosListNewSuporteAnexos = em.merge(oldICategoriaOfSuporteAnexosListNewSuporteAnexos);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = suporteCategorias.getICategoria();
                if (findSuporteCategorias(id) == null) {
                    throw new NonexistentEntityException("The suporteCategorias with id " + id + " no longer exists.");
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
            SuporteCategorias suporteCategorias;
            try {
                suporteCategorias = em.getReference(SuporteCategorias.class, id);
                suporteCategorias.getICategoria();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The suporteCategorias with id " + id + " no longer exists.", enfe);
            }
            List<SuporteAnexos> suporteAnexosList = suporteCategorias.getSuporteAnexosList();
            for (SuporteAnexos suporteAnexosListSuporteAnexos : suporteAnexosList) {
                suporteAnexosListSuporteAnexos.setICategoria(null);
                suporteAnexosListSuporteAnexos = em.merge(suporteAnexosListSuporteAnexos);
            }
            em.remove(suporteCategorias);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<SuporteCategorias> findSuporteCategoriasEntities() {
        return findSuporteCategoriasEntities(true, -1, -1);
    }

    public List<SuporteCategorias> findSuporteCategoriasEntities(int maxResults, int firstResult) {
        return findSuporteCategoriasEntities(false, maxResults, firstResult);
    }

    private List<SuporteCategorias> findSuporteCategoriasEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(SuporteCategorias.class));
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

    public SuporteCategorias findSuporteCategorias(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(SuporteCategorias.class, id);
        } finally {
            em.close();
        }
    }

    public int getSuporteCategoriasCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<SuporteCategorias> rt = cq.from(SuporteCategorias.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

//Método novo
public List<SuporteCategorias> findSuporteCategoriasEntitiesOrdenado() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(SuporteCategorias.class));
            Query q = em.createQuery("select c FROM SuporteCategorias c ORDER BY C.categoriaNome ASC");
            return q.getResultList();
        } finally {
            em.close();
        }
    }
}
