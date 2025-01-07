package game;

public class Board {
    private static final int HOLE_COUNT = 16;
    private int[][] holes;
    private int[] playerScores; 

    public Board() {
        this.holes = new int[HOLE_COUNT][2];
        this.playerScores = new int[2];

        for (int i = 0; i < HOLE_COUNT; i++) {
            holes[i][0] = 2;
            holes[i][1] = 2;
        }
    }

    public int[][] getHoles() {
        return holes;
    }

    public void printBoard() {
        System.out.println("Current Board State:");
        for (int i = 0; i < HOLE_COUNT; i++) {
            System.out.print("Hole " + (i + 1) + ": [Red: " + holes[i][0] + ", Blue: " + holes[i][1] + "] | ");
            if ((i + 1) % 4 == 0) System.out.println();
        }
        System.out.println();
    }

    public void processMove(String move, int playerTurn) {
        int holeIndex = Integer.parseInt(move.substring(0, move.length() - 1)) - 1;
        char color = move.charAt(move.length() - 1);
        int seedsToSow = (color == 'R') ? holes[holeIndex][0] : holes[holeIndex][1];

        if (color == 'R') {
            holes[holeIndex][0] = 0;
        } else {
            holes[holeIndex][1] = 0;
        }

        int currentIndex = holeIndex;

        while (seedsToSow > 0) {
            currentIndex = (currentIndex + 1) % HOLE_COUNT;

            if (currentIndex == holeIndex)
                continue;

            if (color == 'R' && canSowRed(currentIndex, playerTurn)) {
                holes[currentIndex][0]++;
                seedsToSow--;
            }
            else if (color == 'B') {
                holes[currentIndex][1]++;
                seedsToSow--;
            }
        }

        checkForCaptures(currentIndex, playerTurn);
    }

    private void checkForCaptures(int holeIndex, int playerTurn) {

        if (playerTurn < 0 || playerTurn >= playerScores.length) {
            throw new IllegalArgumentException("Invalid playerTurn: " + playerTurn);
        }

        while (canCapture(holeIndex)) {
            playerScores[playerTurn] += holes[holeIndex][0] + holes[holeIndex][1];

            holes[holeIndex][0] = 0;
            holes[holeIndex][1] = 0;

            holeIndex = (holeIndex - 1 + HOLE_COUNT) % HOLE_COUNT;
        }
    }
    
    private boolean canCapture(int holeIndex) {
        int totalSeeds = holes[holeIndex][0] + holes[holeIndex][1];
        return totalSeeds == 2 || totalSeeds == 3;
    }

    private boolean canSowRed(int holeIndex, int playerTurn) {
        return (playerTurn == 0 && holeIndex % 2 == 1) || (playerTurn == 1 && holeIndex % 2 == 0);
    }

    public int getSeedsInHole(int holeIndex, char color) {
        if (color == 'R') {
            return holes[holeIndex][0];
        } else if (color == 'B') {
            return holes[holeIndex][1];
        } else {
            throw new IllegalArgumentException("Invalid seed color: " + color);
        }
    }
    
    public void setSeedsInHole(int holeIndex, char color, int count) {
        if (color == 'R') {
            holes[holeIndex][0] = count;
        } else if (color == 'B') {
            holes[holeIndex][1] = count;
        } else {
            throw new IllegalArgumentException("Invalid seed color: " + color);
        }
    }

    public int getTotalSeeds() {
        int total = 0;
        for (int i = 0; i < HOLE_COUNT; i++) {
            total += holes[i][0] + holes[i][1];
        }
        return total;
    }

    public boolean canPlayerPlay(int playerTurn) {
        for (int i = 0; i < holes.length; i++) {
            if ((playerTurn == 0 && i % 2 == 0) || (playerTurn == 1 && i % 2 == 1)) {
                if (holes[i][0] > 0 || holes[i][1] > 0) {
                    return true;
                }
            }
        }
        return false;
    }

    public void collectAllSeeds(int playerTurn) {
        for (int i = 0; i < holes.length; i++) {
            playerScores[playerTurn] += holes[i][0] + holes[i][1];
            holes[i][0] = 0;
            holes[i][1] = 0;
        }
    }

    public int getPlayerScore(int playerId) {
        if (playerId < 0 || playerId >= playerScores.length) {
            throw new IllegalArgumentException("Invalid player ID: " + playerId);
        }
        return playerScores[playerId];
    }

    public void setPlayerScores(int playerId, int score) {
        if (playerId < 0 || playerId > 1) {
            throw new IllegalArgumentException("Invalid player ID: " + playerId);
        }
        playerScores[playerId] = score;
    }

    public boolean hasWinner() {
        return playerScores[0] >= 33 || playerScores[1] >= 33;
    }
}
