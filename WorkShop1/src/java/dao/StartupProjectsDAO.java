/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import dto.StartupProjectsDTO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import utils.DButils;

/**
 *
 * @author PC
 */
public class StartupProjectsDAO implements IDAO<StartupProjectsDTO, String> {

    @Override
    public boolean create(StartupProjectsDTO entity) {
        String sql = "INSERT INTO [tblStartupProjects] (project_name, description, status, estimated_launch) "
                + "VALUES (?, ?, ?, ?)";
        try {
            Connection conn = DButils.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, entity.getProject_name());
            ps.setString(2, entity.getDescription());
            ps.setString(3, entity.getStatus());
            ps.setDate(4, entity.getEstimated_launch());
            
            int i = ps.executeUpdate();
            return i>0;
        } catch (Exception e) {
            System.out.println(e.toString());
        }
        return false;

    }

    @Override
    public List<StartupProjectsDTO> readAll() {
        return null;
    }

    @Override
    public StartupProjectsDTO readByID(String id) {
        return null;
    }

    @Override
    public boolean update(StartupProjectsDTO entity) {
        return false;
    }

    @Override
    public boolean delete(String id) {
        return false;
    }

    @Override
    public List<StartupProjectsDTO> search(String searchTerm) {
        List<StartupProjectsDTO> result = new ArrayList<>();
        String sql = "SELECT * FROM [tblStartupProjects] WHERE project_name LIKE ?";
        try {
            Connection conn = DButils.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, "%" + searchTerm + "%");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                StartupProjectsDTO spd = new StartupProjectsDTO(
                        rs.getInt("project_id"),
                        rs.getString("project_name"),
                        rs.getString("Description"),
                        rs.getString("Status"),
                        rs.getDate("estimated_launch")
                );
                result.add(spd);
            }
        } catch (Exception e) {
            System.out.println(e.toString());
        }
        return result;
    }

    public boolean updateStatus(int id, String status) {
        String sql = "UPDATE [tblStartupProjects] SET Status = ? WHERE project_id = ?";
        try {
            Connection conn = DButils.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, status);
            ps.setInt(2, id);
            int i = ps.executeUpdate();
            return i > 0;
        } catch (Exception e) {
            System.out.println(e.toString());
        }
        return false;
    }

    public StartupProjectsDTO getProjectByID(int projectID) {
        String sql = "SELECT * FROM [tblStartupProjects] WHERE project_id = ?";
        try {
            Connection conn = DButils.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, projectID);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new StartupProjectsDTO(
                        rs.getInt("project_id"),
                        rs.getString("project_name"),
                        rs.getString("Description"),
                        rs.getString("Status"),
                        rs.getDate("estimated_launch")
                );
            }
        } catch (Exception e) {
            System.out.println(e.toString());
        }
        return null;
    }
}
