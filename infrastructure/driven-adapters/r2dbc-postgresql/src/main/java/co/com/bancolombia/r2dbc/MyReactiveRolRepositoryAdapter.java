package co.com.bancolombia.r2dbc;

import co.com.bancolombia.model.persona.Persona;
import co.com.bancolombia.model.persona.gateways.PersonaRepository;
import co.com.bancolombia.model.rol.Rol;
import co.com.bancolombia.model.rol.gateways.RolRepository;
import co.com.bancolombia.r2dbc.entity.PersonaEntity;
import co.com.bancolombia.r2dbc.entity.RolEntity;
import co.com.bancolombia.r2dbc.helper.ReactiveAdapterOperations;
import org.reactivecommons.utils.ObjectMapper;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public class MyReactiveRolRepositoryAdapter extends ReactiveAdapterOperations<
        Rol/* change for domain model */,
        RolEntity/* change for adapter model */,
        Integer,
        MyReactiveRolRepository
> implements RolRepository {
    public MyReactiveRolRepositoryAdapter(MyReactiveRolRepository repository, ObjectMapper mapper) {
        /**
         *  Could be use mapper.mapBuilder if your domain model implement builder pattern
         *  super(repository, mapper, d -> mapper.mapBuilder(d,ObjectModel.ObjectModelBuilder.class).build());
         *  Or using mapper.map with the class of the object model
         */
        super(repository, mapper, d -> mapper.map(d, Rol.class));
    }
}
