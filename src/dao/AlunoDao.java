package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.JOptionPane;

import model.Aluno;
import model.Registro;
import model.Turma;
import util.ConexaoMySql;

public class AlunoDao {
	
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	
	public boolean inserir(Aluno aluno) {
		
		Connection conn = new ConexaoMySql().conectar();
		if (conn==null) {
			return false;			
		}
		try {
			PreparedStatement pst = conn.prepareStatement("insert into aluno(nome_aluno, numero_matricula) values(?, ?)");
			pst.setString(1, aluno.getNomeAluno());
			pst.setLong(2, aluno.getNumeroMatricula());
			pst.execute();
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
	
	public boolean alterar(Aluno aluno) {
		
		Connection conn = new ConexaoMySql().conectar();
		if (conn==null) {
			return false;			
		}
		try {
			PreparedStatement pst = conn.prepareStatement("update aluno set nome_aluno = ?, numero_matricula = ? where id_aluno = ?");
			pst.setString(1, aluno.getNomeAluno());
			pst.setLong(2, aluno.getNumeroMatricula());
			pst.setLong(3, aluno.getIdAluno());
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
		
	public List<Aluno> pesquisarPorNome(String nome) {
		Connection conn = new ConexaoMySql().conectar();
		if (conn==null) {
			return null;			
		}
		try {
			PreparedStatement pst = conn.prepareStatement("select * from aluno where nome_aluno like ?");
			pst.setString(1, nome + "%");
			ResultSet rs = pst.executeQuery();
			List<Aluno> alunos = new ArrayList<>();
			while (rs.next()) {
				Aluno aluno = new Aluno();
				aluno.setIdAluno(rs.getInt("id_aluno"));
				aluno.setNomeAluno(rs.getString("nome_aluno"));
				aluno.setNumeroMatricula(rs.getLong("numero_matricula"));
				alunos.add(aluno);	
			}
			return alunos;
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
	
	public List<Aluno> pesquisarAlunoNaoPresente(Date dia) {
		Connection conn = new ConexaoMySql().conectar();
		if (conn==null) {
			return null;			
		}
		try {
			PreparedStatement pst = conn.prepareStatement("select * from atrasados where dia = ?");
			pst.setString(1, sdf.format(dia));	
			ResultSet rs = pst.executeQuery();
			List<Aluno> alunos = new ArrayList<>();
			while (rs.next()) {
				Aluno aluno = new Aluno();
				aluno.setIdAluno(rs.getInt("id_aluno"));
				aluno.setNomeAluno(rs.getString("nome_aluno"));
				aluno.setNumeroMatricula(rs.getLong("numero_matricula"));
				Turma turma = new Turma();
				turma.setNomeTurma(rs.getString("nome_turma"));
				ArrayList<Turma> turmas = new ArrayList<>();
				turmas.add(turma);
				aluno.setTurmas(turmas);
				alunos.add(aluno);
			}
			return alunos;
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
	
	public Aluno pesquisarPorNumeroMatricula(Long numeroMatricula) {
		Connection conn = new ConexaoMySql().conectar();
		if (conn==null) {
			return null;			
		}
		try {
			PreparedStatement pst = conn.prepareStatement("select * from aluno where numero_matricula = ?");
			pst.setString(1, numeroMatricula + "%");
			ResultSet rs = pst.executeQuery();
			Aluno aluno = new Aluno();
			while (rs.next()) {
				aluno = new Aluno();
				aluno.setIdAluno(rs.getInt("id_aluno"));
				aluno.setNomeAluno(rs.getString("nome_aluno"));
				aluno.setNumeroMatricula(rs.getLong("numero_matricula"));	
			}
			return aluno;
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
	
	public Aluno pesquisarFrequenciaPorAluno(int idAluno, Date dtInicio, Date dtFinal) {
		Connection conn = new ConexaoMySql().conectar();
		if (conn==null) {
			return null;			
		}
		try {
			PreparedStatement pstRegistro = conn.prepareStatement("select * from frequencia where id_aluno = ? and dia between ? and ?");
			pstRegistro.setInt(1, idAluno);
			pstRegistro.setString(2, sdf.format(dtInicio));
			pstRegistro.setString(3, sdf.format(dtFinal));
			ResultSet rsRegistro = pstRegistro.executeQuery();
			ArrayList<Registro> registros = new ArrayList<>();
			while (rsRegistro.next()) {
				Registro registro = new Registro();
				try {
					registro.setIdRegistro(rsRegistro.getInt("id_registro"));
				} catch (Exception e1) {
					registro.setIdRegistro(0);
				}
				registro.setIdAluno(rsRegistro.getInt("id_aluno"));
				registro.setIdTurma(rsRegistro.getInt("id_turma"));
				try {
					registro.setHoraEntrada(new Date(rsRegistro.getDate("dt_hr_entrada").getTime()));
				} catch (Exception e) {
					registro.setHoraEntrada(null);
				}
				try {
					registro.setHoraSaida(new Date(rsRegistro.getDate("dt_hr_saida").getTime()));
				} catch (Exception e) {
					registro.setHoraSaida(null);
				}				
				registros.add(registro);
			}
			PreparedStatement pstAluno = conn.prepareStatement("select * from aluno where id_aluno = ?");
			pstAluno.setInt(1, idAluno);
			ResultSet rsAluno= pstAluno.executeQuery();
			Aluno aluno = new Aluno();
			while (rsAluno.next()) {
				aluno.setIdAluno(rsAluno.getInt("id_aluno"));
				aluno.setNomeAluno(rsAluno.getString("nome_aluno"));
				aluno.setNumeroMatricula(rsAluno.getLong("numero_matricula"));
				aluno.setRegistros(registros);
			}
			return aluno;
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
