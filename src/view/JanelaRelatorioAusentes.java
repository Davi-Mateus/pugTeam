package view;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;
import java.awt.event.ActionEvent;
import java.awt.Font;
import util.Relatorio;
import com.toedter.calendar.JDateChooser;

public class JanelaRelatorioAusentes {

	private JPanel panel;
	public JFrame frame;
	private JLabel lblTurma;
	private JLabel lblAte;
	private JButton btnGerar;
	private JButton btnCancelar;
	private JButton btnLimpar;
	private JDateChooser dataInicio;
	private JDateChooser dataFinal;

	private SimpleDateFormat sdfData = new SimpleDateFormat("yyyy-MM-dd");


	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					JanelaRelatorioAusentes window = new JanelaRelatorioAusentes(null);
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
	public JanelaRelatorioAusentes(JanelaPrincipal instanciaJanelaPrincipal) {
		initialize(instanciaJanelaPrincipal);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	public void initialize(JanelaPrincipal instanciaJanelaPrincipal) {
		
		frame = new JFrame();
		frame.setResizable(false);
		frame.setTitle("Relatório de alunos ausentes");
		frame.setBounds(0, 0, 761, 180);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
				
		panel = new JPanel();
		frame.getContentPane().add(panel, BorderLayout.CENTER);
		panel.setLayout(null);
		
		lblTurma = new JLabel("Selecione o período:");
		lblTurma.setFont(new Font("Dialog", Font.BOLD, 14));
		lblTurma.setBounds(30, 20, 190, 20);
		panel.add(lblTurma);
		
		btnGerar = new JButton("Gerar");
		btnGerar.addActionListener(new ActionListener() {			
			public void actionPerformed(ActionEvent arg0) {

				String dtInicio;
				try {
					dtInicio = sdfData.format(dataInicio.getDate());
				} catch (Exception e1) {
					JOptionPane.showMessageDialog(null, "ERRO, a data de início não foi selecionada");
					return;
				}
				
				String dtFinal;
				try {
					dtFinal = sdfData.format(dataFinal.getDate());
				} catch (Exception e1) {
					JOptionPane.showMessageDialog(null, "ERRO, a data de final não foi selecionada");
					return;
				}
				
				Map<String, Object> parametros = new HashMap<String, Object>();
				parametros.put("dt_inicio", dtInicio);
				parametros.put("dt_final", dtFinal);
								
				try {
					new Relatorio().gerar( "/home/rafael/GitHub/pugTeam/src/relatorios/AlunosAusentes.jasper", parametros, "Relatório de alunos ausentes" );
				} catch (Exception e) {
					e.printStackTrace();
				}
				
			}
		});
		btnGerar.setBounds(535, 20, 125, 25);
		panel.add(btnGerar);
		
		btnCancelar = new JButton("Cancelar");
		btnCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				limpaCampos();
				frame. dispose();
			}
		});
		btnCancelar.setBounds(451, 95, 117, 25);
		panel.add(btnCancelar);
		
		dataInicio = new JDateChooser();
		dataInicio.setDateFormatString("dd/MM/yyyy");
		dataInicio.setBounds(220, 20, 100, 25);
		panel.add(dataInicio);
		
		dataFinal = new JDateChooser();
		dataFinal.setDateFormatString("dd/MM/yyyy");
		dataFinal.setBounds(370, 20, 100, 25);
		panel.add(dataFinal);
		
		lblAte = new JLabel("até");
		lblAte.setBounds(333, 20, 70, 20);
		panel.add(lblAte);
		
		btnLimpar = new JButton("Limpar");
		btnLimpar.setBounds(257, 95, 117, 25);
		btnLimpar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				limpaCampos();
			}
		});
		panel.add(btnLimpar);
		
		
	}
	
	private void limpaCampos() {
		dataInicio.setDate(null);
		dataFinal.setDate(null);
	}
}
