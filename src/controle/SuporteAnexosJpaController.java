/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controle;

import controle.exceptions.NonexistentEntityException;
import entidades.SuporteAnexos;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import entidades.SuporteCategorias;
import entidades.SuporteEntidades;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import util.Persistencia;

/**
 *
 * @author Hilton
 */
public class SuporteAnexosJpaController implements Serializable {

    public SuporteAnexosJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(SuporteAnexos suporteAnexos) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            SuporteCategorias ICategoria = suporteAnexos.getICategoria();
            if (ICategoria != null) {
                ICategoria = em.getReference(ICategoria.getClass(), ICategoria.getICategoria());
                suporteAnexos.setICategoria(ICategoria);
            }
            SuporteEntidades IEntidade = suporteAnexos.getIEntidade();
            if (IEntidade != null) {
                IEntidade = em.getReference(IEntidade.getClass(), IEntidade.getIEntidade());
                suporteAnexos.setIEntidade(IEntidade);
            }
            em.persist(suporteAnexos);
            if (ICategoria != null) {
                ICategoria.getSuporteAnexosList().add(suporteAnexos);
                ICategoria = em.merge(ICategoria);
            }
            if (IEntidade != null) {
                IEntidade.getSuporteAnexosList().add(suporteAnexos);
                IEntidade = em.merge(IEntidade);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(SuporteAnexos suporteAnexos) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            SuporteAnexos persistentSuporteAnexos = em.find(SuporteAnexos.class, suporteAnexos.getIAnexo());
            SuporteCategorias ICategoriaOld = persistentSuporteAnexos.getICategoria();
            SuporteCategorias ICategoriaNew = suporteAnexos.getICategoria();
            SuporteEntidades IEntidadeOld = persistentSuporteAnexos.getIEntidade();
            SuporteEntidades IEntidadeNew = suporteAnexos.getIEntidade();
            if (ICategoriaNew != null) {
                ICategoriaNew = em.getReference(ICategoriaNew.getClass(), ICategoriaNew.getICategoria());
                suporteAnexos.setICategoria(ICategoriaNew);
            }
            if (IEntidadeNew != null) {
                IEntidadeNew = em.getReference(IEntidadeNew.getClass(), IEntidadeNew.getIEntidade());
                suporteAnexos.setIEntidade(IEntidadeNew);
            }
            suporteAnexos = em.merge(suporteAnexos);
            if (ICategoriaOld != null && !ICategoriaOld.equals(ICategoriaNew)) {
                ICategoriaOld.getSuporteAnexosList().remove(suporteAnexos);
                ICategoriaOld = em.merge(ICategoriaOld);
            }
            if (ICategoriaNew != null && !ICategoriaNew.equals(ICategoriaOld)) {
                ICategoriaNew.getSuporteAnexosList().add(suporteAnexos);
                ICategoriaNew = em.merge(ICategoriaNew);
            }
            if (IEntidadeOld != null && !IEntidadeOld.equals(IEntidadeNew)) {
                IEntidadeOld.getSuporteAnexosList().remove(suporteAnexos);
                IEntidadeOld = em.merge(IEntidadeOld);
            }
            if (IEntidadeNew != null && !IEntidadeNew.equals(IEntidadeOld)) {
                IEntidadeNew.getSuporteAnexosList().add(suporteAnexos);
                IEntidadeNew = em.merge(IEntidadeNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = suporteAnexos.getIAnexo();
                if (findSuporteAnexos(id) == null) {
                    throw new NonexistentEntityException("The suporteAnexos with id " + id + " no longer exists.");
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
            SuporteAnexos suporteAnexos;
            try {
                suporteAnexos = em.getReference(SuporteAnexos.class, id);
                suporteAnexos.getIAnexo();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The suporteAnexos with id " + id + " no longer exists.", enfe);
            }
            SuporteCategorias ICategoria = suporteAnexos.getICategoria();
            if (ICategoria != null) {
                ICategoria.getSuporteAnexosList().remove(suporteAnexos);
                ICategoria = em.merge(ICategoria);
            }
            SuporteEntidades IEntidade = suporteAnexos.getIEntidade();
            if (IEntidade != null) {
                IEntidade.getSuporteAnexosList().remove(suporteAnexos);
                IEntidade = em.merge(IEntidade);
            }
            em.remove(suporteAnexos);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<SuporteAnexos> findSuporteAnexosEntities() {
        return findSuporteAnexosEntities(true, -1, -1);
    }

    public List<SuporteAnexos> findSuporteAnexosEntities(int maxResults, int firstResult) {
        return findSuporteAnexosEntities(false, maxResults, firstResult);
    }

    private List<SuporteAnexos> findSuporteAnexosEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(SuporteAnexos.class));
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

    public SuporteAnexos findSuporteAnexos(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(SuporteAnexos.class, id);
        } finally {
            em.close();
        }
    }

    public int getSuporteAnexosCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<SuporteAnexos> rt = cq.from(SuporteAnexos.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
    
//Método Novo
public List<SuporteAnexos> findSuporteAnexosEntitiesOrdenado() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(SuporteAnexos.class));
            Query q = em.createQuery("SELECT s FROM SuporteAnexos s ORDER BY s.alteracao DESC");
            // Query q = em.createQuery("SELECT s.iAnexo, s.iEntidade, s.iCategoria, s.diretorio, s.anexoNome, s.alteracao FROM SuporteAnexos s ORDER BY s.alteracao DESC");
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public ResultSet recuperarArquivo(int i_anexo) {
        String sql = "select i_anexo, diretorio, arquivo from suporte_anexos where i_anexo = ?";
        Persistencia p = new Persistencia();
        PreparedStatement ps;
        ResultSet rs = null;
        try {
            ps = p.conectar().prepareStatement(sql);
            ps.setInt(1, i_anexo);
            rs = ps.executeQuery();
        } catch (SQLException ex) {
            Logger.getLogger(SuporteAnexosJpaController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return rs;
    }

    public List<SuporteAnexos> findSuporteAnexosOrdenado() throws SQLException {
        ResultSet rs = null;
        //String consulta = "SELECT s.i_anexo, s.i_entidade, s.i_categoria, s.diretorio, s.anexo_nome, s.alteracao FROM suporte_anexos s ORDER BY s.alteracao DESC;";
        String consulta = "SELECT s.i_anexo, e.entidade_nome, c.categoria_nome, s.diretorio, s.anexo_nome, s.alteracao \n"
                + "FROM suporte_anexos s inner join suporte_entidades e on s.i_entidade = e.i_entidade \n"
                + "inner join suporte_categorias c \n"
                + "on s.i_categoria = c.i_categoria\n"
                + "ORDER BY s.alteracao DESC;";
        util.Persistencia con = new util.Persistencia();
        List<SuporteAnexos> anexos = new ArrayList<SuporteAnexos>();
        PreparedStatement ps = con.conectar().prepareStatement(consulta);
        rs = ps.executeQuery();
        while (rs.next()) {
            anexos.add(
                    new SuporteAnexos(rs.getInt(1),
                            rs.getString(2),
                            rs.getString(3),
                            rs.getString(4),
                            rs.getString(5),
                            rs.getDate(6)));
        }
        con.desconectar();
        return anexos;
    }
    
}
