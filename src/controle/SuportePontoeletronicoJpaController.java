/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controle;

import controle.exceptions.IllegalOrphanException;
import controle.exceptions.NonexistentEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import entidades.SuportePeriodos;
import entidades.SuporteUsuarios;
import entidades.SuporteIntercorrencia;
import entidades.SuportePontoeletronico;
import java.util.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
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
public class SuportePontoeletronicoJpaController implements Serializable {

    public SuportePontoeletronicoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(SuportePontoeletronico suportePontoeletronico) {
        if (suportePontoeletronico.getSuporteIntercorrenciaList() == null) {
            suportePontoeletronico.setSuporteIntercorrenciaList(new ArrayList<SuporteIntercorrencia>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            SuportePeriodos IPeriodo = suportePontoeletronico.getIPeriodo();
            if (IPeriodo != null) {
                IPeriodo = em.getReference(IPeriodo.getClass(), IPeriodo.getIPeriodo());
                suportePontoeletronico.setIPeriodo(IPeriodo);
            }
            SuporteUsuarios IUsuario = suportePontoeletronico.getIUsuario();
            if (IUsuario != null) {
                IUsuario = em.getReference(IUsuario.getClass(), IUsuario.getIUsuario());
                suportePontoeletronico.setIUsuario(IUsuario);
            }
            List<SuporteIntercorrencia> attachedSuporteIntercorrenciaList = new ArrayList<SuporteIntercorrencia>();
            for (SuporteIntercorrencia suporteIntercorrenciaListSuporteIntercorrenciaToAttach : suportePontoeletronico.getSuporteIntercorrenciaList()) {
                suporteIntercorrenciaListSuporteIntercorrenciaToAttach = em.getReference(suporteIntercorrenciaListSuporteIntercorrenciaToAttach.getClass(), suporteIntercorrenciaListSuporteIntercorrenciaToAttach.getIIntercorrencia());
                attachedSuporteIntercorrenciaList.add(suporteIntercorrenciaListSuporteIntercorrenciaToAttach);
            }
            suportePontoeletronico.setSuporteIntercorrenciaList(attachedSuporteIntercorrenciaList);
            em.persist(suportePontoeletronico);
            if (IPeriodo != null) {
                IPeriodo.getSuportePontoeletronicoList().add(suportePontoeletronico);
                IPeriodo = em.merge(IPeriodo);
            }
            if (IUsuario != null) {
                IUsuario.getSuportePontoeletronicoList().add(suportePontoeletronico);
                IUsuario = em.merge(IUsuario);
            }
            for (SuporteIntercorrencia suporteIntercorrenciaListSuporteIntercorrencia : suportePontoeletronico.getSuporteIntercorrenciaList()) {
                SuportePontoeletronico oldIPontoOfSuporteIntercorrenciaListSuporteIntercorrencia = suporteIntercorrenciaListSuporteIntercorrencia.getIPonto();
                suporteIntercorrenciaListSuporteIntercorrencia.setIPonto(suportePontoeletronico);
                suporteIntercorrenciaListSuporteIntercorrencia = em.merge(suporteIntercorrenciaListSuporteIntercorrencia);
                if (oldIPontoOfSuporteIntercorrenciaListSuporteIntercorrencia != null) {
                    oldIPontoOfSuporteIntercorrenciaListSuporteIntercorrencia.getSuporteIntercorrenciaList().remove(suporteIntercorrenciaListSuporteIntercorrencia);
                    oldIPontoOfSuporteIntercorrenciaListSuporteIntercorrencia = em.merge(oldIPontoOfSuporteIntercorrenciaListSuporteIntercorrencia);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(SuportePontoeletronico suportePontoeletronico) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            SuportePontoeletronico persistentSuportePontoeletronico = em.find(SuportePontoeletronico.class, suportePontoeletronico.getIPonto());
            SuportePeriodos IPeriodoOld = persistentSuportePontoeletronico.getIPeriodo();
            SuportePeriodos IPeriodoNew = suportePontoeletronico.getIPeriodo();
            SuporteUsuarios IUsuarioOld = persistentSuportePontoeletronico.getIUsuario();
            SuporteUsuarios IUsuarioNew = suportePontoeletronico.getIUsuario();
            List<SuporteIntercorrencia> suporteIntercorrenciaListOld = persistentSuportePontoeletronico.getSuporteIntercorrenciaList();
            List<SuporteIntercorrencia> suporteIntercorrenciaListNew = suportePontoeletronico.getSuporteIntercorrenciaList();
            List<String> illegalOrphanMessages = null;
            for (SuporteIntercorrencia suporteIntercorrenciaListOldSuporteIntercorrencia : suporteIntercorrenciaListOld) {
                if (!suporteIntercorrenciaListNew.contains(suporteIntercorrenciaListOldSuporteIntercorrencia)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain SuporteIntercorrencia " + suporteIntercorrenciaListOldSuporteIntercorrencia + " since its IPonto field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (IPeriodoNew != null) {
                IPeriodoNew = em.getReference(IPeriodoNew.getClass(), IPeriodoNew.getIPeriodo());
                suportePontoeletronico.setIPeriodo(IPeriodoNew);
            }
            if (IUsuarioNew != null) {
                IUsuarioNew = em.getReference(IUsuarioNew.getClass(), IUsuarioNew.getIUsuario());
                suportePontoeletronico.setIUsuario(IUsuarioNew);
            }
            List<SuporteIntercorrencia> attachedSuporteIntercorrenciaListNew = new ArrayList<SuporteIntercorrencia>();
            for (SuporteIntercorrencia suporteIntercorrenciaListNewSuporteIntercorrenciaToAttach : suporteIntercorrenciaListNew) {
                suporteIntercorrenciaListNewSuporteIntercorrenciaToAttach = em.getReference(suporteIntercorrenciaListNewSuporteIntercorrenciaToAttach.getClass(), suporteIntercorrenciaListNewSuporteIntercorrenciaToAttach.getIIntercorrencia());
                attachedSuporteIntercorrenciaListNew.add(suporteIntercorrenciaListNewSuporteIntercorrenciaToAttach);
            }
            suporteIntercorrenciaListNew = attachedSuporteIntercorrenciaListNew;
            suportePontoeletronico.setSuporteIntercorrenciaList(suporteIntercorrenciaListNew);
            suportePontoeletronico = em.merge(suportePontoeletronico);
            if (IPeriodoOld != null && !IPeriodoOld.equals(IPeriodoNew)) {
                IPeriodoOld.getSuportePontoeletronicoList().remove(suportePontoeletronico);
                IPeriodoOld = em.merge(IPeriodoOld);
            }
            if (IPeriodoNew != null && !IPeriodoNew.equals(IPeriodoOld)) {
                IPeriodoNew.getSuportePontoeletronicoList().add(suportePontoeletronico);
                IPeriodoNew = em.merge(IPeriodoNew);
            }
            if (IUsuarioOld != null && !IUsuarioOld.equals(IUsuarioNew)) {
                IUsuarioOld.getSuportePontoeletronicoList().remove(suportePontoeletronico);
                IUsuarioOld = em.merge(IUsuarioOld);
            }
            if (IUsuarioNew != null && !IUsuarioNew.equals(IUsuarioOld)) {
                IUsuarioNew.getSuportePontoeletronicoList().add(suportePontoeletronico);
                IUsuarioNew = em.merge(IUsuarioNew);
            }
            for (SuporteIntercorrencia suporteIntercorrenciaListNewSuporteIntercorrencia : suporteIntercorrenciaListNew) {
                if (!suporteIntercorrenciaListOld.contains(suporteIntercorrenciaListNewSuporteIntercorrencia)) {
                    SuportePontoeletronico oldIPontoOfSuporteIntercorrenciaListNewSuporteIntercorrencia = suporteIntercorrenciaListNewSuporteIntercorrencia.getIPonto();
                    suporteIntercorrenciaListNewSuporteIntercorrencia.setIPonto(suportePontoeletronico);
                    suporteIntercorrenciaListNewSuporteIntercorrencia = em.merge(suporteIntercorrenciaListNewSuporteIntercorrencia);
                    if (oldIPontoOfSuporteIntercorrenciaListNewSuporteIntercorrencia != null && !oldIPontoOfSuporteIntercorrenciaListNewSuporteIntercorrencia.equals(suportePontoeletronico)) {
                        oldIPontoOfSuporteIntercorrenciaListNewSuporteIntercorrencia.getSuporteIntercorrenciaList().remove(suporteIntercorrenciaListNewSuporteIntercorrencia);
                        oldIPontoOfSuporteIntercorrenciaListNewSuporteIntercorrencia = em.merge(oldIPontoOfSuporteIntercorrenciaListNewSuporteIntercorrencia);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = suportePontoeletronico.getIPonto();
                if (findSuportePontoeletronico(id) == null) {
                    throw new NonexistentEntityException("The suportePontoeletronico with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws IllegalOrphanException, NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            SuportePontoeletronico suportePontoeletronico;
            try {
                suportePontoeletronico = em.getReference(SuportePontoeletronico.class, id);
                suportePontoeletronico.getIPonto();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The suportePontoeletronico with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<SuporteIntercorrencia> suporteIntercorrenciaListOrphanCheck = suportePontoeletronico.getSuporteIntercorrenciaList();
            for (SuporteIntercorrencia suporteIntercorrenciaListOrphanCheckSuporteIntercorrencia : suporteIntercorrenciaListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This SuportePontoeletronico (" + suportePontoeletronico + ") cannot be destroyed since the SuporteIntercorrencia " + suporteIntercorrenciaListOrphanCheckSuporteIntercorrencia + " in its suporteIntercorrenciaList field has a non-nullable IPonto field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            SuportePeriodos IPeriodo = suportePontoeletronico.getIPeriodo();
            if (IPeriodo != null) {
                IPeriodo.getSuportePontoeletronicoList().remove(suportePontoeletronico);
                IPeriodo = em.merge(IPeriodo);
            }
            SuporteUsuarios IUsuario = suportePontoeletronico.getIUsuario();
            if (IUsuario != null) {
                IUsuario.getSuportePontoeletronicoList().remove(suportePontoeletronico);
                IUsuario = em.merge(IUsuario);
            }
            em.remove(suportePontoeletronico);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<SuportePontoeletronico> findSuportePontoeletronicoEntitiesOrigin() {
        return findSuportePontoeletronicoEntities(true, -1, -1);
    }

    public List<SuportePontoeletronico> findSuportePontoeletronicoEntities(int maxResults, int firstResult) {
        return findSuportePontoeletronicoEntities(false, maxResults, firstResult);
    }

    private List<SuportePontoeletronico> findSuportePontoeletronicoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(SuportePontoeletronico.class));
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

    public SuportePontoeletronico findSuportePontoeletronico(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(SuportePontoeletronico.class, id);
        } finally {
            em.close();
        }
    }

    public int getSuportePontoeletronicoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<SuportePontoeletronico> rt = cq.from(SuportePontoeletronico.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
/***/
    public List<SuportePontoeletronico> findSuportePontoeletronicoEntitiesFiltros(String tipo, int maxResults, int firstResult, Date dtInicio, Date dtFim, String situacao, int i_usuario, int i_periodo) {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createQuery("select p from SuportePontoeletronico p ORDER BY p.dtRegistro DESC");

            switch (tipo) {
                case "geral.geral":
                    q = em.createQuery("select p from SuportePontoeletronico p ORDER BY p.dtRegistro DESC");
                    break;
                case "geral.max-min":
                    q = em.createQuery("select p from SuportePontoeletronico p ORDER BY p.dtRegistro DESC");
                    q.setMaxResults(maxResults);
                    q.setFirstResult(firstResult);
                    break;
                case "geral.referencia":
                    q = em.createQuery("select p from SuportePontoeletronico p where p.iPeriodo.iPeriodo = " + i_periodo + " ORDER BY p.dtRegistro DESC");
                    break;
                case "geral.periodo":
                    q = em.createQuery("select p from SuportePontoeletronico p where p.dtRegistro between " + dtInicio + " AND " + dtFim + " ORDER BY p.dtRegistro DESC");
                    break;
                case "geral.situacao":
                    q = em.createQuery("select p from SuportePontoeletronico p where p.situacao like '%" + situacao + "%' ORDER BY p.dtRegistro DESC");
                    break;
                case "usuario.geral":
                    q = em.createQuery("select p from SuportePontoeletronico p where p.iUsuario.iUsuario = " + i_usuario + " ORDER BY p.dtRegistro DESC");
                    break;
                case "usuario.max-min":
                    q = em.createQuery("select p from SuportePontoeletronico p where p.iUsuario.iUsuario = '%" + i_usuario + "%' ORDER BY p.dtRegistro DESC");
                    q.setMaxResults(maxResults);
                    q.setFirstResult(firstResult);
                    break;
                case "usuario.referencia":
                    q = em.createQuery("select p from SuportePontoeletronico p where p.iUsuario.iUsuario = " + i_usuario + " and p.iPeriodo.iPeriodo = " + i_periodo + " ORDER BY p.dtRegistro DESC");
                    break;
                case "usuario.periodo":
                    q = em.createQuery("select p from SuportePontoeletronico p where p.iUsuario.iUsuario = '%" + i_usuario + "%' and p.dtRegistro between " + dtInicio + " AND " + dtFim + " ORDER BY p.dtRegistro DESC");
                    break;
                case "usuario.situacao":
                    q = em.createQuery("select p from SuportePontoeletronico p where p.iUsuario.iUsuario = '%" + i_usuario + "%' and p.situacao like '%" + situacao + "%' ORDER BY p.dtRegistro DESC");
                    break;
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public Date diferenca(String dtInformada, int i_ponto) {
        String sql = "select to_timestamp(to_char((now()-'" + dtInformada + "-3 BC'),'DD HH24:MI:SS'::text),'DD HH24:MI:SS'::text)as dif from suporte_pontoeletronico where i_ponto = " + i_ponto + ";";

        ResultSet rs = null;
        util.Persistencia con = new util.Persistencia();
        PreparedStatement ps;
        Timestamp dif = null;
        try {
            ps = con.conectar().prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                dif = rs.getTimestamp(1);
            }
        } catch (SQLException ex) {
            Logger.getLogger(SuportePontoeletronicoJpaController.class.getName()).log(Level.SEVERE, null, ex);
        }
        con.desconectar();
        return dif;

    }

    public Timestamp dataServidor() {
        String sql = "select now();";

        ResultSet rs = null;
        util.Persistencia con = new util.Persistencia();
        PreparedStatement ps;
        Timestamp dif = null;
        try {
            ps = con.conectar().prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                dif = rs.getTimestamp(1);
            }
        } catch (SQLException ex) {
            Logger.getLogger(SuportePontoeletronicoJpaController.class.getName()).log(Level.SEVERE, null, ex);
        }
        con.desconectar();
        return dif;

    }
    
    public List<SuportePontoeletronico> findSuportePontoeletronicoOrdenado() {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createQuery("select p from SuportePontoeletronico p ORDER BY p.iPonto DESC");
            return q.getResultList();
        } finally {
            em.close();
        }
    }

}
