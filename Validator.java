import java.util.List;

public class Validator {
    // TODO implement move validation

    public List<Move> getMoves(Board state, History h, String color) {
        throw new UnsupportedOperationException("Unimplemented method 'getMoves'");
    }

    public boolean bothInsufficientMaterial(Board b) {
        throw new UnsupportedOperationException("Unimplemented method 'bothInsufficientMaterial'");
    }

    public boolean isCheckmate(Board b, String c) {
        throw new UnsupportedOperationException("Unimplemented method 'isCheckmate'");
    }

        // public ArrayList<Move> getMoves(Piece[][] board, int x, int y) {
    //     ArrayList<Move> moves = new ArrayList<>();
    //     if (type.equals("")) {
    //         moves.addAll(getPawnMoves(board, x, y));
    //     }
    //     if (type.equals("k")) {
    //         moves.addAll(getKingMoves(board, x, y));
    //     }
    //     if (type.equals("n")) {
    //         moves.addAll(getKnightMoves(board, x, y));
    //     }
    //     if (type.equals("q") || type.equals("r")) {
    //         moves.addAll(getRookMoves(board, x, y));
    //     }
    //     if (type.equals("q") || type.equals("b")) {
    //         moves.addAll(getBishopMoves(board, x, y));
    //     }
    //     return moves;
    // }

    // private ArrayList<Move> getPawnMoves(Piece[][] board, int x, int y) {
    //     ArrayList<Move> moves = new ArrayList<>();
    //     int dir = color.equals("w") ? 1 : -1;
    //     // forward moves
    //     if (board[x+dir][y] == null) {
    //         moves.add(new Move(x, y, x+dir, y, board[x][y]));
    //         // two spaces
    //         if ((dir == 1 && x == 1) || (dir == -1 && x == 6)) {
    //             if (board[x+2*dir][y] == null) {
    //                 moves.add(new Move(x, y, x+2*dir, y, board[x][y]));
    //             }
    //         }
    //     }
    //     // captures
    //     if (y > 0) {
    //         Piece c1 = board[x+dir][y-1];
    //         if (c1 != null && !c1.isColor(color)) {
    //             moves.add(new Move(x, y, x+dir, y-1, board[x][y]));
    //         }
    //     }
    //     if (y < 7) {
    //         Piece c2 = board[x+dir][y+1];
    //         if (c2 != null && !c2.isColor(color)) {
    //             moves.add(new Move(x, y, x+dir, y+1, board[x][y]));
    //         }
    //     }
    //     return moves;
    // }

    // private ArrayList<Move> getKingMoves(Piece[][] board, int x, int y) {
    //     int[][] kingSquares = {
    //         {-1, -1}, {-1, 0}, {-1, 1}, {0, 1},
    //         {1, 1}, {1, 0}, {1, -1}, {0, -1}
    //     };
    //     return getFixedMoves(board, x, y, kingSquares);
    // }
    // private ArrayList<Move> getKnightMoves(Piece[][] board, int x, int y) {
    //     int[][] knightSquares = {
    //         {2, 1}, {2, -1}, {-2, 1}, {-2, -1},
    //         {1, 2}, {1, -2}, {-1, 2}, {-1, -2}
    //     };
    //     return getFixedMoves(board, x, y, knightSquares);
    // }

    // private ArrayList<Move> getRookMoves(Piece[][] board, int x, int y) {
    //     int[][] rookDirs = {{-1, 0}, {0, 1}, {1, 0}, {0, -1}};
    //     return getLinearMoves(board, x, y, rookDirs);
    // }
    // private ArrayList<Move> getBishopMoves(Piece[][] board, int x, int y) {
    //     int[][] bishopDirs = {{-1, -1}, {-1, 1}, {1, 1}, {1, -1}};
    //     return getLinearMoves(board, x, y, bishopDirs);
    // }

    // private ArrayList<Move> getFixedMoves(Piece[][] board, int x, int y, int[][] squares) {
    //     ArrayList<Move> moves = new ArrayList<>();
    //     for (int[] move : squares) {
    //         int newX = x + move[0];
    //         int newY = y + move[1];
    //         if (newX > 7 || newX < 0 || newY > 7 || newY < 0) {
    //             continue;
    //         }
    //         if (board[newX][newY] != null && board[newX][newY].isColor(color)) {
    //             continue;
    //         }
    //         moves.add(new Move(x, y, newX, newY, board[x][y]));
    //     }
    //     return moves;
    // }

