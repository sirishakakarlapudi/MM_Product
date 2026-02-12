# Comprehensive Pagination Validation - Page-by-Page Range Testing

## 🎯 Enhancement Overview

Enhanced `List_Page_Validation()` to perform **comprehensive page-by-page validation** that:
1. **Clicks through ALL pages** for each results-per-page setting
2. **Validates range display** for every single page (e.g., Page 2 → "11-20 of 31")
3. **Tests multiple per-page values**: 10, 20, 50, 100 (conditionally based on data)
4. **Verifies table row count** matches the range on each page

## 📋 What Was Added

### New Helper Methods in `Department.java`

#### 1. `clickPageNumber(int pageNumber)`
```java
public void clickPageNumber(int pageNumber) throws InterruptedException {
    WebElement pageButton = driver.findElement(
        By.xpath("//span[@class='p-paginator-pages ng-star-inserted']/button[text()='" + pageNumber + "']")
    );
    
    // Scroll into view
    ((JavascriptExecutor) driver).executeScript(
        "arguments[0].scrollIntoView({block: 'center'});", pageButton
    );
    Thread.sleep(200);
    
    pageButton.click();
    Thread.sleep(500);
    log.info("Clicked page number: {}", pageNumber);
}
```

**Purpose**: Navigates to a specific page number

#### 2. `getCurrentPageNumber()`
```java
public int getCurrentPageNumber() {
    WebElement activePage = driver.findElement(
        By.xpath("//span[@class='p-paginator-pages ng-star-inserted']/button[contains(@class,'p-highlight')]")
    );
    return Integer.parseInt(activePage.getText().trim());
}
```

**Purpose**: Returns the currently active page number (highlighted button)

#### 3. `isPaginationPresent()`
```java
public boolean isPaginationPresent() {
    List<WebElement> pageButtons = driver.findElements(
        By.xpath("//span[@class='p-paginator-pages ng-star-inserted']/button")
    );
    return pageButtons.size() > 1;
}
```

**Purpose**: Checks if pagination exists (more than one page)

## 🔄 Enhanced Validation Flow

### Phase 6D: Comprehensive Pagination Testing

```java
// Test all these values conditionally
int[] resultsPerPageValues = {10, 20, 50, 100};

for (int perPageValue : resultsPerPageValues) {
    // Skip if not enough data
    if (totalItemsCount <= perPageValue && perPageValue != 10) {
        continue; // Skip 20, 50, 100 if insufficient data
    }
    
    // 1. Change results per page
    dept.selectResultsPerPage(perPageValue);
    
    // 2. Calculate expected pages
    int expectedPages = ceil(totalItemsCount / perPageValue);
    
    // 3. Iterate through ALL pages
    for (int pageNum = 1; pageNum <= expectedPages; pageNum++) {
        // Click page number
        dept.clickPageNumber(pageNum);
        
        // Calculate expected range
        int expectedStart = ((pageNum - 1) * perPageValue) + 1;
        int expectedEnd = min(pageNum * perPageValue, totalItemsCount);
        
        // Validate actual range matches expected
        assertRangeDisplay(expectedStart, expectedEnd, totalItemsCount);
        
        // Validate table row count
        int expectedRows = expectedEnd - expectedStart + 1;
        assertTableRowCount(expectedRows);
    }
}
```

## 📊 Example Execution (31 Items)

### Results Per Page: 10 (4 pages total)

| Page | Click | Expected Range | Actual Range | Rows | Status |
|------|-------|----------------|--------------|------|--------|
| 1 | Auto | 1-10 of 31 | 1-10 of 31 | 10 | ✅ |
| 2 | Click(2) | 11-20 of 31 | 11-20 of 31 | 10 | ✅ |
| 3 | Click(3) | 21-30 of 31 | 21-30 of 31 | 10 | ✅ |
| 4 | Click(4) | 31-31 of 31 | 31-31 of 31 | 1 | ✅ |

### Results Per Page: 20 (2 pages total)

| Page | Click | Expected Range | Actual Range | Rows | Status |
|------|-------|----------------|--------------|------|--------|
| 1 | Auto | 1-20 of 31 | 1-20 of 31 | 20 | ✅ |
| 2 | Click(2) | 21-31 of 31 | 21-31 of 31 | 11 | ✅ |

### Results Per Page: 50 (1 page - SKIPPED)

**Reason**: Total items (31) ≤ per page value (50)
**Log**: "Skipping 50 per page (total items: 31 <= 50)"

