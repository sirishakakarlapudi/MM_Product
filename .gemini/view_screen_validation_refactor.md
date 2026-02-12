# View Screen Validation - Field Logic Update

## 🎯 Objective
Refine the `View_Screen_Validation` method to enforce strict rules about when data values vs. placeholders should be displayed.

## 🧠 The New Logic

### 1. Mandatory Fields (e.g., Department Name)
*   **Rule**: Since these fields were required during creation, they **MUST** contain data.
*   **Validation**:
    *   `actualValue` must NOT be empty.
    *   `actualValue` must equal `expectedValue` (e.g., "OQ Validation Test").

### 2. Optional Fields (e.g., Description)
*   **Case A: Data Provided**
    *   **Rule**: If data was entered during creation, show it.
    *   **Validation**: `actualValue` equals `expectedValue`.
*   **Case B: Data NOT Provided (Empty)**
    *   **Rule**: If field was left blank, the **placeholder** should be visible.
    *   **Validation**:
        *   `actualValue` is empty ("").
        *   `actualPlaceholder` equals `csvPlaceholder` (from CSV).

## 📝 Code Implementation

```java
if (isMandatory) {
    // CASE A: MANDATORY FIELDS (Must have value)
    sa.assertFalse(actualValue.isEmpty(), "Mandatory Value Missing");
    sa.assertEquals(actualValue, expectedValue, "Mandatory Field Value");
} else {
    // CASE B: NON-MANDATORY (Can be empty)
    if (expectedValue != null && !expectedValue.isEmpty()) {
        // Data Present
        sa.assertEquals(actualValue, expectedValue, "Optional Field Value");
    } else {
        // Data Empty -> Check Placeholder
        sa.assertEquals(actualValue, "", "Optional Field Empty");
        if (actualPlaceholder != null) {
            sa.assertEquals(actualPlaceholder, csvPlaceholder, "Placeholder");
        }
    }
}
```

## ✅ Result
This logic correctly handles scenarios where optional fields (like Description) might be blank, ensuring the UI correctly falls back to displaying the placeholder text in View mode, while enforcing that mandatory fields always retain their data.
