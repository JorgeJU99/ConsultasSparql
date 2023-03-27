
import java.awt.EventQueue;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.hp.hpl.jena.query.Query;
import com.hp.hpl.jena.query.QueryExecution;
import com.hp.hpl.jena.query.QueryExecutionFactory;
import com.hp.hpl.jena.query.QueryFactory;
import com.hp.hpl.jena.query.ResultSet;
import com.hp.hpl.jena.query.ResultSetFormatter;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.util.FileManager;

import java.awt.BorderLayout;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import java.awt.Font;
import javax.swing.JLabel;
import java.awt.Color;
import javax.swing.JSeparator;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.JTextArea;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class principal extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					principal frame = new principal();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Principal.
	 */
	public principal() {
		disenoInterfaz();
	}

	/**
	 * Create the frame.
	 */
	@SuppressWarnings({ "rawtypes", "unchecked", "unused" })
	private void disenoInterfaz() {
		setResizable(false);
		setTitle("Inteligencia Artifical - Sparql");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 869, 472);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblTitulo = new JLabel("Consulta Sparql");
		lblTitulo.setBounds(122, 0, 641, 54);
		lblTitulo.setBackground(new Color(255, 255, 128));
		lblTitulo.setHorizontalAlignment(SwingConstants.CENTER);
		lblTitulo.setFont(new Font("Berlin Sans FB", Font.PLAIN, 20));
		contentPane.add(lblTitulo);

		JLabel lblPelicula = new JLabel("Película:");
		lblPelicula.setFont(new Font("Berlin Sans FB", Font.PLAIN, 16));
		lblPelicula.setBounds(149, 74, 91, 14);
		contentPane.add(lblPelicula);

		JLabel lblCategoria = new JLabel("Categoría:");
		lblCategoria.setFont(new Font("Berlin Sans FB", Font.PLAIN, 16));
		lblCategoria.setBounds(149, 105, 91, 24);
		contentPane.add(lblCategoria);

		JLabel lblPais = new JLabel("País:");
		lblPais.setFont(new Font("Berlin Sans FB", Font.PLAIN, 16));
		lblPais.setBounds(149, 143, 91, 14);
		contentPane.add(lblPais);

		JComboBox comboBoxPelicula = new JComboBox();
		comboBoxPelicula.setFont(new Font("Berlin Sans FB", Font.PLAIN, 16));
		comboBoxPelicula.setModel(new DefaultComboBoxModel(new String[] { "Seleccionar...", "12 anios de esclavitud",
				"El nacimiento de un imperio", "Aires de esperanza", "Batman Assault on Arkham", "Acomplejado",
				"Acumuladores", "Cuponmania" }));
		comboBoxPelicula.setBounds(311, 72, 409, 22);
		contentPane.add(comboBoxPelicula);

		JComboBox comboBoxCategoria = new JComboBox();
		comboBoxCategoria.setModel(new DefaultComboBoxModel(
				new String[] { "Seleccionar...", "Peliculas", "Entretenimiento", "Farandula" }));
		comboBoxCategoria.setFont(new Font("Berlin Sans FB", Font.PLAIN, 16));
		comboBoxCategoria.setBounds(311, 106, 409, 22);
		contentPane.add(comboBoxCategoria);

		JComboBox comboBoxPais = new JComboBox();
		comboBoxPais.setModel(new DefaultComboBoxModel(new String[] { "Seleccionar...", "Ecuador", "Colombia" }));
		comboBoxPais.setFont(new Font("Berlin Sans FB", Font.PLAIN, 16));
		comboBoxPais.setBounds(311, 141, 409, 22);
		contentPane.add(comboBoxPais);

		JSeparator jSeparator1 = new JSeparator();
		jSeparator1.setBounds(0, 51, 853, 30);
		contentPane.add(jSeparator1);

		JSeparator jSeparator2 = new JSeparator();
		jSeparator2.setBounds(0, 230, 853, 30);
		contentPane.add(jSeparator2);

		JTextArea txtAreaResultado = new JTextArea();
		txtAreaResultado.setBounds(0, 230, 853, 203);
		contentPane.add(txtAreaResultado);

		JButton btnConsulta = new JButton("Consultar");
		btnConsulta.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				consulta(comboBoxPelicula, comboBoxCategoria, comboBoxPais, txtAreaResultado);
			}
		});
		btnConsulta.setFont(new Font("Berlin Sans FB", Font.PLAIN, 16));
		btnConsulta.setBounds(389, 187, 122, 23);
		contentPane.add(btnConsulta);
	}

	@SuppressWarnings("unused")
	private void consulta(JComboBox comboBoxPelicula, JComboBox comboBoxCategoria, JComboBox comboBoxPais,
			JTextArea txtAreaResultado) {
		try {
			FileManager.get().addLocatorClassLoader(principal.class.getClassLoader());
			Model model = FileManager.get().loadModel("programa.rdf");

			if (comboBoxPelicula.getSelectedIndex() > 0 && comboBoxCategoria.getSelectedIndex() > 0
					&& comboBoxPais.getSelectedIndex() > 0) {
				String pelicula = comboBoxPelicula.getSelectedItem().toString();
				String categoria = comboBoxCategoria.getSelectedItem().toString();
				String pais = comboBoxPais.getSelectedItem().toString();

				String cadena = "prefix po: <http://purl.org/ontology/po/> "
						+ "prefix rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> "
						+ "select ?pelicula ?actor ?genero ?categoria ?pais " 
						+ "where {"
						+ "?a po:Broadcast ?a1."
						+ "?a po:Brand ?pelicula."
						+ "?a po:actor ?actor." 
						+ "?a po:Genre ?genero." 
						+ "?a po:Category ?categoria."
						+ "?a1 po:Place ?pais."
						+ "filter ((str(?categoria) = '" + categoria + "') && (str(?pelicula) = '" + pelicula+ "')" 
						+ "&& (str(?pais) = '" + pais + "')).}";
				Query query = QueryFactory.create(cadena);
				QueryExecution qe = QueryExecutionFactory.create(query, model);
				ResultSet results = qe.execSelect();
				txtAreaResultado.setText(ResultSetFormatter.asText(results));
			} else {
				txtAreaResultado.setText("Por favor, seleccione info de todas las listas");

			}
		} catch (Exception e) {
			System.out.print(e);
		}

	}
}
