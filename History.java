import java.util.ArrayList;

public class History {
    private ArrayList<Board> h;
    private boolean whiteCanOO;
    private boolean whiteCanOOO;
    private boolean blackCanOO;
    private boolean blackCanOOO;
    private int enPassantX;
    private int enPassantY;

    public History(Board initialBoard) {
        h = new ArrayList<>();
        h.add(initialBoard);
        whiteCanOO = true;
        whiteCanOOO = true;
        blackCanOO = true;
        blackCanOOO = true;
        enPassantX = -1;
        enPassantY = -1;
    }

    public void logBoard(Board b) {
        h.add(b);
        logKingMoves(b);
        logRookMovesAndCaptures(b);
        logPawn2SquareMoves(b);
    }

    private void logKingMoves(Board b) {
        // check for king moves
        if (whiteCanOO || whiteCanOOO) {
            if (!b.getPiece(0, 4).getType().equals("k")) {
                whiteCanOO = false;
                whiteCanOOO = false;
            }
        }
        if (blackCanOO || blackCanOOO) {
            if (!b.getPiece(7, 4).getType().equals("k")) {
                blackCanOO = false;
                blackCanOOO = false;
            }
        }
    }

    private void logRookMovesAndCaptures(Board b) {
        // check for rook moves/captures
        if (whiteCanOO) {
            Piece p = b.getPiece(0, 7);
            if (!p.getType().equals("r") || !p.getColor().equals("w")) {
                whiteCanOO = false;
            }
        }
        if (!whiteCanOOO) {
            Piece p = b.getPiece(0, 0);
            if (!p.getType().equals("r") || !p.getColor().equals("w")) {
                whiteCanOOO = false;
            }
        }
        if (blackCanOO) {
            Piece p = b.getPiece(7, 7);
            if (!p.getType().equals("r") || !p.getColor().equals("b")) {
                blackCanOO = false;
            }
        }
        if (!blackCanOOO) {
            Piece p = b.getPiece(7, 0);
            if (!p.getType().equals("r") || !p.getColor().equals("b")) {
                blackCanOOO = false;
            }
        }
    }

    private void logPawn2SquareMoves(Board b) {
        // check for next move en passant legality
        Board prev = h.get(h.size()-2);
        for (int i = 0; i < 8; i++) {
            // white pawn
            Piece p = b.getPiece(3, i);
            if (p == null) continue;
            if (!p.getType().equals("") || !p.getColor().equals("w"))
                continue;
            if (prev.getPiece(3, i) == null && b.getPiece(1, i) == null && prev.getPiece(1, i).getType().equals("")) {
                enPassantX = 2;
                enPassantY = i;
            }

            // black pawn
            p = b.getPiece(4, i);
            if (p == null) continue;
            if (!p.getType().equals("") || !p.getColor().equals("b"))
                continue;
            if (prev.getPiece(4, i) == null && b.getPiece(6, i) == null && prev.getPiece(6, i).getType().equals("")) {
                enPassantX = 5;
                enPassantY = i;
            }
        }
    }

    public boolean whiteCanKingsideCastleBasedOnHistory() {
        return whiteCanOO;
    }

    public boolean whiteCanQueensideCastleBasedOnHistory() {
        return whiteCanOOO;
    }

    public boolean blackCanKingsideCastleBasedOnHistory() {
        return blackCanOO;
    }

    public boolean blackCanQueensideCastleBasedOnHistory() {
        return blackCanOOO;
    }

    public boolean canEnPassantTo(int x, int y) {
        return x == enPassantX && y == enPassantY;
    }

    public boolean hit3MoveRepetition() {
        // TODO implement 3 move repetition
        // must consider: boards that look the same but have different castling rights are considered distinct

        return false;
    }

    public boolean hit50Move() {
        if (h.size() <= 50) return false;
        Board now = h.getLast();
        Board old = h.get(h.size()-51);

        if (!boardsHaveSameNumberOfPieces(now, old)) return false;
        return allPawnsInSamePositions(now, old);
    }    

    private boolean boardsHaveSameNumberOfPieces(Board b1, Board b2) {
        // compare piece numbers (different # == capture)
        int numPiecesB1 = 0;
        int numPiecesB2 = 0;
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (b1.getPiece(i, j) != null) {
                    numPiecesB1++;
                }
                if (b2.getPiece(i, j) != null) {
                    numPiecesB2++;
                }
            }
        }
        return numPiecesB1 == numPiecesB2;
    }

    private boolean allPawnsInSamePositions(Board b1, Board b2) {
        // check positions of all pawns (not totally equivalent == pawn moved)
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                Piece b1Piece = b1.getPiece(i, j);
                Piece b2Piece = b2.getPiece(i, j);
                if (b1Piece != null && b1Piece.getType().equals("")) {
                    String color = b1Piece.getColor();
                    if (b2Piece == null) return false;
                    if (!b2Piece.getType().equals("")) return false;
                    if (!b2Piece.getColor().equals(color)) return false;
                } else if (b2Piece != null && b2Piece.getType().equals("")) {
                    return false;
                }
            }
        }
        return true;
    }

}
