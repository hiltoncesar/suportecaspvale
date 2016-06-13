/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controle;

import controle.exceptions.NonexistentEntityException;
import entidades.SuporteAcessoRemoto;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import entidades.SuporteEntidades;
import entidades.SuporteHistoricoAcesso;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Hilton
 */
public class SuporteAcessoRemotoJpaController implements Serializable {

    public SuporteAcessoRemotoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(SuporteAcessoRemoto suporteAcessoRemoto) {
        if (suporteAcessoRemoto.getSuporteHistoricoAcessoList() == null) {
            suporteAcessoRemoto.setSuporteHistoricoAcessoList(new ArrayList<SuporteHistoricoAcesso>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            SuporteEntidades IEntidade = suporteAcessoRemoto.getIEntidade();
            if (IEntidade != null) {
                IEntidade = em.getReference(IEntidade.getClass(), IEntidade.getIEntidade());
                suporteAcessoRemoto.setIEntidade(IEntidade);
            }
            List<SuporteHistoricoAcesso> attachedSuporteHistoricoAcessoList = new ArrayList<SuporteHistoricoAcesso>();
            for (SuporteHistoricoAcesso suporteHistoricoAcessoListSuporteHistoricoAcessoToAttach : suporteAcessoRemoto.getSuporteHistoricoAcessoList()) {
                suporteHistoricoAcessoListSuporteHistoricoAcessoToAttach = em.getReference(suporteHistoricoAcessoListSuporteHistoricoAcessoToAttach.getClass(), suporteHistoricoAcessoListSuporteHistoricoAcessoToAttach.getIHistorico());
                attachedSuporteHistoricoAcessoList.add(suporteHistoricoAcessoListSuporteHistoricoAcessoToAttach);
            }
            suporteAcessoRemoto.setSuporteHistoricoAcessoList(attachedSuporteHistoricoAcessoList);
            em.persist(suporteAcessoRemoto);
            if (IEntidade != null) {
                IEntidade.getSuporteAcessoRemotoList().add(suporteAcessoRemoto);
                IEntidade = em.merge(IEntidade);
            }
            for (SuporteHistoricoAcesso suporteHistoricoAcessoListSuporteHistoricoAcesso : suporteAcessoRemoto.getSuporteHistoricoAcessoList()) {
                SuporteAcessoRemoto oldIAcessoOfSuporteHistoricoAcessoListSuporteHistoricoAcesso = suporteHistoricoAcessoListSuporteHistoricoAcesso.getIAcesso();
                suporteHistoricoAcessoListSuporteHistoricoAcesso.setIAcesso(suporteAcessoRemoto);
                suporteHistoricoAcessoListSuporteHistoricoAcesso = em.merge(suporteHistoricoAcessoListSuporteHistoricoAcesso);
                if (oldIAcessoOfSuporteHistoricoAcessoListSuporteHistoricoAcesso != null) {
                    oldIAcessoOfSuporteHistoricoAcessoListSuporteHistoricoAcesso.getSuporteHistoricoAcessoList().remove(suporteHistoricoAcessoListSuporteHistoricoAcesso);
                    oldIAcessoOfSuporteHistoricoAcessoListSuporteHistoricoAcesso = em.merge(oldIAcessoOfSuporteHistoricoAcessoListSuporteHistoricoAcesso);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(SuporteAcessoRemoto suporteAcessoRemoto) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            SuporteAcessoRemoto persistentSuporteAcessoRemoto = em.find(SuporteAcessoRemoto.class, suporteAcessoRemoto.getIAcesso());
            SuporteEntidades IEntidadeOld = persistentSuporteAcessoRemoto.getIEntidade();
            SuporteEntidades IEntidadeNew = suporteAcessoRemoto.getIEntidade();
            List<SuporteHistoricoAcesso> suporteHistoricoAcessoListOld = persistentSuporteAcessoRemoto.getSuporteHistoricoAcessoList();
            List<SuporteHistoricoAcesso> suporteHistoricoAcessoListNew = suporteAcessoRemoto.getSuporteHistoricoAcessoList();
            if (IEntidadeNew != null) {
                IEntidadeNew = em.getReference(IEntidadeNew.getClass(), IEntidadeNew.getIEntidade());
                suporteAcessoRemoto.setIEntidade(IEntidadeNew);
            }
            List<SuporteHistoricoAcesso> attachedSuporteHistoricoAcessoListNew = new ArrayList<SuporteHistoricoAcesso>();
            for (SuporteHistoricoAcesso suporteHistoricoAcessoListNewSuporteHistoricoAcessoToAttach : suporteHistoricoAcessoListNew) {
                suporteHistoricoAcessoListNewSuporteHistoricoAcessoToAttach = em.getReference(suporteHistoricoAcessoListNewSuporteHistoricoAcessoToAttach.getClass(), suporteHistoricoAcessoListNewSuporteHistoricoAcessoToAttach.getIHistorico());
                attachedSuporteHistoricoAcessoListNew.add(suporteHistoricoAcessoListNewSuporteHistoricoAcessoToAttach);
            }
            suporteHistoricoAcessoListNew = attachedSuporteHistoricoAcessoListNew;
            suporteAcessoRemoto.setSuporteHistoricoAcessoList(suporteHistoricoAcessoListNew);
            suporteAcessoRemoto = em.merge(suporteAcessoRemoto);
            if (IEntidadeOld != null && !IEntidadeOld.equals(IEntidadeNew)) {
                IEntidadeOld.getSuporteAcessoRemotoList().remove(suporteAcessoRemoto);
                IEntidadeOld = em.merge(IEntidadeOld);
            }
            if (IEntidadeNew != null && !IEntidadeNew.equals(IEntidadeOld)) {
                IEntidadeNew.getSuporteAcessoRemotoList().add(suporteAcessoRemoto);
                IEntidadeNew = em.merge(IEntidadeNew);
            }
            for (SuporteHistoricoAcesso suporteHistoricoAcessoListOldSuporteHistoricoAcesso : suporteHistoricoAcessoListOld) {
                if (!suporteHistoricoAcessoListNew.contains(suporteHistoricoAcessoListOldSuporteHistoricoAcesso)) {
                    suporteHistoricoAcessoListOldSuporteHistoricoAcesso.setIAcesso(null);
                    suporteHistoricoAcessoListOldSuporteHistoricoAcesso = em.merge(suporteHistoricoAcessoListOldSuporteHistoricoAcesso);
                }
            }
            for (SuporteHistoricoAcesso suporteHistoricoAcessoListNewSuporteHistoricoAcesso : suporteHistoricoAcessoListNew) {
                if (!suporteHistoricoAcessoListOld.contains(suporteHistoricoAcessoListNewSuporteHistoricoAcesso)) {
                    SuporteAcessoRemoto oldIAcessoOfSuporteHistoricoAcessoListNewSuporteHistoricoAcesso = suporteHistoricoAcessoListNewSuporteHistoricoAcesso.getIAcesso();
                    suporteHistoricoAcessoListNewSuporteHistoricoAcesso.setIAcesso(suporteAcessoRemoto);
                    suporteHistoricoAcessoListNewSuporteHistoricoAcesso = em.merge(suporteHistoricoAcessoListNewSuporteHistoricoAcesso);
                    if (oldIAcessoOfSuporteHistoricoAcessoListNewSuporteHistoricoAcesso != null && !oldIAcessoOfSuporteHistoricoAcessoListNewSuporteHistoricoAcesso.equals(suporteAcessoRemoto)) {
                        oldIAcessoOfSuporteHistoricoAcessoListNewSuporteHistoricoAcesso.getSuporteHistoricoAcessoList().remove(suporteHistoricoAcessoListNewSuporteHistoricoAcesso);
                        oldIAcessoOfSuporteHistoricoAcessoListNewSuporteHistoricoAcesso = em.merge(oldIAcessoOfSuporteHistoricoAcessoListNewSuporteHistoricoAcesso);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = suporteAcessoRemoto.getIAcesso();
                if (findSuporteAcessoRemoto(id) == null) {
                    throw new NonexistentEntityException("The suporteAcessoRemoto with id " + id + " no longer exists.");
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
            SuporteAcessoRemoto suporteAcessoRemoto;
            try {
                suporteAcessoRemoto = em.getReference(SuporteAcessoRemoto.class, id);
                suporteAcessoRemoto.getIAcesso();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The suporteAcessoRemoto with id " + id + " no longer exists.", enfe);
            }
            SuporteEntidades IEntidade = suporteAcessoRemoto.getIEntidade();
            if (IEntidade != null) {
                IEntidade.getSuporteAcessoRemotoList().remove(suporteAcessoRemoto);
                IEntidade = em.merge(IEntidade);
            }
            List<SuporteHistoricoAcesso> suporteHistoricoAcessoList = suporteAcessoRemoto.getSuporteHistoricoAcessoList();
            for (SuporteHistoricoAcesso suporteHistoricoAcessoListSuporteHistoricoAcesso : suporteHistoricoAcessoList) {
                suporteHistoricoAcessoListSuporteHistoricoAcesso.setIAcesso(null);
                suporteHistoricoAcessoListSuporteHistoricoAcesso = em.merge(suporteHistoricoAcessoListSuporteHistoricoAcesso);
            }
            em.remove(suporteAcessoRemoto);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<SuporteAcessoRemoto> findSuporteAcessoRemotoEntities() {
        return findSuporteAcessoRemotoEntities(true, -1, -1);
    }

    public List<SuporteAcessoRemoto> findSuporteAcessoRemotoEntities(int maxResults, int firstResult) {
        return findSuporteAcessoRemotoEntities(false, maxResults, firstResult);
    }

    private List<SuporteAcessoRemoto> findSuporteAcessoRemotoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(SuporteAcessoRemoto.class));
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

    public SuporteAcessoRemoto findSuporteAcessoRemoto(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(SuporteAcessoRemoto.class, id);
        } finally {
            em.close();
        }
    }

    public int getSuporteAcessoRemotoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<SuporteAcessoRemoto> rt = cq.from(SuporteAcessoRemoto.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
 //Método novo
    public List<SuporteAcessoRemoto> findSuporteAcessoRemotoEntitiesOrdenado() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(SuporteAcessoRemoto.class));
            Query q = em.createQuery("SELECT s FROM SuporteAcessoRemoto s ORDER BY s.iEntidade.entidadeNome, s.nomeAcesso ASC");
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public List<SuporteAcessoRemoto> findSuporteAcessoRemotoEntitiesJDBC() {
        String sql = "SELECT i_acesso, \n"
                + "	(select u2.entidade_nome from suporte_entidades u2 where u2.i_entidade = a.i_entidade) as entidade,\n"
                + "	nome_acesso,\n"
                + "	comando, \n"
                + "	online\n"
                + "FROM Suporte_Acesso_Remoto a, suporte_entidades e\n"
                + "	where a.i_entidade = e.i_entidade\n"
                + "	ORDER BY e.entidade_nome,a.nome_Acesso ASC";
        ResultSet rs = null;
        util.Persistencia con = new util.Persistencia();
        List<SuporteAcessoRemoto> listAcessoRemoto = new ArrayList<SuporteAcessoRemoto>();
        PreparedStatement ps;
        try {
            ps = con.conectar().prepareStatement(sql);

            rs = ps.executeQuery();
            while (rs.next()) {
                listAcessoRemoto.add(
                        new SuporteAcessoRemoto(rs.getInt(1),
                                rs.getString(2),
                                rs.getString(3),
                                rs.getString(4),
                                rs.getBoolean(5)));
                System.out.println(listAcessoRemoto.get(0).getNomeAcesso());
            }
        } catch (SQLException ex) {
            Logger.getLogger(SuporteAcessoRemotoJpaController.class.getName()).log(Level.SEVERE, null, ex);
        }
        con.desconectar();
        return listAcessoRemoto;
    }
    
}
