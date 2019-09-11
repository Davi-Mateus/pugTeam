package model;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DiaAula {
	//Variaveis
	public int idDiaAula;
	public int idTurma;
	public Date dia;
	public Horario horario;
	private SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
	
	//Getters and Setters
	public int getIdDiaAula() {
		return idDiaAula;
	}
	public void setIdDiaAula(int idDiaAula) {
		this.idDiaAula = idDiaAula;
	}
	public int getIdTurma() {
		return idTurma;
	}
	public void setIdTurma(int idTurma) {
		this.idTurma = idTurma;
	}
	public Date getDia() {
		return dia;
	}
	public void setDia(Date dia) {
		this.dia = dia;
	}
	public Horario getHorario() {
		return horario;
	}
	public void setHorario(Horario horario) {
		this.horario = horario;
	}
	public String toString() {
		return sdf.format(dia) + " - " + horario.toString();
	}
}
