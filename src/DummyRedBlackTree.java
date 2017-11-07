
public class DummyRedBlackTree <E extends Comparable<E>>
{
    private static final boolean RED = false;
    private static final boolean BLACK = true;
    private Node<E> root;

    // NOTE do NOT define any constructors for this project -- the compiler will
    //      generate a default (public, no arguments) constructor for you

    public boolean insert(E element) throws NullPointerException
    {
        // TODO your code goes here
    		throw new UnsupportedOperationException("not implemented yet");
    }

    public boolean contains(Comparable<E> object)
    {
        // TODO your code goes here
    		throw new UnsupportedOperationException("not implemented yet");
    }

    @Override
    public String toString()
    {
        // TODO your code goes here
    		throw new UnsupportedOperationException("not implemented yet");
    }

    // TODO add any helper methods here (optional)

    static class Node <E extends Comparable<E>>
    {
        E element;
        Node<E> leftChild;
        Node<E> rightChild;
        Node<E> parent;
        boolean color;
    }
}
