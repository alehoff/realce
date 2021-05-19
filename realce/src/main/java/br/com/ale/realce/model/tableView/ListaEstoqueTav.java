package br.com.ale.realce.model.tableView;

import br.com.ale.realce.util.Util;

public class ListaEstoqueTav extends  ProdutoTav{

    private Long idEstoque;
    private Integer minimo;
    private Integer quantidade;
    private boolean comprar;

    /**
     * @param nome
     * @param atacado
     * @param categoria
     * @param volume
     */
    public ListaEstoqueTav(String nome, Integer atacado, String categoria, Integer volume,
                           Long idEstoque,Integer quantidade,Integer minimo,boolean comprar) {
        super(nome, atacado, categoria, volume);
        this.idEstoque = idEstoque;
        this.minimo = minimo;
        this.comprar = comprar;
        this.quantidade  = quantidade;
    }

    public String getTacQuantidade(){
        return Util.toCaixaUnidade(quantidade,getAtacado());
    }

    public String getTacMinimo(){
        return Util.toCaixaUnidade(minimo,getAtacado());
    }

    public String getTacComprar(){
        if(comprar) return "Comprar";
        return "";
    }

    public Long getIdEstoque() {
        return idEstoque;
    }
}
