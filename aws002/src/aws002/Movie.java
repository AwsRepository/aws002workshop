package aws002;

import java.util.ArrayList;
import java.util.List;


public class Movie {

	
	private String titulo;
	private String director;
	private Integer fecha;
	private Integer duracion;
	private List<Actor> actores;

	
	public Movie() {
		titulo=new String();
		director=new String();
		fecha= new Integer(0);
		duracion= new Integer(0);
		actores = new ArrayList<Actor>();
	}

	public Movie(String titulo, String director, Integer fecha, Integer duracion, List<Actor> actores) {
		super();
		this.titulo = titulo;
		this.director = director;
		this.fecha = fecha;
		this.duracion = duracion;
		this.actores = actores;
	}


	public String getTitulo() {
		return titulo;
	}
	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}
	public String getDirector() {
		return director;
	}
	public void setDirector(String director) {
		this.director = director;
	}
	public Integer getFecha() {
		return fecha;
	}
	public void setFecha(Integer fecha) {
		this.fecha = fecha;
	}
	public Integer getDuracion() {
		return duracion;
	}
	public void setDuracion(Integer duracion) {
		this.duracion = duracion;
	}
	public List<Actor> getActores() {
		return actores;
	}

	public void setActores(List<Actor> actores) {
		this.actores = actores;
	}
	
	public void addActor(Actor a){
		if (this.actores != null)
			this.actores.add(a);
		else
		{
			this.actores = new ArrayList<Actor>();
			this.actores.add(a);
		}
	}
}
