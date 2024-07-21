package services;

import models.Department;
import services.interfaces.Service;
import utils.CsvDepartmentConverter;
import utils.Reader;
import utils.Writer;

import java.util.ArrayList;
import java.util.List;

/**
 * Service class for managing Department entities.
 * Handles operations such as adding, editing, removing,
 * retrieving, and listing departments.
 */
public class DepartmentService implements Service<Department> {
    private final Reader reader;
    private final Writer writer;
    private final static String departmentsCSVFilename = "departments.csv";

    /**
     * Constructs a DepartmentService with the specified Reader and Writer.
     *
     * @param reader the Reader for reading data
     * @param writer the Writer for writing data
     */
    public DepartmentService(Reader reader, Writer writer) {
        this.reader = reader;
        this.writer = writer;
    }

    /**
     * Adds a new Department entity.
     * This method is currently not implemented.
     *
     * @param args the arguments for creating the department
     * @return null as the method is not implemented
     * @throws RuntimeException if the department creation fails
     */
    @Override
    public Department addEntity(String... args) throws RuntimeException {
        // Can be implemented as extension of the application
        return null;
    }

    /**
     * Edits an existing Department entity.
     * This method is currently not implemented.
     *
     * @param id the ID of the department to edit
     * @param args the arguments for editing the department
     * @return null as the method is not implemented
     * @throws RuntimeException if the department update fails
     */
    @Override
    public Department editEntity(int id, String... args) throws RuntimeException {
        // Can be implemented as extension of the application
        return null;
    }

    /**
     * Removes a Department entity by ID.
     * This method is currently not implemented.
     *
     * @param id the ID of the department to remove
     * @return false as the method is not implemented
     */
    @Override
    public boolean removeEntity(int id) {
        // Can be implemented as extension of the application
        return false;
    }

    /**
     * Retrieves a Department entity by ID.
     * This method is currently not implemented.
     *
     * @param id the ID of the department to retrieve
     * @return null as the method is not implemented
     * @throws RuntimeException if the department is not found
     */
    @Override
    public Department getEntity(int id) throws RuntimeException {
        // Can be implemented as extension of the application
        return null;
    }

    /**
     * Lists all Department entities.
     *
     * @return a list of all departments, or an empty list if an error occurs
     */
    @Override
    public List<Department> listAllEntities() {
        CsvDepartmentConverter departmentConverter = new CsvDepartmentConverter();

        try {
            return departmentConverter.fromListOfMapsToListOfModel(
                    this.reader.read(DepartmentService.departmentsCSVFilename, Department.class));
        } catch (Exception ex) {
            return new ArrayList<>();
        }
    }
}