package chess;

import java.util.List;
import java.util.Scanner;

public class Player {
    private boolean isHuman;
    private Scanner s;

    public Player(boolean isHuman) {
        this.isHuman = isHuman;
        s = new Scanner(System.in);
    }

    public boolean isHuman() {
        return isHuman;
    }

    public Move getMove(List<Move> moves) {
        if (isHuman) {
            return getHumanMove(moves);
        } else {
            return getComputerMove(moves);
        }
    }

    private Move getHumanMove(List<Move> moves) {
        // let the player select a (valid) move
        while (true) {
            System.out.print("Enter your move: ");
            String moveString = s.nextLine();
            String pr = "";
            for (Move m : moves) {
                if (m.toString().equals(moveString)) {
                    if (m.isPromotion()) {
                        if (pr.equals("")) {
                            while (true) {
                                System.out.print("Choose promotion piece: ");
                                pr = s.nextLine();
                                if (pr.equals("q") || pr.equals("r") || pr.equals("b") || pr.equals("n")) {
                                    break;
                                }
                            }
                        }
                        if (m.getPromotionPiece().equals(pr)) {
                            return m;
                        }
                    } else {
                        return m;
                    }
                }
            }
        }
    }

    private Move getComputerMove(List<Move> moves) {
        // pick a random legal move
        int rand = (int) (Math.random() * moves.size());
        return moves.get(rand);
    }
}
