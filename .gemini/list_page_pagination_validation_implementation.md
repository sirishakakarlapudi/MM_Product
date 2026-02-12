# List Page Pagination Validation Implementation

## Overview
Enhanced the `List_Page_Validation()` test method in `Department_TC.java` with comprehensive pagination validation that validates:

1. **Table row count vs displayed total count**
2. **Page numbers based on results per page settings**
3. **Range display text** (e.g., "1-10 of 31")
4. **Dynamic validation with different results per page values**

## Implementation Details

### 1. New Helper Methods in `Department.java`

Added the following methods to support pagination validation:

#### `getTableRowCount()`
- Returns the count of visible table rows on the current page
- Used to verify that displayed rows match expectations

#### `extractTotalItemsCount()`
- Extracts total count from "31 Item(s) found" header text
- Returns integer value (e.g., 31)

#### `getResultsPerPageValue()`
- Gets the currently selected "Results per page" dropdown value
- Returns integer (10, 20, 50, or 100)

#### `getDisplayedPageNumbers()`
- Returns a list of all displayed page number buttons
- Example: [1, 2, 3, 4] for 31 items with 10 per page

#### `calculateExpectedPages(totalItems, resultsPerPage)`
- Calculates expected number of pages using Math.ceil
- Example: 31 items ÷ 10 per page = 4 pages

#### `parseRangeInfo()`
- Parses "1 - 10 of 31" format into [start, end, total] array
- Returns int[] with [startRange, endRange, totalCount]

#### `selectResultsPerPage(value)`
- Changes the results per page dropdown to specified value
- Useful for testing different pagination scenarios

### 2. Enhanced Test Validation - Phase 6A: Table Row Count

**Validation Logic:**
```
totalItemsCount = Extract from "31 Item(s) found"
tableRowCount = Count visible table rows
resultsPerPage = Get dropdown value
expectedRowsOnPage = min(resultsPerPage, totalItemsCount)

Assert: tableRowCount == expectedRowsOnPage
```

**Example:**
- Total: 31 items
- Results per page: 10
- First page should show: min(10, 31) = 10 rows ✓

### 3. Enhanced Test Validation - Phase 6B: Page Numbers

**Validation Logic:**
```
For 31 items with 10 per page:
  expectedPages = ceil(31 / 10) = 4 pages
  displayedPageNumbers should be [1, 2, 3, 4]

For 31 items with 20 per page:
  expectedPages = ceil(31 / 20) = 2 pages
  displayedPageNumbers should be [1, 2]
```

**Assertions:**
- Page count matches calculation
- Page numbers are sequential (1, 2, 3, 4...)
- No missing or extra page numbers

### 4. Enhanced Test Validation - Phase 6C: Range Display

**Validation Logic:**
```
Parse "1 - 10 of 31" into components:
  startRange = 1
  endRange = 10
  totalCount = 31

Assertions:
  - startRange == 1 (always for first page)
  - endRange == min(resultsPerPage, totalItemsCount)
  - totalCount == totalItemsCount from header
```

**Example Validations:**
- First page with 10 per page: "1 - 10 of 31" ✓
- First page with 20 per page: "1 - 20 of 31" ✓
- Total count consistency across all displays

### 5. Enhanced Test Validation - Phase 6D: Dynamic Results Per Page Testing

**Test Flow:**
1. **Default (10 per page):**
   - Validate page count: 4 pages
   - Validate range: "1 - 10 of 31"

2. **Change to 20 per page:**
   - Validate page count: 2 pages
   - Validate page numbers: [1, 2]
   - Validate range: "1 - 20 of 31"

3. **Restore to 10 per page:**
   - Switch back for other tests

## Validation Matrix

| Scenario | Total Items | Per Page | Expected Pages | Page Numbers | Range Display |
|----------|-------------|----------|----------------|--------------|---------------|
| Default | 31 | 10 | 4 | [1,2,3,4] | 1 - 10 of 31 |
| Changed | 31 | 20 | 2 | [1,2] | 1 - 20 of 31 |
| Edge Case | 5 | 10 | 1 | [1] | 1 - 5 of 5 |
| Exact Fit | 20 | 10 | 2 | [1,2] | 1 - 10 of 20 |

## Key Assertions Added

### Phase 6A - Table Row Count
1. ✅ Total items count extracted successfully
2. ✅ Table has visible rows
3. ✅ Results per page value is valid
4. ✅ Table row count matches expected (min of per page or total)

### Phase 6B - Page Numbers
5. ✅ Page number count matches calculated expected pages
6. ✅ Page numbers are sequential from 1
7. ✅ No missing page numbers
8. ✅ No extra page numbers

### Phase 6C - Range Display
9. ✅ Range format is valid (X - Y of Z)
10. ✅ Range start is 1 for first page
11. ✅ Range end matches expected based on per page setting
12. ✅ Total count in range matches header total

### Phase 6D - Dynamic Per Page Testing
13. ✅ Results per page dropdown can be changed
14. ✅ Page count updates correctly with 20 per page
15. ✅ Page numbers update correctly
16. ✅ Range display updates correctly
17. ✅ Can switch back to original setting

## Example Execution Flow

```
Phase 6: Validating Pagination...
  ✓ First/Previous/Next/Last buttons visible
  ✓ Results per page dropdown visible
  ✓ Current page info format correct

Phase 6A: Validating Table Row Count vs Total Count...
  Total Items from header: 31
  Visible table rows: 10
  Results per page: 10
  ✓ Table Row Count Match: Expected 10 rows, found 10

Phase 6B: Validating Page Numbers based on Results Per Page...
  Displayed page numbers: [1, 2, 3, 4]
  Expected total pages: 4 (Total: 31, Per Page: 10)
  ✓ Page Numbers Count: 4 pages for 31 items with 10 per page
  ✓ Page Numbers Sequence: Should be 1, 2, 3... up to 4

Phase 6C: Validating Range Display...
  Parsed range: 1 - 10 of 31
  ✓ Range Start: Should start at 1 for first page
  ✓ Range End: Should be 10 for first page with 10 per page
  ✓ Total Count Consistency: Range total should match header total

Phase 6D: Testing pagination with different Results Per Page values...
  Changing Results Per Page to 20...
  Expected pages with 20 per page: 2
  ✓ Page Count with 20 per page: For 31 items
  ✓ Page Numbers with 20 per page: Should be 1, 2... up to 2
  ✓ Range End with 20 per page: Should be 20
  
  Switching back to 10 Results Per Page...
```

## Benefits

1. **Comprehensive Coverage**: Validates all aspects of pagination functionality
2. **Dynamic Testing**: Tests multiple scenarios (10, 20 per page)
3. **Mathematical Validation**: Uses calculations to verify expected behavior
4. **Data-Driven**: Adapts to actual data count on the page
5. **Consistent Validation**: Ensures all count displays match across UI
6. **Reusable**: Helper methods can be used in other test cases

## Usage in Other Modules

The same validation approach can be applied to any module's list page by:
1. Using the helper methods from `BasePage` or module-specific page objects
2. Following the same 6-phase validation structure
3. Adapting XPath locators if UI differs

## Notes

- All validations use soft assertions to capture all failures in one run
- Screenshots are captured at key validation points
- Detailed logging helps with debugging
- The test is resilient to different data counts (adapts to actual count)
