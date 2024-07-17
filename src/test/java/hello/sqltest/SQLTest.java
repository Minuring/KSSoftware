package hello.sqltest;

import com.mysql.cj.jdbc.Driver;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;


public class SQLTest {

    @Test
    @DisplayName("DB 커넥션 테스트")
    void connectionTest() {
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(SQLTestConfig.class);
        DataSource dataSource = ac.getBean(DataSource.class);

        Connection connection;
        PreparedStatement preparedStatement;
        try {
            connection = dataSource.getConnection();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Test
    @DisplayName("INSERT 테스트")
    void insertToDB() {
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(SQLTestConfig.class);
        DataSource dataSource = ac.getBean(DataSource.class);

        Connection connection;
        PreparedStatement preparedStatement;
        try {
            connection = dataSource.getConnection();
            StringBuilder sql = new StringBuilder();

            sql.append("use KSSoftwareDB;");
            sql.append("insert into test values ('name', 1)");
//            sql.append("");

            preparedStatement = connection.prepareStatement(sql.toString());
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Configuration
    static class SQLTestConfig {
        @Bean
        public DataSource dataSource() {
            System.out.println("SQLTestConfig.dataSource START");
            SimpleDriverDataSource sdds = new SimpleDriverDataSource();
            sdds.setDriverClass(Driver.class);
            sdds.setUrl("jdbc:mysql://kssoftwaredb.clwscii8qmmj.ap-northeast-2.rds.amazonaws.com:3306");
            sdds.setUsername("admin");
            sdds.setPassword("adminadmin");
            System.out.println("SQLTestConfig.dataSource END");
            return sdds;
        }
    }
}
