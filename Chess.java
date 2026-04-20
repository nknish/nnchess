public class Chess {
    public static void main(String[] args) {
        Game g = new Game();
        while (!g.isOver()) {
            g.move();
        }
        System.out.println(g.outcome());
    }
}
