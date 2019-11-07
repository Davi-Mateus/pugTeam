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
import model.Registro;
import util.ConexaoMySql;

public class RegistroDao {
	
	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
	
	public boolean entrar(Registro registro) {
		Connection conn = new ConexaoMySql().conectar();
		if (conn==null) {
			return false;			
		}
		try {
			PreparedStatement pst = conn.prepareStatement("insert into registro(id_aluno, id_turma, id_horario, dt_hr_entrada) values(?, ?, ?, ?)");
			pst.setInt(1, registro.getIdAluno());
			pst.setInt(2, registro.getIdTurma());
			pst.setInt(3, registro.getHorario().getIdHorario());
			pst.setString(4, sdf.format(registro.getHoraEntrada()));
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
	
	public boolean sair(Registro registro) {
		Connection conn = new ConexaoMySql().conectar();
		if (conn==null) {
			return false;			
		}
		try {
			PreparedStatement pst = conn.prepareStatement("update registro set dt_hr_saida = ? where id_registro = ? ");
			pst.setString(1, sdf.format(registro.getHoraSaida()));
			pst.setInt(2, registro.getIdRegistro());
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
	
	public List<Registro> pesquisarPorAluno(Aluno aluno) {
		Connection conn = new ConexaoMySql().conectar();
		if (conn==null) {
			return null;			
		}
		try {
			PreparedStatement pst = conn.prepareStatement("select * from registro_com_horario where id_aluno = ?");
			pst.setInt(1, aluno.getIdAluno());
			ResultSet rs = pst.executeQuery();
			List<Registro> registros = new ArrayList<>();
			while (rs.next()) {
				Horario horario = new Horario();
				horario.setIdHorario(rs.getInt("id_horario"));;
				horario.setDiaSemana(rs.getString("dia_semana"));
				horario.setDiaSemanaInt(rs.getInt("dia_semana_int"));
				horario.setHorarioInicioAula(new Date(rs.getDate("hr_inicio_aula").getTime()));
				horario.setHorarioFinalAula(new Date(rs.getDate("hr_final_aula").getTime()));
				horario.setHorarioInicioAulaLocaTime(LocalTime.parse(rs.getString("hr_inicio_aula")));
				horario.setHorarioFinalAulaLocaTime(LocalTime.parse(rs.getString("hr_final_aula")));
				Registro registro = new Registro();
				registro.setIdRegistro(rs.getInt("id_registro"));
				registro.setIdAluno(rs.getInt("id_aluno"));
				registro.setIdTurma(rs.getInt("id_turma"));
				registro.setHoraEntrada(new Date(rs.getDate("dt_hr_entrada").getTime()));
				registro.setHoraSaida(new Date(rs.getDate("dt_hr_saida").getTime()));	
				registro.setHorario(horario);
				registros.add(registro);
			}
			return registros;
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
	
	public Registro pesquisarPorAlunoPresenteNoColegio(Aluno aluno) {
		Connection conn = new ConexaoMySql().conectar();
		if (conn==null) {
			return null;			
		}
		try {
			PreparedStatement pst = conn.prepareStatement("select * from registro_aluno_presente_dia where id_aluno = ?");
			pst.setInt(1, aluno.getIdAluno());
			ResultSet rs = pst.executeQuery();
			Registro registro = new Registro();
			while (rs.next()) {
				Horario horario = new Horario();
				horario.setIdHorario(rs.getInt("id_horario"));;
				horario.setDiaSemana(rs.getString("dia_semana"));
				horario.setDiaSemanaInt(rs.getInt("dia_semana_int"));
				horario.setHorarioInicioAula(new Date(rs.getDate("hr_inicio_aula").getTime()));
				horario.setHorarioFinalAula(new Date(rs.getDate("hr_final_aula").getTime()));
				horario.setHorarioInicioAulaLocaTime(LocalTime.parse(rs.getString("hr_inicio_aula")));
				horario.setHorarioFinalAulaLocaTime(LocalTime.parse(rs.getString("hr_final_aula")));
				registro.setIdRegistro(rs.getInt("id_registro"));
				registro.setIdAluno(rs.getInt("id_aluno"));
				registro.setIdTurma(rs.getInt("id_turma"));
				registro.setHoraEntrada(new Date(rs.getDate("dt_hr_entrada").getTime()));
				try {
					registro.setHoraSaida(new Date(rs.getDate("dt_hr_saida").getTime()));
				} catch (Exception e) {
				}	
				registro.setHorario(horario);
			}
			return registro;
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