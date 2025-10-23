package seedu.address.storage;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.person.Address;
import seedu.address.model.person.BloodType;
import seedu.address.model.person.Email;
import seedu.address.model.person.EmergencyContact;
import seedu.address.model.person.Name;
import seedu.address.model.person.Organ;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Priority;
import seedu.address.model.tag.Tag;

/**
 * Jackson-friendly version of {@link Person}.
 */
class JsonAdaptedPerson {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Person's %s field is missing!";

    private final String name;
    private final String phone;
    private final String email;
    private final String address;
    private final String organ;
    private final String bloodType;
    private final String emergencyContactName;
    private final String emergencyContactPhone;
    private final String emergencyContactRelationship;
    private final Integer priority;
    private final List<JsonAdaptedTag> tags = new ArrayList<>();

    /**
     * Constructs a {@code JsonAdaptedPerson} with the given person details.
     */
    @JsonCreator
    public JsonAdaptedPerson(@JsonProperty("name") String name, @JsonProperty("phone") String phone,
            @JsonProperty("email") String email, @JsonProperty("address") String address,
            @JsonProperty("organ") String organ, @JsonProperty("blood type") String bloodType,
            @JsonProperty("priority") Integer priority, @JsonProperty("tags") List<JsonAdaptedTag> tags,
            @JsonProperty("emergencyContactName") String emergencyContactName,
            @JsonProperty("emergencyContactPhone") String emergencyContactPhone,
            @JsonProperty("emergencyContactRelationship") String emergencyContactRelationship) {
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.organ = organ;
        this.bloodType = bloodType;
        this.priority = priority;
        if (tags != null) {
            this.tags.addAll(tags);
        }
        this.emergencyContactName = emergencyContactName;
        this.emergencyContactPhone = emergencyContactPhone;
        this.emergencyContactRelationship = emergencyContactRelationship;
    }

    /**
     * Converts a given {@code Person} into this class for Jackson use.
     */
    public JsonAdaptedPerson(Person source) {
        name = source.getName().fullName;
        phone = source.getPhone().value;
        email = source.getEmail().value;
        address = source.getAddress().value;
        organ = source.getOrgan().organName;
        bloodType = source.getBloodType().bloodType;
        priority = source.getPriority().priority;
        tags.addAll(source.getTags().stream()
                .map(JsonAdaptedTag::new)
                .collect(Collectors.toList()));
        EmergencyContact ec = source.getEmergencyContact();
        this.emergencyContactName = ec == null ? null : ec.getName().fullName;
        this.emergencyContactPhone = ec == null ? null : ec.getPhone().value;
        this.emergencyContactRelationship = ec == null ? null : ec.getRelationship();
    }

    /**
     * Converts this Jackson-friendly adapted person object into the model's {@code Person} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted person.
     */
    public Person toModelType() throws IllegalValueException {
        final List<Tag> personTags = new ArrayList<>();
        for (JsonAdaptedTag tag : tags) {
            personTags.add(tag.toModelType());
        }
        EmergencyContact modelEmergencyContact = null;
        if (emergencyContactName != null || emergencyContactPhone != null) {
            if (emergencyContactName == null) {
                throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                        EmergencyContact.class.getSimpleName() + " Name"));
            }
            if (emergencyContactPhone == null) {
                throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                        EmergencyContact.class.getSimpleName() + " Phone"));
            }
            if (!Name.isValidName(emergencyContactName)) {
                throw new IllegalValueException(Name.MESSAGE_CONSTRAINTS);
            }
            if (!Phone.isValidPhone(emergencyContactPhone)) {
                throw new IllegalValueException(Phone.MESSAGE_CONSTRAINTS);
            }
            final Name modelEcName = new Name(emergencyContactName);
            final Phone modelEcPhone = new Phone(emergencyContactPhone);
            final String modelEcRelation = emergencyContactRelationship == null ? "" : emergencyContactRelationship;
            modelEmergencyContact = new EmergencyContact(modelEcName, modelEcPhone, modelEcRelation);
        }
        if (name == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName()));
        }
        if (!Name.isValidName(name)) {
            throw new IllegalValueException(Name.MESSAGE_CONSTRAINTS);
        }
        final Name modelName = new Name(name);

        if (phone == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Phone.class.getSimpleName()));
        }
        if (!Phone.isValidPhone(phone)) {
            throw new IllegalValueException(Phone.MESSAGE_CONSTRAINTS);
        }
        final Phone modelPhone = new Phone(phone);

        if (email == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Email.class.getSimpleName()));
        }
        if (!Email.isValidEmail(email)) {
            throw new IllegalValueException(Email.MESSAGE_CONSTRAINTS);
        }
        final Email modelEmail = new Email(email);

        if (address == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Address.class.getSimpleName()));
        }
        if (!Address.isValidAddress(address)) {
            throw new IllegalValueException(Address.MESSAGE_CONSTRAINTS);
        }
        final Address modelAddress = new Address(address);

        if (organ == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Organ.class.getSimpleName()));
        }
        if (!Organ.isValidOrgan(organ)) {
            throw new IllegalValueException(Organ.MESSAGE_CONSTRAINTS);
        }
        final Organ modelOrgan = new Organ(organ);

        if (bloodType == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    BloodType.class.getSimpleName()));
        }
        if (!BloodType.isValidBloodType(bloodType)) {
            throw new IllegalValueException(BloodType.MESSAGE_CONSTRAINTS);
        }
        final BloodType modelBloodType = new BloodType(bloodType);
        final Set<Tag> modelTags = new HashSet<>(personTags);

        if (priority == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    Priority.class.getSimpleName()));
        }
        if (!Priority.isValidPriority(priority)) {
            throw new IllegalValueException(Priority.MESSAGE_CONSTRAINTS);
        }
        final Priority modelPriority = new Priority(priority);

        return new Person(modelName, modelPhone, modelEmail, modelAddress, modelOrgan,
                modelBloodType, modelPriority, modelTags, modelEmergencyContact);
    }

}
