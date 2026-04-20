public class Move {
    String piece;
    int fromX, fromY, toX, toY;
    boolean isEnPassant;
    boolean isCastle;
    String promotionPiece;

    public Move(String piece, int fromX, int fromY, int toX, int toY, String special) {
        this.piece = piece;
        this.fromX = fromX;
        this.fromY = fromY;
        this.toX = toX;
        this.toY = toY;
        if (special.equals("ep")) {
            isEnPassant = true;
            isCastle = false;
            promotionPiece = null;
        } else if (special.equals("castle")) {
            isEnPassant = false;
            isCastle = true;
            promotionPiece = null;            
        } else if (special.equals("q") || special.equals("r") || special.equals("b") || special.equals("n")) {
            isEnPassant = false;
            isCastle = false;
            promotionPiece = special;
        } else {
            throw new IllegalArgumentException("special can only be 'ep', 'castle', or promotion piece ('q', 'r', 'b', 'n'");
        }
    }

    public int[] getFrom() {
        return new int[] {fromX, fromY};
    }

    public int[] getTo() {
        return new int[] {toX, toY};
    }

    public boolean isEnPassant() {
        return isEnPassant;
    }

    public boolean isCastle() {
        return isCastle;
    }

    public boolean isPromotion() {
        return (promotionPiece != null);
    }

    public String getPromotionPiece() {
        if (promotionPiece == null) {
            throw new RuntimeException("this move is not a promotion");
        }
        return promotionPiece;
    }

    public String toString() {
        String from = "" + (char)('a' + fromY) + (fromX+1);
        String to = "" + (char)('a' + toY) + (toX+1);
        return piece + from + to;
    }
}
