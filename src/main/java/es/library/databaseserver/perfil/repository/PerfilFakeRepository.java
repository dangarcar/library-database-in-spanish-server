package es.library.databaseserver.perfil.repository;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Repository;

import es.library.databaseserver.perfil.Perfil;

@Repository("fake")
public class PerfilFakeRepository implements PerfilRepository {
	
	private final List<Perfil> perfilesList = new ArrayList<>();
	
	@Override
	public List<Perfil> getAllPerfiles() throws SQLException {
		return perfilesList;
	}

	@Override
	public Perfil getPerfilByDNI(int DNI) throws SQLException {
//		return GetPerfil(DNI);
		return perfilesList.stream().filter(p -> p.getDNI().equals(DNI)).collect(Collectors.toList()).get(0);
	}

	@Override
	public void insertPerfil(Perfil perfil) throws SQLException {
		perfilesList.add(perfil);
//		WritePerfil(perfil);
	}

	@Override
	public void deletePerfilByDNI(int DNI) throws SQLException {
		perfilesList.remove(getPerfilByDNI(DNI));
//		DeletePerfil(getPerfilByDNI(DNI));
	}

	@Override
	public void updatePerfilByDNI(int DNI, Perfil perfil) throws SQLException {
		int index = perfilesList.indexOf(getPerfilByDNI(DNI));
		perfilesList.set(index, perfil);
//		perfil.setDNI(DNI);
//		WritePerfil(perfil);
	}
	
	@Bean
    public CommandLineRunner run(String...args) throws Exception {
        return p -> {
        	perfilesList.add(new Perfil("Perico", "De los Palotes", LocalDate.of(2000, 3, 5), 12345678, "Calle de República Checa n45", "perico.palos@gmail.com"));
        	perfilesList.add(new Perfil("Dolores", "Fuertes de Barriga", LocalDate.of(1956, 4, 7), 33333333, "Plaza de España n23", "dolores.fuertes@gmail.com"));
        };
    }
    
