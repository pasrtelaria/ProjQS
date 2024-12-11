package org.example;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class RoomManager implements Serializable {
    private static final long serialVersionUID = 1L;
    private Room pcRoot;
    private Room root;
    private static final String ROOMS_FILE = "rooms.ser";

    public RoomManager() {
        loadRooms();
    }

    public void addRoom(boolean pc, int num, int capacity) {
        Room room = new Room(num, capacity);

        if (pc) {
            if (pcRoot == null) {
                pcRoot = room;
            } else {
                addRoomToBST(pcRoot, room);
            }
        } else {
            if (root == null) {
                root = room;
            } else {
                addRoomToBST(root, room);
            }
        }
        saveRooms();
    }

    private void addRoomToBST(Room current, Room newRoom) {
        if (newRoom.getNum() < current.getNum()) {
            if (current.getLeft() == null) {
                current.setLeft(newRoom);
            } else {
                addRoomToBST(current.getLeft(), newRoom);
            }
        } else {
            if (current.getRight() == null) {
                current.setRight(newRoom);
            } else {
                addRoomToBST(current.getRight(), newRoom);
            }
        }
    }

    public Room searchCapacity(Room current, int cap) {
        if (current == null) {
            return null;
        }

        Room highestCapacityRoom = null;

        while (current != null) {
            if (!current.isTimeout() && current.getCapacity() >= cap) {
                return current;
            }

            if (!current.isTimeout() && (highestCapacityRoom == null || current.getCapacity() > highestCapacityRoom.getCapacity())) {
                highestCapacityRoom = current;
            }

            if (current.getCapacity() < cap) {
                current = current.getRight();
            } else {
                current = current.getLeft();
            }
        }

        return highestCapacityRoom;
    }

    public List<Room> searchRoom(Room current, int cap) {
        List<Room> results = new ArrayList<>();
        Room room = searchCapacity(current, cap);
        while (room != null && cap > 0) {
            results.add(room);
            cap -= room.getCapacity();
            room.setTimeout(true);
            room = searchCapacity(current, cap);
        }
        return results;
    }

    public Room getRoot() {
        return root;
    }

    public Room getPcRoot() {
        return pcRoot;
    }

    private void saveRooms() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(ROOMS_FILE))) {
            oos.writeObject(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadRooms() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(ROOMS_FILE))) {
            RoomManager loaded = (RoomManager) ois.readObject();
            this.pcRoot = loaded.pcRoot;
            this.root = loaded.root;
        } catch (FileNotFoundException e) {
            System.out.println("Rooms file not found, starting with an empty room list.");
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}