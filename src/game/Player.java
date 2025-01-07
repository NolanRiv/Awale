package game;

import ai.MinimaxAlgorithm;
import utils.InputValidator;

public class Player {
    private int playerId; 
    private boolean isAI; 

    public Player(int playerId, boolean isAI) {
        this.playerId = playerId;
        this.isAI = isAI;
    }

    public boolean isAI() {
        return isAI;
    }

    public void setAI(boolean isAI) {
        this.isAI = isAI;
    }

    public String playTurn(Board board) {
        if (isAI) {
            return MinimaxAlgorithm.getBestMove(board, playerId);
        } else {
            return InputValidator.getValidMove(board, playerId);
        }
    }

    public int getPlayerId() {
        return playerId;
    }
}