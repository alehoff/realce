package br.com.ale.realce.model.entidade;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import br.com.ale.realce.util.Util;

@Entity
public class Estoque implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column(nullable = false, precision = 5)
	@NotNull(message = "Quantidade em estoque deve ser informado")
	private Integer quantidade;

	@Column(nullable = false, precision = 5)
	@NotNull(message = "Valor mínimo deve ser informado")
	@Min(value = 1, message = "Valor positivos são aceitos")
	private Integer minimo;

	private Boolean comprar;

	@OneToOne
	private Produto produto;

	private static final long serialVersionUID = 1L;

	public Estoque() {
		this.quantidade = 0;
		this.minimo = 1;
		this.comprar = true;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(Integer quantidade) {
		this.quantidade = quantidade;
		precisaComprar();
	}

	public Integer getMinimo() {
		return minimo;
	}

	public void setMinimo(Integer minimo) {
		this.minimo = minimo;
		precisaComprar();
	}

	public Boolean getComprar() {
		return comprar;
	}

	public void setComprar(Boolean comprar) {
		this.comprar = comprar;
	}

	public Produto getProduto() {
		return produto;
	}

	public void setProduto(Produto produto) {
		this.produto = produto;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;

		Estoque estoque = (Estoque) o;

		return id.equals(estoque.id);
	}

	@Override
	public int hashCode() {
		return id.hashCode();
	}

	public String getTacNome() {
		return this.produto.getDescricaoProduto();
	}

	public String getTacComprar() {
		if (this.comprar) {
			return "Comprar";
		}
		return "";
	}

	public String getTacMinimo() {
		return Util.toCaixaUnidade(minimo, produto.getAtacado());
	}

	public String getTacQuantidade() {
		return Util.toCaixaUnidade(quantidade, produto.getAtacado());
	}

	public void setMinimo(String valor) {
		try {
			this.minimo = Integer.valueOf(valor);
		} catch (Exception e) {
			this.minimo = null;
		} finally {
			precisaComprar();
		}
	}

	/**
	 * Verifica se estoque mínimo é superior ao estoque atual
	 */
	private void precisaComprar() {

		if (minimo != null && quantidade != null) {
			comprar = minimo > quantidade;
		}
	}

	/**
	 * Adiciona quantidade informada ao estoque do produto
	 * 
	 * @param quantidade
	 */
	public void adiciona(Integer quantidade) {
		this.quantidade += quantidade;
		precisaComprar();
	}

	/**
	 * remove quantidade informada do estoque se a mesma for inferior ou igual a
	 * quantidade em estoque
	 * 
	 * @param quantidade1
	 * @throws Exception
	 */
	public void subtrair(Integer quantidade1) throws Exception {

		if (quantidade1 > quantidade) {
			throw new Exception("Estoque insuficiente para quantidade informada");
		}

		this.quantidade -= quantidade1;
		precisaComprar();
	}

	public String getQuantidadeEmCaixaUnidade() {

		if (quantidade == null) {
			return "00 / 00";
		}
		if (produto == null) {
			return "00 / 00";
		}
		return Util.toCaixaUnidade(quantidade, produto.getAtacado());
	}

	public void possuiEstoque(Integer quantidade1) throws Exception {
		if (quantidade1 > quantidade) {
			throw new Exception("Quantidade superior ao estoque disponível");
		}
	}
}
