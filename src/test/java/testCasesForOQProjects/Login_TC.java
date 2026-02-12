package testCasesForOQProjects;

import static configData.LoginPageData.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.aventstack.extentreports.model.ScreenCapture;

import pageObjects.LoginPage;
import pageObjects.BasePage.FieldDetails;
import testBase.BaseClass;
import utilities.DatabaseBackupUtil;
import utilities.ScreenshotUtil;
import utilities.SoftAssertionUtil;
import utilities.CSVUtility;
import java.util.stream.Collectors;

public class Login_TC extends BaseClass {

	LoginPage loginPage;

	SoftAssertionUtil sa;

	@BeforeClass
	@Parameters({ "configFile" })
	public void setUp(@Optional("login.properties") String configFile) throws Exception {
		log.info("--- Starting Department Test Case Setup with config: {} ---", configFile);

		// Load the dynamic property file
		configData.LoginPageData.loadProperties(configFile);

		// Set conditional screenshot execution
		boolean screenshotsEnabled = "yes".equalsIgnoreCase(TAKE_SCREENSHOTS);
		ScreenshotUtil.setIsEnabled(screenshotsEnabled);

		if (screenshotsEnabled) {
			log.info("📸 Processing screenshot template and headers...");
			ScreenshotUtil.loadTemplateForEndAppend(TEMPLATE_PATH, OUTPUT_PATH);
			ScreenshotUtil.updateHeaderCellText(ACTUALHEADER, EXPECTEDHEADER);
			ScreenshotUtil.initScript(SCRIPT_NUMBER);
		}

		browserOpen();
		loginPage = new LoginPage(driver);
		sa = new SoftAssertionUtil();
		log.info("Setup completed. Screenshots enabled: {}", screenshotsEnabled);
	}

	// =======================Initial SetUp==========================

	@Test(groups = { "setup" }, priority = 1)
	public void initialSetUp() throws Exception {
		ScreenshotUtil.nextStep();
		log.info("Navigating to Chrome URL: {}", CHROME_URL);
		driver.navigate().to(CHROME_URL);
		log.info("Waiting for search box to be visible");
		loginPage.waitForElementToVisible(loginPage.getSearchBox());
		Assert.assertTrue(loginPage.getSearchBox().isDisplayed(), "Search box is not visible!");
		log.info("Searching for Application URL: {}", APP_URL);
		loginPage.searchBox(APP_URL);
		loginPage.waitForLoading();
		ScreenshotUtil.capture();
		log.info("Initial setup completed and screenshot captured");
	}

	// ========================Url Navigation===========================

	@Test(groups = { "url" }, priority = 2)
	public void url() throws Throwable {
		ScreenshotUtil.nextStep();
		driver.navigate().to(APP_URL);
		ScreenshotUtil.capture();
		log.info("Navigating to App URL: {}", APP_URL);
	}

	// ========================URL Validation===========================

	@Test(groups = { "urlvalidation" }, dependsOnGroups = "url", priority = 3)
	public void url_Validation() {
		log.info("--- Validating Application URL ---");
		driver.navigate().to(APP_URL);

		String currentUrl = driver.getCurrentUrl();
		sa.assertEquals(currentUrl, APP_URL, "APP_URL",
				"URL Mismatch! Expected: " + APP_URL + ", but found: " + currentUrl);

		log.info("Application URL validation completed.");
		sa.assertAll();
	}

	// ========================Login Field Validation===========================

