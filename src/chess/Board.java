package chess;

import java.lang.StringBuilder;

public class Board {
    private Piece[][] pieces;

    public Board() {
        // fresh new board
        pieces = new Piece[8][8];
        for (int i : new int[] { 0, 7 }) {
            pieces[i][0] = new Piece("r", i == 0 ? "w" : "b");
            pieces[i][1] = new Piece("n", i == 0 ? "w" : "b");
            pieces[i][2] = new Piece("b", i == 0 ? "w" : "b");
            pieces[i][3] = new Piece("q", i == 0 ? "w" : "b");
            pieces[i][4] = new Piece("k", i == 0 ? "w" : "b");
            pieces[i][5] = new Piece("b", i == 0 ? "w" : "b");
            pieces[i][6] = new Piece("n", i == 0 ? "w" : "b");
            pieces[i][7] = new Piece("r", i == 0 ? "w" : "b");
        }
        for (int i = 0; i < 8; i++) {
            pieces[1][i] = new Piece("", "w");
            for (int j = 2; j < 5; j++) {
                pieces[j][i] = null;
            }
            pieces[6][i] = new Piece("", "b");
        }
    }

    private Board(Piece[][] oldPieces) {
        // copy of the board
        pieces = new Piece[8][8];
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                Piece oldPiece = oldPieces[i][j];
                if (oldPiece == null) {
                    pieces[i][j] = null;
                } else {
                    Piece newPiece = new Piece(oldPiece.getType(), oldPiece.getColor());
                    pieces[i][j] = newPiece;
                }
            }
        }
    }

    public Board getCopy() {
        return new Board(pieces);
    }

    public Piece getPiece(int x, int y) {
        Piece p = pieces[x][y];
        if (p == null)
            return null;
        return p.clone();
    }

    public int[] findKing(String c) {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (pieces[i][j] != null && pieces[i][j].getType().equals("k") && pieces[i][j].getColor().equals(c)) {
                    return new int[] { i, j };
                }
            }
        }
        throw new RuntimeException(c + " king not found");
    }

    public int countPieces() {
        int n = 0;
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (pieces[i][j] != null) {
                    n++;
                }
            }
        }
        return n;
    }

    public void makeMove(Move move) {
        // remove piece from spot (save color)
        int[] from = move.getFrom();
        Piece p = pieces[from[0]][from[1]];
        pieces[from[0]][from[1]] = null;

        // put piece in new spot
        int[] to = move.getTo();
        pieces[to[0]][to[1]] = new Piece(p.getType(), p.getColor());

        // handle special moves
        if (move.isCastle()) {
            if (to[1] == 6) {
                // kingside
                pieces[from[0]][5] = new Piece("r", p.getColor());
                pieces[from[0]][7] = null;
            } else {
                // queenside
                pieces[from[0]][3] = new Piece("r", p.getColor());
                pieces[from[0]][0] = null;
            }
        }
        if (move.isEnPassant()) {
            pieces[from[0]][to[1]] = null;
        }
        if (move.isPromotion()) {
            pieces[to[0]][to[1]] = new Piece(move.getPromotionPiece(), p.getColor());
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 7; i >= 0; i--) {
            Piece[] row = pieces[i];
            sb.append(" " + (i + 1) + "|");
            for (Piece p : row) {
                if (p == null) {
                    sb.append(" .");
                } else {
                    sb.append(" " + p);
                }
            }
            sb.append("\n");
        }
        sb.append("   ----------------\n");
        sb.append("    a b c d e f g h\n");
        return sb.toString();
    }

    @Override
    public boolean equals(Object anObject) {
        if (this == anObject) {
            return true;
        }
        return (anObject instanceof Board aBoard)
                && (this.toString().equals(aBoard.toString()));
    }
}