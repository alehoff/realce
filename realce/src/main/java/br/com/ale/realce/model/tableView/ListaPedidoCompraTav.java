package br.com.ale.realce.model.tableView;

import br.com.ale.realce.util.Datas;
import br.com.ale.realce.util.Util;

import java.math.BigDecimal;
import java.util.Date;

public class ListaPedidoCompraTav {

    private Long idPedidoCompra;
    private Date data;
    private Long numero;
    private BigDecimal valorTotalPedido;
    private Long itens;

    public ListaPedidoCompraTav(Long idPedidoCompra, Date data, Long numero, BigDecimal valorTotalPedido, Long itens) {
        this.idPedidoCompra = idPedidoCompra;
        this.data = data;
        this.numero = numero;
        this.valorTotalPedido = valorTotalPedido;
        this.itens = itens;
    }

    public Long getIdPedidoCompra() {
        return idPedidoCompra;
    }

    public String getTacData() {
        return Datas.formata(data);
    }

    public String getTacNumero(){
        return Util.formataNumeroPedido(numero);
    }

    public String getTacValorTotalPedido(){
        return Util.toMoeda(valorTotalPedido);
    }

    public String getTacItens(){
        return Util.toString(itens);
    }
}
