package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.model.Model;
import seedu.address.model.person.CombinedPredicate;

/**
 * Finds and lists all persons in address book that match all specified criteria.
 * Criteria can include name keywords, organ substring, and blood types.
 */
public class CombinedCommand extends Command {

    public static final String COMMAND_WORD = "combined";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all persons matching ALL specified criteria "
            + "(case-insensitive) and displays them as a list with index numbers.\n"
            + "Parameters: [n/NAME_KEYWORDS] [o/ORGAN_SUBSTRING] [bt/BLOOD_TYPES]\n"
            + "At least one parameter must be provided.\n"
            + "Example: " + COMMAND_WORD + " n/alice bob o/kidney bt/O+ A+";

    private final CombinedPredicate predicate;

    public CombinedCommand(CombinedPredicate predicate) {
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredPersonList(predicate);
        if (model.isFilteredPersonListEmpty()) {
            return new CommandResult(String.format(Messages.MESSAGE_NO_PERSONS_FOUND, predicate.getCriteria()));
        }
        return new CommandResult(
                String.format(Messages.MESSAGE_PERSONS_LISTED_OVERVIEW, model.getFilteredPersonList().size()));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof CombinedCommand)) {
            return false;
        }

        CombinedCommand otherCommand = (CombinedCommand) other;
        return predicate.equals(otherCommand.predicate);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("predicate", predicate)
                .toString();
    }
}

