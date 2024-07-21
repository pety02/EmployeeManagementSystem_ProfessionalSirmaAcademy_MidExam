package managers;

import managers.interfaces.Manager;
import models.Department;
import services.DepartmentService;

import java.util.List;

/**
 * This class is responsible for managing Department entities.
 * It provides methods to add, edit, remove, retrieve, and list
 * departments.
 */
public class DepartmentManager implements Manager<Department> {
    private final DepartmentService departmentService;

    /**
     * Constructs a DepartmentManager with the specified DepartmentService.
     *
     * @param departmentService the DepartmentService to use for managing Department entities
     */
    public DepartmentManager(DepartmentService departmentService) {
        this.departmentService = departmentService;
    }

    /**
     * Adds a new Department entity using the provided arguments.
     * Currently, this method always returns false as the functionality
     * is not implemented.
     *
     * @param args the arguments required to create a new Department entity
     * @return false as the functionality is not implemented
     */
    @Override
    public boolean addEntity(String... args) {
        return false;
    }

    /**
     * Edits an existing Department entity with the specified ID using the provided arguments.
     * Currently, this method always returns false as the functionality is not implemented.
     *
     * @param id the ID of the Department entity to edit
     * @param args the arguments to update the Department entity with
     * @return false as the functionality is not implemented
     */
    @Override
    public boolean editEntity(int id, String... args) {
        return false;
    }

    /**
     * Removes the Department entity with the specified ID.
     * Currently, this method always returns false as the
     * functionality is not implemented.
     *
     * @param id the ID of the Department entity to remove
     * @return false as the functionality is not implemented
     */
    @Override
    public boolean removeEntity(int id) {
        return false;
    }

    /**
     * Retrieves the Department entity with the specified ID.
     * Currently, this method always returns null as the
     * functionality is not implemented.
     *
     * @param id the ID of the Department entity to retrieve
     * @return null as the functionality is not implemented
     */
    @Override
    public Department getEntity(int id) {
        return null;
    }

    /**
     * Lists all Department entities.
     *
     * @return a list of all Department entities
     */
    @Override
    public List<Department> listAllEntities() {
        return departmentService.listAllEntities();
    }
}