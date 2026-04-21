import java.util.List;

public class Game {
    private Board b;
    private History h;
    private Validator v;
    private Player white;
    private Player black;
    private boolean whiteToMove;

    public Game() {
        b = new Board();
        h = new History(b.getCopy());
        v = new Validator();
        white = new Player(true);
        black = new Player(false);
        whiteToMove = true;
    }

    public void display() {
        System.out.println();
        b.display();
    }

    public void move() {
        Player p = whiteToMove ? white : black;
        String c = whiteToMove ? "w" : "b";
        Move m = move(p, c);
        if (!p.isHuman()) {
            System.out.println(c + " moved " + m.toString());
        }
        whiteToMove = !whiteToMove;
    }

    private Move move(Player p, String color) {
        List<Move> moves = v.getMoves(b.getCopy(), h, color);
        Move move = p.getMove(moves);
        b.makeMove(move);
        h.logBoard(b.getCopy());
        return move;
    }

    public boolean isOver() {
        if (h.hit3MoveRepetition())
            return true;
        if (h.hit50Move())
            return true;
        if (v.bothInsufficientMaterial(b.getCopy()))
            return true;
        List<Move> moves = whiteToMove ? v.getMoves(b.getCopy(), h, "w") : v.getMoves(b.getCopy(), h, "b");
        if (moves.size() == 0)
            return true;
        return false;
    }

    public String outcome() {
        if (v.isCheckmated(b.getCopy(), h, whiteToMove ? "w" : "b")) {
            if (whiteToMove) {
                return "checkmate! black wins";
            } else {
                return "checkmate! white wins";
            }
        }
        return "draw";
    }
}
