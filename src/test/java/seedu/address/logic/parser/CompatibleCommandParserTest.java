package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.CompatibleCommand;
import seedu.address.model.person.BloodType;
import seedu.address.model.person.BloodTypeCompatibilityPredicate;

public class CompatibleCommandParserTest {

    private CompatibleCommandParser parser = new CompatibleCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, CompatibleCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validBloodType_returnsCompatibleCommand() {
        // no leading and trailing whitespaces
        CompatibleCommand expectedCommand =
                new CompatibleCommand(new BloodTypeCompatibilityPredicate(new BloodType("A+")));
        assertParseSuccess(parser, "A+", expectedCommand);

        // with whitespaces
        assertParseSuccess(parser, " \n A+ \t  ", expectedCommand);
    }

    @Test
    public void parse_validOMinusBloodType_returnsCompatibleCommand() {
        CompatibleCommand expectedCommand =
                new CompatibleCommand(new BloodTypeCompatibilityPredicate(new BloodType("O-")));
        assertParseSuccess(parser, "O-", expectedCommand);
    }

    @Test
    public void parse_validAbPlusBloodType_returnsCompatibleCommand() {
        CompatibleCommand expectedCommand =
                new CompatibleCommand(new BloodTypeCompatibilityPredicate(new BloodType("AB+")));
        assertParseSuccess(parser, "AB+", expectedCommand);
    }

    @Test
    public void parse_invalidBloodType_throwsParseException() {
        // Invalid blood type
        assertParseFailure(parser, "Z+",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, CompatibleCommand.MESSAGE_USAGE));

        // Invalid format
        assertParseFailure(parser, "ABC",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, CompatibleCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_multipleBloodTypes_throwsParseException() {
        // Only one blood type should be provided
        assertParseFailure(parser, "A+ B+",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, CompatibleCommand.MESSAGE_USAGE));
    }
}

