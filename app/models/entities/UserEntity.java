package models.entities;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.List;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import org.bson.types.ObjectId;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.WriteResult;

import models.User;

import models.db.Mongodb2;

import models.utils.Utils;

public class UserEntity {

	protected ObjectId id = null;
	protected String first_name = "";
	protected String email = "";
	protected String password = "";
	protected RoleType role = null;
	protected Date date_registered = null;

	protected static List<User> users = null;
	public static final String _collection_name = "user";
	private boolean _exists = false;
	private boolean _dirty = false;
	private Set<String> _dirty_attributes = new HashSet<String>();
	private boolean _atomic = true;

	public static enum RoleType {
		GUEST,
		ADMIN;

		public static final RoleType[] value = RoleType.values();
	}

	protected UserEntity() { }

	protected UserEntity(ObjectId id) {
		this.id = id;
		this.findById();
	}

	public void findById() {
		Mongodb2 _db = new Mongodb2();

		DBCursor _cursor = _db.getCollection(UserEntity._collection_name).find(new BasicDBObject("_id",this.id));
		try {
			if(_cursor.hasNext()) {
				this.instantiate(_cursor);
			}
		} catch(Exception e) {

		} finally {
			_cursor.close();
		}
	}

	public void instantiate(DBCursor cursor) {
		if(cursor != null && cursor.hasNext()) {
				BasicDBObject _doc = (BasicDBObject)cursor.next();
				this.id = _doc.getObjectId("_id");
				this.first_name = _doc.getString("first_name");
				this.email = _doc.getString("email");
				this.password = _doc.getString("password");
				if(_doc.get("role") == null) {
					this.role = null;
				} else {
					this.role = RoleType.value[_doc.getInt("role")];
				}
				this.date_registered = _doc.getDate("date_registered");

				this._exists = true;
		}
	}

	public boolean insert() {
		boolean _success = false;

		Mongodb2 _db = new Mongodb2();
		DBCollection _col = _db.getCollection(UserEntity._collection_name);

		_col.createIndex(new BasicDBObject().append("email",1), new BasicDBObject("unique",true));

		BasicDBObject _doc = new BasicDBObject();
		_doc.append("first_name", this.first_name);
		_doc.append("email", this.email);
		_doc.append("password", this.password);
		if(this.role == null) {
			_doc.append("role", this.role);
		} else {
			_doc.append("role", this.role.ordinal());
		}
		_doc.append("date_registered", this.date_registered);

		try {
			WriteResult _res = _db.getCollection(UserEntity._collection_name).insert(_doc);

			if(_doc.get("_id") != null) {
				this.id = (ObjectId)_doc.get("_id");

				this._dirty_attributes.clear();
				this._dirty = false;
				this._exists = true;
				_success = true;
			}
		} catch(Exception e) {
			_success = false;
		}

		if(_success && UserEntity.users != null) {
			//TODO: Implement adding this Campaign object
		}

		return _success;
	}

	public boolean update() {
		boolean _success = false;

		if(this._exists && this._dirty) {
			Mongodb2 _db = new Mongodb2();
			DBCollection _col = _db.getCollection(User._collection_name);
			BasicDBObject _params = new BasicDBObject();

			BasicDBObject _set = new BasicDBObject();
			if(this._dirty_attributes.contains("first_name")) { _set.append("first_name",this.first_name); }
			if(this._dirty_attributes.contains("email")) { _set.append("email",this.email); }
			if(this._dirty_attributes.contains("password")) { _set.append("password",this.password); }
			if(this._dirty_attributes.contains("role")) {
				if(role == null) {
					_set.append("role",this.role);
				} else {
					_set.append("role",this.role.ordinal());
				}
			}

			if(this._dirty_attributes.contains("date_registered")) { _set.append("date_registered",this.date_registered); }

			if(_set.size() > 0) {
				_params.append("$set", _set);
			}

			WriteResult _res = _col.update(new BasicDBObject("_id",this.id), _params);

			if(_res.getLastError().ok()) {
				this._dirty_attributes.clear();
				this._dirty = false;
				_success = true;
			}
		}

		return _success;
	}

	public boolean delete() {
		boolean _success = false;

		Mongodb2 _db = new Mongodb2();
		DBCollection _col = _db.getCollection(UserEntity._collection_name);
		WriteResult _res = _col.remove(new BasicDBObject("_id",this.id));
		if(_res.getLastError().ok()) {
			_success = true;
		}

		return _success;
	}

	public static boolean bulkInsert(List<User> users) {
		boolean _success = false;

		if(users.size() > 0) {
			List<DBObject> _objs = new ArrayList<DBObject>(users.size());
			for(User user : users) {
				_objs.add(user.document());
			}

			Mongodb2 _db = new Mongodb2();
			DBCollection _col = _db.getCollection(UserEntity._collection_name);
			WriteResult _res = _col.insert(_objs);
			if(_res.getLastError().ok()) {
				_success = true;
			}
		}

		return _success;
	}

