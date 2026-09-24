package com.mycompany.hotel_management_system;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class RoomDAO {

    public List<Room> getAllRooms() {

        List<Room> rooms = new ArrayList<>();

        String sql = "SELECT RoomID, RoomNumber, RoomType, Price, Status FROM Rooms";

        try (Connection connection = DatabaseConnection.getConnection(); PreparedStatement statement = connection.prepareStatement(sql); ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {

                Room room = new Room();

                room.setRoomID(resultSet.getInt("RoomID"));
                room.setRoomNumber(resultSet.getString("RoomNumber"));
                room.setRoomType(resultSet.getString("RoomType"));
                room.setPrice(resultSet.getDouble("Price"));
                room.setStatus(resultSet.getString("Status"));

                rooms.add(room);
            }

        } catch (Exception e) {
            System.out.println("Error loading rooms from database.");
            e.printStackTrace();
        }

        return rooms;

    }

    public void addRoom(Room room) {

        String sql = "INSERT INTO Rooms (RoomNumber, RoomType, Price, Status) "
                + "VALUES (?, ?, ?, ?)";

        try (Connection connection = DatabaseConnection.getConnection(); PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, room.getRoomNumber());
            statement.setString(2, room.getRoomType());
            statement.setDouble(3, room.getPrice());
            statement.setString(4, room.getStatus());

            statement.executeUpdate();

            System.out.println("Room added successfully!");

        } catch (Exception e) {
            System.out.println("Error adding room.");
            e.printStackTrace();
        }
    }
}
