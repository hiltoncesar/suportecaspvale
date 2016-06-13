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
import entidades.SuporteAcessoRemoto;
import java.util.ArrayList;
import java.util.List;
import entidades.SuporteAnexos;
import entidades.SuporteEntidades;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Hilton
 */
public class SuporteEntidadesJpaController implements Serializable {

    public SuporteEntidadesJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(SuporteEntidades suporteEntidades) {
        if (suporteEntidades.getSuporteAcessoRemotoList() == null) {
            suporteEntidades.setSuporteAcessoRemotoList(new ArrayList<SuporteAcessoRemoto>());
        }
        if (suporteEntidades.getSuporteAnexosList() == null) {
            suporteEntidades.setSuporteAnexosList(new ArrayList<SuporteAnexos>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<SuporteAcessoRemoto> attachedSuporteAcessoRemotoList = new ArrayList<SuporteAcessoRemoto>();
            for (SuporteAcessoRemoto suporteAcessoRemotoListSuporteAcessoRemotoToAttach : suporteEntidades.getSuporteAcessoRemotoList()) {
                suporteAcessoRemotoListSuporteAcessoRemotoToAttach = em.getReference(suporteAcessoRemotoListSuporteAcessoRemotoToAttach.getClass(), suporteAcessoRemotoListSuporteAcessoRemotoToAttach.getIAcesso());
                attachedSuporteAcessoRemotoList.add(suporteAcessoRemotoListSuporteAcessoRemotoToAttach);
            }
            suporteEntidades.setSuporteAcessoRemotoList(attachedSuporteAcessoRemotoList);
            List<SuporteAnexos> attachedSuporteAnexosList = new ArrayList<SuporteAnexos>();
            for (SuporteAnexos suporteAnexosListSuporteAnexosToAttach : suporteEntidades.getSuporteAnexosList()) {
                suporteAnexosListSuporteAnexosToAttach = em.getReference(suporteAnexosListSuporteAnexosToAttach.getClass(), suporteAnexosListSuporteAnexosToAttach.getIAnexo());
                attachedSuporteAnexosList.add(suporteAnexosListSuporteAnexosToAttach);
            }
            suporteEntidades.setSuporteAnexosList(attachedSuporteAnexosList);
            em.persist(suporteEntidades);
            for (SuporteAcessoRemoto suporteAcessoRemotoListSuporteAcessoRemoto : suporteEntidades.getSuporteAcessoRemotoList()) {
                SuporteEntidades oldIEntidadeOfSuporteAcessoRemotoListSuporteAcessoRemoto = suporteAcessoRemotoListSuporteAcessoRemoto.getIEntidade();
                suporteAcessoRemotoListSuporteAcessoRemoto.setIEntidade(suporteEntidades);
                suporteAcessoRemotoListSuporteAcessoRemoto = em.merge(suporteAcessoRemotoListSuporteAcessoRemoto);
                if (oldIEntidadeOfSuporteAcessoRemotoListSuporteAcessoRemoto != null) {
                    oldIEntidadeOfSuporteAcessoRemotoListSuporteAcessoRemoto.getSuporteAcessoRemotoList().remove(suporteAcessoRemotoListSuporteAcessoRemoto);
                    oldIEntidadeOfSuporteAcessoRemotoListSuporteAcessoRemoto = em.merge(oldIEntidadeOfSuporteAcessoRemotoListSuporteAcessoRemoto);
                }
            }
            for (SuporteAnexos suporteAnexosListSuporteAnexos : suporteEntidades.getSuporteAnexosList()) {
                SuporteEntidades oldIEntidadeOfSuporteAnexosListSuporteAnexos = suporteAnexosListSuporteAnexos.getIEntidade();
                suporteAnexosListSuporteAnexos.setIEntidade(suporteEntidades);
                suporteAnexosListSuporteAnexos = em.merge(suporteAnexosListSuporteAnexos);
                if (oldIEntidadeOfSuporteAnexosListSuporteAnexos != null) {
                    oldIEntidadeOfSuporteAnexosListSuporteAnexos.getSuporteAnexosList().remove(suporteAnexosListSuporteAnexos);
                    oldIEntidadeOfSuporteAnexosListSuporteAnexos = em.merge(oldIEntidadeOfSuporteAnexosListSuporteAnexos);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(SuporteEntidades suporteEntidades) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            SuporteEntidades persistentSuporteEntidades = em.find(SuporteEntidades.class, suporteEntidades.getIEntidade());
            List<SuporteAcessoRemoto> suporteAcessoRemotoListOld = persistentSuporteEntidades.getSuporteAcessoRemotoList();
            List<SuporteAcessoRemoto> suporteAcessoRemotoListNew = suporteEntidades.getSuporteAcessoRemotoList();
            List<SuporteAnexos> suporteAnexosListOld = persistentSuporteEntidades.getSuporteAnexosList();
            List<SuporteAnexos> suporteAnexosListNew = suporteEntidades.getSuporteAnexosList();
            List<SuporteAcessoRemoto> attachedSuporteAcessoRemotoListNew = new ArrayList<SuporteAcessoRemoto>();
            for (SuporteAcessoRemoto suporteAcessoRemotoListNewSuporteAcessoRemotoToAttach : suporteAcessoRemotoListNew) {
                suporteAcessoRemotoListNewSuporteAcessoRemotoToAttach = em.getReference(suporteAcessoRemotoListNewSuporteAcessoRemotoToAttach.getClass(), suporteAcessoRemotoListNewSuporteAcessoRemotoToAttach.getIAcesso());
                attachedSuporteAcessoRemotoListNew.add(suporteAcessoRemotoListNewSuporteAcessoRemotoToAttach);
            }
            suporteAcessoRemotoListNew = attachedSuporteAcessoRemotoListNew;
            suporteEntidades.setSuporteAcessoRemotoList(suporteAcessoRemotoListNew);
            List<SuporteAnexos> attachedSuporteAnexosListNew = new ArrayList<SuporteAnexos>();
            for (SuporteAnexos suporteAnexosListNewSuporteAnexosToAttach : suporteAnexosListNew) {
                suporteAnexosListNewSuporteAnexosToAttach = em.getReference(suporteAnexosListNewSuporteAnexosToAttach.getClass(), suporteAnexosListNewSuporteAnexosToAttach.getIAnexo());
                attachedSuporteAnexosListNew.add(suporteAnexosListNewSuporteAnexosToAttach);
            }
            suporteAnexosListNew = attachedSuporteAnexosListNew;
            suporteEntidades.setSuporteAnexosList(suporteAnexosListNew);
            suporteEntidades = em.merge(suporteEntidades);
            for (SuporteAcessoRemoto suporteAcessoRemotoListOldSuporteAcessoRemoto : suporteAcessoRemotoListOld) {
                if (!suporteAcessoRemotoListNew.contains(suporteAcessoRemotoListOldSuporteAcessoRemoto)) {
                    suporteAcessoRemotoListOldSuporteAcessoRemoto.setIEntidade(null);
                    suporteAcessoRemotoListOldSuporteAcessoRemoto = em.merge(suporteAcessoRemotoListOldSuporteAcessoRemoto);
                }
            }
            for (SuporteAcessoRemoto suporteAcessoRemotoListNewSuporteAcessoRemoto : suporteAcessoRemotoListNew) {
                if (!suporteAcessoRemotoListOld.contains(suporteAcessoRemotoListNewSuporteAcessoRemoto)) {
                    SuporteEntidades oldIEntidadeOfSuporteAcessoRemotoListNewSuporteAcessoRemoto = suporteAcessoRemotoListNewSuporteAcessoRemoto.getIEntidade();
                    suporteAcessoRemotoListNewSuporteAcessoRemoto.setIEntidade(suporteEntidades);
                    suporteAcessoRemotoListNewSuporteAcessoRemoto = em.merge(suporteAcessoRemotoListNewSuporteAcessoRemoto);
                    if (oldIEntidadeOfSuporteAcessoRemotoListNewSuporteAcessoRemoto != null && !oldIEntidadeOfSuporteAcessoRemotoListNewSuporteAcessoRemoto.equals(suporteEntidades)) {
                        oldIEntidadeOfSuporteAcessoRemotoListNewSuporteAcessoRemoto.getSuporteAcessoRemotoList().remove(suporteAcessoRemotoListNewSuporteAcessoRemoto);
                        oldIEntidadeOfSuporteAcessoRemotoListNewSuporteAcessoRemoto = em.merge(oldIEntidadeOfSuporteAcessoRemotoListNewSuporteAcessoRemoto);
                    }
                }
            }
            for (SuporteAnexos suporteAnexosListOldSuporteAnexos : suporteAnexosListOld) {
                if (!suporteAnexosListNew.contains(suporteAnexosListOldSuporteAnexos)) {
                    suporteAnexosListOldSuporteAnexos.setIEntidade(null);
                    suporteAnexosListOldSuporteAnexos = em.merge(suporteAnexosListOldSuporteAnexos);
                }
            }
            for (SuporteAnexos suporteAnexosListNewSuporteAnexos : suporteAnexosListNew) {
                if (!suporteAnexosListOld.contains(suporteAnexosListNewSuporteAnexos)) {
                    SuporteEntidades oldIEntidadeOfSuporteAnexosListNewSuporteAnexos = suporteAnexosListNewSuporteAnexos.getIEntidade();
                    suporteAnexosListNewSuporteAnexos.setIEntidade(suporteEntidades);
                    suporteAnexosListNewSuporteAnexos = em.merge(suporteAnexosListNewSuporteAnexos);
                    if (oldIEntidadeOfSuporteAnexosListNewSuporteAnexos != null && !oldIEntidadeOfSuporteAnexosListNewSuporteAnexos.equals(suporteEntidades)) {
                        oldIEntidadeOfSuporteAnexosListNewSuporteAnexos.getSuporteAnexosList().remove(suporteAnexosListNewSuporteAnexos);
                        oldIEntidadeOfSuporteAnexosListNewSuporteAnexos = em.merge(oldIEntidadeOfSuporteAnexosListNewSuporteAnexos);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = suporteEntidades.getIEntidade();
                if (findSuporteEntidades(id) == null) {
                    throw new NonexistentEntityException("The suporteEntidades with id " + id + " no longer exists.");
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
            SuporteEntidades suporteEntidades;
            try {
                suporteEntidades = em.getReference(SuporteEntidades.class, id);
                suporteEntidades.getIEntidade();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The suporteEntidades with id " + id + " no longer exists.", enfe);
            }
            List<SuporteAcessoRemoto> suporteAcessoRemotoList = suporteEntidades.getSuporteAcessoRemotoList();
            for (SuporteAcessoRemoto suporteAcessoRemotoListSuporteAcessoRemoto : suporteAcessoRemotoList) {
                suporteAcessoRemotoListSuporteAcessoRemoto.setIEntidade(null);
                suporteAcessoRemotoListSuporteAcessoRemoto = em.merge(suporteAcessoRemotoListSuporteAcessoRemoto);
            }
            List<SuporteAnexos> suporteAnexosList = suporteEntidades.getSuporteAnexosList();
            for (SuporteAnexos suporteAnexosListSuporteAnexos : suporteAnexosList) {
                suporteAnexosListSuporteAnexos.setIEntidade(null);
                suporteAnexosListSuporteAnexos = em.merge(suporteAnexosListSuporteAnexos);
            }
            em.remove(suporteEntidades);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<SuporteEntidades> findSuporteEntidadesEntities() {
        return findSuporteEntidadesEntities(true, -1, -1);
    }

    public List<SuporteEntidades> findSuporteEntidadesEntities(int maxResults, int firstResult) {
        return findSuporteEntidadesEntities(false, maxResults, firstResult);
    }

    private List<SuporteEntidades> findSuporteEntidadesEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(SuporteEntidades.class));
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

    public SuporteEntidades findSuporteEntidades(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(SuporteEntidades.class, id);
        } finally {
            em.close();
        }
    }

    public int getSuporteEntidadesCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<SuporteEntidades> rt = cq.from(SuporteEntidades.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

    
//Método Novo
public List<SuporteEntidades> findSuporteEntidadesEntitiesOrdenado() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(SuporteEntidades.class));
            Query q = em.createQuery("select e FROM SuporteEntidades e ORDER BY e.entidadeNome ASC");
            return q.getResultList();
        } finally {
            em.close();
        }
    }
}
