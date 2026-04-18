public class Move {
    int fromX, fromY, toX, toY;
    Piece p;

    public Move(int fromX, int fromY, int toX, int toY, Piece p) {
        this.fromX = fromX;
        this.fromY = fromY;
        this.toX = toX;
        this.toY = toY;
        this.p = p;
    }

    public String toString() {
        String chr = (p.getType().equals("p")) ? "" : p.getType();
        String from = "" + (char)('a' + fromY) + (fromX+1);
        String to = "" + (char)('a' + toY) + (toX+1);
        return chr + from + to;
    }
}
