package NothingChess.piece;

public class King extends Piece {

    public King(int color, int col, int row) {
        super(color, col, row);

        if (color == 0) {
            image = getImage("/piece/w-king");
        } else {
            image = getImage("/piece/b-king");
        }
    }
}