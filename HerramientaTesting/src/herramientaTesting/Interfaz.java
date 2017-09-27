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
import java.util.ArrayList;

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
import javax.swing.AbstractListModel;
import javax.swing.ListModel;
import javax.swing.JTextArea;

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
	private JTextArea textOperadores;
	private JTextArea textOperandos;
	private TextArea textCodigo;
	
	private JScrollPane scrollPane;
	private JScrollPane scrollPane_1;
	private JScrollPane scrollPane_2;
	private JScrollPane scrollPane_3;
	private JScrollPane scrollPane_4;
	private JLabel lblAnlisisDelMtodo;
	private JLabel lblLneasDeCdigo;
	private JLabel lblLneasDeCdigo_1;
	private JLabel lblLneasDe;
	private JLabel lblComplejidadCiclomtica;
	private JLabel lblFanIn;
	private JLabel lblFanOut;
	private JLabel lblHalsteadVolumen;
	private JLabel lblHalsteadLongitud;
	private JLabel labelLineasCodigo;
	private JLabel labelLineasComentadas;
	private JLabel labelPorcLineasComentadas;
	private JLabel labelComplejidadCiclomatica;
	private JLabel labelFanIn;
	private JLabel labelFanOut;
	private JLabel labelHalsteadLongitud;
	private JLabel labelHalsteadVolumen;
	
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
		setBounds(100, 100, 1400, 800);
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
		
		textOperandos = new JTextArea();
		textOperandos.setForeground(new Color(0, 0, 51));
		textOperandos.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		textOperandos.setBackground(Color.WHITE);
		scrollPane_3 = new JScrollPane();
		scrollPane_3.setBounds(807, 594, 556, 55);
		scrollPane_3.setViewportView(textOperandos);
		contentPane.add(scrollPane_3);
		
		textOperadores = new JTextArea();
		textOperadores.setForeground(new Color(0, 0, 51));
		textOperadores.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		textOperadores.setBackground(Color.WHITE);
		scrollPane_4 = new JScrollPane();
		scrollPane_4.setBounds(807, 675, 556, 55);
		scrollPane_4.setViewportView(textOperadores);
		contentPane.add(scrollPane_4);
		
		
		JMenuBar menuBar = new JMenuBar();
		menuBar.setFont(new Font("Segoe UI", Font.PLAIN, 32));
		menuBar.setBounds(0, 0, 1400, 31);
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
		
		lblAnlisisDelMtodo = new JLabel("An\u00E1lisis del M\u00E9todo");
		lblAnlisisDelMtodo.setForeground(Color.WHITE);
		lblAnlisisDelMtodo.setFont(new Font("Segoe UI", Font.PLAIN, 24));
		lblAnlisisDelMtodo.setBounds(950, 47, 236, 20);
		contentPane.add(lblAnlisisDelMtodo);
		
		lblLneasDeCdigo = new JLabel("L\u00EDneas de c\u00F3digo totales:");
		lblLneasDeCdigo.setForeground(Color.WHITE);
		lblLneasDeCdigo.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		lblLneasDeCdigo.setBounds(675, 105, 178, 31);
		contentPane.add(lblLneasDeCdigo);
		
		lblLneasDeCdigo_1 = new JLabel("L\u00EDneas de c\u00F3digo comentadas:");
		lblLneasDeCdigo_1.setForeground(Color.WHITE);
		lblLneasDeCdigo_1.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		lblLneasDeCdigo_1.setBounds(671, 165, 229, 31);
		contentPane.add(lblLneasDeCdigo_1);
		
		lblLneasDe = new JLabel("% L\u00EDneas de c\u00F3digo comentadas:");
		lblLneasDe.setForeground(Color.WHITE);
		lblLneasDe.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		lblLneasDe.setBounds(674, 225, 229, 31);
		contentPane.add(lblLneasDe);
		
		lblComplejidadCiclomtica = new JLabel("Complejidad Ciclom\u00E1tica:");
		lblComplejidadCiclomtica.setForeground(Color.WHITE);
		lblComplejidadCiclomtica.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		lblComplejidadCiclomtica.setBounds(674, 285, 178, 31);
		contentPane.add(lblComplejidadCiclomtica);
		
		lblFanIn = new JLabel("Fan In:");
		lblFanIn.setForeground(Color.WHITE);
		lblFanIn.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		lblFanIn.setBounds(674, 345, 50, 31);
		contentPane.add(lblFanIn);
		
		lblFanOut = new JLabel("Fan Out:");
		lblFanOut.setForeground(Color.WHITE);
		lblFanOut.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		lblFanOut.setBounds(674, 405, 69, 31);
		contentPane.add(lblFanOut);
		
		lblHalsteadVolumen = new JLabel("Halstead volumen:");
		lblHalsteadVolumen.setForeground(Color.WHITE);
		lblHalsteadVolumen.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		lblHalsteadVolumen.setBounds(673, 525, 140, 31);
		contentPane.add(lblHalsteadVolumen);
		
		lblHalsteadLongitud = new JLabel("Halstead longitud:");
		lblHalsteadLongitud.setForeground(Color.WHITE);
		lblHalsteadLongitud.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		lblHalsteadLongitud.setBounds(672, 465, 141, 31);
		contentPane.add(lblHalsteadLongitud);
		
		labelLineasCodigo = new JLabel("");
		labelLineasCodigo.setHorizontalAlignment(SwingConstants.CENTER);
		labelLineasCodigo.setForeground(new Color(0, 204, 153));
		labelLineasCodigo.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		labelLineasCodigo.setBounds(864, 110, 102, 20);
		contentPane.add(labelLineasCodigo);
		
		labelLineasComentadas = new JLabel("");
		labelLineasComentadas.setHorizontalAlignment(SwingConstants.CENTER);
		labelLineasComentadas.setForeground(new Color(0, 204, 153));
		labelLineasComentadas.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		labelLineasComentadas.setBounds(913, 170, 102, 20);
		contentPane.add(labelLineasComentadas);
		
		labelPorcLineasComentadas = new JLabel("");
		labelPorcLineasComentadas.setHorizontalAlignment(SwingConstants.CENTER);
		labelPorcLineasComentadas.setForeground(new Color(0, 204, 153));
		labelPorcLineasComentadas.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		labelPorcLineasComentadas.setBounds(918, 230, 102, 20);
		contentPane.add(labelPorcLineasComentadas);
		
		labelComplejidadCiclomatica = new JLabel("");
		labelComplejidadCiclomatica.setHorizontalAlignment(SwingConstants.CENTER);
		labelComplejidadCiclomatica.setForeground(new Color(0, 204, 153));
		labelComplejidadCiclomatica.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		labelComplejidadCiclomatica.setBounds(877, 290, 102, 20);
		contentPane.add(labelComplejidadCiclomatica);
		
		labelFanIn = new JLabel("");
		labelFanIn.setHorizontalAlignment(SwingConstants.CENTER);
		labelFanIn.setForeground(new Color(0, 204, 153));
		labelFanIn.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		labelFanIn.setBounds(744, 350, 102, 20);
		contentPane.add(labelFanIn);
		
		labelFanOut = new JLabel("");
		labelFanOut.setHorizontalAlignment(SwingConstants.CENTER);
		labelFanOut.setForeground(new Color(0, 204, 153));
		labelFanOut.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		labelFanOut.setBounds(758, 410, 102, 20);
		contentPane.add(labelFanOut);
		
		labelHalsteadLongitud = new JLabel("");
		labelHalsteadLongitud.setHorizontalAlignment(SwingConstants.CENTER);
		labelHalsteadLongitud.setForeground(new Color(0, 204, 153));
		labelHalsteadLongitud.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		labelHalsteadLongitud.setBounds(812, 470, 102, 20);
		contentPane.add(labelHalsteadLongitud);
		
		labelHalsteadVolumen = new JLabel("");
		labelHalsteadVolumen.setHorizontalAlignment(SwingConstants.CENTER);
		labelHalsteadVolumen.setForeground(new Color(0, 204, 153));
		labelHalsteadVolumen.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		labelHalsteadVolumen.setBounds(816, 530, 102, 20);
		contentPane.add(labelHalsteadVolumen);
		
		JLabel lblOperandos = new JLabel("Operandos:");
		lblOperandos.setForeground(Color.WHITE);
		lblOperandos.setFont(new Font("Segoe UI", Font.PLAIN, 20));
		lblOperandos.setBounds(675, 606, 117, 29);
		contentPane.add(lblOperandos);
		
		JLabel lblOperadores = new JLabel("Operadores:");
		lblOperadores.setForeground(Color.WHITE);
		lblOperadores.setFont(new Font("Segoe UI", Font.PLAIN, 20));
		lblOperadores.setBounds(675, 682, 117, 29);
		contentPane.add(lblOperadores);

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
                		textOperadores.setText("");
                		textOperandos.setText("");
                		limpiarCampos();
                		metodoSeleccionado = listMetodos.getSelectedValue().toString();
						contador.buscarCodigo(archivoSeleccionado, claseSeleccionada, metodoSeleccionado);
						ArrayList<Float> halstead = (ArrayList<Float>) contador.getComplejidadYHalstead(archivoSeleccionado, claseSeleccionada, metodoSeleccionado);
						textCodigo.setText(contador.getCodigo());
						labelFanIn.setText(contador.getFanIn());
						labelLineasCodigo.setText(String.valueOf(contador.getCantidadLineasCodigo()));
						labelLineasComentadas.setText(String.valueOf(contador.getCantidadComentarios()));
						labelPorcLineasComentadas.setText(String.valueOf(Interfaz.round((double)(contador.getCantidadComentarios()*100)/contador.getCantidadLineasCodigo(), 2)));
						labelComplejidadCiclomatica.setText( String.valueOf(Math.round(halstead.get(0))));
						labelHalsteadLongitud.setText(String.valueOf(Math.round(halstead.get(1)))  );
						labelHalsteadVolumen.setText(String.valueOf((double) Math.round(halstead.get(2) * 100) / 100));
						
						for(String o : contador.getOperadores()) {
							textOperadores.append("   " + o);
						}
						
						for(String o : contador.getOperandos()) {
							textOperandos.append("   " + o);
						}
						
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
		labelHalsteadLongitud.setText("");
		labelHalsteadVolumen.setText("");
	}
	
	public static double round(double value, int places) {
	    if (places < 0) throw new IllegalArgumentException();

	    BigDecimal bd = new BigDecimal(value);
	    bd = bd.setScale(places, RoundingMode.HALF_UP);
	    return bd.doubleValue();
	}
}
