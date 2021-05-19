package br.com.ale.realce.model.entidade;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

@Entity
public class Categoria implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column(nullable = false, unique = true, length = 60)
	@NotBlank(message = "Nome deve ser informado")
	@Length(max = 60, message = "Nome deve conter máximo de 60 caracteres")
	private String nome;

	@Column(nullable = false, unique = true)
	@NotNull(message = "Código inválido")
	private Integer codigo;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "categoria")
	private List<Produto> produtos = new ArrayList<>();

	public Categoria() {
		this.nome = "";
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
		this.nome = nome;
	}

	public List<Produto> getProdutos() {
		return produtos;
	}

	public void setProdutos(List<Produto> produtos) {
		this.produtos = produtos;
	}

	public Integer getCodigo() {
		return codigo;
	}

	public void setCodigo(Integer codigo) {
		this.codigo = codigo;
	}

	@Override
	public String toString() {
		return getNome();
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;

		Categoria categoria = (Categoria) o;

		return nome.equals(categoria.nome);
	}

	@Override
	public int hashCode() {
		return nome.hashCode();
	}
}
