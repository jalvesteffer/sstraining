/**
 * 
 */
package com.ss.training.wk2.project.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.ss.training.wk2.project.entity.Branch;

/**
 * @author jalveste
 *
 */
public class BranchDAO extends BaseDAO<Branch> {

	public BranchDAO(Connection conn) {
		super(conn);
	}

	public void addBranch(Branch branch) throws SQLException, ClassNotFoundException {

		// create a statement Object
		save("INSERT INTO tbl_library_branch (branchName, branchAddress) VALUES (?, ?)",
				new Object[] { branch.getBranchName(), branch.getBranchAddress() });
	}

	public void updateBranch(Branch branch) throws SQLException, ClassNotFoundException {

		save("UPDATE tbl_library_branch SET branchName=?, branchAddress=? WHERE branchId=?",
				new Object[] { branch.getBranchName(), branch.getBranchAddress(), branch.getBranchId() });
	}

	public void deleteBranch(Branch branch) throws ClassNotFoundException, SQLException {

		save("DELETE FROM tbl_library_branch WHERE branchId=?", new Object[] { branch.getBranchId() });
	}

	public List<Branch> readAllBranches() throws ClassNotFoundException, SQLException {

		return read("SELECT * FROM tbl_library_branch", null);

	}

	public List<Branch> readBranchById(Integer branchId) throws ClassNotFoundException, SQLException {

		return read("SELECT * FROM tbl_library_branch WHERE branchId=?", new Object[] { branchId });

	}
	
	public List<Branch> readBranchByName(String name) throws ClassNotFoundException, SQLException {

		return read("SELECT * FROM tbl_library_branch WHERE branchName=?", new Object[] { name });

	}

	@Override
	public List<Branch> extractData(ResultSet rs) throws SQLException {
		List<Branch> branches = new ArrayList<>();
		while (rs.next()) {
			Branch branch = new Branch();
			branch.setBranchId(rs.getInt("branchId"));
			branch.setBranchName(rs.getString("branchName"));
			branch.setBranchAddress(rs.getString("branchAddress"));
			branches.add(branch);
		}

		return branches;
	}

}
