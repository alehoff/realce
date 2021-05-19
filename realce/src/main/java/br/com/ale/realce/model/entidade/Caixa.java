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
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import br.com.ale.realce.model.enuns.Operacao;
import br.com.ale.realce.util.ObjectFactory;
import br.com.ale.realce.util.Util;
import br.com.ale.realce.util.ValidaUtil;

/**
 * Entity implementation class for Entity: Caixa
 *
 */
@Entity
public class Caixa implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column(nullable = false, scale = 2, precision = 12)
	@NotNull(message = "Saldo inválido")
	private BigDecimal saldo;

	@Temporal(TemporalType.DATE)
	private Date data;

	@ManyToOne
	private Usuario usuario;

	@OneToMany(mappedBy = "caixa", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
	private List<HistoricoCaixa> historicosCaixa = new ArrayList<HistoricoCaixa>();

	private static final long serialVersionUID = 1L;

	public Caixa() {
		super();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public BigDecimal getSaldo() {
		return saldo;
	}

	public void setSaldo(BigDecimal saldo) {
		this.saldo = saldo;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}

	public List<HistoricoCaixa> getHistoricosCaixa() {
		return historicosCaixa;
	}

	public void setHistoricosCaixa(List<HistoricoCaixa> historicosCaixa) {
		this.historicosCaixa = historicosCaixa;
	}

	public void addHistoricoCaixa(List<Pagamento> pagamentos) {

		HistoricoCaixa historicoCaixa;

		for (Pagamento pg : pagamentos) {

			historicoCaixa = ObjectFactory.getHistoricoCaixa(this);

			historicoCaixa.setDescricao(
					pg.getTacDescricaoPagamento() + " (Pedido nº " + Util.formataNumeroPedido(pg.getPedidoVenda().getNumero()) + " )");
			historicoCaixa.setOperacao(Operacao.CREDITO);
			historicoCaixa.setValor(pg.getValor());
			saldo = saldo.add(pg.getValor());
			historicoCaixa.setSaldo(saldo);

			historicosCaixa.add(historicoCaixa);
		}
	}

	public void setCredito(BigDecimal valor, String descricao, Date data) throws Exception {

		HistoricoCaixa hc = ObjectFactory.getHistoricoCaixa(this);

		hc.setData(data);
		hc.setDescricao(descricao);
		hc.setOperacao(Operacao.CREDITO);
		hc.setValor(valor);
		saldo = saldo.add(valor);
		hc.setSaldo(saldo);

		new ValidaUtil<HistoricoCaixa>().valida(hc);

		historicosCaixa.add(hc);
	}

	public void setDebito(BigDecimal valor, String descricao, Date data) throws Exception {
		HistoricoCaixa hc = ObjectFactory.getHistoricoCaixa(this);

		hc.setData(data);
		hc.setDescricao(descricao);
		hc.setOperacao(Operacao.DEBITO);
		hc.setValor(valor);
		saldo = saldo.subtract(valor);
		hc.setSaldo(saldo);

		new ValidaUtil<HistoricoCaixa>().valida(hc);

		historicosCaixa.add(hc);

	}

}
