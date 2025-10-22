package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Person's blood type in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidBloodType(String)}
 */
public class BloodType {

    public static final String MESSAGE_CONSTRAINTS =
            "BloodType should be A, B, AB, O only";

    public final String bloodType;

    /**
     * Constructs a {@code Priority}.
     *
     * @param bloodType A valid blood type.
     */
    public BloodType(String bloodType) {
        requireNonNull(bloodType);
        checkArgument(isValidBloodType(bloodType), MESSAGE_CONSTRAINTS);
        this.bloodType = bloodType;
    }

    /**
     * Returns true if a given integer is a valid blood type.
     */
    public static boolean isValidBloodType(String bloodType) {
        return BloodTypeEnum.isValid(bloodType);
    }

    @Override
    public String toString() {
        return bloodType;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof BloodType)) {
            return false;
        }

        BloodType otherBloodType = (BloodType) other;
        return bloodType.equals(otherBloodType.bloodType);
    }

    @Override
    public int hashCode() {
        return bloodType.hashCode();
    }
}

