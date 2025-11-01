---
  layout: default.md
  title: "User Guide"
  pageNav: 3
---

# Organ-izer User Guide

**Organi-zer** is a desktop application that **manages a database of organ donor recipients**.
It is intended for **organ donation coordinators** who require an **easy, fast, and efficient** way to
**navigate a database** and find the **right recipient**, during the **short time window** that an organ is available for donation.

If you are an **organ donation coordinator** at a healthcare facility, **Organ-izer** allows you to:

* **Easily** view your list of potential organ recipients, complete with emergency contact details, blood type, and priority.

* **Efficiently** navigate a database of potential organ recipients.

    * **Organ-izer** uses a **Command Line Interface** (CLI), which means that all functions of the application are accessible via typed commands.

    * If you can type fast, **Organ-izer** can coordinate organ donation much faster than traditional mouse-based applications.

**Organ-izer** is based on the existing [AddressBook Level 3 (AB3)](https://se-education.org/addressbook-level3/UserGuide.html) project, and is optimized for the time-critical environment under which organ donation occurs in a hospital.

<box type="info" seamless>
<markdown>
If you wish to navigate this page quickly, feel free to use the **table of contents** on the right.
</markdown>
</box>

<!-- * Table of Contents -->

<page-nav-print />

--------------------------------------------------------------------------------------------------------------------

## Quick start

1. Ensure you have Java `17` or above installed in your Computer. It can be downloaded [here](https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html).<br>
   <box type="info" seamless>
   <markdown>
   You can **check your Java version** by following [this guide](#https://www.java.com/en/download/help/version_manual.html). <br>
   How to install Java `17`: [Windows](https://se-education.org/guides/tutorials/javaInstallationWindows.html) | [Mac](https://se-education.org/guides/tutorials/javaInstallationMac.html) | [Linux](https://se-education.org/guides/tutorials/javaInstallationLinux.html)
   </markdown>
   </box>

2. Download the latest version of `organ-izer.jar` from [here](https://github.com/AY2526S1-CS2103T-T17-3/tp/releases).

3. Make a folder and place `organ-izer.jar` into it.

4. Open a command terminal.

5. Navigate to the file using cd.

6. Enter `java -jar organ-izer.jar` command to run the application. <br>


   A screen similar to the one below should be visible after a few seconds. Note that the app contains some sample data.<br><br>
   ![Ui](images/Ui.png)
   <br><br>

7. To start, try typing `list` in the command box at the top and press enter to execute it.<br>
    **If it is your first time using the application, it will not display any data**.

8. To add a new user, try typing `
add n/Alice Pauline p/94351253 e/alice@example.com a/123, Jurong West Ave 6, #08-111 o/kidney b/O+ r/1 t/friends
`
9. A list of other commands you can try:
    * `list` : Lists all patients.

    * `clear` : Deletes all patients.

    * `exit` : Exits the app.

10. `Organ-izer` has many other useful commands to help with organ donation coordination. Details about all available commands can be found in the [Features](#features) section below.

<box type="info" seamless>

If you want to **quickly fill in the format of the `add` command**, you can press the `tab` key after typing `add` to make use of our **autofill** feature.

If you **forget how to use any command**, simply typing in the command and pressing `enter` will display information on how to use the command.

</box>

--------------------------------------------------------------------------------------------------------------------

## Features

<box type="info" seamless>

**Notes about the command format:**<br>

* Words in `UPPER_CASE` represent user inputs.<br>
  e.g. in `add n/NAME`, `NAME` is a user input, representing a recipient's **name**,<br> 
  which the user can provide by entering `add n/John Doe`.

* Words in square brackets represent optional inputs.<br>
  e.g. in `n/NAME [t/TAG]` the user may enter `n/John Doe t/friend`,<br>
  or enter `n/John Doe`, if they don't wish to provide a **tag** input .

* Words followed by `…`​ after them, can be provided **multiple times**,<br>
  including **zero times**, if the user does not wish to provide an input.<br>
  e.g. in `[t/TAG]…​` the user can :<br> 
  enter `  `, if the user does not wish to provide a tag,<br>
  or enter `t/friend`, to provide 1 tag,<br>
  or enter `t/friend t/family`, to provide 2 tags etc.

* Inputs can be supplied in any order.<br>
  e.g. if the command specifies `n/NAME p/PHONE_NUMBER`, users can either, <br>
  enter `n/John p/92316570` **or** `p/92316570 n/John`.

* For commands that **don't need any user inputs** (such as `help`, `list`, `exit` and `clear`),<br>
  any user inputs after the command will be **ignored.**<br>
  e.g. if the user entered `help 123`, it will be interpreted as `help`.

* If you are using a PDF version of this document, be careful when copying and pasting commands that span multiple lines as space characters surrounding line-breaks may be omitted when copied over to the application.
  </box>

### Viewing help : `help`

Shows a message explaining how to access the help page.

![help message](images/helpMessage.png)

Format: `help`


### Adding a recipient: `add`

Adds a recipient to the **Organ-izer**.

Format: `add n/NAME p/PHONE e/EMAIL a/ADDRESS o/ORGAN b/BLOOD TYPE r/PRIORITY [en/EMERGENCY_NAME] [ep/EMERGENCY_PHONE] [er/EMERGENCY_RELATION] [t/TAG]...`

<box type="tip" seamless>

**Tip:** A recipient can have any number of tags (including 0)
</box>

<box type="note" seamless>

**Note:** 
Names should only contain alphanumeric characters and spaces, and it should not be blank.

Phone numbers should only contain numbers, and should be 8 digits only.

Emails should be of the format local-part@domain and adhere to the following constraints:
1. The local-part should only contain alphanumeric characters and these special characters, excluding the parentheses, (+_.-). The local-part may not start or end with any special characters.
2. This is followed by a '@' and then a domain name. The domain name is made up of domain labels separated by periods.
The domain name must:
    - end with a domain label at least 2 characters long
    - have each domain label start and end with alphanumeric characters
    - have each domain label consist of alphanumeric characters, separated only by hyphens, if any.

Organ must be all alphabetical characters or spaces, but the first letter must be alphabetical. All characters must be in upper-case.

</box>

Examples:
* `add n/John Doe p/98765432 e/johnd@example.com a/311, Clementi Ave 2, #02-25 o/kidney b/O+ r/1 en/Jane Doe ep/91234567 er/spouse t/friends t/owesMoney`

### Tab Completion for `add` command

Auto creates all required and optional prefixes for the `add` command.

Format: `add` then press the <kbd>tab</kbd> key

<box type="note" seamless>

**Note:** The tab completion feature brings the cursor to the first field to be filled in.
</box>

Examples:
* `add` followed by <kbd>tab</kbd> gives `add n/ p/ e/ a/ o/ b/ r/ t/ en/ ep/ er/`
  
### Listing all recipients : `list`

Shows a list of all recipients in the Organiser.

Format: `list`

### Finding compatible blood types: `compatible`

Finds and lists all recipients whose blood type is compatible with the specified donor blood type. 

* Compatible means they can receive from someone with the specified blood type.

Format: `compatible BLOOD_TYPE`

<box type="note" seamless>

**Note:** Blood types must be one of A+, A-, B+, B-, AB+, AB-, O+, O-.
</box>

Examples:
* `compatible O-` Finds all recipients whose blood type is compatible with O-.

### Finding recipients by organ : `organ`

Finds and lists all recipients in **Organ-izer** whose organ contains the specified substring. 

* Substring matching is case insensitive.

Format: `organ ORGAN`

<box type="note" seamless>

**Note:** Organ must be all alphabetical characters or spaces, but the first letter must be alphabetical. All characters must be in upper-case.
</box>

Examples:
* `organ kidney` Finds all recipients in **Organ-izer** whose organ contains `kidney` as a substring.

### Finding recipients by bloodtype : `bloodtype`

Finds and lists all recipients in **Organ-izer** whose blood type matches the specified blood types.

Format: `bloodtype BLOODTYPE`

<box type="note" seamless>

**Note:** BLOODTYPE must be A+, A- B+, B- AB+, AB-, O+ or O- only (case sensitive)
 and can accept multiple values to find recipients of either blood type.
</box>

Examples:
* `bloodtype O+ A+` Finds all recipients in **Organ-izer** whose blood type is either `O+` or `A+`.
* `bloodtype AB+` Finds all recipients in **Organ-izer** whose blood type is AB+.

### Finding recipients by priority : `priority`

Finds and lists all recipients in **Organ-izer** whose priority matches the specified priorities.

Format: `priority PRIORITY`

<box type="note" seamless>

**Note:** Priority must be a number from 1-5, and can accept multiple values to find recipients of either priority value.
</box>

Examples:
* `priority 1 2` Finds all recipients in **Organ-izer** whose priority is either `1` or `2`.
* `priority 3` Finds all recipients in **Organ-izer** whose priority is 3.

### Finding recipients by multiple criteria: `combined`

Finds and lists all recipients in **Organ-izer** that match all specified criteria. 

Format: `[n/EXACT_NAME] [o/ORGAN_SUBSTRING] [b/RECIPIENT_BLOOD_TYPES]`

* Find all recipients in **Organ-izer** that matches the exact name, whose organ needed matches the specified organ in the command, and has a blood type compatible with the specified blood type in the command.
* Criteria can include exact name match, organ substring, and compatible blood types for recipients.
* At least one of the optional fields must be provided.
* Name in the command must be an exact match. Blood type finds donors compatible with the specified recipient blood types.

Examples:
* `combines n/Alice Pauline o/kidney bt/O+` Find all recipients in **Organ-izer** with name `Alice` or `Pauline`, whose organ contains `kidney` as a substring, and has blood type compatible with `O+`. 

### Editing a recipient : `edit`

Edits an existing recipient in the **Organ-izer**.

Format: `edit INDEX [n/NAME] [p/PHONE] [e/EMAIL] [a/ADDRESS] [o/ORGAN] [b/BLOOD TYPE] [r/PRIORITY] [en/EMERGENCY_NAME] [ep/EMERGENCY_PHONE] [er/EMERGENCY_RELATION] [t/TAG]...`

* Edits the recipient at the specified `INDEX`. The index refers to the index number shown in the displayed recipient list. The index **must be a positive integer** 1, 2, 3, …​
* At least one of the optional fields must be provided.
* Existing values will be updated to the input values.
* When editing tags, the existing tags of the recipient will be removed i.e adding of tags is not cumulative.
* You can remove all the recipient’s tags by typing `edit INDEX t/` without
    specifying any tags after it.
* Emergency contact can be added onto a recipient that did not already have an emergency contact by typing `edit INDEX [en/EMERGENCY_NAME] [ep/EMERGENCY_PHONE]`.
* Emergency contact fields of a recipient can only be edited if the recipient has an emergency contact.
* You can remove the recipient’s emergency contact by typing `edit INDEX en/ ep/`, leaving these fields empty. Any emergency contact relationship is removed along with it. A recipient without emergency contact will leave an empty space at the bottom of the entry to allow uniformity in dimensions of entries.

Examples:
*  `edit 1 p/91234567 e/johndoe@example.com en/Bobby ep/12345678` Edits the phone number and email address of the 1st recipient to be `91234567` and `johndoe@example.com` respectively. This also adds an emergency contact named “Bobby” with contact number “12345678” to the recipient’s details.
*  `edit 2 n/Betsy Crower t/` Edits the name of the 2nd recipient to be `Betsy Crower` and clears all existing tags.
* `edit 1 en/ ep/` removes the emergency contact from the 1st recipient.

### Locating recipients by name: `search`

Finds recipients whose names contain any of the given keywords.

Format: `search KEYWORD`

* The search is case-insensitive. e.g `hans` will match `Hans`
* If the KEYWORD includes multiple words separated by spaces, then the order of the keywords does not matter. e.g. `search Hans Bo` will match `Bo Hans`
* Only the name is searched.
* Only full words will be matched e.g. `search Han` will not match `Hans`
* Recipients matching all words in KEYWORD will be returned (i.e. `AND` search).
  e.g. `Hans Bo` will return `Hans Gruber`, `Bo Yang`

Examples:
* `search John` returns `john` and `John Doe`
* `search Doe John` returns `John Doe`

### Deleting a recipient : `delete`

Deletes the specified recipient from **Organ-izer**.

Format: `delete INDEX`

* Deletes the recipient at the specified `INDEX`.
* The index refers to the index number shown in the displayed recipient list.
* The index **must be a positive integer** 1, 2, 3, …​

Examples:
* `list` followed by `delete 2` deletes the 2nd recipient in **Organ-izer**.
* `find Betsy` followed by `delete 1` deletes the 1st recipient in the results of the `find` command.

### Statistic summary : `summary`

Displays a summary of how many patients require each type of organ.

Format: `summary`

### Clearing all entries : `clear`

Clears all entries from the **Organ-izer**.

Format: `clear`

### Exiting the program : `exit`

Exits the program.

Format: `exit`

### Search filter retention

After reopening the app, filters set from the last `search` command will be restored.

<box type="note" seamless>

**Note:** This filter retention only applies to the filters set from the `search` command, and does not apply to filters set from other commands such as `compatible` or `bloodtype`.
</box>

### Saving the data

Organ-izer data is saved in the hard disk automatically after any command that changes the data. There is no need to save manually.

### Editing the data file

Organ-izer data is saved automatically as a JSON file `[JAR file location]/data/organ-izer.json`. Advanced users are welcome to update data directly by editing that data file.

<box type="warning" seamless>

**Caution:**
If your changes to the data file makes its format invalid, Organ-izer will discard all data and start with an empty data file at the next run.  Hence, it is recommended to take a backup of the file before editing it.<br>
Furthermore, certain edits can cause the Organ-izer to behave in unexpected ways (e.g., if a value entered is outside the acceptable range). Therefore, edit the data file only if you are confident that you can update it correctly.
</box>

### Archiving data files `[coming in v2.0]`

_Details coming soon ..._

--------------------------------------------------------------------------------------------------------------------

## Duplicate handling

* Recipients with the same phone number are considered as duplicates and rejected.
* Emergency contact’s phone number cannot be the same as the recipient’s phone number. Same emergency contact name and recipient name is allowed.
* Recipients with the same name are not considered duplicates and are accepted.

--------------------------------------------------------------------------------------------------------------------

## FAQ

**Q**: How do I transfer my data to another Computer?<br>
**A**: Install the app in the other computer and overwrite the empty data file it creates with the file that contains the data of your previous Organ-izer home folder.

--------------------------------------------------------------------------------------------------------------------

## Known issues

1. **When using multiple screens**, if you move the application to a secondary screen, and later switch to using only the primary screen, the GUI will open off-screen. The remedy is to delete the `preferences.json` file created by the application before running the application again.
2. **If you minimize the Help Window** and then run the `help` command (or use the `Help` menu, or the keyboard shortcut `F1`) again, the original Help Window will remain minimized, and no new Help Window will appear. The remedy is to manually restore the minimized Help Window.

--------------------------------------------------------------------------------------------------------------------

## Command summary

Action 	| Format, Examples
-----------|----------------------------------------------------------------------------------------------------------------------------------------------------------------------
**Add**	   | `add n/NAME p/PHONE_NUMBER e/EMAIL a/ADDRESS [t/TAG]…​` <br> e.g., `add n/James Ho p/22224444 e/jamesho@example.com a/123, Clementi Rd, 1234665 t/friend t/colleague`
**Clear**  | `clear`
**Delete** | `delete INDEX`<br> e.g., `delete 3`
**Edit**   | `edit INDEX [n/NAME] [p/PHONE_NUMBER] [e/EMAIL] [a/ADDRESS] [t/TAG]…​`<br> e.g.,`edit 2 n/James Lee e/jameslee@example.com`
**List**   | `list`
**Summary**   | `summary`
**Bloodtype**   | `bloodtpye BLOODTYPE [BLOODTYPE]...`
**Organ**   | `organ KEYWORD`
**Priority**   | `priority PRIORITY [PRIORITY]...`
**Search**   | `search KEYWORD [KEYWORD]`
**Combined**   | `combined [n/EXACT_NAME] [o/ORGAN_KEYWORD] [b/BLOODTYPE]`
**Compatible**   | `compatible BLOODTYPE`
**Exit**   | `exit`

