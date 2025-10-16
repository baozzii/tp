package seedu.address.model.person;

import java.util.function.Predicate;

import seedu.address.commons.util.ToStringBuilder;

/**
 * Tests that a {@code Person}'s {@code Organ} contains the given substring.
 * Keyword matching is case insensitive and uses substring search.
 */
public class OrganContainsSubstringPredicate implements Predicate<Person> {
    private final String substring;

    public OrganContainsSubstringPredicate(String substring) {
        this.substring = substring;
    }

    @Override
    public boolean test(Person person) {
        return person.getOrgan().organName.toLowerCase().contains(substring.toLowerCase());
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof OrganContainsSubstringPredicate)) {
            return false;
        }

        OrganContainsSubstringPredicate otherPredicate = (OrganContainsSubstringPredicate) other;
        return substring.equals(otherPredicate.substring);
    }

    public String getSubstring() {
        return substring;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).add("substring", substring).toString();
    }
}

