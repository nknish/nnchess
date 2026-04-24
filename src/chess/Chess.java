package chess;

public class Chess {
    public static void main(String[] args) {
        Game g = new Game();
        g.display();
        while (!g.isOver()) {
            g.move();
            g.display();
        }
        System.out.println(g.outcome());
    }
}
