package seedu.address.model.person;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

import seedu.address.commons.util.ToStringBuilder;

/**
 * Tests that a {@code Person}'s {@code BloodType} is compatible with the specified donor blood type.
 * Finds recipients who can receive from the specified donor.
 */
public class BloodTypeCompatibilityPredicate implements Predicate<Person> {
    private static final Map<String, List<String>> COMPATIBLE_RECIPIENTS = new HashMap<>();

    static {
        // Map of recipient blood type -> list of compatible donor blood types
        COMPATIBLE_RECIPIENTS.put("O-", Arrays.asList("O-"));
        COMPATIBLE_RECIPIENTS.put("O+", Arrays.asList("O-", "O+"));
        COMPATIBLE_RECIPIENTS.put("A-", Arrays.asList("O-", "A-"));
        COMPATIBLE_RECIPIENTS.put("A+", Arrays.asList("O-", "O+", "A-", "A+"));
        COMPATIBLE_RECIPIENTS.put("B-", Arrays.asList("O-", "B-"));
        COMPATIBLE_RECIPIENTS.put("B+", Arrays.asList("O-", "O+", "B-", "B+"));
        COMPATIBLE_RECIPIENTS.put("AB-", Arrays.asList("O-", "A-", "B-", "AB-"));
        COMPATIBLE_RECIPIENTS.put("AB+", Arrays.asList("O-", "O+", "A-", "A+", "B-", "B+", "AB-", "AB+"));
    }

    private final BloodType donorBloodType;

    /**
     * Constructs a BloodTypeCompatibilityPredicate.
     *
     * @param donorBloodType The blood type of the donor.
     */
    public BloodTypeCompatibilityPredicate(BloodType donorBloodType) {
        this.donorBloodType = donorBloodType;
    }

    @Override
    public boolean test(Person person) {
        String donorType = donorBloodType.bloodType.toUpperCase();
        String recipientType = person.getBloodType().bloodType.toUpperCase();

        List<String> compatibleDonors = COMPATIBLE_RECIPIENTS.get(recipientType);
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
        return donorBloodType.equals(otherPredicate.donorBloodType);
    }

    public String getDonorBloodType() {
        return donorBloodType.bloodType;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).add("donorBloodType", donorBloodType).toString();
    }
}

