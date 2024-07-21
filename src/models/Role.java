package models;

/**
 * This enum represents various roles that an employee can
 * have within a company. It provides a method to get a Role
 * enum from a string and overrides the toString method to give
 * a formatted string representation of the role.
 */
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

    /**
     * Converts a string to a Role enum value.
     * The string can contain spaces and is case of insensitive.
     *
     * @param role the string representation of the role
     * @return the corresponding Role enum value, or null if no match is found
     */
    public static Role getRole(String role) {
        if (role == null) {
            return null;
        }
        role = role.replace(' ', '_');
        try {
            return Role.valueOf(role.toUpperCase());
        } catch (IllegalArgumentException e) {
            return null;
        }
    }

    /**
     * Converts the enum name to a more readable format.
     *
     * @return a formatted string representation of the role
     */
    @Override
    public String toString() {
        String name = this.name();
        return name.charAt(0) + name.substring(1).toLowerCase().replace('_', ' ');
    }
}