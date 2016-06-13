/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entidades;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Hilton
 */
@Entity
@Table(name = "suporte_acesso_remoto")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "SuporteAcessoRemoto.findAll", query = "SELECT s FROM SuporteAcessoRemoto s"),
    @NamedQuery(name = "SuporteAcessoRemoto.findByIAcesso", query = "SELECT s FROM SuporteAcessoRemoto s WHERE s.iAcesso = :iAcesso"),
    @NamedQuery(name = "SuporteAcessoRemoto.findByAlteracao", query = "SELECT s FROM SuporteAcessoRemoto s WHERE s.alteracao = :alteracao"),
    @NamedQuery(name = "SuporteAcessoRemoto.findByComando", query = "SELECT s FROM SuporteAcessoRemoto s WHERE s.comando = :comando"),
    @NamedQuery(name = "SuporteAcessoRemoto.findByIdTeamviewer", query = "SELECT s FROM SuporteAcessoRemoto s WHERE s.idTeamviewer = :idTeamviewer"),
    @NamedQuery(name = "SuporteAcessoRemoto.findByIpPorta", query = "SELECT s FROM SuporteAcessoRemoto s WHERE s.ipPorta = :ipPorta"),
    @NamedQuery(name = "SuporteAcessoRemoto.findByLink", query = "SELECT s FROM SuporteAcessoRemoto s WHERE s.link = :link"),
    @NamedQuery(name = "SuporteAcessoRemoto.findByNomeAcesso", query = "SELECT s FROM SuporteAcessoRemoto s WHERE s.nomeAcesso = :nomeAcesso"),
    @NamedQuery(name = "SuporteAcessoRemoto.findByOnline", query = "SELECT s FROM SuporteAcessoRemoto s WHERE s.online = :online"),
    @NamedQuery(name = "SuporteAcessoRemoto.findBySenha", query = "SELECT s FROM SuporteAcessoRemoto s WHERE s.senha = :senha"),
    @NamedQuery(name = "SuporteAcessoRemoto.findBySenhaTeamviewer", query = "SELECT s FROM SuporteAcessoRemoto s WHERE s.senhaTeamviewer = :senhaTeamviewer"),
    @NamedQuery(name = "SuporteAcessoRemoto.findBySituacao", query = "SELECT s FROM SuporteAcessoRemoto s WHERE s.situacao = :situacao"),
    @NamedQuery(name = "SuporteAcessoRemoto.findByUsuario", query = "SELECT s FROM SuporteAcessoRemoto s WHERE s.usuario = :usuario")})
public class SuporteAcessoRemoto implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "i_acesso")
    private Integer iAcesso;
    @Column(name = "alteracao")
    @Temporal(TemporalType.TIME)
    private Date alteracao;
    @Column(name = "comando")
    private String comando;
    @Column(name = "id_teamviewer")
    private Integer idTeamviewer;
    @Column(name = "ip_porta")
    private String ipPorta;
    @Column(name = "link")
    private String link;
    @Column(name = "nome_acesso")
    private String nomeAcesso;
    @Column(name = "online")
    private Boolean online;
    @Column(name = "senha")
    private String senha;
    @Column(name = "senha_teamviewer")
    private String senhaTeamviewer;
    @Column(name = "situacao")
    private String situacao;
    @Column(name = "usuario")
    private String usuario;
    @JoinColumn(name = "i_entidade", referencedColumnName = "i_entidade")
    @ManyToOne
    private SuporteEntidades iEntidade;
    @OneToMany(mappedBy = "iAcesso")
    private List<SuporteHistoricoAcesso> suporteHistoricoAcessoList;

    public SuporteAcessoRemoto() {
    }
    
    public SuporteAcessoRemoto(int i_acesso, String entidade, String host, String tipo, boolean estado) {
        System.out.println(entidade);
        this.iAcesso = i_acesso;
        this.iEntidade.setEntidadeNome(entidade);
        this.nomeAcesso = host;
        this.comando = tipo;
        this.online = estado;
    }

    public SuporteAcessoRemoto(Integer iAcesso) {
        this.iAcesso = iAcesso;
    }

    public Integer getIAcesso() {
        return iAcesso;
    }

    public void setIAcesso(Integer iAcesso) {
        this.iAcesso = iAcesso;
    }

    public Date getAlteracao() {
        return alteracao;
    }

    public void setAlteracao(Date alteracao) {
        this.alteracao = alteracao;
    }

    public String getComando() {
        return comando;
    }

    public void setComando(String comando) {
        this.comando = comando;
    }

    public Integer getIdTeamviewer() {
        return idTeamviewer;
    }

    public void setIdTeamviewer(Integer idTeamviewer) {
        this.idTeamviewer = idTeamviewer;
    }

    public String getIpPorta() {
        return ipPorta;
    }

    public void setIpPorta(String ipPorta) {
        this.ipPorta = ipPorta;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getNomeAcesso() {
        return nomeAcesso;
    }

    public void setNomeAcesso(String nomeAcesso) {
        this.nomeAcesso = nomeAcesso;
    }

    public Boolean getOnline() {
        return online;
    }

    public void setOnline(Boolean online) {
        this.online = online;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getSenhaTeamviewer() {
        return senhaTeamviewer;
    }

    public void setSenhaTeamviewer(String senhaTeamviewer) {
        this.senhaTeamviewer = senhaTeamviewer;
    }

    public String getSituacao() {
        return situacao;
    }

    public void setSituacao(String situacao) {
        this.situacao = situacao;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public SuporteEntidades getIEntidade() {
        return iEntidade;
    }

    public void setIEntidade(SuporteEntidades iEntidade) {
        this.iEntidade = iEntidade;
    }

    @XmlTransient
    public List<SuporteHistoricoAcesso> getSuporteHistoricoAcessoList() {
        return suporteHistoricoAcessoList;
    }

    public void setSuporteHistoricoAcessoList(List<SuporteHistoricoAcesso> suporteHistoricoAcessoList) {
        this.suporteHistoricoAcessoList = suporteHistoricoAcessoList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (iAcesso != null ? iAcesso.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SuporteAcessoRemoto)) {
            return false;
        }
        SuporteAcessoRemoto other = (SuporteAcessoRemoto) object;
        if ((this.iAcesso == null && other.iAcesso != null) || (this.iAcesso != null && !this.iAcesso.equals(other.iAcesso))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidades.SuporteAcessoRemoto[ iAcesso=" + iAcesso + " ]";
    }
    
}
