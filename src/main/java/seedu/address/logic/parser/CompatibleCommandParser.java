package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.logic.commands.CompatibleCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.BloodType;
import seedu.address.model.person.BloodTypeCompatibilityPredicate;

/**
 * Parses input arguments and creates a new CompatibleCommand object
 */
public class CompatibleCommandParser implements Parser<CompatibleCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the CompatibleCommand
     * and returns a CompatibleCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public CompatibleCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, CompatibleCommand.MESSAGE_USAGE));
        }

        // Validate that it's a single blood type
        String[] parts = trimmedArgs.split("\\s+");
        if (parts.length > 1) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, CompatibleCommand.MESSAGE_USAGE));
        }

        if (!BloodType.isValidBloodType(trimmedArgs)) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, CompatibleCommand.MESSAGE_USAGE));
        }

        BloodType donorBloodType = new BloodType(trimmedArgs);
        return new CompatibleCommand(new BloodTypeCompatibilityPredicate(donorBloodType));
    }
}

