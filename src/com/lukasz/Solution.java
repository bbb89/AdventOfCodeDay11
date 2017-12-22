package com.lukasz;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Solution {
    public static final String[] directions = { "sw", "s", "se", "nw", "n", "ne" } ;

    private List<String> path;
    private Map<String, Integer> movesNumbers;
    private int stepsToReachChildProcess;

    public Solution(List<String> path) {
        this.path = path;
        movesNumbers = new HashMap<>();

        movesNumbers = fillMapOfDirections(path);

        calculatePath();

    }

    public int calculateFurthestPosition() {
        int max = 0;

        for(int i = 0; i < path.size(); i++) {
            List<String> currentPath = new ArrayList<>();
            currentPath.addAll(path.subList(0,i));
            movesNumbers = fillMapOfDirections(currentPath);
            calculatePath();

            if(stepsToReachChildProcess > max) max = stepsToReachChildProcess;
        }

        return max;
    }

    private void calculatePath() {
        reduceDirections("se", "nw");
        reduceDirections("sw", "ne");

        centerDirections("s");
        centerDirections("n");
        reduceDirections("s", "n");

        reduceDiagonals();

        sumSteps();
    }

    private Map<String, Integer> fillMapOfDirections(List<String> path) {
        Map<String, Integer> mapOfDirections = new HashMap<>();

        for(String currentMove : directions) {
            int currentMoveNumber = (int) path.stream().filter( s -> s.equals(currentMove) ).count();
            mapOfDirections.put(currentMove, currentMoveNumber);
        }

         return mapOfDirections;
    }

    public int getStepsToReachChildProcess() {
        return stepsToReachChildProcess;
    }

    private void sumSteps() {
        stepsToReachChildProcess = 0;

        for(String direction : movesNumbers.keySet()) {
            stepsToReachChildProcess += movesNumbers.get(direction);
        }
    }

    private void reduceDirections(String direction1, String direction2) {
        if ( movesNumbers.get(direction1) > (movesNumbers.get(direction2)) ) {
            int difference = movesNumbers.get(direction1) - movesNumbers.get(direction2);
            movesNumbers.put(direction1, difference);
            movesNumbers.put(direction2, 0);
        }else {
            int difference = movesNumbers.get(direction2) - movesNumbers.get(direction1);
            movesNumbers.put(direction1, 0);
            movesNumbers.put(direction2, difference);
        }
    }

    private void centerDirections(String direction) {
        String directionW = direction + "w";
        String directionE = direction + "e";
        int valueToAdd = Math.min(movesNumbers.get(directionW), movesNumbers.get(directionE));
        movesNumbers.put(direction, movesNumbers.get(direction) + valueToAdd);
        reduceDirections(directionW, directionE);
    }

    private void reduceDiagonal(String direction, String diagonalToReduce) {
        int difference = Math.abs(movesNumbers.get(direction) - movesNumbers.get(diagonalToReduce));
        String directionToAdd = direction + diagonalToReduce.charAt(1);
        movesNumbers.put(directionToAdd, movesNumbers.get(directionToAdd) + Math.min(movesNumbers.get(direction), movesNumbers.get(diagonalToReduce)));

        if(movesNumbers.get(direction) > movesNumbers.get(diagonalToReduce)) {
            movesNumbers.put(direction, difference);
            movesNumbers.put(diagonalToReduce, 0);
        }else {
            movesNumbers.put(direction, 0);
            movesNumbers.put(diagonalToReduce, difference);
        }
    }

    private void reduceDiagonals() {
        if(movesNumbers.get("n") > 0) {
            if(movesNumbers.get("sw") > 0) {
                reduceDiagonal("n", "sw");
            }
            if(movesNumbers.get("se") > 0) {
                reduceDiagonal("n", "se");
            }
        }
        if(movesNumbers.get("s") > 0) {
            if(movesNumbers.get("nw") > 0) {
                reduceDiagonal("s", "nw");
            }
            if(movesNumbers.get("ne") > 0) {
                reduceDiagonal("s", "ne");
            }
        }
    }
}
