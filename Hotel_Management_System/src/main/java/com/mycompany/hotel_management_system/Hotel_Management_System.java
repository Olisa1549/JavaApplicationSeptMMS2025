package com.mycompany.hotel_management_system;

public class Hotel_Management_System {

    public static void main(String[] args) {

        RoomDAO roomDAO = new RoomDAO();

        Room newRoom = new Room(
                0,
                "104",
                "Single",
                50000,
                "Available"
        );

        roomDAO.addRoom(newRoom);

        System.out.println("Room 104 has been added.");
    }
}