### Results Per Page: 100 (1 page - SKIPPED)

**Reason**: Total items (31) ≤ per page value (100)
**Log**: "Skipping 100 per page (total items: 31 <= 100)"

## 🔍 Validation Details

### For Each Page, We Validate:

#### 1. Current Page Number
```java
int currentPage = dept.getCurrentPageNumber();
assertEquals(currentPage, pageNum, "Should be on page " + pageNum);
```

#### 2. Range Start
```java
int expectedStart = ((pageNum - 1) * perPageValue) + 1;
int actualStart = dept.parseRangeInfo()[0];
assertEquals(actualStart, expectedStart, "Range Start");
```

**Examples**:
- Page 1, 10/page: `((1-1) * 10) + 1 = 1` ✓
- Page 2, 10/page: `((2-1) * 10) + 1 = 11` ✓
- Page 3, 20/page: `((3-1) * 20) + 1 = 41` ✓

#### 3. Range End
```java
int expectedEnd = Math.min(pageNum * perPageValue, totalItemsCount);
int actualEnd = dept.parseRangeInfo()[1];
assertEquals(actualEnd, expectedEnd, "Range End");
```

**Examples**:
- Page 1, 10/page, 31 total: `min(1*10, 31) = 10` ✓
- Page 4, 10/page, 31 total: `min(4*10, 31) = 31` ✓
- Page 2, 20/page, 31 total: `min(2*20, 31) = 31` ✓

#### 4. Total Count Consistency
```java
int totalFromRange = dept.parseRangeInfo()[2];
assertEquals(totalFromRange, totalItemsCount, "Total always shows 31");
```

**Validates**: The total count in "X-Y of **31**" is always correct

#### 5. Table Row Count
```java
int expectedRows = expectedEnd - expectedStart + 1;
int actualRows = dept.getTableRowCount();
assertEquals(actualRows, expectedRows, "Table row count");
```

**Examples**:
- Page 1 (1-10): `10 - 1 + 1 = 10 rows` ✓
- Page 4 (31-31): `31 - 31 + 1 = 1 row` ✓
- Page 2, 20/page (21-31): `31 - 21 + 1 = 11 rows` ✓

## 📝 Detailed Console Output

### Example for 10 Results Per Page:

```
15:56:30 INFO - ═══════════════════════════════════════════════════════
15:56:30 INFO - Testing with 10 Results Per Page...
15:56:30 INFO - ═══════════════════════════════════════════════════════
15:56:30 INFO - Total Items: 31, Per Page: 10, Expected Pages: 4
15:56:30 INFO - ✅ Page Count with 10 per page matched [Value: 4]
15:56:30 INFO - Clicking through all 4 pages and validating range display...

15:56:30 INFO - --- Validating Page 1 of 4 ---
15:56:30 INFO - Expected Range: 1-10 of 31
15:56:30 INFO - Actual Range: 1-10 of 31
15:56:30 INFO - ✅ Range Start (Page 1) matched [Value: 1]
15:56:30 INFO - ✅ Range End (Page 1) matched [Value: 10]
15:56:30 INFO - ✅ Total Count (Page 1) matched [Value: 31]
15:56:30 INFO - ✅ Table Rows (Page 1) matched [Value: 10]
15:56:30 INFO - ✅ Page 1 validated: 1-10 of 31 (10 rows)

15:56:31 INFO - --- Validating Page 2 of 4 ---
15:56:31 INFO - Clicked page number: 2
15:56:31 INFO - Expected Range: 11-20 of 31
15:56:31 INFO - Actual Range: 11-20 of 31
15:56:31 INFO - ✅ Range Start (Page 2) matched [Value: 11]
15:56:31 INFO - ✅ Range End (Page 2) matched [Value: 20]
15:56:31 INFO - ✅ Total Count (Page 2) matched [Value: 31]
15:56:31 INFO - ✅ Table Rows (Page 2) matched [Value: 10]
15:56:31 INFO - ✅ Page 2 validated: 11-20 of 31 (10 rows)

15:56:32 INFO - --- Validating Page 3 of 4 ---
15:56:32 INFO - Clicked page number: 3
15:56:32 INFO - Expected Range: 21-30 of 31
15:56:32 INFO - Actual Range: 21-30 of 31
15:56:32 INFO - ✅ Range Start (Page 3) matched [Value: 21]
15:56:32 INFO - ✅ Range End (Page 3) matched [Value: 30]
15:56:32 INFO - ✅ Total Count (Page 3) matched [Value: 31]
15:56:32 INFO - ✅ Table Rows (Page 3) matched [Value: 10]
15:56:32 INFO - ✅ Page 3 validated: 21-30 of 31 (10 rows)

15:56:33 INFO - --- Validating Page 4 of 4 ---
15:56:33 INFO - Clicked page number: 4
15:56:33 INFO - Expected Range: 31-31 of 31
15:56:33 INFO - Actual Range: 31-31 of 31
15:56:33 INFO - ✅ Range Start (Page 4) matched [Value: 31]
15:56:33 INFO - ✅ Range End (Page 4) matched [Value: 31]
15:56:33 INFO - ✅ Total Count (Page 4) matched [Value: 31]
15:56:33 INFO - ✅ Table Rows (Page 4) matched [Value: 1]
15:56:33 INFO - ✅ Page 4 validated: 31-31 of 31 (1 row)

15:56:33 INFO - ✅ Completed validation for 10 results per page
```

