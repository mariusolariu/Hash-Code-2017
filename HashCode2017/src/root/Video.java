/**
 * 
 */
package root;

/**
 * @author Marius
 *
 */
public class Video {
	public int id;
	public int requestsToN_point;
	public int totalRequests;
	public int size;
	public int storedInCache;
	public int endpointWithMostRequests;
	
	public Video(int id, int size) {
		this.id = id;
		this.size = size;
	}
	
}
