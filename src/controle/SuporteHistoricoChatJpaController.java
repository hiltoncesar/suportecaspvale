/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controle;

import controle.exceptions.NonexistentEntityException;
import entidades.SuporteHistoricoChat;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import entidades.SuporteUsuarios;
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
public class SuporteHistoricoChatJpaController implements Serializable {

    public SuporteHistoricoChatJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(SuporteHistoricoChat suporteHistoricoChat) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            SuporteUsuarios IUsuarioDe = suporteHistoricoChat.getIUsuarioDe();
            if (IUsuarioDe != null) {
                IUsuarioDe = em.getReference(IUsuarioDe.getClass(), IUsuarioDe.getIUsuario());
                suporteHistoricoChat.setIUsuarioDe(IUsuarioDe);
            }
            SuporteUsuarios IUsuarioPara = suporteHistoricoChat.getIUsuarioPara();
            if (IUsuarioPara != null) {
                IUsuarioPara = em.getReference(IUsuarioPara.getClass(), IUsuarioPara.getIUsuario());
                suporteHistoricoChat.setIUsuarioPara(IUsuarioPara);
            }
            em.persist(suporteHistoricoChat);
            if (IUsuarioDe != null) {
                IUsuarioDe.getSuporteHistoricoChatList().add(suporteHistoricoChat);
                IUsuarioDe = em.merge(IUsuarioDe);
            }
            if (IUsuarioPara != null) {
                IUsuarioPara.getSuporteHistoricoChatList().add(suporteHistoricoChat);
                IUsuarioPara = em.merge(IUsuarioPara);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(SuporteHistoricoChat suporteHistoricoChat) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            SuporteHistoricoChat persistentSuporteHistoricoChat = em.find(SuporteHistoricoChat.class, suporteHistoricoChat.getIHistoricoChat());
            SuporteUsuarios IUsuarioDeOld = persistentSuporteHistoricoChat.getIUsuarioDe();
            SuporteUsuarios IUsuarioDeNew = suporteHistoricoChat.getIUsuarioDe();
            SuporteUsuarios IUsuarioParaOld = persistentSuporteHistoricoChat.getIUsuarioPara();
            SuporteUsuarios IUsuarioParaNew = suporteHistoricoChat.getIUsuarioPara();
            if (IUsuarioDeNew != null) {
                IUsuarioDeNew = em.getReference(IUsuarioDeNew.getClass(), IUsuarioDeNew.getIUsuario());
                suporteHistoricoChat.setIUsuarioDe(IUsuarioDeNew);
            }
            if (IUsuarioParaNew != null) {
                IUsuarioParaNew = em.getReference(IUsuarioParaNew.getClass(), IUsuarioParaNew.getIUsuario());
                suporteHistoricoChat.setIUsuarioPara(IUsuarioParaNew);
            }
            suporteHistoricoChat = em.merge(suporteHistoricoChat);
            if (IUsuarioDeOld != null && !IUsuarioDeOld.equals(IUsuarioDeNew)) {
                IUsuarioDeOld.getSuporteHistoricoChatList().remove(suporteHistoricoChat);
                IUsuarioDeOld = em.merge(IUsuarioDeOld);
            }
            if (IUsuarioDeNew != null && !IUsuarioDeNew.equals(IUsuarioDeOld)) {
                IUsuarioDeNew.getSuporteHistoricoChatList().add(suporteHistoricoChat);
                IUsuarioDeNew = em.merge(IUsuarioDeNew);
            }
            if (IUsuarioParaOld != null && !IUsuarioParaOld.equals(IUsuarioParaNew)) {
                IUsuarioParaOld.getSuporteHistoricoChatList().remove(suporteHistoricoChat);
                IUsuarioParaOld = em.merge(IUsuarioParaOld);
            }
            if (IUsuarioParaNew != null && !IUsuarioParaNew.equals(IUsuarioParaOld)) {
                IUsuarioParaNew.getSuporteHistoricoChatList().add(suporteHistoricoChat);
                IUsuarioParaNew = em.merge(IUsuarioParaNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = suporteHistoricoChat.getIHistoricoChat();
                if (findSuporteHistoricoChat(id) == null) {
                    throw new NonexistentEntityException("The suporteHistoricoChat with id " + id + " no longer exists.");
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
            SuporteHistoricoChat suporteHistoricoChat;
            try {
                suporteHistoricoChat = em.getReference(SuporteHistoricoChat.class, id);
                suporteHistoricoChat.getIHistoricoChat();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The suporteHistoricoChat with id " + id + " no longer exists.", enfe);
            }
            SuporteUsuarios IUsuarioDe = suporteHistoricoChat.getIUsuarioDe();
            if (IUsuarioDe != null) {
                IUsuarioDe.getSuporteHistoricoChatList().remove(suporteHistoricoChat);
                IUsuarioDe = em.merge(IUsuarioDe);
            }
            SuporteUsuarios IUsuarioPara = suporteHistoricoChat.getIUsuarioPara();
            if (IUsuarioPara != null) {
                IUsuarioPara.getSuporteHistoricoChatList().remove(suporteHistoricoChat);
                IUsuarioPara = em.merge(IUsuarioPara);
            }
            em.remove(suporteHistoricoChat);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<SuporteHistoricoChat> findSuporteHistoricoChatEntities() {
        return findSuporteHistoricoChatEntities(true, -1, -1);
    }

    public List<SuporteHistoricoChat> findSuporteHistoricoChatEntities(int maxResults, int firstResult) {
        return findSuporteHistoricoChatEntities(false, maxResults, firstResult);
    }

    private List<SuporteHistoricoChat> findSuporteHistoricoChatEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(SuporteHistoricoChat.class));
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

    public SuporteHistoricoChat findSuporteHistoricoChat(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(SuporteHistoricoChat.class, id);
        } finally {
            em.close();
        }
    }

    public int getSuporteHistoricoChatCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<SuporteHistoricoChat> rt = cq.from(SuporteHistoricoChat.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
//Método Novo
 public List<SuporteHistoricoChat> findSuporteHistoricoChatEntitiesSemAnexo(int i_usu_de, int i_usu_para) throws SQLException {
        String sql = "select hc.i_historico_chat, \n"
                + "(select u2.usuario_nome from suporte_usuarios u2 where u2.i_usuario = hc.i_usuario_de) as usude,\n"
                + "(select u2.usuario_nome from suporte_usuarios u2 where u2.i_usuario = hc.i_usuario_para) as usupara,\n"
                + "hc.alteracao, \n"
                + "hc.texto \n"
                + "from suporte_historico_chat hc,suporte_usuarios u \n"
                + "where u.i_usuario = hc.i_usuario_de\n"
                + "and hc.i_usuario_de = "+i_usu_de+"\n"
                + "and hc.i_usuario_para = "+i_usu_para+"\n"
                + "order by alteracao asc;";
        ResultSet rs = null;
        util.Persistencia con = new util.Persistencia();
        List<SuporteHistoricoChat> listHistChat = new ArrayList<SuporteHistoricoChat>();
        PreparedStatement ps = con.conectar().prepareStatement(sql);
        rs = ps.executeQuery();
        while (rs.next()) {
            listHistChat.add(
                    new SuporteHistoricoChat(rs.getInt(1),
                            rs.getString(2),
                            rs.getString(3),
                            rs.getDate(4),
                            rs.getString(5)));
        }
        con.desconectar();
        return listHistChat;
    }
    
    public List<SuporteHistoricoChat> findSuporteHistoricoChatEntitiesOrdenado(boolean all, int maxResults, int firstResult, int i_usu_de, int i_usu_para, String alt) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(SuporteHistoricoChat.class));
            Query q = em.createQuery("select h from SuporteHistoricoChat h WHERE ((h.iUsuarioDe.iUsuario = "+i_usu_de+" and h.iUsuarioPara.iUsuario = "+i_usu_para+") OR (h.iUsuarioDe.iUsuario = "+i_usu_para+" and h.iUsuarioPara.iUsuario = "+i_usu_de+")) and h.alteracao >= '"+alt+"'");
            //Query q = em.createQuery("select h from SuporteHistoricoChat h WHERE (h.iUsuarioDe.iUsuario = ? and h.iUsuarioPara.iUsuario) OR (h.iUsuarioDe.iUsuario = ? and h.iUsuarioPara.iUsuario = ?)");
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }
    
    public void limparHistorico(int i_usu_de, int usu_para) {
        String sql = "DELETE FROM Suporte_Historico_Chat h WHERE h.i_usuario_de = "+i_usu_de+" and h.i_usuario_para = "+usu_para;       
        util.Persistencia con = new util.Persistencia();
        PreparedStatement ps;
        try {
            ps = con.conectar().prepareStatement(sql);
            ps.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(SuporteHistoricoChatJpaController.class.getName()).log(Level.SEVERE, null, ex);
        }
        con.desconectar();        
    }
    
    
    public List<SuporteHistoricoChat> findSuporteHistoricoChatEntitiesLidos(boolean lido, int i_usu_de, int i_usu_para) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(SuporteHistoricoChat.class));
            Query q = em.createQuery("select h from SuporteHistoricoChat h WHERE (h.iUsuarioDe.iUsuario = "+i_usu_para+" and h.iUsuarioPara.iUsuario = "+i_usu_de+") and h.visualizado ="+lido+" order by h.alteracao asc");
//            Query q = em.createQuery("select h from SuporteHistoricoChat h WHERE ((h.iUsuarioDe.iUsuario = "+i_usu_de+" and h.iUsuarioPara.iUsuario = "+i_usu_para+") OR (h.iUsuarioDe.iUsuario = "+i_usu_para+" and h.iUsuarioPara.iUsuario = "+i_usu_de+")) and h.visualizado ="+lido+" order by h.alteracao asc");
            //Query q = em.createQuery("select h from SuporteHistoricoChat h WHERE (h.iUsuarioDe.iUsuario = ? and h.iUsuarioPara.iUsuario) OR (h.iUsuarioDe.iUsuario = ? and h.iUsuarioPara.iUsuario = ?)");
            
            return q.getResultList();
        } finally {
            em.close();
        }
    }
    
    public List<SuporteHistoricoChat> findSuporteHistoricoChatEntitiesLidosTodos(int i_usu_de) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(SuporteHistoricoChat.class));
            Query q = em.createQuery("select h from SuporteHistoricoChat h WHERE h.iUsuarioPara.iUsuario = "+i_usu_de+" and h.visualizado = false order by h.alteracao asc");
            
            return q.getResultList();
        } finally {
            em.close();
        }
    }
    
}
