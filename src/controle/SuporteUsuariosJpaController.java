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
import entidades.SuporteAreas;
import entidades.SuportePontoeletronico;
import java.util.ArrayList;
import java.util.List;
import entidades.SuporteHistoricoChat;
import entidades.SuporteHistoricoAcesso;
import entidades.SuporteUsuarios;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Hilton
 */
public class SuporteUsuariosJpaController implements Serializable {

    public SuporteUsuariosJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(SuporteUsuarios suporteUsuarios) {
        if (suporteUsuarios.getSuportePontoeletronicoList() == null) {
            suporteUsuarios.setSuportePontoeletronicoList(new ArrayList<SuportePontoeletronico>());
        }
        if (suporteUsuarios.getSuporteHistoricoChatList() == null) {
            suporteUsuarios.setSuporteHistoricoChatList(new ArrayList<SuporteHistoricoChat>());
        }
        if (suporteUsuarios.getSuporteHistoricoChatList1() == null) {
            suporteUsuarios.setSuporteHistoricoChatList1(new ArrayList<SuporteHistoricoChat>());
        }
        if (suporteUsuarios.getSuporteHistoricoAcessoList() == null) {
            suporteUsuarios.setSuporteHistoricoAcessoList(new ArrayList<SuporteHistoricoAcesso>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            SuporteAreas IArea = suporteUsuarios.getIArea();
            if (IArea != null) {
                IArea = em.getReference(IArea.getClass(), IArea.getIArea());
                suporteUsuarios.setIArea(IArea);
            }
            List<SuportePontoeletronico> attachedSuportePontoeletronicoList = new ArrayList<SuportePontoeletronico>();
            for (SuportePontoeletronico suportePontoeletronicoListSuportePontoeletronicoToAttach : suporteUsuarios.getSuportePontoeletronicoList()) {
                suportePontoeletronicoListSuportePontoeletronicoToAttach = em.getReference(suportePontoeletronicoListSuportePontoeletronicoToAttach.getClass(), suportePontoeletronicoListSuportePontoeletronicoToAttach.getIPonto());
                attachedSuportePontoeletronicoList.add(suportePontoeletronicoListSuportePontoeletronicoToAttach);
            }
            suporteUsuarios.setSuportePontoeletronicoList(attachedSuportePontoeletronicoList);
            List<SuporteHistoricoChat> attachedSuporteHistoricoChatList = new ArrayList<SuporteHistoricoChat>();
            for (SuporteHistoricoChat suporteHistoricoChatListSuporteHistoricoChatToAttach : suporteUsuarios.getSuporteHistoricoChatList()) {
                suporteHistoricoChatListSuporteHistoricoChatToAttach = em.getReference(suporteHistoricoChatListSuporteHistoricoChatToAttach.getClass(), suporteHistoricoChatListSuporteHistoricoChatToAttach.getIHistoricoChat());
                attachedSuporteHistoricoChatList.add(suporteHistoricoChatListSuporteHistoricoChatToAttach);
            }
            suporteUsuarios.setSuporteHistoricoChatList(attachedSuporteHistoricoChatList);
            List<SuporteHistoricoChat> attachedSuporteHistoricoChatList1 = new ArrayList<SuporteHistoricoChat>();
            for (SuporteHistoricoChat suporteHistoricoChatList1SuporteHistoricoChatToAttach : suporteUsuarios.getSuporteHistoricoChatList1()) {
                suporteHistoricoChatList1SuporteHistoricoChatToAttach = em.getReference(suporteHistoricoChatList1SuporteHistoricoChatToAttach.getClass(), suporteHistoricoChatList1SuporteHistoricoChatToAttach.getIHistoricoChat());
                attachedSuporteHistoricoChatList1.add(suporteHistoricoChatList1SuporteHistoricoChatToAttach);
            }
            suporteUsuarios.setSuporteHistoricoChatList1(attachedSuporteHistoricoChatList1);
            List<SuporteHistoricoAcesso> attachedSuporteHistoricoAcessoList = new ArrayList<SuporteHistoricoAcesso>();
            for (SuporteHistoricoAcesso suporteHistoricoAcessoListSuporteHistoricoAcessoToAttach : suporteUsuarios.getSuporteHistoricoAcessoList()) {
                suporteHistoricoAcessoListSuporteHistoricoAcessoToAttach = em.getReference(suporteHistoricoAcessoListSuporteHistoricoAcessoToAttach.getClass(), suporteHistoricoAcessoListSuporteHistoricoAcessoToAttach.getIHistorico());
                attachedSuporteHistoricoAcessoList.add(suporteHistoricoAcessoListSuporteHistoricoAcessoToAttach);
            }
            suporteUsuarios.setSuporteHistoricoAcessoList(attachedSuporteHistoricoAcessoList);
            em.persist(suporteUsuarios);
            if (IArea != null) {
                IArea.getSuporteUsuariosList().add(suporteUsuarios);
                IArea = em.merge(IArea);
            }
            for (SuportePontoeletronico suportePontoeletronicoListSuportePontoeletronico : suporteUsuarios.getSuportePontoeletronicoList()) {
                SuporteUsuarios oldIUsuarioOfSuportePontoeletronicoListSuportePontoeletronico = suportePontoeletronicoListSuportePontoeletronico.getIUsuario();
                suportePontoeletronicoListSuportePontoeletronico.setIUsuario(suporteUsuarios);
                suportePontoeletronicoListSuportePontoeletronico = em.merge(suportePontoeletronicoListSuportePontoeletronico);
                if (oldIUsuarioOfSuportePontoeletronicoListSuportePontoeletronico != null) {
                    oldIUsuarioOfSuportePontoeletronicoListSuportePontoeletronico.getSuportePontoeletronicoList().remove(suportePontoeletronicoListSuportePontoeletronico);
                    oldIUsuarioOfSuportePontoeletronicoListSuportePontoeletronico = em.merge(oldIUsuarioOfSuportePontoeletronicoListSuportePontoeletronico);
                }
            }
            for (SuporteHistoricoChat suporteHistoricoChatListSuporteHistoricoChat : suporteUsuarios.getSuporteHistoricoChatList()) {
                SuporteUsuarios oldIUsuarioDeOfSuporteHistoricoChatListSuporteHistoricoChat = suporteHistoricoChatListSuporteHistoricoChat.getIUsuarioDe();
                suporteHistoricoChatListSuporteHistoricoChat.setIUsuarioDe(suporteUsuarios);
                suporteHistoricoChatListSuporteHistoricoChat = em.merge(suporteHistoricoChatListSuporteHistoricoChat);
                if (oldIUsuarioDeOfSuporteHistoricoChatListSuporteHistoricoChat != null) {
                    oldIUsuarioDeOfSuporteHistoricoChatListSuporteHistoricoChat.getSuporteHistoricoChatList().remove(suporteHistoricoChatListSuporteHistoricoChat);
                    oldIUsuarioDeOfSuporteHistoricoChatListSuporteHistoricoChat = em.merge(oldIUsuarioDeOfSuporteHistoricoChatListSuporteHistoricoChat);
                }
            }
            for (SuporteHistoricoChat suporteHistoricoChatList1SuporteHistoricoChat : suporteUsuarios.getSuporteHistoricoChatList1()) {
                SuporteUsuarios oldIUsuarioParaOfSuporteHistoricoChatList1SuporteHistoricoChat = suporteHistoricoChatList1SuporteHistoricoChat.getIUsuarioPara();
                suporteHistoricoChatList1SuporteHistoricoChat.setIUsuarioPara(suporteUsuarios);
                suporteHistoricoChatList1SuporteHistoricoChat = em.merge(suporteHistoricoChatList1SuporteHistoricoChat);
                if (oldIUsuarioParaOfSuporteHistoricoChatList1SuporteHistoricoChat != null) {
                    oldIUsuarioParaOfSuporteHistoricoChatList1SuporteHistoricoChat.getSuporteHistoricoChatList1().remove(suporteHistoricoChatList1SuporteHistoricoChat);
                    oldIUsuarioParaOfSuporteHistoricoChatList1SuporteHistoricoChat = em.merge(oldIUsuarioParaOfSuporteHistoricoChatList1SuporteHistoricoChat);
                }
            }
            for (SuporteHistoricoAcesso suporteHistoricoAcessoListSuporteHistoricoAcesso : suporteUsuarios.getSuporteHistoricoAcessoList()) {
                SuporteUsuarios oldIUsuarioOfSuporteHistoricoAcessoListSuporteHistoricoAcesso = suporteHistoricoAcessoListSuporteHistoricoAcesso.getIUsuario();
                suporteHistoricoAcessoListSuporteHistoricoAcesso.setIUsuario(suporteUsuarios);
                suporteHistoricoAcessoListSuporteHistoricoAcesso = em.merge(suporteHistoricoAcessoListSuporteHistoricoAcesso);
                if (oldIUsuarioOfSuporteHistoricoAcessoListSuporteHistoricoAcesso != null) {
                    oldIUsuarioOfSuporteHistoricoAcessoListSuporteHistoricoAcesso.getSuporteHistoricoAcessoList().remove(suporteHistoricoAcessoListSuporteHistoricoAcesso);
                    oldIUsuarioOfSuporteHistoricoAcessoListSuporteHistoricoAcesso = em.merge(oldIUsuarioOfSuporteHistoricoAcessoListSuporteHistoricoAcesso);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(SuporteUsuarios suporteUsuarios) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            SuporteUsuarios persistentSuporteUsuarios = em.find(SuporteUsuarios.class, suporteUsuarios.getIUsuario());
            SuporteAreas IAreaOld = persistentSuporteUsuarios.getIArea();
            SuporteAreas IAreaNew = suporteUsuarios.getIArea();
            List<SuportePontoeletronico> suportePontoeletronicoListOld = persistentSuporteUsuarios.getSuportePontoeletronicoList();
            List<SuportePontoeletronico> suportePontoeletronicoListNew = suporteUsuarios.getSuportePontoeletronicoList();
            List<SuporteHistoricoChat> suporteHistoricoChatListOld = persistentSuporteUsuarios.getSuporteHistoricoChatList();
            List<SuporteHistoricoChat> suporteHistoricoChatListNew = suporteUsuarios.getSuporteHistoricoChatList();
            List<SuporteHistoricoChat> suporteHistoricoChatList1Old = persistentSuporteUsuarios.getSuporteHistoricoChatList1();
            List<SuporteHistoricoChat> suporteHistoricoChatList1New = suporteUsuarios.getSuporteHistoricoChatList1();
            List<SuporteHistoricoAcesso> suporteHistoricoAcessoListOld = persistentSuporteUsuarios.getSuporteHistoricoAcessoList();
            List<SuporteHistoricoAcesso> suporteHistoricoAcessoListNew = suporteUsuarios.getSuporteHistoricoAcessoList();
            List<String> illegalOrphanMessages = null;
            for (SuportePontoeletronico suportePontoeletronicoListOldSuportePontoeletronico : suportePontoeletronicoListOld) {
                if (!suportePontoeletronicoListNew.contains(suportePontoeletronicoListOldSuportePontoeletronico)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain SuportePontoeletronico " + suportePontoeletronicoListOldSuportePontoeletronico + " since its IUsuario field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (IAreaNew != null) {
                IAreaNew = em.getReference(IAreaNew.getClass(), IAreaNew.getIArea());
                suporteUsuarios.setIArea(IAreaNew);
            }
            List<SuportePontoeletronico> attachedSuportePontoeletronicoListNew = new ArrayList<SuportePontoeletronico>();
            for (SuportePontoeletronico suportePontoeletronicoListNewSuportePontoeletronicoToAttach : suportePontoeletronicoListNew) {
                suportePontoeletronicoListNewSuportePontoeletronicoToAttach = em.getReference(suportePontoeletronicoListNewSuportePontoeletronicoToAttach.getClass(), suportePontoeletronicoListNewSuportePontoeletronicoToAttach.getIPonto());
                attachedSuportePontoeletronicoListNew.add(suportePontoeletronicoListNewSuportePontoeletronicoToAttach);
            }
            suportePontoeletronicoListNew = attachedSuportePontoeletronicoListNew;
            suporteUsuarios.setSuportePontoeletronicoList(suportePontoeletronicoListNew);
            List<SuporteHistoricoChat> attachedSuporteHistoricoChatListNew = new ArrayList<SuporteHistoricoChat>();
            for (SuporteHistoricoChat suporteHistoricoChatListNewSuporteHistoricoChatToAttach : suporteHistoricoChatListNew) {
                suporteHistoricoChatListNewSuporteHistoricoChatToAttach = em.getReference(suporteHistoricoChatListNewSuporteHistoricoChatToAttach.getClass(), suporteHistoricoChatListNewSuporteHistoricoChatToAttach.getIHistoricoChat());
                attachedSuporteHistoricoChatListNew.add(suporteHistoricoChatListNewSuporteHistoricoChatToAttach);
            }
            suporteHistoricoChatListNew = attachedSuporteHistoricoChatListNew;
            suporteUsuarios.setSuporteHistoricoChatList(suporteHistoricoChatListNew);
            List<SuporteHistoricoChat> attachedSuporteHistoricoChatList1New = new ArrayList<SuporteHistoricoChat>();
            for (SuporteHistoricoChat suporteHistoricoChatList1NewSuporteHistoricoChatToAttach : suporteHistoricoChatList1New) {
                suporteHistoricoChatList1NewSuporteHistoricoChatToAttach = em.getReference(suporteHistoricoChatList1NewSuporteHistoricoChatToAttach.getClass(), suporteHistoricoChatList1NewSuporteHistoricoChatToAttach.getIHistoricoChat());
                attachedSuporteHistoricoChatList1New.add(suporteHistoricoChatList1NewSuporteHistoricoChatToAttach);
            }
            suporteHistoricoChatList1New = attachedSuporteHistoricoChatList1New;
            suporteUsuarios.setSuporteHistoricoChatList1(suporteHistoricoChatList1New);
            List<SuporteHistoricoAcesso> attachedSuporteHistoricoAcessoListNew = new ArrayList<SuporteHistoricoAcesso>();
            for (SuporteHistoricoAcesso suporteHistoricoAcessoListNewSuporteHistoricoAcessoToAttach : suporteHistoricoAcessoListNew) {
                suporteHistoricoAcessoListNewSuporteHistoricoAcessoToAttach = em.getReference(suporteHistoricoAcessoListNewSuporteHistoricoAcessoToAttach.getClass(), suporteHistoricoAcessoListNewSuporteHistoricoAcessoToAttach.getIHistorico());
                attachedSuporteHistoricoAcessoListNew.add(suporteHistoricoAcessoListNewSuporteHistoricoAcessoToAttach);
            }
            suporteHistoricoAcessoListNew = attachedSuporteHistoricoAcessoListNew;
            suporteUsuarios.setSuporteHistoricoAcessoList(suporteHistoricoAcessoListNew);
            suporteUsuarios = em.merge(suporteUsuarios);
            if (IAreaOld != null && !IAreaOld.equals(IAreaNew)) {
                IAreaOld.getSuporteUsuariosList().remove(suporteUsuarios);
                IAreaOld = em.merge(IAreaOld);
            }
            if (IAreaNew != null && !IAreaNew.equals(IAreaOld)) {
                IAreaNew.getSuporteUsuariosList().add(suporteUsuarios);
                IAreaNew = em.merge(IAreaNew);
            }
            for (SuportePontoeletronico suportePontoeletronicoListNewSuportePontoeletronico : suportePontoeletronicoListNew) {
                if (!suportePontoeletronicoListOld.contains(suportePontoeletronicoListNewSuportePontoeletronico)) {
                    SuporteUsuarios oldIUsuarioOfSuportePontoeletronicoListNewSuportePontoeletronico = suportePontoeletronicoListNewSuportePontoeletronico.getIUsuario();
                    suportePontoeletronicoListNewSuportePontoeletronico.setIUsuario(suporteUsuarios);
                    suportePontoeletronicoListNewSuportePontoeletronico = em.merge(suportePontoeletronicoListNewSuportePontoeletronico);
                    if (oldIUsuarioOfSuportePontoeletronicoListNewSuportePontoeletronico != null && !oldIUsuarioOfSuportePontoeletronicoListNewSuportePontoeletronico.equals(suporteUsuarios)) {
                        oldIUsuarioOfSuportePontoeletronicoListNewSuportePontoeletronico.getSuportePontoeletronicoList().remove(suportePontoeletronicoListNewSuportePontoeletronico);
                        oldIUsuarioOfSuportePontoeletronicoListNewSuportePontoeletronico = em.merge(oldIUsuarioOfSuportePontoeletronicoListNewSuportePontoeletronico);
                    }
                }
            }
            for (SuporteHistoricoChat suporteHistoricoChatListOldSuporteHistoricoChat : suporteHistoricoChatListOld) {
                if (!suporteHistoricoChatListNew.contains(suporteHistoricoChatListOldSuporteHistoricoChat)) {
                    suporteHistoricoChatListOldSuporteHistoricoChat.setIUsuarioDe(null);
                    suporteHistoricoChatListOldSuporteHistoricoChat = em.merge(suporteHistoricoChatListOldSuporteHistoricoChat);
                }
            }
            for (SuporteHistoricoChat suporteHistoricoChatListNewSuporteHistoricoChat : suporteHistoricoChatListNew) {
                if (!suporteHistoricoChatListOld.contains(suporteHistoricoChatListNewSuporteHistoricoChat)) {
                    SuporteUsuarios oldIUsuarioDeOfSuporteHistoricoChatListNewSuporteHistoricoChat = suporteHistoricoChatListNewSuporteHistoricoChat.getIUsuarioDe();
                    suporteHistoricoChatListNewSuporteHistoricoChat.setIUsuarioDe(suporteUsuarios);
                    suporteHistoricoChatListNewSuporteHistoricoChat = em.merge(suporteHistoricoChatListNewSuporteHistoricoChat);
                    if (oldIUsuarioDeOfSuporteHistoricoChatListNewSuporteHistoricoChat != null && !oldIUsuarioDeOfSuporteHistoricoChatListNewSuporteHistoricoChat.equals(suporteUsuarios)) {
                        oldIUsuarioDeOfSuporteHistoricoChatListNewSuporteHistoricoChat.getSuporteHistoricoChatList().remove(suporteHistoricoChatListNewSuporteHistoricoChat);
                        oldIUsuarioDeOfSuporteHistoricoChatListNewSuporteHistoricoChat = em.merge(oldIUsuarioDeOfSuporteHistoricoChatListNewSuporteHistoricoChat);
                    }
                }
            }
            for (SuporteHistoricoChat suporteHistoricoChatList1OldSuporteHistoricoChat : suporteHistoricoChatList1Old) {
                if (!suporteHistoricoChatList1New.contains(suporteHistoricoChatList1OldSuporteHistoricoChat)) {
                    suporteHistoricoChatList1OldSuporteHistoricoChat.setIUsuarioPara(null);
                    suporteHistoricoChatList1OldSuporteHistoricoChat = em.merge(suporteHistoricoChatList1OldSuporteHistoricoChat);
                }
            }
            for (SuporteHistoricoChat suporteHistoricoChatList1NewSuporteHistoricoChat : suporteHistoricoChatList1New) {
                if (!suporteHistoricoChatList1Old.contains(suporteHistoricoChatList1NewSuporteHistoricoChat)) {
                    SuporteUsuarios oldIUsuarioParaOfSuporteHistoricoChatList1NewSuporteHistoricoChat = suporteHistoricoChatList1NewSuporteHistoricoChat.getIUsuarioPara();
                    suporteHistoricoChatList1NewSuporteHistoricoChat.setIUsuarioPara(suporteUsuarios);
                    suporteHistoricoChatList1NewSuporteHistoricoChat = em.merge(suporteHistoricoChatList1NewSuporteHistoricoChat);
                    if (oldIUsuarioParaOfSuporteHistoricoChatList1NewSuporteHistoricoChat != null && !oldIUsuarioParaOfSuporteHistoricoChatList1NewSuporteHistoricoChat.equals(suporteUsuarios)) {
                        oldIUsuarioParaOfSuporteHistoricoChatList1NewSuporteHistoricoChat.getSuporteHistoricoChatList1().remove(suporteHistoricoChatList1NewSuporteHistoricoChat);
                        oldIUsuarioParaOfSuporteHistoricoChatList1NewSuporteHistoricoChat = em.merge(oldIUsuarioParaOfSuporteHistoricoChatList1NewSuporteHistoricoChat);
                    }
                }
            }
            for (SuporteHistoricoAcesso suporteHistoricoAcessoListOldSuporteHistoricoAcesso : suporteHistoricoAcessoListOld) {
                if (!suporteHistoricoAcessoListNew.contains(suporteHistoricoAcessoListOldSuporteHistoricoAcesso)) {
                    suporteHistoricoAcessoListOldSuporteHistoricoAcesso.setIUsuario(null);
                    suporteHistoricoAcessoListOldSuporteHistoricoAcesso = em.merge(suporteHistoricoAcessoListOldSuporteHistoricoAcesso);
                }
            }
            for (SuporteHistoricoAcesso suporteHistoricoAcessoListNewSuporteHistoricoAcesso : suporteHistoricoAcessoListNew) {
                if (!suporteHistoricoAcessoListOld.contains(suporteHistoricoAcessoListNewSuporteHistoricoAcesso)) {
                    SuporteUsuarios oldIUsuarioOfSuporteHistoricoAcessoListNewSuporteHistoricoAcesso = suporteHistoricoAcessoListNewSuporteHistoricoAcesso.getIUsuario();
                    suporteHistoricoAcessoListNewSuporteHistoricoAcesso.setIUsuario(suporteUsuarios);
                    suporteHistoricoAcessoListNewSuporteHistoricoAcesso = em.merge(suporteHistoricoAcessoListNewSuporteHistoricoAcesso);
                    if (oldIUsuarioOfSuporteHistoricoAcessoListNewSuporteHistoricoAcesso != null && !oldIUsuarioOfSuporteHistoricoAcessoListNewSuporteHistoricoAcesso.equals(suporteUsuarios)) {
                        oldIUsuarioOfSuporteHistoricoAcessoListNewSuporteHistoricoAcesso.getSuporteHistoricoAcessoList().remove(suporteHistoricoAcessoListNewSuporteHistoricoAcesso);
                        oldIUsuarioOfSuporteHistoricoAcessoListNewSuporteHistoricoAcesso = em.merge(oldIUsuarioOfSuporteHistoricoAcessoListNewSuporteHistoricoAcesso);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = suporteUsuarios.getIUsuario();
                if (findSuporteUsuarios(id) == null) {
                    throw new NonexistentEntityException("The suporteUsuarios with id " + id + " no longer exists.");
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
            SuporteUsuarios suporteUsuarios;
            try {
                suporteUsuarios = em.getReference(SuporteUsuarios.class, id);
                suporteUsuarios.getIUsuario();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The suporteUsuarios with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<SuportePontoeletronico> suportePontoeletronicoListOrphanCheck = suporteUsuarios.getSuportePontoeletronicoList();
            for (SuportePontoeletronico suportePontoeletronicoListOrphanCheckSuportePontoeletronico : suportePontoeletronicoListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This SuporteUsuarios (" + suporteUsuarios + ") cannot be destroyed since the SuportePontoeletronico " + suportePontoeletronicoListOrphanCheckSuportePontoeletronico + " in its suportePontoeletronicoList field has a non-nullable IUsuario field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            SuporteAreas IArea = suporteUsuarios.getIArea();
            if (IArea != null) {
                IArea.getSuporteUsuariosList().remove(suporteUsuarios);
                IArea = em.merge(IArea);
            }
            List<SuporteHistoricoChat> suporteHistoricoChatList = suporteUsuarios.getSuporteHistoricoChatList();
            for (SuporteHistoricoChat suporteHistoricoChatListSuporteHistoricoChat : suporteHistoricoChatList) {
                suporteHistoricoChatListSuporteHistoricoChat.setIUsuarioDe(null);
                suporteHistoricoChatListSuporteHistoricoChat = em.merge(suporteHistoricoChatListSuporteHistoricoChat);
            }
            List<SuporteHistoricoChat> suporteHistoricoChatList1 = suporteUsuarios.getSuporteHistoricoChatList1();
            for (SuporteHistoricoChat suporteHistoricoChatList1SuporteHistoricoChat : suporteHistoricoChatList1) {
                suporteHistoricoChatList1SuporteHistoricoChat.setIUsuarioPara(null);
                suporteHistoricoChatList1SuporteHistoricoChat = em.merge(suporteHistoricoChatList1SuporteHistoricoChat);
            }
            List<SuporteHistoricoAcesso> suporteHistoricoAcessoList = suporteUsuarios.getSuporteHistoricoAcessoList();
            for (SuporteHistoricoAcesso suporteHistoricoAcessoListSuporteHistoricoAcesso : suporteHistoricoAcessoList) {
                suporteHistoricoAcessoListSuporteHistoricoAcesso.setIUsuario(null);
                suporteHistoricoAcessoListSuporteHistoricoAcesso = em.merge(suporteHistoricoAcessoListSuporteHistoricoAcesso);
            }
            em.remove(suporteUsuarios);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<SuporteUsuarios> findSuporteUsuariosEntities() {
        return findSuporteUsuariosEntities(true, -1, -1);
    }

    public List<SuporteUsuarios> findSuporteUsuariosEntities(int maxResults, int firstResult) {
        return findSuporteUsuariosEntities(false, maxResults, firstResult);
    }

    private List<SuporteUsuarios> findSuporteUsuariosEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(SuporteUsuarios.class));
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

    public SuporteUsuarios findSuporteUsuarios(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(SuporteUsuarios.class, id);
        } finally {
            em.close();
        }
    }

    public int getSuporteUsuariosCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<SuporteUsuarios> rt = cq.from(SuporteUsuarios.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
//Método Novo
public List<SuporteUsuarios> findSuporteUsuariosEntitiesOrdenado() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(SuporteUsuarios.class));
            Query q = em.createQuery("select u FROM SuporteUsuarios AS u ORDER BY U.iUsuario");
            return q.getResultList();
        } finally {
            em.close();
        }
    }
    
}
