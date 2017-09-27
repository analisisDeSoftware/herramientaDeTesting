package herramientaTesting;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;

public class HerramientaTesting {
	
	private String archivoTemporal = "SinComentarios";
	private String finDeLinea = System.getProperty("line.separator");
	
	private ArrayList<String> archivosJava = new ArrayList<String>();
	private ArrayList<String> clases = new ArrayList<String>();
	private ArrayList<String> metodos = new ArrayList<String>();
	private String codigo;
	private ArrayList<String> archivosSinComentarios = new ArrayList<String>();
	private int cantidadArchivosJava = 0;
	
	private BufferedReader br;
	private BufferedWriter bw;
	
	private int cantidadLineasCodigo = 0;
	private int cantidadComentarios = 0;
	private int cantidadClases = 0;
	private int cantidadMetodos = 0;
	private int complejidadCiclomatica = 0;
	private int nodosPredicado = 0;
	private int cantOperadores = 0;
	private int cantOperandos = 0;
	private int longitud = 0;
	private int volumen = 0;
	
	private HashSet<String> operadores = new LinkedHashSet<String>();
	private HashSet<String> operandos  = new LinkedHashSet<String>();
	
	// Expresiones regulares 
	
	private String regexComentarios = "(.*//.*)|(.*/\\*.*)";
	private String regexComentarioMultipleIni = "[^//]*/\\*.*";
	private String regexComentarioMultipleFin = ".*\\*/.*";
	private String regexMismaLinea = "//.*";
	private String regexMismaLineaMultiple = "/\\*.*";
	private String regexMismaLineaMultipleFin = ".*\\*/(\\s*)?\\S*";
	
	private String acceso = "\\s*(public|private)\\s+";
	private String modificador = "(final|static|final\\sstatic)?.*";
	private String parametros = "\\(.*\\)\\s*(throws\\s+[a-zA-Z]+)?\\s*\\{.*";
	
	private String regexClase = ".*class\\s+nombreClase.*";
	private String regexMetodo = "\\s*(public|private)\\s+(final|static|final\\sstatic)?.*nombreMetodo\\s*\\(.*\\).*\\{.*";
	
	
	private String regexNodosPredicado = "for.*|if.*|switch.*|while.*";
	
	private String palabrasReservadas = "(abstract|assert|boolean|break|byte|case|catch|char|char\\[.*\\]|class|const|continue|default|do|double|else|enum|"
			+ "|extends|false|final|finally|float|float\\[.*\\]|goto|implements|import|instanceof|int|int\\[.*\\]|interface|long|long\\[.*\\]|native|new|package|private|protected|public|"
			+ "|return|short|short\\[.*\\]|static|strictfp|String|super|synchronized|this|throw|throws|transient|true|try|void|volatile)" + ";?";
	
	private String operadoresAritmeticos = "|\\+|-|\\*|/";
	
	private String operadoresLogicos = "|&&|\\|\\||>|<|>=|<=|==|\\!=";
	
	private String regexOperadores = palabrasReservadas + operadoresAritmeticos + operadoresLogicos + "|=";
	
	private String regexID = "[a-zA-Z](\\w|_)*(;|\\[.*\\]|,|\\.)?";
	
	private String regexOperandos = regexID;

	
	public void buscarArchivosJava(File file) {
		File[] java = file.listFiles(new FilenameFilter() {
		    public boolean accept(File dir, String name) {
		        return name.endsWith("java");
		    }
		});
		
		for(File f : java)
			archivosJava.add(f.getPath());
		
	    File[] children = file.listFiles();
	    for (File child : children) {
	    	if(child.isDirectory())
	    		buscarArchivosJava(child);
	    }
	}
	
	public void buscarClases(String path) throws IOException {
		clases.clear();
		br = new BufferedReader(new FileReader(path));
		
		String linea;
		String rxClase = regexClase.replace("nombreClase", "");
		while ((linea = br.readLine()) != null) {
			if(linea.matches(rxClase)) 
				clases.add(linea.replaceFirst(".*class\\s+", "").split(" ")[0]);
		}
		
		br.close();
	}
	
