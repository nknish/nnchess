import java.util.ArrayList;

public class Piece {
    private String color;
    private String type;

    public Piece(String type, String color) {
        this.type = type;
        this.color = color;
        if (!color.equals("w") && !color.equals("b")) {
            throw new IllegalArgumentException("Color must be 'w' or 'b'");
        }
        if (!type.equals("k") && !type.equals("q") && !type.equals("r") && !type.equals("b") && !type.equals("n") && !type.equals("p")) {
            throw new IllegalArgumentException("Type must be 'p', 'q', 'k', 'b', 'r', or 'n'");
        }
    }

    public boolean isColor(String color) {
        return this.color.equals(color);
    }

    public String getType() {
        return type;
    }

    public ArrayList<Move> getMoves(Piece[][] board, int x, int y) {
        ArrayList<Move> moves = new ArrayList<>();
        if (type.equals("p")) {
            moves.addAll(getPawnMoves(board, x, y));
        }
        if (type.equals("k")) {
            moves.addAll(getKingMoves(board, x, y));
        }
        if (type.equals("n")) {
            moves.addAll(getKnightMoves(board, x, y));
        }
        if (type.equals("q") || type.equals("r")) {
            moves.addAll(getRookMoves(board, x, y));
        }
        if (type.equals("q") || type.equals("b")) {
            moves.addAll(getBishopMoves(board, x, y));
        }
        return moves;
    }

    private ArrayList<Move> getPawnMoves(Piece[][] board, int x, int y) {
        ArrayList<Move> moves = new ArrayList<>();
        int dir = color.equals("w") ? 1 : -1;
        // forward moves
        if (board[x+dir][y] == null) {
            moves.add(new Move(x, y, x+dir, y, board[x][y]));
            // two spaces
            if ((dir == 1 && x == 1) || (dir == -1 && x == 6)) {
                if (board[x+2*dir][y] == null) {
                    moves.add(new Move(x, y, x+2*dir, y, board[x][y]));
                }
            }
        }
        // captures
        if (y > 0) {
            Piece c1 = board[x+dir][y-1];
            if (c1 != null && !c1.isColor(color)) {
                moves.add(new Move(x, y, x+dir, y-1, board[x][y]));
            }
        }
        if (y < 7) {
            Piece c2 = board[x+dir][y+1];
            if (c2 != null && !c2.isColor(color)) {
                moves.add(new Move(x, y, x+dir, y+1, board[x][y]));
            }
        }
        return moves;
    }

    private ArrayList<Move> getKingMoves(Piece[][] board, int x, int y) {
        int[][] kingSquares = {
            {-1, -1}, {-1, 0}, {-1, 1}, {0, 1},
            {1, 1}, {1, 0}, {1, -1}, {0, -1}
        };
        return getFixedMoves(board, x, y, kingSquares);
    }
    private ArrayList<Move> getKnightMoves(Piece[][] board, int x, int y) {
        int[][] knightSquares = {
            {2, 1}, {2, -1}, {-2, 1}, {-2, -1},
            {1, 2}, {1, -2}, {-1, 2}, {-1, -2}
        };
        return getFixedMoves(board, x, y, knightSquares);
    }

    private ArrayList<Move> getRookMoves(Piece[][] board, int x, int y) {
        int[][] rookDirs = {{-1, 0}, {0, 1}, {1, 0}, {0, -1}};
        return getLinearMoves(board, x, y, rookDirs);
    }
    private ArrayList<Move> getBishopMoves(Piece[][] board, int x, int y) {
        int[][] bishopDirs = {{-1, -1}, {-1, 1}, {1, 1}, {1, -1}};
        return getLinearMoves(board, x, y, bishopDirs);
    }

    private ArrayList<Move> getFixedMoves(Piece[][] board, int x, int y, int[][] squares) {
        ArrayList<Move> moves = new ArrayList<>();
        for (int[] move : squares) {
            int newX = x + move[0];
            int newY = y + move[1];
            if (newX > 7 || newX < 0 || newY > 7 || newY < 0) {
                continue;
            }
            if (board[newX][newY] != null && board[newX][newY].isColor(color)) {
                continue;
            }
            moves.add(new Move(x, y, newX, newY, board[x][y]));
        }
        return moves;
    }

    private ArrayList<Move> getLinearMoves(Piece[][] board, int x, int y, int[][] dirs) {
        ArrayList<Move> moves = new ArrayList<>();
        for (int[] dir : dirs) {
            int newX = x + dir[0];
            int newY = y + dir[1];
            while (newX >= 0 && newX < 8 && newY >= 0 && newY < 8) {
                if (board[newX][newY] == null) {
                    moves.add(new Move(x, y, newX, newY, board[x][y]));
                } else if (board[newX][newY].isColor(color)) {
                    break;
                } else {
                    moves.add(new Move(x, y, newX, newY, board[x][y]));
                    break;
                }
                newX += dir[0];
                newY += dir[1];
            }
        }
        return moves;
    }

    @Override
    public Piece clone() {
        return new Piece(type, color);
    }

    @Override
    public String toString() {
        if (color.equals("w")) {
            switch (type) {
                case "p": return "♙";
                case "r": return "♖";
                case "n": return "♘";
                case "b": return "♗";
                case "q": return "♕";
                case "k": return "♔";
            }
        } else {
            switch (type) {
                case "p": return "♟";
                case "r": return "♜";
                case "n": return "♞";
                case "b": return "♝";
                case "q": return "♛";
                case "k": return "♚";
            }
        }
        throw new IllegalArgumentException("Invalid piece type");
    }
}