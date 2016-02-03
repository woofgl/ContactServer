package com.britesnow.contactsdemo.entity;

import java.util.Optional;

/**
 * <p>Based class for all entity class.</p>
 *
 * <p>Note: We mark this class abstract since it would not make sense to create an instance of it, and
 * it is used to not create a corresponding IDao (Matcher in the AppConfig to ignore Abstract entity classes).</p>
 *
 * @param <I>
 */
public abstract class BaseEntity<I>{
	private I   id;
	public I getId() {
				return id;
		}
	public void setId(I id) {
		this.id = id;
	}


	// --------- Id accessor conveniences --------- //
	// to make getOrCreate simpler to write
	public I setIdAndReturnId(I id) {
		setId(id);
		return id;
	}
	public Optional<I> getOptionalId() {
		return Optional.ofNullable(id);
	}
	// --------- /Id accessor conveniences --------- //



}
