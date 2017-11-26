/**
 * @author rbk
 * Ver 1.0: 2017/09/29
 * Example to extend Graph/Vertex/Edge classes to implement algorithms in which nodes and edges
 * need to be disabled during execution.  Design goal: be able to call other graph algorithms
 * without changing their codes to account for disabled elements.
 * <p>
 * Ver 1.1: 2017/10/09
 * Updated iterator with boolean field ready. Previously, if hasNext() is called multiple
 * times, then cursor keeps moving forward, even though the elements were not accessed
 * by next().  Also, if program calls next() multiple times, without calling hasNext()
 * in between, same element is returned.  Added UnsupportedOperationException to remove.
 **/

package cs6301.g1025;

import cs6301.g00.ArrayIterator;

import java.util.*;

public class XGraph extends Graph {
    public static final int INFINITY = Integer.MAX_VALUE;

    public static class XVertex extends Vertex {
        boolean disabled;
        List<XEdge> xadj;
        List<XEdge> xrevAdj;
        int height;
        int excess;
        boolean seen;
        int distance;
        Vertex parent;
        Edge parentEdge;

        XVertex(Vertex u) {
            super(u);
            disabled = false;
            xadj = new LinkedList<>();
            xrevAdj = new LinkedList<>();
            height = 0;
            excess = 0;
            distance = INFINITY;
            parent = null;
            parentEdge = null;
        }

        @Override
        public Iterator<Edge> iterator() {
            return new XVertexIterator(this);
        }

        class XVertexIterator implements Iterator<Edge> {
            XEdge cur;
            Iterator<XEdge> it;
            boolean ready;

            XVertexIterator(XVertex u) {
                this.it = u.xadj.iterator();
                ready = false;
            }

            public boolean hasNext() {
                if (ready) {
                    return true;
                }
                if (!it.hasNext()) {
                    return false;
                }
                cur = it.next();
                while (cur.flow >= cur.capacity && it.hasNext()) {
                    cur = it.next();
                }
                if (cur.flow < cur.capacity) {
                    ready = true;

                } else {
                    ready = false;

                }
                return ready;
            }

            public Edge next() {
                if (!ready) {
                    if (!hasNext()) {
                        throw new java.util.NoSuchElementException();
                    }
                }
                ready = false;
                return cur;
            }

            public void remove() {
                throw new java.lang.UnsupportedOperationException();
            }
        }
    }

    static class XEdge extends Edge {
        int capacity;
        int flow;
        XEdge reverseEdge;

        XEdge(XVertex from, XVertex to, int weight) {
            super(from, to, weight);
            capacity = weight;
            flow = 0;
        }

        XEdge(XVertex from, XVertex to, int flow, int capacity, int name) {
            super(from, to, 0, name);
            this.capacity = capacity;
            this.flow = flow;
            this.from = from;
            this.to = to;
        }

        int capacity() {
            return capacity;
        }

        int flow() {
            return flow;
        }

    }

    XVertex[] xv; // vertices of graph
    XEdge[] edges;

    public XGraph(Graph g, HashMap<Edge, Integer> capacity) {
        super(g);
        xv = new XVertex[g.size()]; // Extra space is allocated in array for
        // nodes to be added later
        for (Vertex u : g) {
            xv[u.getName()] = new XVertex(u);
            xv[u.getName()].height=0;
            xv[u.getName()].excess=0;

        }
        edges = new XEdge[g.m + 1];

        // Make copy of edges
        for (Vertex u : g) {
            for (Edge e : u) {
                Vertex v = e.otherEnd(u);
                XVertex x1 = getVertex(u);
                XVertex x2 = getVertex(v);
                XEdge xe1 = new XEdge(x1, x2, 0, capacity.get(e), e.getName());
                XEdge xe2 = new XEdge(x2, x1, 0, 0, -e.getName());
                xe1.reverseEdge = xe2;// adding the corresponding reverse edge
                // to these edges
                xe2.reverseEdge = xe1;// adding the corresponding reverse edge

                edges[e.getName()] = xe1;
                // to these edges
                x1.xadj.add(xe1);
                x2.xadj.add(xe2);
                x1.xrevAdj.add(xe2);
                x2.xrevAdj.add(xe1);

            }
        }
    }

    @Override
    public Iterator<Vertex> iterator() {
        return new XGraphIterator(this);
    }

    class XGraphIterator implements Iterator<Vertex> {
        Iterator<XVertex> it;
        XVertex xcur;
        boolean ready;

        XGraphIterator(XGraph xg) {
            this.it = new ArrayIterator<XVertex>(xg.xv, 0, xg.size() - 1);
        }

