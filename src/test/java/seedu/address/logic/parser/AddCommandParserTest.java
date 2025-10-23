package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.ADDRESS_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.ADDRESS_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.BLOODTYPE_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.BLOODTYPE_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.EMAIL_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.EMAIL_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.EMERGENCY_NAME_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.EMERGENCY_NAME_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.EMERGENCY_PHONE_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.EMERGENCY_PHONE_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.EMERGENCY_RELATION_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.EMERGENCY_RELATION_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_ADDRESS_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_BLOODTYPE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_EMAIL_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_EMERGENCY_NAME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_EMERGENCY_PHONE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_NAME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_ORGAN_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_PHONE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_TAG_DESC;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.ORGAN_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.ORGAN_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.PHONE_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.PHONE_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_NON_EMPTY;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static seedu.address.logic.commands.CommandTestUtil.PRIORITY_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.PRIORITY_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_FRIEND;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_HUSBAND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_BLOODTYPE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMERGENCY_NAME_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMERGENCY_PHONE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMERGENCY_RELATION_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ORGAN_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_FRIEND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_BLOODTYPE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMERGENCY_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMERGENCY_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMERGENCY_RELATION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ORGAN;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PRIORITY;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalPersons.AMY;
import static seedu.address.testutil.TypicalPersons.BOB;

import org.junit.jupiter.api.Test;

import seedu.address.logic.Messages;
import seedu.address.logic.commands.AddCommand;
import seedu.address.model.person.Address;
import seedu.address.model.person.BloodType;
import seedu.address.model.person.Email;
import seedu.address.model.person.EmergencyContact;
import seedu.address.model.person.Name;
import seedu.address.model.person.Organ;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.tag.Tag;
import seedu.address.testutil.PersonBuilder;

public class AddCommandParserTest {
    private AddCommandParser parser = new AddCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        Person expectedPerson = new PersonBuilder(BOB).withTags(VALID_TAG_FRIEND).build();

