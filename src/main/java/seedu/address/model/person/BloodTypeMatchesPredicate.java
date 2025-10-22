package seedu.address.model.person;

import seedu.address.commons.util.ToStringBuilder;

import java.util.List;
import java.util.function.Predicate;

/**
 * Tests that a {@code Person}'s {@code BloodType} matches the given blood types.
 * Returns true as long as any of the blood types match
 */
public class BloodTypeMatchesPredicate implements Predicate<Person> {
    private final List<BloodType> bloodTypes;

    public BloodTypeMatchesPredicate(List<BloodType> bloodTypes) {
        this.bloodTypes = bloodTypes;
    }

    @Override
    public boolean test(Person person) {
        return bloodTypes.stream()
                .anyMatch(type -> person.getBloodType().equals(type));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof BloodTypeMatchesPredicate)) {
            return false;
        }

        BloodTypeMatchesPredicate otherBloodTypeMatchesPredicate = (BloodTypeMatchesPredicate) other;
        return bloodTypes.equals(otherBloodTypeMatchesPredicate.bloodTypes);
    }

    public String getBloodTypes() {
        return String.join(" ", bloodTypes.toString());
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).add("blood types", bloodTypes).toString();
    }
}
