/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entidades;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Hilton
 */
@Entity
@Table(name = "suporte_historico_chat")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "SuporteHistoricoChat.findAll", query = "SELECT s FROM SuporteHistoricoChat s"),
    @NamedQuery(name = "SuporteHistoricoChat.findByIHistoricoChat", query = "SELECT s FROM SuporteHistoricoChat s WHERE s.iHistoricoChat = :iHistoricoChat"),
    @NamedQuery(name = "SuporteHistoricoChat.findByAlteracao", query = "SELECT s FROM SuporteHistoricoChat s WHERE s.alteracao = :alteracao"),
    @NamedQuery(name = "SuporteHistoricoChat.findByTexto", query = "SELECT s FROM SuporteHistoricoChat s WHERE s.texto = :texto"),
    @NamedQuery(name = "SuporteHistoricoChat.findByVisualizado", query = "SELECT s FROM SuporteHistoricoChat s WHERE s.visualizado = :visualizado")})
public class SuporteHistoricoChat implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "i_historico_chat")
    private Integer iHistoricoChat;
    @Column(name = "alteracao")
    @Temporal(TemporalType.TIMESTAMP)
    private Date alteracao;
    @Lob
    @Column(name = "anexo")
    private byte[] anexo;
    @Column(name = "texto")
    private String texto;
    @Column(name = "visualizado")
    private Boolean visualizado;
    @JoinColumn(name = "i_usuario_de", referencedColumnName = "i_usuario")
    @ManyToOne
    private SuporteUsuarios iUsuarioDe;
    @JoinColumn(name = "i_usuario_para", referencedColumnName = "i_usuario")
    @ManyToOne
    private SuporteUsuarios iUsuarioPara;

    public SuporteHistoricoChat() {
    }
    
    public SuporteHistoricoChat(int iHistoricoChat,String usu_de_nome, String usu_para_nome, Date alteracao, String texto) {
        this.texto = texto;
        this.iHistoricoChat = iHistoricoChat;
        this.alteracao = alteracao;
        this.iUsuarioDe.setUsuarioNome(usu_de_nome);
        this.iUsuarioPara.setUsuarioNome(usu_para_nome);
    } 

    public SuporteHistoricoChat(Integer iHistoricoChat) {
        this.iHistoricoChat = iHistoricoChat;
    }

    public Integer getIHistoricoChat() {
        return iHistoricoChat;
    }

    public void setIHistoricoChat(Integer iHistoricoChat) {
        this.iHistoricoChat = iHistoricoChat;
    }

    public Date getAlteracao() {
        return alteracao;
    }

    public void setAlteracao(Date alteracao) {
        this.alteracao = alteracao;
    }

    public byte[] getAnexo() {
        return anexo;
    }

    public void setAnexo(byte[] anexo) {
        this.anexo = anexo;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public Boolean getVisualizado() {
        return visualizado;
    }

    public void setVisualizado(Boolean visualizado) {
        this.visualizado = visualizado;
    }

    public SuporteUsuarios getIUsuarioDe() {
        return iUsuarioDe;
    }

    public void setIUsuarioDe(SuporteUsuarios iUsuarioDe) {
        this.iUsuarioDe = iUsuarioDe;
    }

    public SuporteUsuarios getIUsuarioPara() {
        return iUsuarioPara;
    }

    public void setIUsuarioPara(SuporteUsuarios iUsuarioPara) {
        this.iUsuarioPara = iUsuarioPara;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (iHistoricoChat != null ? iHistoricoChat.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SuporteHistoricoChat)) {
            return false;
        }
        SuporteHistoricoChat other = (SuporteHistoricoChat) object;
        if ((this.iHistoricoChat == null && other.iHistoricoChat != null) || (this.iHistoricoChat != null && !this.iHistoricoChat.equals(other.iHistoricoChat))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidades.SuporteHistoricoChat[ iHistoricoChat=" + iHistoricoChat + " ]";
    }
    
}
