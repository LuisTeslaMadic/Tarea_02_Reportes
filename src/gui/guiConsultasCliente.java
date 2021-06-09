package gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import Entidad.Cliente;
import Modelo.ClienteModel;
import Reporte.GeneradorReporte;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.view.JRViewer;
import javax.swing.ImageIcon;
import java.awt.Color;
import java.awt.Toolkit;

public class guiConsultasCliente extends JFrame implements ActionListener{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JButton btnReportar;
	private JPanel ReporteCliente;
	private JLabel lblTipoDeFiltro;
	private JTextField txtFiltro;
	private JComboBox<String> cboTipoFiltro;
	private JLabel lblDni;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					guiConsultasCliente frame = new guiConsultasCliente();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public guiConsultasCliente() {
		setTitle("Reporte Cliente");
		setIconImage(Toolkit.getDefaultToolkit().getImage(guiConsultasCliente.class.getResource("/icons/dashboard.png")));
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 935, 631);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		btnReportar = new JButton("REPORTAR");
		btnReportar.setFocusable(false);
		btnReportar.setBackground(Color.WHITE);
		btnReportar.setForeground(Color.DARK_GRAY);
		btnReportar.setIcon(new ImageIcon(guiConsultasCliente.class.getResource("/icons/checklist.png")));
		btnReportar.addActionListener(this);
		btnReportar.setFont(new Font("Segoe UI", Font.BOLD | Font.ITALIC, 15));
		btnReportar.setBounds(727, 11, 182, 44);
		contentPane.add(btnReportar);
		
		ReporteCliente = new JPanel();
		ReporteCliente.setBounds(10, 88, 899, 493);
		contentPane.add(ReporteCliente);
		ReporteCliente.setLayout(new BorderLayout(0, 0));
		
		lblTipoDeFiltro = new JLabel("Tipo de filtro :");
		lblTipoDeFiltro.setForeground(Color.DARK_GRAY);
		lblTipoDeFiltro.setFont(new Font("Segoe UI", Font.BOLD | Font.ITALIC, 13));
		lblTipoDeFiltro.setBounds(21, 11, 94, 18);
		contentPane.add(lblTipoDeFiltro);
		
		txtFiltro = new JTextField();
		txtFiltro.setFont(new Font("Segoe UI", Font.ITALIC, 14));
		txtFiltro.setBounds(129, 46, 120, 20);
		contentPane.add(txtFiltro);
		txtFiltro.setColumns(10);
		
		cboTipoFiltro = new JComboBox<String>();
		cboTipoFiltro.setModel(new DefaultComboBoxModel<String>(new String[] {"Nombre", "Dni"}));
		cboTipoFiltro.setFont(new Font("Segoe UI", Font.ITALIC, 14));
		cboTipoFiltro.setBounds(129, 10, 120, 22);
		contentPane.add(cboTipoFiltro);
		
		lblDni = new JLabel("Filtro               :");
		lblDni.setForeground(Color.DARK_GRAY);
		lblDni.setFont(new Font("Segoe UI", Font.BOLD | Font.ITALIC, 13));
		lblDni.setBounds(21, 46, 105, 18);
		contentPane.add(lblDni);
		
	}

	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == btnReportar) {
			actionPerformedBtnReportar(e);
		}
	}

	protected void actionPerformedBtnReportar(ActionEvent e) {
		ClienteModel cliMo = new ClienteModel();
		List<Cliente> ArregloData = null;
		String filtro     = getFiltro();
		String TipoFiltro = TipoFiltro();
		if(filtro.equals("")) {
			ArregloData =  cliMo.ListarCliente();
		}else if (TipoFiltro.equals("c.nombres")){
			ArregloData = cliMo.Buscar(TipoFiltro, filtro);
		}else if(TipoFiltro.equals("c.dni")) {
			ArregloData = cliMo.Buscar(TipoFiltro, filtro);
		}
		
		JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(ArregloData);

		String file = "ReporteCliente.jasper";

		JasperPrint jasperPrint = GeneradorReporte.genera(file, dataSource, null);

		JRViewer jRViewer = new JRViewer(jasperPrint);

		ReporteCliente.removeAll();
		ReporteCliente.add(jRViewer);
		ReporteCliente.repaint();
		ReporteCliente.revalidate();

	}
	
	// Metodos complementarios
	String TipoFiltro() {
		if(cboTipoFiltro.getSelectedIndex() == 0) {
			return "c.nombres";
		}
	  return "c.dni";	
	}
	String getFiltro() {
		return txtFiltro.getText().trim();
	}
	
}
