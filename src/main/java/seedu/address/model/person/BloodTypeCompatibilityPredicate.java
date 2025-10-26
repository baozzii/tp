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
        // Map of donor blood type -> list of compatible recipient blood types
        COMPATIBLE_RECIPIENTS.put("O-", Arrays.asList("O-", "O+", "A-", "A+", "B-", "B+", "AB-", "AB+"));
        COMPATIBLE_RECIPIENTS.put("O+", Arrays.asList("O+", "A+", "B+", "AB+"));
        COMPATIBLE_RECIPIENTS.put("A-", Arrays.asList("A-", "A+", "AB-", "AB+"));
        COMPATIBLE_RECIPIENTS.put("A+", Arrays.asList("A+", "AB+"));
        COMPATIBLE_RECIPIENTS.put("B-", Arrays.asList("B-", "B+", "AB-", "AB+"));
        COMPATIBLE_RECIPIENTS.put("B+", Arrays.asList("B+", "AB+"));
        COMPATIBLE_RECIPIENTS.put("AB-", Arrays.asList("AB-", "AB+"));
        COMPATIBLE_RECIPIENTS.put("AB+", Arrays.asList("AB+"));
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
        String donorType = donorBloodType.bloodType;
        String recipientType = person.getBloodType().bloodType;

        List<String> compatibleRecipients = COMPATIBLE_RECIPIENTS.get(donorType);
        return compatibleRecipients != null && compatibleRecipients.contains(recipientType);
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

