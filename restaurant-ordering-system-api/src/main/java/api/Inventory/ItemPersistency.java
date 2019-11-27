package api.Inventory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

@Repository
public class ItemPersistency{

	@Autowired
	private JdbcOperations jdbc;
	
	private static final String SQL_INSERT = "insert into MenuItem (name, description, price, availability) values (?, ?, ?, ?)";
	private static final String SQL_UPDATE = "update MenuItem set name=?, description=?, price=?, availability=? where id=?";
	private static final String SQL_FIND_ONE = "select * from MenuItem where id = ?";
	private static final String SQL_FIND_ALL = "select * from MenuItem order by name";
	private static final String SQL_DELETE_ONE = "delete from MenuItem where id = ?";

	
	
	public Item findOne(long id) {
		return jdbc.queryForObject(SQL_FIND_ONE, new CustomerRowMapper(), id);
	}

	
	public Item save(final Item cust) {
		
		KeyHolder holder = new GeneratedKeyHolder();
		
		int rows = jdbc.update(new PreparedStatementCreator() {
			
			
			public PreparedStatement createPreparedStatement(Connection conn) throws SQLException {
				PreparedStatement ps = conn.prepareStatement(SQL_INSERT, new String[]{"id"});
				
				ps.setString(1,  cust.getName());
				ps.setString(2, cust.getDescription());
				ps.setDouble(3, cust.getPrice());
				ps.setBoolean(4, cust.isAvailable());
				
				return ps;
			}
		}, holder);
		
		if(rows == 1) {	// success, so apply ID to the customer object
			cust.setId((int)holder.getKey());
			return cust;
		}
		
		return null;
		
	}

	
	public List<Item> findAll() {
		return jdbc.query(SQL_FIND_ALL, new CustomerRowMapper());
	}

	
	public int update(Item cust) {
		return jdbc.update(SQL_UPDATE, cust.getName(), cust.getDescription(), cust.getPrice(), cust.isAvailable(),cust.getId());
	}

	
	public int delete(Item cust) {
		return jdbc.update(SQL_DELETE_ONE, cust.getId());
	}

	private class CustomerRowMapper implements RowMapper<Item> {

		
		public Item mapRow(ResultSet rs, int row) throws SQLException {
			return new Item(rs.getInt("id"), rs.getString("name"), rs.getString("description"), rs.getDouble("price"), rs.getBoolean("availability")); 
			
		}
		
	}
}
