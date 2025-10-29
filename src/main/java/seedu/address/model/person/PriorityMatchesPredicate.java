package seedu.address.model.person;

import java.util.List;
import java.util.function.Predicate;

import seedu.address.commons.util.ToStringBuilder;

/**
 * Tests that a {@code Person}'s {@code Priority} matches the given Priorities.
 * Returns true as long as any of the priorities match
 */
public class PriorityMatchesPredicate implements Predicate<Person> {
    private final List<Priority> priorities;

    public PriorityMatchesPredicate(List<Priority> priorities) {
        this.priorities = priorities;
    }

    @Override
    public boolean test(Person person) {
        return priorities.contains(person.getPriority());
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof PriorityMatchesPredicate)) {
            return false;
        }

        PriorityMatchesPredicate otherPriorityMatchesPredicate = (PriorityMatchesPredicate) other;
        return priorities.equals(otherPriorityMatchesPredicate.priorities);
    }

    public String getPriorities() {
        return String.join(" ", this.priorities.toString());
    }

    public String toString() {
        return new ToStringBuilder(this).add("priorities", priorities).toString();
    }
}
