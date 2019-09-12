package model;

import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;

public class Horario {
	//Variaveis
	private int idHorario;
	private int diaSemanaInt;
	private String diaSemana;
	private Date horarioInicioAula;
	private Date horarioFinalAula;
	private LocalTime horarioInicioAulaLocaTime;
	private LocalTime horarioFinalAulaLocaTime;
	private ArrayList<Turma> turmas;
	private SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
	
	//Getter and Setters
	public int getIdHorario() {
		return idHorario;
	}
	public void setIdHorario(int idQuadroHorarios) {
		this.idHorario = idQuadroHorarios;
	}
	public String getDiaSemana() {
		return diaSemana;
	}
	public void setDiaSemana(String diaSemana) {
		this.diaSemana = diaSemana;
	}
	public Date getHorarioInicioAula() {
		return horarioInicioAula;
	}
	public void setHorarioInicioAula(Date horarioInicioAula) {
		this.horarioInicioAula = horarioInicioAula;
	}
	public Date getHorarioFinalAula() {
		return horarioFinalAula;
	}
	public void setHorarioFinalAula(Date horarioFinalAula) {
		this.horarioFinalAula = horarioFinalAula;
	}
	public ArrayList<Turma> getTurmas() {
		return turmas;
	}
	public void setTurmas(ArrayList<Turma> turmas) {
		this.turmas = turmas;
	}
	public int getDiaSemanaInt() {
		return diaSemanaInt;
	}
	public void setDiaSemanaInt(int diaSemanaInt) {
		this.diaSemanaInt = diaSemanaInt;
	}
	public LocalTime getHorarioInicioAulaLocaTime() {
		return horarioInicioAulaLocaTime;
	}
	public void setHorarioInicioAulaLocaTime(LocalTime horarioInicioAulaSql) {
		this.horarioInicioAulaLocaTime = horarioInicioAulaSql;
	}
	public LocalTime getHorarioFinalAulaLocaTime() {
		return horarioFinalAulaLocaTime;
	}
	public void setHorarioFinalAulaLocaTime(LocalTime horarioFinalAulaSql) {
		this.horarioFinalAulaLocaTime = horarioFinalAulaSql;
	}
	
	// Methods
	public String toString() {
		return diaSemana + " - " + sdf.format(horarioInicioAula) + " até às " + sdf.format(horarioFinalAula);
	}
}