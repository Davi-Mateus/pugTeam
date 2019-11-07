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
import model.Matricula;
import model.Turma;
import util.ConexaoMySql;

public class MatriculaDao {
	
	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	
	public boolean inserir(Matricula matricula) {
		Connection conn = new ConexaoMySql().conectar();
		if (conn==null) {
			return false;			
		}
		try {
			PreparedStatement pst = conn.prepareStatement("insert into matricula(id_aluno, id_turma, dt_matricula) values(?, ?, ?)");
			pst.setObject(1, matricula.getAluno().getIdAluno());
			pst.setObject(2, matricula.getTurma().getIdTurma());
			pst.setString(3, sdf.format(new Date()));
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
	
	public boolean cancelar(Matricula matricula) {
		Connection conn = new ConexaoMySql().conectar();
		if (conn==null) {
			return false;			
		}
		try {
			PreparedStatement pst = conn.prepareStatement("update matricula set dt_cancelamento_matricula = ? where id_aluno = ? and id_turma = ?");
			pst.setString(1, sdf.format(new Date()));
			pst.setInt(2, matricula.getAluno().getIdAluno());
			pst.setInt(3, matricula.getTurma().getIdTurma());
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
	
	public boolean alterar(Matricula matricula) {
		Connection conn = new ConexaoMySql().conectar();
		if (conn==null) {
			return false;			
		}
		try {
			PreparedStatement pst = conn.prepareStatement("update matricula set id_aluno = ?, id_turma = ? where id_matricula = ?");
			pst.setObject(1, matricula.getAluno().getIdAluno());
			pst.setObject(2, matricula.getTurma().getIdTurma());
			pst.setInt(3, matricula.getIdMatricula());
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
		
	public List<Matricula> pesquisarPorNome(String nome) {
		Connection conn = new ConexaoMySql().conectar();
		if (conn==null) {
			return null;			
		}
		try {
			PreparedStatement pst = conn.prepareStatement("select * from matriculas_por_aluno where nome_aluno like ?");
			pst.setString(1, nome + "%");
			ResultSet rs = pst.executeQuery();
			List<Matricula> matriculas = new ArrayList<>();
			while (rs.next()) {
				Matricula matricula = new Matricula();
				Aluno aluno = new Aluno();
				Turma turma = new Turma();
				turma.setAno(rs.getInt("ano"));
				turma.setIdTurma(rs.getInt("id_turma"));
				turma.setNomeTurma(rs.getString("nome_turma"));
				turma.setDataInicio(new Date(rs.getDate("dt_inicio").getTime()));
				turma.setDataFinal(new Date(rs.getDate("dt_final").getTime()));
				aluno.setIdAluno(rs.getInt("id_aluno"));
				aluno.setNomeAluno(rs.getString("nome_aluno"));
				aluno.setNumeroMatricula(rs.getLong("numero_matricula"));
				matricula.setAluno(aluno);
				matricula.setTurma(turma);
				matricula.setIdMatricula(rs.getInt("id_matricula"));
				matricula.setDataMatricula(new Date((rs.getDate("dt_matricula").getTime())));
				matricula.setDataCancelamentoMatricula(new Date((rs.getDate("dt_cancelamento_matricula").getTime())));
				matriculas.add(matricula);
			}
			return matriculas;
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
		
	public boolean verificarMatriculaExistente(Matricula matriculaTestada) {
		Connection conn = new ConexaoMySql().conectar();
		if (conn==null) {
			return false;			
		}
		try {
			PreparedStatement pst = conn.prepareStatement("select * from matricula where id_aluno = ? and id_turma = ? and dt_cancelamento_matricula is null");
			pst.setInt(1, matriculaTestada.getAluno().getIdAluno());
			pst.setInt(2, matriculaTestada.getTurma().getIdTurma());
			ResultSet rs = pst.executeQuery();
			List<Matricula> matriculas = new ArrayList<>();
			while (rs.next()) {
				Matricula matriculaExistente = new Matricula();
				
				matriculaExistente.setIdMatricula(rs.getInt("id_matricula"));
				matriculas.add(matriculaExistente);
			}
			if (matriculas.size() > 0) {
				return true;
			} else {
				return false;
			}
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
};