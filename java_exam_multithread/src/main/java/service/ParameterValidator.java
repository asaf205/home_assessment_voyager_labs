package service;

public class ParameterValidator {
    public static void validateParameters(String[] args) {
        if (args.length < 4) {
            throw new IllegalArgumentException("Insufficient number of arguments. Expected 4 arguments.");
        }

        String startUrl = args[0];
        Integer maxUrls;
        Integer maxDepth;
        try {
            maxUrls = Integer.parseInt(args[1]);
            maxDepth = Integer.parseInt(args[2]);
            parseBoolean(args[3]);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid argument type. maxUrls and maxDepth should be integers, and crossLevelUniqueness should be a boolean.");
        }

        if (startUrl == null || startUrl.isEmpty()) {
            throw new IllegalArgumentException("startUrl cannot be null or empty.");
        }

        if (maxUrls <= 0) {
            throw new IllegalArgumentException("maxUrls must be a positive integer.");
        }

        if (maxDepth <= 0) {
            throw new IllegalArgumentException("maxDepth must be a positive integer.");
        }
    }

    public static boolean parseBoolean(String value) {
        if (value.equalsIgnoreCase("true")) {
            return true;
        } else if (value.equalsIgnoreCase("false")) {
            return false;
        } else {
            throw new IllegalArgumentException("Invalid argument type. maxUrls and maxDepth should be integers, and crossLevelUniqueness should be a boolean.");
        }
    }
}
