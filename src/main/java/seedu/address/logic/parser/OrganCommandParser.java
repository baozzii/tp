package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.logic.commands.OrganCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.OrganContainsSubstringPredicate;

/**
 * Parses input arguments and creates a new OrganCommand object
 */
public class OrganCommandParser implements Parser<OrganCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the OrganCommand
     * and returns an OrganCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public OrganCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, OrganCommand.MESSAGE_USAGE));
        }

        return new OrganCommand(new OrganContainsSubstringPredicate(trimmedArgs));
    }

}

