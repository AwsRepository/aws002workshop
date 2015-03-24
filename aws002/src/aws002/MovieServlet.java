package aws002;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.*;

import com.google.gson.Gson;

public class MovieServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private static List<Movie> peliculas;
	
	

	public void init() throws ServletException {
		super.init();

		//Esto era una lista con todass las peliculas, Actua como una cache, para aumentar el rendimiento
		//En este metodo se carga en memoeria, todas las peliculas del datastore
		//Unicamente atacaremos el datastore cuando insertemos, borremos o actualicemos una pelicula.
		peliculas=Persistencia.selectMovies();
	}
	
	

	
	
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {

		String uri = req.getRequestURI();

		String[] uricomponent = uri.split("/");

		if (uricomponent.length > 2) {
			// Rama para GET unico

			String tituloPelicula = uricomponent[2];

			if (Movies.containsName(peliculas, tituloPelicula)) {
				if (uricomponent.length > 3)
				{
					Movie pelicula = Movies.getMovie(peliculas, tituloPelicula);
					if (uricomponent.length > 4)
					{
						String nombreActor = uricomponent[4];
						if (Actores.containsName(pelicula.getActores(), nombreActor))
						{
							Actor actor = Actores.getActor(pelicula.getActores(), nombreActor);
							
							Gson gson = new Gson();
							String jsonString = gson.toJson(actor);
							resp.setContentType("text/json");
							resp.getWriter().println(jsonString);
						}
						else
							resp.sendError(404);
					}
					else if (uricomponent[3].equals("actores"))
					{	
						Gson gson = new Gson();
						String jsonString = gson.toJson(pelicula.getActores());
						resp.setContentType("text/json");
						resp.getWriter().println(jsonString);
					}
					else
						resp.sendError(404);
				}
				else
				{
					Gson gson = new Gson();
					String jsonString2 = gson.toJson(Movies.getMovie(peliculas, tituloPelicula));
	
					resp.setContentType("text/json");
					resp.getWriter().println(jsonString2);
				}

			} else {
				// No existe recurso
				resp.sendError(404);
			}

		} else {
			// Rama para Get total
			Gson gson = new Gson();
			String jsonString = gson.toJson(peliculas);

			resp.setContentType("text/json");
			resp.getWriter().println(jsonString);
		}

	}
	
	

	public void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {

		String uri = req.getRequestURI();

		String[] uricompoment = uri.split("/");
		
		if (uricompoment.length == 2)
		{
			Movie m = null;
			Gson gson = new Gson();
			StringBuilder sb = new StringBuilder();
			BufferedReader br = req.getReader();
			String jsonString;
			while ((jsonString = br.readLine()) != null) {
				sb.append(jsonString);
			}

			jsonString = sb.toString();

			try {
				m = gson.fromJson(jsonString, Movie.class);
			} catch (Exception e) {
				System.out.println("ERROR parsing Movie: " + e.getMessage());
			}

			// Comprobar que no existe previamente la pelicula
			if(Movies.containsName(peliculas, m.getTitulo())){
				
				//No modificado 304
				resp.sendError(304);

			}else{
				
				peliculas.add(m);
				Persistencia.insertMovie(m);
				
				//Que debe devolver en este caso el service ¿?¿?
				//Devuelve otro json, que debria de devolver ???? 
				resp.setContentType("text/json");
				resp.getWriter().println(jsonString);
			}
		}
		else if (uricompoment.length == 4 &&
				Movies.containsName(peliculas, uricompoment[2]) &&
				uricompoment[3].equals("actores"))
		{
			Actor a = null;
			Gson gson = new Gson();
			StringBuilder sb = new StringBuilder();
			BufferedReader br = req.getReader();
			String jsonString;
			while ((jsonString = br.readLine()) != null) {
				sb.append(jsonString);
			}

			jsonString = sb.toString();

			try {
				a = gson.fromJson(jsonString, Actor.class);
			} catch (Exception e) {
				System.out.println("ERROR parsing Actor: " + e.getMessage());
			}
			
			Movie pelicula = Movies.getMovie(peliculas, uricompoment[2]);
			
			// Comprobar que no existe previamente el actor
			if(Actores.containsName(pelicula.getActores(), a.getNombreCompleto())){
				
				//No modificado 304
				resp.sendError(304);

			}else{
				
				pelicula.addActor(a);
				Persistencia.insertActor(pelicula.getTitulo(), a);
				
				//Que debe devolver en este caso el service ¿?¿?
				//Devuelve otro json, que debria de devolver ???? 
				resp.setContentType("text/json");
				resp.getWriter().println(jsonString);
			}
		}
		else
			//Accion prohibida
			resp.sendError(403);	
	}

	
	
	
	@Override
	protected void doDelete(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		String uri = req.getRequestURI();

		String[] uricompoment = uri.split("/");

		if (uricompoment.length > 2) {
			// Rama para GET unico

			String t = uricompoment[2];

			if (Movies.containsName(peliculas, t)) {

				Gson gson2 = new Gson();
				String jsonString2 = gson2.toJson(Movies.getMovie(peliculas, t));

				peliculas.remove(Movies.getMovie(peliculas, t));
				Persistencia.deleteMovie(Movies.getMovie(peliculas,t).getTitulo());
				
				//???Se devolveria
				resp.setContentType("text/json");
				resp.getWriter().println(jsonString2);

			} else {
				// No existe recurso
				resp.sendError(404);
			}

		} else {
			// Rama para Get total
			Gson gson = new Gson();
			String jsonString = gson.toJson(peliculas);

			peliculas=new LinkedList<Movie>();
			Persistencia.deleteMovies();
			
			//???Se devuelven todas las peliculas que se han borrado
			resp.setContentType("text/json");
			resp.getWriter().println(jsonString);
		}
		
		
	}

	@Override
	protected void doPut(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		String uri = req.getRequestURI();

		String[] uricompoment = uri.split("/");

		if (uricompoment.length > 2) {

			String t = uricompoment[2];

			if (Movies.containsName(peliculas, t)) {
				
				Movie m = null;
				Gson gson = new Gson();
				StringBuilder sb = new StringBuilder();
				BufferedReader br = req.getReader();
				String jsonString;
				while ((jsonString = br.readLine()) != null) {
					sb.append(jsonString);
				}

				jsonString = sb.toString();

				try {
					m = gson.fromJson(jsonString, Movie.class);
				} catch (Exception e) {
					System.out.println("ERROR parsing Movie: " + e.getMessage());
				}
				
				if(Movies.compareNameCamel(m, t)){
					peliculas.remove(Movies.getMovie(peliculas, t));
					peliculas.add(m);
					Persistencia.deleteMovie(t);
					Persistencia.insertMovie(m);
					
				}else{
					//El objeto que manda no tiene el mismo nombre que la direccion url
					//No modificado
					resp.sendError(304);
				}
				
				
				

			} else {
				// No existe recurso
				resp.sendError(404);
			}

		} else {
			//Rama para PUT en lista, esta prohibido
			//PROHIBIDO
			resp.sendError(403);

		}

		
		
	}
	
	
}
