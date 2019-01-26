import java.util.Comparator;

public class SortByScore implements Comparator<Player> {

	// sorting logic for collections library
	@Override
	public int compare(Player arg0, Player arg1) {

		return arg1.getFinalScore() - arg0.getFinalScore();
	}

}