	/*public List<Perfil> getAllPerfil() throws SQLException{
		List<Perfil> perfiles = new ArrayList<>();
		Perfil perfil = null;
		ResultSet resultado = null;
		ConectorSQL conector = null;
	
		String nombre;
		String apellidos;
		LocalDate fechaDeNacimiento = null;
		int DNI;
		String direccionCasa;
		String correoElectronico;
	
		String peticionSQL = "SELECT Nombre,Apellidos,FechaDeNacimiento,DNI,DireccionCasa,CorreoElectronico,ContenidoEnPrestamo FROM Perfiles;";
		
		try {
			//Creo un objeto con el que conectarme a la base de datos y me conecto a la base de datos
			conector = new ConectorSQL();
			conector.conectar();
			
			//Hago al peticion SQL a la base de datos y almaceno el ResultSet
			resultado = conector.seleccionar(peticionSQL);
		
			//Saco las variables a partir de el ResultSet SQL
			nombre = resultado.getString("Nombre");
			apellidos = resultado.getString("Apellidos");
			try{
				DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
				fechaDeNacimiento = LocalDate.parse(resultado.getString("FechaDeNacimiento"),formatter);
			} catch(DateTimeParseException e) {
				System.out.println("¿Eres admin?");
			}
			DNI = resultado.getInt("DNI");
			direccionCasa = resultado.getString("DireccionCasa");
			correoElectronico = resultado.getString("CorreoElectronico");

			perfil = new Perfil(nombre,apellidos,fechaDeNacimiento,DNI,direccionCasa,correoElectronico);
			perfil.setContenidosEnPrestamo((inputEnPrestamo(resultado.getString("ContenidoEnPrestamo")) != null)? inputEnPrestamo(resultado.getString("ContenidoEnPrestamo")):new ArrayList<Integer>());
			
			if(perfil != null) perfiles.add(perfil);
		} finally {
			//Si la conexion a la base de datos existe, la cierro
			if(conector != null) {
				conector.cerrar();
			}
		}
	
		return perfiles;
	}
	
	public Perfil GetPerfil(int dni) throws SQLException{
		Perfil perfil = null;
		ResultSet resultado = null;
		ConectorSQL conector = null;
		
		String nombre;
		String apellidos;
		LocalDate fechaDeNacimiento = null;
		int DNI;
		String direccionCasa;
		String correoElectronico;
		
		String peticionSQL = "SELECT Nombre,Apellidos,FechaDeNacimiento,DNI,DireccionCasa,CorreoElectronico,ContenidoEnPrestamo FROM Perfiles WHERE DNI = "+dni+";";
		
		try {
			//Creo un objeto con el que conectarme a la base de datos y me conecto a la base de datos
			conector = new ConectorSQL();
			conector.conectar();
			
			//Hago al peticion SQL a la base de datos y almaceno el ResultSet
			resultado = conector.seleccionar(peticionSQL);
			
			//Saco las variables a partir de el ResultSet SQL
			nombre = resultado.getString("Nombre");
			apellidos = resultado.getString("Apellidos");
			try{
				DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
				fechaDeNacimiento = LocalDate.parse(resultado.getString("FechaDeNacimiento"),formatter);
			} catch(DateTimeParseException e) {
				System.out.println("¿Eres admin?");
			}
			DNI = resultado.getInt("DNI");
			direccionCasa = resultado.getString("DireccionCasa");
			correoElectronico = resultado.getString("CorreoElectronico");

			perfil = new Perfil(nombre,apellidos,fechaDeNacimiento,DNI,direccionCasa,correoElectronico);
			perfil.setContenidosEnPrestamo((inputEnPrestamo(resultado.getString("ContenidoEnPrestamo")) != null)? inputEnPrestamo(resultado.getString("ContenidoEnPrestamo")):new ArrayList<Integer>());
			
		} finally {
			//Si la conexion a la base de datos existe, la cierro
			if(conector != null) {
				conector.cerrar();
			}
		}
		
		return perfil;
	}
	
	public void WritePerfil(Perfil perfil) throws SQLException {
		ConectorSQL conector = null;
		PreparedStatement st;
		
		if (perfil != null) {
			try{
				//Me conecto a la base de datos
				conector = new ConectorSQL();
				Connection connect = conector.conectar();
				
				DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
				String peticionSQL = "INSERT INTO Perfiles(Nombre,Apellidos,FechaDeNacimiento,DNI,DireccionCasa,CorreoElectronico,ContenidoEnPrestamo) VALUES(?,?,?,?,?,?,?);";
			
				st = connect.prepareStatement(peticionSQL);
				st.setString(1, perfil.getNombre());
				st.setString(2, perfil.getApellido());
				st.setString(3, (perfil.getFechaNacimiento() != null)? perfil.getFechaNacimiento().format(formatter):null);
				st.setInt(4, perfil.getDNI());
				st.setString(5, perfil.getDireccionDeCasa());
				st.setString(6, perfil.getCorreoElectronico());
				st.setString(7, outputEnPrestamo(perfil.getContenidosEnPrestamo()));
				st.executeUpdate();
				st.clearParameters();
				
				//System.out.println("Se ha guardado el perfil en la base de datos");
			
			} finally {
				//Si la conexión a la base de datos existe, la cierro
				if(conector != null) {
					conector.cerrar();
				}
			}
		}
	}
	
	public static void DeletePerfil(Perfil perfil) throws SQLException{
		ConectorSQL conector = null;
		PreparedStatement st;
		
		if (perfil != null) {
			try{
				//Me conecto a la base de datos
				conector = new ConectorSQL();
				Connection connect = conector.conectar();
				
				String peticionSQL = "DELETE FROM Perfiles WHERE DNI = (?);"; 
			
				st = connect.prepareStatement(peticionSQL);
				st.setInt(1, perfil.getDNI());
				st.executeUpdate();
				st.clearParameters();
				
				//System.out.println("Se ha borrado el perfil de la base de datos");
			
			} finally {
				//Si la conexión a la base de datos existe, la cierro
				if(conector != null) {
					conector.cerrar();
				}
			}
		}
	}
	
	public static ArrayList<Integer> inputEnPrestamo(String array){
		Gson gson = new Gson();
		Type type = new TypeToken<ArrayList<Integer>>() {}.getType();
		ArrayList<Integer> a = gson.fromJson(array, type);
		return a;
	}
	
	public static String outputEnPrestamo(ArrayList<Integer> p){
		Gson gson = new Gson();
		String outputString = gson.toJson(p);
		return outputString;
	}*/
}


/*
class ConectorSQL {
	private Connection connect = null;
	public Connection conectar(){
		try {
			Class.forName("org.sqlite.JDBC");
            connect = DriverManager.getConnection("jdbc:sqlite:src/main/java/es/library/databaseserver/database.db");
		} catch (SQLException | ClassNotFoundException e) {
			JOptionPane.showMessageDialog(null, "NO se ha podido establecer conexión con la base de datos");
			e.printStackTrace();
		}
		return connect;
	}
	
	
	public void cerrar(){
		try {
			connect.close();
			//JOptionPane.showMessageDialog(null, "Se ha cerrado la conexión con la base de datos");
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "No se ha podido cerrar la conexión con la base de datos");
			e.printStackTrace();
		}
	}
	
	public ResultSet seleccionar(String sentenciaSQL) {
		ResultSet resultado = null;
		try {
			Statement st = connect.createStatement();
			resultado = st.executeQuery(sentenciaSQL);
		
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "No se ha podido realizar la peticion correctamente");
			e.printStackTrace();
		}
		return resultado;
	}
}*/