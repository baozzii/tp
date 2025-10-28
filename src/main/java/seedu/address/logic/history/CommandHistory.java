package seedu.address.logic.history;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.List;

/**
 * A Container to maintain the executed commands
 */
public final class CommandHistory {
    private final List<String> history = new ArrayList<>();
    private int pointer;

    /**
     * Construct an empty command history container
     */
    public CommandHistory() {
        pointer = 0;
    }

    /**
     * Add an executed command to the back of the container
     */
    public void add(String commandText) {
        requireNonNull(commandText);
        history.add(commandText.trim());
        pointer = history.size();
    }

    /**
     * Get the previous command
     */
    public String prev() {
        pointer = Math.max(pointer - 1, 0);
        if (pointer >= history.size()) {
            return null;
        }
        return history.get(pointer);
    }

    /**
     * Get the next command
     */
    public String next() {
        pointer = Math.min(pointer + 1, history.size());
        if (pointer >= history.size()) {
            return null;
        }
        return history.get(pointer);
    }

    /**
     * Return whether the pointer is at the end of the container
     */
    public boolean isEnd() {
        return pointer == history.size();
    }
}
