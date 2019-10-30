package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import model.Aluno;
import model.Horario;
import model.Turma;
import util.ConexaoMySql;

public class HorarioDao {
	
	private SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
	
	public boolean inserir(Horario qh) {
		Connection conn = new ConexaoMySql().conectar();
		if (conn==null) {
			return false;			
		}
		try {
			PreparedStatement pst = conn.prepareStatement("insert into horario(dia_semana, hr_inicio_aula, hr_final_aula, dia_semana_int) values(?, ?, ?, ?)");
			pst.setString(1, qh.getDiaSemana());
			pst.setString(2, sdf.format(qh.getHorarioInicioAula()));
			pst.setString(3, sdf.format(qh.getHorarioFinalAula()));
			pst.setInt(4, qh.getDiaSemanaInt());
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
	
	public boolean alterar(Horario qh) {
		Connection conn = new ConexaoMySql().conectar();
		if (conn==null) {
			return false;			
		}
		try {
			PreparedStatement pst = conn.prepareStatement("update horario set dia_semana = ?, hr_inicio_aula = ?, hr_final_aula = ?, dia_semana_int = ? where id_horario = ?");
			pst.setString(1, qh.getDiaSemana());
			pst.setString(2, sdf.format(qh.getHorarioInicioAula()));
			pst.setString(3, sdf.format(qh.getHorarioFinalAula()));
			pst.setInt(4, qh.getDiaSemanaInt());
			pst.setInt(5, qh.getIdHorario());
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
	
	public boolean excluir(Horario qh) {
		Connection conn = new ConexaoMySql().conectar();
		if (conn==null) {
			return false;			
		}
		try {
			PreparedStatement pst = conn.prepareStatement("delete from horario where id_horario = ?");
			pst.setInt(1, qh.getIdHorario());
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
	
	public List<Horario> pesquisarPorNome(String nome) {
		Connection conn = new ConexaoMySql().conectar();
		if (conn==null) {
			return null;			
		}
		try {
			PreparedStatement pst = conn.prepareStatement("select * from horario where dia_semana like ?");
			pst.setString(1, nome + "%");
			ResultSet rs = pst.executeQuery();
			List<Horario> qhs = new ArrayList<>();
			while (rs.next()) {
				Horario qh = new Horario();
				qh.setIdHorario(rs.getInt("id_horario"));
				qh.setDiaSemana(rs.getString("dia_semana"));
				qh.setDiaSemanaInt(rs.getInt("dia_semana_int"));
				qh.setHorarioInicioAula(new Date(rs.getTime("hr_inicio_aula").getTime()));
				qh.setHorarioFinalAula(new Date(rs.getTime("hr_final_aula").getTime()));
				qh.setHorarioInicioAulaLocaTime(LocalTime.parse(rs.getString("hr_inicio_aula")));
				qh.setHorarioFinalAulaLocaTime(LocalTime.parse(rs.getString("hr_final_aula")));
				qhs.add(qh);
			}
			return qhs;
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
	
	public Horario pesquisarIdHorario(int idHorario) {
		Connection conn = new ConexaoMySql().conectar();
		if (conn==null) {
			return null;			
		}
		try {
			PreparedStatement pst = conn.prepareStatement("select * from horario where id_horario = ?");
			pst.setInt(1, idHorario);
			ResultSet rs = pst.executeQuery();
			Horario qh = new Horario();
			while (rs.next()) {
				qh.setIdHorario(rs.getInt("id_horario"));
				qh.setDiaSemana(rs.getString("dia_semana"));
				qh.setDiaSemanaInt(rs.getInt("dia_semana_int"));
				qh.setHorarioInicioAula(new Date(rs.getTime("hr_inicio_aula").getTime()));
				qh.setHorarioFinalAula(new Date(rs.getTime("hr_final_aula").getTime()));
				qh.setHorarioInicioAulaLocaTime(LocalTime.parse(rs.getString("hr_inicio_aula")));
				qh.setHorarioFinalAulaLocaTime(LocalTime.parse(rs.getString("hr_final_aula")));
			}
			return qh;
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
	
	public List<Horario> pesquisarPorHorarioDeletavel(String nome) {
		Connection conn = new ConexaoMySql().conectar();
		if (conn==null) {
			return null;			
		}
		try {
			PreparedStatement pst = conn.prepareStatement("select * from horario where dia_semana like ?");
			pst.setString(1, nome + "%");
			ResultSet rs = pst.executeQuery();
			List<Horario> qhs = new ArrayList<>();
			while (rs.next()) {
				Horario qh = new Horario();
				qh.setIdHorario(rs.getInt("id_horario"));
				qh.setDiaSemana(rs.getString("dia_semana"));
				qh.setDiaSemanaInt(rs.getInt("dia_semana_int"));
				qh.setHorarioInicioAula(new Date(rs.getTime("hr_inicio_aula").getTime()));
				qh.setHorarioFinalAula(new Date(rs.getTime("hr_final_aula").getTime()));
				qh.setHorarioInicioAulaLocaTime(LocalTime.parse(rs.getString("hr_inicio_aula")));
				qh.setHorarioFinalAulaLocaTime(LocalTime.parse(rs.getString("hr_final_aula")));
				qhs.add(qh);
			}
			return qhs;
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
	
	public List<Horario> pesquisarPorHorariosDoDia() {
		Connection conn = new ConexaoMySql().conectar();
		if (conn==null) {
			return null;			
		}
		try {
			PreparedStatement pst1 = conn.prepareStatement("select * from aulas_dia");
			PreparedStatement pst2 = conn.prepareStatement("select * from aulas_dia");
			ResultSet rs1 = pst1.executeQuery();
			ResultSet rs2 = pst2.executeQuery();
			List<Horario> horarios = new ArrayList<>();
			ArrayList<Turma> turmas = new ArrayList<>();
			while (rs1.next()) {
				Turma turma = new Turma();
				turma.setIdTurma(rs1.getInt("id_turma"));
				turma.setNomeTurma(rs1.getString("nome_turma"));
				turmas.add(turma);
			}
			while (rs2.next()) {
				
				Horario horario = new Horario();
				horario.setIdHorario(rs2.getInt("id_horario"));
				horario.setDiaSemana(rs2.getString("dia_semana"));
				horario.setDiaSemanaInt(rs2.getInt("dia_semana_int"));
				horario.setHorarioInicioAula(new Date(rs2.getTime("hr_inicio_aula").getTime()));
				horario.setHorarioFinalAula(new Date(rs2.getTime("hr_final_aula").getTime()));
				horario.setHorarioInicioAulaLocaTime(LocalTime.parse(rs2.getString("hr_inicio_aula")));
				horario.setHorarioFinalAulaLocaTime(LocalTime.parse(rs2.getString("hr_final_aula")));
				horario.setTurmas(turmas);
				horarios.add(horario);
			}
			return horarios;
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
	
	public List<Horario> pesquisarHorariosPorTurma(Turma turmaSelecionada) {
		Connection conn = new ConexaoMySql().conectar();
		if (conn==null) {
			return null;			
		}
		try {
			PreparedStatement pst = conn.prepareStatement("select * from horarios_turma where nome_turma like ?");
			pst.setString(1, turmaSelecionada.getNomeTurma());
			ResultSet rs = pst.executeQuery();
			List<Horario> horarios = new ArrayList<>();
			while (rs.next()) {
				Horario horario = new Horario();
				horario.setDiaSemana(rs.getString("dia_semana"));
				horario.setIdHorario(rs.getInt("id_horario"));
				horario.setDiaSemanaInt(rs.getInt("dia_semana_int"));
				horario.setHorarioInicioAula(new Date(rs.getTime("hr_inicio_aula").getTime()));
				horario.setHorarioFinalAula(new Date(rs.getTime("hr_final_aula").getTime()));
				horario.setHorarioInicioAulaLocaTime(LocalTime.parse(rs.getString("hr_inicio_aula")));
				horario.setHorarioFinalAulaLocaTime(LocalTime.parse(rs.getString("hr_final_aula")));
				horarios.add(horario);	
			}
			return horarios;
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
	
	public List<Horario> pesquisarHorariosPorAluno(Aluno aluno) {
		Connection conn = new ConexaoMySql().conectar();
		if (conn==null) {
			return null;			
		}
		try {
			PreparedStatement pst1 = conn.prepareStatement("select * from horarios_aluno where id_aluno = ?");
			pst1.setInt(1, aluno.getIdAluno());
			ResultSet rs1 = pst1.executeQuery();
			PreparedStatement pst2 = conn.prepareStatement("select * from horarios_aluno where id_aluno = ?");
			pst2.setInt(1, aluno.getIdAluno());
			ResultSet rs2 = pst2.executeQuery();
			
			ArrayList<Turma> turmas = new ArrayList<>();
			while (rs1.next()) {
				Turma turma = new Turma();
				turma.setAno(rs1.getInt("ano"));
				turma.setIdTurma(rs1.getInt("id_turma"));
				turma.setNomeTurma(rs1.getString("nome_turma"));
				turmas.add(turma);
			}
			
			List<Horario> horarios = new ArrayList<>();
			while (rs2.next()) {
				Horario horario = new Horario();
				horario.setDiaSemana(rs2.getString("dia_semana"));
				horario.setIdHorario(rs2.getInt("id_horario"));
				horario.setDiaSemanaInt(rs2.getInt("dia_semana_int"));
				horario.setHorarioInicioAula(new Date(rs2.getTime("hr_inicio_aula").getTime()));
				horario.setHorarioFinalAula(new Date(rs2.getTime("hr_final_aula").getTime()));
				horario.setHorarioInicioAulaLocaTime(LocalTime.parse(rs2.getString("hr_inicio_aula")));
				horario.setHorarioFinalAulaLocaTime(LocalTime.parse(rs2.getString("hr_final_aula")));
				horario.setTurmas(turmas);
				horarios.add(horario);
			}
			return horarios;
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
	
	public List<Horario> pesquisarHorariosPorAlunoDia(Aluno aluno) {
		Connection conn = new ConexaoMySql().conectar();
		if (conn==null) {
			return null;			
		}
		try {
			PreparedStatement pst1 = conn.prepareStatement("select * from horarios_aluno_dia where id_aluno = ?");
			pst1.setInt(1, aluno.getIdAluno());
			ResultSet rs1 = pst1.executeQuery();
			PreparedStatement pst2 = conn.prepareStatement("select * from horarios_aluno_dia where id_aluno = ?");
			pst2.setInt(1, aluno.getIdAluno());
			ResultSet rs2 = pst2.executeQuery();
			
			ArrayList<Turma> turmas = new ArrayList<>();
			while (rs1.next()) {
				Turma turma = new Turma();
				turma.setAno(rs1.getInt("ano"));
				turma.setIdTurma(rs1.getInt("id_turma"));
				turma.setNomeTurma(rs1.getString("nome_turma"));
				turmas.add(turma);
			}
			
			List<Horario> horarios = new ArrayList<>();
			while (rs2.next()) {
				Horario horario = new Horario();
				horario.setDiaSemana(rs2.getString("dia_semana"));
				horario.setIdHorario(rs2.getInt("id_horario"));
				horario.setDiaSemanaInt(rs2.getInt("dia_semana_int"));
				horario.setHorarioInicioAula(new Date(rs2.getTime("hr_inicio_aula").getTime()));
				horario.setHorarioFinalAula(new Date(rs2.getTime("hr_final_aula").getTime()));
				horario.setHorarioInicioAulaLocaTime(LocalTime.parse(rs2.getString("hr_inicio_aula")));
				horario.setHorarioFinalAulaLocaTime(LocalTime.parse(rs2.getString("hr_final_aula")));
				horario.setTurmas(turmas);
				horarios.add(horario);
			}
			return horarios;
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
};