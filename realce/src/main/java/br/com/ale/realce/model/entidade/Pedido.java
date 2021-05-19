package br.com.ale.realce.model.entidade;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@SequenceGenerator(name = Pedido.SEQUENCE_NAME, sequenceName = Pedido.SEQUENCE_NAME, initialValue = 1, allocationSize = 3)
public abstract class Pedido implements Serializable {

	public static final String SEQUENCE_NAME = "SEQUENCIA_PEDIDO";

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = SEQUENCE_NAME)
	protected Long id;

	@Column(nullable = false,unique = false)
	@NotNull(message = "Número pedido inválido")
	protected Long numero;

	@Temporal(TemporalType.DATE)
	protected Date data;

	@Column(unique = false, nullable = false, precision = 12, scale = 2)
	@NotNull(message = "Valor pedido deve ser informado")
	@Min(value = 0, message = "Valor pedido deve possuir valor positivo")
	protected BigDecimal valorTotalPedido;

	@ManyToOne
	protected Usuario usuario;

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "pedido", orphanRemoval = true)
	//@OrderBy("produto.nome")
	protected List<ItemPedido> itensPedido = new ArrayList<ItemPedido>();

	private static final long serialVersionUID = 1L;

	public Pedido() {
		super();
		this.numero = 0L;
		this.valorTotalPedido = new BigDecimal("0.0");
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getData() {

		return this.data;
	}

	public void setData(Date data) {
		this.data = data;
	}

	public BigDecimal getValorTotalPedido() {
		return this.valorTotalPedido;
	}	

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public Long getNumero() {
		return numero;
	}

	public void setNumero(Long numero) {
		this.numero = numero;
	}

	public List<ItemPedido> getItensPedido() {
		return itensPedido;
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
		Pedido other = (Pedido) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
}
