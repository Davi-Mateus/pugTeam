package model;

import java.util.ArrayList;

public class Curso {
	//Variaveis
	private int idCurso;
	private String nomeCurso;
	private ArrayList<Turma> turmas;
	
	//Getter and setters
	public int getIdCurso() {
		return idCurso;
	}
	public void setIdCurso(int idCurso) {
		this.idCurso = idCurso;
	}
	public String getNomeCurso() {
		return nomeCurso;
	}
	public void setNomeCurso(String nomeCurso) {
		this.nomeCurso = nomeCurso;
	}
	public ArrayList<Turma> getTurmas() {
		return turmas;
	}
	public void setTurmas(ArrayList<Turma> turmas) {
		this.turmas = turmas;
	}
	public String toString() {
		return nomeCurso;
	}
}