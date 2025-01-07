package main;

import game.GameEngine;

public class Main {
    public static void main(String[] args) {
        System.out.println("======================================");
        System.out.println("      Welcome to the Awalé Game!      ");
        System.out.println("======================================");
        System.out.println();

        GameEngine gameEngine = new GameEngine();

        // Start the game
        gameEngine.startGame();

        System.out.println("======================================");
        System.out.println("       Thank you for playing!         ");
        System.out.println("======================================");
    }
}