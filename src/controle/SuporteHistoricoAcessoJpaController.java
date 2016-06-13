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
import entidades.SuporteHistoricoAcesso;
import entidades.SuporteUsuarios;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Hilton
 */
public class SuporteHistoricoAcessoJpaController implements Serializable {

    public SuporteHistoricoAcessoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(SuporteHistoricoAcesso suporteHistoricoAcesso) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            SuporteAcessoRemoto IAcesso = suporteHistoricoAcesso.getIAcesso();
            if (IAcesso != null) {
                IAcesso = em.getReference(IAcesso.getClass(), IAcesso.getIAcesso());
                suporteHistoricoAcesso.setIAcesso(IAcesso);
            }
            SuporteUsuarios IUsuario = suporteHistoricoAcesso.getIUsuario();
            if (IUsuario != null) {
                IUsuario = em.getReference(IUsuario.getClass(), IUsuario.getIUsuario());
                suporteHistoricoAcesso.setIUsuario(IUsuario);
            }
            em.persist(suporteHistoricoAcesso);
            if (IAcesso != null) {
                IAcesso.getSuporteHistoricoAcessoList().add(suporteHistoricoAcesso);
                IAcesso = em.merge(IAcesso);
            }
            if (IUsuario != null) {
                IUsuario.getSuporteHistoricoAcessoList().add(suporteHistoricoAcesso);
                IUsuario = em.merge(IUsuario);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(SuporteHistoricoAcesso suporteHistoricoAcesso) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            SuporteHistoricoAcesso persistentSuporteHistoricoAcesso = em.find(SuporteHistoricoAcesso.class, suporteHistoricoAcesso.getIHistorico());
            SuporteAcessoRemoto IAcessoOld = persistentSuporteHistoricoAcesso.getIAcesso();
            SuporteAcessoRemoto IAcessoNew = suporteHistoricoAcesso.getIAcesso();
            SuporteUsuarios IUsuarioOld = persistentSuporteHistoricoAcesso.getIUsuario();
            SuporteUsuarios IUsuarioNew = suporteHistoricoAcesso.getIUsuario();
            if (IAcessoNew != null) {
                IAcessoNew = em.getReference(IAcessoNew.getClass(), IAcessoNew.getIAcesso());
                suporteHistoricoAcesso.setIAcesso(IAcessoNew);
            }
            if (IUsuarioNew != null) {
                IUsuarioNew = em.getReference(IUsuarioNew.getClass(), IUsuarioNew.getIUsuario());
                suporteHistoricoAcesso.setIUsuario(IUsuarioNew);
            }
            suporteHistoricoAcesso = em.merge(suporteHistoricoAcesso);
            if (IAcessoOld != null && !IAcessoOld.equals(IAcessoNew)) {
                IAcessoOld.getSuporteHistoricoAcessoList().remove(suporteHistoricoAcesso);
                IAcessoOld = em.merge(IAcessoOld);
            }
            if (IAcessoNew != null && !IAcessoNew.equals(IAcessoOld)) {
                IAcessoNew.getSuporteHistoricoAcessoList().add(suporteHistoricoAcesso);
                IAcessoNew = em.merge(IAcessoNew);
            }
            if (IUsuarioOld != null && !IUsuarioOld.equals(IUsuarioNew)) {
                IUsuarioOld.getSuporteHistoricoAcessoList().remove(suporteHistoricoAcesso);
                IUsuarioOld = em.merge(IUsuarioOld);
            }
            if (IUsuarioNew != null && !IUsuarioNew.equals(IUsuarioOld)) {
                IUsuarioNew.getSuporteHistoricoAcessoList().add(suporteHistoricoAcesso);
                IUsuarioNew = em.merge(IUsuarioNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = suporteHistoricoAcesso.getIHistorico();
                if (findSuporteHistoricoAcesso(id) == null) {
                    throw new NonexistentEntityException("The suporteHistoricoAcesso with id " + id + " no longer exists.");
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
            SuporteHistoricoAcesso suporteHistoricoAcesso;
            try {
                suporteHistoricoAcesso = em.getReference(SuporteHistoricoAcesso.class, id);
                suporteHistoricoAcesso.getIHistorico();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The suporteHistoricoAcesso with id " + id + " no longer exists.", enfe);
            }
            SuporteAcessoRemoto IAcesso = suporteHistoricoAcesso.getIAcesso();
            if (IAcesso != null) {
                IAcesso.getSuporteHistoricoAcessoList().remove(suporteHistoricoAcesso);
                IAcesso = em.merge(IAcesso);
            }
            SuporteUsuarios IUsuario = suporteHistoricoAcesso.getIUsuario();
            if (IUsuario != null) {
                IUsuario.getSuporteHistoricoAcessoList().remove(suporteHistoricoAcesso);
                IUsuario = em.merge(IUsuario);
            }
            em.remove(suporteHistoricoAcesso);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<SuporteHistoricoAcesso> findSuporteHistoricoAcessoEntities() {
        return findSuporteHistoricoAcessoEntities(true, -1, -1);
    }

    public List<SuporteHistoricoAcesso> findSuporteHistoricoAcessoEntities(int maxResults, int firstResult) {
        return findSuporteHistoricoAcessoEntities(false, maxResults, firstResult);
    }

    private List<SuporteHistoricoAcesso> findSuporteHistoricoAcessoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(SuporteHistoricoAcesso.class));
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

    public SuporteHistoricoAcesso findSuporteHistoricoAcesso(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(SuporteHistoricoAcesso.class, id);
        } finally {
            em.close();
        }
    }

    public int getSuporteHistoricoAcessoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<SuporteHistoricoAcesso> rt = cq.from(SuporteHistoricoAcesso.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

    
//Método Novo
public List<SuporteHistoricoAcesso> findSuporteHistoricoAcessoUltimos(int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(SuporteHistoricoAcesso.class));
            Query q = em.createQuery("SELECT s FROM SuporteHistoricoAcesso s ORDER BY s.iHistorico DESC");
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            return q.getResultList();
        } finally {
            em.close();
        }
    }
}
