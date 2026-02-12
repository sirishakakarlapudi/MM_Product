# Breadcrumbs Validation in View Page

## 🎯 Objective
Added a dedicated test method `BreadCrumbs_Validation_In_ViewPage` to validate the breadcrumb navigation specific to the "View Department" screen.

## 🛠️ Implementation Details

### Method Logic
Mirroring `BreadCrumbs_Validation_In_CreatePage`, this method verifies:
1.  **URL**: Contains "view" or "details".
2.  **Sequence**: `Home` > `Department` > `View Department`.
3.  **Visuals**: Checks for separator arrows and Home icon.
4.  **Navigation**:
    *   Clicking "Department" returns to List Page.
    *   Clicking "Home" returns to Home Dashboard.

### 🔗 Dependency
- **Depends On**: `Creation_Of_Department`
- **Priority**: 9
- **Reason**: Ensures a department record exists before attempting to view it.

## 📝 Code Snippet
```java
@Test(groups = { "breadcrumbsvalidationinview" }, dependsOnMethods = "Creation_Of_Department", priority = 9)
public void BreadCrumbs_Validation_In_ViewPage() throws Throwable {
    // Navigate to View
    dept.clickView(currentDeptName);
    
    // Validate Structure
    List<String> expectedBreadcrumbs = Arrays.asList("Home", MASTER_MODULE, "View " + MASTER_MODULE);
    sa.assertEquals(actualBreadcrumbs, expectedBreadcrumbs, "Breadcrumbs Sequence");
    
    // Validate Navigation
    dept.masterClick(MASTER_MODULE);
    sa.assertTrue(driver.getCurrentUrl().contains("departments"), "Back to List");
}
```

## ✅ Result
Comprehensive validation of page hierarchy and navigation paths within the View screen context.
