package alararestaurant.service;

import alararestaurant.common.Constants;
import alararestaurant.domain.dtos.EmployeeImportDto;
import alararestaurant.domain.entities.Employee;
import alararestaurant.domain.entities.Position;
import alararestaurant.repository.EmployeeRepository;
import alararestaurant.repository.PositionRepository;
import alararestaurant.util.FileUtil;
import alararestaurant.util.ValidationUtil;
import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class EmployeeServiceImpl implements EmployeeService {
    private static final String FILES_EMPLOYEES_JSON = System.getProperty("user.dir") + "/src/main/resources/files/employees.json";

    private final EmployeeRepository employeeRepository;
    private final PositionRepository positionRepository;
    private final FileUtil fileUtil;
    private final Gson gson;
    private final ValidationUtil validationUtil;
    private final ModelMapper mapper;

    @Autowired
    public EmployeeServiceImpl(EmployeeRepository employeeRepository, PositionRepository positionRepository, FileUtil fileUtil, Gson gson, ValidationUtil validationUtil, ModelMapper mapper) {
        this.employeeRepository = employeeRepository;
        this.positionRepository = positionRepository;
        this.fileUtil = fileUtil;
        this.gson = gson;
        this.validationUtil = validationUtil;
        this.mapper = mapper;
    }

    @Override
    public Boolean employeesAreImported() {
        return this.employeeRepository.count() > 0;
    }

    @Override
    public String readEmployeesJsonFile() throws IOException {
        return this.fileUtil.readFile(FILES_EMPLOYEES_JSON);
    }

    @Override
    public String importEmployees(String employees) {
        StringBuilder importResult = new StringBuilder();

        EmployeeImportDto[] employeeImportDtos = this.gson
                .fromJson(employees, EmployeeImportDto[].class);

        for (EmployeeImportDto employeeImportDto : employeeImportDtos) {
            Position position = this.positionRepository.findByName(employeeImportDto.getPosition()).orElse(null);

            if (!this.validationUtil.isValid(employeeImportDto)){
                importResult.append(Constants.INVALID_DATA_FORMAT).append(System.lineSeparator());

                continue;
            }

            if (position == null){
                position = new Position();
                position.setName(employeeImportDto.getPosition());
                this.positionRepository.saveAndFlush(position);
            }

            Employee employee = this.mapper.map(employeeImportDto, Employee.class);
            employee.setPosition(position);
            this.employeeRepository.saveAndFlush(employee);

            importResult.append(String.format(Constants.SUCCESSFULLY_IMPORTED_JSON,
                    employee.getName())).append(System.lineSeparator());

        }

        return importResult.toString().trim();
    }
}