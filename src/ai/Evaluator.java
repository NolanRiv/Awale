package ai;

import game.Board;

public class Evaluator {
    
    public static int evaluate(Board board, int playerId) {
        if (playerId < 0 || playerId > 1) {
            throw new IllegalArgumentException("Invalid player ID: " + playerId);
        }

        int opponentId = 1 - playerId;

        int playerScore = board.getPlayerScore(playerId);
        int opponentScore = board.getPlayerScore(opponentId);
        int capturedDifference = playerScore - opponentScore;

        int playerControlledSeeds = getControlledSeeds(board, playerId);
        int opponentControlledSeeds = getControlledSeeds(board, opponentId);
        int controlledDifference = playerControlledSeeds - opponentControlledSeeds;

        int playerPotentialCaptures = getPotentialCaptures(board, playerId);
        int opponentPotentialCaptures = getPotentialCaptures(board, opponentId);
        int potentialDifference = playerPotentialCaptures - opponentPotentialCaptures;

        int starvationPotential = getStarvationPotential(board, opponentId);

        int threatPotential = getThreatPotential(board, playerId);

        return (capturedDifference * 10) +  
               (controlledDifference * 3) + 
               (potentialDifference * 5) +  
               (starvationPotential * 2) +  
               (threatPotential * 4);       
    }

    private static int getControlledSeeds(Board board, int playerId) {
        int totalSeeds = 0;
        for (int i = 0; i < 16; i++) {
            if ((playerId == 0 && i % 2 == 0) || (playerId == 1 && i % 2 == 1)) {
                totalSeeds += board.getSeedsInHole(i, 'R') + board.getSeedsInHole(i, 'B');
            }
        }
        return totalSeeds;
    }

    private static int getPotentialCaptures(Board board, int playerId) {
        int potentialCaptures = 0;
        for (int i = 0; i < 16; i++) {
            if ((playerId == 0 && i % 2 == 0) || (playerId == 1 && i % 2 == 1)) {
                int totalSeeds = board.getSeedsInHole(i, 'R') + board.getSeedsInHole(i, 'B');
                if (totalSeeds == 2 || totalSeeds == 3) {
                    potentialCaptures++;
                }
            }
        }
        return potentialCaptures;
    }

    private static int getStarvationPotential(Board board, int opponentId) {
        int starvationCount = 0;
        for (int i = 0; i < 16; i++) {
            if ((opponentId == 0 && i % 2 == 0) || (opponentId == 1 && i % 2 == 1)) {
                if (board.getSeedsInHole(i, 'R') + board.getSeedsInHole(i, 'B') == 0) {
                    starvationCount++;
                }
            }
        }
        return starvationCount;
    }

    private static int getThreatPotential(Board board, int playerId) {
        int threatScore = 0;
        for (int i = 0; i < 16; i++) {
            if ((playerId == 0 && i % 2 == 1) || (playerId == 1 && i % 2 == 0)) {
                int totalSeeds = board.getSeedsInHole(i, 'R') + board.getSeedsInHole(i, 'B');
                if (totalSeeds > 3) {
                    threatScore += 2;
                }
            }
        }
        return threatScore;
    }
}