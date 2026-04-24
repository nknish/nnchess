package chess;

import java.io.FileWriter;
import java.io.IOException;
import java.lang.StringBuilder;

public class SaveLoad {
    public static void saveFEN(Board b, History h, String fname) {
        StringBuilder sb = new StringBuilder();

        // piece placement data
        for (int i = 7; i >= 0; i--) {
            int numBlank = 0;
            for (int j = 0; j < 8; j++) {
                Piece p = b.getPiece(i, j);
                if (p == null) {
                    numBlank++;
                } else {
                    if (numBlank > 0) {
                        sb.append(numBlank);
                        numBlank = 0;
                    }
                    if (p.isType("")) {
                        sb.append(p.isColor("w") ? "P" : "p");
                    } else if (p.isColor("w")) {
                        sb.append(p.getType().toUpperCase());
                    } else {
                        sb.append(p.getType());
                    }
                }
            }
            if (numBlank > 0) {
                sb.append(numBlank);
            }
            if (i > 0) {
                sb.append("/");
            }
        }
        sb.append(" ");

        // active color
        if (h.whiteToMove()) {
            sb.append("w");
        } else {
            sb.append("b");
        }
        sb.append(" ");

        // castling availability
        int lenBefore = sb.length();
        if (h.hasKingsideCastle("w")) {
            sb.append("K");
        }
        if (h.hasQueensideCastle("w")) {
            sb.append("Q");
        }
        if (h.hasKingsideCastle("b")) {
            sb.append("k");
        }
        if (h.hasQueensideCastle("b")) {
            sb.append("q");
        }
        if (sb.length() == lenBefore) {
            sb.append("-");
        }
        sb.append(" ");

        // en passant target square
        int[] enPassantSquare = h.getEnPassantSquare();
        if (enPassantSquare[0] == -1) {
            sb.append("-");
        } else {
            sb.append((char) ('a' + enPassantSquare[1]));
            sb.append(enPassantSquare[0] + 1);
        }
        sb.append(" ");

        // halfmove clock
        sb.append(h.getHalfmoveClock());
        sb.append(" ");

        // fullmove number
        sb.append(h.getFullmoveCount());

        // write out
        try {
            FileWriter w = new FileWriter(fname);
            w.write(sb.toString());
            w.close();
        } catch (IOException e) {
            throw new RuntimeException("couldn't write to file");
        }
    }
}
