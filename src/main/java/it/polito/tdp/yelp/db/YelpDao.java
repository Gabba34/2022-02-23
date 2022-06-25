package it.polito.tdp.yelp.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import it.polito.tdp.yelp.model.Business;
import it.polito.tdp.yelp.model.Review;
import it.polito.tdp.yelp.model.User;

public class YelpDao {
	
	
	public List<Business> getAllBusiness(){
		String sql = "SELECT * FROM Business";
		List<Business> result = new ArrayList<Business>();
		Connection conn = DBConnect.getConnection();

		try {
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();
			while (res.next()) {

				Business business = new Business(res.getString("business_id"), 
						res.getString("full_address"),
						res.getString("active"),
						res.getString("categories"),
						res.getString("city"),
						res.getInt("review_count"),
						res.getString("business_name"),
						res.getString("neighborhoods"),
						res.getDouble("latitude"),
						res.getDouble("longitude"),
						res.getString("state"),
						res.getDouble("stars"));
				result.add(business);
			}
			res.close();
			st.close();
			conn.close();
			return result;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public List<Review> getAllReviews(){
		String sql = "SELECT * FROM Reviews";
		List<Review> result = new ArrayList<Review>();
		Connection conn = DBConnect.getConnection();

		try {
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();
			while (res.next()) {

				Review review = new Review(res.getString("review_id"), 
						res.getString("business_id"),
						res.getString("user_id"),
						res.getDouble("stars"),
						res.getDate("review_date").toLocalDate(),
						res.getInt("votes_funny"),
						res.getInt("votes_useful"),
						res.getInt("votes_cool"),
						res.getString("review_text"));
				result.add(review);
			}
			res.close();
			st.close();
			conn.close();
			return result;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public List<User> getAllUsers(){
		String sql = "SELECT * FROM Users";
		List<User> result = new ArrayList<User>();
		Connection conn = DBConnect.getConnection();

		try {
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();
			while (res.next()) {

				User user = new User(res.getString("user_id"),
						res.getInt("votes_funny"),
						res.getInt("votes_useful"),
						res.getInt("votes_cool"),
						res.getString("name"),
						res.getDouble("average_stars"),
						res.getInt("review_count"));
				
				result.add(user);
			}
			res.close();
			st.close();
			conn.close();
			return result;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public List<String> getCities(){
		String sql = "SELECT DISTINCT City "
				+ "FROM business "
				+ "ORDER BY City";
		List<String> città = new ArrayList<>();
		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement statement = conn.prepareStatement(sql);
			ResultSet set = statement.executeQuery();
			while(set.next()) {
				città.add(new String(set.getString("City")));
			}
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
			return null;		
		}
		return città;
	}
	
	public List<Business> getBusinesses(String city){
		List<Business> businesses = new ArrayList<>();
		String sql = "SELECT * "
				+ "FROM business "
				+ "WHERE City = ? "
				+ "ORDER BY business_name ";
		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement statement = conn.prepareStatement(sql);
			statement.setString(1, city);
			ResultSet set = statement.executeQuery();
			while (set.next()) {
				Business business = new Business(set.getString("business_id"), 
						set.getString("full_address"),
						set.getString("active"),
						set.getString("categories"),
						set.getString("city"),
						set.getInt("review_count"),
						set.getString("business_name"),
						set.getString("neighborhoods"),
						set.getDouble("latitude"),
						set.getDouble("longitude"),
						set.getString("state"),
						set.getDouble("stars"));
				businesses.add(business);
			}
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
			return null;	
		}
		return businesses;
	}
	
	public List<Review> getReviews(Business b){
		List<Review> reviews = new ArrayList<>();
		String sql = "SELECT * "
				+ "FROM reviews "
				+ "WHERE business_id = ? ";
		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement statement = conn.prepareStatement(sql);
			statement.setString(1, b.getBusinessId());
			ResultSet set = statement.executeQuery();
			while(set.next()) {
				Review review = new Review(set.getString("review_id"), 
						set.getString("business_id"),
						set.getString("user_id"),
						set.getDouble("stars"),
						set.getDate("review_date").toLocalDate(),
						set.getInt("votes_funny"),
						set.getInt("votes_useful"),
						set.getInt("votes_cool"),
						set.getString("review_text"));
				reviews.add(review);
			}
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
		return reviews;
	}
	
}