	@Test(groups = { "loginvalidation" }, dependsOnMethods = "url", priority = 4)
	public void login_Field_Validation() {
		log.info("--- Validating Login Fields ---");

		// ================= DATA FROM CSV =================
		String csvPath = System.getProperty("user.dir") + "/src/test/resources/CSV_Data/CreateScreenValidation.csv";
		List<Map<String, String>> screenData = CSVUtility.getRowsByModule(csvPath, "Login");

		List<String> expectedFieldNames = screenData.stream().map(row -> row.get("FieldName"))
				.collect(Collectors.toList());
		List<String> expTypes = screenData.stream().map(row -> row.get("FieldType")).collect(Collectors.toList());
		List<String> expPlaceholders = screenData.stream().map(row -> row.get("Placeholder"))
				.collect(Collectors.toList());
		List<String> mandatoryFields = screenData.stream()
				.filter(row -> row.get("isMandatory").equalsIgnoreCase("Yes"))
				.map(row -> row.get("FieldName"))
				.collect(Collectors.toList());

		// ================= EXPECTED BUTTONS =================
		List<String> expectedButtons = new ArrayList<>();
		if (LOGIN_EXPECTED_BUTTONS != null && !LOGIN_EXPECTED_BUTTONS.isEmpty()) {
			expectedButtons = Arrays.asList(LOGIN_EXPECTED_BUTTONS.split(","));
		}

		// ================= EXPECTED LINKS =================
		List<String> expectedLinks = new ArrayList<>();
		if (LOGIN_EXPECTED_LINKS != null && !LOGIN_EXPECTED_LINKS.isEmpty()) {
			expectedLinks = Arrays.asList(LOGIN_EXPECTED_LINKS.split(","));
		}

		// ================= EXPECTED IMAGES =================
		List<String> expectedImages = new ArrayList<>();
		if (LOGIN_EXPECTED_IMAGES != null && !LOGIN_EXPECTED_IMAGES.isEmpty()) {
			expectedImages = Arrays.asList(LOGIN_EXPECTED_IMAGES.split(","));
		}

		// ================= ACTUAL UI DATA =================
		List<String> actualFields = loginPage.getDisplayedFieldLabels();
		List<String> actualButtons = loginPage.getDisplayedButtons();
		List<String> actualLinks = loginPage.getDisplayedLinks();
		List<String> actualImages = loginPage.getDisplayedImages();

		actualFields.removeIf(f -> f == null || f.trim().isEmpty());
		actualButtons.removeIf(b -> b == null || b.trim().isEmpty());
		actualLinks.removeIf(l -> l == null || l.trim().isEmpty());

		log.info("Actual Fields on UI  : " + actualFields);
		log.info("Actual Buttons on UI : " + actualButtons);
		log.info("Actual Links on UI   : " + actualLinks);
		log.info("Actual Images on UI  : " + actualImages);

		// ================= STRICT FIELD CONTRACT =================
		sa.assertEquals(actualFields, expectedFieldNames, "Fields Contract", "Strict match expected for fields");

		// ================= STRICT BUTTON CONTRACT =================
		sa.assertEquals(actualButtons, expectedButtons, "Buttons Contract", "Strict match expected for buttons");

		// ================= STRICT LINK CONTRACT =================
		sa.assertEquals(actualLinks, expectedLinks, "Links Contract", "Strict match expected for links");

		// ================= IMAGE VALIDATION =================
		sa.assertEquals(actualImages, expectedImages, "Images Contract",
				"Expected images: " + expectedImages + " Found: " + actualImages);

		// ================= INDIVIDUAL FIELD VALIDATION =================
		for (int i = 0; i < expectedFieldNames.size(); i++) {
			String fieldName = expectedFieldNames.get(i).trim();
			String expectedType = expTypes.get(i).trim();
			String expectedPH = expPlaceholders.get(i).trim();

			log.info("Validating Field: [" + fieldName + "]");

			// existence & visibility
			sa.assertTrue(loginPage.isLabelDisplayed(fieldName), "Label visibility", fieldName);
			WebElement input = loginPage.getInputFieldForLabel(fieldName);
			sa.assertNotNull(input, "Field existence in DOM", fieldName);
			sa.assertTrue(input.isDisplayed(), "Field visibility", fieldName);

			// Type validation (Input vs Drop)
			String actualType = loginPage.getFieldType(fieldName);
			sa.assertEquals(actualType, expectedType, "Field Type", fieldName);

			// Placeholder validation
			String actualPH = loginPage.getPlaceholder(fieldName);
			sa.assertEquals(actualPH, expectedPH, "Placeholder", fieldName);

			// Editable check
			sa.assertTrue(input.isEnabled(), "Field Editability", fieldName);

			// Mandatory Star check
			if (mandatoryFields.contains(fieldName)) {
				boolean hasRedStar = false;
				try {
					hasRedStar = loginPage.isRedStarDisplayedForField(fieldName).isDisplayed();
				} catch (Exception e) {
				}
				sa.assertTrue(hasRedStar, "Mandatory Red Star", fieldName);
			}
		}

		// ================= BUTTON STATE =================
		for (String btnName : expectedButtons) {
			WebElement btn = loginPage.getButtonByText(btnName);
			sa.assertNotNull(btn, "Button ", btnName);
			sa.assertTrue(btn.isDisplayed(), "Button", btnName);
			sa.assertTrue(btn.isEnabled(), "Button Enable", btnName);
		}

		log.info("Login field validation completed.");

		try {
			ScreenshotUtil.capture();
		} catch (Exception e) {
			e.printStackTrace();
		}

		sa.assertAll();
	}

