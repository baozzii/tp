package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.model.Model;
import seedu.address.model.person.BloodTypeCompatibilityPredicate;

/**
 * Finds and lists all persons whose blood type is compatible with the specified donor blood type.
 * Compatible means they can receive from someone with the specified blood type.
 */
public class CompatibleCommand extends Command {

    public static final String COMMAND_WORD = "compatible";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all persons whose blood type can "
            + "receive from the specified donor blood type "
            + "and displays them as a list with index numbers.\n"
            + "Parameters: BLOOD_TYPE (must be one of: A+, A-, B+, B-, AB+, AB-, O+, O-)\n"
            + "Example: " + COMMAND_WORD + " O-";

    private final BloodTypeCompatibilityPredicate predicate;

    public CompatibleCommand(BloodTypeCompatibilityPredicate predicate) {
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredPersonList(predicate);
        if (model.isFilteredPersonListEmpty()) {
            return new CommandResult(String.format(Messages.MESSAGE_NO_PERSONS_FOUND,
                    "compatible recipients for donor " + predicate.getDonorBloodType()));
        }
        return new CommandResult(
                String.format(Messages.MESSAGE_PERSONS_LISTED_OVERVIEW,
                    "for donor " + predicate.getDonorBloodType(), model.getFilteredPersonList().size()));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof CompatibleCommand)) {
            return false;
        }

        CompatibleCommand otherCommand = (CompatibleCommand) other;
        return predicate.equals(otherCommand.predicate);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("predicate", predicate)
                .toString();
    }
}

