package pxb.android.axml;

public abstract class NodeVisitor {

    public static final int TYPE_FIRST_INT = 0x10;
    public static final int TYPE_INT_HEX = 0x11;
    static final int TYPE_INT_BOOLEAN = 0x12;
    static final int TYPE_REFERENCE = 0x01;
    static final int TYPE_STRING = 0x03;
    NodeVisitor nv;

    NodeVisitor() {
        super();
    }

    protected NodeVisitor(NodeVisitor nv) {
        super();
        this.nv = nv;
    }

    /**
     * add attribute to the node
     *
     * @param ns
     * @param name
     * @param resourceId
     * @param type       {@link #TYPE_STRING} or others
     * @param obj        a string for {@link #TYPE_STRING} ,and Integer for others
     */
    public void attr(String ns, String name, int resourceId, int type, Object obj) {
        if (nv != null) {
            nv.attr(ns, name, resourceId, type, obj);
        }
    }

    /**
     * create a child node
     *
     * @param ns
     * @param name
     * @return
     */
    public NodeVisitor child(String ns, String name) {
        if (nv != null) {
            return nv.child(ns, name);
        }
        return null;
    }

    /**
     * end the visit
     */
    public void end() {
        if (nv != null) {
            nv.end();
        }
    }

    /**
     * line number in the .xml
     *
     * @param ln
     */
    public void line(int ln) {
        if (nv != null) {
            nv.line(ln);
        }
    }

    /**
     * the node text
     *
     * @param value
     */
    public void text(int lineNumber, String value) {
        if (nv != null) {
            nv.text(lineNumber, value);
        }
    }
}