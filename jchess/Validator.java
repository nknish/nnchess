package jchess;

import java.util.ArrayList;
import java.util.List;

public class Validator {
    private int[][] knightSquares = {
            { 2, 1 }, { 2, -1 }, { -2, 1 }, { -2, -1 },
            { 1, 2 }, { 1, -2 }, { -1, 2 }, { -1, -2 }
    };
    private int[][] kingSquares = {
            { -1, -1 }, { -1, 0 }, { -1, 1 }, { 0, 1 },
            { 1, 1 }, { 1, 0 }, { 1, -1 }, { 0, -1 }
    };
    private int[][] rookDirs = { { -1, 0 }, { 0, 1 }, { 1, 0 }, { 0, -1 } };
    private int[][] bishopDirs = { { -1, -1 }, { -1, 1 }, { 1, 1 }, { 1, -1 } };
    private int[][] queenDirs = {
            { -1, -1 }, { -1, 0 }, { -1, 1 }, { 0, 1 },
            { 1, 1 }, { 1, 0 }, { 1, -1 }, { 0, -1 }
    };

    public List<Move> getMoves(Board b, History h, String color) {
        // TODO shouldn't be able to castle out of check
        List<Move> possibleMoves = getMovesIgnoringCheck(b, h, color);
        ArrayList<Move> moves = new ArrayList<>();
        for (Move m : possibleMoves) {
            if (!causesCheck(b.getCopy(), h, m, color)) {
                moves.add(m);
            }
        }
        return moves;
    }

