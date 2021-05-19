package br.com.ale.realce.model.entidade;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;

import javax.persistence.Entity;

import br.com.ale.realce.util.Util;

@Entity
public class ItemPedidoCompra extends ItemPedido implements Serializable {

    private static final long serialVersionUID = 1L;

    public ItemPedidoCompra() {
        super();
    }

    public void atualizaTabelaCusto() throws Exception {
        if (produto == null) {
            throw new Exception("Produto não selecionado");
        }
        produto.getTabelaCusto().setValorUnidade(valorUnidade);
    }

    @Override
    public void setValorTotalItem(BigDecimal valorTotalItem) {
        super.setValorTotalItem(valorTotalItem);
        if (valorTotalItem != null) {
            if (valorTotalItem.compareTo(Util.stringParaBigDecimal("0.0")) > 0) {
                valorUnidade = valorTotalItem.divide(Util.stringParaBigDecimal(quantidade.toString()), 2, RoundingMode.CEILING);
                produto.getTabelaCusto().setValorUnidade(valorUnidade);
            }
        }
    }

    @Override
    public void setQuantidade(Integer caixa, Integer unidade) throws Exception {
        super.setQuantidade(caixa, unidade);
        produto.getEstoque().adiciona(super.quantidade);
    }

}
