package br.com.ale.realce.model.entidade;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import br.com.ale.realce.model.enuns.Operacao;
import br.com.ale.realce.util.Datas;
import br.com.ale.realce.util.Util;

/**
 * Entity implementation class for Entity: HistoricoCaixa
 *
 */
@Entity
public class HistoricoCaixa implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column(nullable = false, length = 120)
	@Length(max = 120, message = "Descrição histórico excede 120 caracteres")
	@NotEmpty(message = "Descrição histórico inválida")
	private String descricao;

	@Column(nullable = false, precision = 12, scale = 2)
	@NotNull(message = "Valor histórico inválido")
	private BigDecimal valor;

	@Column(nullable = false, precision = 12, scale = 2)
	@NotNull(message = "Valor saldo inválido")
	private BigDecimal saldo;

	@Enumerated(EnumType.STRING)
	private Operacao operacao;

	@ManyToOne
	private Caixa caixa;

	@Temporal(TemporalType.TIMESTAMP)
	private Date data;

	private static final long serialVersionUID = 1L;

	public HistoricoCaixa() {
		super();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public BigDecimal getValor() {
		return valor;
	}

	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}

	public BigDecimal getSaldo() {
		return saldo;
	}

	public void setSaldo(BigDecimal saldo) {
		this.saldo = saldo;
	}

	public Operacao getOperacao() {
		return operacao;
	}

	public void setOperacao(Operacao operacao) {
		this.operacao = operacao;
	}

	public Caixa getCaixa() {
		return caixa;
	}

	public void setCaixa(Caixa caixa) {
		this.caixa = caixa;
	}

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}

	/**
	 * Coluna para tableView
	 * 
	 * @return
	 */
	public String getTacHorario() {
		return Datas.formataHoraLocal(data);
	}

	/**
	 * Coluna para tableView
	 * 
	 * @return
	 */
	public String getTacDescricao() {
		return this.descricao;
	}

	/**
	 * Coluna para tableView
	 * 
	 * @return
	 */
	public String getTacOperacao() {
		return this.operacao.getOperacao();
	}

	/**
	 * Coluna para tableView
	 * 
	 * @return
	 */
	public String getTacValor() {
		return Util.toMoeda(valor);
	}

	/**
	 * Coluna para tableView
	 * 
	 * @return
	 */
	public String getTacSaldo() {
		return Util.toMoeda(saldo);
	}

}
