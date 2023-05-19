package Logic;

import java.util.ArrayList;
import java.util.Collections;

public class Utils{
    public static ArrayList<Player> winners;

    //Method to evaluate winning player(s)
    public static void setWinners(ArrayList<Player> players) {

        players.sort(new ComparatorByResult());
        Collections.reverse(players);
        System.out.println("Sorted:");
        System.out.println(players);
        winners = new ArrayList<>(players);


        for(int i = 1; i < winners.size(); i++){

            Player currentPlayer = winners.get(i);
            Player highestRankedPlayer = winners.get(0);
            int result = new ComparatorByResult().compare(currentPlayer, highestRankedPlayer);
            var highestRank = highestRankedPlayer.getcRank();

            if(result != 0 || currentPlayer.getcRank().getValue() != highestRank.getValue()){
                winners.remove(currentPlayer);
                i--;
            }
        }

        winners.forEach(p -> p.setWinner(true));
        System.out.println("WINNERS:");
        System.out.println(winners + "\n");
    }
}
