package dao;

import java.sql.Connection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import model.Permissao;
import model.Visitante;
import util.ConexaoMySql;

public class VisitanteDao {
	
	public boolean inserir(Visitante visitante) {
		Connection conn = new ConexaoMySql().conectar();
		if (conn==null) {
			return false;			
		}
		try {
			PreparedStatement pst = conn.prepareStatement("insert into visitante(nome_visitante, motivo, id_permissao) values(?, ?, ?)");
			pst.setString(1, visitante.getNomeVisitante());
			pst.setString(2, visitante.getMotivoVisita());
			pst.setInt(3, visitante.getPermissao().getIdPermissao());
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
	
	public boolean alterar(Visitante visitante) {
		Connection conn = new ConexaoMySql().conectar();
		if (conn==null) {
			return false;			
		}
		try {
			PreparedStatement pst = conn.prepareStatement("update visitante set nome_visitante = ?, motivo = ? where id_visitante = ? ");
			pst.setString(1, visitante.getNomeVisitante());
			pst.setString(2, visitante.getMotivoVisita());
			pst.setInt(3, visitante.getIdVisitante());
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
	
	public boolean excluir(Visitante visitante) {
		Connection conn = new ConexaoMySql().conectar();
		if (conn==null) {
			return false;			
		}
		try {
			PreparedStatement pst = conn.prepareStatement("delete from visitante where id_visitante = ?");
			pst.setInt(1, visitante.getIdVisitante());
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
	
	public List<Visitante> pesquisarPorNome(String nome) {
		Connection conn = new ConexaoMySql().conectar();
		if (conn==null) {
			return null;			
		}
		try {
			PreparedStatement pst = conn.prepareStatement("select * from visitantes where nome_visitante like ?");
			pst.setString(1, nome + "%");
			ResultSet rs = pst.executeQuery();
			List<Visitante> visitantes = new ArrayList<>();
			while (rs.next()) {
				Visitante visitante = new Visitante();
				Permissao permissao = new Permissao();
				permissao.setIdPermissao(rs.getInt("id_permissao"));
				permissao.setResponsavel(rs.getString("responsavel"));
				permissao.setTipoPermissao(rs.getString("tipo"));
				permissao.setDataPermissao(new Date(rs.getDate("dt_permissao").getTime()));
				visitante.setPermissao(permissao);
				visitante.setIdVisitante(rs.getInt("id_visitante"));
				visitante.setNomeVisitante(rs.getString("nome_visitante"));
				visitante.setMotivoVisita(rs.getString("motivo"));
				visitantes.add(visitante);
			}
			return visitantes;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	public List<Visitante> pesquisarVisitantesDia() {
		Connection conn = new ConexaoMySql().conectar();
		if (conn==null) {
			return null;			
		}
		try {
			PreparedStatement pst = conn.prepareStatement("select * from visitantes_do_dia");
			ResultSet rs = pst.executeQuery();
			List<Visitante> visitantes = new ArrayList<>();
			while (rs.next()) {
				Visitante visitante = new Visitante();
				Permissao permissao = new Permissao();
				permissao.setIdPermissao(rs.getInt("id_permissao"));
				permissao.setResponsavel(rs.getString("responsavel"));
				permissao.setTipoPermissao(rs.getString("tipo"));
				permissao.setDataPermissao(new Date(rs.getDate("dt_permissao").getTime()));
				visitante.setPermissao(permissao);
				visitante.setIdVisitante(rs.getInt("id_visitante"));
				visitante.setNomeVisitante(rs.getString("nome_visitante"));
				visitante.setMotivoVisita(rs.getString("motivo"));
				visitantes.add(visitante);
			}
			return visitantes;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
}