	public static boolean bulkInsert(User[] users) {
		boolean _success = false;

		if(users.length > 0) {
			DBObject[] _objs = new DBObject[users.length];
			for(int i=0;i<_objs.length;i++) {
				_objs[i] = users[i].document();
			}

			Mongodb2 _db = new Mongodb2();
			DBCollection _col = _db.getCollection(UserEntity._collection_name);
			WriteResult _res = _col.insert(_objs);
			if(_res.getLastError().ok()) {
				_success = true;
			}
		}

		return _success;
	}

	public static void drop() {
		Mongodb2 _db = new Mongodb2();
		_db.getCollection(User._collection_name).drop();
	}

	public static long count() {
		Mongodb2 _db = new Mongodb2();
		return _db.getCollection(User._collection_name).count();
	}

	public boolean exists() {
		return this._exists;
	}

	public void exists(boolean exists) {
		this._exists = exists;
	}

	public boolean atomic() {
		return this._atomic;
	}

	public void atomic(boolean _atomic) {
		this._atomic = _atomic;
	}

	public DBObject document() {
		BasicDBObject _obj = new BasicDBObject();
		_obj.append("first_name",this.first_name);
		_obj.append("email",this.email);
		_obj.append("password",this.password);
		if(role == null) {
			_obj.append("role",this.role);
		} else {
			_obj.append("role",this.role.ordinal());
		}

		_obj.append("date_registered",this.date_registered);

		return _obj;
	}

	public static User getUserByEmail(String email) {
		Mongodb2 _db = new Mongodb2();
		DBCollection _col = _db.getCollection(UserEntity._collection_name);

		User user = new User();
		BasicDBObject _obj = new BasicDBObject()
			.append("email",email);
		user.instantiate(_col.find(_obj));

		return user;
	}

	public static boolean emailExists(String email) {
		Mongodb2 _db = new Mongodb2();
		DBCollection _col = _db.getCollection(User._collection_name);

		BasicDBObject _obj = new BasicDBObject()
			.append("email",email);

		if(_col.find(_obj).count() > 0) {
			return true;
		} else {
			return false;
		}
	}

	public void setId(ObjectId id) {
		this.id = id;
	}

	public void setFirstName(String first_name) {
		if(first_name != null && first_name.length() >= 1 && first_name.length() <= 30) {
			this.first_name = first_name;
			this._dirty_attributes.add("first_name");
			this._dirty = true;

			if(!this._atomic) {
				this.update();
			}
		}
	}

	public void setEmail(String email) {
		if(email != null && email.length() >= 1 && email.length() <= 30) {
			this.email = email;
			this._dirty_attributes.add("email");
			this._dirty = true;

			if(!this._atomic) {
				this.update();
			}
		}
	}

	public void setPassword(String password) {
		MessageDigest md = null;
		if(password != null) {
			try {
				md = MessageDigest.getInstance("SHA-512");
				md.update(password.getBytes("UTF-8"));
			} catch (NoSuchAlgorithmException e) {
				e.printStackTrace();
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
			if(md != null) {
				this.password = Utils.toHexString(md.digest());
				this._dirty_attributes.add("password");
				this._dirty = true;
			}

			if(!this._atomic) {
				this.update();
			}
		}
	}

	public void setRole(RoleType role) {
		this.role = role;
		this._dirty_attributes.add("role");
		this._dirty = true;

		if(!this._atomic) {
			this.update();
		}
	}

	public void setDateRegistered(Date date_registered) {
		if(date_registered != null) {
			this.date_registered = date_registered;
			this._dirty_attributes.add("date_registered");
			this._dirty = true;

			if(!this._atomic) {
				this.update();
			}
		}
	}

	public ObjectId getId() {
		return this.id;
	}

	public String getFirstName() {
		return this.first_name;
	}

	public String getEmail() {
		return this.email;
	}

	public String getPassword() {
		return this.password;
	}

	public RoleType getRole() {
		return this.role;
	}

	public Date getDateRegistered() {
		return this.date_registered;
	}

	public static List<User> getUsers() {
		if(UserEntity.users == null) {
			Mongodb2 _db = new Mongodb2();
			DBCollection _col = _db.getCollection(UserEntity._collection_name);

			DBCursor _cursor = _col.find();
			UserEntity.users = new ArrayList<User>(_cursor.count());

			try {
				while(_cursor.hasNext()) {
					User user = new User();
					user.instantiate(_cursor);
					UserEntity.users.add(user);
				}
			} catch(Exception e) {

			} finally {
				_cursor.close();
			}
		}

		return UserEntity.users;
	}

	public static List<User> getUsers(int start, int limit) {
		Mongodb2 _db = new Mongodb2();
		DBCollection _col = _db.getCollection(UserEntity._collection_name);

		DBCursor _cursor = _col.find().skip(start).limit(limit);
		List<User> users = new ArrayList<User>(limit);

		try {
			while(_cursor.hasNext()) {
				User user = new User();
				user.instantiate(_cursor);
				users.add(user);
			}
		} catch(Exception e) {

		} finally {
			_cursor.close();
		}

		return users;
	}

}