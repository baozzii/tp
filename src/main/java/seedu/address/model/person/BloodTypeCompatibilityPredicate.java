package seedu.address.model.person;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

import seedu.address.commons.util.ToStringBuilder;

/**
 * Tests that a {@code Person}'s {@code BloodType} is compatible with the specified blood type.
 * Compatibility is based on blood donation rules (who can donate to whom).
 */
public class BloodTypeCompatibilityPredicate implements Predicate<Person> {
    private static final Map<String, List<String>> COMPATIBLE_DONORS = new HashMap<>();

    static {
        // Map of recipient blood type -> list of compatible donor blood types
        COMPATIBLE_DONORS.put("O-", Arrays.asList("O-"));
        COMPATIBLE_DONORS.put("O+", Arrays.asList("O-", "O+"));
        COMPATIBLE_DONORS.put("A-", Arrays.asList("O-", "A-"));
        COMPATIBLE_DONORS.put("A+", Arrays.asList("O-", "O+", "A-", "A+"));
        COMPATIBLE_DONORS.put("B-", Arrays.asList("O-", "B-"));
        COMPATIBLE_DONORS.put("B+", Arrays.asList("O-", "O+", "B-", "B+"));
        COMPATIBLE_DONORS.put("AB-", Arrays.asList("O-", "A-", "B-", "AB-"));
        COMPATIBLE_DONORS.put("AB+", Arrays.asList("O-", "O+", "A-", "A+", "B-", "B+", "AB-", "AB+"));
    }

    private final BloodType recipientBloodType;

    /**
     * Constructs a BloodTypeCompatibilityPredicate.
     *
     * @param recipientBloodType The blood type of the person who needs a donation.
     */
    public BloodTypeCompatibilityPredicate(BloodType recipientBloodType) {
        this.recipientBloodType = recipientBloodType;
    }

    @Override
    public boolean test(Person person) {
        String recipientType = recipientBloodType.bloodType;
        String donorType = person.getBloodType().bloodType;

        List<String> compatibleDonors = COMPATIBLE_DONORS.get(recipientType);
        return compatibleDonors != null && compatibleDonors.contains(donorType);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof BloodTypeCompatibilityPredicate)) {
            return false;
        }

        BloodTypeCompatibilityPredicate otherPredicate = (BloodTypeCompatibilityPredicate) other;
        return recipientBloodType.equals(otherPredicate.recipientBloodType);
    }

    public String getRecipientBloodType() {
        return recipientBloodType.bloodType;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).add("recipientBloodType", recipientBloodType).toString();
    }
}

