package ${basePackage};

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.NoRepositoryBean;

import java.io.Serializable;

/**
 * @author  ${author}
 */
@NoRepositoryBean
public interface BaseDao<T> extends JpaRepository<T, Serializable>, JpaSpecificationExecutor<T> {

}