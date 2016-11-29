package roguelike;

import roguelike.logic.Client;
import roguelike.logic.Game;

import java.util.Random;

/**
 * Created by the7winds on 27.11.16.
 */
public class Main {

    public static void main(String[] args) {
        Random random = new Random(4);
        Client client = new Client(System.in, System.out);
        Game game = new Game(random, client);
        game.play();
    }
}
