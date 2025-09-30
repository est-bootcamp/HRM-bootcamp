package com.example.est_bootcamp.test;

import com.example.est_bootcamp.emp.Employee;
import com.example.est_bootcamp.org.Department;
import com.example.est_bootcamp.org.Position;
import com.example.est_bootcamp.repo.DepartmentMapper;
import com.example.est_bootcamp.repo.EmployeeMapper;
import com.example.est_bootcamp.repo.PositionMapper;
import org.junit.jupiter.api.*;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;

import java.time.LocalDate;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@MybatisTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE) // 실제 DB 사용
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class EmployeeMapperTest {

    @Autowired
    private EmployeeMapper employeeMapper;

    @Autowired
    private DepartmentMapper departmentMapper;

    @Autowired
    private PositionMapper positionMapper;

    private static Long savedEmpId;
    private static Department savedDept;
    private static Position savedPos;

    @BeforeAll
    static void setup(@Autowired DepartmentMapper departmentMapper,
                      @Autowired PositionMapper positionMapper,
                      @Autowired EmployeeMapper employeeMapper) {

        // 공통 부서 저장
        savedDept = Department.builder()
                .dprName("총무팀")
                .dprCode("D-INIT")
                .useYn("Y")
                .build();
        departmentMapper.insert(savedDept);

        // 공통 직급 저장
        savedPos = Position.builder()
                .pstName("사원")
                .pstCode("P-INIT")
                .useYn("Y")
                .build();
        positionMapper.insert(savedPos);

        // 최초 직원 등록
        Employee emp = Employee.builder()
                .name("홍길동")
                .email("hong@test.com")
                .phone("010-1234-5678")
                .hireDate(LocalDate.now())
                .gender("M")
                .dprId(savedDept.getDprId())
                .pstId(savedPos.getPstId())
                .status("재직중")
                .useYn("Y")
                .build();

        employeeMapper.insert(emp);
        savedEmpId = emp.getEmpId();

        System.out.println("✅ 초기 생성된 직원 ID = " + savedEmpId);
    }

    @Test
    @Order(2)
    @DisplayName("2. 직원 조회 (Read)")
    void testReadEmployee() {
        Optional<Employee> empOpt = employeeMapper.findById(savedEmpId);

        assertThat(empOpt).isPresent();
        assertThat(empOpt.get().getEmail()).isEqualTo("hong@test.com");
    }

    @Test
    @Order(3)
    @DisplayName("3. 직원 수정 (Update)")
    void testUpdateEmployee() {
        Employee emp = employeeMapper.findById(savedEmpId).orElseThrow();
        emp.setName("홍길동-수정");

        employeeMapper.update(emp);

        Employee updated = employeeMapper.findById(savedEmpId).orElseThrow();
        assertThat(updated.getName()).isEqualTo("홍길동-수정");
    }

    @Test
    @Order(4)
    @DisplayName("4. 직원 삭제 (Delete)")
    void testDeleteEmployee() {
        Employee emp = new Employee();
        emp.setEmpId(savedEmpId);

        employeeMapper.delete(emp);

        Optional<Employee> empOpt = employeeMapper.findById(savedEmpId);
        assertThat(empOpt).isEmpty();
    }
}
