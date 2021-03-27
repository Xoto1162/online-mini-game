package entities;

import utils.Coordinate;

import java.util.UUID;

public class Item extends Entity {
    private ItemType _type;

    public Item(Coordinate coordinate, ItemType type) {
        super(type.getTexture(), coordinate);
        _type = type;
    }

    public ItemType getType(){
        return _type;
    }

    public static Item fromPacket(String[] packet) {
        ItemType type = ItemType.getById(Integer.parseInt(packet[2]));
        if (type != null) {
            Item item = new Item(new Coordinate(Integer.parseInt(packet[3]), Integer.parseInt(packet[4])), type);
            item._uuid = UUID.fromString(packet[1]);
            return item;
        }
        return null;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;

        Item item = (Item) o;

        return this.getType().equals(item.getType())
                && this.getCoordinate().equals(item.getCoordinate())
                && this.getTexture().equals(item.getTexture());
    }
}
