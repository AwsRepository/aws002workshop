package aws002;

import java.util.LinkedList;
import java.util.List;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.FilterPredicate;

public class Persistencia {

	// Los nombres de las funciones estaran formados por select, insert, update,
	// delete,
	// seguido de Movies o Movies dependiendo si se trae una pelicula o todas

	// OK
	// Insert una pelicula
	public static void insertMovie(Movie m) {

		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();

		Entity e = new Entity("Movie");

		e.setProperty("titulo", m.getTitulo());
		e.setProperty("director", m.getDirector());
		e.setProperty("fecha", m.getFecha());
		e.setProperty("duracion", m.getDuracion());

		datastore.put(e);	}

	// OK
	public static void deleteMovie(String m) {
		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();
		Entity e = Persistencia.selectEntity(m);
		try {
			datastore.delete(e.getKey());
		} catch (Exception b) {

		}
	}

	// Arreglar
	public static Movie selectMovie(String a) {

		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();
		// Problema no filtra con camel
		Query q = new Query("Movie").setFilter(new FilterPredicate("titulo",
				Query.FilterOperator.EQUAL, a));

		PreparedQuery pq = datastore.prepare(q);

		Entity e = pq.asSingleEntity();

		Movie m = new Movie((String) e.getProperty("titulo"),
				(String) e.getProperty("director"),
				(Integer) e.getProperty("fecha"),
				(Integer) e.getProperty("duracion"));
		return m;
	}

	// El string de entrada es el nombre completo
	// Arreglar
	// ELIMINAR
	public static Entity selectEntity(String a) {

		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();
		// Problema no filtra con camel
		Query q = new Query("Movie").setFilter(new FilterPredicate("titulo",
				Query.FilterOperator.EQUAL, a));

		PreparedQuery pq = datastore.prepare(q);

		Entity e = pq.asSingleEntity();

		return e;
	}

	// Arreglar
	// ELIMINAR
	public static Boolean isMovie(String a) {
		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();
		// Problema no filtra con camel
		Query q = new Query("Movie").setFilter(new FilterPredicate("titulo",
				Query.FilterOperator.EQUAL, a));

		PreparedQuery pq = datastore.prepare(q);

		Entity e = pq.asSingleEntity();

		return e != null;
	}

	// OK
	public static List<Movie> selectMovies() {
		// TODO Auto-generated method stub
		List<Movie> movies = new LinkedList<Movie>();

		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();
		// Problema no filtra con camel
		Query q = new Query("Movie");

		PreparedQuery pq = datastore.prepare(q);

		Iterable<Entity> it = pq.asIterable();

		for (Entity e : it) {

			Movie m = new Movie((String) e.getProperty("titulo"),
					(String) e.getProperty("director"), new Integer(
							(int) (long) e.getProperty("fecha")), new Integer(
							(int) (long) e.getProperty("duracion")));
			movies.add(m);
		}

		return movies;

	}

	// OK
	public static void deleteMovies() {

		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();
		Query q = new Query("Movie");

		PreparedQuery pq = datastore.prepare(q);

		Iterable<Entity> it = pq.asIterable();

		for (Entity e : it) {

			datastore.delete(e.getKey());

		}

	}

}
