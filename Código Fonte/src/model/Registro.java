package model;

import java.time.LocalTime;
import java.util.Date;

public class Registro {
	//Variaveis
	private int idRegistro;
	private int idAluno;
	private int idTurma;
	private Horario horario;
	private Date horaEntrada;
	private Date horaSaida;
	private LocalTime horaEntradaLocalTime;
	private LocalTime horaSaidaLocalTime;
	
	//Getters and Setters
	public int getIdRegistro() {
		return idRegistro;
	}
	public void setIdRegistro(int idRegistro) {
		this.idRegistro = idRegistro;
	}
	public int getIdAluno() {
		return idAluno;
	}
	public void setIdAluno(int idAluno) {
		this.idAluno = idAluno;
	}
	public int getIdTurma() {
		return idTurma;
	}
	public void setIdTurma(int idTurma) {
		this.idTurma = idTurma;
	}
	public Date getHoraEntrada() {
		return horaEntrada;
	}
	public void setHoraEntrada(Date horaEntrada) {
		this.horaEntrada = horaEntrada;
	}
	public Date getHoraSaida() {
		return horaSaida;
	}
	public void setHoraSaida(Date horaSaida) {
		this.horaSaida = horaSaida;
	}
	public Horario getHorario() {
		return horario;
	}
	public void setHorario(Horario horario) {
		this.horario = horario;
	}
	public LocalTime getHoraEntradaLocalTime() {
		return horaEntradaLocalTime;
	}
	public void setHoraEntradaLocalTime(LocalTime horaEntradaLocalTime) {
		this.horaEntradaLocalTime = horaEntradaLocalTime;
	}
	public LocalTime getHoraSaidaLocalTime() {
		return horaSaidaLocalTime;
	}
	public void setHoraSaidaLocalTime(LocalTime horaSaidaLocalTime) {
		this.horaSaidaLocalTime = horaSaidaLocalTime;
	}
}