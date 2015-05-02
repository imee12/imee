package models;

import org.bson.types.ObjectId;

import models.entities.UserEntity;

public class User extends UserEntity {

	public User() {
		super();
	}

	public User(ObjectId id) {
		super(id);
	}

}