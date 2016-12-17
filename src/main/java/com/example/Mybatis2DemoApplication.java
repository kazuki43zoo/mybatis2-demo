package com.example;

import com.example.dao.TodoDao;
import com.example.domain.Todo;
import com.ibatis.sqlmap.client.SqlMapClient;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.orm.ibatis.SqlMapClientFactoryBean;
import org.springframework.orm.ibatis.SqlMapClientOperations;
import org.springframework.orm.ibatis.SqlMapClientTemplate;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;
import java.io.IOException;

@SpringBootApplication
public class Mybatis2DemoApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(Mybatis2DemoApplication.class, args);
    }

    private final TodoDao todoDao;

    Mybatis2DemoApplication(TodoDao todoDao) {
        this.todoDao = todoDao;
    }

    @Transactional
    @Override
    public void run(String... args) throws Exception {
        Todo newTodo = new Todo();
        newTodo.setTitle("飲み会");
        newTodo.setDetails("銀座 19:00");

        todoDao.insert(newTodo); // 新しいTodoをインサートする

        Todo loadedTodo = todoDao.select(newTodo.getId()); // インサートしたTodoを取得して標準出力する
        System.out.println("ID       : " + loadedTodo.getId());
        System.out.println("TITLE    : " + loadedTodo.getTitle());
        System.out.println("DETAILS  : " + loadedTodo.getDetails());
        System.out.println("FINISHED : " + loadedTodo.isFinished());
    }


    @Configuration
    @SuppressWarnings("deprecation")
    static class MyBatisConfig {

        private final ResourcePatternResolver resourcePatternResolver;

        MyBatisConfig(ResourcePatternResolver resourcePatternResolver) {
            this.resourcePatternResolver = resourcePatternResolver;
        }

        @Bean
        SqlMapClientFactoryBean sqlMapClient(DataSource dataSource) throws IOException {
            SqlMapClientFactoryBean factoryBean = new SqlMapClientFactoryBean();
            factoryBean.setConfigLocations(resourcePatternResolver.getResources("classpath*:/mybatis/sqlMapConfig.xml"));
            factoryBean.setMappingLocations(resourcePatternResolver.getResources("classpath*:/mybatis/**/*-sqlmap.xml"));
            factoryBean.setDataSource(dataSource);
            return factoryBean;
        }

        @Bean
        SqlMapClientOperations sqlMapClientOperations(SqlMapClient sqlMapClient) {
            return new SqlMapClientTemplate(sqlMapClient);
        }

    }

}
