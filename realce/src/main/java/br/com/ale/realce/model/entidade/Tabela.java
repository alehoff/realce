package br.com.ale.realce.model.entidade;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.OneToOne;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public abstract class Tabela implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	protected Long id;

	@Column(nullable = false, precision = 10, scale = 4)
	@Min(value = (int) 0.01, message = "Valores unitários positivos são aceitos")
	@NotNull(message = "Valor unitário incorreto")
	protected BigDecimal valorUnidade;

	@Column(nullable = true, precision = 10, scale = 4)
	protected BigDecimal valorAtacado;

	@OneToOne
	protected Produto produto;

	public Tabela() {
		this.valorAtacado = new BigDecimal("0.01");
		this.valorUnidade = new BigDecimal("0.01");
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
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
		Tabela other = (Tabela) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	public BigDecimal getValorAtacado() {
		return valorAtacado;
	}

	public void setValorAtacado(BigDecimal valorAtacado) {
		this.valorAtacado = valorAtacado;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public BigDecimal getValorUnidade() {
		return valorUnidade;
	}

	public void setValorUnidade(BigDecimal valorUnidade) {
		try {
			this.valorUnidade = valorUnidade;
			this.valorAtacado = this.valorUnidade.multiply(new BigDecimal(produto.getAtacado())).setScale(2,
					RoundingMode.HALF_UP);
		} catch (Exception e) {
			this.valorUnidade = null;
		}
	}

	public Produto getProduto() {
		return produto;
	}

	public void setProduto(Produto produto) {
		this.produto = produto;
	}

}
