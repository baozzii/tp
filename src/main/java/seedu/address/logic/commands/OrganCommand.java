package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.model.Model;
import seedu.address.model.person.OrganContainsSubstringPredicate;

/**
 * Finds and lists all persons in address book whose organ contains the specified substring.
 * Substring matching is case insensitive.
 */
public class OrganCommand extends Command {

    public static final String COMMAND_WORD = "organ";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all persons whose organ contains "
            + "the specified substring (case-insensitive) and displays them as a list with index numbers.\n"
            + "Parameters: SUBSTRING\n"
            + "Example: " + COMMAND_WORD + " kidney";

    private final OrganContainsSubstringPredicate predicate;

    public OrganCommand(OrganContainsSubstringPredicate predicate) {
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredPersonList(predicate);
        if (model.isFilteredPersonListEmpty()) {
            return new CommandResult(String.format(Messages.MESSAGE_NO_PERSONS_FOUND, predicate.getSubstring()));
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
        if (!(other instanceof OrganCommand)) {
            return false;
        }

        OrganCommand otherCommand = (OrganCommand) other;
        return predicate.equals(otherCommand.predicate);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("predicate", predicate)
                .toString();
    }
}

