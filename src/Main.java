import java.util.LinkedList;
import java.util.List;

public class Main {

	public static void main(String[] args) {
		SqlHandler s = new SqlHandler();
		s.insertMovie(2, "nikt", "niznany");
		System.out.println("Przeszlo");
		
		List<Movie> movies = new LinkedList<Movie>();
		movies = s.selectMovies();
		
		System.out.println("lista filmow");
		for(Movie c: movies){
			System.out.println(c);
		}
		
		s.close();
	}

}
