package aws002;

import java.util.List;

public class Actores {

	public static boolean containsName(List<Actor> l, String n) {
		boolean res = false;
		if (l != null)
			for (Actor a : l) {
				if (Actores.compareNameCamel(a, n)) {
					res = true;
				}
	
			}
		return res;
	}
	
	public static Actor getActor(List<Actor> l, String n) {
		Actor res = new Actor();
		for (Actor a : l) {
			if (Actores.compareNameCamel(a, n)) {
				// Pasar a pelo, deberia de ir con clone o algo asi
				res = a;
				break;
			}
		}
		return res;
	}
	
	public static boolean compareNameCamel(Actor a, String n) {
		return a.getNombreCompleto().toLowerCase().replace(" ", "")
				.equals(n.toLowerCase());
	}
}
