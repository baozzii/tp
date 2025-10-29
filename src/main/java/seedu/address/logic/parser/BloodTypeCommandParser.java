package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.Arrays;
import java.util.List;

import seedu.address.logic.commands.BloodTypeCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.BloodType;
import seedu.address.model.person.BloodTypeMatchesPredicate;

/**
 * Parses input arguments and creates a new BloodTypeCommand object
 */
public class BloodTypeCommandParser {

    /**
     * Parses the given {@code String} of arguments in the context of the FindCommand
     * and returns a FindCommand object for execution.
     * @throws ParseException if the user input does not conform to the expected format
     */
    public BloodTypeCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, BloodTypeCommand.MESSAGE_USAGE));
        }

        String[] nameKeywords = trimmedArgs.split("\\s+");

        if (Arrays.stream(nameKeywords)
                .anyMatch(keyword -> !BloodType.isValidBloodType(keyword))) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, BloodTypeCommand.MESSAGE_USAGE));
        }

        List<BloodType> bloodTypes = Arrays.stream(nameKeywords).map(BloodType::new).toList();

        return new BloodTypeCommand(new BloodTypeMatchesPredicate(bloodTypes));
    }

}
