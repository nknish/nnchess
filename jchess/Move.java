package jchess;

public class Move {
    private String piece;
    private int fromX, fromY, toX, toY;
    private boolean isEnPassant;
    private boolean isCastle;
    private String promotionPiece;

    public Move(String piece, int fromX, int fromY, int toX, int toY) {
        this.piece = piece;
        this.fromX = fromX;
        this.fromY = fromY;
        this.toX = toX;
        this.toY = toY;
        validateCoords(fromX, fromY, toX, toY);
    }
    
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
        validateCoords(fromX, fromY, toX, toY);
    }

    private void validateCoords(int fromX, int fromY, int toX, int toY) {
        if (fromX < 0 || fromX > 7 || fromY < 0 || fromY > 7 || toX < 0 || toX > 7 || toY < 0 || toY > 7) {
            throw new IllegalArgumentException("coords of move oob: (" + fromX + ", " + fromY + ") to (" + toX + ", " + toY + ")");
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
