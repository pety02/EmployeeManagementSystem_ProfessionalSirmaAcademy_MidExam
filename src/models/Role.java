package models;

public enum Role {
    SOFTWARE_DEVELOPER,
    FRONT_END_DEVELOPER,
    BACK_END_DEVELOPER,
    FULL_STACK_DEVELOPER,
    MOBILE_DEVELOPER,
    DEVOPS_ENGINEER,
    DATA_SCIENTIST,
    DATA_ENGINEER,
    DATA_ANALYST,
    DATABASE_ADMINISTRATOR,
    QA_ENGINEER,
    AUTOMATION_TESTER,
    MANUAL_TESTER,
    PROJECT_MANAGER,
    SCRUM_MASTER,
    PRODUCT_MANAGER,
    IT_SUPPORT_SPECIALIST,
    HELP_DESK_TECHNICIAN,
    SYSTEM_ADMINISTRATOR,
    NETWORK_ENGINEER,
    SECURITY_ANALYST,
    CYBERSECURITY_SPECIALIST,
    UX_DESIGNER,
    UI_DESIGNER,
    UX_RESEARCHER,
    CIO,
    CTO,
    IT_DIRECTOR,
    CLOUD_ENGINEER,
    AI_ML_ENGINEER,
    BLOCKCHAIN_DEVELOPER;

    public static Role getRole(String role) {
        if (role == null) {
            return null;
        }
        role = role.replace(' ', '_');
        try {
            return Role.valueOf(role.toUpperCase());
        } catch (IllegalArgumentException e) {
            // Return null or throw an exception if the role is not found
            return null;  // Or throw new IllegalArgumentException("No enum constant for role: " + role);
        }
    }

    @Override
    public String toString() {
        // Converts the enum name to a more readable format
        String name = this.name();
        return name.charAt(0) + name.substring(1).toLowerCase().replace('_', ' ');
    }
}