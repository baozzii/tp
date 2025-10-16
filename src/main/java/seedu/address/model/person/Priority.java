package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Person's priority in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidPriority(Integer)}
 */
public class Priority implements Comparable<Priority> {


    public static final String MESSAGE_CONSTRAINTS =
            "Priority should be an integer between 1 and 5, where 1 being the highest priority and 5 being the lowest";
    public final Integer priority;

    /**
     * Constructs a {@code Priority}.
     *
     * @param priority A valid priority.
     */
    public Priority(Integer priority) {
        requireNonNull(priority);
        checkArgument(isValidPriority(priority), MESSAGE_CONSTRAINTS);
        this.priority = priority;
    }

    /**
     * Returns true if a given integer is a valid priority.
     */
    public static boolean isValidPriority(Integer priority) {
        return 1 <= priority && priority <= 5;
    }

    @Override
    public String toString() {
        return priority.toString();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Priority)) {
            return false;
        }

        Priority otherPriority = (Priority) other;
        return priority.equals(otherPriority.priority);
    }

    @Override
    public int hashCode() {
        return priority.hashCode();
    }

    @Override
    public int compareTo(Priority other) {
        return this.priority.compareTo(other.priority);
    }
}
