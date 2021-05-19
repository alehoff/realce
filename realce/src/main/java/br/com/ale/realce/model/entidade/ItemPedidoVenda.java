package br.com.ale.realce.model.entidade;

import java.io.Serializable;

import javax.persistence.Entity;

import br.com.ale.realce.util.Util;

/**
 * Entity implementation class for Entity: ItemPedidoVenda
 */
@Entity
public class ItemPedidoVenda extends ItemPedido implements Serializable {

    private static final long serialVersionUID = 1L;

    public ItemPedidoVenda() {
        super();
    }

    @Override
    public void setProduto(Produto produto) {
        super.setProduto(produto);
        if (produto != null) {
            valorUnidade = produto.getTabelaVenda().getValorUnidade();
        }
    }

    @Override
    public void setQuantidade(Integer caixa, Integer unidade) throws Exception {

        super.setQuantidade(caixa, unidade);//calcula quantidade
        if (quantidade != null ) {
            valorTotalItem = valorUnidade.multiply(Util.toBigDecimal(quantidade));//calcula total do item
        }
    }


}
