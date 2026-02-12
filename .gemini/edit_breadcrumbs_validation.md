# Breadcrumbs Validation in Edit Page

## 🎯 Objective
Added test method `BreadCrumbs_Validation_In_EditPage` to validate breadcrumb navigation on the "Edit Department" screen.

## 🛠️ Logic & Workflow

### 1. Navigation
- Navigates to the Edit page for `currentDeptName` (Department Name).

### 2. Validation
- **URL**: Validates URL contains "edit" or "update".
- **Sequence**: "Home" > `MASTER_MODULE` > "Edit " + `MASTER_MODULE`.
- **Arrows**: Ensures separator arrows are displayed.
- **Home Icon**: Ensures the Home icon is visible.

### 3. Navigation Links
- **Back to List**: Validates that clicking on `MASTER_MODULE` (e.g., "Departments") returns to the list view.
- **Back to Home**: Validates that clicking on "Home" returns to the `All Apps` dashboard.

## 🔗 Dependency
- **Depends On**: `Edit_Screen_Validation`
- **Priority**: 11
- **Reason**: Ensures field editing logic is tested before verifying breadcrumb navigation in the same context.

## 📝 Code Snippet
```java
@Test(dependsOnMethods = "Edit_Screen_Validation", priority = 11)
public void BreadCrumbs_Validation_In_EditPage() throws Throwable {
    dept.clickEdit(currentDeptName);
    List<String> expected = Arrays.asList("Home", MASTER_MODULE, "Edit " + MASTER_MODULE);
    sa.assertEquals(dept.getBreadcrumbsText(), expected, "Breadcrumb Sequence");
    // ... Navigation checks ...
}
```

## ✅ Result
Ensures consistent navigation experience across List, Create, View, and Edit screens.
