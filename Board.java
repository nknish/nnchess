import java.util.ArrayList;

public class Board {
    private Piece[][] board;
    private boolean whiteToMove;

    public Board() {
        board = new Piece[8][8];
        whiteToMove = true;
        for (int i = 0; i < 8; i += 7) {
            board[i][0] = new Piece("r", i == 0 ? "w" : "b");
            board[i][1] = new Piece("n", i == 0 ? "w" : "b");
            board[i][2] = new Piece("b", i == 0 ? "w" : "b");
            board[i][3] = new Piece("q", i == 0 ? "w" : "b");
            board[i][4] = new Piece("k", i == 0 ? "w" : "b");
            board[i][5] = new Piece("b", i == 0 ? "w" : "b");
            board[i][6] = new Piece("n", i == 0 ? "w" : "b");
            board[i][7] = new Piece("r", i == 0 ? "w" : "b");
        }
        for (int i = 0; i < 8; i++) {
            board[1][i] = new Piece("p", "w");
            for (int j = 2; j < 5; j++) {
                board[j][i] = null;
            }
            board[6][i] = new Piece("p", "b");
        }
    }

    public void printBoard() {
        for (int i = 7; i >= 0; i--) {
            Piece[] row = board[i];
            System.out.print(" " + (i + 1) + "|");
            for (Piece p : row) {
                if (p == null) {
                    System.out.print(" .");
                } else {
                    System.out.print(" " + p);
                }
            }
            System.out.println();
        }
        System.out.println("   ----------------");
        System.out.println("    a b c d e f g h");
    }

    public ArrayList<Move> getLegalMoves() {
        String color = whiteToMove ? "w" : "b";
        // get 1d list, including moves causing check
        ArrayList<Move> moves = get1LayerLegalMoves(board, color);

        for (int i = moves.size()-1; i >= 0; i--) {
            // speculatively test the move (for check) on a cloned board
            Move m = moves.get(i);
            Piece[][] copy = getCopy();
            makeMove(m, copy);
            if (checkCheck(copy, color)) {
                moves.remove(i);
            }
        }
        return moves;
    }

    private ArrayList<Move> get1LayerLegalMoves(Piece[][] board, String color) {
        // gets all moves, including ones that would cause check
        ArrayList<Move> moves = new ArrayList<>();
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                Piece p = board[i][j];
                if (p == null || !p.isColor(color))
                    continue;
                moves.addAll(p.getMoves(board, i, j));
            }
        }
        return moves;
    }

    public void makeMove(Move move) {
        makeMove(move, board);
        System.out.println("Move: " + move);
        whiteToMove = !whiteToMove;
    }

    private void makeMove(Move move, Piece[][] board) {
        // real or speculative move on real or clone board
        board[move.toX][move.toY] = move.p;
        board[move.fromX][move.fromY] = null;

        // special moves: promotion, castle, en passant
        if (move.p.getType().equals("p") && (move.toX == 0 || move.toX == 7)) {
            board[move.toX][move.toY] = new Piece("q", move.p.isColor("w") ? "w" : "b");
        }
        // (haven't done castle/en passant yet)
    }

    // check if 'color' is in check given the state of 'board'
    private boolean checkCheck(Piece[][] board, String color) {
        // find king
        int[] king = findKing(board, color);
        String kingCaptureSuffix = "" + (char) ('a' + king[1]) + (king[0] + 1);

        // look at opponent's next moves
        String oppColor = (color == "w") ? "b" : "w";
        ArrayList<Move> possibleNextMoves = get1LayerLegalMoves(board, oppColor);
        for (Move m : possibleNextMoves) {
            if (m.toString().endsWith(kingCaptureSuffix)) {
                return true; // in check
            }
        }
        return false;
    }

    // find king of specified color on given board
    private int[] findKing(Piece[][] board, String color) {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (board[i][j] != null && board[i][j].getType().equals("k") && board[i][j].isColor(color)) {
                    return new int[] {i, j};
                }
            }
        }
        throw new RuntimeException(color + "king not found");
    }

    private Piece[][] getCopy() {
        Piece[][] nboard = new Piece[8][8];
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (board[i][j] == null) {
                    nboard[i][j] = null;
                } else {
                    nboard[i][j] = board[i][j].clone();
                }
            }
        }
        return nboard;
    }
}