# Manual Test Case: End-to-End Department Workflow

**Test Case ID:** TC_DEPT_E2E_001  
**Module:** Master Module -> Departments  
**Objective:** Verify the complete lifecycle of a Department record, including creation, viewing, editing (pre-approval), returning for correction, re-editing, and final approval.

| **Step No.** | **Step Description** | **Test Data (Example)** | **Expected Result** |
| :--- | :--- | :--- | :--- |
| **1. Login** | Launch the application URL and log in with valid credentials. | **URL:** *(Your App URL)*<br>**User:** Creator (e.g., `qa001`) | User logs in successfully and lands on the dashboard. Toast message confirms login. |
| **2. Navigate** | Navigate to `Masters` -> `Departments`. | | The Departments list screen is displayed. |
| **3. Create** | Click the **"Create"** button. | | The "Create Department" form opens. |
| **4. Fill Form** | Enter a unique Department Name and Description. | **Name:** `OQ Department_Test`<br>**Desc:** `Initial Description` | Input fields accept the text correctly. |
| **5. Submit** | Click the **"Submit"** button. | | The record is saved. A toast message appears confirming creation. The new department appears in the "Pending" or "Draft" list with status "Review/Approve Pending". |
| **6. Authenticate** | Enter password to simple sign/authenticate the action. | **Password:** *(Creator's Password)* | Authentication is successful. |
| **7. View** (Optional) | Locate the newly created department and click the **"View"** (Eye icon) button. | | The View screen opens, showing the correct details (e.g. `OQ Department_Test`). User can click "Back" to return to the list. |
| **8. Edit** (Optional) | Locate the department and click the **"Edit"** (Pencil icon) button. Update the description. | **New Desc:** `Updated Description` | The Edit screen opens. Changes are saved upon clicking "Update", and the list reflects the updated description. |
| **9. Review/Return** | **If using separate users:** Log out and log in as a Reviewer/Approver.<br>Locate the department, click **"Actions"** -> **"Approve"**. | **User:** Approver (e.g., `qa003`) | The Approval/Return dialog opens. |
| **10. Return Action** | In the dialog, enter remarks and click **"Return"**. | **Remarks:** `Department Returned for Correction` | The record status changes to "Returned" (or similar). A toast message confirms the action. |
| **11. Correction** | **Switch back to Creator (if needed).**<br>Locate the returned department and click **"Edit"**. Modify the name and description. | **New Name:** `OQ Department Edited`<br>**New Desc:** `Final Description` | The Edit form opens. The user can update the fields. Clicking "Update" saves the changes and resubmits the record for approval. |
| **12. Final Approval** | **Switch to Approver (if needed).**<br>Locate the department, click **"Actions"** -> **"Approve"**. | **User:** Approver (e.g., `qa003`) | The Approval dialog opens. |
| **13. Confirm Approval** | Enter approval remarks and click **"Approve"**. | **Remarks:** `Final OQ Approval` | The record is approved. It moves from the "Pending" list to the "Active" list. Status changes to "Active" or "Approved". |
| **14. Logout** | Click the profile icon and select **"Logout"**. | | User is logged out successfully. |
| **15. Backup** | (Backend verification) Check if database backup was triggered. | | Database dump files are created in the `DB_Backups` folder. |

---

### **Pre-requisites:**
1.  Application is accessible.
2.  Two valid user accounts (Creator and Approver) exist if testing the multiple-user workflow.
3.  Database connection is valid (for the backup step).

### **Post-conditions:**
*   A new Department record exists in the "Active" state.
*   Audit logs (if any) should reflect the creation, return, edit, and approval actions.
*   Screenshots/Evidence document is generated (if enabled).
