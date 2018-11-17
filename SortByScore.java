import java.util.Comparator;

public class SortByScore implements Comparator <Player>{

	@Override
	public int compare(Player arg0, Player arg1) {
		
		return arg1.getFinalScore() - arg0.getFinalScore();
	}

}