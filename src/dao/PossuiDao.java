package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import util.ConexaoMySql;

public class PossuiDao {
	public boolean inserir(int idTurma, int idQuadroHorarios) {
		Connection conn = new ConexaoMySql().conectar();
		if (conn==null) {
			return false;			
		}
		try {
			PreparedStatement pst = conn.prepareStatement("insert into possui (id_turma, id_horario) values(?, ?)");
			pst.setInt(1, idTurma);
			pst.setInt(2, idQuadroHorarios);
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
			PreparedStatement pst = conn.prepareStatement("delete from possui where id_turma = ?");
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
	
	public boolean excluirPorHorario(int idHorario) {
		Connection conn = new ConexaoMySql().conectar();
		if (conn==null) {
			return false;			
		}
		try {
			PreparedStatement pst = conn.prepareStatement("delete from possui where id_horario = ?");
			pst.setInt(1, idHorario);
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