        public boolean hasNext() {
            if (ready) {
                return true;
            }
            if (!it.hasNext()) {
                return false;
            }
            xcur = it.next();
            ready = true;
            return ready;
        }

        public Vertex next() {
            if (!ready) {
                if (!hasNext()) {
                    throw new java.util.NoSuchElementException();
                }
            }
            ready = false;
            return xcur;
        }

        public void remove() {
            throw new java.lang.UnsupportedOperationException();
        }

    }

    XEdge getEdge(Edge e) {
        return edges[e.getName()];
    }

    @Override
    public String toString() {
        for (Vertex u : this) {
            XVertex xu = this.getVertex(u);
            System.out.println("AjacencyList for Vertex " + u + " :"+ "and its excess  "+xu.excess+" height  "+xu.height);
            for (Edge e : xu.xadj) {
                XEdge xe = (XEdge) e;
                XVertex xv=this.getVertex(xe.otherEnd(xu));
                System.out.print(e + " flow " + xe.flow + " capacity " + xe.capacity+",");
            }
            System.out.println();
        }

        return "";
    }


    /**
     * HELPER METHODS
     */

    boolean seen(Vertex v){
        XVertex xv = (XVertex) v;
        return xv.seen;
    }

    void setSeen(Vertex v){
        XVertex xv = (XVertex) v;
        xv.seen = true;
    }

    void setDistance(Vertex v, int distance){
        XVertex xv = (XVertex) v;
        xv.distance = distance;
    }

    int getDistance(Vertex v){
        XVertex xv = (XVertex) v;
        return xv.distance;
    }

    void setParent(Vertex v, Vertex parent){
        XVertex xv = (XVertex) v;
        xv.parent = parent;
    }

    void resetDistance(Vertex v){
        XVertex xv = (XVertex) v;
        xv.distance = INFINITY;
    }

    void resetParent(Vertex v){
        XVertex xv = (XVertex) v;
        xv.parent = null;
        xv.parentEdge = null;
    }

    void resetSeen(Vertex v){
        XVertex xv = (XVertex) v;
        xv.seen = false;
    }

    int height(Vertex v){
        XVertex xv = (XVertex) v;
        return xv.height;
    }

    void setHeight(Vertex v, int value){
        XVertex xv = (XVertex) v;
        xv.height = value;
    }

    int excess(Vertex v){
        XVertex xv = (XVertex) v;
        return xv.excess;
    }

    void setExcess(Vertex v, int value){
        XVertex xv = (XVertex) v;
        xv.excess = value;
    }

    int flow(Edge e) {
        XEdge xe = getEdge(e.getName());
        return xe.flow();
    }

    void setFlow(Edge e, int value) {
        XEdge xe = getEdge(e.getName());
        xe.flow = value;
    }

    int capacity(Edge e) {
        XEdge xe = getEdge(e.getName());
        return xe.capacity();
    }

    void setCapacity(Edge e, int value) {
        XEdge xe = getEdge(e.getName());
        xe.capacity = value;
    }

    List<XEdge> getAdj(Vertex v){
        XVertex xv = (XVertex) v;
        return xv.xadj;
    }

    List<XEdge> getRevAdj(Vertex v){
        XVertex xv = (XVertex) v;
        return xv.xrevAdj;
    }

    @Override
    public Vertex getVertex(int n) {
        return xv[n];
    }

    XVertex getVertex(Vertex u) {
        return Vertex.getVertex(xv, u);
    }

    XEdge getEdge(int name) {
        return edges[name];
    }

//    void printGraph(BFS b) {
//        for (Vertex u : this) {
//            System.out.print("  " + u + "  :   " + b.distance(u) + "  : ");
//            for (Edge e : u) {
//                System.out.print(e);
//            }
//            System.out.println();
//        }
//
//    }

}

/*
Sample output:

Node : Dist : Edges
  1  :   0  : (1,2)(1,3)
  2  :   1  : (2,1)(2,4)(2,5)
  3  :   1  : (3,1)(3,6)(3,7)
  4  :   2  : (4,2)(4,8)
  5  :   2  : (5,2)
  6  :   2  : (6,3)
  7  :   2  : (7,3)(7,9)
  8  :   3  : (8,4)
  9  :   3  : (9,7)
Source: 1 Farthest: 8 Distance: 3

Disabling vertices 8 and 9
  1  :   0  : (1,2)(1,3)
  2  :   1  : (2,1)(2,4)(2,5)
  3  :   1  : (3,1)(3,6)(3,7)
  4  :   2  : (4,2)
  5  :   2  : (5,2)
  6  :   2  : (6,3)
  7  :   2  : (7,3)
Source: 1 Farthest: 4 Distance: 2

*/