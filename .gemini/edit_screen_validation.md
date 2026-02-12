# Edit Screen Validation

## 🎯 Objective
Validate the "Edit Department" screen to ensure it allows editing, retains existing data, and strictly enforces mandatory field validation.

## 🛠️ Logic & Workflow

### 1. Data Driven
- Uses `CreateScreenValidation.csv` to identify fields, placeholders, and mandatory flags.

### 2. UI Contract (Positive)
- **Editability**: All fields must be enabled.
- **Data Retention**: Dept Name and Description must match the values used during creation.
- **Buttons**: `Update` and `Cancel` must be present.

### 3. Validation Logic (Negative Test)
For every **Mandatory Field**, the test performs this sequence:
1.  **Clear Data**: Remove the pre-filled text.
2.  **Attempt Update**: Click `Update` button.
3.  **Assert Error**:
    - Verify **Error Message** is displayed.
    - Verify **Red Star** (mandatory indicator) appears.
4.  **Restore State**: Re-enter the original value so the test can proceed to the next field (or exit cleanly).

## 📝 Code Snippet - Mandatory Check
```java
// 1. Clear
input.clear();
dept.getButtonByText("Update").click();

// 2. Assert Failure
sa.assertTrue(dept.getErrorMessage(fieldName).isDisplayed(), "Error Msg Details");
sa.assertTrue(dept.isRedStarDisplayedForField(fieldName).isDisplayed(), "Red Star Details");

// 3. Restore
input.sendKeys(originalValue);
```

## ✅ Result
This ensures that users cannot accidentally save incomplete records while verifying that the editing interface behaves as expected.
