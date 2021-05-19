package br.com.ale.realce.model.tableView;

import br.com.ale.realce.util.Util;

import java.math.BigDecimal;

public class ListaItemPedidoTav extends ProdutoTav {

    private int index;
    private Integer quantidade;
    private BigDecimal valorUnidade;
    private BigDecimal valorTotal;

    /**
     * @param nome
     * @param atacado
     * @param categoria
     * @param volume
     * @param quantidade
     * @param valorUnidade
     * @param valorTotal
     */
    public ListaItemPedidoTav
    (
            int index,
            String nome,
            Integer atacado,
            String categoria,
            Integer volume,
            Integer quantidade,
            BigDecimal valorUnidade,
            BigDecimal valorTotal
    )
    {
        super(nome, atacado, categoria, volume);
        this.quantidade = quantidade;
        this.valorUnidade = valorUnidade;
        this.valorTotal = valorTotal;
        this.index = index;
    }

    public String getTacQuantidade() {
        return Util.toCaixaUnidade(quantidade, getAtacado());
    }

    public String getTacValorUnidade() {
        return Util.toMoeda(valorUnidade);
    }

    public String getTacValorTotal() {
        return Util.toMoeda(valorTotal);
    }

    public int getIndex() {
        return index;
    }
}
