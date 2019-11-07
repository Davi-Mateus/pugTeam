package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import model.DiaAula;
import model.Horario;
import util.ConexaoMySql;

public class DiaAulaDao {
	
	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	
	public boolean inserir(DiaAula diaAula) {
		Connection conn = new ConexaoMySql().conectar();
		if (conn==null) {
			return false;			
		}
		try {
			PreparedStatement pst = conn.prepareStatement("insert into dia_aula(dia, id_turma, id_horario) values(?, ?, ?)");
			pst.setString(1, sdf.format(diaAula.getDia()));
			pst.setInt(2, diaAula.getIdTurma());
			pst.setInt(3, diaAula.getHorario().getIdHorario());
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
	
	public boolean alterar(DiaAula diaAula) {
		Connection conn = new ConexaoMySql().conectar();
		if (conn==null) {
			return false;			
		}
		try {
			PreparedStatement pst = conn.prepareStatement("update dia_aula set dia = ?, id_turma = ?, id_dia_aula = ? where id_dia_aula = ?");
			pst.setString(1, sdf.format(diaAula.getDia()));
			pst.setInt(2, diaAula.getIdTurma());
			pst.setInt(3, diaAula.getHorario().getIdHorario());
			pst.setInt(4, diaAula.getIdDiaAula());
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
	
	public boolean excluirPorTurma(int idTurma) {
		Connection conn = new ConexaoMySql().conectar();
		if (conn==null) {
			return false;			
		}
		try {
			PreparedStatement pst = conn.prepareStatement("delete from dia_aula where id_turma = ?");
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
	
	public boolean excluirPorDiaAula(DiaAula diaAula) {
		Connection conn = new ConexaoMySql().conectar();
		if (conn==null) {
			return false;			
		}
		try {
			PreparedStatement pst = conn.prepareStatement("delete from dia_aula where id_dia_aula = ?");
			pst.setInt(1, diaAula.idDiaAula);
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
	
	public boolean excluirPorData(Date data) {
		Connection conn = new ConexaoMySql().conectar();
		if (conn==null) {
			return false;			
		}
		try {
			PreparedStatement pst = conn.prepareStatement("delete from dia_aula where dia = ?");
			pst.setString(1, sdf.format(data));
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
	
	public List<DiaAula> pesquisarDiaAula(int idTurma, Date dia) {
		Connection conn = new ConexaoMySql().conectar();
		if (conn==null) {
			return null;			
		}
		try {
			PreparedStatement pst = conn.prepareStatement("select * from dias_curso where id_turma = ? and dia = ?");
			pst.setInt(1, idTurma);
			pst.setString(2, sdf.format(dia));
			ResultSet rs = pst.executeQuery();
			List<DiaAula> diasAula = new ArrayList<>();
			while (rs.next()) {
				Horario horario = new Horario();
				horario.setDiaSemana(rs.getString("dia_semana"));
				horario.setDiaSemanaInt(rs.getInt("dia_semana_int"));
				horario.setHorarioFinalAula(new Date(rs.getTime("hr_final_aula").getTime()));
				horario.setHorarioInicioAula(new Date(rs.getTime("hr_inicio_aula").getTime()));
				horario.setIdHorario(rs.getInt("id_horario"));
				DiaAula diaAula = new DiaAula();
				diaAula.setIdDiaAula(rs.getInt("id_dia_aula"));
				diaAula.setIdTurma(rs.getInt("id_turma"));
				diaAula.setDia(new Date(rs.getDate("dia").getTime()));
				diaAula.setHorario(horario);
				diasAula.add(diaAula);
			}
			return diasAula;
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