


public class Pair<T,S> {
	private T elt1;
	private S elt2;
	public Pair(T elt1, S elt2){
		this.elt1 = elt1;
		this.elt2 = elt2;
	}
	public T getElementA(){
		return elt1;
	}
	public S getElementB(){
		return elt2;
	}
}
