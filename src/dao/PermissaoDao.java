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
import model.Permissao;
import model.Turma;
import model.Visitante;
import util.ConexaoMySql;

public class PermissaoDao {
	
	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	
	public boolean inserirPermissao(Permissao permissao) {
		Connection conn = new ConexaoMySql().conectar();
		if (conn==null) {
			return false;			
		}
		try {
			PreparedStatement pst = conn.prepareStatement("insert into permissao (responsavel, tipo, dt_permissao) values(?, ?, ?)");
			pst.setString(1, permissao.getResponsavel());
			pst.setString(2, permissao.getTipoPermissao());
			pst.setString(3, sdf.format(permissao.getDataPermissao()));
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
	
	public boolean alterar(Permissao permissao) {
		Connection conn = new ConexaoMySql().conectar();
		if (conn==null) {
			return false;			
		}
		try {
			PreparedStatement pst = conn.prepareStatement("upsdate permissao set responsavel = ?, tipo = ?, dt_permissao = ?  where id_permissao = ?");
			pst.setString(1, permissao.getResponsavel());
			pst.setString(2, permissao.getTipoPermissao());
			pst.setString(3, sdf.format(permissao.getDataPermissao()));
			pst.setInt(4, permissao.getIdPermissao());
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
	
	public boolean excluir(Permissao permissao) {
		Connection conn = new ConexaoMySql().conectar();
		if (conn==null) {
			return false;			
		}
		try {
			PreparedStatement pst = conn.prepareStatement("delete from permissao where id_permissao = ?");
			pst.setInt(1, permissao.getIdPermissao());
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
			PreparedStatement pst = conn.prepareStatement("select max(id_permissao) as id from permissao");
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
	
	public List<Permissao> pesquisarPorAluno(String nome) {
		Connection conn = new ConexaoMySql().conectar();
		if (conn==null) {
			return null;			
		}
		try {
			PreparedStatement pst = conn.prepareStatement("select * from permissoes_aluno where nome_aluno like ?");
			pst.setString(1, nome + "%");
			ResultSet rs = pst.executeQuery();
			List<Permissao> permissoes = new ArrayList<>();
			while (rs.next()) {
				Permissao permissao = new Permissao();
				Aluno aluno = new Aluno();
				aluno.setIdAluno(rs.getInt("id_aluno"));
				aluno.setNomeAluno(rs.getString("nome_aluno"));
				aluno.setNumeroMatricula(rs.getLong("numero_matricula"));
				permissao.setAluno(aluno);
				permissao.setTipoPermissao(rs.getString("tipo"));
				permissao.setIdPermissao(rs.getInt("id_permissao"));
				permissao.setResponsavel(rs.getString("responsavel"));
				permissao.setDataPermissao(new Date(rs.getDate("dt_permissao").getTime()));
				permissoes.add(permissao);
			}
			return permissoes;
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
	
	public List<Permissao> pesquisarAlunosComPermissaoDia() {
		Connection conn = new ConexaoMySql().conectar();
		if (conn==null) {
			return null;			
		}
		try {
			PreparedStatement pst = conn.prepareStatement("select * from permissoes_aluno_dia");
			ResultSet rs = pst.executeQuery();
			List<Permissao> permissoes = new ArrayList<>();
			while (rs.next()) {
				Permissao permissao = new Permissao();
				Aluno aluno = new Aluno();
				aluno.setIdAluno(rs.getInt("id_aluno"));
				aluno.setNomeAluno(rs.getString("nome_aluno"));
				aluno.setNumeroMatricula(rs.getLong("numero_matricula"));
				permissao.setAluno(aluno);
				permissao.setTipoPermissao(rs.getString("tipo"));
				permissao.setIdPermissao(rs.getInt("id_permissao"));
				permissao.setResponsavel(rs.getString("responsavel"));
				permissao.setDataPermissao(new Date(rs.getDate("dt_permissao").getTime()));
				permissoes.add(permissao);
			}
			return permissoes;
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
	
	public List<Permissao> pesquisarPermissoesAlunoDia(Aluno alunoSelecionado) {
		Connection conn = new ConexaoMySql().conectar();
		if (conn==null) {
			return null;			
		}
		try {
			PreparedStatement pst = conn.prepareStatement("select * from permissoes_aluno_dia where id_aluno = ?");
			pst.setInt(1, alunoSelecionado.getIdAluno());
			ResultSet rs = pst.executeQuery();
			List<Permissao> permissoes = new ArrayList<>();
			while (rs.next()) {
				Permissao permissao = new Permissao();
				Aluno aluno = new Aluno();
				aluno.setIdAluno(rs.getInt("id_aluno"));
				aluno.setNomeAluno(rs.getString("nome_aluno"));
				aluno.setNumeroMatricula(rs.getLong("numero_matricula"));
				permissao.setAluno(aluno);
				permissao.setTipoPermissao(rs.getString("tipo"));
				permissao.setIdPermissao(rs.getInt("id_permissao"));
				permissao.setResponsavel(rs.getString("responsavel"));
				permissao.setDataPermissao(new Date(rs.getDate("dt_permissao").getTime()));
				permissoes.add(permissao);
			}
			return permissoes;
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
		
	public List<Permissao> pesquisarPermissoesTurmaDia(Turma _turma) {
		Connection conn = new ConexaoMySql().conectar();
		if (conn==null) {
			return null;			
		}
		try {
			PreparedStatement pst = conn.prepareStatement("select * from permissoes_turma_dia where id_turma = ?");
			pst.setInt(1, _turma.getIdTurma());
			ResultSet rs = pst.executeQuery();
			List<Permissao> permissoes = new ArrayList<>();
			while (rs.next()) {
				Permissao permissao = new Permissao();
				Turma turma = new Turma();
				Curso curso = new Curso();
				curso.setNomeCurso(rs.getString("nome_curso"));
				turma.setCurso(curso);
				turma.setNomeTurma(rs.getString("nome_turma"));
				try {
					turma.setAno(rs.getInt("ano"));
					turma.setDataFinal(new Date(rs.getDate("dt_final").getTime()));
					turma.setDataInicio(new Date(rs.getDate("dt_inicio").getTime()));
				} catch (Exception e) {
				}
				turma.setIdTurma(rs.getInt("id_turma"));
				permissao.setTurma(turma);
				permissao.setTipoPermissao(rs.getString("tipo"));
				permissao.setIdPermissao(rs.getInt("id_permissao"));
				permissao.setResponsavel(rs.getString("responsavel"));
				permissao.setDataPermissao(new Date(rs.getDate("dt_permissao").getTime()));
				permissoes.add(permissao);
			}
			return permissoes;
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
	
	public List<Permissao> pesquisarTurmasComPermissaoDia() {
		Connection conn = new ConexaoMySql().conectar();
		if (conn==null) {
			return null;			
		}
		try {
			PreparedStatement pst = conn.prepareStatement("select * from permissoes_turma_dia");
			ResultSet rs = pst.executeQuery();
			List<Permissao> permissoes = new ArrayList<>();
			while (rs.next()) {
				Permissao permissao = new Permissao();
				Turma turma = new Turma();
				Curso curso = new Curso();
				curso.setNomeCurso(rs.getString("nome_curso"));
				turma.setCurso(curso);
				turma.setNomeTurma(rs.getString("nome_turma"));
				try {
					turma.setAno(rs.getInt("ano"));
					turma.setDataFinal(new Date(rs.getDate("dt_final").getTime()));
					turma.setDataInicio(new Date(rs.getDate("dt_inicio").getTime()));
				} catch (Exception e) {
				}
				turma.setIdTurma(rs.getInt("id_turma"));
				permissao.setTurma(turma);
				permissao.setTipoPermissao(rs.getString("tipo"));
				permissao.setIdPermissao(rs.getInt("id_permissao"));
				permissao.setResponsavel(rs.getString("responsavel"));
				permissao.setDataPermissao(new Date(rs.getDate("dt_permissao").getTime()));
				permissoes.add(permissao);
			}
			return permissoes;
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
	
	public List<Permissao> pesquisarPorTurma(String nome) {
		Connection conn = new ConexaoMySql().conectar();
		if (conn==null) {
			return null;			
		}
		try {
			PreparedStatement pst = conn.prepareStatement("select * from permissoes_turma where nome_turma like ?");
			pst.setString(1, nome + "%");
			ResultSet rs = pst.executeQuery();
			List<Permissao> permissoes = new ArrayList<>();
			while (rs.next()) {
				Permissao permissao = new Permissao();
				Turma turma = new Turma();
				Curso curso = new Curso();
				curso.setIdCurso(rs.getInt("id_curso"));
				curso.setNomeCurso(rs.getString("nome_curso"));
				turma.setCurso(curso);
				try {
					turma.setAno(rs.getInt("ANO"));
					turma.setDataFinal(new Date(rs.getDate("dt_final").getTime()));
					turma.setDataInicio(new Date(rs.getDate("dt_inicio").getTime()));
				} catch (Exception e) {
				}
				turma.setIdTurma(rs.getInt("id_turma"));
				turma.setNomeTurma(rs.getString("nome_turma"));
				permissao.setTurma(turma);
				permissao.setTipoPermissao(rs.getString("tipo"));
				permissao.setIdPermissao(rs.getInt("id_permissao"));
				permissao.setResponsavel(rs.getString("responsavel"));
				permissao.setDataPermissao(new Date(rs.getDate("dt_permissao").getTime()));
				permissoes.add(permissao);
			}
			return permissoes;
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
	
	public List<Permissao> pesquisarPorVisitante(String nome) {
		Connection conn = new ConexaoMySql().conectar();
		if (conn==null) {
			return null;			
		}
		try {
			PreparedStatement pst = conn.prepareStatement("select * from permissoes_visitante where nome_visitante like ?");
			pst.setString(1, nome + "%");
			ResultSet rs = pst.executeQuery();
			List<Permissao> permissoes = new ArrayList<>();
			while (rs.next()) {
				Permissao permissao = new Permissao();
				Visitante visitante = new Visitante();
				visitante.setIdVisitante(rs.getInt("id_visitante"));
				visitante.setMotivoVisita(rs.getString("motivo"));
				visitante.setNomeVisitante(rs.getString("nome_visitante"));
				permissao.setVisitante(visitante);
				permissao.setTipoPermissao(rs.getString("tipo"));
				permissao.setIdPermissao(rs.getInt("id_permissao"));
				permissao.setResponsavel(rs.getString("responsavel"));
				permissao.setDataPermissao(new Date(rs.getDate("dt_permissao").getTime()));
				permissoes.add(permissao);
			}
			return permissoes;
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
