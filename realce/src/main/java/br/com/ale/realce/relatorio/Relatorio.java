package br.com.ale.realce.relatorio;

import java.io.InputStream;
import java.util.HashMap;
import java.util.List;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.view.JasperViewer;

public abstract class Relatorio {

	protected void gerarRelatorio(HashMap<String, Object> map, List<?> lista, String url) throws JRException {

		InputStream arq = getClass().getResourceAsStream(url);

		JasperReport jr = JasperCompileManager.compileReport(arq);

		JasperPrint print = JasperFillManager.fillReport(jr, map, new JRBeanCollectionDataSource(lista));

		JasperViewer.viewReport(print, false);
	}

}
