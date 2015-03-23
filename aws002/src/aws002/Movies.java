package aws002;

import java.util.LinkedList;
import java.util.List;

public class Movies {

	//Esto es una clase que nos servira para trabajar con la lista de peliculas 
	//Para evitar trabajar constantemente con la BBDD, tenemos una lista de peliculas
	//para trabajar de un modo mas rapido.
	
	
	// Deprecated
	public static List<Movie> lista() {
		List<Movie> res = new LinkedList<Movie>();

		Movie b = new Movie("Shutter Island", "Martin Scorsese", new Integer(
				2010), new Integer(138));
		res.add(b);

		Movie b2 = new Movie("Seven", "David Fincher", new Integer(1995),
				new Integer(127));
		res.add(b2);

		Movie b3 = new Movie("El club de la lucha", "David Fincher",
				new Integer(1999), new Integer(139));
		res.add(b3);

		return res;

	}

	//Comprueba si hay una pelicula con el nombre n
	public static boolean containsName(List<Movie> l, String n) {
		boolean res = false;
		for (Movie m : l) {
			if (Movies.compareNameCamel(m, n)) {
				res = true;
			}

		}
		return res;
	}

	//Devuelve la pelicula de la lista que contenta el nombre n
	public static Movie getName(List<Movie> l, String n) {
		Movie res = new Movie();
		for (Movie m : l) {
			if (Movies.compareNameCamel(m, n)) {
				// Pasar a pelo, deberia de ir con clone o algo asi
				res = m;
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