    private List<Move> getMovesIgnoringCheck(Board b, History h, String color) {
        ArrayList<Move> moves = new ArrayList<>();
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                Piece p = b.getPiece(i, j);
                if (p == null)
                    continue;
                if (!p.getColor().equals(color))
                    continue;
                List<Move> pieceMoves = getMoves(b.getCopy(), h, p, i, j);
                moves.addAll(pieceMoves);
            }
        }
        return moves;
    }

    private boolean causesCheck(Board b, History h, Move m, String c) {
        b.makeMove(m);
        int[] kingPos = b.findKing(c);
        
        String opponentColor = c.equals("w") ? "b" : "w";
        List<Move> opponentNextMoves = getMovesIgnoringCheck(b, h, opponentColor);
        for (Move opponentMove : opponentNextMoves) {
            int[] moveDest = opponentMove.getTo();
            if (moveDest[0] == kingPos[0] && moveDest[1] == kingPos[1]) {
                return true;
            }

            // TODO if castling, also check intermediate spaces for king
        }
        return false;
    }

    private List<Move> getMoves(Board b, History h, Piece p, int x, int y) {
        switch (p.getType()) {
            case "k":
                return getKingMoves(b, h, x, y);
            case "q":
                return getQueenMoves(b, h, x, y);
            case "r":
                return getRookMoves(b, h, x, y);
            case "b":
                return getBishopMoves(b, h, x, y);
            case "n":
                return getKnightMoves(b, h, x, y);
            case "":
                return getPawnMoves(b, h, x, y);
            default:
                throw new RuntimeException("can only get moves for valid piece");
        }
    }

    private List<Move> getKingMoves(Board b, History h, int x, int y) {
        ArrayList<Move> moves = new ArrayList<>();
        Piece p = b.getPiece(x, y);

        moves = getFixedMoves(b, x, y, kingSquares);
        moves.addAll(getCastleMoves(b, h, p));

        return moves;
    }

    private List<Move> getQueenMoves(Board b, History h, int x, int y) {
        return getLinearMoves(b, x, y, queenDirs);
    }

    private List<Move> getRookMoves(Board b, History h, int x, int y) {
        return getLinearMoves(b, x, y, rookDirs);
    }

    private List<Move> getBishopMoves(Board b, History h, int x, int y) {
        return getLinearMoves(b, x, y, bishopDirs);
    }

    private List<Move> getKnightMoves(Board b, History h, int x, int y) {
        return getFixedMoves(b, x, y, knightSquares);
    }

    private ArrayList<Move> getLinearMoves(Board b, int x, int y, int[][] dirs) {
        Piece p = b.getPiece(x, y);
        ArrayList<Move> moves = new ArrayList<>();
        for (int[] dir : dirs) {
            int newX = x + dir[0];
            int newY = y + dir[1];
            while (newX >= 0 && newX < 8 && newY >= 0 && newY < 8) {
                Piece pieceInTheWay = b.getPiece(newX, newY);
                if (pieceInTheWay == null) {
                    moves.add(new Move(p.getType(), x, y, newX, newY));
                } else if (b.getPiece(newX, newY).getColor().equals(p.getColor())) {
                    break;
                } else {
                    moves.add(new Move(p.getType(), x, y, newX, newY));
                    break;
                }
                newX += dir[0];
                newY += dir[1];
            }
        }
        return moves;
    }

    private ArrayList<Move> getFixedMoves(Board b, int x, int y, int[][] squares) {
        Piece p = b.getPiece(x, y);
        ArrayList<Move> moves = new ArrayList<>();
        for (int[] move : squares) {
            int newX = x + move[0];
            int newY = y + move[1];
            if (newX > 7 || newX < 0 || newY > 7 || newY < 0) {
                continue;
            }
            Piece pieceInTheWay = b.getPiece(newX, newY);
            if (pieceInTheWay != null && pieceInTheWay.getColor().equals(p.getColor())) {
                continue;
            }
            moves.add(new Move(p.getType(), x, y, newX, newY));
        }
        return moves;
    }

    private ArrayList<Move> getCastleMoves(Board b, History h, Piece p) {
        ArrayList<Move> moves = new ArrayList<>();
        int x = p.getColor().equals("w") ? 0 : 7;

        if (h.hasKingsideCastle(p.getColor())) {
            moves.add(new Move(p.getType(), x, 4, x, 6, "castle"));
        }
        if (h.hasQueensideCastle(p.getColor())) {
            moves.add(new Move(p.getType(), x, 4, x, 1, "castle"));
        }

        return moves;
    }

    private List<Move> getPawnMoves(Board b, History h, int x, int y) {
        ArrayList<Move> moves = new ArrayList<>();
        Piece p = b.getPiece(x, y);
        String color = p.getColor();
        int dir = color.equals("w") ? 1 : -1;

        // get forward moves (1 or spaces 2) including promotions
        if (b.getPiece(x + dir, y) == null) {
            moves.addAll(getPawnMovesIncludingPromotion(p, x, y, x + dir, y));
            // two spaces
            if ((dir == 1 && x == 1) || (dir == -1 && x == 6)) {
                if (b.getPiece(x + 2 * dir, y) == null) {
                    moves.add(new Move(p.getType(), x, y, x + 2 * dir, y));
                }
            }
        }

        // get captures, including promotions and en passant
        if (y > 0) {
            Piece c1 = b.getPiece(x + dir, y - 1);
            if (c1 != null && !c1.getColor().equals(color)) {
                moves.addAll(getPawnMovesIncludingPromotion(p, x, y, x + dir, y - 1));
            } else if (c1 == null && h.canEnPassantTo(x + dir, y - 1)) {
                moves.add(new Move(p.getType(), x, y, x + dir, y - 1, "ep"));
            }
        }
        if (y < 7) {
            Piece c2 = b.getPiece(x + dir, y + 1);
            if (c2 != null && !c2.getColor().equals(color)) {
                moves.addAll(getPawnMovesIncludingPromotion(p, x, y, x + dir, y + 1));
            } else if (c2 == null && h.canEnPassantTo(x + dir, y + 1)) {
                moves.add(new Move(p.getType(), x, y, x + dir, y + 1, "ep"));
            }
        }

        return moves;
    }

    // for use when a destination for move(s) has already been determined valid
    private List<Move> getPawnMovesIncludingPromotion(Piece p, int fromX, int fromY, int toX, int toY) {
        ArrayList<Move> moves = new ArrayList<>();
        if (toX == 0 || toX == 7) {
            for (String promotionPiece : new String[] { "q", "r", "b", "n" }) {
                moves.add(new Move(p.getType(), fromX, fromY, toX, toY, promotionPiece));
            }
        } else {
            moves.add(new Move(p.getType(), fromX, fromY, toX, toY));
        }
        return moves;
    }

    public boolean bothInsufficientMaterial(Board b) {
        return b.countPieces() == 2;
        // TODO determine what constitutes insufficient material
    }

    public boolean isCheckmated(Board b, History h, String c) {
        // make sure 'c' can't move
        if (getMoves(b, h, c).size() != 0)
            return false;

        // make sure the player that isn't 'c' can capture the king
        List<Move> moves = getMoves(b, h, c.equals("w") ? "b" : "w");
        int[] kingPos = b.findKing(c);
        for (Move m : moves) {
            if (m.getTo()[0] == kingPos[0] && m.getTo()[1] == kingPos[1])
                return true;
        }
        return false;
    }
}
