package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.model.Model;
import seedu.address.model.person.Priority;
import seedu.address.model.person.PriorityMatchesPredicate;

/**
 * Finds and lists all persons in address book whose priority matches the specified priorities.
 * Priority must be a number from 1-5.
 */
public class PriorityCommand extends Command {
    public static final String COMMAND_WORD = "priority";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all persons whose priority matches "
            + "the specified priorities and displays them as a list with index numbers. \n"
            + Priority.MESSAGE_CONSTRAINTS
            + "\nParameters: PRIORITY\n"
            + "Example: " + COMMAND_WORD + " 1 2";

    private final PriorityMatchesPredicate predicate;

    public PriorityCommand(PriorityMatchesPredicate predicate) {
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredPersonList(predicate);
        if (model.isFilteredPersonListEmpty()) {
            return new CommandResult(String.format(Messages.MESSAGE_NO_PRIORITIES_FOUND, predicate.getPriorities()));
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
        if (!(other instanceof PriorityCommand)) {
            return false;
        }

        PriorityCommand otherCommand = (PriorityCommand) other;
        return predicate.equals(otherCommand.predicate);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("predicate", predicate)
                .toString();
    }
}
