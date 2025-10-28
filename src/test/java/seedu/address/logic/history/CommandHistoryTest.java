package seedu.address.logic.history;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class CommandHistoryTest {
    @Test
    public void add_null_failure() {
        CommandHistory commandHistory = new CommandHistory();
        assertThrows(NullPointerException.class, () -> commandHistory.add(null));
    }

    @Test
    public void add_isEnd_success() {
        CommandHistory commandHistory = new CommandHistory();
        commandHistory.add("");
        assertTrue(commandHistory.isEnd());
    }

    @Test
    public void add_isStart_success() {
        assertTrue(new CommandHistory().isEnd());
    }
}
