package br.com.ale.realce.model.entidade;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.validation.constraints.NotNull;

@Entity
public class TabelaCusto extends Tabela {

	private static final long serialVersionUID = 1L;

	@Column(precision = 10, scale = 4)
	@NotNull(message = "Valor Sugerido ninválido")
	private BigDecimal valorSugerido;

	public TabelaCusto() {
		super();
		this.valorSugerido = new BigDecimal("0.01");
	}

	public void setValorUnidade(BigDecimal valor) {
		super.setValorUnidade(valor);
		atualizaValorSugerido();
	}

	public void atualizaValorSugerido() {
		if (valorUnidade.compareTo(new BigDecimal("0.01")) > 0) {
			Double margem = produto.getMargemLucro();
			margem /= 100;
			margem += 1;
			this.valorSugerido = valorUnidade.multiply(new BigDecimal(margem));
		}
	}

}
