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
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.awt.event.ActionEvent;
import java.awt.Font;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;

import dao.HorarioDao;
import model.Horario;
public class JanelaAlteracaoHorario {

	private JPanel panel;
	public JFrame frame;
	private JLabel lblDiaSemana;
	private JLabel lblHoraInicio;
	private JLabel lblHoraFinal;
	private JLabel labelHorario;
	private JFormattedTextField inputHoraFinal;
	private JFormattedTextField inputHoraInicio;
	private JComboBox<String> comboBoxDiaSemana;
	private JComboBox<Object> comboBoxHorario;
	private JButton btnSalvar;
	private JButton btnCancelar;
	
	private SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
	private MaskFormatter mfHoraInicio = new MaskFormatter();
	private MaskFormatter mfHoraFinal = new MaskFormatter();
	private Horario horarioSelecionado = new Horario();
	

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					JanelaAlteracaoHorario window = new JanelaAlteracaoHorario(null);
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
	public JanelaAlteracaoHorario(JanelaPrincipal instanciaJanelaPrincipal) {
		initialize(instanciaJanelaPrincipal);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	public void initialize(JanelaPrincipal instanciaJanelaPrincipal) {
		
		frame = new JFrame();
		frame.setResizable(false);
		frame.setTitle("Alterar hor·rios de aula");
		frame.setBounds(0, 0, 761, 430);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		panel = new JPanel();
		frame.getContentPane().add(panel, BorderLayout.CENTER);
		panel.setLayout(null);
		
		lblDiaSemana = new JLabel("Novo dia da semana:");
		lblDiaSemana.setFont(new Font("Dialog", Font.BOLD, 14));
		lblDiaSemana.setBounds(58, 88, 186, 20);
		panel.add(lblDiaSemana);
		
		lblHoraInicio = new JLabel("Nova hora inÌcio aula:");
		lblHoraInicio.setFont(new Font("Dialog", Font.BOLD, 14));
		lblHoraInicio.setBounds(58, 144, 186, 20);
		panel.add(lblHoraInicio);
		
		lblHoraFinal = new JLabel("Nova hora final aula:");
		lblHoraFinal.setFont(new Font("Dialog", Font.BOLD, 14));
		lblHoraFinal.setBounds(58, 200, 186, 20);
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
		inputHoraFinal.setBounds(244, 200, 362, 25);
		panel.add(inputHoraFinal);
		mfHoraInicio.install(inputHoraFinal);
		
		inputHoraInicio = new JFormattedTextField();
		inputHoraInicio.setColumns(10);
		inputHoraInicio.setBounds(244, 144, 362, 25);
		panel.add(inputHoraInicio);
		mfHoraFinal.install(inputHoraInicio);
		
		comboBoxDiaSemana = new JComboBox<String>();
		comboBoxDiaSemana.setBounds(244, 88, 362, 25);
		comboBoxDiaSemana.addItem("Selecione o dia da semana");
		comboBoxDiaSemana.addItem("Segunda-feira");
		comboBoxDiaSemana.addItem("TerÁa-feira");
		comboBoxDiaSemana.addItem("Quarta-feira");
		comboBoxDiaSemana.addItem("Quinta-feira");
		comboBoxDiaSemana.addItem("Sexta-feira");
		comboBoxDiaSemana.addItem("S·bado");
		comboBoxDiaSemana.addItem("Domingo");
		panel.add(comboBoxDiaSemana);
		
		comboBoxHorario = new JComboBox<Object>();
		preencherComboBoxHorario();
		comboBoxHorario.setBounds(244, 32, 362, 25);
		comboBoxHorario.addItemListener(new ItemListener() {
	        public void itemStateChanged(ItemEvent arg0) {
	        	if (comboBoxHorario.getSelectedIndex() == 0) {
	        		limpaCampos();
	        		return;
				}
	        	horarioSelecionado = (Horario)comboBoxHorario.getSelectedItem();
				if (horarioSelecionado == null) {
					return;
				}
				inputHoraInicio.setText(sdf.format(horarioSelecionado.getHorarioInicioAula()));
				inputHoraFinal.setText(sdf.format(horarioSelecionado.getHorarioFinalAula()));
	        	selecionaComboBoxDiaSemana();
	        }
	    });
		panel.add(comboBoxHorario);
		
		labelHorario = new JLabel("hor·rio:");
		labelHorario.setFont(new Font("Dialog", Font.BOLD, 14));
		labelHorario.setBounds(58, 32, 130, 20);
		panel.add(labelHorario);

		btnSalvar = new JButton("Salvar");
		btnSalvar.addActionListener(new ActionListener() {			
			public void actionPerformed(ActionEvent arg0) {
				
				Horario horario;
				String diaSemana = comboBoxDiaSemana.getSelectedItem().toString();
				Date horaInicio = null;
				Date horaFinal = null;
				
				try {
					horario = (Horario) comboBoxHorario.getSelectedItem();
				} catch (Exception e1) {
					JOptionPane.showMessageDialog(null, "ERRO, o hor·rio n√£o foi selecionado");
					return;
				}
				if (diaSemana == "Selecione o dia da semana") {
					JOptionPane.showMessageDialog(null, "ERRO, o dia da semana n√£o foi selecionado");
					return;
				}
				try {
					horaInicio = sdf.parse(inputHoraInicio.getText());
				} catch (ParseException e) {
					JOptionPane.showMessageDialog(null, "ERRO, a hora de inicio n„o foi preenchida corretamente");
					return;
				}
				try {
					horaFinal = sdf.parse(inputHoraFinal.getText());
				} catch (ParseException e) {
					JOptionPane.showMessageDialog(null, "ERRO, a hora final das aulas n„o foi preenchida corretamente");
					return;
				}
				if (horaInicio.equals(horaFinal)) {
					JOptionPane.showMessageDialog(null, "ERRO, a hora de inicio È igual ‡ hora final das aulas");
					return;
				}
				if (horaInicio.after(horaFinal)) {
					JOptionPane.showMessageDialog(null, "ERRO, a hora de inicio È posterior ‡ hora final das aulas");
					return;
				}
				
				Horario horarioNovo = new Horario();
				horarioNovo.setIdHorario(horario.getIdHorario());
				horarioNovo.setDiaSemana(diaSemana);
				horarioNovo.setHorarioInicioAula(horaInicio);
				horarioNovo.setHorarioFinalAula(horaFinal);
				switch (horario.getDiaSemana()) {
				case "Segunda-feira":
					horarioNovo.setDiaSemanaInt(1);
					break;
				case "Ter√ßa-feira":
					horarioNovo.setDiaSemanaInt(2);
					break;
				case "Quarta-feira":
					horarioNovo.setDiaSemanaInt(3);
					break;
				case "Quinta-feira":
					horarioNovo.setDiaSemanaInt(4);
					break;
				case "Sexta-feira":
					horarioNovo.setDiaSemanaInt(5);
					break;
				case "S√°bado":
					horarioNovo.setDiaSemanaInt(6);
					break;
					
				default:
					horario.setDiaSemanaInt(0);
				}
				
				HorarioDao quadroHorariosDaoNovo = new HorarioDao();
				boolean alteracao = quadroHorariosDaoNovo.alterar(horarioNovo);
				
				if (!alteracao) {
					JOptionPane.showMessageDialog(null, "ERRO ao alterar o hor·rio de aula no banco de dados");
					limpaCampos();
					return;
				}
				
				limpaCampos();
				preencherComboBoxHorario();	
				instanciaJanelaPrincipal.criaSchedulerPorAula();
				JOptionPane.showMessageDialog(null, "Hor·rio de aula alterado com sucesso");
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
	
	private void preencherComboBoxHorario() {
		comboBoxHorario.removeAllItems();
		List<Horario> quadrosHorarios = new ArrayList<>();
		HorarioDao quadroHorariosDao = new HorarioDao();
		quadrosHorarios = quadroHorariosDao.pesquisarPorNome("");
		comboBoxHorario.addItem("Selecione o hor·rio");
		for (Horario quadroHorarios: quadrosHorarios) {
			comboBoxHorario.addItem(quadroHorarios);
		}
	}
	
	private void selecionaComboBoxDiaSemana() {
		for (int i = 0; i < comboBoxHorario.getItemCount(); i++) {
			try {
				if ((int)((Horario)comboBoxHorario.getItemAt(i)).getIdHorario() == horarioSelecionado.getIdHorario()) {
					comboBoxDiaSemana.setSelectedItem((String)((Horario)comboBoxHorario.getItemAt(i)).getDiaSemana());;
					break;
				}
			} catch (Exception e) {
			}
		}
	}
		
	private void limpaCampos() {
		comboBoxDiaSemana.setSelectedIndex(0);
		inputHoraInicio.setText("");
		inputHoraFinal.setText("");
	}
}
