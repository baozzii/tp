package seedu.address.model.person;

import java.util.Optional;
import java.util.function.Predicate;

import seedu.address.commons.util.ToStringBuilder;

/**
 * Tests that a {@code Person} matches all specified criteria.
 * Combines name, organ, and blood type predicates with AND logic.
 */
public class CombinedPredicate implements Predicate<Person> {
    private final Optional<NameContainsKeywordsPredicate> namePredicate;
    private final Optional<OrganContainsSubstringPredicate> organPredicate;
    private final Optional<BloodTypeMatchesPredicate> bloodTypePredicate;

    /**
     * Constructs a CombinedPredicate with the given predicates.
     * At least one predicate must be present.
     */
    public CombinedPredicate(Optional<NameContainsKeywordsPredicate> namePredicate,
                            Optional<OrganContainsSubstringPredicate> organPredicate,
                            Optional<BloodTypeMatchesPredicate> bloodTypePredicate) {
        this.namePredicate = namePredicate;
        this.organPredicate = organPredicate;
        this.bloodTypePredicate = bloodTypePredicate;
    }

    @Override
    public boolean test(Person person) {
        boolean nameMatches = namePredicate.map(p -> p.test(person)).orElse(true);
        boolean organMatches = organPredicate.map(p -> p.test(person)).orElse(true);
        boolean bloodTypeMatches = bloodTypePredicate.map(p -> p.test(person)).orElse(true);

        return nameMatches && organMatches && bloodTypeMatches;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof CombinedPredicate)) {
            return false;
        }

        CombinedPredicate otherPredicate = (CombinedPredicate) other;
        return namePredicate.equals(otherPredicate.namePredicate)
                && organPredicate.equals(otherPredicate.organPredicate)
                && bloodTypePredicate.equals(otherPredicate.bloodTypePredicate);
    }

    public String getCriteria() {
        StringBuilder sb = new StringBuilder();
        namePredicate.ifPresent(p -> sb.append("name: ").append(p.getKeywords()).append(" "));
        organPredicate.ifPresent(p -> sb.append("organ: ").append(p.getSubstring()).append(" "));
        bloodTypePredicate.ifPresent(p -> sb.append("blood type: ").append(p.getBloodTypes()).append(" "));
        return sb.toString().trim();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("namePredicate", namePredicate)
                .add("organPredicate", organPredicate)
                .add("bloodTypePredicate", bloodTypePredicate)
                .toString();
    }
}

