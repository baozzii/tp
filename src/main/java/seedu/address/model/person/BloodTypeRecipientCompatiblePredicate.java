package seedu.address.model.person;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

import seedu.address.commons.util.ToStringBuilder;

/**
 * Tests that a {@code Person}'s {@code BloodType} is compatible to donate to the specified recipient blood types.
 * Returns true if the person can donate to any of the specified recipient blood types.
 */
public class BloodTypeRecipientCompatiblePredicate implements Predicate<Person> {
    private static final Map<String, List<String>> DONOR_TO_RECIPIENT_MAP = new HashMap<>();

    static {
        // Map donor blood types to compatible recipient blood types
        DONOR_TO_RECIPIENT_MAP.put("O-", Arrays.asList("O-", "O+", "A-", "A+", "B-", "B+", "AB-", "AB+"));
        DONOR_TO_RECIPIENT_MAP.put("O+", Arrays.asList("O+", "A+", "B+", "AB+"));
        DONOR_TO_RECIPIENT_MAP.put("A-", Arrays.asList("A-", "A+", "AB-", "AB+"));
        DONOR_TO_RECIPIENT_MAP.put("A+", Arrays.asList("A+", "AB+"));
        DONOR_TO_RECIPIENT_MAP.put("B-", Arrays.asList("B-", "B+", "AB-", "AB+"));
        DONOR_TO_RECIPIENT_MAP.put("B+", Arrays.asList("B+", "AB+"));
        DONOR_TO_RECIPIENT_MAP.put("AB-", Arrays.asList("AB-", "AB+"));
        DONOR_TO_RECIPIENT_MAP.put("AB+", Arrays.asList("AB+"));
    }

    private final List<BloodType> recipientBloodTypes;

    public BloodTypeRecipientCompatiblePredicate(List<BloodType> recipientBloodTypes) {
        this.recipientBloodTypes = recipientBloodTypes;
    }

    @Override
    public boolean test(Person person) {
        String donorBloodType = person.getBloodType().toString();
        List<String> compatibleRecipients = DONOR_TO_RECIPIENT_MAP.get(donorBloodType);

        if (compatibleRecipients == null) {
            return false;
        }

        // Check if the donor can donate to any of the specified recipient blood types
        return recipientBloodTypes.stream()
                .anyMatch(recipientType -> compatibleRecipients.contains(recipientType.toString()));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof BloodTypeRecipientCompatiblePredicate)) {
            return false;
        }

        BloodTypeRecipientCompatiblePredicate otherPredicate = (BloodTypeRecipientCompatiblePredicate) other;
        return recipientBloodTypes.equals(otherPredicate.recipientBloodTypes);
    }

    public String getBloodTypes() {
        return String.join(" ", recipientBloodTypes.toString());
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).add("recipient blood types", recipientBloodTypes).toString();
    }
}