	// =======================Negative Login Scenarios===========================

	@Test(groups = { "login_negative" }, priority = 5)
	public void negative_Login_Validation() throws Throwable {
		log.info("--- Validating Negative Login Scenarios ---");

		Object[][] scenarios = {
				{ "invalid_user", "admin", "invalid_user: User not found", "" },
				{ "admin", "invalid_pass", "Invalid Credentials. No. of attempts Left: 3", "" },
				{ "", "admin", "required", "Username" },
				{ "admin", "", "required", "Password" },
				{ "", "", "required", "Both" }
		};

		for (Object[] scenario : scenarios) {
			String user = (String) scenario[0];
			String pass = (String) scenario[1];
			String expectedKeyword = (String) scenario[2];

			log.info("Testing Login with: [User: '{}', Pass: '{}']", user, pass);
			loginPage.clearLoginFields();

			String toast = loginPage.login(user, pass, PC_DB_NAME);
			log.info("Captured Toast: {}", toast);

			if (user.isEmpty() || pass.isEmpty()) {
				if (user.isEmpty()) {
					String error = loginPage.getFieldRequiredErrorMessage("Username");
					sa.assertTrue(error.toLowerCase().contains("username is required"),
							"Inline Error (User)", "Scenario: " + user + "/" + pass);
				}
				if (pass.isEmpty()) {
					String error = loginPage.getFieldRequiredErrorMessage("Password");
					sa.assertTrue(error.toLowerCase().contains("password is required"),
							"Inline Error (Pass)", "Scenario: " + user + "/" + pass);
				}
			} else {
				sa.assertTrue(toast.toLowerCase().contains(expectedKeyword.toLowerCase()),
						"Toast Error Message", "Scenario: " + user + "/" + pass);
			}

			ScreenshotUtil.capture();
			sa.assertFalse(toast.equalsIgnoreCase("Login successful"), "Toast Result", "Should not login");
		}

		log.info("Negative login validation completed.");
		sa.assertAll();
	}

	// =======================User Login===========================

	@Test(groups = { "userlogin" }, priority = 6)
	public void userLoginBeforeCreate() throws Throwable {
		ScreenshotUtil.nextStep();
		log.info("Executing the login flow without assertion...");
		if (ACTIONSPERFORMEDBY.equalsIgnoreCase("single")) {
			loginPage.login(USERNAME, PASSWORD, PC_DB_NAME);
		} else {
			loginPage.login(USERNAME1, PASSWORD1, PC_DB_NAME);
		}
	}

	// ==============User Login Validation===========================
	@Test(groups = { "userlogin_validation" }, priority = 7)
	public void userLoginBeforeCreate_validation() throws Throwable {
		log.info("--- Validating User Login Toaster ---");
		String toast;
		if (ACTIONSPERFORMEDBY.equalsIgnoreCase("single")) {
			toast = loginPage.login(USERNAME, PASSWORD, PC_DB_NAME);
		} else {
			toast = loginPage.login(USERNAME1, PASSWORD1, PC_DB_NAME);
		}
		sa.assertEquals(toast, "Login successful", "Toast Message", "Login");
		sa.assertAll();
	}

	// ===========================App Click===========================

	@Test(groups = { "appselect" }, priority = 9)
	public void appsSelect() throws Throwable {
		log.info("--- Clicking on Apps ---");
		ScreenshotUtil.nextStep();
		loginPage.click_titleMasters();
		log.info("Clicked on Masters title");
		ScreenshotUtil.capture();
	}

