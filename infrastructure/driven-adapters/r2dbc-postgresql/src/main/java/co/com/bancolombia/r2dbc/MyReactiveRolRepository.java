package co.com.bancolombia.r2dbc;

import co.com.bancolombia.r2dbc.entity.RolEntity;
import org.springframework.data.repository.query.ReactiveQueryByExampleExecutor;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

// TODO: This file is just an example, you should delete or modify it
public interface MyReactiveRolRepository extends ReactiveCrudRepository<RolEntity, Integer>, ReactiveQueryByExampleExecutor<RolEntity> {

}