    // private ArrayList<Move> getLinearMoves(Piece[][] board, int x, int y, int[][] dirs) {
    //     ArrayList<Move> moves = new ArrayList<>();
    //     for (int[] dir : dirs) {
    //         int newX = x + dir[0];
    //         int newY = y + dir[1];
    //         while (newX >= 0 && newX < 8 && newY >= 0 && newY < 8) {
    //             if (board[newX][newY] == null) {
    //                 moves.add(new Move(x, y, newX, newY, board[x][y]));
    //             } else if (board[newX][newY].isColor(color)) {
    //                 break;
    //             } else {
    //                 moves.add(new Move(x, y, newX, newY, board[x][y]));
    //                 break;
    //             }
    //             newX += dir[0];
    //             newY += dir[1];
    //         }
    //     }
    //     return moves;
    // }

        // public ArrayList<Move> getLegalMoves() {
    //     String color = whiteToMove ? "w" : "b";
    //     // get 1d list, including moves causing check
    //     ArrayList<Move> moves = get1LayerLegalMoves(board, color);

    //     for (int i = moves.size()-1; i >= 0; i--) {
    //         // speculatively test the move (for check) on a cloned board
    //         Move m = moves.get(i);
    //         Piece[][] copy = getCopy();
    //         makeMove(m, copy);
    //         if (checkCheck(copy, color)) {
    //             moves.remove(i);
    //         }
    //     }
    //     return moves;
    // }

    // private ArrayList<Move> get1LayerLegalMoves(Piece[][] board, String color) {
    //     // gets all moves, including ones that would cause check
    //     ArrayList<Move> moves = new ArrayList<>();
    //     for (int i = 0; i < 8; i++) {
    //         for (int j = 0; j < 8; j++) {
    //             Piece p = board[i][j];
    //             if (p == null || !p.isColor(color))
    //                 continue;
    //             moves.addAll(p.getMoves(board, i, j));
    //         }
    //     }
    //     return moves;
    // }

    // private void makeMove(Move move, Piece[][] board) {
    //     // real or speculative move on real or clone board
    //     board[move.toX][move.toY] = move.p;
    //     board[move.fromX][move.fromY] = null;

    //     // special moves: promotion, castle, en passant
    //     if (move.p.getType().equals("p") && (move.toX == 0 || move.toX == 7)) {
    //         board[move.toX][move.toY] = new Piece("q", move.p.isColor("w") ? "w" : "b");
    //     }
    //     // (haven't done castle/en passant yet)
    // }

    // // check if 'color' is in check given the state of 'board'
    // private boolean checkCheck(Piece[][] board, String color) {
    //     // find king
    //     int[] king = findKing(board, color);
    //     String kingCaptureSuffix = "" + (char) ('a' + king[1]) + (king[0] + 1);

    //     // look at opponent's next moves
    //     String oppColor = (color.equals("w")) ? "b" : "w";
    //     ArrayList<Move> possibleNextMoves = get1LayerLegalMoves(board, oppColor);
    //     for (Move m : possibleNextMoves) {
    //         if (m.toString().endsWith(kingCaptureSuffix)) {
    //             return true; // in check
    //         }
    //     }
    //     return false;
    // }

    // // find king of specified color on given board
    // private int[] findKing(Piece[][] board, String color) {
    //     for (int i = 0; i < 8; i++) {
    //         for (int j = 0; j < 8; j++) {
    //             if (board[i][j] != null && board[i][j].getType().equals("k") && board[i][j].isColor(color)) {
    //                 return new int[] {i, j};
    //             }
    //         }
    //     }
    //     throw new RuntimeException(color + "king not found");
    // }

    // private Piece[][] getCopy() {
    //     Piece[][] nboard = new Piece[8][8];
    //     for (int i = 0; i < 8; i++) {
    //         for (int j = 0; j < 8; j++) {
    //             if (board[i][j] == null) {
    //                 nboard[i][j] = null;
    //             } else {
    //                 nboard[i][j] = board[i][j].clone();
    //             }
    //         }
    //     }
    //     return nboard;
    // }
}