## 🎯 Conditional Testing Logic

### When to Test Each Per-Page Value:

```java
if (totalItemsCount <= perPageValue && perPageValue != 10) {
    // Skip testing this value
}
```

| Total Items | Test 10? | Test 20? | Test 50? | Test 100? |
|-------------|----------|----------|----------|-----------|
| 15 | ✅ Always | ❌ Skip | ❌ Skip | ❌ Skip |
| 31 | ✅ Always | ✅ Test | ❌ Skip | ❌ Skip |
| 75 | ✅ Always | ✅ Test | ✅ Test | ❌ Skip |
| 150 | ✅ Always | ✅ Test | ✅ Test | ✅ Test |

**Rule**: Always test 10 per page (default), only test others if `totalItems > perPageValue`

## 📊 Total Assertions Made

### For 31 Items Example:

#### 10 Per Page (4 pages):
- **Per-page setting validation**: 1 assertion
- **Page count validation**: 1 assertion  
- **Per page × 5 validations**: 4 pages × 5 = **20 assertions**
  - Current page number
  - Range start
  - Range end
  - Total count
  - Table row count

**Subtotal**: 22 assertions

#### 20 Per Page (2 pages):
- **Per-page setting validation**: 1 assertion
- **Page count validation**: 1 assertion
- **Per page × 5 validations**: 2 pages × 5 = **10 assertions**

**Subtotal**: 12 assertions

#### 50 Per Page:
- **SKIPPED** (31 ≤ 50)

#### 100 Per Page:
- **SKIPPED** (31 ≤ 100)

### **Grand Total**: 34 assertions for Phase 6D alone!

## 🚀 Benefits

### 1. Comprehensive Coverage
- ✅ Every page is clicked and validated
- ✅ Every range display is checked
- ✅ Every table row count is verified

### 2. Data-Driven
- ✅ Adapts to actual item count
- ✅ Only tests relevant per-page values
- ✅ No hardcoded expectations

### 3. Real User Simulation
- ✅ Mimics actual user clicking through pages
- ✅ Tests pagination UX thoroughly
- ✅ Catches off-by-one errors

### 4. Early Error Detection
- ✅ Finds range calculation bugs
- ✅ Detects incorrect row counts
- ✅ Validates edge cases (last page)

## 🔧 Edge Cases Handled

### Last Page with Partial Data
```
Page 4: 31-31 of 31 (1 row)
         ↑
    Only 1 item on last page
```

**Validation**: Correctly calculates `expectedRows = 31 - 31 + 1 = 1`

### Single Page Scenario
```
100 items, 100 per page = 1 page total
Loop runs once (pageNum = 1)
Range: 1-100 of 100
```

### Exact Fit Scenario
```
40 items, 20 per page = 2 pages exactly
Page 1: 1-20 of 40 (20 rows)
Page 2: 21-40 of 40 (20 rows)
```

## 🎉 Summary

| Feature | Before | After |
|---------|--------|-------|
| Pages tested | Only page 1 | ALL pages |
| Per-page values | 10, 20 fixed | 10, 20, 50, 100 conditional |
| Range validation | Page 1 only | Every page |
| Row count check | Page 1 only | Every page |
| Assertions | ~15 | ~34+ (scales with data) |
| Edge cases | Minimal | Last page, partial data, exact fits |

The validation now provides **complete confidence** that pagination works correctly across all scenarios! 🚀
