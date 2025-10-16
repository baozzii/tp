package seedu.address.logic.parser;

import org.junit.jupiter.api.Test;
import seedu.address.logic.commands.OrganCommand;
import seedu.address.logic.commands.SearchCommand;
import seedu.address.model.person.NameContainsKeywordsPredicate;
import seedu.address.model.person.OrganContainsSubstringPredicate;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

class OrganCommandParserTest {

    private OrganCommandParser parser = new OrganCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ", String.format(MESSAGE_INVALID_COMMAND_FORMAT, OrganCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validArgs_returnsFindCommand() {
        // no leading and trailing whitespaces
        OrganCommand expectedOrganCommand =
                new OrganCommand(new OrganContainsSubstringPredicate("heart kidney"));
        assertParseSuccess(parser, "heart kidney", expectedOrganCommand);
    }
}