	// ===============All Apps Validation Based on DBPrivileges============

	@Test(groups = { "allappvalidation" }, priority = 8)
	public void all_Apps_Validation() throws Throwable {
		log.info("--- Validating Module Selection Elements Based on DB Privileges ---");

		// 1. Get dynamically expected apps from DB
		List<String> expectedModuleNames = loginPage.getExpectedAppsFromModQuery(loginPage.currentUsername,
				MASTER_DB_NAME);
		log.info("Dynamically determined expected modules: {}", expectedModuleNames);

		// 2. Define metadata for all possible modules (to check icons/desc if they
		// appear)
		Map<String, FieldDetails> appMetadata = new LinkedHashMap<>();
		appMetadata.put("MASTERS",
				new FieldDetails("Central repository for managing core data", "/content/images/settings.png"));
		appMetadata.put("MM (Material Management)", new FieldDetails(
				"Manages Inventory, Vendors, Product Cycle, Time & Costing.", "/content/images/mmicon.png"));

		// 3. Validate each expected module on UI
		for (String moduleName : expectedModuleNames) {
			FieldDetails data = appMetadata.get(moduleName);
			if (data == null) {
				log.warn("No metadata found for module: {}. Validation may be partial.", moduleName);
				continue;
			}

			WebElement card = loginPage.getCardByTitle(moduleName);
			sa.assertNotNull(card, "Module Card", moduleName);

			WebElement icon = loginPage.getIcon(moduleName);
			sa.assertTrue(icon.isDisplayed(), "Module Icon", moduleName);
			sa.assertTrue(icon.getAttribute("src").contains(data.getIconPath()), "Icon Path", moduleName);

			WebElement desc = loginPage.getDescription(moduleName);
			sa.assertEquals(desc.getText().trim(), data.getDescription(), "Module Description", moduleName);

			log.info("✅ Home card validated: " + moduleName);
		}

		// 4. Strict check: Compare full UI list vs DB expected list
		List<String> actualApps = loginPage.getAllAppsDisplayed();
		actualApps.removeIf(f -> f == null || f.trim().isEmpty());
		log.info("Actual modules on UI: {}", actualApps);

		sa.assertEquals(actualApps.size(), expectedModuleNames.size(), "Module Count", "Apps on UI");
		sa.assertTrue(actualApps.containsAll(expectedModuleNames), "Module Presence", "All Expected Apps");
		sa.assertTrue(expectedModuleNames.containsAll(actualApps), "Module Restriction", "No Extra Apps");

		log.info("Module validation successfully completed.");
		sa.assertAll();
	}

	// ====================Validation of Breadcrumbs and URL after clicking on
	// Masters====================
	@Test(groups = { "breadcrumbsvalidationonhome" }, priority = 10)
	public void breadCrumbs_Validation_On_Home() throws Throwable {
		log.info("--- Validating Breadcrumbs and URL After Clicking Masters ---");

		loginPage.click_titleMasters();
		loginPage.waitForLoading();
		ScreenshotUtil.capture();

		// 1. Validate Current URL
		String mastersUrl = driver.getCurrentUrl();
		log.info("Current URL after clicking Masters: {}", mastersUrl);
		sa.assertTrue(mastersUrl.contains("masters"), "Masters Navigation", "URL Validation");

		// 2. Validate Breadcrumb Labels (Strict Case Sensitive)
		List<String> actualBreadcrumbs = loginPage.getBreadcrumbsText();
		log.info("Actual Breadcrumbs on UI: {}", actualBreadcrumbs);

		List<String> expectedBreadcrumbs = Arrays.asList("Home", "Dashboard");
		sa.assertEquals(actualBreadcrumbs, expectedBreadcrumbs, "Breadcrumbs Sequence",
				"Exact Text Match (Case Sensitive)");

		// 3. Validate Separator Arrow
		List<WebElement> arrows = loginPage.getBreadcrumbArrows();
		boolean arrowVisible = !arrows.isEmpty() && arrows.get(0).isDisplayed();
		sa.assertTrue(arrowVisible, "Breadcrumb Separator", "Arrow Presence between Home and Dashboard");

		// 4. Home icon check
		WebElement homeIcon = loginPage.getHomeIcon();
		sa.assertTrue(homeIcon.isDisplayed(), "Home Icon", "Presence");

		// 5. Verify Home link can navigate back
		log.info("Clicking on Home breadcrumb to verify navigation back to All Apps...");
		loginPage.HomeBreadcrumbLink();
		loginPage.waitForLoading();
		ScreenshotUtil.capture();

		String homeUrl = driver.getCurrentUrl();
		sa.assertTrue(homeUrl.contains("/home"), "Breadcrumb Navigation", "Back to All Apps Page");

		log.info("Breadcrumbs, Arrows, and URL validation completed.");
		loginPage.click_titleMasters();
		loginPage.waitForLoading();
		sa.assertAll();
	}

