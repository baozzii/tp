package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.CombinedCommand;
import seedu.address.model.person.BloodType;
import seedu.address.model.person.BloodTypeRecipientCompatiblePredicate;
import seedu.address.model.person.CombinedPredicate;
import seedu.address.model.person.NameExactMatchPredicate;
import seedu.address.model.person.OrganContainsSubstringPredicate;

public class CombinedCommandParserTest {

    private CombinedCommandParser parser = new CombinedCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, CombinedCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_noPrefixes_throwsParseException() {
        assertParseFailure(parser, "some random text",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, CombinedCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validNameOnly_returnsCombinedCommand() {
        CombinedPredicate expectedPredicate = new CombinedPredicate(
                Optional.of(new NameExactMatchPredicate("Alice")),
                Optional.empty(),
                Optional.empty());
        CombinedCommand expectedCommand = new CombinedCommand(expectedPredicate);
        assertParseSuccess(parser, " n/Alice", expectedCommand);
    }

    @Test
    public void parse_validOrganOnly_returnsCombinedCommand() {
        CombinedPredicate expectedPredicate = new CombinedPredicate(
                Optional.empty(),
                Optional.of(new OrganContainsSubstringPredicate("kidney")),
                Optional.empty());
        CombinedCommand expectedCommand = new CombinedCommand(expectedPredicate);
        assertParseSuccess(parser, " o/kidney", expectedCommand);
    }

    @Test
    public void parse_validBloodTypeOnly_returnsCombinedCommand() {
        List<BloodType> bloodTypes = Arrays.asList(new BloodType("O+"));
        CombinedPredicate expectedPredicate = new CombinedPredicate(
                Optional.empty(),
                Optional.empty(),
                Optional.of(new BloodTypeRecipientCompatiblePredicate(bloodTypes)));
        CombinedCommand expectedCommand = new CombinedCommand(expectedPredicate);
        assertParseSuccess(parser, " bt/O+", expectedCommand);
    }

    @Test
    public void parse_multipleBloodTypes_returnsCombinedCommand() {
        List<BloodType> bloodTypes = Arrays.asList(new BloodType("A+"), new BloodType("O+"));
        CombinedPredicate expectedPredicate = new CombinedPredicate(
                Optional.empty(),
                Optional.empty(),
                Optional.of(new BloodTypeRecipientCompatiblePredicate(bloodTypes)));
        CombinedCommand expectedCommand = new CombinedCommand(expectedPredicate);
        assertParseSuccess(parser, " bt/A+ O+", expectedCommand);
    }

    @Test
    public void parse_allFields_returnsCombinedCommand() {
        List<BloodType> bloodTypes = Arrays.asList(new BloodType("O+"));
        CombinedPredicate expectedPredicate = new CombinedPredicate(
                Optional.of(new NameExactMatchPredicate("Alice Bob")),
                Optional.of(new OrganContainsSubstringPredicate("kidney")),
                Optional.of(new BloodTypeRecipientCompatiblePredicate(bloodTypes)));
        CombinedCommand expectedCommand = new CombinedCommand(expectedPredicate);
        assertParseSuccess(parser, " n/Alice Bob o/kidney bt/O+", expectedCommand);
    }

    @Test
    public void parse_invalidBloodType_throwsParseException() {
        assertParseFailure(parser, " bt/Z+",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, CombinedCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_emptyPrefixValues_throwsParseException() {
        assertParseFailure(parser, " n/ o/ bt/",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, CombinedCommand.MESSAGE_USAGE));
    }
}

