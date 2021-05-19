package br.com.ale.realce.model.entidade;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.ManyToOne;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * Entity implementation class for Entity: ItemPedido
 */
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public abstract class ItemPedido implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    protected Long id;

    @Column(nullable = false)
    @Min(value = 1)
    @NotNull(message = "Quantidade deve ser informada para item do pedido")
    protected Integer quantidade;

    @Column(nullable = false)
    @Min(value = (int) 0.1)
    @NotNull(message = "Valor unidade deve ser informada para item do pedido")
    protected BigDecimal valorUnidade;

    @Column(nullable = false, precision = 12, scale = 4)
    @Min(value = (int) 0.1)
    @NotNull(message = "Valor total deve ser informado para item do pedido")
    protected BigDecimal valorTotalItem;

    @ManyToOne(cascade = CascadeType.MERGE)
    @Valid
    @NotNull(message = "Produto não selecionado para este item")
    protected Produto produto;

    @ManyToOne
    protected Pedido pedido;

    public ItemPedido() {
        super();
        this.quantidade = 0;
        this.valorUnidade = new BigDecimal("0.0");
        this.valorTotalItem = new BigDecimal("0.0");
    }

    public Long getId() {
        return id;
    }

    public Integer getQuantidade() {
        return quantidade;
    }

    public BigDecimal getValorUnidade() {
        return valorUnidade;
    }

    public BigDecimal getValorTotalItem() {
        return valorTotalItem;
    }

    public Produto getProduto() {
        return produto;
    }

    public Pedido getPedido() {
        return pedido;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setValorUnidade(BigDecimal valorUnidade) {
        this.valorUnidade = valorUnidade;
    }

    public void setValorTotalItem(BigDecimal valorTotalItem) {
        this.valorTotalItem = valorTotalItem;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }

    public void setPedido(Pedido pedido) {
        this.pedido = pedido;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((produto == null) ? 0 : produto.hashCode());
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
        ItemPedido other = (ItemPedido) obj;
        if (produto == null) {
            return other.produto == null;
        } else
            return produto.equals(other.produto);
    }

    public void setQuantidade(Integer caixa, Integer unidade) throws Exception {

        try {
            if (caixa > 0) {
                quantidade = (caixa * produto.getAtacado()) + unidade;
            } else {
                quantidade = unidade;
            }
        } catch (Exception e) {
            quantidade = null;
        }
    }

}