	// ==========Validation of Side-Navigation Modules Based on DB
	// Privileges==========

	@Test(groups = { "sidenavvalidation" }, dependsOnMethods = "appsSelect", priority = 11)
	public void SideNav_Validation() throws Throwable {
		log.info("--- Validating Side-Navigation Modules Based on DB Privileges ---");

		// 1. Get dynamically expected side-nav modules from DB
		List<String> expectedModules = loginPage.getExpectedSideNavFromDB(loginPage.currentUsername,
				SIDE_NAV_MODULE_MAPPING,
				PC_DB_NAME);
		log.info("Dynamically determined expected side-nav modules: {}", expectedModules);

		// 2. Expand each parent module and collect visible items incrementally (to
		// handle accordion behavior)
		List<String> finalActualModules = new ArrayList<>();

		// Initial capture for static/already-open modules
		finalActualModules.addAll(loginPage.getAllSideNavModulesDisplayed());

		String[] mappings = SIDE_NAV_MODULE_MAPPING.split(";");
		for (String map : mappings) {
			String[] parts = map.split(":");
			if (parts.length > 2) {
				String parentModule = parts[0].trim();
				if (expectedModules.contains(parentModule)) {
					log.info("Expanding for incremental capture: {}", parentModule);
					loginPage.expandSideNavModule(parentModule);
					// Collect newly visible sub-modules
					for (String active : loginPage.getAllSideNavModulesDisplayed()) {
						if (!finalActualModules.contains(active))
							finalActualModules.add(active);
					}
				}
			}
		}
		ScreenshotUtil.capture();

		System.out.println("====================================================================");
		System.out.println("SIDE-NAV VALIDATION FOR USER: " + loginPage.currentUsername);
		System.out.println("Expected Modules from DB Mapping: " + expectedModules);
		System.out.println("Actual Combined Modules from UI : " + finalActualModules);
		System.out.println("====================================================================");

		// 3. Conditional Assertion
		List<String> missing = expectedModules.stream().filter(e -> !finalActualModules.contains(e)).toList();
		boolean isAdmin = utilities.DatabaseBackupUtil.hasAuthority(loginPage.currentUsername, "ROLE_ADMIN");

		if (isAdmin) {
			log.info("Performing STRICT sidebar validation for ROLE_ADMIN: UI must exactly match DB Mapping.");
			sa.assertEquals(finalActualModules.size(), expectedModules.size(), "Side-Nav Count mismatch",
					"Total Modules");
			sa.assertTrue(missing.isEmpty(), "Module Presence", "Missing: " + missing);

			List<String> extra = finalActualModules.stream().filter(a -> !expectedModules.contains(a)).toList();
			sa.assertTrue(extra.isEmpty(), "Module Restriction", "Extra: " + extra);
		} else {
			log.info("Performing PRESENCE sidebar validation for User {}: UI must contain all DB-authorized modules.",
					loginPage.currentUsername);
			// For non-admin, we only care if they HAVE what they should have. Extra
			// visibility is ignored here.
			sa.assertTrue(missing.isEmpty(), "Module Presence",
					"Database-authorized modules missing from UI: " + missing);
		}

		log.info("Side-Navigation validation successfully completed.");
		sa.assertAll();
	}