	public void buscarMetodos(String path, String clase) throws IOException {
		metodos.clear();
		br = new BufferedReader(new FileReader(path));
		
		String linea;
		String nombreClase;
		String rxClase = regexClase.replace("nombreClase", clase);
		String rxMetodo = regexMetodo.replace("nombreMetodo", "[a-zA-Z]+");
		
		while ((linea = br.readLine()) != null && !linea.matches(rxClase)) { }
		while ((linea = br.readLine()) != null) {	
			if(linea.matches(rxMetodo)){
				nombreClase = getNombreMetodo(linea);
				if(!nombreClase.equals("") && !metodos.contains(nombreClase)){
					metodos.add(nombreClase);
				}
			}
		}

		br.close();
	}
	
	public void buscarCodigo(String path, String clase, String metodo) throws IOException {
		codigo = "";
		cantidadLineasCodigo = 0;
		cantidadComentarios = 0;
		
		br = new BufferedReader(new FileReader(path));
		int contadorDeLlaves = 0;
		boolean firstTime = true;
		String linea;
		String rxClase = regexClase.replace("nombreClase", clase);
		String rxMetodo = regexMetodo.replace("nombreMetodo", metodo);

		while ((linea = br.readLine()) != null && !linea.matches(rxClase)) { }
				while ((linea = br.readLine()) != null && !linea.matches(rxMetodo)) { }
					linea = linea.trim();
					codigo = codigo + linea + finDeLinea;
					
					if(linea.contains("{")) 
						contadorDeLlaves++;
					
					if (linea.matches("\\s*\\}")) 
						contadorDeLlaves--;
					
						
						while ((linea = br.readLine()) != null && contadorDeLlaves != 0 || firstTime ) {
							firstTime = false;
							codigo = codigo + linea.trim() + finDeLinea;
								cantidadLineasCodigo++;
							
							if(linea.contains("{")) 
								contadorDeLlaves++;
							
							if (linea.contains("}")) 
								contadorDeLlaves--;
							
							if (linea.matches(regexComentarios)) { // Hay un comentario
								cantidadComentarios++;
															
								if (linea.matches(regexComentarioMultipleIni) && !linea.replaceFirst("/\\*", "").matches(regexComentarioMultipleFin)) { // Comienza el conteo de comentarios multiples (/*)
									while ((linea = br.readLine()) != null && !linea.matches(regexComentarioMultipleFin)) {
										cantidadComentarios++;
										cantidadLineasCodigo++;
										codigo = codigo + linea.trim() + finDeLinea;
									}
										
									cantidadLineasCodigo++;
									cantidadComentarios++;
									codigo = codigo + linea.trim() + finDeLinea;
								}
							}
						}
						if(linea != null) {
							codigo = codigo + linea.trim() + finDeLinea;
						}
				
		cantidadLineasCodigo--;
		br.close();
	}
	
	public List<Float> getComplejidadYHalstead(String path, String clase, String metodo) throws IOException {
		
		nodosPredicado = 0;
		operadores.clear();
		operandos.clear();
		cantOperadores = 0;
		cantOperandos = 0;
		
		br = new BufferedReader(new FileReader(path));
		int contadorDeLlaves = 0;
		String linea;
		String rxClase = regexClase.replace("nombreClase", clase);
		String rxMetodo = regexMetodo.replace("nombreMetodo", metodo);
		boolean firstTime = true;
		
		while ((linea = br.readLine()) != null && !linea.matches(rxClase)) { }
			while ((linea = br.readLine()) != null && !linea.matches(rxMetodo)) { }
					
				if(linea.contains("{")) 
					contadorDeLlaves++;
					
				if (linea.matches("\\s*\\}")) 
					contadorDeLlaves--;
				
					while ((linea = br.readLine()) != null && contadorDeLlaves != 0 || firstTime ) {
						firstTime = false;
						linea = linea.replaceAll("\t", "");
						linea = linea.trim();
						
						if(linea.contains("{")) 
							contadorDeLlaves++;
						
						if (linea.contains("}")) 
							contadorDeLlaves--;
		
						if (linea.matches(regexComentarios)) { // Hay un comentario
							
							if(!linea.matches(regexMismaLinea) && !linea.matches(regexMismaLineaMultiple)) { // La linea no es s�lo un comentario (hay alguna instruccion)
								linea = linea.replaceFirst(regexMismaLinea + "|" + regexMismaLineaMultiple, ""); // Elimino el contenido del comentario
								getOperandosYOperadores(linea);
							}
								
							if (linea.matches(regexComentarioMultipleIni) && !linea.replaceFirst("/\\*", "").matches(regexComentarioMultipleFin)) { 
								while ((linea = br.readLine()) != null && !linea.matches(regexComentarioMultipleFin)) {
									linea = linea.replaceFirst(".*\\*/\\s*", ""); // Elimino el contenido del comentario
									getOperandosYOperadores(linea);
								}
							}
							
						} else {
							getOperandosYOperadores(linea);
						}
					}

					br.close();
					
		float complejidad = nodosPredicado + 1;
		float longitud = cantOperadores + cantOperandos;
		int n = operadores.size() + operandos.size();
		float volumen = longitud * (float)(Math.log(n)/Math.log(2));
		
		return new ArrayList<Float>(Arrays.asList(complejidad, longitud, volumen));
	}
	
