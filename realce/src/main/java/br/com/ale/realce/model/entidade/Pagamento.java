package br.com.ale.realce.model.entidade;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import br.com.ale.realce.model.enuns.FormaPagamento;
import br.com.ale.realce.util.Util;

/**
 * Entity implementation class for Entity: Pagamento
 */
@Entity
public class Pagamento implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false, precision = 12, scale = 2)
    @NotNull(message = "Valor pagamento deve ser informado!!")
    private BigDecimal valor;

    @ManyToOne
    private PedidoVenda pedidoVenda;

    @Enumerated(EnumType.STRING)
    private FormaPagamento formaPagamento;


    private static final long serialVersionUID = 1L;

    public Pagamento() {
        super();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public PedidoVenda getPedidoVenda() {
        return pedidoVenda;
    }

    public void setPedidoVenda(PedidoVenda pedidoVenda) {
        this.pedidoVenda = pedidoVenda;
    }

    public FormaPagamento getFormaPagamento() {
        return formaPagamento;
    }

    public void setFormaPagamento(FormaPagamento formaPagamento) {
        this.formaPagamento = formaPagamento;
    }

    public String getTacDescricaoPagamento() {
        return "Total pago em: " + formaPagamento.getFormaPagamento();
    }
	public String getTacValorPagamento(){
        return Util.toMoeda(valor);
    }

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((formaPagamento == null) ? 0 : formaPagamento.hashCode());
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
		Pagamento other = (Pagamento) obj;
		if (formaPagamento != other.formaPagamento)
			return false;
		return true;
	}


}
