package herramientaTesting;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.ArrayList;

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
	//private String regexMetodo = acceso + modificador + "nombreMetodo\\s*" + parametros;
	private String regexMetodo = "\\s*(public|private)\\s+(final|static|final\\sstatic)?.*nombreMetodo\\s*\\(.*\\).*\\{.*";
	
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
		String rxClase = regexClase.replace("nombreClase", clase);
		String rxMetodo = regexMetodo.replace("nombreMetodo", "[a-zA-Z]+");
		while ((linea = br.readLine()) != null && !linea.matches(rxClase)) { }
		
			while ((linea = br.readLine()) != null) {	
				if(linea.matches(rxMetodo)) 
						metodos.add(linea.split("\\(")[0].replaceFirst("\\s*(public|private)\\s+(final|static|final\\sstatic)?\\s*(.*\\<.*\\>\\s+|[a-zA-Z]+)", "").trim());
				}
		
		br.close();
	}
	
	public void buscarCodigo(String path, String clase, String metodo) throws IOException {
		codigo = "";
		cantidadLineasCodigo = 0;
		cantidadComentarios = 0;
		
		br = new BufferedReader(new FileReader(path));
		
		String linea;
		String rxClase = regexClase.replace("nombreClase", clase);
		String rxMetodo = regexMetodo.replace("nombreMetodo", metodo);
		while ((linea = br.readLine()) != null && !linea.matches(rxClase)) { }
				while ((linea = br.readLine()) != null && !linea.matches(rxMetodo)) { }
					linea = linea.trim();
					codigo = codigo + linea + finDeLinea ;
					
						while ((linea = br.readLine()) != null && !linea.matches("\\s*\\}")) {
							codigo = codigo + linea.trim() + finDeLinea;
							cantidadLineasCodigo++;
							
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

						codigo = codigo + linea.trim() + finDeLinea;

		br.close();
	}

	public void generarArchivoSinComentarios(String entrada, String salida) throws IOException {

		br = new BufferedReader(new FileReader(entrada));
		bw = new BufferedWriter(new FileWriter(salida));

		String linea;
		String lineaModificada;

			while ((linea = br.readLine()) != null) {
				linea = linea.trim();
					
				if (linea.matches(regexComentarios)) { // Hay un comentario
						
					if(!linea.matches(regexMismaLinea) && !linea.matches(regexMismaLineaMultiple)) { // La linea no es sólo un comentario (hay alguna instruccion)
						lineaModificada = linea.replaceFirst(regexMismaLinea + "|" + regexMismaLineaMultiple, ""); // Elimino el contenido del comentario
						bw.write(lineaModificada + finDeLinea);
					}
						
					if (linea.matches(regexComentarioMultipleIni) && !linea.replaceFirst("/\\*", "").matches(regexComentarioMultipleFin)) { // Comienza el conteo de comentarios multiples (/*)
						while ((linea = br.readLine()) != null && !linea.matches(regexComentarioMultipleFin)) {}

						if(linea.matches(regexMismaLineaMultipleFin)) { // La linea no es sólo un comentario (hay alguna instruccion)
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
	
		String linea;
		
		while ((linea = br.readLine()) != null && !linea.matches(metodo)) {}
		
			while ((linea = br.readLine()) != null) {
			linea = linea.trim();
					
				if (linea.matches(regexComentarios)) { // Hay un comentario
					cantidadComentarios++;
												
					if (linea.matches(regexComentarioMultipleIni) && !linea.replaceFirst("/\\*", "").matches(regexComentarioMultipleFin)) { // Comienza el conteo de comentarios multiples (/*)
						while ((linea = br.readLine()) != null && !linea.matches(regexComentarioMultipleFin))
							cantidadComentarios++;
									
						if(linea.matches(regexMismaLineaMultipleFin))  // La linea no es sólo un comentario (hay alguna instruccion)
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