	private void getOperandosYOperadores(String linea) {
		System.out.println(linea);
		String token;
		ArrayList<String> tokens;
		int operadoresLogicos;
		if(linea.matches(regexNodosPredicado)) {
			operadoresLogicos = 1;
			if(linea.contains("{")) { operadores.add("{"); operadores.add("}"); cantOperadores++; }
			
			token = linea.replaceAll("!(for\\(.*\\)|if\\(.*\\)|switch\\(.*\\)|while\\(.*\\))", "");
			
			tokens = new ArrayList<String>(Arrays.asList(linea.split(" ")));
			
			for(String t : tokens) 
				if(t.matches("&&|\\|\\|"))
					operadoresLogicos++;
			
			nodosPredicado += operadoresLogicos;
			
			token = token.replaceAll("\\(.*", "");
			token = token.trim();
			operadores.add(token);
			cantOperadores++;
		}
		else {
			linea = linea.replaceAll("\\(.*\\)", "\\(\\)");
			tokens = new ArrayList<String>(Arrays.asList(linea.split(" ")));
			for(String t : tokens) {
				
				if(t.matches(regexOperadores)) {
					t = t.replaceAll("\\[.*\\]", "");
					t = t.replace(";", "");
					t = t.replace(".", "");
					t = t.trim();
					operadores.add(t);
					cantOperadores++;
			
				} else {
					
					if(t.matches(regexOperandos) && !t.equals("null")) {
						t = t.replaceAll("\\[.*\\]", "");
						t = t.replace(";", "");
						t = t.replace(",", "");
						t = t.trim();
						operandos.add(t);
						cantOperandos++;
					}
					
					if(t.contains("[")) { operadores.add("["); operadores.add("]"); cantOperadores++; }
					if(t.contains("(")) { operadores.add("("); operadores.add(")"); cantOperadores++; }
					if(t.contains("{")) { operadores.add("{"); operadores.add("}"); cantOperadores++; }
					if(t.contains("++")) { operadores.add("++"); cantOperadores++; }
					if(t.contains("--")) { operadores.add("--"); cantOperadores++; }
					
				} 
			}
		}
	}
	
	public HashSet<String> getOperandos() {
		return operandos;
	}
	
	public HashSet<String> getOperadores() {
		return operadores;
	}
	
	public String getFanIn() {
	int cantidad = 0;
		for (String metodo : metodos) {
			if(metodo.length() != 0) {
				cantidad += Math.ceil((codigo.length() - codigo.replaceAll(metodo, "").length())/metodo.length());
			}
		}
		return String.valueOf(cantidad);
	}

