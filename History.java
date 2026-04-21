// TODO check for off-by-one errors in 50-move and 3-move

import java.util.ArrayList;
import java.util.List;

public class History {
    private ArrayList<Board> h;
    private int whiteLostOO;
    private int whiteLostOOO;
    private int blackLostOO;
    private int blackLostOOO;
    private int enPassantX;
    private int enPassantY;

    public History(Board initialBoard) {
        h = new ArrayList<>();
        h.add(initialBoard);
        whiteLostOO = -1;
        whiteLostOOO = -1;
        blackLostOO = -1;
        blackLostOOO = -1;
        enPassantX = -1;
        enPassantY = -1;
    }

    public void logBoard(Board b) {
        enPassantX = -1;
        enPassantY = -1;
        h.add(b);
        logKingMoves(b);
        logRookMovesAndCaptures(b);
        logPawn2SquareMoves(b);
    }

    private void logKingMoves(Board b) {
        // check for king moves
        if (hasKingsideCastle("w") || hasQueensideCastle("w")) {
            Piece p = b.getPiece(0, 4);
            if (p == null || !p.getType().equals("k") || !p.getColor().equals("w")) {
                whiteLostOO = getMoveNumber();
                whiteLostOOO = getMoveNumber();
            }
        }
        if (hasKingsideCastle("b") || hasQueensideCastle("b")) {
            Piece p = b.getPiece(7, 4);
            if (p == null || !p.getType().equals("k") || !p.getColor().equals("b")) {
                blackLostOO = getMoveNumber();
                blackLostOOO = getMoveNumber();
            }
        }
    }

    private void logRookMovesAndCaptures(Board b) {
        // check for rook moves/captures
        if (hasKingsideCastle("w")) {
            Piece p = b.getPiece(0, 7);
            if (p == null || !p.getType().equals("r") || !p.getColor().equals("w")) {
                whiteLostOO = getMoveNumber();
            }
        }
        if (hasQueensideCastle("w")) {
            Piece p = b.getPiece(0, 0);
            if (p == null || !p.getType().equals("r") || !p.getColor().equals("w")) {
                whiteLostOOO = getMoveNumber();
            }
        }
        if (hasKingsideCastle("b")) {
            Piece p = b.getPiece(7, 7);
            if (p == null || !p.getType().equals("r") || !p.getColor().equals("b")) {
                blackLostOO = getMoveNumber();
            }
        }
        if (hasQueensideCastle("b")) {
            Piece p = b.getPiece(7, 0);
            if (p == null || !p.getType().equals("r") || !p.getColor().equals("b")) {
                blackLostOOO = getMoveNumber();
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
            if (prev.getPiece(3, i) == null && b.getPiece(1, i) == null && prev.getPiece(1, i) != null && prev.getPiece(1, i).getType().equals("")) {
                enPassantX = 2;
                enPassantY = i;
            }

            // black pawn
            p = b.getPiece(4, i);
            if (p == null) continue;
            if (!p.getType().equals("") || !p.getColor().equals("b"))
                continue;
            if (prev.getPiece(4, i) == null && b.getPiece(6, i) == null && prev.getPiece(6, i) != null && prev.getPiece(6, i).getType().equals("")) {
                enPassantX = 5;
                enPassantY = i;
            }
        }
    }

    private int getMoveNumber() {
        return h.size() - 1;
    }

    public boolean hasKingsideCastle(String c) {
        if (c.equals("w")) return whiteLostOO == -1;
        else if (c.equals("b")) return blackLostOO == -1;
        else throw new IllegalArgumentException("only colors are 'b' or 'w'");
    }
    
    public boolean hasQueensideCastle(String c) {
        if (c.equals("w")) return whiteLostOOO == -1;
        else if (c.equals("b")) return blackLostOOO == -1;
        else throw new IllegalArgumentException("only colors are 'b' or 'w'");
    }

    public boolean canEnPassantTo(int x, int y) {
        return x == enPassantX && y == enPassantY;
    }

    public boolean hit3MoveRepetition() {
        int repeats = 1;
        Board now = h.getLast();
        int piecesNow = now.countPieces();

        // lookback window ends when castling rights changed (or 0)
        int mostRecentCastlingRightsChange = Math.max(
            Math.max(whiteLostOO, whiteLostOOO), 
            Math.max(blackLostOO, (blackLostOOO == -1) ? 0 : blackLostOOO)
        );
        List<Board> lookback = h.subList(mostRecentCastlingRightsChange, h.size()).reversed();

        // count each identical board in the lookback window
        for (Board old : lookback) {
            if (old.countPieces() != piecesNow) return false;
            if (now.equals(old)) {
                repeats ++;
                if (repeats == 3)
                    return true;
            }
        }
        return false;
    }

    public boolean hit50Move() {
        if (h.size() <= 100) return false;
        Board now = h.getLast();
        Board old = h.get(h.size()-101);

        if (!boardsHaveSameNumberOfPieces(now, old)) return false;
        return allPawnsInSamePositions(now, old);
    }    

    private boolean boardsHaveSameNumberOfPieces(Board b1, Board b2) {
        // compare piece numbers (different # == capture)
        int numPiecesB1 = b1.countPieces();
        int numPiecesB2 = b2.countPieces();
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
