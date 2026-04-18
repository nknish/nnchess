import java.util.List;
import java.util.Scanner;

public class Chess {
    public static void main(String[] args) {
        Board board = new Board();
        Scanner s = new Scanner(System.in);
        for (int i = 0; i < 1000; i++) {
            // player 1's turn
            board.printBoard();
            if (!humanMove(board, s))
                break;

            // player 2's turn
            board.printBoard();
            if (!computerMove(board))
                break;
        }
        s.close();
    }

    private static boolean humanMove(Board board, Scanner s) {
        // check for legal player moves
        Move move = null;
        boolean validMove = false;
        List<Move> moves = board.getLegalMoves();
        if (moves.size() == 0) {
            System.out.println("game over");
            return false; // player couldn't move, game over
        }

        // let the player select a (valid) move
        while (!validMove) {
            System.out.print("Enter your move: ");
            String moveString = s.nextLine();
            for (Move m : moves) {
                if (m.toString().equals(moveString)) {
                    validMove = true;
                    move = m;
                    break;
                }
            }
        }

        // make player move
        board.makeMove(move);
        return true; // player moved, game continues
    }

    private static boolean computerMove(Board board) {
        // check for legal cpu moves
        List<Move> moves = board.getLegalMoves();
        if (moves.size() == 0) {
            System.out.println("game over");
            return false; // cpu couldn't move, game over
        }
        for (Move m : moves)
            System.out.print(m + " ");

        // pick a random cpu move and make it
        int rand = (int) (Math.random() * moves.size());
        Move move = moves.get(rand);
        board.makeMove(move);
        return true; // cpu moved, game continues
    }
}
