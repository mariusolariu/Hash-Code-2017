/**
 * 
 */
package root;

/**
 * @author Marius
 *
 */
public class Endpoint {
	
	public int datacenterDelay;
	public int[] cacheDelay;
	public int[] videoRequest; // the index represents the video, nigga!
	
	public Endpoint(int datacenterDelay, int numberOfCaches, int noVideos) {
		
		this.datacenterDelay = datacenterDelay;
		cacheDelay = new int[numberOfCaches];
		
		for ( int i = 0; i < numberOfCaches; i++ ) {
			
			cacheDelay[i] = -1;
		}
		
		videoRequest = new int[noVideos];
	}
	
	@Override
	public String toString() {
		StringBuffer buff = new StringBuffer();
		
		buff.append("datacenterDelay=" + datacenterDelay + "\n");
		
		for ( int i = 0; i < cacheDelay.length; i++ ) {
			if ( cacheDelay[i] != -1 ) {
				buff.append("cacheServer " + i + ":" + cacheDelay[i] + "\n");
			}
		}
		
		for ( int i = 0; i < videoRequest.length; i++ ) {
			if ( videoRequest[i] != -1 ) {
				buff.append("video " + i + ":" + videoRequest[i] + "\n");
			}
		}
		
		return buff.toString();
	}
	
	public int getBestCache(int videoSize) {
		
		int minDelayCacheIndex = -1;
		
		for ( int i = 0; i < cacheDelay.length; i++ ) {
			if ( cacheDelay[i] != -1 ) {
				minDelayCacheIndex = i;
				System.out.println(cacheDelay[i]);
				break;
				
			}
		}
		
		boolean found = false;
		for ( int i = 0; i < cacheDelay.length; i++ ) {
			if ( (cacheDelay[i] != -1) && (cacheDelay[i] < cacheDelay[minDelayCacheIndex])
			        && (MainClass.cacheServers[i].getSizeAvailable() >= videoSize) ) {
				minDelayCacheIndex = i;
				found = true;
				
			}
			
		}
		
		return found ? minDelayCacheIndex : -1;
	}
	
}
