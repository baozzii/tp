package seedu.address.ui;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.history.CommandHistory;


/**
 * A wrapper class for UI to access command history.
 */
public class HistoryNavigator {
    private final CommandHistory history;
    private String currentText = "";

    /**
     * Creates a HistoryNavigator which wraps {@code history}.
     */
    HistoryNavigator(CommandHistory history) {
        this.history = history;
    }

    /**
     * Set the current text field to be {@code text}.
     */
    public void setCurrentText(String text) {
        requireNonNull(text);
        this.currentText = text;
    }

    /**
     * Clears the current test field.
     */
    public void clearCurrentText() {
        this.currentText = "";
    }

    /**
     * Clears the current test field.
     */
    public String getPreviousCommand() {
        String command = history.prev();
        if (command == null) {
            return currentText;
        }
        return command;
    }

    /**
     * Returns the next command.
     */
    public String getNextCommand() {
        String command = history.next();
        if (command == null) {
            return currentText;
        }
        return command;
    }

    /**
     * Returns the previous command.
     */
    public boolean isEnd() {
        return history.isEnd();
    }
}
