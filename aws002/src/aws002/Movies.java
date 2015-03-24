package aws002;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Movies {

	//Esto es una clase que nos servira para trabajar con la lista de peliculas 
	//Para evitar trabajar constantemente con la BBDD, tenemos una lista de peliculas
	//para trabajar de un modo mas rapido.
	
	
	// Deprecated
	public static List<Movie> lista() {
		List<Movie> res = new LinkedList<Movie>();

		List<Actor> a = new ArrayList<Actor>();
		Movie b = new Movie("Shutter Island", "Martin Scorsese", new Integer(
				2010), new Integer(138), a);
		res.add(b);

		List<Actor> a2 = new ArrayList<Actor>();
		a2.add(new Actor("Brad", "Pitt", 51, "EEUU"));
		a2.add(new Actor("Kevin", "Spacey", 55, "EEUU"));
		Movie b2 = new Movie("Seven", "David Fincher", new Integer(1995),
				new Integer(127), a2);
		res.add(b2);

		List<Actor> a3 = new ArrayList<Actor>();
		Movie b3 = new Movie("El club de la lucha", "David Fincher",
				new Integer(1999), new Integer(139), a3);
		res.add(b3);

		return res;

	}

	//Comprueba si hay una pelicula con el nombre n
	public static boolean containsName(List<Movie> l, String n) {
		boolean res = false;
		if (l != null)
			for (Movie m : l) {
				if (Movies.compareNameCamel(m, n)) {
					res = true;
				}
		
			}
		return res;
	}

	//Devuelve la pelicula de la lista que contenta el nombre n
	public static Movie getMovie(List<Movie> l, String n) {
		Movie res = new Movie();
		for (Movie m : l) {
			if (Movies.compareNameCamel(m, n)) {
				// Pasar a pelo, deberia de ir con clone o algo asi
				res = m;
				break;
			}
		}
		return res;
	}

	//Comprueba si un nombre en formato camello, coincide con el nombre de la pelicula
	public static boolean compareNameCamel(Movie m, String n) {
		return m.getTitulo().toLowerCase().replace(" ", "")
				.equals(n.toLowerCase());
	}

}
