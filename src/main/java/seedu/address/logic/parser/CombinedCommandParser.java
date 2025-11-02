package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import seedu.address.logic.commands.CombinedCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.BloodType;
import seedu.address.model.person.BloodTypeRecipientCompatiblePredicate;
import seedu.address.model.person.CombinedPredicate;
import seedu.address.model.person.NameExactMatchPredicate;
import seedu.address.model.person.OrganContainsSubstringPredicate;

/**
 * Parses input arguments and creates a new CombinedCommand object
 */
public class CombinedCommandParser implements Parser<CombinedCommand> {

    private static final Prefix PREFIX_NAME = new Prefix("n/");
    private static final Prefix PREFIX_ORGAN = new Prefix("o/");
    private static final Prefix PREFIX_BLOODTYPE = new Prefix("b/");

    /**
     * Parses the given {@code String} of arguments in the context of the CombinedCommand
     * and returns a CombinedCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public CombinedCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_ORGAN, PREFIX_BLOODTYPE);

        // Check if at least one prefix is present
        boolean hasName = argMultimap.getValue(PREFIX_NAME).isPresent();
        boolean hasOrgan = argMultimap.getValue(PREFIX_ORGAN).isPresent();
        boolean hasBloodType = argMultimap.getValue(PREFIX_BLOODTYPE).isPresent();

        if (!hasName && !hasOrgan && !hasBloodType) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, CombinedCommand.MESSAGE_USAGE));
        }

        Optional<NameExactMatchPredicate> namePredicate = Optional.empty();
        Optional<OrganContainsSubstringPredicate> organPredicate = Optional.empty();
        Optional<BloodTypeRecipientCompatiblePredicate> bloodTypePredicate = Optional.empty();

        // Parse name for exact match
        if (hasName) {
            String name = argMultimap.getValue(PREFIX_NAME).get().trim();
            if (!name.isEmpty()) {
                namePredicate = Optional.of(new NameExactMatchPredicate(name));
            }
        }

        // Parse organ substring
        if (hasOrgan) {
            String organSubstring = argMultimap.getValue(PREFIX_ORGAN).get().trim();
            if (!organSubstring.isEmpty()) {
                organPredicate = Optional.of(new OrganContainsSubstringPredicate(organSubstring));
            }
        }

        // Parse blood types (for recipient compatibility)
        if (hasBloodType) {
            String bloodTypeString = argMultimap.getValue(PREFIX_BLOODTYPE).get().trim();
            if (!bloodTypeString.isEmpty()) {
                String[] bloodTypeKeywords = bloodTypeString.split("\\s+");

                // Validate blood types
                if (Arrays.stream(bloodTypeKeywords)
                        .anyMatch(keyword -> !BloodType.isValidBloodType(keyword))) {
                    throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                            CombinedCommand.MESSAGE_USAGE));
                }

                List<BloodType> bloodTypes = Arrays.stream(bloodTypeKeywords)
                        .map(BloodType::new)
                        .toList();
                bloodTypePredicate = Optional.of(new BloodTypeRecipientCompatiblePredicate(bloodTypes));
            }
        }

        // Ensure at least one predicate is actually present (not just empty)
        if (namePredicate.isEmpty() && organPredicate.isEmpty() && bloodTypePredicate.isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, CombinedCommand.MESSAGE_USAGE));
        }

        return new CombinedCommand(new CombinedPredicate(namePredicate, organPredicate, bloodTypePredicate));
    }
}

