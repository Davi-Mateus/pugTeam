package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import util.ConexaoMySql;

public class ImportacaoDao {
	
	public boolean importar(String caminho) {
			
		Connection conn = new ConexaoMySql().conectar();
		if (conn==null) {
			return false;			
		}
		try {
			PreparedStatement pst1 = conn.prepareStatement("load data infile ?" + "into table temporaria fields terminated by ';' ignore 9 lines" + "(numero, matricula, nome, sexo, dt_nascimento, identidade, dt_matricula, cpf)");
			pst1.setString(1, caminho);
			pst1.execute();
			PreparedStatement pst2 = conn.prepareStatement("call pr_add_csv ()");
			pst2.execute();
			return true;
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "Erro:" + e.getMessage());
			return false;
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
}
