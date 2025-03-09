package com.prisonersdilemma.exceptions;

public class InvalidChoiceHistory extends Exception {
    public InvalidChoiceHistory(String message) {
        super(message);
    }
    public static InvalidChoiceHistory invalidNumberOfSuspectsInMatrix() {
        return new InvalidChoiceHistory("Make sure only 2 suspects are present in the matrix");
    }
    public static InvalidChoiceHistory suspectHistorySizeMissMatch() {
        return new InvalidChoiceHistory("Make sure only all suspects have the same choice history size");
    }
}