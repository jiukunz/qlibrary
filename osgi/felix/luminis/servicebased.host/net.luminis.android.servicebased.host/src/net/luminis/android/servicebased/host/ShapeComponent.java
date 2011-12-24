package net.luminis.android.servicebased.host;

import net.luminis.android.servicebased.host.service.SimpleShape;

public class ShapeComponent {
    private final SimpleShape m_shape;
    private final int m_x;
    private final int m_y;

    public ShapeComponent(SimpleShape shape, int x, int y) {
        m_shape = shape;
        m_x = x;
        m_y = y;
    }

    public SimpleShape getShape() {
        return m_shape;
    }

    public int getX() {
        return m_x;
    }

    public int getY() {
        return m_y;
    }
}
