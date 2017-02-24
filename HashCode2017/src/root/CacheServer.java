/**
 * 
 */
package root;

import java.util.ArrayList;

/**
 * @author Marius
 *
 */
public class CacheServer {
	public int totalSize;
	public int sizeAvailable;
	public int Id;
	
	public ArrayList<Video> storedVideos;
	
	public CacheServer(int Id, int totalSize) {
		this.Id = Id;
		this.totalSize = totalSize;
		sizeAvailable = totalSize;
		
		storedVideos = new ArrayList<>();
		
	}
	
	public void setSizeAvailable(int needed) {
		sizeAvailable -= needed;
				
	}
	
	public int getSizeAvailable() {
		return sizeAvailable;
	}
	
	public void addVideo(Video video) {
		storedVideos.add(video);
		System.out.println(this.Id + " video id:" + video.id);
	}
}
