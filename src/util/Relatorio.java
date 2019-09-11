package util;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.view.JasperViewer;
import util.ConexaoMySql;

public class Relatorio {
      
	public void gerar(String layout, Map<String, Object> parametros, String titulo) throws JRException , SQLException, ClassNotFoundException {

		Connection conn = new ConexaoMySql().conectar();
		if (conn==null) {
			return;			
		}

		JasperPrint jPrint = JasperFillManager.fillReport( layout , parametros, conn );
   
		JasperViewer jViewer = new JasperViewer( jPrint , false );
		jViewer.setTitle(titulo);
		jViewer.setVisible(true);
	}

}