	public void generarArchivoSinComentarios(String entrada, String salida) throws IOException {

		br = new BufferedReader(new FileReader(entrada));
		bw = new BufferedWriter(new FileWriter(salida));

		String linea;
		String lineaModificada;

			while ((linea = br.readLine()) != null) {
				linea = linea.trim();
					
				if (linea.matches(regexComentarios)) { // Hay un comentario
						
					if(!linea.matches(regexMismaLinea) && !linea.matches(regexMismaLineaMultiple)) { // La linea no es s�lo un comentario (hay alguna instruccion)
						lineaModificada = linea.replaceFirst(regexMismaLinea + "|" + regexMismaLineaMultiple, ""); // Elimino el contenido del comentario
						bw.write(lineaModificada + finDeLinea);
					}
						
					if (linea.matches(regexComentarioMultipleIni) && !linea.replaceFirst("/\\*", "").matches(regexComentarioMultipleFin)) { // Comienza el conteo de comentarios multiples (/*)
						while ((linea = br.readLine()) != null && !linea.matches(regexComentarioMultipleFin)) {}

						if(linea.matches(regexMismaLineaMultipleFin)) { // La linea no es s�lo un comentario (hay alguna instruccion)
							lineaModificada = linea.replaceFirst(".*\\*/\\s*", ""); // Elimino el contenido del comentario
							bw.write(lineaModificada + finDeLinea);
						}
					}
				}

				else
					bw.write(linea + finDeLinea);
			}
				
		br.close();
		bw.close();
	}
	
	public void buscarComentarios(String path, String metodo) throws IOException {
	
		br = new BufferedReader(new FileReader(path));
		int contadorDeLlaves = 0;
		boolean firstTime = true;
		String linea;
		
		
		while ((linea = br.readLine()) != null && !linea.matches(metodo)) {}
		
			if(linea.contains("{")) 
				contadorDeLlaves++;
	
			if (linea.matches("\\s*\\}")) 
				contadorDeLlaves--;
			
			while ((linea = br.readLine()) != null && contadorDeLlaves != 0 || firstTime ) {
				firstTime = false;
				codigo = codigo + linea.trim() + finDeLinea;
				cantidadLineasCodigo++;
				
				if(linea.contains("{")) 
					contadorDeLlaves++;
	
				if (linea.contains("}")) 
					contadorDeLlaves--;
	
				linea = linea.trim();
					
				if (linea.matches(regexComentarios)) { // Hay un comentario
					cantidadComentarios++;
												
					if (linea.matches(regexComentarioMultipleIni) && !linea.replaceFirst("/\\*", "").matches(regexComentarioMultipleFin)) { // Comienza el conteo de comentarios multiples (/*)
						while ((linea = br.readLine()) != null && !linea.matches(regexComentarioMultipleFin))
							cantidadComentarios++;
									
						if(linea.matches(regexMismaLineaMultipleFin))  // La linea no es s�lo un comentario (hay alguna instruccion)
							if(linea.replaceFirst(".*\\*/\\s*", "").matches(".*;")) // Fin de la clase
								break; 
							
						cantidadComentarios++;
					}
				}
			}
			
			br.close();
	}
	
	public void sinComentarios() throws IOException {
		 br = new BufferedReader(new FileReader(archivoTemporal));
		 
		 String linea;
		 while((linea = br.readLine()) != null) {
				
				if(linea.matches(regexClase))
					cantidadClases++;
				
				if(linea.matches(regexMetodo))
					cantidadMetodos++;
			}
		 
		 br.close();
	}
	
	public String getNombreMetodo(String metodo) {
		metodo = new StringBuilder(metodo).reverse().toString();
		metodo = (metodo.split("\\(")[1].trim()).split(" ")[0];
		return new StringBuilder(metodo).reverse().toString();
	}

	
	
	
	
	// Getters & Setters
	
	
	public String getArchivoTemporal() {
		return archivoTemporal;
	}

	public void setArchivoTemporal(String archivoTemporal) {
		this.archivoTemporal = archivoTemporal;
	}

	public String getFinDeLinea() {
		return finDeLinea;
	}

	public void setFinDeLinea(String finDeLinea) {
		this.finDeLinea = finDeLinea;
	}

	public ArrayList<String> getArchivosJava() {
		return archivosJava;
	}

	public void setArchivosJava(ArrayList<String> archivosJava) {
		this.archivosJava = archivosJava;
	}

