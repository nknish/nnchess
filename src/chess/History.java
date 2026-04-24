package chess;

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
    private int halfmoveClock;

    public History(Board initialBoard) {
        h = new ArrayList<>();
        h.add(initialBoard);
        whiteLostOO = -1;
        whiteLostOOO = -1;
        blackLostOO = -1;
        blackLostOOO = -1;
        enPassantX = -1;
        enPassantY = -1;
        halfmoveClock = 0;
    }

    public void logBoard(Board b) {
        h.add(b);
        logKingMoves(b);
        logRookMovesAndCaptures(b);
        logPawn2SquareMoves(b);
        tickHalfmoveClock(b);
    }

    private void logKingMoves(Board b) {
        // check for king moves
        if (hasKingsideCastle("w") || hasQueensideCastle("w")) {
            Piece p = b.getPiece(0, 4);
            if (p == null || !p.isType("k") || !p.isColor("w")) {
                whiteLostOO = getMoveNumber();
                whiteLostOOO = getMoveNumber();
            }
        }
        if (hasKingsideCastle("b") || hasQueensideCastle("b")) {
            Piece p = b.getPiece(7, 4);
            if (p == null || !p.isType("k") || !p.isColor("b")) {
                blackLostOO = getMoveNumber();
                blackLostOOO = getMoveNumber();
            }
        }
    }

    private void logRookMovesAndCaptures(Board b) {
        // check for rook moves/captures
        if (hasKingsideCastle("w")) {
            Piece p = b.getPiece(0, 7);
            if (p == null || !p.isType("r") || !p.isColor("w")) {
                whiteLostOO = getMoveNumber();
            }
        }
        if (hasQueensideCastle("w")) {
            Piece p = b.getPiece(0, 0);
            if (p == null || !p.isType("r") || !p.isColor("w")) {
                whiteLostOOO = getMoveNumber();
            }
        }
        if (hasKingsideCastle("b")) {
            Piece p = b.getPiece(7, 7);
            if (p == null || !p.isType("r") || !p.isColor("b")) {
                blackLostOO = getMoveNumber();
            }
        }
        if (hasQueensideCastle("b")) {
            Piece p = b.getPiece(7, 0);
            if (p == null || !p.isType("r") || !p.isColor("b")) {
                blackLostOOO = getMoveNumber();
            }
        }
    }

    private void logPawn2SquareMoves(Board b) {
        // check for next move en passant legality
        enPassantX = -1;
        enPassantY = -1;

        Board prev = h.get(h.size()-2);
        for (int i = 0; i < 8; i++) {
            // white pawn
            Piece p = b.getPiece(3, i);
            if (p == null) continue;
            if (!p.isType("") || !p.isColor("w"))
                continue;
            if (prev.getPiece(3, i) == null && b.getPiece(1, i) == null && prev.getPiece(1, i) != null && prev.getPiece(1, i).isType("")) {
                enPassantX = 2;
                enPassantY = i;
            }

            // black pawn
            p = b.getPiece(4, i);
            if (p == null) continue;
            if (!p.isType("") || !p.isColor("b"))
                continue;
            if (prev.getPiece(4, i) == null && b.getPiece(6, i) == null && prev.getPiece(6, i) != null && prev.getPiece(6, i).isType("")) {
                enPassantX = 5;
                enPassantY = i;
            }
        }
    }

    private void tickHalfmoveClock(Board b) {
        Board prev = h.get(h.size() - 2);

        // check for captures
        if (b.countPieces() != prev.countPieces()) {
            halfmoveClock = 0;
            return;
        }

        // check for pawn moves
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                Piece p1 = b.getPiece(i, j);
                Piece p2 = prev.getPiece(i, j);

                // both null
                if (p1 == null && p2 == null) continue;

                // 1 null
                if (p1 == null) {
                    if (p2.isType("")) {
                        halfmoveClock = 0;
                        return;
                    } else {
                        continue;
                    }
                }
                if (p2 == null) {
                    if (p1.isType("")) {
                        halfmoveClock = 0;
                        return;
                    } else {
                        continue;
                    }
                }

                // neither null
                if (p1.isType("") && !p2.isType("")) {
                    halfmoveClock = 0;
                    return;
                }
                if (p2.isType("") && !p1.isType("")) {
                    halfmoveClock = 0;
                    return;
                }
            }
        }

        // if neither, tick the clock
        halfmoveClock++;
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
        List<Board> lookback = h.subList(mostRecentCastlingRightsChange, h.size() - 1).reversed();

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
        return halfmoveClock >= 100;
    }    

    public boolean whiteToMove() {
        return h.size() % 2 == 1;
    }

    public int[] getEnPassantSquare() {
        return new int[] {enPassantX, enPassantY};
    }

    public int getHalfmoveClock() {
        return halfmoveClock;
    }

    public int getFullmoveCount() {
        return (h.size() + 1) / 2;
    }
}