	// ====================Module Switch from Header Dropdown====================

	@Test(groups = { "moduleSwitch" }, priority = 12)
	public void moduleSwitch() throws Throwable {
		ScreenshotUtil.nextStep();
		log.info("--- Validating Module Switch from Header Dropdown ---");

		// Click dropdown and select different module (MM)
		loginPage.click_allAppsdropdown();

		ScreenshotUtil.capture();
		loginPage.click_dropdownMM();
		ScreenshotUtil.capture();
		loginPage.click_confirmYes();
		ScreenshotUtil.capture();
	}

	// ============Validation of Header Dropdown for App Switching==============
	@Test(groups = { "headerdropdownvalidation" }, priority = 13)
	public void header_App_Dropdown_Validation() throws Throwable {
		log.info("--- Validating Header Application Dropdown ---");

		// 1. Verify currently selected app in header
		String selectedTitle = loginPage.getSelectedAppTitle();
		String selectedIcon = loginPage.getSelectedAppIconSrc();

		log.info("Header Selected App: {} [Icon: {}]", selectedTitle, selectedIcon);
		// The active title is 'MASTER' but DB might say 'MASTERS'
		sa.assertTrue(selectedTitle.equalsIgnoreCase("MASTER") || selectedTitle.equalsIgnoreCase("MASTERS"),
				"Header Title", "Validation");

		// 2. Click dropdown to see all apps
		loginPage.click_allAppsdropdown();
		Thread.sleep(500); // Wait for dropdown animation
		loginPage.waitForLoading();

		// 3. Get expected apps from DB for comparison
		List<String> expectedModuleNames = loginPage.getExpectedAppsFromModQuery(loginPage.currentUsername,
				MASTER_DB_NAME);

		// 4. Define metadata for validation (Title, Description, Icon) - matching DB
		// naming
		Map<String, FieldDetails> appMetadata = new LinkedHashMap<>();
		appMetadata.put("MASTERS", new FieldDetails("Masters creation of all..", "qmsicon.png"));
		appMetadata.put("MM (Material Management)",
				new FieldDetails("Manages Inventory, Vendors, Pro...", "mmicon.png"));

		// 5. Get actual apps from UI dropdown
		List<LoginPage.AppDropdownDetails> actualDropdownApps = loginPage.getAppDropdownDetails();
		log.info("Actual Apps in Header Dropdown: {}", actualDropdownApps);

		// 6. Validate presence and details
		for (String expectedName : expectedModuleNames) {
			// Find matching app in dropdown by partial/starts-with (handles MASTERS vs
			// MASTER)
			LoginPage.AppDropdownDetails actualApp = actualDropdownApps.stream()
					.filter(a -> expectedName.toUpperCase().startsWith(a.title.toUpperCase())
							|| a.title.toUpperCase().startsWith(expectedName.toUpperCase().split(" ")[0]))
					.findFirst().orElse(null);

			sa.assertNotNull(actualApp, "App Dropdown Presence", expectedName);

			if (actualApp != null) {
				FieldDetails meta = appMetadata.get(expectedName);
				if (meta != null) {
					sa.assertEquals(actualApp.description, meta.getDescription(), "App Description", expectedName);
					sa.assertTrue(actualApp.iconSrc.contains(meta.getIconPath()), "App Icon Path", expectedName);
				}
			}
		}

		// 7. Strict count check
		sa.assertEquals(actualDropdownApps.size(), expectedModuleNames.size(), "Dropdown App Count", "Exact match");

		log.info("Header application dropdown validation completed.");
		loginPage.click_allAppsdropdown();
		sa.assertAll();
	}
	// ===============Validation of App Switcher Confirmation Popup==========

