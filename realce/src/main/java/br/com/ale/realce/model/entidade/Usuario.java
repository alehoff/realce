package br.com.ale.realce.model.entidade;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotEmpty;

import org.hibernate.validator.constraints.Length;

import br.com.ale.realce.model.enuns.Privilegio;
import br.com.ale.realce.util.Md5;

/**
 * Entity implementation class for Entity: Usuario
 *
 */
@Entity

public class Usuario implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column(unique = true, nullable = false, length = 15)
	@NotEmpty(message = "Nome de usuário inválido")
	@Length(max = 15, message = "Usuário deve conter de 1 a 15 caracteres", min = 1)
	private String usuario;

	@NotEmpty(message = "Senha inválida")
	@Column(nullable = false, unique = false)
	@Length(min = 6, message = "Senha deve conter mínimo de 6 caracteres")
	private String senha;

	@Enumerated(EnumType.STRING)
	private Privilegio privilegio;

	private static final long serialVersionUID = 1L;

	public Usuario() {
		super();
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsuario() {
		return this.usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public String getSenha() {
		return this.senha;
	}

	public void setSenha(String senha) {
		this.senha = Md5.criptografar(senha);
	}

	public Privilegio getPrivilegio() {
		return privilegio;
	}

	public void setPrivilegio(Privilegio privilegio) {
		this.privilegio = privilegio;
	}
}
