package com.lukasz;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Main {

    private static List<String> getInput() {
        System.out.println("Enter path:");
        Scanner scanner = new Scanner(System.in);
        String[] moves = scanner.nextLine().split(",");
        return Arrays.asList(moves);

    }

    public static void main(String[] args) {
        List<String> path = getInput();
        Solution solution = new Solution(path);
        Solution solution2 = new Solution(path);

        System.out.println("Fewest number of steps required to reach child process is " + solution.getStepsToReachChildProcess());
        System.out.println("Furthest he ever got was " + solution2.calculateFurthestPosition() + " steps");
    }
}
