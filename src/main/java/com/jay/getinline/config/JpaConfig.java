package com.jay.getinline.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

@EnableJpaAuditing
@Configuration
public class JpaConfig {

    // 트랜젝션 관련 중요 지식
    // 1. @Transactional : Propagation - 중첩된 트랜잭션의 동작 규칙
    //  - REQUIRED(default) : 현재 있으면 보조, 없으면 새로 만들기
    //  - SUPPORTS : 현재 있으면 보조, 없으면 트랜잭션 없이 실행
    //  - MANDATORY : 있으면 보조, 없으면 예외 처리
    //  - REQUIRES_NEW : 트랜잭션 생성하고 실행, 현재 있던 것은 미룸
    //  - NOT_SUPPORTED : 트랜잭션 없이 실행, 현재 있던 것은 미룸
    //  - NEVER : 트랜잭션 없이 실행, 현재 트랜잭션 있었으면 예외 처리
    //  - NESTED : 현재 있으면 그 안에서 중첩된 트랜잭션 형성 / 별개로 커밋 또는 롤백될 수 있

    // 2. @Transactional : Isolation - 내부 데이터의 격리 수준
    //  - DEFAULT : 데이터베이스에게 맡김
    //  - READ_UNCOMMITTED : dirty read + non-repeatable read + phantom read / 트랜잭션에 처리중인 혹은 아직 커밋되지 않은 데이터를 다른 트랜잭션이 읽는 것을 허용
    //  - READ_COMMITTED : non-repeatable read + phantom read / 트랜잭션이 커밋되어 확정된 데이터만을 읽는 것을 허용
    //  - REPEATABLE_READ : phantom read
    //      / 트랜잭션이 완료될 때까지 SELECT 문장이 사용하는 모든 데이터에 shared lock이 걸리므로 다른 사용자는 그 영역에 해당되는 데이터에 대한 수정이 불가
    //        , 선행 트랜잭션이 읽은 데이터는 트랜잭션이 종료될 때까지 후행 트랜잭션이 갱신하거나 삭제하는 것을 불허함으로써 같은 데이터를 두 번 쿼리했을 때 일관성 있는 결과를 리턴
    //  SERIALIZABLE : 완전 직렬 수행
    //  / 데이터의 일관성 및 동시성을 위해 MVCC(Multi Version Concurrency Control)을 사용하지 않음
    //    (MVCC는 다중 사용자 데이터베이스 성능을 위한 기술로 데이터 조회 시 LOCK을 사용하지 않고 데이터의 버전을 관리해 데이터의 일관성 및 동시성을 높이는 기술)


    /* ========= Datasource 및 Jpa Adaptor, TransactionManager 설정 =======================
    // .properties 에서 설정한 값으로 지정
    @ConfigurationProperties("spring.datasource")
    @Bean
    public DataSource dataSource() {

        // Datasource 직접 설정 (방법 1)
//        return DataSourceBuilder.create()
//                .driverClassName()
//                .type()
//                .url()
//                .username()
//                .password()
//                .build();

        return DataSourceBuilder.create().build();

    // Datasource 직접 설정 (방법 2)
//        EmbeddedDatabaseBuilder builder = new EmbeddedDatabaseBuilder();
//        return builder.setType(EmbeddedDatabaseType.H2).build();
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(*//*위에서 설정한 Bean 주입*//* DataSource dataSource) {
        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        vendorAdapter.setGenerateDdl(true);

        LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean();
        factory.setJpaVendorAdapter(vendorAdapter);
        factory.setPackagesToScan("com.jay.getinline");
        factory.setDataSource(dataSource);

        return factory;
    }

    @Bean
    public PlatformTransactionManager transactionManager(*//*위에서 설정한 Bean 주입*//* EntityManagerFactory entityManagerFactory) {
        JpaTransactionManager txManager = new JpaTransactionManager();
        txManager.setEntityManagerFactory(entityManagerFactory);

        return txManager;
    }*/

}