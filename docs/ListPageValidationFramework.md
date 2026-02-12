# List Page Validation Framework - Usage Guide

## Overview
This framework provides a **comprehensive, reusable validation method** for list/table pages across all modules in your application.

## Components Created

### 1. CSV Data File: `ListPageValidation.csv`
**Location:** `src/test/resources/CSV_Data/ListPageValidation.csv`

**Structure:**
```csv
Module,Component,ElementName,ElementType,Placeholder,isMandatory,isVisible
Department,Filter,Department,input,Department,No,Yes
Department,Filter,Clear,button,,No,Yes
Department,Filter,Search,button,,No,Yes
Department,TableHeader,Department Name,column,,No,Yes
...
```

**Components Supported:**
- `Filter` - Filter panel fields and buttons
- `TableHeader` - Table column headers
- `TopBar` - Preferences, PDF, Excel, Create buttons
- `Pagination` - Navigation and rows-per-page controls

### 2. Test Method: `List_Page_Validation()`
**Location:** `Department_TC.java`

**Validation Phases:**

#### Phase 1: Breadcrumbs
- Validates breadcrumb sequence (Home > Departments)
- Ensures correct navigation path

#### Phase 2: Filter Panel
- ✅ Filter toggle button visibility
- ✅ Default expanded state
- ✅ All filter fields (from CSV):
  - Existence
  - Visibility
  - Enabled state
  - Placeholder text
- ✅ Clear & Search buttons
- ✅ Collapse/Expand functionality

#### Phase 3: Create Button (Privilege-Based)
- ✅ Visibility based on user's CREATE privilege
- ✅ Enabled state when visible
- ✅ Hidden when user lacks privilege

#### Phase 4: Table Header Bar
- ✅ Preferences dropdown (left)
- ✅ Item count display (center) - "X Item(s) found"
- ✅ PDF button (right)
- ✅ Excel button (right)

#### Phase 5: Table Columns
- ✅ Strict column header validation
- ✅ Expected vs Actual comparison (from CSV)

#### Phase 6: Pagination
- ✅ First page button
- ✅ Previous page button
- ✅ Next page button
- ✅ Last page button
- ✅ Rows per page dropdown
- ✅ Current page info format ("1 - 10 of 31")

### 3. Page Object Methods: `Department.java`
**Location:** `src/test/java/pageObjects/Department.java`

**New Methods Added:**

**Filter Panel:**
- `getFilterToggleButton()` - Get filter icon/button
- `isFilterPanelExpanded()` - Check if filter is expanded
- `clickFilterToggle()` - Toggle filter panel
- `getFilterFieldByLabel(String)` - Get specific filter field
- `getFilterButton(String)` - Get Clear/Search button

**Create Button & Privileges:**
- `isCreateButtonDisplayed()` - Check visibility
- `getCreateButton()` - Get create button element
- `hasPrivilege(String)` - Check user privilege (CREATE, EDIT, DELETE)

**Table Header Bar:**
- `getPreferencesDropdown()` - Get preferences dropdown
- `getItemCountText()` - Get "X Item(s) found" text
- `getPDFButton()` - Get PDF export button
- `getExcelButton()` - Get Excel export button

**Table:**
- `getTableHeaders()` - Get all column headers as List<String>

**Pagination:**
- `getPaginationButton(String)` - Get First/Previous/Next/Last button
- `getRowsPerPageDropdown()` - Get rows-per-page dropdown
- `getCurrentPageInfo()` - Get "X - Y of Z" text

## How to Use for Other Modules

### Step 1: Add Module Data to CSV
Edit `ListPageValidation.csv` and add rows for your module:

```csv
Facility,Filter,Facility Name,input,Enter Facility Name,No,Yes
Facility,Filter,Location,input,Enter Location,No,Yes
Facility,TableHeader,Facility Name,column,,No,Yes
Facility,TableHeader,Type,column,,No,Yes
Facility,TableHeader,Location,column,,No,Yes
Facility,TableHeader,Status,column,,No,Yes
Facility,TableHeader,Actions,column,,No,Yes
```

### Step 2: Copy the Test Method
Copy `List_Page_Validation()` from `Department_TC.java` to your module's test class (e.g., `Facility_TC.java`).

**Change only:**
- Module name in CSV query: `CSVUtility.getRowsByModule(csvPath, "Facility")`
- `MASTER_MODULE` variable reference (if different)

### Step 3: Reuse Page Object Methods
All methods in `Department.java` are **generic** and work for any module! Just call them from your page object class (e.g., `Facility.java` can extend `BasePage` which has these methods).

## Benefits

✅ **Centralized Data Management** - All expected UI elements in one CSV
✅ **Reusable Across Modules** - Same validation logic for all list pages
✅ **Comprehensive Coverage** - Validates 6 major UI components
✅ **Privilege-Based Testing** - Automatically adapts to user permissions
✅ **Easy Maintenance** - Update CSV instead of code
✅ **Consistent Validation** - Same standards across all modules

## Example Test Execution

```java
@Test(groups = { "listpagevalidation" }, dependsOnMethods = "moduleClick", priority = 5)
public void List_Page_Validation() throws Throwable {
    // Automatically validates:
    // - Breadcrumbs
    // - Filter panel (fields, buttons, toggle)
    // - Create button (privilege-based)
    // - Table header bar (preferences, PDF, Excel, count)
    // - Table columns
    // - Pagination
}
```

## Notes

1. **Privilege Checking**: The `hasPrivilege()` method needs to be implemented in `DatabaseBackupUtil` to query actual user privileges.

2. **XPath Customization**: If your module has different HTML structure, update the XPath in the corresponding page object method.

3. **CSV Flexibility**: You can add more columns to the CSV (e.g., `isRequired`, `defaultValue`) and extend the validation logic.

## Future Enhancements

- Add filter search functionality validation
- Add table row data validation
- Add sorting validation (click column headers)
- Add preference selection validation
- Add PDF/Excel download validation
