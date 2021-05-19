package br.com.ale.realce.util;

public class PaginacaoUtil {

	private final int totalLinhas = 10;
	private final int totalIndicesPorPagina = 10;

	public PaginacaoUtil() {
	}

	public int getTotalLinhas() {
		return totalLinhas;
	}

	public int getTotalIndicesPorPagina() {
		return totalIndicesPorPagina;
	}

	public int getindiceBancoDados(int indice) {
		if (indice == 0) {
			return 0;
		}
		return indice * totalLinhas;
	}

	public int getMaxPage(long totalRegistros) {

		if (totalRegistros < totalLinhas) {
			return 1;
		}

		int indice = (int) totalRegistros / totalLinhas;

		if (totalRegistros % totalLinhas > 0) {
			indice++;
		}
		return indice;
	}
}
