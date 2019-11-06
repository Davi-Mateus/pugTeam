package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import model.Aluno;
import model.Curso;
import model.Turma;
import util.ConexaoMySql;

public class TurmaDao {
	
	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	
	public boolean inserir(Turma turma) {
		Connection conn = new ConexaoMySql().conectar();
		if (conn==null) {
			return false;			
		}
		try {
			PreparedStatement pst = conn.prepareStatement("insert into turma(nome_turma, ano, dt_inicio, dt_final, id_curso) values(?, ?, ?, ? ,?)");
			pst.setString(1, turma.getNomeTurma());
			pst.setInt(2, turma.getAno());
			pst.setString(3, sdf.format(turma.getDataInicio()));
			pst.setString(4, sdf.format(turma.getDataFinal()));
			pst.setInt(5, turma.getCurso().getIdCurso());
			pst.execute();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public boolean alterar(Turma turma) {
		Connection conn = new ConexaoMySql().conectar();
		if (conn==null) {
			return false;			
		}
		try {
			PreparedStatement pst = conn.prepareStatement("update turma set nome_turma = ?, ano = ?, dt_inicio = ?, dt_final = ?, id_curso = ? where id_turma = ?");
			pst.setString(1, turma.getNomeTurma());
			pst.setInt(2, turma.getAno());
			pst.setString(3, sdf.format(turma.getDataInicio()));
			pst.setString(4, sdf.format(turma.getDataFinal()));
			pst.setInt(5, turma.getCurso().getIdCurso());
			pst.setInt(6, turma.getIdTurma());
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
	
	public boolean excluir(Turma turma) {
		Connection conn = new ConexaoMySql().conectar();
		if (conn==null) {
			return false;			
		}
		try {
			PreparedStatement pst = conn.prepareStatement("delete from turma where id_turma = ?");
			pst.setInt(1, turma.getIdTurma());
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
	
	public int buscarUltimoId() {
		Connection conn = new ConexaoMySql().conectar();
		if (conn==null) {
			
			return 0;			
		}
		try {
			PreparedStatement pst = conn.prepareStatement("select max(id_turma) as id from turma");
			ResultSet rs = pst.executeQuery();
			int id = 0;
			while (rs.next()) {
				id = rs.getInt("id");
			}
			return id;
		} catch (SQLException e) {
			e.printStackTrace();
			return 0;
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	public List<Turma> pesquisarPorNome(String nome) {
		Connection conn = new ConexaoMySql().conectar();
		if (conn==null) {
			return null;			
		}
		try {
			PreparedStatement pst = conn.prepareStatement("select * from turmas where nome_turma like ?");
			pst.setString(1, nome + "%");
			ResultSet rs = pst.executeQuery();
			List<Turma> turmas = new ArrayList<>();
			while (rs.next()) {
				Turma turma = new Turma();
				Curso curso = new Curso();
				curso.setIdCurso(rs.getInt("id_curso"));
				curso.setNomeCurso(rs.getString("nome_curso"));
				turma.setCurso(curso);
				turma.setIdTurma(rs.getInt("id_turma"));
				turma.setNomeTurma(rs.getString("nome_turma"));
				try {
					turma.setAno(rs.getInt("ANO"));
					turma.setDataInicio(new Date((rs.getDate("dt_inicio").getTime())));
					turma.setDataFinal(new Date((rs.getDate("dt_final").getTime())));
				} catch (Exception e) {
				}
				turmas.add(turma);	
			}
			return turmas;
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
	
	public List<Turma> pesquisarPorNomeDeletavel(String nome) {
		Connection conn = new ConexaoMySql().conectar();
		if (conn==null) {
			return null;			
		}
		try {
			PreparedStatement pst = conn.prepareStatement("select * from turmas_deletaveis where nome_turma like ?");
			pst.setString(1, nome + "%");
			ResultSet rs = pst.executeQuery();
			List<Turma> turmas = new ArrayList<>();
			while (rs.next()) {
				Turma turma = new Turma();
				Curso curso = new Curso();
				curso.setIdCurso(rs.getInt("id_curso"));
				curso.setNomeCurso(rs.getString("nome_curso"));
				turma.setCurso(curso);
				turma.setIdTurma(rs.getInt("id_turma"));
				turma.setNomeTurma(rs.getString("nome_turma"));
				try {
					turma.setAno(rs.getInt("ANO"));
					turma.setDataInicio(new Date((rs.getDate("dt_inicio").getTime())));
					turma.setDataFinal(new Date((rs.getDate("dt_final").getTime())));
				} catch (Exception e) {
				}
				turmas.add(turma);	
			}
			return turmas;
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
	
	public List<Turma> pesquisarPorAluno(Aluno aluno) {
		Connection conn = new ConexaoMySql().conectar();
		if (conn==null) {
			return null;			
		}
		try {
			PreparedStatement pst = conn.prepareStatement("select * from turma where id_aluno = ?");
			pst.setInt(1, aluno.getIdAluno());
			ResultSet rs = pst.executeQuery();
			List<Turma> turmas = new ArrayList<>();
			while (rs.next()) {
				Turma turma = new Turma();
				Curso curso = new Curso();
				curso.setIdCurso(rs.getInt("id_curso"));
				curso.setNomeCurso(rs.getString("nome_curso"));
				turma.setCurso(curso);
				turma.setIdTurma(rs.getInt("id_turma"));
				turma.setNomeTurma(rs.getString("nome_turma"));
				try {
					turma.setAno(rs.getInt("ANO"));
					turma.setDataInicio(new Date((rs.getDate("dt_inicio").getTime())));
					turma.setDataFinal(new Date((rs.getDate("dt_final").getTime())));
				} catch (Exception e) {
				}
				turmas.add(turma);	
			}
			return turmas;
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