	public ArrayList<String> getClases() {
		return clases;
	}

	public void setClases(ArrayList<String> clases) {
		this.clases = clases;
	}

	public ArrayList<String> getMetodos() {
		return metodos;
	}

	public void setMetodos(ArrayList<String> metodos) {
		this.metodos = metodos;
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public ArrayList<String> getArchivosSinComentarios() {
		return archivosSinComentarios;
	}

	public void setArchivosSinComentarios(ArrayList<String> archivosSinComentarios) {
		this.archivosSinComentarios = archivosSinComentarios;
	}

	public int getCantidadArchivosJava() {
		return cantidadArchivosJava;
	}

	public void setCantidadArchivosJava(int cantidadArchivosJava) {
		this.cantidadArchivosJava = cantidadArchivosJava;
	}

	public BufferedReader getBr() {
		return br;
	}

	public void setBr(BufferedReader br) {
		this.br = br;
	}

	public BufferedWriter getBw() {
		return bw;
	}

	public void setBw(BufferedWriter bw) {
		this.bw = bw;
	}

	public int getCantidadLineasCodigo() {
		return cantidadLineasCodigo;
	}

	public void setCantidadLineasCodigo(int cantidadLineasCodigo) {
		this.cantidadLineasCodigo = cantidadLineasCodigo;
	}

	public int getCantidadComentarios() {
		return cantidadComentarios;
	}

	public void setCantidadComentarios(int cantidadComentarios) {
		this.cantidadComentarios = cantidadComentarios;
	}

	public int getCantidadClases() {
		return cantidadClases;
	}

	public void setCantidadClases(int cantidadClases) {
		this.cantidadClases = cantidadClases;
	}

	public int getCantidadMetodos() {
		return cantidadMetodos;
	}

	public void setCantidadMetodos(int cantidadMetodos) {
		this.cantidadMetodos = cantidadMetodos;
	}

	public String getRegexComentarios() {
		return regexComentarios;
	}

	public void setRegexComentarios(String regexComentarios) {
		this.regexComentarios = regexComentarios;
	}

	public String getRegexComentarioMultipleIni() {
		return regexComentarioMultipleIni;
	}

	public void setRegexComentarioMultipleIni(String regexComentarioMultipleIni) {
		this.regexComentarioMultipleIni = regexComentarioMultipleIni;
	}

	public String getRegexComentarioMultipleFin() {
		return regexComentarioMultipleFin;
	}

	public void setRegexComentarioMultipleFin(String regexComentarioMultipleFin) {
		this.regexComentarioMultipleFin = regexComentarioMultipleFin;
	}

	public String getRegexMismaLinea() {
		return regexMismaLinea;
	}

	public void setRegexMismaLinea(String regexMismaLinea) {
		this.regexMismaLinea = regexMismaLinea;
	}

	public String getRegexMismaLineaMultiple() {
		return regexMismaLineaMultiple;
	}

	public void setRegexMismaLineaMultiple(String regexMismaLineaMultiple) {
		this.regexMismaLineaMultiple = regexMismaLineaMultiple;
	}

	public String getRegexMismaLineaMultipleFin() {
		return regexMismaLineaMultipleFin;
	}

	public void setRegexMismaLineaMultipleFin(String regexMismaLineaMultipleFin) {
		this.regexMismaLineaMultipleFin = regexMismaLineaMultipleFin;
	}

	public String getAcceso() {
		return acceso;
	}

	public void setAcceso(String acceso) {
		this.acceso = acceso;
	}

	public String getModificador() {
		return modificador;
	}

	public void setModificador(String modificador) {
		this.modificador = modificador;
	}

	public String getParametros() {
		return parametros;
	}

	public void setParametros(String parametros) {
		this.parametros = parametros;
	}

	public String getRegexClase() {
		return regexClase;
	}

	public void setRegexClase(String regexClase) {
		this.regexClase = regexClase;
	}

	public String getRegexMetodo() {
		return regexMetodo;
	}

	public void setRegexMetodo(String regexMetodo) {
		this.regexMetodo = regexMetodo;
	}
}