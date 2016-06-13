/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controle;

import controle.exceptions.NonexistentEntityException;
import entidades.SuporteAreas;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import entidades.SuporteUsuarios;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Hilton
 */
public class SuporteAreasJpaController implements Serializable {

    public SuporteAreasJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(SuporteAreas suporteAreas) {
        if (suporteAreas.getSuporteUsuariosList() == null) {
            suporteAreas.setSuporteUsuariosList(new ArrayList<SuporteUsuarios>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<SuporteUsuarios> attachedSuporteUsuariosList = new ArrayList<SuporteUsuarios>();
            for (SuporteUsuarios suporteUsuariosListSuporteUsuariosToAttach : suporteAreas.getSuporteUsuariosList()) {
                suporteUsuariosListSuporteUsuariosToAttach = em.getReference(suporteUsuariosListSuporteUsuariosToAttach.getClass(), suporteUsuariosListSuporteUsuariosToAttach.getIUsuario());
                attachedSuporteUsuariosList.add(suporteUsuariosListSuporteUsuariosToAttach);
            }
            suporteAreas.setSuporteUsuariosList(attachedSuporteUsuariosList);
            em.persist(suporteAreas);
            for (SuporteUsuarios suporteUsuariosListSuporteUsuarios : suporteAreas.getSuporteUsuariosList()) {
                SuporteAreas oldIAreaOfSuporteUsuariosListSuporteUsuarios = suporteUsuariosListSuporteUsuarios.getIArea();
                suporteUsuariosListSuporteUsuarios.setIArea(suporteAreas);
                suporteUsuariosListSuporteUsuarios = em.merge(suporteUsuariosListSuporteUsuarios);
                if (oldIAreaOfSuporteUsuariosListSuporteUsuarios != null) {
                    oldIAreaOfSuporteUsuariosListSuporteUsuarios.getSuporteUsuariosList().remove(suporteUsuariosListSuporteUsuarios);
                    oldIAreaOfSuporteUsuariosListSuporteUsuarios = em.merge(oldIAreaOfSuporteUsuariosListSuporteUsuarios);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(SuporteAreas suporteAreas) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            SuporteAreas persistentSuporteAreas = em.find(SuporteAreas.class, suporteAreas.getIArea());
            List<SuporteUsuarios> suporteUsuariosListOld = persistentSuporteAreas.getSuporteUsuariosList();
            List<SuporteUsuarios> suporteUsuariosListNew = suporteAreas.getSuporteUsuariosList();
            List<SuporteUsuarios> attachedSuporteUsuariosListNew = new ArrayList<SuporteUsuarios>();
            for (SuporteUsuarios suporteUsuariosListNewSuporteUsuariosToAttach : suporteUsuariosListNew) {
                suporteUsuariosListNewSuporteUsuariosToAttach = em.getReference(suporteUsuariosListNewSuporteUsuariosToAttach.getClass(), suporteUsuariosListNewSuporteUsuariosToAttach.getIUsuario());
                attachedSuporteUsuariosListNew.add(suporteUsuariosListNewSuporteUsuariosToAttach);
            }
            suporteUsuariosListNew = attachedSuporteUsuariosListNew;
            suporteAreas.setSuporteUsuariosList(suporteUsuariosListNew);
            suporteAreas = em.merge(suporteAreas);
            for (SuporteUsuarios suporteUsuariosListOldSuporteUsuarios : suporteUsuariosListOld) {
                if (!suporteUsuariosListNew.contains(suporteUsuariosListOldSuporteUsuarios)) {
                    suporteUsuariosListOldSuporteUsuarios.setIArea(null);
                    suporteUsuariosListOldSuporteUsuarios = em.merge(suporteUsuariosListOldSuporteUsuarios);
                }
            }
            for (SuporteUsuarios suporteUsuariosListNewSuporteUsuarios : suporteUsuariosListNew) {
                if (!suporteUsuariosListOld.contains(suporteUsuariosListNewSuporteUsuarios)) {
                    SuporteAreas oldIAreaOfSuporteUsuariosListNewSuporteUsuarios = suporteUsuariosListNewSuporteUsuarios.getIArea();
                    suporteUsuariosListNewSuporteUsuarios.setIArea(suporteAreas);
                    suporteUsuariosListNewSuporteUsuarios = em.merge(suporteUsuariosListNewSuporteUsuarios);
                    if (oldIAreaOfSuporteUsuariosListNewSuporteUsuarios != null && !oldIAreaOfSuporteUsuariosListNewSuporteUsuarios.equals(suporteAreas)) {
                        oldIAreaOfSuporteUsuariosListNewSuporteUsuarios.getSuporteUsuariosList().remove(suporteUsuariosListNewSuporteUsuarios);
                        oldIAreaOfSuporteUsuariosListNewSuporteUsuarios = em.merge(oldIAreaOfSuporteUsuariosListNewSuporteUsuarios);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = suporteAreas.getIArea();
                if (findSuporteAreas(id) == null) {
                    throw new NonexistentEntityException("The suporteAreas with id " + id + " no longer exists.");
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
            SuporteAreas suporteAreas;
            try {
                suporteAreas = em.getReference(SuporteAreas.class, id);
                suporteAreas.getIArea();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The suporteAreas with id " + id + " no longer exists.", enfe);
            }
            List<SuporteUsuarios> suporteUsuariosList = suporteAreas.getSuporteUsuariosList();
            for (SuporteUsuarios suporteUsuariosListSuporteUsuarios : suporteUsuariosList) {
                suporteUsuariosListSuporteUsuarios.setIArea(null);
                suporteUsuariosListSuporteUsuarios = em.merge(suporteUsuariosListSuporteUsuarios);
            }
            em.remove(suporteAreas);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<SuporteAreas> findSuporteAreasEntities() {
        return findSuporteAreasEntities(true, -1, -1);
    }

    public List<SuporteAreas> findSuporteAreasEntities(int maxResults, int firstResult) {
        return findSuporteAreasEntities(false, maxResults, firstResult);
    }

    private List<SuporteAreas> findSuporteAreasEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(SuporteAreas.class));
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

    public SuporteAreas findSuporteAreas(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(SuporteAreas.class, id);
        } finally {
            em.close();
        }
    }

    public int getSuporteAreasCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<SuporteAreas> rt = cq.from(SuporteAreas.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
    
 //Método Novo
public List<SuporteAreas> findSuporteAreasEntitiesOrdenado() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(SuporteAreas.class));
            Query q = em.createQuery("select a FROM SuporteAreas a ORDER BY a.areaNome");
            return q.getResultList();
        } finally {
            em.close();
        }
    }
}