	@Test(groups = { "switcherconfirmation" }, priority = 14)
	public void switcher_Confirmation_Validation() throws Throwable {
		log.info("--- Validating App Switcher Confirmation Popup ---");

		String initialTitle = loginPage.getSelectedAppTitle();
		log.info("Initial App in Header: {}", initialTitle);

		// 1. Click current app (MASTER) - No popup expected
		loginPage.click_allAppsdropdown();
		Thread.sleep(500);
		loginPage.clickAppByTitle(initialTitle);
		Thread.sleep(500);
		sa.assertFalse(loginPage.isConfirmationPopupDisplayed(), "Confirmation Popup", "Current App Click");

		// 2. Click different app (MM) - Popup expected
		loginPage.click_allAppsdropdown();
		Thread.sleep(500);
		// Note: Using "MM" as a common keyword for the other module
		loginPage.clickAppByTitle("MM");
		Thread.sleep(500);
		ScreenshotUtil.capture();
		sa.assertTrue(loginPage.isConfirmationPopupDisplayed(), "Confirmation Popup", "Different App Click (MM)");

		// 3. Click NO - Popup closed, title remains same
		loginPage.click_confirmNo();
		Thread.sleep(500);
		sa.assertFalse(loginPage.isConfirmationPopupDisplayed(), "Confirmation Popup", "After clicking NO");
		sa.assertEquals(loginPage.getSelectedAppTitle(), initialTitle, "Header Title", "Validation (NO)");

		// 4. Click MM again and click YES - Title changes
		loginPage.click_allAppsdropdown();
		Thread.sleep(500);
		loginPage.clickAppByTitle("MM");
		Thread.sleep(500);
		loginPage.click_confirmYes();
		loginPage.waitForLoading();
		ScreenshotUtil.capture();

		String newTitle = loginPage.getSelectedAppTitle();
		log.info("App after switcher confirmation: {}", newTitle);
		sa.assertTrue(newTitle.equalsIgnoreCase("MM") || newTitle.contains("Management"), "Header Title",
				"Validation (YES)");

		log.info("Switcher confirmation validation completed.");
		sa.assertAll();
	}

	// ====================User Profile Page====================
	@Test(groups = { "profile" }, priority = 15)
	public void Profile() throws Throwable {
		ScreenshotUtil.nextStep();
		log.info("--- Navigating to User Profile Page ---");
		loginPage.click_profileDropdown();
		ScreenshotUtil.capture();
		loginPage.profile();
		loginPage.waitForLoading();
		ScreenshotUtil.capture();

	}

	// ====================Validation of User Profile Page and its
	// fields====================
	@Test(groups = { "profilevalidation" }, priority = 16)
	public void Profile_Validation() throws Throwable {
		log.info("--- Validating User Profile Page ---");

		// 1. Navigate to Profile
		loginPage.profile();

		// 2. Validate Breadcrumbs
		List<String> breadcrumbs = loginPage.getBreadcrumbsText();
		log.info("Profile Breadcrumbs: {}", breadcrumbs);
		sa.assertTrue(breadcrumbs.contains("Home"), "Breadcrumb Home", "Presence");
		sa.assertTrue(breadcrumbs.contains("Profile"), "Breadcrumb Profile", "Presence");

		// 3. Prepare Expected Data
		String username = loginPage.currentUsername;
		boolean isAdmin = "admin".equalsIgnoreCase(username);

		String expectedEmployeeName;
		if (isAdmin) {
			expectedEmployeeName = "Admin"; // Based on observed behavior for admin
		} else {
			// Query first_name from sys_user table
			String sql = "SELECT first_name FROM sys_user WHERE login = ?";
			expectedEmployeeName = utilities.DatabaseBackupUtil.getSingleValueFromDB(sql, username);
		}

		log.info("Validating Profile Fields for User: {} (IsAdmin: {})", username, isAdmin);
		log.info("Expected Employee Name: {}", expectedEmployeeName);

		// 4. Validate Placeholders
		sa.assertEquals(loginPage.getProfileFieldPlaceholder("userName"), "Username", "Placeholder", "userName");
		sa.assertEquals(loginPage.getProfileFieldPlaceholder("employeeName"), "Employee Name", "Placeholder",
				"employeeName");
		sa.assertEquals(loginPage.getProfileFieldPlaceholder("employeeId"), "Employee ID", "Placeholder", "employeeId");
		sa.assertEquals(loginPage.getProfileFieldPlaceholder("department"), "Department", "Placeholder", "department");
		sa.assertEquals(loginPage.getProfileFieldPlaceholder("userRole"), "Security Group Name(s)", "Placeholder",
				"securityGroup");
		sa.assertEquals(loginPage.getProfileFieldPlaceholder("email"), "Email", "Placeholder", "email");
		sa.assertEquals(loginPage.getProfileFieldPlaceholder("mobileNumber"), "Mobile Number", "Placeholder",
				"mobileNumber");

		// Password Placeholders
		sa.assertEquals(loginPage.getProfileFieldPlaceholder("currentPassword"), "Old Password", "Placeholder",
				"oldPassword");
		sa.assertEquals(loginPage.getProfileFieldPlaceholder("newPassword"), "New Password", "Placeholder",
				"newPassword");
		sa.assertEquals(loginPage.getProfileFieldPlaceholder("confirmNewPassword"), "Confirm New Password",
				"Placeholder", "confirmPassword");

		// 5. Validate Field Values
		sa.assertEquals(loginPage.getProfileFieldValue("userName"), username, "Profile Value", "userName");
		sa.assertEquals(loginPage.getProfileFieldValue("employeeName"), expectedEmployeeName, "Profile Value",
				"employeeName");

		// For admin, these are likely "NA" as per screenshot
		if (isAdmin) {
			sa.assertEquals(loginPage.getProfileFieldValue("employeeId"), "admin", "Profile Value", "employeeId");
			sa.assertEquals(loginPage.getProfileFieldValue("department"), "NA", "Profile Value", "department");
			sa.assertEquals(loginPage.getProfileFieldValue("userRole"), "NA", "Profile Value", "securityGroup");
			sa.assertEquals(loginPage.getPrivilegesValue(), "NA", "Profile Value", "privileges");
		} else {
			// For non-admin, we'd need more DB queries. For now, checking if not empty.
			sa.assertFalse(loginPage.getProfileFieldValue("employeeId").isEmpty(), "Profile Value", "employeeId");
			sa.assertFalse(loginPage.getPrivilegesValue().isEmpty(), "Profile Value", "privileges");
		}

		log.info("Profile validation completed.");
		sa.assertAll();
	}

