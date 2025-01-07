package ai;

import game.Board;
import game.Rules;
import utils.ConsolePrinter;

import java.util.*;

public class MinimaxAlgorithm {

    private static final int MAX_DEPTH = 6;
    private static final boolean USE_ALPHA_BETA = true;
    private static final int HOLE_COUNT = 16;

    private static final long TIME_LIMIT_MS = 2000;
    private static long startTime;

    private static Map<String, Integer> transpositionTable = new HashMap<>();

    public static String getBestMove(Board board, int playerId) {
        startTime = System.currentTimeMillis();
        int bestScore = Integer.MIN_VALUE;
        String bestMove = null;

        List<String> possibleMoves = generateOrderedMoves(board, playerId);

        for (String move : possibleMoves) {
            Board simulatedBoard = cloneBoard(board);
            simulatedBoard.processMove(move, playerId);

            int depth = getDynamicDepth(simulatedBoard);
            int score = minimax(simulatedBoard, depth, false, playerId, Integer.MIN_VALUE, Integer.MAX_VALUE);

            if (score > bestScore) {
                bestScore = score;
                bestMove = move;
            }

            if (isTimeExceeded()) {
                break;
            }
        }

        if (bestMove == null && !possibleMoves.isEmpty()) {
            Random random = new Random();
            bestMove = possibleMoves.get(random.nextInt(possibleMoves.size()));
            ConsolePrinter.printMessage("AI chose a random move: " + bestMove);
        }

        if (bestMove == null) {
            throw new IllegalStateException("No valid moves found for player " + playerId);
        }

        return bestMove;
    }

    private static int minimax(Board board, int depth, boolean isMaximizing, int playerId, int alpha, int beta) {

        if (isTimeExceeded()) {
            return 0;
        }

        String boardKey = board.toString() + depth + isMaximizing;

        if (transpositionTable.containsKey(boardKey)) {
            return transpositionTable.get(boardKey);
        }

        if (depth == 0 || Rules.shouldEndGame(board)) {
            int evaluation = Evaluator.evaluate(board, playerId);
            transpositionTable.put(boardKey, evaluation);
            return evaluation;
        }

        int opponentId = 1 - playerId;
        int bestScore = isMaximizing ? Integer.MIN_VALUE : Integer.MAX_VALUE;

        List<String> possibleMoves = generateOrderedMoves(board, isMaximizing ? playerId : opponentId);

        for (String move : possibleMoves) {
            Board simulatedBoard = cloneBoard(board);
            simulatedBoard.processMove(move, isMaximizing ? playerId : opponentId);

            int score = minimax(simulatedBoard, depth - 1, !isMaximizing, playerId, alpha, beta);

            if (isMaximizing) {
                bestScore = Math.max(bestScore, score);
                alpha = Math.max(alpha, score);
            } else {
                bestScore = Math.min(bestScore, score);
                beta = Math.min(beta, score);
            }

            if (USE_ALPHA_BETA && beta <= alpha) {
                break;
            }

            if (isTimeExceeded()) {
                break;
            }
        }

        transpositionTable.put(boardKey, bestScore);
        return bestScore;
    }

    private static boolean isTimeExceeded() {
        return (System.currentTimeMillis() - startTime) > TIME_LIMIT_MS;
    }

    private static List<String> generateOrderedMoves(Board board, int playerId) {
        List<String> moves = new ArrayList<>();

        for (int i = 0; i < HOLE_COUNT; i++) {
            for (char color : new char[] { 'R', 'B' }) {
                String move = (i + 1) + "" + color;
                if (Rules.isValidMove(move, playerId, board)) {
                    moves.add(move);
                }
            }
        }

        moves.sort((move1, move2) -> {
            Board tempBoard1 = cloneBoard(board);
            Board tempBoard2 = cloneBoard(board);
            tempBoard1.processMove(move1, playerId);
            tempBoard2.processMove(move2, playerId);

            int score1 = Evaluator.evaluate(tempBoard1, playerId);
            int score2 = Evaluator.evaluate(tempBoard2, playerId);

            return Integer.compare(score2, score1);
        });

        return moves;
    }

    private static int getDynamicDepth(Board board) {
        int remainingSeeds = board.getTotalSeeds();
        int potentialCaptures = calculatePotentialCaptures(board);

        if (remainingSeeds < 10 || potentialCaptures > 5) return 10;
        if (remainingSeeds < 20) return 8;
        return MAX_DEPTH;
    }

    private static int calculatePotentialCaptures(Board board) {
        int potentialCaptures = 0;
        for (int i = 0; i < HOLE_COUNT; i++) {
            if (board.getSeedsInHole(i, 'R') + board.getSeedsInHole(i, 'B') == 2 ||
                board.getSeedsInHole(i, 'R') + board.getSeedsInHole(i, 'B') == 3) {
                potentialCaptures++;
            }
        }
        return potentialCaptures;
    }

    private static Board cloneBoard(Board board) {
        Board clonedBoard = new Board();

        for (int i = 0; i < HOLE_COUNT; i++) {
            clonedBoard.setSeedsInHole(i, 'R', board.getSeedsInHole(i, 'R'));
            clonedBoard.setSeedsInHole(i, 'B', board.getSeedsInHole(i, 'B'));
        }

        clonedBoard.setPlayerScores(0, board.getPlayerScore(0));
        clonedBoard.setPlayerScores(1, board.getPlayerScore(1));

        return clonedBoard;
    }
}