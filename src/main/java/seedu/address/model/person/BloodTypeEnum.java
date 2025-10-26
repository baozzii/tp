package seedu.address.model.person;

import static java.util.Objects.requireNonNull;

/**
 * Represents all possible blood types.
 */
public enum BloodTypeEnum {
    A_POSITIVE("A+"),
    A_NEGATIVE("A-"),
    B_POSITIVE("B+"),
    B_NEGATIVE("B-"),
    AB_POSITIVE("AB+"),
    AB_NEGATIVE("AB-"),
    O_POSITIVE("O+"),
    O_NEGATIVE("O-");

    private final String label;

    /**
     * Constructor for each enum constant.
     */
    BloodTypeEnum(String label) {
        this.label = label;
    }
    /**
     * Returns true if a given string is a valid blood type.
     */
    public static boolean isValidBloodType(String bloodType) {
        requireNonNull(bloodType);
        // Loop through all enum constants
        for (BloodTypeEnum type : BloodTypeEnum.values()) {
            // Check if the input value matches the label of any enum constant
            if (type.getLabel().equalsIgnoreCase(bloodType)) {
                return true;
            }
        }
        return false;
    }

    /**
     *  Returns the label of the blood type
     */
    private String getLabel() {
        return label;
    }
    @Override
    public String toString() {
        return label;
    }
}
