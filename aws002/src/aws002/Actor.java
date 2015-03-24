package aws002;

public class Actor {

	private String nombre;
	private String apellidos;
	private Integer edad;
	private String pais;
	
	
	public Actor()
	{
		this.nombre = "";
		this.apellidos = "";
		this.edad = 0;
		this.pais = "";
	}
	
	public Actor(String nombre, String apellidos,
			Integer edad, String pais) {
		super();
		this.nombre = nombre;
		this.apellidos = apellidos;
		this.edad = edad;
		this.pais = pais;
	}
	
	public String getNombreCompleto(){
		return nombre + " " + apellidos;
	}
	
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getApellidos() {
		return apellidos;
	}
	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
	}
	
	public Integer getEdad() {
		return edad;
	}
	public void setEdad(Integer edad) {
		this.edad = edad;
	}
	public String getPais() {
		return pais;
	}
	public void setPais(String pais) {
		this.pais = pais;
	}
	
	
}
