package com.example.est_bootcamp.test;

import com.example.est_bootcamp.common.Role;
import com.example.est_bootcamp.emp.Employee;
import com.example.est_bootcamp.org.Department;
import com.example.est_bootcamp.org.Position;
import com.example.est_bootcamp.repo.DepartmentRepository;
import com.example.est_bootcamp.repo.EmployeeRepository;
import com.example.est_bootcamp.repo.PositionRepository;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import java.time.LocalDate;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class EmployeeRepositoryTest {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private DepartmentRepository departmentRepository;

    @Autowired
    private PositionRepository positionRepository;

    private static Long savedEmpId;
    private static Department savedDept;
    private static Position savedPos;

    @BeforeAll
    static void setup(
            @Autowired DepartmentRepository departmentRepository,
            @Autowired PositionRepository positionRepository,
            @Autowired EmployeeRepository employeeRepository
    ) {
        // 공통 부서 저장
        savedDept = departmentRepository.save(
                Department.builder()
                        .name("총무팀")
                        .code("D-INIT")
                        .useYn("Y")
                        .build()
        );

        // 공통 직급 저장
        savedPos = positionRepository.save(
                Position.builder()
                        .name("사원")
                        .code("P-INIT")
                        .useYn("Y")
                        .build()
        );

        // 최초 직원 등록 (다른 테스트들이 사용할 대상)
        Employee emp = Employee.builder()
                .empName("홍길동")
                .birthDate(LocalDate.of(1990, 5, 1))
                .gender("M")
                .email("hong@test.com")
                .phoneNo("010-1234-5678")
                .hireDate(LocalDate.now())
                .department(savedDept)
                .position(savedPos)
                .useYn("Y")
                .role(Role.STAFF)
                .build();

        Employee saved = employeeRepository.save(emp);
        savedEmpId = saved.getEmpId();

        System.out.println("초기 생성된 직원 ID = " + savedEmpId);
    }

    @Test
    @Order(2)
    @DisplayName("2. 직원 조회 (Read)")
    void testReadEmployee() {
        Optional<Employee> empOpt = employeeRepository.findById(savedEmpId);

        assertThat(empOpt).isPresent();
        assertThat(empOpt.get().getEmail()).isEqualTo("hong@test.com");
    }

    @Test
    @Order(3)
    @DisplayName("3. 직원 수정 (Update)")
    void testUpdateEmployee() {
        Employee emp = employeeRepository.findById(savedEmpId).orElseThrow();
        emp.setEmpName("홍길동-수정");

        Employee updated = employeeRepository.save(emp);
        assertThat(updated.getEmpName()).isEqualTo("홍길동-수정");
    }

    @Test
    @Order(4)
    @DisplayName("4. 직원 삭제 (Delete)")
    void testDeleteEmployee() {
        employeeRepository.deleteById(savedEmpId);
        Optional<Employee> empOpt = employeeRepository.findById(savedEmpId);

        assertThat(empOpt).isEmpty();
    }
}
