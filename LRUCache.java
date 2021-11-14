import java.util.HashMap;
import java.util.Map;

/**
 * An implementation of <tt>Cache</tt> that uses a least-recently-used (LRU)
 * eviction policy.
 */
public class LRUCache<T, U> implements Cache<T, U> {
	final private Map<T,CachedObject> cache;
	final private DataProvider<T,U> provider;
	private CachedObject back;
	private CachedObject front;
	private int capacity;
	private int timesMissed;
	/**
	 * @param provider the data provider to consult for a cache miss
	 * @param capacity the exact number of (key,value) pairs to store in the cache
	 */
	public LRUCache (DataProvider<T, U> provider, int capacity) {
		if (capacity < 1) {
			throw new IllegalArgumentException("capacity must be at least 1");
		}
		else {
			cache = new HashMap<T,CachedObject>(capacity);
			this.provider = provider;
			this.capacity = capacity;
			front = null;
			back = null;
		}
	}
	

	/**
	 * Returns the value associated with the specified key.
	 * @param key the key
	 * @return the value associated with the key
	 */
	public U get (T key) {
		// TODO: implement me
		if(isInCache(key)) {
			requestedObj = cache.get(key);
			placeAtFront(requestedObj);
			return requestedObj;
		}
		
		else {
			final CachedObject newObject = new CachedObject(key, provider.get(key));
			if (cache.size() >= capacity) {
				evict();
			}
			cache.put(key, newObject);
			placeAtFront(newObject);
			return newObject;
		}
	}
	
	private placeAtFront (CachedObject obj) {
		x.before = null;
		x.after = front;
		if(front != null) front.before = x;
		front = x;
		if(back == null) back = front;
	}
	
	private evict () {
		cache.remove(back.key);
		back = back.before;
	}

	/**
	 * Returns the number of cache misses since the object's instantiation.
	 * @return the number of cache misses since the object's instantiation.
	 */
	public int getNumMisses () {
		return 0;  // TODO: implement me
	}

	/**
	 * Returns whether the object with the specified key is contained in the cache.
	 * @param the key of the object
	 * @return whether the object is contained in the cache.
	 */
	public boolean isInCache (T key) {
		// TODO: implement me
		return cache.containsKey(key);
	}
}
