package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import util.ConexaoMySql;

public class PermiteTurmaDao {
	
	public boolean inserir(int idTurma, int idPermissao) {
		Connection conn = new ConexaoMySql().conectar();
		if (conn==null) {
			return false;			
		}
		try {
			PreparedStatement pst = conn.prepareStatement("insert into permite_turma(id_turma, id_permissao) values(?, ?)");
			pst.setInt(1, idTurma);
			pst.setInt(2, idPermissao);
			pst.execute();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	public boolean alterar(int idTurmaNovo, int idPermissaoNovo, int idTurmaAntigo, int idPermissaoAntigo) {
		Connection conn = new ConexaoMySql().conectar();
		if (conn==null) {
			return false;			
		}
		try {
			PreparedStatement pst = conn.prepareStatement("update permite_turma set id_turma = ?, id_permissao = ? where id_turma = ? and id_permissao = ?");
			pst.setInt(1, idTurmaNovo);
			pst.setInt(2, idPermissaoNovo);
			pst.setInt(3, idTurmaAntigo);
			pst.setInt(4, idPermissaoAntigo);
			pst.execute();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}		
	}
	
	public boolean excluir(int idTurma, int idPermissao) {
		Connection conn = new ConexaoMySql().conectar();
		if (conn==null) {
			return false;			
		}
		try {
			PreparedStatement pst = conn.prepareStatement("delete from permite_turma where id_turma = ? and id_permissao = ?");
			pst.setInt(1, idTurma);
			pst.setInt(2, idPermissao);
			pst.execute();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	public boolean excluir(int idTurma) {
		Connection conn = new ConexaoMySql().conectar();
		if (conn==null) {
			return false;			
		}
		try {
			PreparedStatement pst = conn.prepareStatement("delete from permite_turma where id_turma = ?");
			pst.setInt(1, idTurma);
			pst.execute();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
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

