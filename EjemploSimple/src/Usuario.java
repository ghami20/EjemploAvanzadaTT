import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.LinkedList;

import javax.swing.JOptionPane;

public class Usuario implements Encriptador {
    protected int id;
    protected String nombre;
    protected String email;
    protected String tipo;
    protected String password;
	private static Connection con = Conexion.getInstance().getConnection();

    public Usuario(int id, String nombre, String email,String tipo,String password) {
        this.id = id;
        this.nombre = nombre;
        this.email = email;
        this.tipo = tipo;
        this.password = password;
        
    }
    public Usuario(String nombre, String email,String tipo,String password) {
        this.nombre = nombre;
        this.email = email;
        this.tipo = tipo;
        this.password = password;
        
    }
    public Usuario() {
      
    }
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getTipo() {
		return tipo;
	}
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	@Override
	public String toString() {
		return "Usuario [id=" + id + ", nombre=" + nombre + ", email=" + email + ", tipo=" + tipo + ", password="
				+ password + "]";
	}
	public static Usuario login(String nombre, String password) {
		Usuario usuario = new Usuario();
		try {
			PreparedStatement stmt = con.prepareStatement("SELECT * FROM usuario WHERE nombre = ? AND password = ?");
			stmt.setString(1, nombre);
			stmt.setString(2, usuario.encriptar(password));

			ResultSet rs = stmt.executeQuery();

			if (rs.next()) {
				int id = rs.getInt("id");
				String email = rs.getString("email");
				String tipo = rs.getString("tipo");
				String contrasenia = rs.getString("password");
				

				usuario = new Usuario(id, nombre, email, tipo, usuario.desencriptar(contrasenia));

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return usuario;
	}

	public static void agregarUsuario(Usuario usuario) {
		try {
			PreparedStatement statement = con
					.prepareStatement("INSERT INTO usuario (nombre, email, tipo, password) VALUES (?, ?, ?, ?)");
			statement.setString(1, usuario.getNombre());
			statement.setString(2, usuario.getEmail());
			statement.setString(3, usuario.getTipo());
			statement.setString(4,usuario.encriptar(usuario.getPassword()));

			int filas = statement.executeUpdate();
			if (filas > 0) {
				System.out.println("Usuario agregado correctamente.");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void Registrarse(Usuario nuevo) {
		LinkedList<Usuario> creados = mostrarUsuarios();
		for (Usuario creado : creados) {
			if (creado.getEmail().equals(nuevo.email)) {
				JOptionPane.showMessageDialog(null, "Usuario creado, con este mismo mail");
				return;
			}
		}
		agregarUsuario(nuevo);
		
	}
	
	public static LinkedList<Usuario> mostrarUsuarios() {
		LinkedList<Usuario> usuarios = new LinkedList<>();
		try {
			PreparedStatement stmt = con.prepareStatement("SELECT * FROM usuario");
			ResultSet rs = stmt.executeQuery();

			while (rs.next()) {
				int id = rs.getInt("id");
				String nombre = rs.getString("nombre");
				String email = rs.getString("email");
				String tipo = rs.getString("tipo");
				String password = rs.getString("password");
				usuarios.add(new Usuario(id, nombre, email, tipo, password));

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return usuarios;
	}
	
	
    

}
