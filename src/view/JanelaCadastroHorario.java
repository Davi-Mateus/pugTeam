package view;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.text.MaskFormatter;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.awt.event.ActionEvent;
import java.awt.Font;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;

import dao.HorarioDao;
import model.Horario;

public class JanelaCadastroHorario {

	private JPanel panel;
	public JFrame frame;
	private JLabel lblDiaSemana;
	private JLabel lblHoraInicio;
	private JLabel lblHoraFinal;
	private JFormattedTextField inputHoraFinal;
	private JFormattedTextField inputHoraInicio;

	private SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
	private MaskFormatter mfHoraInicio = new MaskFormatter();
	private MaskFormatter mfHoraFinal = new MaskFormatter();
	private JComboBox<String> comboBoxDiaSemana;
	private JButton btnSalvar;
	private JButton btnCancelar;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					JanelaCadastroHorario window = new JanelaCadastroHorario(null);
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public JanelaCadastroHorario(JanelaPrincipal instanciaJanelaPrincipal) {
		initialize(instanciaJanelaPrincipal);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	public void initialize(JanelaPrincipal instanciaJanelaPrincipal) {
		
		frame = new JFrame();
		frame.setResizable(false);
		frame.setTitle("Cadastro de horários de aula");
		frame.setBounds(0, 0, 761, 430);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		panel = new JPanel();
		frame.getContentPane().add(panel, BorderLayout.CENTER);
		panel.setLayout(null);
		
		lblDiaSemana = new JLabel("Dia da semana:");
		lblDiaSemana.setFont(new Font("Dialog", Font.BOLD, 14));
		lblDiaSemana.setBounds(58, 32, 130, 20);
		panel.add(lblDiaSemana);
		
		lblHoraInicio = new JLabel("Hora início aula:");
		lblHoraInicio.setFont(new Font("Dialog", Font.BOLD, 14));
		lblHoraInicio.setBounds(58, 88, 130, 20);
		panel.add(lblHoraInicio);
		
		lblHoraFinal = new JLabel("Hora final aula:");
		lblHoraFinal.setFont(new Font("Dialog", Font.BOLD, 14));
		lblHoraFinal.setBounds(58, 144, 147, 20);
		panel.add(lblHoraFinal);
		
		try {
			mfHoraInicio.setMask("##:##");
		} catch (ParseException e1) {
			e1.printStackTrace();
		}
		try {
			mfHoraFinal.setMask("##:##");
		} catch (ParseException e2) {
			e2.printStackTrace();
		}
		
		inputHoraFinal = new JFormattedTextField();
		inputHoraFinal.setColumns(10);
		inputHoraFinal.setBounds(244, 144, 362, 25);
		panel.add(inputHoraFinal);
		mfHoraInicio.install(inputHoraFinal);
		
		inputHoraInicio = new JFormattedTextField();
		inputHoraInicio.setColumns(10);
		inputHoraInicio.setBounds(244, 88, 362, 25);
		panel.add(inputHoraInicio);
		mfHoraFinal.install(inputHoraInicio);
		
		comboBoxDiaSemana = new JComboBox<String>();
		comboBoxDiaSemana.setBounds(244, 32, 362, 25);
		comboBoxDiaSemana.addItem("Selecione o dia da semana");
		comboBoxDiaSemana.addItem("Segunda-feira");
		comboBoxDiaSemana.addItem("Terça-feira");
		comboBoxDiaSemana.addItem("Quarta-feira");
		comboBoxDiaSemana.addItem("Quinta-feira");
		comboBoxDiaSemana.addItem("Sexta-feira");
		comboBoxDiaSemana.addItem("Sábado");
		comboBoxDiaSemana.addItem("Domingo");
		panel.add(comboBoxDiaSemana);

		btnSalvar = new JButton("Salvar");
		btnSalvar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				String diaSemana = comboBoxDiaSemana.getSelectedItem().toString();
				Date horaInicio = null;
				Date horaFinal = null;
				
				if (diaSemana == "Selecione o dia da semana") {
					JOptionPane.showMessageDialog(null, "ERRO, o dia da semana não foi selecionado");
					return;
				}
				try {
					horaInicio = sdf.parse(inputHoraInicio.getText());
				} catch (ParseException e) {
					JOptionPane.showMessageDialog(null, "ERRO, a hora de início das aulas não foi preenchida corretamente");
					return;
				}
				try {
					horaFinal = sdf.parse(inputHoraFinal.getText());
				} catch (ParseException e) {
					JOptionPane.showMessageDialog(null, "ERRO, a hora final das aulas não foi preenchida corretamente");
					return;
				}
				if (inputHoraInicio.getText().length() < 5) {
					JOptionPane.showMessageDialog(null, "ERRO, a hora de inicio não foi preenchida corretamente");
					return;
				}
				if (inputHoraFinal.getText().length() < 5) {
					JOptionPane.showMessageDialog(null, "ERRO, a hora final não foi preenchida corretamente");
					return;
				}
				if (horaInicio.equals(horaFinal)) {
					JOptionPane.showMessageDialog(null, "ERRO, a hora de inicio é igual à hora final das aulas");
					return;
				}
				if (horaInicio.after(horaFinal)) {
					JOptionPane.showMessageDialog(null, "ERRO, a hora de inicio é posterior à hora final das aulas");
					return;
				}
				
				Horario horario = new Horario();
				horario.setDiaSemana(diaSemana);
				horario.setHorarioInicioAula(horaInicio);
				horario.setHorarioFinalAula(horaFinal);
				switch (horario.getDiaSemana()) {
				case "Segunda-feira":
					horario.setDiaSemanaInt(1);
					break;
				case "Terça-feira":
					horario.setDiaSemanaInt(2);
					break;
				case "Quarta-feira":
					horario.setDiaSemanaInt(3);
					break;
				case "Quinta-feira":
					horario.setDiaSemanaInt(4);
					break;
				case "Sexta-feira":
					horario.setDiaSemanaInt(5);
					break;
				case "Sábado":
					horario.setDiaSemanaInt(6);
					break;
				default:
					horario.setDiaSemanaInt(0);
				}
				
				HorarioDao horarioDao = new HorarioDao();
				
				boolean insercao = horarioDao.inserir(horario);
				
				if (!insercao) {
					JOptionPane.showMessageDialog(null, "ERRO ao inserir o horário de aula no banco de dados");
					limpaCampos();
					return;
				}
				
				limpaCampos();
				instanciaJanelaPrincipal.criaSchedulerPorAula();				
				JOptionPane.showMessageDialog(null, "Horário de aula inserido com sucesso");
			}
		});
		btnSalvar.setBounds(257, 333, 117, 25);
		panel.add(btnSalvar);
		
		btnCancelar = new JButton("Cancelar");
		btnCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				limpaCampos();
				frame. dispose();
			}
		});
		btnCancelar.setBounds(451, 333, 117, 25);
		panel.add(btnCancelar);				
	}
	
	private void limpaCampos() {
		comboBoxDiaSemana.setSelectedIndex(0);
		inputHoraInicio.setText("");
		inputHoraFinal.setText("");
	}
}
