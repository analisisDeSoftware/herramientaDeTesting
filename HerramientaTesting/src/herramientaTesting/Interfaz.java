package herramientaTesting;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.TextArea;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;

import javax.swing.DefaultListModel;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class Interfaz extends JFrame {

	private JPanel contentPane;

	private HerramientaTesting contador;
	private String archivoSeleccionado;
	private String claseSeleccionada;
	private String metodoSeleccionado;
	
	private DefaultListModel<String> dmArchivos;
	private DefaultListModel<String> dmClases;
	private DefaultListModel<String> dmMetodos;
	
	private JList listArchivosJava;
	private JList listClases;
	private JList listMetodos;
	private TextArea textCodigo;
	
	private JScrollPane scrollPane;
	private JScrollPane scrollPane_1;
	private JScrollPane scrollPane_2;
	private JLabel lblAnlisisDelMtodo;
	private JLabel lblLneasDeCdigo;
	private JLabel lblLneasDeCdigo_1;
	private JLabel lblLneasDe;
	private JLabel lblComplejidadCiclomtica;
	private JLabel lblFanIn;
	private JLabel lblFanOut;
	private JLabel lblHasteadVolumen;
	private JLabel lblHasteadLongitud;
	private JLabel labelLineasCodigo;
	private JLabel labelLineasComentadas;
	private JLabel labelPorcLineasComentadas;
	private JLabel labelComplejidadCiclomatica;
	private JLabel labelFanIn;
	private JLabel labelFanOut;
	private JLabel labelHasteadLongitud;
	private JLabel labelHasteadVolumen;
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
				        if ("Nimbus".equals(info.getName())) {
				            UIManager.setLookAndFeel(info.getClassName());
				            break;
				        }
					}
					Interfaz frame = new Interfaz();
					frame.setResizable(false);
					frame.setLocationRelativeTo(null);
					frame.setTitle("Herramienta de Testing");
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	
	public Interfaz() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1200, 800);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(0, 0, 51));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		dmArchivos = new DefaultListModel<String>();
		listArchivosJava = new JList(dmArchivos);
		listArchivosJava.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		listArchivosJava.setBackground(new Color(255, 255, 255));
		listArchivosJava.setForeground(new Color(0, 0, 51));
		scrollPane_2 = new JScrollPane();
		scrollPane_2.setBounds(25, 78, 618, 158);
		scrollPane_2.setViewportView(listArchivosJava);
		contentPane.add(scrollPane_2);
		
		
		dmClases = new DefaultListModel<String>();
		listClases = new JList(dmClases);
		listClases.setForeground(new Color(0, 0, 51));
		listClases.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		listClases.setBackground(Color.WHITE);
		scrollPane = new JScrollPane();
		scrollPane.setBounds(25, 270, 186, 201);
		scrollPane.setViewportView(listClases);
		contentPane.add(scrollPane);
		

		dmMetodos = new DefaultListModel<String>();
		listMetodos = new JList(dmMetodos);
		listMetodos.setForeground(new Color(0, 0, 51));
		listMetodos.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		listMetodos.setBackground(Color.WHITE);
		scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(271, 270, 372, 201);
		scrollPane_1.setViewportView(listMetodos);
		contentPane.add(scrollPane_1);
		
		
		JMenuBar menuBar = new JMenuBar();
		menuBar.setFont(new Font("Segoe UI", Font.PLAIN, 32));
		menuBar.setBounds(0, 0, 1200, 31);
		contentPane.add(menuBar);
		
		JMenu mnArchivo = new JMenu("Carpeta");
		mnArchivo.setFont(new Font("Segoe UI", Font.PLAIN, 20));
		menuBar.add(mnArchivo);
		
		JMenuItem mntmAbrir = new JMenuItem("Abrir");
		mntmAbrir.setFont(new Font("Segoe UI", Font.PLAIN, 20));
		mnArchivo.add(mntmAbrir);
		
		textCodigo = new TextArea();
		textCodigo.setBounds(25, 523, 618, 210);
		textCodigo.setEditable(false);
		contentPane.add(textCodigo);
		textCodigo.setForeground(new Color(0, 0, 51));
		textCodigo.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		textCodigo.setBackground(Color.WHITE);
		
		JLabel lblArchivos = new JLabel("Archivos");
		lblArchivos.setForeground(new Color(255, 255, 255));
		lblArchivos.setFont(new Font("Segoe UI", Font.PLAIN, 20));
		lblArchivos.setBounds(25, 56, 87, 20);
		contentPane.add(lblArchivos);
		
		JLabel lblClases = new JLabel("Clases");
		lblClases.setForeground(Color.WHITE);
		lblClases.setFont(new Font("Segoe UI", Font.PLAIN, 20));
		lblClases.setBounds(25, 244, 87, 20);
		contentPane.add(lblClases);
		
		JLabel lblMtodos = new JLabel("M\u00E9todos");
		lblMtodos.setForeground(Color.WHITE);
		lblMtodos.setFont(new Font("Segoe UI", Font.PLAIN, 20));
		lblMtodos.setBounds(276, 244, 87, 20);
		contentPane.add(lblMtodos);
		
		JLabel lblCodigo = new JLabel("C\u00F3digo");
		lblCodigo.setForeground(Color.WHITE);
		lblCodigo.setFont(new Font("Segoe UI", Font.PLAIN, 20));
		lblCodigo.setBounds(25, 486, 87, 31);
		contentPane.add(lblCodigo);
		
		JLabel label = new JLabel("New label");
		label.setBounds(746, 82, 69, 20);
		contentPane.add(label);
		
		lblAnlisisDelMtodo = new JLabel("An\u00E1lisis del M\u00E9todo");
		lblAnlisisDelMtodo.setForeground(Color.WHITE);
		lblAnlisisDelMtodo.setFont(new Font("Segoe UI", Font.PLAIN, 20));
		lblAnlisisDelMtodo.setBounds(726, 47, 178, 20);
		contentPane.add(lblAnlisisDelMtodo);
		
		lblLneasDeCdigo = new JLabel("L\u00EDneas de c\u00F3digo totales:");
		lblLneasDeCdigo.setForeground(Color.WHITE);
		lblLneasDeCdigo.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		lblLneasDeCdigo.setBounds(675, 85, 178, 31);
		contentPane.add(lblLneasDeCdigo);
		
		lblLneasDeCdigo_1 = new JLabel("L\u00EDneas de c\u00F3digo comentadas:");
		lblLneasDeCdigo_1.setForeground(Color.WHITE);
		lblLneasDeCdigo_1.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		lblLneasDeCdigo_1.setBounds(675, 170, 206, 31);
		contentPane.add(lblLneasDeCdigo_1);
		
		lblLneasDe = new JLabel("% L\u00EDneas de c\u00F3digo comentadas:");
		lblLneasDe.setForeground(Color.WHITE);
		lblLneasDe.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		lblLneasDe.setBounds(675, 255, 229, 31);
		contentPane.add(lblLneasDe);
		
		lblComplejidadCiclomtica = new JLabel("Complejidad Ciclom\u00E1tica:");
		lblComplejidadCiclomtica.setForeground(Color.WHITE);
		lblComplejidadCiclomtica.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		lblComplejidadCiclomtica.setBounds(675, 340, 178, 31);
		contentPane.add(lblComplejidadCiclomtica);
		
		lblFanIn = new JLabel("Fan In:");
		lblFanIn.setForeground(Color.WHITE);
		lblFanIn.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		lblFanIn.setBounds(675, 425, 50, 31);
		contentPane.add(lblFanIn);
		
		lblFanOut = new JLabel("Fan Out:");
		lblFanOut.setForeground(Color.WHITE);
		lblFanOut.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		lblFanOut.setBounds(675, 510, 69, 31);
		contentPane.add(lblFanOut);
		
		lblHasteadVolumen = new JLabel("Hastead volumen:");
		lblHasteadVolumen.setForeground(Color.WHITE);
		lblHasteadVolumen.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		lblHasteadVolumen.setBounds(675, 680, 128, 31);
		contentPane.add(lblHasteadVolumen);
		
		lblHasteadLongitud = new JLabel("Hastead longitud:");
		lblHasteadLongitud.setForeground(Color.WHITE);
		lblHasteadLongitud.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		lblHasteadLongitud.setBounds(675, 595, 128, 31);
		contentPane.add(lblHasteadLongitud);
		
		labelLineasCodigo = new JLabel("");
		labelLineasCodigo.setHorizontalAlignment(SwingConstants.CENTER);
		labelLineasCodigo.setForeground(new Color(0, 204, 153));
		labelLineasCodigo.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		labelLineasCodigo.setBounds(675, 134, 102, 20);
		contentPane.add(labelLineasCodigo);
		
		labelLineasComentadas = new JLabel("");
		labelLineasComentadas.setHorizontalAlignment(SwingConstants.CENTER);
		labelLineasComentadas.setForeground(new Color(0, 204, 153));
		labelLineasComentadas.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		labelLineasComentadas.setBounds(675, 219, 102, 20);
		contentPane.add(labelLineasComentadas);
		
		labelPorcLineasComentadas = new JLabel("");
		labelPorcLineasComentadas.setHorizontalAlignment(SwingConstants.CENTER);
		labelPorcLineasComentadas.setForeground(new Color(0, 204, 153));
		labelPorcLineasComentadas.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		labelPorcLineasComentadas.setBounds(675, 304, 102, 20);
		contentPane.add(labelPorcLineasComentadas);
		
		labelComplejidadCiclomatica = new JLabel("");
		labelComplejidadCiclomatica.setHorizontalAlignment(SwingConstants.CENTER);
		labelComplejidadCiclomatica.setForeground(new Color(0, 204, 153));
		labelComplejidadCiclomatica.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		labelComplejidadCiclomatica.setBounds(675, 389, 102, 20);
		contentPane.add(labelComplejidadCiclomatica);
		
		labelFanIn = new JLabel("");
		labelFanIn.setHorizontalAlignment(SwingConstants.CENTER);
		labelFanIn.setForeground(new Color(0, 204, 153));
		labelFanIn.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		labelFanIn.setBounds(675, 474, 102, 20);
		contentPane.add(labelFanIn);
		
		labelFanOut = new JLabel("");
		labelFanOut.setHorizontalAlignment(SwingConstants.CENTER);
		labelFanOut.setForeground(new Color(0, 204, 153));
		labelFanOut.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		labelFanOut.setBounds(675, 559, 102, 20);
		contentPane.add(labelFanOut);
		
		labelHasteadLongitud = new JLabel("");
		labelHasteadLongitud.setHorizontalAlignment(SwingConstants.CENTER);
		labelHasteadLongitud.setForeground(new Color(0, 204, 153));
		labelHasteadLongitud.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		labelHasteadLongitud.setBounds(675, 644, 102, 20);
		contentPane.add(labelHasteadLongitud);
		
		labelHasteadVolumen = new JLabel("");
		labelHasteadVolumen.setHorizontalAlignment(SwingConstants.CENTER);
		labelHasteadVolumen.setForeground(new Color(0, 204, 153));
		labelHasteadVolumen.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		labelHasteadVolumen.setBounds(675, 713, 102, 20);
		contentPane.add(labelHasteadVolumen);

		// Listeners
		
		mntmAbrir.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent arg0) {
				
				contador = new HerramientaTesting();
				
				JFileChooser fc = new JFileChooser();
				fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				int opcion = fc.showOpenDialog(null);
				
				if(opcion == JFileChooser.APPROVE_OPTION) {
					contador.getArchivosJava().clear();
					contador.buscarArchivosJava(fc.getSelectedFile());
				
					dmArchivos.clear();
					dmClases.clear();
            		dmMetodos.clear();
            		listArchivosJava.setSelectedIndex(-1);
            		listClases.setSelectedIndex(-1);
            		listMetodos.setSelectedIndex(-1);
            		textCodigo.setText("");
					
					for(String archivo : contador.getArchivosJava()) 
						dmArchivos.addElement(archivo);
				}
				
			}
		});
		
		
		listArchivosJava.addListSelectionListener(new ListSelectionListener() {

            @Override
            public void valueChanged(ListSelectionEvent arg0) {
            	if (!arg0.getValueIsAdjusting() && listArchivosJava.getSelectedIndex() != -1) {
                	try {
    					dmClases.clear();
                		dmMetodos.clear();
                		listClases.setSelectedIndex(-1);
                		listMetodos.setSelectedIndex(-1);
                		textCodigo.setText("");
                		limpiarCampos();
                		archivoSeleccionado = listArchivosJava.getSelectedValue().toString();
						contador.buscarClases(archivoSeleccionado);
						for(String clase : contador.getClases()) 
							dmClases.addElement(clase);
					} catch (IOException e) {
						e.printStackTrace();
					}
                }
            }
        });
		
		listClases.addListSelectionListener(new ListSelectionListener() {

            @Override
            public void valueChanged(ListSelectionEvent arg0) {
                if (!arg0.getValueIsAdjusting() && listClases.getSelectedIndex() != -1) {
                	try {
                		dmMetodos.clear();
                		listMetodos.setSelectedIndex(-1);
                		textCodigo.setText("");
                		limpiarCampos();
                		claseSeleccionada = listClases.getSelectedValue().toString();
						contador.buscarMetodos(archivoSeleccionado, claseSeleccionada);
						for(String metodo : contador.getMetodos()) 
							dmMetodos.addElement(metodo);
					} catch (IOException e) {
						e.printStackTrace();
					}
                }
            }
        });
		
		listMetodos.addListSelectionListener(new ListSelectionListener() {

            @Override
            public void valueChanged(ListSelectionEvent arg0) {
            	if (!arg0.getValueIsAdjusting() && listMetodos.getSelectedIndex() != -1) {
                	try {
                		textCodigo.setText("");
                		limpiarCampos();
                		metodoSeleccionado = listMetodos.getSelectedValue().toString();
						contador.buscarCodigo(archivoSeleccionado, claseSeleccionada, metodoSeleccionado);
						textCodigo.setText(contador.getCodigo());
						labelFanIn.setText(contador.getFanIn());
						labelLineasCodigo.setText(String.valueOf(contador.getCantidadLineasCodigo()));
						labelLineasComentadas.setText(String.valueOf(contador.getCantidadComentarios()));
						labelPorcLineasComentadas.setText(String.valueOf(Interfaz.round((double)(contador.getCantidadComentarios()*100)/contador.getCantidadLineasCodigo(), 2)));
					} catch (IOException e) {
						e.printStackTrace();
					}
                }
            }
        });
	}
	
	
	public void limpiarCampos() {
		labelLineasCodigo.setText("");
		labelLineasComentadas.setText("");
		labelPorcLineasComentadas.setText("");
		labelComplejidadCiclomatica.setText("");
		labelFanIn.setText("");
		labelFanOut.setText("");
		labelHasteadLongitud.setText("");
		labelHasteadVolumen.setText("");
	}
	
	public static double round(double value, int places) {
	    if (places < 0) throw new IllegalArgumentException();

	    BigDecimal bd = new BigDecimal(value);
	    bd = bd.setScale(places, RoundingMode.HALF_UP);
	    return bd.doubleValue();
	}
}
