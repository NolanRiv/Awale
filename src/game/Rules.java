package game;

public class Rules {

    public static boolean isValidMove(String move, int playerTurn, Board board) {
        if (move == null || move.length() < 2) {
            return false;
        }

        try {
            int hole = Integer.parseInt(move.substring(0, move.length() - 1)) - 1;
            char color = move.charAt(move.length() - 1);

            if (hole < 0 || hole >= 16) {
                return false;
            }

            if (color != 'R' && color != 'B') {
                return false;
            }

            if (playerTurn == 0 && hole % 2 != 0) {
                return false;
            } else if (playerTurn == 1 && hole % 2 != 1) {
                return false;
            }

            if (color == 'R' && board.getSeedsInHole(hole, 'R') == 0) {
                return false;
            }
            if (color == 'B' && board.getSeedsInHole(hole, 'B') == 0) {
                return false;
            }

            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static boolean canCapture(int holeIndex, Board board) {
        int totalSeeds = board.getSeedsInHole(holeIndex, 'R') + board.getSeedsInHole(holeIndex, 'B');
        return totalSeeds == 2 || totalSeeds == 3;
    }

    public static boolean canSowRed(int holeIndex, int playerTurn) {
        return (playerTurn == 0 && holeIndex % 2 == 1) || (playerTurn == 1 && holeIndex % 2 == 0);
    }

    public static boolean shouldEndGame(Board board) {
        return board.getTotalSeeds() < 8;
    }
}
