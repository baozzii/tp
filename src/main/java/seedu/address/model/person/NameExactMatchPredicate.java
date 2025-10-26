package seedu.address.model.person;

import java.util.function.Predicate;

import seedu.address.commons.util.ToStringBuilder;

/**
 * Tests that a {@code Person}'s {@code Name} matches the given name exactly (case-insensitive).
 */
public class NameExactMatchPredicate implements Predicate<Person> {
    private final String name;

    public NameExactMatchPredicate(String name) {
        this.name = name;
    }

    @Override
    public boolean test(Person person) {
        return person.getName().fullName.equalsIgnoreCase(name);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof NameExactMatchPredicate)) {
            return false;
        }

        NameExactMatchPredicate otherNameExactMatchPredicate = (NameExactMatchPredicate) other;
        return name.equalsIgnoreCase(otherNameExactMatchPredicate.name);
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).add("name", name).toString();
    }
}

