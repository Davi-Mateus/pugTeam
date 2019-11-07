package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import util.ConexaoMySql;

public class PermiteAlunoDao {
	
	public boolean inserir(int idAluno, int idPermissao) {
		Connection conn = new ConexaoMySql().conectar();
		if (conn==null) {
			return false;			
		}
		try {
			PreparedStatement pst = conn.prepareStatement("insert into permite_aluno(id_aluno, id_permissao) values(?, ?)");
			pst.setInt(1, idAluno);
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
	
	public boolean alterar(int idAlunoNovo, int idPermissaoNovo, int idAlunoAntigo, int idPermissaoAntigo) {
		Connection conn = new ConexaoMySql().conectar();
		if (conn==null) {
			return false;			
		}
		try {
			PreparedStatement pst = conn.prepareStatement("update permite_aluno set id_aluno = ?, id_permissao = ? where id_aluno = ? and id_permissao = ?");
			pst.setInt(1, idAlunoNovo);
			pst.setInt(2, idPermissaoNovo);
			pst.setInt(3, idAlunoAntigo);
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
	
	public boolean excluir(int idAluno, int idPermissao) {
		Connection conn = new ConexaoMySql().conectar();
		if (conn==null) {
			return false;			
		}
		try {
			PreparedStatement pst = conn.prepareStatement("delete from permite_aluno where id_aluno = ? and id_permissao = ?");
			pst.setInt(1, idAluno);
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
}