        // whitespace only preamble
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB
                + ADDRESS_DESC_BOB + ORGAN_DESC_BOB + BLOODTYPE_DESC_BOB + PRIORITY_DESC_BOB
                + TAG_DESC_FRIEND, new AddCommand(expectedPerson));

        // multiple tags - all accepted
        Person expectedPersonMultipleTags = new PersonBuilder(BOB).withTags(VALID_TAG_FRIEND, VALID_TAG_HUSBAND)
                .build();
        assertParseSuccess(parser, NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB
                + ORGAN_DESC_BOB + BLOODTYPE_DESC_BOB + PRIORITY_DESC_BOB + TAG_DESC_FRIEND + TAG_DESC_HUSBAND,
                new AddCommand(expectedPersonMultipleTags));

        // emergency contact - all fields present
        Person expectedPersonWithEC = new PersonBuilder(BOB).withTags(VALID_TAG_FRIEND)
                .withEmergencyContact(VALID_EMERGENCY_NAME_AMY, VALID_EMERGENCY_PHONE_AMY, VALID_EMERGENCY_RELATION_AMY)
                .build();
        assertParseSuccess(parser, NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB
                + ORGAN_DESC_BOB + BLOODTYPE_DESC_BOB + PRIORITY_DESC_BOB + TAG_DESC_FRIEND
                + EMERGENCY_NAME_DESC_AMY + EMERGENCY_PHONE_DESC_AMY + EMERGENCY_RELATION_DESC_AMY,
                new AddCommand(expectedPersonWithEC));
    }

    @Test
    public void parse_repeatedNonTagValue_failure() {
        String validExpectedPersonString = NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB
                + ADDRESS_DESC_BOB + ORGAN_DESC_BOB + BLOODTYPE_DESC_BOB + PRIORITY_DESC_BOB + TAG_DESC_FRIEND;

        // multiple names
        assertParseFailure(parser, NAME_DESC_AMY + validExpectedPersonString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_NAME));

        // multiple phones
        assertParseFailure(parser, PHONE_DESC_AMY + validExpectedPersonString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_PHONE));

        // multiple emails
        assertParseFailure(parser, EMAIL_DESC_AMY + validExpectedPersonString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_EMAIL));

        // multiple addresses
        assertParseFailure(parser, ADDRESS_DESC_AMY + validExpectedPersonString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_ADDRESS));

        // multiple organs
        assertParseFailure(parser, ORGAN_DESC_AMY + validExpectedPersonString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_ORGAN));

        // multiple blood types
        assertParseFailure(parser, BLOODTYPE_DESC_AMY + validExpectedPersonString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_BLOODTYPE));

        // multiple fields repeated
        assertParseFailure(parser,
                validExpectedPersonString + PHONE_DESC_AMY + EMAIL_DESC_AMY + NAME_DESC_AMY + ADDRESS_DESC_AMY
                        + ORGAN_DESC_AMY + BLOODTYPE_DESC_AMY + PRIORITY_DESC_AMY,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_ORGAN, PREFIX_NAME, PREFIX_ADDRESS,
                        PREFIX_EMAIL, PREFIX_PHONE, PREFIX_BLOODTYPE, PREFIX_PRIORITY));

        // multiple emergency names
        assertParseFailure(parser, validExpectedPersonString + EMERGENCY_NAME_DESC_AMY + EMERGENCY_NAME_DESC_BOB,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_EMERGENCY_NAME));

        // multiple emergency phones
        assertParseFailure(parser, validExpectedPersonString + EMERGENCY_PHONE_DESC_AMY + EMERGENCY_PHONE_DESC_BOB,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_EMERGENCY_PHONE));

        // multiple emergency relationships
        assertParseFailure(parser, validExpectedPersonString + EMERGENCY_RELATION_DESC_AMY
                + EMERGENCY_RELATION_DESC_BOB,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_EMERGENCY_RELATION));

        // invalid value followed by valid value

        // invalid name
        assertParseFailure(parser, INVALID_NAME_DESC + validExpectedPersonString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_NAME));

        // invalid email
        assertParseFailure(parser, INVALID_EMAIL_DESC + validExpectedPersonString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_EMAIL));

        // invalid phone
        assertParseFailure(parser, INVALID_PHONE_DESC + validExpectedPersonString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_PHONE));

        // invalid address
        assertParseFailure(parser, INVALID_ADDRESS_DESC + validExpectedPersonString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_ADDRESS));
        // invalid organ
        assertParseFailure(parser, INVALID_ORGAN_DESC + validExpectedPersonString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_ORGAN));
        // invalid blood type
        assertParseFailure(parser, INVALID_BLOODTYPE_DESC + validExpectedPersonString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_BLOODTYPE));
        // valid value followed by invalid value
        // invalid name
        assertParseFailure(parser, validExpectedPersonString + INVALID_NAME_DESC,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_NAME));

        // invalid email
        assertParseFailure(parser, validExpectedPersonString + INVALID_EMAIL_DESC,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_EMAIL));

        // invalid phone
        assertParseFailure(parser, validExpectedPersonString + INVALID_PHONE_DESC,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_PHONE));

        // invalid address
        assertParseFailure(parser, validExpectedPersonString + INVALID_ADDRESS_DESC,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_ADDRESS));

        // invalid organ
        assertParseFailure(parser, validExpectedPersonString + INVALID_ORGAN_DESC,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_ORGAN));

        // invalid blood type
        assertParseFailure(parser, validExpectedPersonString + INVALID_BLOODTYPE_DESC,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_BLOODTYPE));
    }

    @Test
    public void parse_optionalFieldsMissing_success() {
        // Only tags are optional, all other fields including organ are mandatory
        Person expectedPerson = new PersonBuilder(AMY).withTags().withEmergencyContact((EmergencyContact) null).build();
        assertParseSuccess(parser, NAME_DESC_AMY + PHONE_DESC_AMY + EMAIL_DESC_AMY
                + ADDRESS_DESC_AMY + ORGAN_DESC_AMY + BLOODTYPE_DESC_AMY + PRIORITY_DESC_AMY,
                new AddCommand(expectedPerson));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE);

        // missing name prefix
        assertParseFailure(parser, VALID_NAME_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB,
                expectedMessage);

        // missing phone prefix
        assertParseFailure(parser, NAME_DESC_BOB + VALID_PHONE_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB,
                expectedMessage);

        // missing email prefix
        assertParseFailure(parser, NAME_DESC_BOB + PHONE_DESC_BOB + VALID_EMAIL_BOB + ADDRESS_DESC_BOB,
                expectedMessage);

        // missing address prefix
        assertParseFailure(parser, NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + VALID_ADDRESS_BOB,
                expectedMessage);

        // missing organ prefix
        assertParseFailure(parser, NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB
                + VALID_ORGAN_BOB, expectedMessage);

        // missing blood type prefix
        assertParseFailure(parser, NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB
                + ORGAN_DESC_BOB + VALID_BLOODTYPE_BOB, expectedMessage);

        // all prefixes missing
        assertParseFailure(parser, VALID_NAME_BOB + VALID_PHONE_BOB + VALID_EMAIL_BOB + VALID_ADDRESS_BOB
                + VALID_ORGAN_BOB + VALID_BLOODTYPE_BOB, expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid name
        assertParseFailure(parser, INVALID_NAME_DESC + PHONE_DESC_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB
                + ORGAN_DESC_BOB + BLOODTYPE_DESC_BOB + PRIORITY_DESC_BOB
                + TAG_DESC_HUSBAND + TAG_DESC_FRIEND, Name.MESSAGE_CONSTRAINTS);

        // invalid phone
        assertParseFailure(parser, NAME_DESC_BOB + INVALID_PHONE_DESC + EMAIL_DESC_BOB + ADDRESS_DESC_BOB
                + ORGAN_DESC_BOB + BLOODTYPE_DESC_BOB + PRIORITY_DESC_BOB
                + TAG_DESC_HUSBAND + TAG_DESC_FRIEND, Phone.MESSAGE_CONSTRAINTS);

        // invalid email
        assertParseFailure(parser, NAME_DESC_BOB + PHONE_DESC_BOB + INVALID_EMAIL_DESC + ADDRESS_DESC_BOB
                + ORGAN_DESC_BOB + BLOODTYPE_DESC_BOB + PRIORITY_DESC_BOB
                + TAG_DESC_HUSBAND + TAG_DESC_FRIEND, Email.MESSAGE_CONSTRAINTS);

        // invalid address
        assertParseFailure(parser, NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + INVALID_ADDRESS_DESC
                + ORGAN_DESC_BOB + BLOODTYPE_DESC_BOB + PRIORITY_DESC_BOB
                + TAG_DESC_HUSBAND + TAG_DESC_FRIEND, Address.MESSAGE_CONSTRAINTS);

        // invalid organ
        assertParseFailure(parser, NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB
                + INVALID_ORGAN_DESC + BLOODTYPE_DESC_BOB + PRIORITY_DESC_BOB
                + TAG_DESC_HUSBAND + TAG_DESC_FRIEND, Organ.MESSAGE_CONSTRAINTS);
        // invalid blood type
        assertParseFailure(parser, NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB
                + ORGAN_DESC_BOB + INVALID_BLOODTYPE_DESC + PRIORITY_DESC_BOB
                + TAG_DESC_HUSBAND + TAG_DESC_FRIEND, BloodType.MESSAGE_CONSTRAINTS);
        // invalid tag
        assertParseFailure(parser, NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB
                + ORGAN_DESC_BOB + BLOODTYPE_DESC_BOB + PRIORITY_DESC_BOB + INVALID_TAG_DESC
                + VALID_TAG_FRIEND, Tag.MESSAGE_CONSTRAINTS);

        // invalid emergency contact name
        assertParseFailure(parser, NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB
                + ORGAN_DESC_BOB + BLOODTYPE_DESC_BOB + PRIORITY_DESC_BOB + INVALID_EMERGENCY_NAME_DESC
                + EMERGENCY_PHONE_DESC_AMY + TAG_DESC_FRIEND, Name.MESSAGE_CONSTRAINTS);

        // invalid emergency contact phone
        assertParseFailure(parser, NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB
                + ORGAN_DESC_BOB + BLOODTYPE_DESC_BOB + PRIORITY_DESC_BOB + EMERGENCY_NAME_DESC_AMY
                + INVALID_EMERGENCY_PHONE_DESC + TAG_DESC_FRIEND, Phone.MESSAGE_CONSTRAINTS);

        // two invalid values, only first invalid value reported
        assertParseFailure(parser, INVALID_NAME_DESC + PHONE_DESC_BOB + EMAIL_DESC_BOB + INVALID_ADDRESS_DESC
                + ORGAN_DESC_BOB + BLOODTYPE_DESC_BOB + PRIORITY_DESC_BOB
                + TAG_DESC_FRIEND, Name.MESSAGE_CONSTRAINTS);

        // non-empty preamble
        assertParseFailure(parser, PREAMBLE_NON_EMPTY + NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB
                + ADDRESS_DESC_BOB + ORGAN_DESC_BOB + BLOODTYPE_DESC_BOB + PRIORITY_DESC_BOB + TAG_DESC_HUSBAND
                + TAG_DESC_FRIEND, String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_emergencyContactPartialFields_failure() {
        String validExpectedPersonString = NAME_DESC_AMY + PHONE_DESC_AMY + EMAIL_DESC_AMY + ADDRESS_DESC_AMY
                + ORGAN_DESC_AMY + BLOODTYPE_DESC_AMY + PRIORITY_DESC_AMY;

        // only emergency name provided
        assertParseFailure(parser, validExpectedPersonString + EMERGENCY_NAME_DESC_AMY,
                EmergencyContact.MESSAGE_CONSTRAINTS);

        // only emergency phone provided
        assertParseFailure(parser, validExpectedPersonString + EMERGENCY_PHONE_DESC_AMY,
                EmergencyContact.MESSAGE_CONSTRAINTS);

        // only emergency relationship provided
        assertParseFailure(parser, validExpectedPersonString + EMERGENCY_RELATION_DESC_AMY,
                EmergencyContact.MESSAGE_CONSTRAINTS);
    }

    @Test
    public void parse_emergencyContactWithoutRelationship_success() {
        // Emergency contact with name and phone but no relationship should work
        Person expectedPerson = new PersonBuilder(AMY)
                .withEmergencyContact(VALID_EMERGENCY_NAME_AMY, VALID_EMERGENCY_PHONE_AMY, "").build();

        assertParseSuccess(parser, NAME_DESC_AMY + PHONE_DESC_AMY + EMAIL_DESC_AMY + ADDRESS_DESC_AMY
                + ORGAN_DESC_AMY + BLOODTYPE_DESC_AMY + PRIORITY_DESC_AMY + TAG_DESC_FRIEND + EMERGENCY_NAME_DESC_AMY
                + EMERGENCY_PHONE_DESC_AMY, new AddCommand(expectedPerson));
    }
}
