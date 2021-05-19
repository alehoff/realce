package br.com.ale.realce.model.tableView;

public class ListaProdutoTav extends ProdutoTav {

	private Long id;

	private String codigoLeitura;

	/**
	 * @param id
	 * @param nome
	 * @param codigoLeitura
	 * @param atacado
	 * @param categoria
	 * @param volume
	 */
	public ListaProdutoTav(Long id, String nome, String codigoLeitura, Integer atacado, String categoria,
			Integer volume) {
		super(nome, atacado, categoria, volume);
		this.id = id;
		this.codigoLeitura = codigoLeitura;
	}

	public Long getId() {
		return id;
	}

	public String getTacCodigo() {
		return codigoLeitura;
	}
}
