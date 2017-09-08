package ver1.util;

public class Pair<F, S> {
    private F first; //first member of pair
    private S second; //second member of pair

    public Pair(F first, S second) {
        this.first = first;
        this.second = second;
    }

    public void setFirst(F first) {
        this.first = first;
    }

    public void setSecond(S second) {
        this.second = second;
    }

    public F getFirst() {
        return first;
    }

    public S getSecond() {
        return second;
    }
    
    @Override
    public String toString() {
    	return "<" + first + ", " + second + ">";
    }
    
    @Override
    public int hashCode() {
    	return first.hashCode() ^ second.hashCode();
    }
    
    @Override
    public boolean equals(Object other) {
    	if (other instanceof Pair) {
    		Pair<?, ?> p = (Pair<?, ?>)other;
    		return first.equals(p.first) && second.equals(p.second);
    	}
    	return false;
    }
}
