package seedu.address.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class UserPrefsTest {

    @Test
    public void setGuiSettings_nullGuiSettings_throwsNullPointerException() {
        UserPrefs userPref = new UserPrefs();
        assertThrows(NullPointerException.class, () -> userPref.setGuiSettings(null));
    }

    @Test
    public void setAddressBookFilePath_nullPath_throwsNullPointerException() {
        UserPrefs userPrefs = new UserPrefs();
        assertThrows(NullPointerException.class, () -> userPrefs.setAddressBookFilePath(null));
    }

    @Test
    public void getLastFilterKeywords_defaultValue_returnsEmptyString() {
        UserPrefs userPrefs = new UserPrefs();
        assertEquals("", userPrefs.getLastFilterKeywords());
    }

    @Test
    public void setLastFilterKeywords_validKeywords_success() {
        UserPrefs userPrefs = new UserPrefs();
        String testKeywords = "john mary";
        userPrefs.setLastFilterKeywords(testKeywords);
        assertEquals(testKeywords, userPrefs.getLastFilterKeywords());
    }

    @Test
    public void setLastFilterKeywords_emptyString_success() {
        UserPrefs userPrefs = new UserPrefs();
        userPrefs.setLastFilterKeywords("initial");
        userPrefs.setLastFilterKeywords("");
        assertEquals("", userPrefs.getLastFilterKeywords());
    }

    @Test
    public void resetData_withFilterKeywords_success() {
        UserPrefs userPrefs = new UserPrefs();
        UserPrefs newUserPrefs = new UserPrefs();
        newUserPrefs.setLastFilterKeywords("alice bob");
        userPrefs.resetData(newUserPrefs);
        assertEquals("alice bob", userPrefs.getLastFilterKeywords());
    }

    @Test
    public void equals_sameFilterKeywords_returnsTrue() {
        UserPrefs userPrefs1 = new UserPrefs();
        UserPrefs userPrefs2 = new UserPrefs();
        userPrefs1.setLastFilterKeywords("john");
        userPrefs2.setLastFilterKeywords("john");
        assertEquals(userPrefs1, userPrefs2);
    }

    @Test
    public void equals_differentFilterKeywords_returnsFalse() {
        UserPrefs userPrefs1 = new UserPrefs();
        UserPrefs userPrefs2 = new UserPrefs();
        userPrefs1.setLastFilterKeywords("john");
        userPrefs2.setLastFilterKeywords("mary");
        assertNotEquals(userPrefs1, userPrefs2);
    }

}
