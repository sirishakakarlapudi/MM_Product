# BasePage Locator Fix - View/Edit Icons

## ❌ The Issue
The `clickView` and `clickEdit` methods in `BasePage.java` were malfunctioning because of how the locators were constructed.

**Original Code (Example):**
```java
performTableActionGeneric(..., 
    "//span[contains(@class,'be-pencil')] | //i[contains(@class,'pi-eye')]", 
    ...);
```
**Mechanism:** 
The `performTableActionGeneric` method concatenates this locator to the row XPath:
`//tbody/tr[1]` + `//span[...] | //i[...]`

**Resulting XPath:**
`//tbody/tr[1]//span[...] | //i[...]`

Due to the pipe `|` and the `//` prefix in the second part, the XPath engine treats `//i[...]` as an **absolute path** starting from the document root. This caused the script to find and click the **first View icon in the entire table** (usually Row 1), regardless of which row matched the department name.

 Additionally, `clickView` incorrectly included `be-pencil` (Edit icon) in its search criteria, which was a copy-paste error.

## ✅ The Fix
I updated the locators to be relative and specific.

**1. `clickView` Fix:**
- **Code:** `"//*[contains(@class,'pi-eye')]"`
- **Result:** Finds only the View icon (`pi-eye`) *inside* the specific row.

**2. `clickEdit` Fix:**
- **Code:** `"//*[contains(@class,'be-pencil') or contains(@class,'pi-pencil')]"`
- **Result:** Finds either Edit icon style, but strictly *inside* the specific row, preventing global matches.

## 🚀 Impact
The `View_Screen_Validation` test will now correctly click the View icon for the specific department (e.g., `OQ-Department6`) instead of opening the first record in the table.