	// =============Logout===========================
	@Test(groups = { "Logout" }, dependsOnMethods = "appsSelect", priority = 17)
	public void logout() throws Throwable {
		log.info("--- Executing Final Logout ---");
		ScreenshotUtil.nextStep();
		loginPage.click_profileDropdown();
		ScreenshotUtil.capture();
		loginPage.logout();
		log.info("Clicked logout button");
		loginPage.waitForToast();
		ScreenshotUtil.capture();
		log.info("--- Department Test Case Execution Finished ---");
	}

	// ====================Validation of Logout Toaster Message====================

	@Test(groups = { "logoutvalidation" }, priority = 18)
	public void logout_Validation() throws Throwable {
		log.info("--- Validating Logout Toaster Message ---");

		loginPage.click_profileDropdown();
		loginPage.logout();
		String toast = loginPage.waitForToast();

		log.info("Logout Toast Message captured: {}", toast);
		sa.assertTrue(toast.toLowerCase().contains("logout") || toast.toLowerCase().contains("successfully"),
				"Toast Message", "Logout Validation");

		sa.assertAll();
	}

	@Test(groups = { "DB Back" }, priority = 19)
	public void Database_Backup() {
		log.info("--- Initiating Post-Test Database Backup ---");

		// Fallback logic: Use config filename if script number is missing
		String backupFolderName = (SCRIPT_NUMBER == null || SCRIPT_NUMBER.trim().isEmpty()) ? CURRENT_CONFIG_NAME
				: SCRIPT_NUMBER;

		log.info("Backup folder name determined: {}", backupFolderName);

		// Parameters: folderName, dbName, dbUser, dbPass, host, port
		DatabaseBackupUtil.backupPostgres(backupFolderName, PC_DB_NAME, "postgres", "root", "localhost", "5432");
		DatabaseBackupUtil.backupPostgres(backupFolderName, MASTER_DB_NAME, "postgres", "root", "localhost", "5432");
		DatabaseBackupUtil.backupPostgres(backupFolderName, MM_DB_NAME, "postgres", "root", "localhost", "5432");
	}

}
