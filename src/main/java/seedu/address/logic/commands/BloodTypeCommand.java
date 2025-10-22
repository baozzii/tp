package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.model.Model;
import seedu.address.model.person.BloodTypeMatchesPredicate;

/**
 * Finds and lists all persons in an address book whose blood type matches the specified blood type
 * Blood type entered must be A+, A- B+, B- AB+, AB-, O+ or O- only
 */
public class BloodTypeCommand extends Command {

    public static final String COMMAND_WORD = "bloodtype";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all persons whose organ contains "
            + "the specified blood types and displays them as a list with index numbers. The blood types "
            + "entered must be one of A+, A- B+, B- AB+, AB-, O+ or O- only\n"
            + "Parameters: BLOODTYPE\n"
            + "Example: " + COMMAND_WORD + "A+ B- B+";

    private final BloodTypeMatchesPredicate predicate;

    public BloodTypeCommand(BloodTypeMatchesPredicate predicate) {
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredPersonList(predicate);
        if (model.isFilteredPersonListEmpty()) {
            return new CommandResult(String.format(Messages.MESSAGE_NO_PERSONS_FOUND,
                    predicate.getBloodTypes()));
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
        if (!(other instanceof BloodTypeCommand)) {
            return false;
        }

        BloodTypeCommand otherBloodTypeCommand = (BloodTypeCommand) other;
        return predicate.equals(otherBloodTypeCommand.predicate);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("predicate", predicate)
                .toString();
    }
}
