package br.com.marcospcruz.controleestoque.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "Estoque")
@NamedQueries({
		@NamedQuery(name = "itemestoque.readDescricaoPecao", query = "select ie from ItemEstoque ie "
				+ "JOIN ie.produto p "
				+ "where upper(p.descricaoProduto) like :descricao"),
		@NamedQuery(name = "itemEstoque.readAll", query = "select ie from ItemEstoque ie "
				+ "JOIN ie.produto p " + "order by p.descricaoProduto")

})
public class ItemEstoque implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3771838971214907284L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer idItemEstoque;

	@OneToOne
	private Produto produto;

	private Integer quantidade;

	private Date dataContagem;

	public Integer getIdItemEstoque() {
		return idItemEstoque;
	}

	public void setIdItemEstoque(Integer idItemEstoque) {
		this.idItemEstoque = idItemEstoque;
	}

	public Produto getProduto() {
		return produto;
	}

	public void setProduto(Produto produto) {
		this.produto = produto;
	}

	public Integer getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(int quantidade) {
		this.quantidade = quantidade;
	}

	public Date getDataContagem() {
		return dataContagem;
	}

	public void setDataContagem(Date dataContagem) {
		this.dataContagem = dataContagem;
	}

	public void setQuantidade(Integer quantidade) {
		this.quantidade = quantidade;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((dataContagem == null) ? 0 : dataContagem.hashCode());
		result = prime * result
				+ ((idItemEstoque == null) ? 0 : idItemEstoque.hashCode());
		result = prime * result + ((produto == null) ? 0 : produto.hashCode());
		result = prime * result
				+ ((quantidade == null) ? 0 : quantidade.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ItemEstoque other = (ItemEstoque) obj;
		if (dataContagem == null) {
			if (other.dataContagem != null)
				return false;
		} else if (!dataContagem.equals(other.dataContagem))
			return false;
		if (idItemEstoque == null) {
			if (other.idItemEstoque != null)
				return false;
		} else if (!idItemEstoque.equals(other.idItemEstoque))
			return false;
		if (produto == null) {
			if (other.produto != null)
				return false;
		} else if (!produto.equals(other.produto))
			return false;
		if (quantidade == null) {
			if (other.quantidade != null)
				return false;
		} else if (!quantidade.equals(other.quantidade))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "ItemEstoque [idItemEstoque=" + idItemEstoque + ", produto="
				+ produto + ", quantidade=" + quantidade + ", dataContagem="
				+ dataContagem + "]";
	}

}
