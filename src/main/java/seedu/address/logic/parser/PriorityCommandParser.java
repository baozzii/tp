package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.Arrays;
import java.util.List;

import seedu.address.logic.commands.PriorityCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Priority;
import seedu.address.model.person.PriorityMatchesPredicate;

/**
 * Parses input arguments and creates a new PriorityCommand object
 */
public class PriorityCommandParser {

    /**
     * Parses the given {@code String} of arguments in the context of the PriorityCommand
     * and returns a PriorityCommand object for execution.
     * @throws ParseException if the user input does not conform to the expected format
     */
    public PriorityCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, PriorityCommand.MESSAGE_USAGE));
        }

        String[] priorityKeywords = trimmedArgs.split("\\s+");

        List<Priority> priorityList;

        try {
            priorityList = Arrays.stream(priorityKeywords)
                    .map(keyword -> new Priority(Integer.parseInt(keyword)))
                    .toList();
        } catch (IllegalArgumentException e) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, PriorityCommand.MESSAGE_USAGE));
        }

        return new PriorityCommand(new PriorityMatchesPredicate(priorityList));
    }
}
