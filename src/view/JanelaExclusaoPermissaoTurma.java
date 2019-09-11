package view;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import java.awt.BorderLayout;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.awt.event.ActionEvent;
import java.awt.Font;

import dao.PermissaoDao;
import dao.PermiteTurmaDao;
import model.Permissao;
import javax.swing.JTextField;

public class JanelaExclusaoPermissaoTurma {

	public JFrame frame;
	private JPanel panel;
	private JLabel labelTurma;
	private JButton btnExcluir;
	private JButton btnCancelar;
	private JTextField inputPermissao;
	
	private Permissao permissaoSelecionada = new Permissao();
	private SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
	

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					JanelaExclusaoPermissaoTurma window = new JanelaExclusaoPermissaoTurma(null);
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
	public JanelaExclusaoPermissaoTurma(JanelaPrincipal instanciaJanelaPrincipal) {
		initialize(instanciaJanelaPrincipal);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	public void initialize(JanelaPrincipal instanciaJanelaPrincipal) {
		
		frame = new JFrame();
		frame.setResizable(false);
		frame.setTitle("Excluir permissão de turma");
		frame.setBounds(0, 0, 761, 430);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		panel = new JPanel();
		frame.getContentPane().add(panel, BorderLayout.CENTER);
		panel.setLayout(null);
				
		labelTurma = new JLabel("Turma:");
		labelTurma.setFont(new Font("Dialog", Font.BOLD, 14));
		labelTurma.setBounds(58, 32, 130, 20);
		panel.add(labelTurma);
		
		inputPermissao = new JTextField();
		inputPermissao.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent arg0) {
				JPopupMenu popupMenu = new JPopupMenu();
				List<Permissao> permissoes = new ArrayList<>();
				PermissaoDao permissaoDao = new PermissaoDao();
				permissoes = permissaoDao.pesquisarPorTurma(inputPermissao.getText());
				for (Permissao permissao: permissoes) {
					JMenuItem item = new JMenuItem(permissao.getTurma().getNomeTurma() + " - " + permissao.getTurma().getAno() + " - " + sdf.format(permissao.getDataPermissao()) + " - " + permissao.getTipoPermissao());
					popupMenu.add(item);
					item.addActionListener(new ActionListener() {
			            public void actionPerformed(ActionEvent e) {
			                inputPermissao.setText(permissao.getTurma().getNomeTurma() + " - " + permissao.getTurma().getAno() + " - " + sdf.format(permissao.getDataPermissao()) + " - " + permissao.getTipoPermissao());
			                permissaoSelecionada = permissao;
			            }
			        });
				}
				popupMenu.show(inputPermissao, 0, 25);
				inputPermissao.requestFocus();
			}
		});
		inputPermissao.setColumns(10);
		inputPermissao.setBounds(244, 32, 362, 25);
		panel.add(inputPermissao);							

		btnExcluir = new JButton("Excluir");
		btnExcluir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				PermiteTurmaDao permiteTurmaDao = new PermiteTurmaDao();
				PermissaoDao permissaoDao = new PermissaoDao();
				
				if (permissaoSelecionada.getIdPermissao() == 0) {
					JOptionPane.showMessageDialog(null, "ERRO, a permissão não foi selecionada corretamente");
					return;
				}
				
				boolean exclusaoPermiteTurma = permiteTurmaDao.excluir(permissaoSelecionada.getTurma().getIdTurma(), permissaoSelecionada.getIdPermissao());
				
				if (!exclusaoPermiteTurma) {
					JOptionPane.showMessageDialog(null, "ERRO ao excluir a permissão do banco de dados");
					limpaCampos();
					return;
				}
				
				boolean exclusaoPermissao = permissaoDao.excluir(permissaoSelecionada);
				
				if (!exclusaoPermissao) {
					JOptionPane.showMessageDialog(null, "ERRO ao excluir a permissão do banco de dados");
					limpaCampos();
					return;
				}
				limpaCampos();
				instanciaJanelaPrincipal.preencheTurmasComPermissao();
				JOptionPane.showMessageDialog(null, "Permissão excluída com sucesso");
			}
		});
		btnExcluir.setBounds(257, 333, 117, 25);
		panel.add(btnExcluir);
		
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
		inputPermissao.setText("");
	}
}
