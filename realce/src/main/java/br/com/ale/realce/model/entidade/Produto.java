package br.com.ale.realce.model.entidade;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import br.com.ale.realce.model.enuns.Status;

@Entity
public class Produto implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column(nullable = false, unique = true, length = 40)
	@NotNull(message = "Nome deve ser informado")
	@Length(min = 1, max = 40, message = "Nome produto deve conter de 1 a 40 caracteres")
	private String nome;

	@Column(nullable = false, unique = true, length = 20)
	@NotEmpty(message = "Código de leitura inválido!!")
	private String codigoLeitura;

	@Column(nullable = true, length = 13)
	private String codigoBarra;

	@Column(nullable = false)
	@NotNull(message = "Atacado deve ser informado e possuir valor positivo")
	@Min(value = 1)
	private Integer atacado;

	@Column(nullable = false, precision = 5, scale = 2)
	@NotNull(message = "Margem de lucro deve ser informada e ser positiva")
	@Min(value = 1)
	private Double margemLucro;

	@Column(nullable = false, precision = 5)
	@Min(value = 1)
	private Integer codigo;

	@Valid
	@ManyToOne
	private Categoria categoria;

	@Valid
	@OneToOne(mappedBy = "produto", cascade = CascadeType.ALL)
	private Estoque estoque;

	@Enumerated(EnumType.STRING)
	private Status status;

	@Valid
	@OneToOne(mappedBy = "produto", cascade = CascadeType.ALL)
	private TabelaVenda tabelaVenda;

	@Valid
	@OneToOne(mappedBy = "produto", cascade = CascadeType.ALL)
	private TabelaCusto tabelaCusto;

	@Valid
	@ManyToOne
	private Volume volume;

	public Produto() {
		this.codigoLeitura="";
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome.isEmpty() ? null : nome;
	}

	public Integer getAtacado() {
		return atacado;
	}

	public String getAtacadoToString() {
		return this.atacado.toString();
	}

	public void setAtacado(Integer atacado) {
		this.atacado = atacado;
	}

	public void setAtacado(String atacado) {
		try {
			this.atacado = Integer.valueOf(atacado);
		} catch (NumberFormatException e) {
			this.atacado = null;
		}
	}

	public Double getMargemLucro() {
		return margemLucro;
	}

	public void setMargemLucro(Double margemLucro) {
		this.margemLucro = margemLucro;
	}

	public String getMargemLucroToString() {
		return this.margemLucro.toString().replace(".", ",");
	}

	public void setMargemLucro(String margemLucro) {
		try {
			this.margemLucro = Double.valueOf(margemLucro.replace(",", "."));
		} catch (NumberFormatException e) {
			this.margemLucro = null;
		}
	}

	public Categoria getCategoria() {
		return categoria;
	}

	public void setCategoria(Categoria categoria) {
		this.categoria = categoria;
	}

	public Estoque getEstoque() {
		return estoque;
	}

	public void setEstoque(Estoque estoque) {
		this.estoque = estoque;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public TabelaVenda getTabelaVenda() {
		return tabelaVenda;
	}

	public void setTabelaVenda(TabelaVenda tabelaVenda) {
		this.tabelaVenda = tabelaVenda;
	}

	public String getCodigoLeitura() {
		return codigoLeitura;
	}

	public void setCodigoLeitura(String codigoLeitura) {
		this.codigoLeitura = codigoLeitura;
	}

	public String getCodigoBarra() {
		return codigoBarra;
	}

	public void setCodigoBarra(String codigoBarra) {
		this.codigoBarra = codigoBarra;
	}

	public Integer getCodigo() {
		return codigo;
	}

	public void setCodigo(Integer codigo) {
		this.codigo = codigo;
	}

	public Volume getVolume() {
		return volume;
	}

	public void setVolume(Volume volume) {
		this.volume = volume;
	}

	public TabelaCusto getTabelaCusto() {
		return tabelaCusto;
	}

	public void setTabelaCusto(TabelaCusto tabelaCusto) {
		this.tabelaCusto = tabelaCusto;
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
		Produto other = (Produto) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	public String getDescricaoProduto() {
		return this.categoria.getNome() + " " + this.nome + " " + this.getAtacadoToString() + "/"
				+ volume.getVolume().toString();
	}

	public void atualizaStatus() {
		this.status = this.status.equals(Status.ATIVO) ? Status.INATIVO : Status.ATIVO;
	}

}
