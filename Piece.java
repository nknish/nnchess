// TODO maybe...make a null piece its own type? no blank squares?
public class Piece {
    private String color;
    private String type;

    public Piece(String type, String color) {
        this.type = type;
        this.color = color;
        if (!color.equals("w") && !color.equals("b")) {
            throw new IllegalArgumentException("Color must be 'w' or 'b'");
        }
        if (!type.equals("k") && !type.equals("q") && !type.equals("r") && !type.equals("b") && !type.equals("n") && !type.equals("")) {
            throw new IllegalArgumentException("Type must be '', 'q', 'k', 'b', 'r', or 'n'");
        }
    }

    public String getColor() {
        return color;
    }

    public String getType() {
        return type;
    }

    @Override
    public Piece clone() {
        return new Piece(type, color);
    }

    @Override
    public String toString() {
        if (color.equals("w")) {
            switch (type) {
                case "": return "♙";
                case "p": return "♙";
                case "r": return "♖";
                case "n": return "♘";
                case "b": return "♗";
                case "q": return "♕";
                case "k": return "♔";
            }
        } else {
            switch (type) {
                case "": return "♟";
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