package com.britesnow.contactsdemo.dao;
import com.britesnow.contactsdemo.entity.User;
import org.j8ql.query.Condition;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface IDao<E,I> {

	public enum SortOrder {
		ASC, DESC
	}

	public Optional<E> get(User user, I id);

	/**
	 * Create the entity and returns its id.
	 * @return entity id
	 */
	public I create(User user, E newEntity);

	public I create(User user, Map entityMap);

	public int update(User user, E entity, I id);

	public int update(User user, Map entity, I id);

	public int delete(User user, I id);

	public List<E> list(User user,Condition filter, int pageIdx, int pageSize, String... orderBy);

	public Long count(Condition filter);

	public Class<E> getEntityClass();

	public Class<I> getIdClass();

}